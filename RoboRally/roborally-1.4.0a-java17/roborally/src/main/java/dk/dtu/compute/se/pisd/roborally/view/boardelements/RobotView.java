package dk.dtu.compute.se.pisd.roborally.view.boardelements;

import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class RobotView {
    public static void drawRobot(SpaceView spaceView, Space space){
        Player player = space.getPlayer();
        if (player != null) {
            String imageName = "Robot" + player.getRobot() + ".png";
            Image robot = new Image(imageName,35,35,true,true);
            ImageView viewRobot = new ImageView(robot);

            viewRobot.setRotate((90 * player.getHeading().ordinal()) % 360);
            Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_WIDTH);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.BLUEVIOLET);
            gc.setLineWidth(1);
            gc.strokeText(String.valueOf(player.getNoCheckpointReached()),
                    SpaceView.SPACE_WIDTH * 0.8, SpaceView.SPACE_WIDTH * 0.8);

            spaceView.getChildren().add(canvas);
            gc.setStroke(Color.YELLOW);
            gc.setLineWidth(1);
            spaceView.getChildren().add(viewRobot);
        }
    }
}
