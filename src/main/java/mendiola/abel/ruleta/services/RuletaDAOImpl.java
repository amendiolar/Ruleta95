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
        super();
    };


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
    public Ruleta actualizarRuleta(Ruleta ruletaEncontrada, Ruleta ruleta)
    {
        Ruleta ruletaActualizada = null;
        ruletaEncontrada.setColor();
        ruletaEncontrada.setNumero();
        ruletaEncontrada.setValorApuesta();
        ruletaActualizada = repository.save(ruletaEncontrada);
        return ruletaActualizada;
    }

    @Override
    public Ruleta adicionarApuesta(Ruleta ruletaEncontrada, Ruleta ruleta) {
        return null;
    }
}
