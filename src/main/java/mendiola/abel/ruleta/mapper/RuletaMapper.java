package mendiola.abel.ruleta.mapper;

import mendiola.abel.ruleta.models.dto.RuletaDTO;
import mendiola.abel.ruleta.models.entities.Ruleta;

public class RuletaMapper
{
    public static RuletaDTO mapRuleta(Ruleta ruleta)
    {
        RuletaDTO ruletaDTO = new RuletaDTO();
        ruletaDTO.setId(ruleta.getId());
        ruletaDTO.setEstaAbierta(ruleta.getEstaAbierta());
        return ruletaDTO;
    }
}
