package utils;

/**
 * Clase Usuario, contiene los datos de un Usuario obtenido de la Base de Datos 
 * Es empleado como un DTO para facilitar el manejo de información.
 * 
 * @author daniel
 */
public class Usuario {
    
    private int id;
    private String nombre;
    private String correoElectronico;
    private String clave;
    
    
    /**
     * Crea un nuevo Usuario
     * 
     * @param correoElectronico el correo electrónico del Usuario
     * @param clave la clave de la cuenta del usuario
     */
    public Usuario (String correoElectronico, String clave) {
        this.correoElectronico = correoElectronico;
        this.clave = clave;
    }
    
    /**
     * Crea un nuevo Usuario
     * 
     * @param nombre el Nombre de Usuario
     * @param correoElectronico el correo eletrónico del Usuario
     * @param clave la clave de la cuenta del Usuario
     */
    public Usuario (String nombre, String correoElectronico, String clave) {
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.clave = clave;
    }
    
    /**
     * Crea un nuevo Usuario
     * 
     * @param id el ID del Usuario
     * @param nombre el Nombre de Usuario
     * @param correoElectronico el correo eletrónico del Usuario
     */
    public Usuario (int id, String nombre, String correoElectronico) {
        this.id = id;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
    }
    

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }
    
}
