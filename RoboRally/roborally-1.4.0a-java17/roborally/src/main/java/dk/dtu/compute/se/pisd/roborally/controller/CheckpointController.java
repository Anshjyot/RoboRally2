package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class CheckpointController extends FieldAction {

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
            if (currentPlayer.getNoCheckpointReached() >= gameController.board.getNoOfCheckpoints()) {
                //there is a winner!
                AppController.gameFinished(currentPlayer.getName());
            }
        return true;
    }
}
