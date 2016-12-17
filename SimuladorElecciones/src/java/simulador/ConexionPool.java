package simulador;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.naming.InitialContext;

/**
 *
 * @author daniel
 */
public class ConexionPool {
    private static ConexionPool pool = null;
    private static DataSource dataSource = null;
    
    private ConexionPool() {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/pdsc_simulador");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static ConexionPool getInstancia() {
        if (pool == null) {
            pool = new ConexionPool();
        }
        return pool;
    }
    
    public Connection getConexion() {
        try {
            return dataSource.getConnection();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }
    }
    
    public void freeConexion(Connection c) {
        try {
            c.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
    
}
