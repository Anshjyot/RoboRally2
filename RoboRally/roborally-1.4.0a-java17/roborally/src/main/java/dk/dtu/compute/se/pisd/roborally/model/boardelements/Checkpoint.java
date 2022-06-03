package dk.dtu.compute.se.pisd.roborally.model.boardelements;

import dk.dtu.compute.se.pisd.roborally.controller.AppController;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.FieldAction;

public class Checkpoint extends FieldAction {

    private int checkpointNo;

    public void setCheckpointNo(int number) {
        checkpointNo = number;
    }

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
