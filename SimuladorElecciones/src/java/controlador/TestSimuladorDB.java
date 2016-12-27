/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.SimuladorDB;
import utils.Eleccion;
import utils.TipoEleccion;
import utils.Usuario;

/**
 * Servlet empleado para probar la conexion con la base de datos
 * 
 * @author daniel
 */
@WebServlet(name = "TestServlet", urlPatterns = {"/TestServlet"})
public class TestSimuladorDB extends HttpServlet {

    /** 
     * @return true si la base de datos de la aplicacion funciona correctamente
     */
    private boolean superaUnitTest() {
        // Insert de Usuario
        Usuario usuario1 = new Usuario("Usuario1", "usuario1@correo.com", "111");
        int idUsuario1 = SimuladorDB.insertUsuario(usuario1);
        if(idUsuario1 < 0) { return false; }
        Usuario usuario2 = new Usuario("Usuario2", "usuario2@correo.com", "111");
        int idUsuario2 = SimuladorDB.insertUsuario(usuario2);
        if(idUsuario2 < 0) { return false; }

        // Select de Usuario
        Usuario usuarioSelected = SimuladorDB.selectUsuario(idUsuario1);
        if((usuarioSelected.getId() != idUsuario1)
            || !usuarioSelected.getNombre().equals(usuario1.getNombre())
            || !usuarioSelected.getCorreoElectronico().equals(usuario1.getCorreoElectronico())
        ) { return false; }

        // Insert de Eleccion
        Eleccion eleccion1 = new Eleccion(new Date(), TipoEleccion.Autonomicas);
        int idEleccion1 = SimuladorDB.insertEleccion(eleccion1);
        if(idEleccion1 < 0) { return false; }
        Eleccion eleccion2 = new Eleccion(new Date(), TipoEleccion.CongresoDiputados);
        int idEleccion2 = SimuladorDB.insertEleccion(eleccion2);
        if(idEleccion2 < 0) { return false; }

        // Select de Eleccion
        Eleccion eleccionSelected = SimuladorDB.selectEleccion(idEleccion1);
        if((eleccionSelected.getId() != idEleccion1)
            || (eleccionSelected.getFecha().compareTo(eleccion1.getFecha()) == 0)
            || (eleccionSelected.getTipoEleccion() != eleccion1.getTipoEleccion())
        ) { return false; }

        // Insert de UsuarioEleccionMap
        if(!SimuladorDB.insertUsuarioEleccion(idUsuario1, idEleccion1, true)) { return false; }
        if(!SimuladorDB.insertUsuarioEleccion(idUsuario1, idEleccion2, true)) { return false; }
        if(!SimuladorDB.insertUsuarioEleccion(idUsuario2, idEleccion1, true)) { return false; }

        // Select Elecciones de Usuario
        if(SimuladorDB.selectElecciones(idUsuario1).size() != 2) { return false; }
        if(SimuladorDB.selectElecciones(idUsuario2).size() != 1) { return false; }

        // Remove de UsuarioEleccionMap
        if(!SimuladorDB.removeUsuarioEleccion(idUsuario1, idEleccion1)) { return false; }
        if(!SimuladorDB.removeUsuarioEleccion(idUsuario1, idEleccion2)) { return false; }
        if(SimuladorDB.selectEleccion(idEleccion1) == null) { return false; }
        if(SimuladorDB.selectEleccion(idEleccion2) != null) { return false; }

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
