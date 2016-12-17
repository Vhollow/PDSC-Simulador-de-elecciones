package simulador;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import utils.Usuario;

/**
 *
 * @author daniel
 */
public class SimuladorDB {
    
    /**
     * Introduce el usuario especificado en la base de datos.
     * 
     * @param   usuario los datos del usuario que queremos introducir en la
     *          base de datos.
     * @return  el id genererado para el nuevo usuario, o 0 en caso de error.
     */
    public static int insertUsuario(Usuario usuario) {
        
        ConexionPool pool = ConexionPool.getInstancia();
        Connection conexion = pool.getConexion();
        
        String consultaString = "INSERT INTO Usuario "
                + "(nombre, correo_electronico, clave) "
                + "VALUES (?, ?, ?)";
        
        try {
            PreparedStatement statement = conexion.prepareStatement(
                consultaString,
                Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getCorreoElectronico());
            statement.setString(3, usuario.getClave());
            
            // El id se genera automaticamente
            int idNuevoUsuario;
            if ((idNuevoUsuario = statement.executeUpdate()) != 0)
            {
                ResultSet claves = statement.getGeneratedKeys();
                if (claves.next()) {
                    idNuevoUsuario = claves.getInt(1);
                }
            }
            
            statement.close();
            pool.freeConexion(conexion);
            
            return idNuevoUsuario;
            
        } catch(SQLException e) {
            e.printStackTrace();
            return 0;
        }
        
    }

}
