package mendiola.abel.ruleta.repositories;


import mendiola.abel.ruleta.models.entities.Ruleta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface RuletaRepository extends CrudRepository<Ruleta,Integer>
{
    public Ruleta saveRuleta(Ruleta entidad);
    public Optional<Ruleta> findRuletaById(Long id);

}
