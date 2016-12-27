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
import utils.Usuario;

/**
 * Clase SimuladorDB, es la clase empleada para acceder (introducir y obtener
 * datos) a la Base de Datos de la Aplicación
 * 
 * @author daniel
 */
public class SimuladorDB {
    
    /**
     * Transforma la fecha especificada al tipo java.sql.Date
     * 
     * @param   date la fecha de tipo java.util.Date que queremos transformar
     * @return  la fecha transformada a java.sql.Date
     */
    private static java.sql.Date utilDateToSQLDate(java.util.Date date) {
        return new java.sql.Date( date.getTime() );
    }
    
    /**
     * Transforma la fecha especificada al tipo java.sql.Date
     * 
     * @param   date la fecha de tipo java.util.Date que queremos transformar
     * @return  la fecha transformada a java.sql.Date
     */
    private static TipoEleccion idToTipoEleccion(int id) {
        TipoEleccion ret = TipoEleccion.Otro;
        
        if (id == TipoEleccion.CongresoDiputados.getValor()) {
            ret = TipoEleccion.CongresoDiputados;
        } else if (id == TipoEleccion.Autonomicas.getValor()) {
            ret = TipoEleccion.Autonomicas;
        } else if (id == TipoEleccion.Municipales.getValor()) {
            ret = TipoEleccion.Municipales;
        } else if (id == TipoEleccion.ParlamentoEuropeo.getValor()) {
            ret = TipoEleccion.ParlamentoEuropeo;
        }
        
        return ret;
    }    
    
    
// USUARIO
    /**
     * Introduce el usuario especificado en la base de datos.
     * 
     * @param   usuario los datos del usuario que queremos introducir en la
     *          base de datos.
     * @return  el id genererado para el nuevo usuario, o -1 en caso de error.
     */
    public static int insertUsuario(Usuario usuario) {
        
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
     * Devuelve el usuario con el mismo id que el especificado.
     * 
     * @param   id el id del usuario.
     * @return  el Usuario con el mismo id que el especificado.
     */
    public static Usuario selectUsuario(int id) {
        
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
    
// ELECCION
    /**
     * Introduce la eleccion especificado en la base de datos.
     * 
     * @param   eleccion los datos de la eleccion que queremos introducir en la
     *          base de datos.
     * @return  el id genererado para la nueva elección, o -1 en caso de error.
     */
    public static int insertEleccion(Eleccion eleccion) {
        
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
     * Devuelve la eleccion con el mismo id que el especificado.
     * 
     * @param   id el id de la eleccion.
     * @return  la Eleccion con el mismo id que el especificado.
     */
    public static Eleccion selectEleccion(int id) {
        
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
                        idToTipoEleccion(resultado.getInt("tipo_eleccion"))
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
     * Devuelve las elecciones compartidas o creadas por el Usuario con el mismo
     * id que el especificado
     * 
     * @param   idUsuario el id del Usuario.
     * @return  las Elecciones compartidas o creadas por el Usuario.
     */
    public static List<Eleccion> selectElecciones(int idUsuario) {
        
        ConexionPool pool = ConexionPool.getInstancia();
        Connection conexion = pool.getConnection();
        
        String consultaString = "SELECT * "
                + "FROM Eleccion E, UsuarioEleccionMap M "
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
                        idToTipoEleccion(resultado.getInt("tipo_eleccion"))
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
    
// USUARIOELECCIONMAP
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
    public static boolean insertUsuarioEleccion(
            int idUsuario, int idEleccion, boolean nuevo
    ) {        
        ConexionPool pool = ConexionPool.getInstancia();
        Connection conexion = pool.getConnection();
        
        String sentenciaString = "INSERT INTO UsuarioEleccionMap "
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
     * Elimina el par entre el Usuario y la Eleccion especificadas en 
     * la base de datos.
     * 
     * @note    En caso de que no haya más Usuarios con la eleccion con el id
     *          especficado, la Elección también será borrada
     * @param   idUsuario el id del usuario que queremos mapear.
     * @param   idEleccion el id de la eleccion que queremos mapear.
     * @return  true si el par se introdujo correctamente, false en caso
     * contrario
     */
    public static boolean removeUsuarioEleccion(int idUsuario, int idEleccion) {
        
        ConexionPool pool = ConexionPool.getInstancia();
        Connection conexion = pool.getConnection();
        
        String sentenciaString = "DELETE "
                + "FROM UsuarioEleccionMap "
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
