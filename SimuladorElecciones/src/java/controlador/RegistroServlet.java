/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.UsuarioDAO;
import modelo.UsuarioDAOImpl;
import utils.Usuario;

/**
 *
 * @author Vicente
 */
@WebServlet(name = "RegistroServlet", urlPatterns = {"/registrate"})
public class RegistroServlet extends HttpServlet {

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
        
        // 3. Vamos a la pagina de registro
        String url = "/registro/registro.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
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
        
        /* Generamos un nuevo usuario con los datos pasados por la petición*/
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo-electronico");
        String clave = request.getParameter("clave");
        Usuario nuevoUsuario = new Usuario(nombre, correo, clave);
        
        /* Insertamos el nuevo usuario en la base de datos */
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
    
        try{
            int idUsuario = usuarioDAO.insertUsuario(nuevoUsuario);
            nuevoUsuario = usuarioDAO.selectUsuario(idUsuario);
        
            HttpSession session = request.getSession();
            session.setAttribute("usuarioActual", nuevoUsuario);

            // 3. Vamos a la pagina de usuario
            String url = "/usuario";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
            dispatcher.forward(request, response);
        } catch(Exception e) {
            String url = "/registro/registro.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
            dispatcher.forward(request, response);
        }
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
