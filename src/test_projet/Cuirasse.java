/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test_projet;

import java.io.IOException;
import javafx.scene.image.Image;

/**
 *
 * @author lisa
 */
public class Cuirasse extends Bateau {
    public Cuirasse(int taille, boolean horizontal) throws IOException {
        super(taille);
        if (horizontal){decouperImageHorizontale("/Users/lisa/Desktop/fleur.jpg");}
        else {decouperImageVerticale("/Users/lisa/Desktop/fleur.jpg");}
    }
}