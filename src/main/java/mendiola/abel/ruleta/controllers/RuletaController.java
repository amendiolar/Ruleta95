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
     * @return Retorna una lista de ruletas en DTO
     * @NotFoundException En caso de que no encuentre ningun elemento en la base de datos
     * @author AMR - 17-mayo-2022
     */
    @PostMapping("/ruleta/crear")
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
