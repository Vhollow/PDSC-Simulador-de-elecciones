package utils;

import java.util.Date;

/**
 * El Tipo de las Elecciones
 * 
 * @author daniel
 */
enum TipoEleccion
{
    CongresoDiputados,
    Autonomicas,
    Municipales,
    ParlamentoEuropeo
}


/**
 *
 * @author Daniel
 */
public class Eleccion {
    
    private TipoEleccion tipoEleccion;
    private Date fecha;
    
    
    /**
     * Crea una nueva Eleccion
     * 
     * @param tipoEleccion el Tipo de la Eleccion
     * @param fecha 
     */
    Eleccion (TipoEleccion tipoEleccion, Date fecha)
    {
        this.tipoEleccion = tipoEleccion;
        this.fecha = fecha;
    }

    /**
     * @return the tipoEleccion
     */
    public TipoEleccion getTipoEleccion() {
        return tipoEleccion;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }
    
}
