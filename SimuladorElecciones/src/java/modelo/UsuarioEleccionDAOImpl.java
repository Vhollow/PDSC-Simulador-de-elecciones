package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase UsuarioEleccionDAO, es la clase empleada para acceder a la Base de
 * Datos de la Aplicación para introducir o borrar pares UsuarioEleccionMap.
 * Implementa la interfaz UsuarioEleccionDAO
 * 
 * @author daniel
 */
public class UsuarioEleccionDAOImpl implements UsuarioEleccionDAO {
    
    /**
     * Crea un nuevo UsuarioEleccionDAOImpl
     */
    public UsuarioEleccionDAOImpl() {}
    
    /**
     * @inheritDoc
     */
    @Override
    public boolean insertUsuarioEleccion(
            int idUsuario, int idEleccion, boolean nuevo
    ) {        
        ConexionPool pool = ConexionPool.getInstancia();
        Connection conexion = pool.getConnection();
        
        String sentenciaString = "INSERT INTO Usuario_Eleccion_Map "
                + "(id_usuario, id_eleccion, nuevo) "
                + "VALUES (?, ?, ?)";
        
        boolean ret = false;
        
        try {
            PreparedStatement sentencia = conexion.prepareStatement(
                sentenciaString
            );
            sentencia.setInt(1, idUsuario);
            sentencia.setInt(2, idEleccion);
            sentencia.setBoolean(3, nuevo);
            
            if (sentencia.executeUpdate() != 0)
            {
                ret = true;
            }
            
            sentencia.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }        
        
        pool.freeConnection(conexion);
        
        return ret;
    }
    
    /**
     * @inheritDoc
     */
    @Override
    public boolean deleteUsuarioEleccion(int idUsuario, int idEleccion) {
        
        ConexionPool pool = ConexionPool.getInstancia();
        Connection conexion = pool.getConnection();
        
        String sentenciaString = "DELETE "
                + "FROM Usuario_Eleccion_Map "
                + "WHERE id_usuario=? AND id_eleccion=?";
        
        boolean ret = false;
        
        try {            
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaString);
            sentencia.setInt(1, idUsuario);
            sentencia.setInt(2, idEleccion);
            
            if (sentencia.executeUpdate() != 0)
            {
                ret = true;
            }
            
            sentencia.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        pool.freeConnection(conexion);
        
        return ret;
    }
    
}
