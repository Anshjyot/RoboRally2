package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.controller.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import static java.awt.Color.green;


/**
 *  Designs our ConyeyorBelt
 *
 * @author Anshjyot Singh, s215806
 *
 */
//
public class ConveyorBeltView {

    public static void drawConveyorBeltView(SpaceView spaceView, FieldAction fieldAction) {
        ConveyorBelt conveyorBelt = (ConveyorBelt) fieldAction;
        Heading heading = conveyorBelt.getHeading();
        spaceView.getChildren().clear();
        try {
            Rectangle rect1 = new Rectangle(50,50);
            rect1.setStyle("-fx-background-color: green;");

            Image right = new Image("ArrowRight.png", 50, 50, true, true);
            Image left = new Image("ArrowLeft.png", 50, 50, true, true);
            Image Up = new Image("ArrowUp.png", 50, 50, true, true);
            Image Down = new Image("ArrowDown.png", 50, 50, true, true);
            ImageView imageView = new ImageView(right);
            ImageView imageView2 = new ImageView(left);
            ImageView imageView3 = new ImageView(Up);
            ImageView imageView4 = new ImageView(Down);

            imageView.setX(30);
            imageView.setY(30);
            imageView2.setX(35);
            imageView2.setY(35);
            imageView3.setX(40);
            imageView3.setY(40);
            imageView4.setX(45);
            imageView4.setY(45);


            Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
            GraphicsContext graphic = canvas.getGraphicsContext2D();

            switch (heading) {
                case UP -> graphic.drawImage(Up, 0, 0);
                case DOWN -> graphic.drawImage(Down, 0, 0);
                case LEFT -> graphic.drawImage(left, 0, 0);
                case RIGHT -> graphic.drawImage(right, 0, 0);

            }

            spaceView.getChildren().add(canvas);
        }
        catch(Exception e){
            System.out.print("No Image");
        }
    }
}