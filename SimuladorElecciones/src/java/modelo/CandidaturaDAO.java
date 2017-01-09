package modelo;

import java.util.List;
import utils.Candidatura;

/**
 * Interface CandidaturaDAO, es la interfaz empleada para acceder a la Base de
 * Datos de la Aplicación para introducir, obtener o borrar datos de
 * Candidaturas.
 * 
 * @author daniel
 */
public interface CandidaturaDAO {
        
    /**
     * Introduce la candidatura especificada en la base de datos.
     * 
     * @param   idEleccion el id de la Eleccion al que pertenece la
     *          Circunscripcion
     * @param   candidatura los datos de la Candidatura que queremos
     *          introducir en la base de datos.
     * @return  true si se introdujeron los datos correctamente en la base de
     *          datos, false en caso constrario
     */
    public boolean insertCandidatura(int idEleccion, Candidatura candidatura);

    /**
     * Devuelve las candidaturas de la eleccion especificada.
     * 
     * @param   idEleccion el id de la eleccion.
     * @return  una lista con las candidaturas de la eleccion especificada.
     */
    public List<Candidatura> selectCandidaturas(int idEleccion);

    /**
     * Elimina la candidatura especificada de la base de datos.
     * 
     * @param   idEleccion el id de la eleccion.
     * @param   nombreCorto el nombre de la candidatura.
     * @return  true si se eliminaron los datos correctamente de la base de
     *          datos, false en caso constrario
     */
    public boolean deleteCandidatura(int idEleccion, String nombreCorto);
    
}
