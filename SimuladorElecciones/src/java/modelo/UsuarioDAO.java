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
     * @param   id el id de la cuenta del Usuario
     * @return  el Usuario con el mismo id que el especificado.
     */
    public Usuario selectUsuario(int id);
    
    /**
     * Devuelve el usuario con el mismo correo electronico que el especificado.
     * 
     * @param   correoElectronico el correo electronico de la cuenta del Usuario
     * @return  el Usuario con el mismo correo que el especificado.
     */
    public Usuario selectUsuario(String correoElectronico);
    
    /**
     * Elimina el usuario especificado de la base de datos.
     * 
     * @param   idUsuario el id del usuario que queremos eliminar de la base de
     *          datos.
     * @return  true si el usuario fue eliminado correctamente, false en caso
     *          contrario.
     */
    public boolean deleteUsuario(int idUsuario);
    
}
