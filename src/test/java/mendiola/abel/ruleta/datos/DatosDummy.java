package mendiola.abel.ruleta.datos;

import mendiola.abel.ruleta.models.entities.Ruleta;

public class DatosDummy
{
    public static Ruleta ruletacreacion01(){return new Ruleta(null, " ",null, false,null);}
    public static Ruleta ruletacreacion02(){return new Ruleta(null, " ",null, false,null);}
    public static Ruleta ruletacreacion03(){return new Ruleta(null, " ",null, false,null);}

    public static Ruleta ruletaapertura01(){return new Ruleta(null, " ",null, false,null);}

    public static Ruleta ruletaapuesta01(){return new Ruleta(null, "Negro",null, true,5000.0);}
    public static Ruleta ruletaapuesta02(){return new Ruleta(null, "",5, true,5000.0);}
    public static Ruleta ruletaapuesta03(){return new Ruleta(null, "Negro",null, true,15000.0);}
    public static Ruleta ruletaapuesta04(){return new Ruleta(null, " ",5, true,25000.0);}

    public static Ruleta ruletacierre01(){return new Ruleta(null, " ",null, true,null);}
}
