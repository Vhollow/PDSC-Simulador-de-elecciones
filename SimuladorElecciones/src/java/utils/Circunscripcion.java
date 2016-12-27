package utils;

/**
 *
 * @author daniel
 */
public class Circunscripcion {
    
    private String nombre;
    private int numeroRepresentantes;
    private int votoNulo;
    private int votoEnBlanco;
    private int abstencion;
    private int minimoRepresentacion;
    
    
    /**
     * Crea una nueva Circunscripcion
     * 
     * @param nombre el nombre de la circunscripcion
     */
    public Circunscripcion(String nombre) {
        this.nombre = nombre;
    }
    

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the numeroRepresentantes
     */
    public int getNumeroRepresentantes() {
        return numeroRepresentantes;
    }

    /**
     * @param numeroRepresentantes the numero_representantes to set
     */
    public void setNumeroRepresentantes(int numeroRepresentantes) {
        this.numeroRepresentantes = numeroRepresentantes;
    }

    /**
     * @return the votoNulo
     */
    public int getVotoNulo() {
        return votoNulo;
    }

    /**
     * @param votoNulo the votoNulo to set
     */
    public void setVotoNulo(int votoNulo) {
        this.votoNulo = votoNulo;
    }

    /**
     * @return the votoEnBlanco
     */
    public int getVotoEnBlanco() {
        return votoEnBlanco;
    }

    /**
     * @param votoEnBlanco the votoEnBlanco to set
     */
    public void setVotoEnBlanco(int votoEnBlanco) {
        this.votoEnBlanco = votoEnBlanco;
    }

    /**
     * @return the abstencion
     */
    public int getAbstencion() {
        return abstencion;
    }

    /**
     * @param abstencion the abstencion to set
     */
    public void setAbstencion(int abstencion) {
        this.abstencion = abstencion;
    }

    /**
     * @return the minimoRepresentacion
     */
    public int getMinimoRepresentacion() {
        return minimoRepresentacion;
    }

    /**
     * @param minimoRepresentacion the minimoRepresentacion to set
     */
    public void setMinimoRepresentacion(int minimoRepresentacion) {
            this.minimoRepresentacion = minimoRepresentacion;
    }
    
}
