package mendiola.abel.ruleta.services;

import mendiola.abel.ruleta.models.entities.Ruleta;

import java.util.Optional;

public interface RuletaDAO
{
    public Ruleta saveRuleta(Ruleta entidad);

    public Ruleta actualizarRuleta(Ruleta ruletaEncontrada, Ruleta ruleta);

    public Optional<Ruleta> findRuletaById(Long id);


    public Iterable<Ruleta> findAll();


}
