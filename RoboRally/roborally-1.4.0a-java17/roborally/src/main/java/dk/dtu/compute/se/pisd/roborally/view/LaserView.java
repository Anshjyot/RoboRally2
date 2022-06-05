package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.Laser;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.FieldAction;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import static javafx.scene.paint.Color.RED;

/**
 * @author Anshjyot Singh S215806
 * Designs the lasers
 */
public class LaserView {

    public static void drawLaserView(SpaceView spaceView, FieldAction fa){
        Laser laser = (Laser) fa;
        Heading heading = laser.getHeading();

        try {
            Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_WIDTH);
            GraphicsContext graphic = canvas.getGraphicsContext2D();
            graphic.setStroke(RED);
            graphic.setLineWidth(2);

            switch(heading){
                case DOWN -> {
                    if(laser.getNoOfLaser() == 1) {
                        graphic.strokeLine(SpaceView.SPACE_WIDTH / 2, SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT / 2, 0);
                    }
                    if(laser.getNoOfLaser() == 2){
                        graphic.strokeLine(SpaceView.SPACE_WIDTH/3, SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT/3, 0);
                        graphic.strokeLine(SpaceView.SPACE_WIDTH*0.65, SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT*0.65, 0);
                    }
                    else if(laser.getNoOfLaser() == 3) {
                        graphic.strokeLine(SpaceView.SPACE_WIDTH/2, SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT/2, 0);
                        graphic.strokeLine(SpaceView.SPACE_WIDTH/3, SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT/3, 0);
                        graphic.strokeLine(SpaceView.SPACE_WIDTH*0.65, SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT*0.65, 0);
                    }
                    if (!laser.getCenter()) {
                        Image down = new Image("LaserDown.png",30,30, true, true);
                        graphic.drawImage(down,SpaceView.SPACE_WIDTH/6,0);
                    }
                    break;
                }
                case LEFT -> {
                    if(laser.getNoOfLaser() == 1) {
                        graphic.strokeLine(0, SpaceView.SPACE_WIDTH / 2, SpaceView.SPACE_HEIGHT, SpaceView.SPACE_HEIGHT / 2);
                    }

                    if(laser.getNoOfLaser() == 2) {
                        graphic.strokeLine(0, SpaceView.SPACE_WIDTH/3, SpaceView.SPACE_HEIGHT, SpaceView.SPACE_HEIGHT/3);
                        graphic.strokeLine(0, SpaceView.SPACE_WIDTH*0.65, SpaceView.SPACE_HEIGHT, SpaceView.SPACE_HEIGHT*0.65);
                    }

                   else if(laser.getNoOfLaser() == 3) {
                        graphic.strokeLine(0, SpaceView.SPACE_WIDTH/2, SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT/2);
                        graphic.strokeLine(0, SpaceView.SPACE_WIDTH/3, SpaceView.SPACE_HEIGHT, SpaceView.SPACE_HEIGHT/3);
                        graphic.strokeLine(0, SpaceView.SPACE_WIDTH*0.65, SpaceView.SPACE_HEIGHT, SpaceView.SPACE_HEIGHT*0.65);
                    }

                    if (!laser.getCenter()) {
                        Image left = new Image("LaserLeft.png",30,30, true, true);
                        graphic.drawImage(left,SpaceView.SPACE_WIDTH*0.65,SpaceView.SPACE_HEIGHT/5);
                    }
                    break;
                }
                case UP -> {
                    if(laser.getNoOfLaser() == 1) {
                        graphic.strokeLine(SpaceView.SPACE_WIDTH / 2, SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT / 2, 0);
                    }
                    if(laser.getNoOfLaser() == 2){
                        graphic.strokeLine(SpaceView.SPACE_WIDTH/3, SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT/3, 0);
                        graphic.strokeLine(SpaceView.SPACE_WIDTH*0.65, SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT*0.65, 0);
                    }
                    else if(laser.getNoOfLaser() == 3) {
                        graphic.strokeLine(SpaceView.SPACE_WIDTH/2, SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT/2, 0);
                        graphic.strokeLine(SpaceView.SPACE_WIDTH/3, SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT/3, 0);
                        graphic.strokeLine(SpaceView.SPACE_WIDTH*0.65, SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT*0.65, 0);
                    }
                    if (!laser.getCenter()) {
                        Image up = new Image("LaserUp.png",30,30, true, true);
                        graphic.drawImage(up,SpaceView.SPACE_WIDTH/6,SpaceView.SPACE_HEIGHT*0.70);
                    }
                    break;
                }
                case RIGHT -> {
                    if(laser.getNoOfLaser() == 1) {
                        graphic.strokeLine(0, SpaceView.SPACE_WIDTH / 2, SpaceView.SPACE_HEIGHT, SpaceView.SPACE_HEIGHT / 2);
                    }
                     if(laser.getNoOfLaser() == 2){
                        graphic.strokeLine(0, SpaceView.SPACE_WIDTH/3, SpaceView.SPACE_HEIGHT, SpaceView.SPACE_HEIGHT/3);
                        graphic.strokeLine(0, SpaceView.SPACE_WIDTH*0.65, SpaceView.SPACE_HEIGHT, SpaceView.SPACE_HEIGHT*0.65);
                    }
                    else if(laser.getNoOfLaser() == 3) {
                        graphic.strokeLine(0, SpaceView.SPACE_WIDTH/2, SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT/2);
                        graphic.strokeLine(0, SpaceView.SPACE_WIDTH/3, SpaceView.SPACE_HEIGHT, SpaceView.SPACE_HEIGHT/3);
                        graphic.strokeLine(0, SpaceView.SPACE_WIDTH*0.65, SpaceView.SPACE_HEIGHT, SpaceView.SPACE_HEIGHT*0.65);
                    }
                    if (!laser.getCenter()) {
                        Image right = new Image("LaserRight.png",30,30, true, true);
                        graphic.drawImage(right,0,SpaceView.SPACE_HEIGHT/6);
                    }
                    break;
                }
            }
            spaceView.getChildren().add(canvas);
        }
        catch(Exception e){
            System.out.println("Image file not found");
        }
    }
}


