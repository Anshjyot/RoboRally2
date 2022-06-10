package dk.dtu.compute.se.pisd.roborally.view.boardelements;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.List;

/**
 * Designs our  Wall
 *
 * @author Anshjyot Singh, s215806
 *
 */

public class WallView {

    public static void drawWall(SpaceView spaceView, Space space) {
        List<Heading> walls = space.getWalls();
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //resizing directly on loading:
        Image image = new Image("wall.png", 10, 50, false, false);

        for (int i = 0; i < walls.size(); i++) {
            Heading header = walls.get(i);
            switch (header) {

                case DOWN:
                    canvas.setRotate(270);
                    gc.drawImage(image, 0, 0);
                    break;
                case LEFT:
                    gc.drawImage(image, 0, 0);
                    break;
                case UP:
                    canvas.setRotate(90);
                    gc.drawImage(image, 0, 0);
                    break;
                case RIGHT:
                    gc.drawImage(image, 40, 0);
                    break;
            }
            spaceView.getChildren().add(canvas);
        }
    }
}

