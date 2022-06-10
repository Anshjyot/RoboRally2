package dk.dtu.compute.se.pisd.roborally.model.boardelements;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Command;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
/**
 * @author  Marco Miljkov Hansen, s194302@dtu.dk
 * This is the code-logic regarding a field-action board-element.
 * Pit moves the player to the reboot Field, the logic is implemented in the game controller.
 */
public class Pit extends FieldAction{
    private Heading heading;
    public Heading getHeading() {
        return heading;
    }
@Override
public boolean doAction(GameController gameController, Space space) {
        Player currentPlayer = space.getPlayer();

        //gameController.moveToRebootToken(currentPlayer);


    return false;
}

}
