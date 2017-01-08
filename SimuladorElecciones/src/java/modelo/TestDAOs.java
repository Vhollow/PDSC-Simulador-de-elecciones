package modelo;

import java.util.Date;
import java.util.List;
import utils.Candidatura;
import utils.Circunscripcion;
import utils.Eleccion;
import utils.TipoEleccion;
import utils.Usuario;

/**
 * Clase TestDAOs, contiene unicamente un metodo para poder probar la conexion
 * con la base de datos
 * 
 * @author daniel
 */
public class TestDAOs {
    
    /** 
     * @return  true si los DAOs del modelo empleados para introducir datos en
     *          la base de datos de la aplicacion funcionan correctamente
     */
    public static boolean superaTest() {
        
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        EleccionDAO eleccionDAO = new EleccionDAOImpl();
        UsuarioEleccionDAO usuarioEleccionDAO = new UsuarioEleccionDAOImpl();
        CircunscripcionDAO circunscripcionDAO = new CircunscripcionDAOImpl();
        CandidaturaDAO candidaturaDAO = new CandidaturaDAOImpl();
        VotoDAO votoDAO = new VotoDAOImpl();
        
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

        // Exists UsuarioEleccionMap
        if(!usuarioEleccionDAO.existsUsuarioEleccion(idUsuario1, idEleccion1)) { return false; }
        if(!usuarioEleccionDAO.existsUsuarioEleccion(idUsuario1, idEleccion2)) { return false; }
        if(!usuarioEleccionDAO.existsUsuarioEleccion(idUsuario2, idEleccion1)) { return false; }
        
        // Select Elecciones de Usuario
        if(eleccionDAO.selectElecciones(idUsuario1).size() != 2) { return false; }
        if(eleccionDAO.selectElecciones(idUsuario2).size() != 1) { return false; }

        // Delete de UsuarioEleccionMap
        if(!usuarioEleccionDAO.deleteUsuarioEleccion(idUsuario1, idEleccion1)) { return false; }
        if(!usuarioEleccionDAO.deleteUsuarioEleccion(idUsuario1, idEleccion2)) { return false; }
        if(eleccionDAO.selectEleccion(idEleccion1) == null) { return false; }
        if(eleccionDAO.selectEleccion(idEleccion2) != null) { return false; }

        // Insert Circunscripcion
        Circunscripcion circunscripcion1 = new Circunscripcion("Valladolid");
        if(!circunscripcionDAO.insertCircunscripcion(idEleccion1, circunscripcion1)) { return false; }
        
        // Select Circunscripcion
        List<Circunscripcion> circunscripcionesSelected = circunscripcionDAO.selectCircunscripciones(idEleccion1);
        if(!circunscripcionesSelected.get(0).getNombre().equals(circunscripcion1.getNombre())) { return false; }
        
        // Delete Circunscripcion
        if(!circunscripcionDAO.deleteCircunscripcion(idEleccion1, circunscripcion1.getNombre())) { return false; }
        
        // Insert Candidatura
        Candidatura candidatura1 = new Candidatura( "PI", "Partido Inventado", 16777215);
        Candidatura candidatura2 = new Candidatura( "PI2", "Partido Inventado2", 16777218);
        if(!candidaturaDAO.insertCandidatura(idEleccion1, candidatura1)) { return false; }
        if(!candidaturaDAO.insertCandidatura(idEleccion1, candidatura2)) { return false; }
        
        // Select Candidatura
        List<Candidatura> candidaturasSelected = candidaturaDAO.selectCandidaturas(idEleccion1);
        if( (!candidaturasSelected.get(0).getNombreCorto().equals(candidatura1.getNombreCorto()))
            || (!candidaturasSelected.get(0).getNombreLargo().equals(candidatura1.getNombreLargo()))
            || (candidaturasSelected.get(0).getColor() != candidatura1.getColor()) ) { return false; }
        
        // Delete Candidatura
        if(!candidaturaDAO.deleteCandidatura(idEleccion1, candidatura1.getNombreCorto())) { return false; }
        
        // Insert Voto
        int conteo1 = 50;
        int conteo2 = 55;
        if(!candidaturaDAO.insertCandidatura(idEleccion1, candidatura1)) { return false; }
        if(!circunscripcionDAO.insertCircunscripcion(idEleccion1, circunscripcion1)) { return false; }
        if(!votoDAO.insertVoto(idEleccion1, candidatura1.getNombreCorto(), circunscripcion1.getNombre(), conteo1)) { return false; }
        if(!votoDAO.insertVoto(idEleccion1, candidatura2.getNombreCorto(), circunscripcion1.getNombre(), conteo2)) { return false; }
        
        // Select Voto
        if(votoDAO.selectVoto(idEleccion1, candidatura1.getNombreCorto(), circunscripcion1.getNombre()) != conteo1) { return false; }
        
        // Delete Voto
        if(!votoDAO.deleteVoto(idEleccion1, candidatura1.getNombreCorto(), circunscripcion1.getNombre())) { return false; }
        if(!candidaturaDAO.deleteCandidatura(idEleccion1, candidatura2.getNombreCorto())) { return false; }
        
        
        // Delete UsuarioEleccion restante y prueba de que se elimina el
        // contenido relacionado a la eleccion
        if(!usuarioEleccionDAO.deleteUsuarioEleccion(idUsuario2, idEleccion1)) { return false; }
        if(!candidaturaDAO.selectCandidaturas(idEleccion1).isEmpty()) { return false; }
        
        // Delete Usuario
        if(!usuarioDAO.deleteUsuario(idUsuario1)) { return false; }
        if(!usuarioDAO.deleteUsuario(idUsuario2)) { return false; }
        
        return true;
    }
    
}
