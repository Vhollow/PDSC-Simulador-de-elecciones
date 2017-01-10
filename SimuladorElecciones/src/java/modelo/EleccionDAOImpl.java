package modelo;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.Eleccion;
import utils.TipoEleccion;

/**
 * Clase EleccionDAOImpl, es la clase empleada para acceder a la Base de Datos
 * de la Aplicación para introducir u obtener o borrar datos de Elecciones.
 * Implementa la interfaz EleccionDAO
 * 
 * @author daniel
 */
public class EleccionDAOImpl implements EleccionDAO {
    
    /**
     * Crea un nuevo EleccionDAOImpl
     */
    public EleccionDAOImpl() {}
    
    /**
     * @inheritDoc
     */
    @Override
    public int insertEleccion(Eleccion eleccion) {
        
        ConexionPool pool = ConexionPool.getInstancia();
        Connection conexion = pool.getConnection();
        
        String sentenciaString = "INSERT INTO Eleccion "
                + "(fecha, tipo_eleccion) "
                + "VALUES (?, ?)";
        
        int idNuevaEleccion = -1;
        
        try {
            PreparedStatement sentencia = conexion.prepareStatement(
                sentenciaString,
                Statement.RETURN_GENERATED_KEYS
            );
            sentencia.setDate(1, utilDateToSQLDate(eleccion.getFecha()));
            sentencia.setInt(2, eleccion.getTipoEleccion().getValor());
            
            // El id se genera automaticamente
            if (sentencia.executeUpdate() != 0)
            {
                ResultSet claves = sentencia.getGeneratedKeys();
                if (claves.next()) {
                    idNuevaEleccion = claves.getInt(1);
                }
            }
            
            sentencia.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }        
        
        pool.freeConnection(conexion);
        
        return idNuevaEleccion;
    }
    
    /**
     * @inheritDoc
     */
    @Override
    public Eleccion selectEleccion(int id) {
        
        ConexionPool pool = ConexionPool.getInstancia();
        Connection conexion = pool.getConnection();
        
        String consultaString = "SELECT * "
                + "FROM Eleccion "
                + "WHERE id=?";
        
        Eleccion eleccion = null;
        
        try {            
            PreparedStatement consulta = conexion.prepareStatement(consultaString);
            consulta.setInt(1, id);
            
            ResultSet resultado = consulta.executeQuery();
            
            if( resultado.next() ) {
                eleccion = new Eleccion(
                        resultado.getInt("id"),
                        resultado.getDate("fecha"),
                        TipoEleccion.numToTipoEleccion(resultado.getInt("tipo_eleccion"))
                );
            }
            
            resultado.close();
            consulta.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        pool.freeConnection(conexion);
        
        return eleccion;
    }
    
    /**
     * @inheritDoc
     */
    @Override
    public List<Eleccion> selectElecciones(int idUsuario) {
        
        ConexionPool pool = ConexionPool.getInstancia();
        Connection conexion = pool.getConnection();
        
        String consultaString = "SELECT * "
                + "FROM Eleccion E, Usuario_Eleccion_Map M "
                + "WHERE M.id_usuario=? AND M.id_eleccion=E.id "
                + "ORDER BY E.fecha";
        
        List<Eleccion> ret = new ArrayList<Eleccion>();
        
        try {            
            PreparedStatement consulta = conexion.prepareStatement(consultaString);
            consulta.setInt(1, idUsuario);
            
            ResultSet resultado = consulta.executeQuery();
            
            while( resultado.next() ) {
                ret.add(
                    new Eleccion(
                        resultado.getInt("id"),
                        resultado.getDate("fecha"),
                        TipoEleccion.numToTipoEleccion(resultado.getInt("tipo_eleccion"))
                    )
                );
            }
            
            resultado.close();
            consulta.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        pool.freeConnection(conexion);
        
        return ret;
    }
    
// Funciones privadas
    /**
     * Transforma la fecha especificada al tipo java.sql.Date
     * 
     * @param   date la fecha de tipo java.util.Date que queremos transformar
     * @return  la fecha transformada a java.sql.Date
     */
    private java.sql.Date utilDateToSQLDate(java.util.Date date) {
        return new java.sql.Date( date.getTime() );
    }
    
}
