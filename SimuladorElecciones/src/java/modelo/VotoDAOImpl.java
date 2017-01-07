package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase VotoDAOImpl, es la clase empleada para acceder a la Base de
 * Datos de la Aplicación para introducir, obtener o borrar datos de
 * Votos.
 * Implementa la interfaz VotoDAO
 * 
 * @author daniel
 */
public class VotoDAOImpl implements VotoDAO {
    
    /**
     * Crea un nuevo VotoDAOImpl
     */
    public VotoDAOImpl() {}
    
    /**
     * @inheritDoc
     */
    @Override
    public boolean insertVoto(int idEleccion, String nombreCandidatura,
            String nombreCircunscripcion, int conteoVotos) {
        
        ConexionPool pool = ConexionPool.getInstancia();
        Connection conexion = pool.getConnection();
        
        String sentenciaString = "INSERT INTO Voto "
                + "(id_eleccion, nombre_candidatura, nombre_circunscripcion, "
                + "conteo) "
                + "VALUES (?, ?, ?, ?)";
        
        boolean ret = false;
        
        try {
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaString);
            sentencia.setInt(1, idEleccion);
            sentencia.setString(2, nombreCandidatura);
            sentencia.setString(3, nombreCircunscripcion);
            sentencia.setInt(4, conteoVotos);
            
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
    public int selectVoto(int idEleccion, String nombreCandidatura,
            String nombreCircunscripcion) {
        
        ConexionPool pool = ConexionPool.getInstancia();
        Connection conexion = pool.getConnection();
        
        String consultaString = "SELECT * "
                + "FROM Voto "
                + "WHERE id_eleccion=? "
                + "AND nombre_candidatura=? AND nombre_circunscripcion=?";
        
        int conteo = -1;
        
        try {            
            PreparedStatement consulta = conexion.prepareStatement(consultaString);
            consulta.setInt(1, idEleccion);
            consulta.setString(2, nombreCandidatura);
            consulta.setString(3, nombreCircunscripcion);
            
            ResultSet resultado = consulta.executeQuery();
            
            if( resultado.next() ) {
                conteo = resultado.getInt("conteo");
            }
            
            resultado.close();
            consulta.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        pool.freeConnection(conexion);
        
        return conteo;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean deleteVoto(int idEleccion, String nombreCandidatura,
            String nombreCircunscripcion) {
        
        ConexionPool pool = ConexionPool.getInstancia();
        Connection conexion = pool.getConnection();
        
        String sentenciaString = "DELETE "
                + "FROM Voto "
                + "WHERE id_eleccion=? "
                + "AND nombre_candidatura=? AND nombre_circunscripcion=?";
        
        boolean ret = false;
        
        try {
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaString);
            sentencia.setInt(1, idEleccion);
            sentencia.setString(2, nombreCandidatura);
            sentencia.setString(3, nombreCircunscripcion);
            
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
