package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.Laser;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.FieldAction;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static javafx.scene.paint.Color.BLUE;
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
            Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
            GraphicsContext graphic = canvas.getGraphicsContext2D();
            graphic.setStroke(RED);
            graphic.setLineWidth(2);

            switch(heading){
               // switch() {

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
                        Image down = null;
                        switch (laser.getNoOfLaser()) {
                            case 1:
                                down = new Image("LaserDown.png",30,30, true, true);
                                graphic.drawImage(down,SpaceView.SPACE_WIDTH/3,SpaceView.SPACE_HEIGHT*0.70);
                                break;
                            case 2:
                                down = new Image("TwoLaserDown.png",50,50, true, true);
                                graphic.drawImage(down,0,0);
                                break;
                            case 3:
                                down = new Image("ThreeLaserDown.png",40,30, true, true);
                                graphic.drawImage(down,SpaceView.SPACE_WIDTH/3,SpaceView.SPACE_HEIGHT*0.70);
                                break;
                        }

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
                        Image left = null;
                        switch (laser.getNoOfLaser()) {
                            case 1:
                                left = new Image("LaserLeft.png",30,30, true, true);
                                break;
                            case 2:
                                left = new Image("TwoLaserLeft.png",50,50, true, true);
                                break;
                            case 3:
                                left = new Image("ThreeLaserLeft.png",40,30, true, true);
                                break;
                        }
                        graphic.drawImage(left,SpaceView.SPACE_WIDTH/3,SpaceView.SPACE_HEIGHT*0.70);
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
                        graphic.strokeLine(SpaceView.SPACE_WIDTH*0.18, SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT*0.18, 0);
                        graphic.strokeLine(SpaceView.SPACE_WIDTH*0.49, SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT*0.49, 0);
                        graphic.strokeLine(SpaceView.SPACE_WIDTH*0.82, SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT*0.82, 0);
                    }
                    if (!laser.getCenter()) {
                        Image up = null;
                        switch (laser.getNoOfLaser()) {
                            case 1:
                                up = new Image("LaserUp.png",30,30, true, true);
                                graphic.drawImage(up,SpaceView.SPACE_WIDTH/3,SpaceView.SPACE_HEIGHT*0.70);
                                break;
                            case 2:
                                up = new Image("TwoLaserUp.png",30,30, true, true);
                                graphic.drawImage(up,SpaceView.SPACE_WIDTH/3,SpaceView.SPACE_HEIGHT*0.70);
                                break;
                            case 3:
                                up = new Image("ThreeLaserUp.png",SpaceView.SPACE_WIDTH,SpaceView.SPACE_HEIGHT*(120.0/636), true, true);
                                graphic.drawImage(up,0,SpaceView.SPACE_WIDTH*(1-(120.0/636)));
                                break;
                        }
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
                        Image right = null;
                        switch (laser.getNoOfLaser()) {
                            case 1:
                                right = new Image("LaserRight.png",30,30, true, true);
                                break;
                            case 2:
                                right = new Image("TwoLaserRight.png",30,30, true, true);
                                break;
                            case 3:
                                right = new Image("ThreeLaserRight.png",SpaceView.SPACE_HEIGHT*(120.0/636),SpaceView.SPACE_HEIGHT, true, true);
                                break;
                        }
                        graphic.drawImage(right,0,SpaceView.SPACE_WIDTH*(0));
                    }
                    break;
                }
            }
            spaceView.getChildren().add(canvas);
        }
        catch(Exception e){
            System.out.println("No Image");
        }
    }
}


