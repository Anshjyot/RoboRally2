package dk.dtu.compute.se.pisd.roborally.view.boardelements;

import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

import static java.awt.Color.green;

public class StartpointView {

    public static void drawStartpoint(SpaceView spaceView){
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_WIDTH);
        GraphicsContext graphic = canvas.getGraphicsContext2D();

        try{
           Image startpoint = new Image("Startpoint.png", 50,50, true, true);
           graphic.drawImage(startpoint,0,0);
           spaceView.getChildren().add(canvas);
        }
        catch(Exception e){
            System.out.print("No image");
        }
    }
}
