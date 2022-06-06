package dk.dtu.compute.se.pisd.roborally.model.boardelements;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Command;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Reboot extends FieldAction {
    private Heading heading;

    public Heading getHeading() {
        return heading;
    }


    @Override
    public boolean doAction(GameController gameController, Space space) {

        Player currentPlayer = space.getPlayer();

            //gets 2 spam cards when reaching the reboot space
            currentPlayer.setDamagecards(Command.SPAM);
            currentPlayer.setDamagecards(Command.SPAM);
        return true;
    }
}