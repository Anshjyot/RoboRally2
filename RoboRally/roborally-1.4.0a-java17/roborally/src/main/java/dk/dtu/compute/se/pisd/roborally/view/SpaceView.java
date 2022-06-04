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
import dk.dtu.compute.se.pisd.roborally.controller.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;
import javafx.scene.text.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;


/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class SpaceView extends StackPane implements ViewObserver {
    public int tileAngle=0;
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

        int i = 1;
        if (space.x == 3 && space.y == 5) {
            this.setStyle("-fx-background-color: green;");
            //sætte nr. checkpoint 1-4
            Text text = new Text();
            text.setText("1");
            text.setX(50);
            text.setY(50);
            text.setFill(Color.LIME);
            this.getChildren().add(text);
        }
        updateNormalSpace();
/*
        else if ((space.x + space.y) % 2 == 0) {
            this.setStyle("-fx-background-color: white;");
        } else {
            this.setStyle("-fx-background-color: black;");
        }

 */

        /* if(space.isWall()) {
            this.setStyle("-fx-background-color: red;");
            //updateWall();

            //laver en linje - kan måske bruges til laser
            Line line = new Line();
            line.setStartX(200);
            line.setStartY(200);
            line.setRotate(90);

            this.getChildren().add(line);

         */
            /*
            Image image = new Image("wall.png", 10, 50, false, false);
            ImageView imageView = new ImageView(image);

            this.getChildren().add(imageView);

*/
        // updatePlayer();

        // This space view should listen to changes of the space
        space.attach(this);
        update(space);


    }

    private void updatePlayer() {
        //this.getChildren().clear();

        //removes player from previous space
        for (int i = 0; i < this.getChildren().size(); i++) {
            if(this.getChildren().get(i).getClass().getSimpleName().equals("Polygon")){
                this.getChildren().remove(i);
            }

        }
        //fjerner ikke altid...
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i).getClass().getSimpleName().equals("Canvas")) {
                this.getChildren().remove(i);
            }
        }

        Player player = space.getPlayer();
        if (player != null) {
            Polygon arrow = new Polygon(0.0, 0.0,
                    10.0, 20.0,
                    20.0, 0.0);
            try {
                arrow.setFill(Color.valueOf(player.getColor()));
            } catch (Exception e) {
                arrow.setFill(Color.MEDIUMPURPLE);
            }

            arrow.setRotate((90 * player.getHeading().ordinal()) % 360);
            Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_WIDTH);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.BLUEVIOLET);
            gc.setLineWidth(1);
            gc.strokeText(String.valueOf(player.getCheckpoints()), SpaceView.SPACE_WIDTH*0.8, SpaceView.SPACE_WIDTH*0.8);

            this.getChildren().add(canvas);
            gc.setStroke(Color.YELLOW);
            gc.setLineWidth(1);
            this.getChildren().add(arrow);
        }
           /*
            try {
                arrow.setFill(Color.valueOf(player.getColor()));
            } catch (Exception e) {
                arrow.setFill(Color.MEDIUMPURPLE);
            }

            arrow.setRotate((90 * player.getHeading().ordinal()) % 360);
            this.getChildren().add(arrow); */
    }

    /**
     * Draws the walls on the gameboard.
     */

    private void drawWall() {

        List<Heading> walls = space.getWalls();
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

       // ImagePattern wall = new ImagePattern(new Image("Pictures/wall.png", 50, 50, false, false));

        //resizing directly on loading:
        Image image = new Image("wall.png", 10, 50, false, false);
        ImageView imageView_vertical = new ImageView(image);
        ImageView imageView_horizontal = new ImageView(image);
        imageView_horizontal.setRotate(90);

        /* for (Heading wallHeading : this.space.getWalls()) {
            Rectangle rectangle = new Rectangle(0.0, 0.0, SPACE_WIDTH, SPACE_HEIGHT);
            rectangle.setFill(wall);
         */

            for (int i = 0; i < walls.size(); i++) {
                Heading header = walls.get(i);
                switch(header){

                case DOWN -> gc.drawImage(image,0,0);
                case LEFT -> gc.drawImage(image,44,0);
                case UP -> gc.drawImage(image,0,0);
                case RIGHT-> gc.drawImage(image,0,44);
            };
            /*
                rectangle.setRotate(angle - this.tileAngle);
            rectangle.toFront();
            this.getChildren().add(rectangle);
             */
        }
        if(space.isWall()) {
            this.getChildren().add(imageView_vertical);
            this.getChildren().add(imageView_horizontal);
        }
    }



    private void drawLaser() {

        List<Heading> laser = space.getLaser();
        Canvas canvas1 = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas1.getGraphicsContext2D();

        // ImagePattern wall = new ImagePattern(new Image("Pictures/wall.png", 50, 50, false, false));

        //resizing directly on loading:
        Image image2 = new Image("laser.png", 50, 50, false, false);
        ImageView imageView_vertical = new ImageView(image2);
        ImageView imageView_horizontal = new ImageView(image2);
        imageView_horizontal.setRotate(90);
        gc.drawImage(image2, 0, 0);

        /* for (Heading wallHeading : this.space.getWalls()) {
            Rectangle rectangle = new Rectangle(0.0, 0.0, SPACE_WIDTH, SPACE_HEIGHT);
            rectangle.setFill(wall);
         */

        for (int i = 0; i < laser.size(); i++) {
            Heading header = laser.get(i);
            switch(header){

                case DOWN -> gc.drawImage(image2,0,0);
                case LEFT -> gc.drawImage(image2,44,0);
                case UP -> gc.drawImage(image2,0,0);
                case RIGHT-> gc.drawImage(image2,0,44);
            };
            /*
                rectangle.setRotate(angle - this.tileAngle);
            rectangle.toFront();
            this.getChildren().add(rectangle);
             */
        }
        if(space.isLaser()) {
            this.getChildren().add(canvas1);
           // this.getChildren().add(imageView_horizontal);
        }
    }


    @Override
    public void updateView(Subject subject) {
        //this.getChildren().clear();
        if (subject == this.space) {
            updateNormalSpace();
            if (this.space.getStartPoint()) {
                StartpointView.drawStartpoint(this);
            }

            if (!this.space.getWalls().isEmpty()) {
                WallView.drawWall(this, this.space);
            }

        }
        for (FieldAction fa : space.getActions()) {
            if (fa instanceof ConveyorBelt) {
                ConveyorBeltView.drawConveyorBeltView(this, fa);

            }
        }
        updatePlayer();
        drawWall();
        drawLaser();
    }

    public void updateNormalSpace() {
        //this.getChildren().clear();

        //Rectangle rect = new Rectangle(50,50);
        //rect.setFill(Color.GREEN);
        Image image = new Image("space.png", 50, 50, true, true);
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext graphic = canvas.getGraphicsContext2D();
        graphic.drawImage(image, 0,0);
        this.getChildren().add(canvas);

        // canvas.requestFocus();
        //System.out.println(canvas.isFocused());

        /*
        Circle circle = new Circle();
        circle.setCenterX(100.0f);
        circle.setCenterY(100.0f);
        circle.setRadius(50.0f);
        circle.setFill(Color.RED);
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_WIDTH);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(1);

        this.getChildren().add(canvas);
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(1);
        this.getChildren().add(circle);

*/
        //System.out.println(canvas.setStyle(graphic.drawImage(file:space.png, 50, 50)));
    }
}

