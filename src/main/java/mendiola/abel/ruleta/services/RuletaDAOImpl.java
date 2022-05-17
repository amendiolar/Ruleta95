package mendiola.abel.ruleta.services;

import mendiola.abel.ruleta.models.entities.Ruleta;
import mendiola.abel.ruleta.repositories.RuletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RuletaDAOImpl implements RuletaDAO
{
    @Autowired
    public RuletaDAOImpl(RuletaRepository repository)
    {
        super(repository);
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
    @Transactional
    public Ruleta actualizarRuleta(Ruleta ruletaEncontrado, Ruleta ruleta)
    {
        Ruleta ruletaActualizado = null;
        ruletaEncontrado.setColor();
        ruletaEncontrado.setNumero();
        ruletaEncontrado.setValorApuesta();
        ruletaActualizado = repository.save(ruletaEncontrado);
        return ruletaActualizado;
    }

    @Override
    public Ruleta adicionarApuesta(Ruleta ruletaEncontrado, Ruleta ruleta) {
        return null;
    }
}
