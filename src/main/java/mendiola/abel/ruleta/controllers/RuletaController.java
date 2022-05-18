package mendiola.abel.ruleta.controllers;

import mendiola.abel.ruleta.exceptions.BadRequestException;
import mendiola.abel.ruleta.exceptions.NotFoundException;
import mendiola.abel.ruleta.mapper.RuletaMapper;
import mendiola.abel.ruleta.models.dto.RuletaDTO;
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

    /**
     * 1. Endpoint de creación de nuevas ruletas que devuelva el id de la nueva ruleta creada
     * @return Retorna el id de la nueva ruleta creada
     * @BadRequestException En caso de que no se solicite correctamente
     * @author AMR - 17-mayo-2022
     */
    @PostMapping("/crear")
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

        return new ResponseEntity<Ruleta>(ruletaGuardada, HttpStatus.CREATED);
    }


    /**
     *
     2. Endpoint de apertura de ruleta (el input es un id de ruleta) que permita las
     posteriores peticiones de apuestas, este debe devolver simplemente un estado
     queconfirme que la operación fue exitosa o denegada
     * @param ruletaId
     * @param ruleta
     * @return
     */
    @PutMapping("/apertura/ruletaId/{ruletaId}")
    public ResponseEntity<?> actualizarRuleta(@PathVariable Integer ruletaId, @RequestBody Ruleta ruleta)
    {
        Optional<Ruleta> oRuleta = ruletaDao.buscarPorId(ruletaId);

        if(!oRuleta.isPresent())
            throw new NotFoundException(String.format("Las ruleta con ID %d no existe", ruletaId));

        Ruleta ruletaActualizado = ((RuletaDAO)ruletaDao).actualizarRuleta(oRuleta.get(),ruleta);
        return new ResponseEntity<Ruleta>(ruletaActualizado, HttpStatus.OK);
    }


    /**
     *
     3. Endpoint de apuesta a un número (los números válidos para apostar son del 0 al
     36)o color (negro o rojo) de la ruleta una cantidad determinada de dinero (máximo
     10.000 dólares) a una ruleta abierta.
     * @param ruletaId
     * @param ruleta
     * @return
     */

    @PutMapping("/apostar/ruletaId/{ruletaId}/numero/{numero}/color/{color}/valorApuesta/{valorApuesta}/estado/{estaAbierta}")
    public ResponseEntity<?> adicionarApuesta(@PathVariable long ruletaId, String color, Integer numero, Double valorApuesta, Boolean estaAbierta, @RequestBody Ruleta ruleta)
    {
        Optional<Ruleta> oRuleta = ruletaDao.buscarPorId(ruletaId);

        if(!oRuleta.isPresent())
        {
            throw new NotFoundException(String.format("Las ruleta con ID %d no existe", ruletaId));
        } else if (color!= "Negro" || color!= "Rojo" ) {
            throw new BadRequestException("Solo se aceptan Negro o Rojo.");
        } else if (numero< 0 && numero> 36) {
            throw new BadRequestException("El numero debe estar entre 0 y 36.");
        } else if (valorApuesta>10000.0) {
            throw new BadRequestException("La apuesta maxima es de 10,000 dolares.");
        } else if (estaAbierta==false) {
            throw new BadRequestException("La ruleta está cerrada.");
        } else {
            Ruleta ruletaActualizado = ((RuletaDAO)ruletaDao).actualizarRuleta(oRuleta.get(),ruleta);
            return new ResponseEntity<Ruleta>(ruletaActualizado, HttpStatus.OK);
        }

    }


    /**
     * 4. Endpoint de cierre apuestas dado un id de ruleta, este Endpoint debe devolver
     * el resultado de las apuestas hechas desde su apertura hasta el cierre.
     * @return
     * @NotFoundException En caso de que no encuentre ningun elemento en la base de datos
     * @author AMR - 17-mayo-2022
     */
    @PutMapping("/cierre/ruletaId/{ruletaId}/estado/{estaAbierta}")
    public ResponseEntity<?> cerrarRuleta(@PathVariable long ruletaId, Boolean estaAbierto, @RequestBody Ruleta ruleta)
    {
        Optional<Ruleta> oRuleta = ruletaDao.buscarPorId(ruletaId);

        if(!oRuleta.isPresent())
            throw new NotFoundException(String.format("Las ruleta con ID %d no existe", ruletaId));

        Ruleta ruletaActualizado = ((RuletaDAO)ruletaDao).actualizarRuleta(oRuleta.get(),ruleta);
        return new ResponseEntity<Ruleta>(ruletaActualizado, HttpStatus.OK);
    }




    /**
     * 5. Endpoint de listado de ruletas creadas con sus estados (abierta o cerrada)
     * @return Retorna una lista de ruletas en DTO
     * @NotFoundException En caso de que no encuentre ningun elemento en la base de datos
     * @author AMR - 17-mayo-2022
     */
    @GetMapping("/ruletas/dto")
    public ResponseEntity<?> obtenerRuletasDTO()
    {
        List<Ruleta> ruletas = (List<Ruleta>) ruletaDao.buscarTodos();

        if(ruletas.isEmpty())
            throw new NotFoundException("No existen ruletas en la base de datos.");

        List<RuletaDTO> listaRuletas = ruletas
                .stream()
                .map(RuletaMapper::mapRuleta)
                .collect(Collectors.toList());
        return new ResponseEntity<List<RuletaDTO>>(listaRuletas, HttpStatus.OK);
    }
}
