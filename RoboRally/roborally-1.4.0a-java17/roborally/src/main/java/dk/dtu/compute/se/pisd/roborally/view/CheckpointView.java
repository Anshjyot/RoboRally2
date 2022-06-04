package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.Checkpoint;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.FieldAction;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class CheckpointView {
    public static void drawCheckpointView(SpaceView spaceView, FieldAction fieldAction) {
        Checkpoint checkpoint = (Checkpoint) fieldAction;
        int checkNo = checkpoint.getCheckpointNo();
        //spaceView.getChildren().clear();
        try {
            Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
            GraphicsContext graphic = canvas.getGraphicsContext2D();
            String imageName = "Checkpoint " + String.valueOf(checkNo) + ".jpg";
            Image checkpointImage = new Image(imageName,50,50,true,true);
            graphic.drawImage(checkpointImage, 0, 0);
            /*
            switch (checkNo) {
                case 1:
                    Image first = new Image("Checkpoint 1.jpg",50,50,true,true);
                    graphic.drawImage(first, 0, 0);
                    break;
            }*/

            spaceView.getChildren().add(canvas);
        }
        catch(Exception e){
            System.out.print("No Image");
        }
    }
}
