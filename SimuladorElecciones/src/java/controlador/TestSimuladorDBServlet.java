package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.Candidatura;
import utils.Circunscripcion;
import utils.Eleccion;
import utils.TipoEleccion;
import utils.Usuario;
import modelo.UsuarioDAO;
import modelo.EleccionDAO;
import modelo.UsuarioEleccionDAO;
import modelo.CircunscripcionDAO;
import modelo.CandidaturaDAO;
import modelo.UsuarioDAOImpl;
import modelo.EleccionDAOImpl;
import modelo.UsuarioEleccionDAOImpl;
import modelo.CircunscripcionDAOImpl;
import modelo.CandidaturaDAOImpl;

/**
 * Clase TestSimuladorDBServlet. Es un Servlet empleado para probar la conexion
 * con la base de datos
 * 
 * @author daniel
 */
@WebServlet(name = "TestServlet", urlPatterns = {"/TestServlet"})
public class TestSimuladorDBServlet extends HttpServlet {

    /** 
     * @return true si la base de datos de la aplicacion funciona correctamente
     */
    private boolean superaUnitTest() {
        
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        EleccionDAO eleccionDAO = new EleccionDAOImpl();
        UsuarioEleccionDAO usuarioEleccionDAO = new UsuarioEleccionDAOImpl();
        CircunscripcionDAO circunscripcionDAO = new CircunscripcionDAOImpl();
        CandidaturaDAO candidaturaDAO = new CandidaturaDAOImpl();
        
        // Insert de Usuario
        Usuario usuario1 = new Usuario("Usuario1", "usuario1@correo.com", "111");
        int idUsuario1 = usuarioDAO.insertUsuario(usuario1);
        if(idUsuario1 < 0) { return false; }
        Usuario usuario2 = new Usuario("Usuario2", "usuario2@correo.com", "111");
        int idUsuario2 = usuarioDAO.insertUsuario(usuario2);
        if(idUsuario2 < 0) { return false; }

        // Select de Usuario
        Usuario usuarioSelected = usuarioDAO.selectUsuario(usuario1.getCorreoElectronico(), usuario1.getClave());
        if((usuarioSelected.getId() != idUsuario1)
            || !usuarioSelected.getNombre().equals(usuario1.getNombre())
            || !usuarioSelected.getCorreoElectronico().equals(usuario1.getCorreoElectronico())
        ) { return false; }

        // Insert de Eleccion
        Eleccion eleccion1 = new Eleccion(new Date(), TipoEleccion.Autonomicas);
        int idEleccion1 = eleccionDAO.insertEleccion(eleccion1);
        if(idEleccion1 < 0) { return false; }
        Eleccion eleccion2 = new Eleccion(new Date(), TipoEleccion.CongresoDiputados);
        int idEleccion2 = eleccionDAO.insertEleccion(eleccion2);
        if(idEleccion2 < 0) { return false; }

        // Select de Eleccion
        Eleccion eleccionSelected = eleccionDAO.selectEleccion(idEleccion1);
        if((eleccionSelected.getId() != idEleccion1)
            || (eleccionSelected.getFecha().compareTo(eleccion1.getFecha()) == 0)
            || (eleccionSelected.getTipoEleccion() != eleccion1.getTipoEleccion())
        ) { return false; }

        // Insert de UsuarioEleccionMap
        if(!usuarioEleccionDAO.insertUsuarioEleccion(idUsuario1, idEleccion1, true)) { return false; }
        if(!usuarioEleccionDAO.insertUsuarioEleccion(idUsuario1, idEleccion2, true)) { return false; }
        if(!usuarioEleccionDAO.insertUsuarioEleccion(idUsuario2, idEleccion1, true)) { return false; }

        // Select Elecciones de Usuario
        if(eleccionDAO.selectElecciones(idUsuario1).size() != 2) { return false; }
        if(eleccionDAO.selectElecciones(idUsuario2).size() != 1) { return false; }

        // Delete de UsuarioEleccionMap
        if(!usuarioEleccionDAO.deleteUsuarioEleccion(idUsuario1, idEleccion1)) { return false; }
        if(!usuarioEleccionDAO.deleteUsuarioEleccion(idUsuario1, idEleccion2)) { return false; }
        if(eleccionDAO.selectEleccion(idEleccion1) == null) { return false; }
        if(eleccionDAO.selectEleccion(idEleccion2) != null) { return false; }

        // Insert Circunscripcion
        String nombreCircunscripcion = "Valladolid";
        Circunscripcion circunscripcion1 = new Circunscripcion(nombreCircunscripcion);
        if(!circunscripcionDAO.insertCircunscripcion(idEleccion1, circunscripcion1)) { return false; };
        
        // Select Circunscripcion
        Circunscripcion circunscripcionSelected = circunscripcionDAO.selectCircunscripcion(idEleccion1, nombreCircunscripcion);
        if(!circunscripcionSelected.getNombre().equals(circunscripcion1.getNombre())) { return false; }
        
        // Delete Circunscripcion
        if(!circunscripcionDAO.deleteCircunscripcion(idEleccion1, nombreCircunscripcion)) { return false; };
        
        // Insert Candidatura
        String nombreCorto = "PI";
        Candidatura candidatura1 = new Candidatura(nombreCorto, "Partido Inventado", 16777215);
        if(!candidaturaDAO.insertCandidatura(idEleccion1, candidatura1)) { return false; };
        
        // Select Candidatura
        Candidatura candidaturaSelected = candidaturaDAO.selectCandidatura(idEleccion1, nombreCorto);
        if( (!candidaturaSelected.getNombreCorto().equals(candidatura1.getNombreCorto()))
            || (!candidaturaSelected.getNombreLargo().equals(candidatura1.getNombreLargo()))
            || (candidaturaSelected.getColor() != candidatura1.getColor()) ) { return false; }
        
        // Delete Candidatura
        if(!candidaturaDAO.deleteCandidatura(idEleccion1, nombreCorto)) { return false; };
        
        return true;
    }
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TestSimuladorDB at " + request.getContextPath() + "</h1>");
            if (superaUnitTest()) { out.println("<p>Test Superado</p>"); }
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
