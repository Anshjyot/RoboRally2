package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Checkpoint extends FieldAction{

    private int checkpointNo;

    public void setCheckpointNo(int number){
        checkpointNo = number;
    }

    public int getCheckpointNo(){
        return checkpointNo;
    }

    @Override
    public boolean doAction(GameController gameController, Space space) {
        return false;
    }
}
