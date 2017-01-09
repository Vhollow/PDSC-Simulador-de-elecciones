package modelo;

/**
 * Interface UsuarioEleccionDAO, es la interfaz empleada para acceder a la Base
 * de Datos de la Aplicación para introducir o borrar pares UsuarioEleccionMap.
 * 
 * @author daniel
 */
public interface UsuarioEleccionDAO {
        
    /**
     * Introduce un par entre el Usuario y la Eleccion especificadas en 
     * la base de datos.
     * 
     * @param   idUsuario el id del usuario que queremos mapear.
     * @param   idEleccion el id de la eleccion que queremos mapear.
     * @param   nuevo si queremos almacenar como nuevo el par o no
     * @return  true si el par se introdujo correctamente, false en caso
     *          contrario
     */
    public boolean insertUsuarioEleccion(
            int idUsuario, int idEleccion, boolean nuevo
    );
    
    /**
     * Devuelve true si existe una relacion entre el usuario y la eleccion
     * especificadas
     * 
     * @param   idUsuario el id del usuario.
     * @param   idEleccion el id de la eleccion.
     * @return  true si el par existe, false en caso contrario
     */
    public boolean existsUsuarioEleccion(int idUsuario, int idEleccion);
    
    /**
     * Elimina el par entre el Usuario y la Eleccion especificadas en 
     * la base de datos.
     * 
     * @note    En caso de que no haya más Usuarios con la eleccion con el id
     *          especficado, la Elección también será borrada
     * @param   idUsuario el id del usuario.
     * @param   idEleccion el id de la eleccion.
     * @return  true si el par se introdujo correctamente, false en caso
     * contrario
     */
    public boolean deleteUsuarioEleccion(int idUsuario, int idEleccion);
    
}
