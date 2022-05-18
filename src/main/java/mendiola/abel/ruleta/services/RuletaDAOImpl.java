package mendiola.abel.ruleta.services;

import mendiola.abel.ruleta.models.entities.Ruleta;
import mendiola.abel.ruleta.repositories.RuletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RuletaDAOImpl implements RuletaDAO
{
    private RuletaRepository repository;

    @Autowired
    public RuletaDAOImpl(RuletaRepository repository)
    {
        this.repository = repository;
    }


    @Override
    public Ruleta crearRuleta(Ruleta entidad) {
        return null;
    }

    @Override
    public Iterable<Ruleta> buscarTodos() {
        return null;
    }

    @Override
    public Optional<Ruleta> buscarPorId(long id) {
        return Optional.empty();
    }

    @Override
    public Ruleta guardar(Ruleta entidad) {
        return null;
    }

    @Override
    @Transactional
    public Ruleta actualizarRuleta(Ruleta ruletaEncontrado, Ruleta ruleta)
    {
        Ruleta ruletaActualizado = null;
        ruletaEncontrado.setColor(ruleta.getColor());
        ruletaEncontrado.setNumero(ruleta.getNumero());
        ruletaEncontrado.setValorApuesta(ruleta.getValorApuesta());
        ruletaActualizado = repository.save(ruletaEncontrado);
        return ruletaActualizado;
    }

    @Override
    public Ruleta adicionarApuesta(Ruleta ruletaEncontrado, Ruleta ruleta) {
        return null;
    }

    @Override
    public Ruleta cerrarRuleta(Long id) {
        return null;
    }
}
