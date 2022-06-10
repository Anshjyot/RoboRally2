package dk.dtu.compute.se.pisd.roborally.model.boardelements;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.ArrayList;


/**
 *  @author Anshjyot Singh, s215806
 *  Designs the logic regarding the PushPanels, pushes the robot a space field in the direction of the push panel
 *  - when landing on that space
 */

public class PushPanels extends FieldAction {
    private Heading heading;
    public Heading getHeading(){
        return heading;
    }
    private int register[];

    @Override
    public boolean doAction(GameController gameController, Space space) {
       /* for(int i = 0; i == register.length; i++) {
            register != gameController.board.getStep())
            return false;
        }*/
        Heading push = this.heading;

        Space neighbour = gameController.board.getNeighbour(space, push);
        try {
            switch (push) {
                case DOWN:


            }
            gameController.moveToSpace(space.getPlayer(), neighbour, push);
            return true;
        }
        catch(Exception e){}

        return false;
    }
}
