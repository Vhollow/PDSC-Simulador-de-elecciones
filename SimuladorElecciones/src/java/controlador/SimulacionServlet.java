package controlador;

import java.util.Date;
import java.util.List;
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
        
        // 1. Obtenemos el id de la eleccion pedida
        String idEleccionString = request.getParameter("id");
        
        // 2. Obtenemos el Usuario de la sesión actual
        HttpSession session = request.getSession();
        Usuario usuarioActual = (Usuario)session.getAttribute("usuarioActual");
        
        if (idEleccionString != null && usuarioActual != null) {
            int idEleccion = Integer.parseInt(idEleccionString);
            
            // 3. Comprobamos si existe alguna relacion entre el usuario y la
            // eleccion pedida
            UsuarioEleccionDAO usuarioEleccionDAO = new UsuarioEleccionDAOImpl();
            if (usuarioEleccionDAO.existsUsuarioEleccion(usuarioActual.getId(), idEleccion)) {
                
                EleccionDAO eleccionDAO = new EleccionDAOImpl();
                CandidaturaDAO candidaturaDAO = new CandidaturaDAOImpl();
                CircunscripcionDAO circunscripcionDAO = new CircunscripcionDAOImpl();
                VotoDAO votoDAO = new VotoDAOImpl();
                
                // 4. Obtenemos los datos de la simulacion
                Eleccion eleccion = eleccionDAO.selectEleccion(idEleccion);
                List<Circunscripcion> circunscripciones = circunscripcionDAO.selectCircunscripciones(idEleccion);
                List<Candidatura> candidaturas = candidaturaDAO.selectCandidaturas(idEleccion);
                int[][] votos = new int[circunscripciones.size()][candidaturas.size()];
                for (int i = 0; i < circunscripciones.size(); i++) {
                    String nombreCircunscripcion = circunscripciones.get(i).getNombre();
                    for (int j = 0; j < candidaturas.size(); j++) {
                        int conteo = votoDAO.selectVoto(idEleccion, candidaturas.get(j).getNombreCorto(), nombreCircunscripcion);
                        votos[i][j] = (conteo >= 0)? conteo : 0;
                    }
                }
                
                // 5. Almacenamos los datos como parametros de la peticion
                request.setAttribute("eleccion", eleccion);
                request.setAttribute("circunscripciones", circunscripciones);
                request.setAttribute("candidaturas", candidaturas);
                request.setAttribute("votos", votos);
            }
        }
        
        // Pagina para simulacion
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
        
        try {
            EleccionDAO eleccionDAO = new EleccionDAOImpl();
            CandidaturaDAO candidaturaDAO = new CandidaturaDAOImpl();
            CircunscripcionDAO circunscripcionDAO = new CircunscripcionDAOImpl();
            VotoDAO votoDAO = new VotoDAOImpl();

            // Insert de la nueva Eleccion
            Date fecha = new Date();
            TipoEleccion tipoEleccion = TipoEleccion.numToTipoEleccion(
                Integer.parseInt(request.getParameter("hidden-tipo-eleccion"))
            );
            Eleccion eleccion = new Eleccion(fecha, tipoEleccion);
            int idEleccion = eleccionDAO.insertEleccion(eleccion);

            // Insert del par UsuarioEleccion
            UsuarioEleccionDAO usuarioEleccionDAO = new UsuarioEleccionDAOImpl();
            usuarioEleccionDAO.insertUsuarioEleccion(usuarioActual.getId(), idEleccion, true);

            // Insert de Candidaturas
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
            for (int i = 0; i < numeroCircunscripciones; i++) {
                String nombreCircunscripcion = request.getParameter("hidden-circunscripcion-nombre" + i);
                for (int j = 0; j < numeroCandidaturas; j++) {
                    String nombreCandidatura = request.getParameter("hidden-candidatura-nombre-corto" + j);
                    int conteoVotos = Integer.parseInt(request.getParameter("input-votos" + i + "" + j));
                    votoDAO.insertVoto(idEleccion, nombreCandidatura, nombreCircunscripcion, conteoVotos);
                }
            }

            // Pagina de usuario
            String url = "/usuario";
            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);

        } catch(Exception e) {
            // Pagina para simulacion
            response.sendRedirect("./simulacion");
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
