package modelo;

import utils.Usuario;

/**
 * Interface UsuarioDAO, es la interfaz empleada para acceder a la Base de Datos
 * de la Aplicación para introducir u obtener datos de Usuarios.
 * 
 * @author daniel
 */
public interface UsuarioDAO {

    /**
     * Introduce el usuario especificado en la base de datos.
     * 
     * @param   usuario los datos del usuario que queremos introducir en la
     *          base de datos.
     * @return  el id genererado para el nuevo usuario, o -1 en caso de error.
     */
    public int insertUsuario(Usuario usuario);
    
    /**
     * Devuelve el usuario con el mismo id que el especificado.
     * 
     * @param   id el id del usuario.
     * @return  el Usuario con el mismo id que el especificado.
     */
    public Usuario selectUsuario(int id);
    
}
