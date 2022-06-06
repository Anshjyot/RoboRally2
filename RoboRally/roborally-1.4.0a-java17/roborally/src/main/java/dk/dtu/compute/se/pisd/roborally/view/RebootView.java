package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.Reboot;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;

public class RebootView {
    public static void drawRebootView(SpaceView spaceView, FieldAction fieldAction){

        Reboot reboot = (Reboot) fieldAction;
        Heading heading = reboot.getHeading();

        try{
            Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_WIDTH);
            GraphicsContext graphic = canvas.getGraphicsContext2D();

            Image rebootImage = new Image("Reboot.png", 50,50, true, true);
            graphic.drawImage(rebootImage,50,50);

                switch (heading) {
                case DOWN:
                    graphic.drawImage(rebootImage, 0, 0);
                    break;
                case LEFT:
                    canvas.setRotate(90);
                    graphic.drawImage(rebootImage, 0, 0);
                    break;
                case UP:
                    canvas.setRotate(180);
                    graphic.drawImage(rebootImage, 0, 0);
                    break;
                case RIGHT:
                    canvas.setRotate(270);
                    graphic.drawImage(rebootImage, 40, 0);
                    break;
            }
            spaceView.getChildren().add(canvas);
        }
        catch(Exception e){
        System.out.print("No image");
      }

    }
}

