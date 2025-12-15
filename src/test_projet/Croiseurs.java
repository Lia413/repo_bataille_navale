/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test_projet;

import java.io.IOException;


/**
 *
 * @author lisa
 */
public class Croiseurs extends Bateau {
    public Croiseurs(int taille, boolean horizontal) throws IOException {
        super(taille);
        if (horizontal){decouperImageHorizontale("/Users/lisa/Desktop/fleur.jpg");}
        else {decouperImageVerticale("/Users/lisa/Desktop/fleur.jpg");}
    }
}



