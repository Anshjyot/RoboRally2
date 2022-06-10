package dk.dtu.compute.se.pisd.roborally.view.boardelements;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.Reboot;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;

    public class PitView {
        public static void drawPitView(SpaceView spaceView, FieldAction fa){

            try{
                Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_WIDTH);
                GraphicsContext graphic = canvas.getGraphicsContext2D();
                Image PitImage = new Image("Pit.png", 50,50, true, true);
                graphic.drawImage(PitImage,0,0);
                spaceView.getChildren().add(canvas);

            }
            catch(Exception e){
                System.out.print("No image");
            }

        }
    }



