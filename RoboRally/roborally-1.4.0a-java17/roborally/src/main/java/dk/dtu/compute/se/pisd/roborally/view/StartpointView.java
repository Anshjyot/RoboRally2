package dk.dtu.compute.se.pisd.roborally.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

import static java.awt.Color.green;

/**
 *   Designs our Startpoint
 *
 * @author Anshjyot Singh, s215806
 *
 */

public class StartpointView {

    public static void drawStartpoint(SpaceView spaceView){
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_WIDTH);
        GraphicsContext graphic = canvas.getGraphicsContext2D();

        try{
           Image startpoint = new Image("file:start.png", 50,50, true, true);
           graphic.drawImage(startpoint,50,50);
        }
        catch(Exception e){
            System.out.print("No image");
        }
        spaceView.getChildren().add(canvas);
    }
}
