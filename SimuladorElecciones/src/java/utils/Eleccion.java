package utils;

import java.util.Date;

/**
 * Clase Eleccion, representa una eleccion en nuestra base de datos
 * 
 * @author Daniel
 */
public class Eleccion {
    
    /** El id de la Eleccion en la base de datos */
    private int id;
    
    /** Fecha de creacion de la Eleccion */
    private Date fecha;
    
    /** Tipo de la Eleccion */
    private TipoEleccion tipoEleccion;
    
    
    /**
     * Crea una nueva Eleccion
     * 
     * @param tipoEleccion el Tipo de la Eleccion
     * @param fecha la fecha de creacion de la Eleccion
     */
    public Eleccion (Date fecha, TipoEleccion tipoEleccion) {
        id = -1;
        this.tipoEleccion = tipoEleccion;
        this.fecha = fecha;
    }
    
    /**
     * Crea una nueva Eleccion
     * 
     * @param id el id de la eleccion
     * @param tipoEleccion el Tipo de la Eleccion
     * @param fecha la fecha de creacion de la Eleccion
     */
    public Eleccion (int id, Date fecha, TipoEleccion tipoEleccion) {
        this.id = id;
        this.fecha = fecha;
        this.tipoEleccion = tipoEleccion;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @return the tipoEleccion
     */
    public TipoEleccion getTipoEleccion() {
        return tipoEleccion;
    }
    
}
