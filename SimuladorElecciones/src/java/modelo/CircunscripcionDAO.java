package modelo;

import utils.Circunscripcion;

/**
 * Interface CircunscripcionDAO, es la interfaz empleada para acceder a la Base
 * de Datos de la Aplicación para introducir, obtener o borrar datos de
 * Circunscripcion.
 * 
 * @author daniel
 */
public interface CircunscripcionDAO {
    
    /**
     * Introduce la circunscripcion especificada en la base de datos.
     * 
     * @param   idEleccion el id de la Eleccion al que pertenece la
     *          Circunscripcion
     * @param   circunscripcion los datos de la Circunscripcion que queremos
     *          introducir en la base de datos.
     * @return  true si se introdujeron los datos correctamente en la base de
     *          datos, false en caso constrario
     */
    public boolean insertCircunscripcion(
            int idEleccion, Circunscripcion circunscripcion
    );

    /**
     * Devuelve la circunscripcion con el mismo nombre e id que el especificado.
     * 
     * @param   idEleccion el id de la eleccion.
     * @param   nombre el nombre de la circunscripcion.
     * @return  la Circunscripcion con el mismo nombre e id que el especificado.
     */
    public Circunscripcion selectCircunscripcion(int idEleccion, String nombre);

    /**
     * Elimina la circunscripcion especificada de la base de datos.
     * 
     * @param   idEleccion el id de la eleccion.
     * @param   nombre el nombre de la circunscripcion.
     * @return  true si se eliminaron los datos correctamente de la base de
     *          datos, false en caso constrario
     */
    public boolean deleteCircunscripcion(int idEleccion, String nombre);
    
}
