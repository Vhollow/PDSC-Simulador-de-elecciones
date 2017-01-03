package utils;

/**
 * Enum TipoEleccion, representa el Tipo de una Eleccion
 * 
 * @author daniel
 */
public enum TipoEleccion {
    
    Otro(0),
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
}
