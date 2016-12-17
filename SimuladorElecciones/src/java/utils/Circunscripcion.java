package utils;

/**
 *
 * @author daniel
 */
public class Circunscripcion {
    
    private String nombre;
    private int numero_representantes;
    private int voto_nulo;
    private int voto_en_blanco;
    private int abstencion;
    private int minimo_representacion;
    
    
    /**
     * Crea una nueva Circunscripcion
     * 
     * @param nombre el nombre de la circunscripcion
     */
    Circunscripcion(String nombre)
    {
        this.nombre = nombre;
    }
    

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the numero_representantes
     */
    public int getNumero_representantes() {
        return numero_representantes;
    }

    /**
     * @param numero_representantes the numero_representantes to set
     */
    public void setNumero_representantes(int numero_representantes) {
        this.numero_representantes = numero_representantes;
    }

    /**
     * @return the voto_nulo
     */
    public int getVoto_nulo() {
        return voto_nulo;
    }

    /**
     * @param voto_nulo the voto_nulo to set
     */
    public void setVoto_nulo(int voto_nulo) {
        this.voto_nulo = voto_nulo;
    }

    /**
     * @return the voto_en_blanco
     */
    public int getVoto_en_blanco() {
        return voto_en_blanco;
    }

    /**
     * @param voto_en_blanco the voto_en_blanco to set
     */
    public void setVoto_en_blanco(int voto_en_blanco) {
        this.voto_en_blanco = voto_en_blanco;
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
     * @return the minimo_representacion
     */
    public int getMinimo_representacion() {
        return minimo_representacion;
    }

    /**
     * @param minimo_representacion the minimo_representacion to set
     */
    public void setMinimo_representacion(int minimo_representacion) {
        this.minimo_representacion = minimo_representacion;
    }
    
}
