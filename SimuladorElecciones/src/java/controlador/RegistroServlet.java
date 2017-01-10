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
        String url = "/Registro/registro.jsp";
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
        String nombre = (String)request.getAttribute("nombre");
        String correo = (String)request.getAttribute("correo");
        String clave = (String)request.getAttribute("clave");
        Usuario newUser = new Usuario(nombre,correo,clave);
        
        /* Insertamos el nuevo usuario en la base de datos */
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
    
        try{
            
            usuarioDAO.insertUsuario(newUser);
            
        }catch(Exception e){
            String url = "/registro/registro.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
            dispatcher.forward(request, response);
        }
        
        HttpSession session = request.getSession();
        session.setAttribute("usuarioActual", newUser);
            
        // 3. Vamos a la pagina de usuario
        String url = "/usuario";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
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
