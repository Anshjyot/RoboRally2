package dk.dtu.compute.se.pisd.roborally.view.boardelements;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.PushPanels;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.FieldAction;

/**
 *  @author Anshjyot Singh, s215806
 */
public class PushPanelsView {
    /**
     * This class displays the view of a pushpanel
     * @param spaceView is SpaceView object and updates the view for the specific space
     * @param fa takes the FieldAction object to determine the heading, which is also later used in Space-view
     */
    public static void drawPushPanel(SpaceView spaceView, FieldAction fa) {
        try {
            PushPanels pushPanels = (PushPanels) fa;
            Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
            GraphicsContext graphic = canvas.getGraphicsContext2D();
            Heading heading = pushPanels.getHeading();

            switch (heading) {
                case UP:
                    Image Up = new Image("PushPanelUp.png", 50, 50, true, true);
                    graphic.drawImage(Up, 0, 0);
                    break;
                case DOWN:
                    Image Down = new Image("PushPanelDown.png", 50, 50, true, true);
                    graphic.drawImage(Down, 0, 0);
                    break;
                case LEFT:
                    Image left = new Image("PushPanelLeft.png", 50, 50, true, true);
                    graphic.drawImage(left, 0, 0);
                    break;
                case RIGHT:
                    Image right = new Image("PushPanelRight.png", 50, 50, true, true);
                    graphic.drawImage(right, 0, 0);
                    break;
            }
            spaceView.getChildren().add(canvas);
        }
        catch(Exception e){
            System.out.print("No Image");
        }
    }
}
