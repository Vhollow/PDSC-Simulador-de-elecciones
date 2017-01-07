package controlador;

import java.util.Date;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.CandidaturaDAO;
import modelo.CandidaturaDAOImpl;
import modelo.CircunscripcionDAO;
import modelo.CircunscripcionDAOImpl;
import modelo.EleccionDAO;
import modelo.EleccionDAOImpl;
import modelo.UsuarioEleccionDAO;
import modelo.UsuarioEleccionDAOImpl;
import modelo.VotoDAO;
import modelo.VotoDAOImpl;
import utils.Candidatura;
import utils.Circunscripcion;
import utils.Eleccion;
import utils.TipoEleccion;
import utils.Usuario;

/**
 * Clase SimulacionServlet, es el servlet encargado de recibir las peticiones de
 * datos de la página de simulacion así como de almacenar simulacion en la base
 * de datos
 *
 * @author daniel
 */
@WebServlet(name = "SimulacionServlet", urlPatterns = {"/SimulacionServlet"})
public class SimulacionServlet extends HttpServlet {

    private TipoEleccion parseTipoEleccion(String nombreTipo) {
        if (nombreTipo.equals("Autonomicas")) {
            return TipoEleccion.Autonomicas;
        } else if (nombreTipo.equals("Congreso Diputados")) {
            return TipoEleccion.CongresoDiputados;
        } else if (nombreTipo.equals("Municipales")) {
            return TipoEleccion.Municipales;
        } else if (nombreTipo.equals("Parlamento Europeo")) {
            return TipoEleccion.ParlamentoEuropeo;
        } else {
            return null;
        }
    }
    
    
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
        
        String url = "/simulacion/simulacion.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
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

        // 1. Obtenemos el Usuario de la sesión actual
        HttpSession session = request.getSession();
        Usuario usuarioActual = (Usuario)session.getAttribute("usuarioActual");
        
        if (usuarioActual != null) {
            
            // Insert de la nueva Eleccion
            EleccionDAO eleccionDAO = new EleccionDAOImpl();
            Eleccion eleccion = new Eleccion(
                    new Date(),
                    parseTipoEleccion(request.getParameter("input-tipo-eleccion"))
            );
            int idEleccion = eleccionDAO.insertEleccion(eleccion);
            
            // Insert del par UsuarioEleccion
            UsuarioEleccionDAO usuarioEleccionDAO = new UsuarioEleccionDAOImpl();
            usuarioEleccionDAO.insertUsuarioEleccion(usuarioActual.getId(), idEleccion, true);

            // Insert de Candidaturas
            CandidaturaDAO candidaturaDAO = new CandidaturaDAOImpl();
            int numeroCandidaturas = Integer.parseInt(request.getParameter("hidden-numero-candidaturas"));
            for(int i = 0; i < numeroCandidaturas; i++) {
                Candidatura candidatura = new Candidatura(
                    request.getParameter("hidden-candidatura-nombre-corto" + i),
                    request.getParameter("hidden-candidatura-nombre-largo" + i),
                    Integer.parseInt(request.getParameter("hidden-candidatura-color" + i))
                );
                
                candidaturaDAO.insertCandidatura(idEleccion, candidatura);
            }

            // Insert de Circunscripciones
            CircunscripcionDAO circunscripcionDAO = new CircunscripcionDAOImpl();
            int numeroCircunscripciones = Integer.parseInt(request.getParameter("hidden-numero-circunscripciones"));
            for(int i = 0; i < numeroCircunscripciones; i++) {
                Circunscripcion circunscripcion = new Circunscripcion(
                    request.getParameter("hidden-circunscripcion-nombre" + i)
                );
                circunscripcion.setNumeroRepresentantes(
                        Integer.parseInt(request.getParameter("input-circunscripcion-numero-representantes" + i))
                );
                circunscripcion.setVotoEnBlanco(
                        Integer.parseInt(request.getParameter("input-circunscripcion-voto-en-blanco" + i))
                );
                circunscripcion.setVotoNulo(
                        Integer.parseInt(request.getParameter("input-circunscripcion-voto-nulo" + i))
                );
                
                circunscripcionDAO.insertCircunscripcion(idEleccion, circunscripcion);
            }
            
            // Insert de Votos
            VotoDAO votoDAO = new VotoDAOImpl();
            for (int i = 0; i < numeroCircunscripciones; i++) {
                String nombreCircunscripcion = request.getParameter("hidden-circunscripcion-nombre" + i);
                for (int j = 0; j < numeroCandidaturas; j++) {
                    String nombreCandidatura = request.getParameter("hidden-candidatura-nombre-corto" + j);
                    int conteoVotos = Integer.parseInt(request.getParameter("input-votos" + i + "" + j));
                    votoDAO.insertVoto(idEleccion, nombreCandidatura, nombreCircunscripcion, conteoVotos);
                }
            }
            
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
