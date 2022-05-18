package mendiola.abel.ruleta.repositories;


import mendiola.abel.ruleta.models.entities.Ruleta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface RuletaRepository extends CrudRepository<Ruleta,Integer>
{
    public Ruleta crearRuleta(Ruleta entidad);

    public Iterable<Ruleta> buscarTodos();

    public Ruleta actualizarRuleta(Ruleta ruletaEncontrada, Ruleta ruleta);

    public Ruleta adicionarApuesta(Ruleta ruletaEncontrada, Ruleta ruleta);

    @Query("select r from Ruleta r where r.id = ?1 and sum()")
    public Ruleta cerrarRuleta(Long id);

}
