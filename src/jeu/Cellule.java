import java.awt.Dimension;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author thoma
 */
public class Plateau extends Canvas {
    Cellule[][] tab = new Cellule[10][10];//10 de largeur et 5 de hauteur
    Dimension ech = new Dimension();//l'échelle
    
    public Plateau(int largeur, int hauteur){
        super(largeur, hauteur);
        //Créer toutes les cellules
        for(int x=0; x<tab.length; x++){
            for(int y = 0; y<tab[x].length; y++){
                tab[x][y] = new Cellule();
            }
        }
    }
    public void calculeEchelle(){
        //largeur 1 case = largeur zone de dessin / largeur de la grille
        ech.width=(int)(getWidth()/tab.length);
        //hauteur 1 case = ...
        ech.height=(int)(getHeight()/tab[0].length);
    }
    public void paint(GraphicsContext gc){
        calculeEchelle();
        //Met la couleur
        gc.setFill(Color.CYAN);
        gc.fillRect(0,0, getWidth(), getHeight());//coordonnées du point supérieur gauche, puis largeur et hauteur
        //Dessine les cellules
        for(int x=0; x<tab.length; x++){
            for(int y = 0; y<tab[x].length; y++){
                tab[x][y].dessineToi(gc,x,y,ech);
            }
        }
    }
}
