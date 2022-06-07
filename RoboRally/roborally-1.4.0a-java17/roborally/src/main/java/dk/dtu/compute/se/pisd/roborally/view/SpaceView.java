/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.*;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.Laser;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import org.jetbrains.annotations.NotNull;
import javafx.scene.text.*;
import java.util.List;


/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class SpaceView extends StackPane implements ViewObserver {
    public int tileAngle = 0;
    final public static int SPACE_HEIGHT = 50; // 75;
    final public static int SPACE_WIDTH = 50; // 75;

    public final Space space;

    public SpaceView(@NotNull Space space) {
        this.space = space;

        // XXX the following styling should better be done with styles
        this.setPrefWidth(SPACE_WIDTH);
        this.setMinWidth(SPACE_WIDTH);
        this.setMaxWidth(SPACE_WIDTH);

        this.setPrefHeight(SPACE_HEIGHT);
        this.setMinHeight(SPACE_HEIGHT);
        this.setMaxHeight(SPACE_HEIGHT);

            // This space view should listen to changes of the space
            space.attach(this);
            update(space);
    }

        private void updatePlayer () {
            if (this.getShape() instanceof Polygon) {
                this.getChildren().clear();
            }

            if (this.getStyleableNode() instanceof Canvas) {
                this.getChildren().clear();
            }

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

                this.getChildren().add(canvas);
                gc.setStroke(Color.YELLOW);
                gc.setLineWidth(1);
                this.getChildren().add(viewRobot);
            }
        }

        /**
         * Draws the walls on the gameboard.
         */

        private void drawWall () {

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
                this.getChildren().add(canvas);
            }
        }

        @Override
        public void updateView (Subject subject){
            this.getChildren().clear();
            updateNormalSpace();
            if (this.space.getStartPoint()) {
                StartpointView.drawStartpoint(this);
            }
            drawWall();

            for (FieldAction fa : space.getActions()) {
                if (fa instanceof ConveyorBelt) {
                    ConveyorBeltView.drawConveyorBeltView(this, fa);
                }
                else if(fa instanceof Checkpoint){
                    CheckpointView.drawCheckpointView(this,fa);
                }
                else if(fa instanceof Laser){
                    LaserView.drawLaserView(this,fa);
                }
                else if (fa instanceof Gear){
                    GearView.drawGearView(this,fa);
                }
                else if (fa instanceof PushPanels) {
                    PushPanelsView.drawPushPanel(this, fa);
                }
                else if (fa instanceof Reboot){
                    RebootView.drawRebootView(this,fa);
                }
            }
            updatePlayer();
        }

        public void updateNormalSpace () {
            Image image = new Image("space.png", 50, 50, true, true);
            Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
            GraphicsContext graphic = canvas.getGraphicsContext2D();
            graphic.drawImage(image, 0, 0);
            this.getChildren().add(canvas);
    }
}

