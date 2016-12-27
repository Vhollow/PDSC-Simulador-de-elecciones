/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Faztor
 */
public class Candidatura {
    
    private String nombreCorto;
    private String nombreLargo;
    private int color;
    
    /**
     * Crea a nueva Candidatura
     * 
     * @param nombreCorto el nombre corto de la candidatura
     * @param nombreLargo el nombre largo de la candidatura
     * @param color el color de la candidatura
     */
    public Candidatura(String nombreCorto, String nombreLargo, int color) {
        this.nombreCorto = nombreCorto;
        this.nombreLargo = nombreLargo;
        this.color = color;
    }

    /**
     * @return the nombreCorto
     */
    public String getNombreCorto() {
        return nombreCorto;
    }

    /**
     * @return the nombreLargo
     */
    public String getNombreLargo() {
        return nombreLargo;
    }

    /**
     * @return the color
     */
    public int getColor() {
        return color;
    }
}
