package mendiola.abel.ruleta.controllers;

import mendiola.abel.ruleta.exceptions.BadRequestException;
import mendiola.abel.ruleta.models.entities.Ruleta;
import mendiola.abel.ruleta.services.RuletaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ruleta")
public class RuletaController
{
    @Autowired
    private RuletaDAO ruletaDao;

    @GetMapping("/lista/ruletas")
    public List<Ruleta> buscarTodas()
    {
        List<Ruleta> ruletas = (List<Ruleta>) ruletaDao.buscarTodos();
        if(ruletas.isEmpty())
            throw new BadRequestException("No existen ruletas");

        return ruletas;
    }

    @GetMapping("/id/{ruletaId}")
    public Ruleta buscarRuletaPorId(@PathVariable Integer ruletaId)
    {
		/*Optional<Carrera> oCarrera = carreraDao.buscarPorId(carreraId);
		if(!oCarrera.isPresent())
			throw new BadRequestException(String.format("La carrera con ID: %d no existe", carreraId));

		return oCarrera.get();*/

        Ruleta ruleta = ruletaDao.buscarPorId(ruletaId).orElse(null);
        if(ruleta == null)
            throw new BadRequestException(String.format("La ruleta con ID: %d no existe", ruletaId));

        return ruleta;
    }

    @PostMapping
    public ResponseEntity<?> guardarRuleta(@Valid @RequestBody Ruleta ruleta, BindingResult result)
    {
        Map<String, Object> validaciones = new HashMap<String, Object>();
        if(result.hasErrors())
        {
            List<String> listaErrores = result.getFieldErrors()
                    .stream()
                    .map(errores -> "Campo: '" + errores.getField() + "' " + errores.getDefaultMessage())
                    .collect(Collectors.toList());
            validaciones.put("Lista Errores", listaErrores);
            return new ResponseEntity<Map<String, Object>>(validaciones, HttpStatus.BAD_REQUEST);
        }

        Ruleta ruletaGuardada = ruletaDao.guardar(ruleta);

        return new ResponseEntity<Carrera>(carreraGuardada, HttpStatus.CREATED);
    }

    /**
     * Endoint para actualizar un objeto de tipo carrera
     * @param carreraId Parámetro para buscar la carrera
     * @param carrera Objeto con la información a modificar
     * @return Retorna un objeto de tipo Carrera con la información actualizada
     * @NotFoundException En caso de que falle actualizando el objeto carrera
     * @author NDSC - 06/12/2021
     */
    @PutMapping("/upd/carreraId/{carreraId}")
    public ResponseEntity<?> actualizarCarrera(@PathVariable Integer carreraId, @RequestBody Carrera carrera)
    {
        Optional<Carrera> oCarrera = carreraDao.buscarPorId(carreraId);

        if(!oCarrera.isPresent())
            throw new NotFoundException(String.format("La carrera con ID: %d no existe", carreraId));

        Carrera carreraActualizada = carreraDao.actualizar(oCarrera.get(), carrera);

        return new ResponseEntity<Carrera>(carreraActualizada, HttpStatus.OK);
    }

    @DeleteMapping("/carreraId/{carreraId}")
    public ResponseEntity<?> eliminarCarrera(@PathVariable Integer carreraId)
    {
        Map<String, Object> respuesta = new HashMap<String, Object>();

        Optional<Carrera> carrera = carreraDao.buscarPorId(carreraId);

        if(!carrera.isPresent())
            throw new NotFoundException(String.format("La carrera con ID: %d no existe", carreraId));

        carreraDao.eliminarPorId(carreraId);
        respuesta.put("OK", "Carrera ID: " + carreraId + " eliminada exitosamente");
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.ACCEPTED);
    }

    /**
     * Endpoint para consultar todas las carreras
     * @return Retorna una lista de carreras en DTO
     * @NotFoundException En caso de que no encuentre ningun elemento en la base de datos
     * @author NDSC - 07-12-2021
     */
    @GetMapping("/carreras/dto")
    public ResponseEntity<?> obtenerCarrerasDTO()
    {
        List<Carrera> carreras = (List<Carrera>) carreraDao.buscarTodos();

        if(carreras.isEmpty())
            throw new NotFoundException("No existen carreras en la base de datos.");

        List<CarreraDTO> listaCarreras = carreras
                .stream()
                .map(CarreraMapper::mapCarrera)
                .collect(Collectors.toList());
        return new ResponseEntity<List<CarreraDTO>>(listaCarreras, HttpStatus.OK);
    }
}
