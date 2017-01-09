package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.Circunscripcion;

/**
 * Clase CircunscripcionDAO, es la clase empleada para acceder a la Base de
 * Datos de la Aplicación para introducir, obtener o borrar datos de
 * Circunscripcion.
 * Implementa la interfaz CircunscripcionDAO
 * 
 * @author daniel
 */
public class CircunscripcionDAOImpl implements CircunscripcionDAO {
    
    /**
     * Crea un nuevo CircunscripcionDAOImpl
     */
    public CircunscripcionDAOImpl() {}
    
    /**
     * @inheritDoc
     */
    @Override
    public boolean insertCircunscripcion(int idEleccion, Circunscripcion circunscripcion) {
        
        ConexionPool pool = ConexionPool.getInstancia();
        Connection conexion = pool.getConnection();
        
        String sentenciaString = "INSERT INTO Circunscripcion "
                + "(id_eleccion, nombre, numero_representantes, voto_nulo, "
                + "voto_en_blanco, abstencion, minimo_representacion) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        boolean ret = false;
        
        try {
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaString);
            sentencia.setInt(1, idEleccion);
            sentencia.setString(2, circunscripcion.getNombre());
            sentencia.setInt(3, circunscripcion.getNumeroRepresentantes());
            sentencia.setInt(4, circunscripcion.getVotoNulo());
            sentencia.setInt(5, circunscripcion.getVotoEnBlanco());
            sentencia.setInt(6, circunscripcion.getAbstencion());
            sentencia.setInt(7, circunscripcion.getMinimoRepresentacion());
            
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
    public List<Circunscripcion> selectCircunscripciones(int idEleccion) {
        
        ConexionPool pool = ConexionPool.getInstancia();
        Connection conexion = pool.getConnection();
        
        String consultaString = "SELECT * "
                + "FROM Circunscripcion "
                + "WHERE id_eleccion=?";
        
        List<Circunscripcion> circunscripciones = new ArrayList<Circunscripcion>();
        
        try {            
            PreparedStatement consulta = conexion.prepareStatement(consultaString);
            consulta.setInt(1, idEleccion);
            
            ResultSet resultado = consulta.executeQuery();
            
            while( resultado.next() ) {
                Circunscripcion c = new Circunscripcion(resultado.getString("nombre"));
                c.setNumeroRepresentantes(resultado.getInt("numero_representantes"));
                c.setVotoNulo(resultado.getInt("voto_nulo"));
                c.setVotoEnBlanco(resultado.getInt("voto_en_blanco"));
                c.setAbstencion(resultado.getInt("abstencion"));
                c.setMinimoRepresentacion(resultado.getInt("minimo_representacion"));
                
                circunscripciones.add(c);
            }
            
            resultado.close();
            consulta.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        pool.freeConnection(conexion);
        
        return circunscripciones;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean deleteCircunscripcion(int idEleccion, String nombre) {
        
        ConexionPool pool = ConexionPool.getInstancia();
        Connection conexion = pool.getConnection();
        
        String sentenciaString = "DELETE "
                + "FROM Circunscripcion "
                + "WHERE id_eleccion=? AND nombre=?";
        
        boolean ret = false;
        
        try {
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaString);
            sentencia.setInt(1, idEleccion);
            sentencia.setString(2, nombre);
            
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
