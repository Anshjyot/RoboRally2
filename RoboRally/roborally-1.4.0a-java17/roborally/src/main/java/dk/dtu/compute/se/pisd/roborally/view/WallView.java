package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;
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
        try {
            List<Heading> walls = space.getWalls();
            Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
            GraphicsContext graphic = canvas.getGraphicsContext2D();
            Image wallNorthSouth = new Image("file:wall.png", 50, 50, true, true);
            Image wallWestEast = new Image("file:wall.png", 50, 50, true, true);

            for (int i = 0; i < walls.size(); i++) {
                Heading header = walls.get(i);
                switch (header) {
                    case DOWN -> graphic.drawImage(wallNorthSouth, 0, 44);
                    case UP -> graphic.drawImage(wallNorthSouth, 0, 0);
                    case LEFT -> graphic.drawImage(wallWestEast, 0, 0);
                    case RIGHT -> graphic.drawImage(wallWestEast, 44, 0);
                }
            }
            spaceView.getChildren().add(canvas);
        }
        catch(Exception e){
            System.out.println("No Image");
        }

    }
}