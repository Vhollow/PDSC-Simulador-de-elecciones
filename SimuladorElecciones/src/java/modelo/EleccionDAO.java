package modelo;

import java.util.List;
import utils.Eleccion;

/**
 * Interface EleccionDAO, es la interfaz empleada para acceder a la Base de
 * Datos de la Aplicación para introducir u obtener o borrar datos de Elecciones
 * 
 * @author daniel
 */
public interface EleccionDAO {

    /**
     * Introduce la eleccion especificado en la base de datos.
     * 
     * @param   eleccion los datos de la eleccion que queremos introducir en la
     *          base de datos.
     * @return  el id genererado para la nueva elección, o -1 en caso de error.
     */
    public int insertEleccion(Eleccion eleccion);
    
    /**
     * Devuelve la eleccion con el mismo id que el especificado.
     * 
     * @param   id el id de la eleccion.
     * @return  la Eleccion con el mismo id que el especificado.
     */
    public Eleccion selectEleccion(int id);
    
    /**
     * Devuelve las elecciones compartidas o creadas por el Usuario con el mismo
     * id que el especificado
     * 
     * @param   idUsuario el id del Usuario.
     * @return  las Elecciones compartidas o creadas por el Usuario.
     */
    public List<Eleccion> selectElecciones(int idUsuario);
    
}
