package dk.dtu.compute.se.pisd.roborally.model.boardelements;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * Checks if player who reached the checkpoint
 * has reached the checkpoint before it, and then
 * adds this checkpoint if they have.
 * @author Mathilde Elia s215811
 */

public class Checkpoint extends FieldAction {

    private int checkpointNo;

    public int getCheckpointNo() {
        return checkpointNo;
    }

    @Override
    public boolean doAction(GameController gameController, Space space) {
            Player currentPlayer = space.getPlayer();

            if (checkpointNo - 1 == currentPlayer.getNoCheckpointReached()) {
                currentPlayer.reachedCheckpoint();
            }
        return true;
    }
}
