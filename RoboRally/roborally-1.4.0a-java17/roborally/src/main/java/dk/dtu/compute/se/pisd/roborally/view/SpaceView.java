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
import dk.dtu.compute.se.pisd.roborally.RoboRally;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.css.Rect;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class SpaceView extends StackPane implements ViewObserver {

    final public static int SPACE_HEIGHT = 50; // 75;
    final public static int SPACE_WIDTH = 50; // 75;

    public final Space space;


    public SpaceView(@NotNull Space space){
        this.space = space;

        // XXX the following styling should better be done with styles
        this.setPrefWidth(SPACE_WIDTH);
        this.setMinWidth(SPACE_WIDTH);
        this.setMaxWidth(SPACE_WIDTH);

        this.setPrefHeight(SPACE_HEIGHT);
        this.setMinHeight(SPACE_HEIGHT);
        this.setMaxHeight(SPACE_HEIGHT);

        //sætter midlertidigt checkpoint til at være grøn.
        if(space.isSpaceCheckPoint()){
            /* FileInputStream inputstream = null;
            try {
                inputstream = new FileInputStream("C:\\Users\\mathi\\IdeaProjects\\RoboRally2\\RoboRally\\roborally-1.4.0a-java17\\roborally\\src\\main\\resources\\RoboRally CheckPoint 1.jpg");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            assert inputstream != null;
            Image image = new Image(inputstream);
            this.setStyle("-fx-background-image: url('" + image + "'); " +
                    "-fx-background-position: center center; " +
                    "-fx-background-repeat: stretch;"); */
            //checkPointView(space);
            this.setStyle("-fx-background-color: green;");
        }

        else if ((space.x + space.y) % 2 == 0) {
            this.setStyle("-fx-background-color: white;");
        } else {
            this.setStyle("-fx-background-color: black;");
        }

        // updatePlayer();

        // This space view should listen to changes of the space
        space.attach(this);
        update(space);
    }


    //virker ikke
    public void checkPointView(){
        FileInputStream inputstream = null;
        try {
            inputstream = new FileInputStream("C:\\Users\\mathi\\IdeaProjects\\RoboRally2\\RoboRally\\roborally-1.4.0a-java17\\roborally\\src\\main\\resources\\RoboRally CheckPoint 1.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("billede?");
        assert inputstream != null;
        Image image = new Image(inputstream);
        this.setStyle("-fx-background-image: url('" + image + "'); ");
                //+
                //"-fx-background-position: center center; " +
                //"-fx-background-repeat: stretch;");
    }

    private void updatePlayer() {
        this.getChildren().clear();

        Player player = space.getPlayer();
        if (player != null) {
            Polygon arrow = new Polygon(0.0, 0.0,
                    10.0, 20.0,
                    20.0, 0.0 );
            try {
                arrow.setFill(Color.valueOf(player.getColor()));
            } catch (Exception e) {
                arrow.setFill(Color.MEDIUMPURPLE);
            }

            arrow.setRotate((90*player.getHeading().ordinal())%360);
            this.getChildren().add(arrow);
        }
    }

    @Override
    public void updateView(Subject subject) {
        if (subject == this.space) {
            updatePlayer();
        }
    }

}
