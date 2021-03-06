package dk.dtu.compute.se.pisd.roborally.view.boardelements;

import dk.dtu.compute.se.pisd.roborally.model.boardelements.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;


/**
 * @author Anshjyot Singh, s215806 & Marco Miljkov Hansen @s194302
 */

public class ConveyorBeltView {
    /**
     * Creates the view for the conveyorBelts, which is differentiated by the different directions
     * and the two different conveyor belts (Green and Blue)
     * @param spaceView is SpaceView object and updates the view for the specific space
     * @param fa takes the FieldAction object to determine the heading, which is also later used in Space-view
     */
    public static void drawConveyorBeltView(SpaceView spaceView, FieldAction fa) {
        ConveyorBelt conveyorBelt = (ConveyorBelt) fa;
        Heading heading = conveyorBelt.getHeading();
        //spaceView.getChildren().clear();
        try {
            Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
            GraphicsContext graphic = canvas.getGraphicsContext2D();

            switch (heading) {
                case UP:
                    if (conveyorBelt.getSpeed() == 1) {
                        Image Up = new Image("ArrowUp.png", 50, 50, true, true);
                        graphic.drawImage(Up, 0, 0);
                    }
                    else {
                        Image Up2 = new Image("ArrowUpBlue.png", 50, 50, true, true);
                        graphic.drawImage(Up2, 0, 0);
                    }

                    break;
                case DOWN:
                    if (conveyorBelt.getSpeed() == 1) {
                        Image Down = new Image("ArrowDown.png", 50, 50, true, true);
                        graphic.drawImage(Down, 0, 0);
                    }
                    else {
                        Image Down2 = new Image("ArrowDownBlue.png", 50, 50, true, true);
                        graphic.drawImage(Down2, 0, 0);
                    }
                    break;
                case LEFT:
                    if (conveyorBelt.getSpeed() == 1) {
                        Image left = new Image("ArrowLeft.png", 50, 50, true, true);
                        graphic.drawImage(left, 0, 0);
                    }
                    else {
                        Image left2 = new Image("ArrowLeftBlue.png", 50, 50, true, true);
                        graphic.drawImage(left2, 0, 0);
                    }
                    break;
                case RIGHT:
                    if (conveyorBelt.getSpeed() == 1) {
                        Image Right = new Image("ArrowRight.png", 50, 50, true, true);
                        graphic.drawImage(Right, 0, 0);
                    }
                    else {
                        Image Right2 = new Image("ArrowRightBlue.png", 50, 50, true, true);
                        graphic.drawImage(Right2, 0, 0);
                    }
                    break;
            }

            spaceView.getChildren().add(canvas);
        } catch (Exception e) {
            System.out.print("No Image");
        }
    }
}
