package utils;

/**
 * Enum TipoEleccion, representa el Tipo de una Eleccion
 * 
 * @author daniel
 */
public enum TipoEleccion {
    
    CongresoDiputados(1),
    Autonomicas(2),
    Municipales(3),
    ParlamentoEuropeo(4);
    
    private final int valor;
    private TipoEleccion(int valor) {
        this.valor = valor;
    }
    
    /**
     * @return el valor entero (indice en la base de datos) del TipoEleccion
     */
    public int getValor() {
        return valor;
    }
    
    /**
     * Transforma un entero al tipo Eleccion
     * @param num el entero que queremos transformar (de 1 a 4)
     * @return  el enum o null en caso de no poder transformarlo
     */
    public static TipoEleccion numToTipoEleccion(int num) {
        TipoEleccion ret = null;
        
        if (num == CongresoDiputados.valor) {
            ret = CongresoDiputados;
        } else if (num == Autonomicas.valor) {
            ret = Autonomicas;
        } else if (num == Municipales.valor) {
            ret = Municipales;
        } else if (num == ParlamentoEuropeo.valor) {
            ret = ParlamentoEuropeo;
        }
        
        return ret;
    }
    
}
