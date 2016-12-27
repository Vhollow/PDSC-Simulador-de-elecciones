package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import utils.Usuario;

/**
 * Clase UsuarioDAO, es la clase empleada para acceder a la Base de Datos
 * de la Aplicación para introducir u obtener datos de Usuarios.
 * Implementa la interfaz UsuarioDAO
 * 
 * @author daniel
 */
public class UsuarioDAOImpl implements UsuarioDAO {
    
    /**
     * Crea un nuevo UsuarioDAOImpl
     */
    public UsuarioDAOImpl() {}
    
    /**
     * @inheritDoc
     */
    @Override
    public int insertUsuario(Usuario usuario) {
        
        ConexionPool pool = ConexionPool.getInstancia();
        Connection conexion = pool.getConnection();
        
        String sentenciaString = "INSERT INTO Usuario "
                + "(nombre, correo_electronico, clave) "
                + "VALUES (?, ?, ?)";
        
        int idNuevoUsuario = -1;
        
        try {
            PreparedStatement sentencia = conexion.prepareStatement(
                sentenciaString,
                Statement.RETURN_GENERATED_KEYS
            );
            sentencia.setString(1, usuario.getNombre());
            sentencia.setString(2, usuario.getCorreoElectronico());
            sentencia.setString(3, usuario.getClave());
            
            // El id se genera automaticamente
            if (sentencia.executeUpdate() != 0)
            {
                ResultSet claves = sentencia.getGeneratedKeys();
                if (claves.next()) {
                    idNuevoUsuario = claves.getInt(1);
                }
            }
            
            sentencia.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }        
        
        pool.freeConnection(conexion);
        
        return idNuevoUsuario;
    }
    
    /**
     * @inheritDoc
     */
    @Override
    public Usuario selectUsuario(int id) {
        
        ConexionPool pool = ConexionPool.getInstancia();
        Connection conexion = pool.getConnection();
        
        String consultaString = "SELECT * "
                + "FROM Usuario "
                + "WHERE id=?";
        
        Usuario usuario = null;
        
        try {            
            PreparedStatement consulta = conexion.prepareStatement(consultaString);
            consulta.setInt(1, id);
            
            ResultSet resultado = consulta.executeQuery();
            
            if( resultado.next() ) {
                usuario = new Usuario(
                        resultado.getInt("id"),
                        resultado.getString("nombre"),
                        resultado.getString("correo_electronico")
                );
            }
            
            resultado.close();
            consulta.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        pool.freeConnection(conexion);
        
        return usuario;
    }
    
}
