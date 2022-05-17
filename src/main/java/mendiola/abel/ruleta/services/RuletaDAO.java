package mendiola.abel.ruleta.services;

import mendiola.abel.ruleta.models.entities.Ruleta;

public interface RuletaDAO
{
    // Requerimiento No1
    public Ruleta crearRuleta(Ruleta entidad);

    // Requerimiento No5
    public Iterable<Ruleta> buscarTodos();

    // Requerimiento No2 Abrir,  Requerimiento No4 Cerrar
    public Ruleta actualizarRuleta(Ruleta ruletaEncontrada, Ruleta ruleta);

    // Requerimiento No3 Apostar
    public Ruleta adicionarApuesta(Ruleta ruletaEncontrada, Ruleta ruleta);
}
