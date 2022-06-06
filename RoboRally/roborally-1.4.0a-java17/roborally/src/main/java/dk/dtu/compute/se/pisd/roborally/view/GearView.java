package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.Gear;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.FieldAction;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import static javafx.scene.paint.Color.RED;

/**
 * @author 8
 *
 */

public class GearView {

    public static void drawGearView(SpaceView spaceView, FieldAction fa){

        Gear gear = (Gear) fa;
        Heading heading = gear.getHeading();
        try {
            Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_WIDTH);
            GraphicsContext graphic = canvas.getGraphicsContext2D();

            switch(heading){
                case RIGHT:
                    Image right = new Image("GearRight.png", 50, 50, true, true);
                    graphic.drawImage(right, 0, 0);
                    break;

                case LEFT:
                    Image left = new Image("GearLeft.png", 50, 50, true, true);
                    graphic.drawImage(left, 0, 0);
                    break;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
