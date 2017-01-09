package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.Candidatura;

/**
 * Clase CandidaturaDAO, es la clase empleada para acceder a la Base de Datos
 * de la Aplicación para introducir, obtener o borrar datos de Candidaturas.
 * Implementa la interfaz CandidaturaDAO
 * 
 * @author daniel
 */
public class CandidaturaDAOImpl implements CandidaturaDAO{
    
    /**
     * Crea un nuevo CandidaturaDAOImpl
     */
    public CandidaturaDAOImpl() {}
    
    /**
     * @inheritDoc
     */
    @Override
    public boolean insertCandidatura(int idEleccion, Candidatura candidatura) {
        
        ConexionPool pool = ConexionPool.getInstancia();
        Connection conexion = pool.getConnection();
        
        String sentenciaString = "INSERT INTO Candidatura "
                + "(id_eleccion, nombre_corto, nombre_largo, color) "
                + "VALUES (?, ?, ?, ?)";
        
        boolean ret = false;
        
        try {
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaString);
            sentencia.setInt(1, idEleccion);
            sentencia.setString(2, candidatura.getNombreCorto());
            sentencia.setString(3, candidatura.getNombreLargo());
            sentencia.setInt(4, candidatura.getColor());
            
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
    public List<Candidatura> selectCandidaturas(int idEleccion) {
        
        ConexionPool pool = ConexionPool.getInstancia();
        Connection conexion = pool.getConnection();
        
        String consultaString = "SELECT * "
                + "FROM Candidatura "
                + "WHERE id_eleccion=?";
        
        List<Candidatura> candidaturas = new ArrayList<Candidatura>();
        
        try {            
            PreparedStatement consulta = conexion.prepareStatement(consultaString);
            consulta.setInt(1, idEleccion);
            
            ResultSet resultado = consulta.executeQuery();
            
            while( resultado.next() ) {
                candidaturas.add(
                    new Candidatura(
                        resultado.getString("nombre_corto"),
                        resultado.getString("nombre_largo"),
                        resultado.getInt("color")
                    )
                );
            }
            
            resultado.close();
            consulta.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        pool.freeConnection(conexion);
        
        return candidaturas;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean deleteCandidatura(int idEleccion, String nombreCorto) {
        
        ConexionPool pool = ConexionPool.getInstancia();
        Connection conexion = pool.getConnection();
        
        String sentenciaString = "DELETE "
                + "FROM Candidatura "
                + "WHERE id_eleccion=? AND nombre_corto=?";
        
        boolean ret = false;
        
        try {
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaString);
            sentencia.setInt(1, idEleccion);
            sentencia.setString(2, nombreCorto);
            
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
