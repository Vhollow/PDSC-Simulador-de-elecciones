package controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.util.List;
import utils.Usuario;
import utils.Eleccion;
import modelo.EleccionDAO;
import modelo.EleccionDAOImpl;

/**
 * Clase UsuarioServlet, es el servlet encargado de recibir las peticiones de
 * datos de la página de usuario
 *
 * @author daniel
 */
@WebServlet(name = "UsuarioServlet", urlPatterns = {"/UsuarioServlet"})
public class UsuarioServlet extends HttpServlet {

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

        // 1. Obtenemos el usuario del parametro id de la URL
        HttpSession session = request.getSession();
        Usuario usuarioActual = (Usuario)session.getAttribute("usuarioActual");
        
        if (usuarioActual != null) {
            // 2. Obtenemos las Elecciones creadas o compartidas con el usuario
            EleccionDAO eleccionDAO = new EleccionDAOImpl();
            List<Eleccion> eleccionesUsuario = eleccionDAO.selectElecciones(usuarioActual.getId());
                        
            // 3. Pasamos los datos obtenidos de la base de datos a la página
            // del usuario de la vista
            request.setAttribute("usuarioActual", usuarioActual);
            request.setAttribute("eleccionesUsuario", eleccionesUsuario);
            
            String url = "/usuario/usuario.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
            dispatcher.forward(request, response);
        } else {
            String url = "/index";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
            dispatcher.forward(request, response);
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
