package modelo;

/**
 * Interface VotoDAO, es la interfaz empleada para acceder a la Base de Datos
 * de la Aplicación para introducir u obtener datos de Votos.
 * 
 * @author daniel
 */
public interface VotoDAO {

    /**
     * Introduce el numero de votos dado como el número de votos que obtuvo la
     * candidatura en la circunscripcion y elecciones especificadas
     * 
     * @param   idEleccion el id de la Eleccion al que pertenece el Voto
     * @param   nombreCandidatura el nombre de la candidatura
     * @param   nombreCircunscripcion el nombre de la circunscripcion
     * @param   conteoVotos el número de votos de la candidatura
     * @return  true si se introdujeron los datos correctamente en la base de
     *          datos, false en caso constrario
     */
    public boolean insertVoto(int idEleccion, String nombreCandidatura,
            String nombreCircunscripcion, int conteoVotos);
    
    /**
     * Devuelve numero de votos a la candidatura con el mismo nombre que el dado
     * en la circunscripcion y elecciones especificadas.
     * 
     * @param   idEleccion el id de la eleccion
     * @param   nombreCandidatura el nombre de la candidatura
     * @param   nombreCircunscripcion el nombre de la circunscripcion
     * @return  el número de votos, -1 en caso de error
     */
    public int selectVoto(int idEleccion, String nombreCandidatura,
            String nombreCircunscripcion);
    
    /**
     * Elimina el voto especificado de la base de datos.
     * 
     * @param   idEleccion el id de la eleccion
     * @param   nombreCandidatura el nombre de la candidatura
     * @param   nombreCircunscripcion el nombre de la circunscripcion
     * @return  true si se eliminaron los datos correctamente de la base de
     *          datos, false en caso constrario
     */
    public boolean deleteVoto(int idEleccion, String nombreCandidatura,
            String nombreCircunscripcion);
    
}
