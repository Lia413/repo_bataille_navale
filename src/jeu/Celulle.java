package jeu

import java.awt.Dimension;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author thoma
 */
public class Cellule {
    public void dessineToi(GraphicsContext gc, int x, int y, Dimension ech){
        gc.strokeRect(x*ech.width, y*ech.height, ech.width, ech.height);
    }
}
