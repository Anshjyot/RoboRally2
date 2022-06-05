package dk.dtu.compute.se.pisd.roborally.model.boardelements;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 *  @author Anshjyot Singh, s215806
 *  Designs the logic regarding the Lasers (SPAM CARDS)
 */
public class Laser extends FieldAction {

    private int noOfLaser;
    private Heading heading;
    boolean center;

    public int getNoOfLaser() {
        return noOfLaser;
    }
    public Heading getHeading() {
        return heading;
    }


    public boolean getCenter(){
        return center;
    }


    @Override
    public boolean doAction(GameController gameController, Space space) {

        for (int i = 0; i < noOfLaser; i++) {
            Player player = space.getPlayer();
            player.setDamagecards(Command.SPAM);
        }
        return true;
    }
}