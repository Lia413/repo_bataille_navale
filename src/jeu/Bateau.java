/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jeu;

/**
 *
 * @author lisa
 */



public abstract class Bateau {
    int taille;
    String nom;
    private int touchesRecues;

    
    public Bateau(int taille,String nom) {
        this.taille=taille;
        this.nom=nom;
        this.touchesRecues = 0;
    }
    
    public int getTaille() {
        return taille;
    }
    
    public String getnom(){
        return nom;
    }
    
    public void toucher() {
        touchesRecues++;
    }
    
    public boolean estCoule() {
        return touchesRecues >= taille;
    }
    
    public int getTouchesRecues() {
        return touchesRecues;
    }
    
    public String orientation(){
        return "";}
    
    
}
