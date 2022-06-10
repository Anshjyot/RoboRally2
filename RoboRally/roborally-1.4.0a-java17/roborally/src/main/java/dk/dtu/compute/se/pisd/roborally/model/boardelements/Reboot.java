package dk.dtu.compute.se.pisd.roborally.model.boardelements;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Command;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * Sets a rebooting robots heading to the reboots heading.
 * Then gives them two spam cards and clears
 * their remaining registers.
 * @author Mathilde Elia s215811
 */

public class Reboot extends FieldAction {
    private Heading heading;

    public Heading getHeading() {
        return heading;
    }

    @Override
    public boolean doAction(GameController gameController, Space space) {

        Player currentPlayer = space.getPlayer();
        if(currentPlayer.isRebooting()){
            currentPlayer.setHeading(this.heading);
            currentPlayer.setDamagecards(Command.SPAM);
            currentPlayer.setDamagecards(Command.SPAM);
            for(int i = 0; i < Player.NO_REGISTERS; i++){
                currentPlayer.clearProgram(i);
            }
        }
        return false;
    }
}
