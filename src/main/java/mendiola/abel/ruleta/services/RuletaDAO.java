package mendiola.abel.ruleta.services;

import mendiola.abel.ruleta.models.entities.Ruleta;

import java.util.Optional;

public interface RuletaDAO
{
    // Requerimiento No1
    public Ruleta crearRuleta(Ruleta entidad);

    // Requerimiento No5
    public Iterable<Ruleta> buscarTodos();

    public Optional<Ruleta> buscarPorId(long id);

    public Ruleta guardar(Ruleta entidad);

    // Requerimiento No2 Abrir,  Requerimiento No4 Cerrar
    public Ruleta actualizarRuleta(Ruleta ruletaEncontrada, Ruleta ruleta);

    // Requerimiento No3 Apostar
    public Ruleta adicionarApuesta(Ruleta ruletaEncontrada, Ruleta ruleta);

    public Ruleta cerrarRuleta(Long id);
}
