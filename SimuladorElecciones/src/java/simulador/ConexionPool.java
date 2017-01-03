package simulador;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.naming.InitialContext;

/**
 * Clase ConexionPool, es la clase encargada de llevar a cabo las conexiones
 * con la base de datos. Esta clase sigue el patron Singleton por lo que solo
 * puede haber una unica instancia.
 * 
 * @author daniel
 */
public class ConexionPool {
    
    /** URL y puerto de la base de datos del servidor */
    private static final String URL = "java:/comp/env/jdbc/SimuladorDB";
    
    /** La única instancia que puede haber de la clase */
    private static ConexionPool pool = null;
    
    /** Objeto gestor de la base de datos */
    private DataSource dataSource = null;
    
    
    /**
     * Crea un nuevo ConexionPool, es privado para garantizar que solo se crea
     * la instancia mediante el metodo getInstancia
     */
    private ConexionPool() {
        try {
            InitialContext contextoInicial = new InitialContext();
            dataSource = (DataSource) contextoInicial.lookup(URL);
        } catch(Exception e) {
            throw new IllegalStateException("Base de datos no encontrada", e);
        }
    }
    
    /** 
     * @return la unica instancia posible de la Clase ConexionPool, en caso de
     * que no exista tambien se encarga de crearla
     */
    public static ConexionPool getInstancia() {
        if (pool == null) {
            pool = new ConexionPool();
        }
        return pool;
    }
    
    /**
     * @return una conexion con la base de datos
     */
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }
    }
    
    /**
     * Libera la conexión dada
     * 
     * @param c la conexion que queremos liberar
     */
    public void freeConnection(Connection c) {
        try {
            c.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
    
}
