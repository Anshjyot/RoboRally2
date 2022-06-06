package dk.dtu.compute.se.pisd.roborally.model.boardelements;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class PushPanels extends FieldAction {
    private Heading heading;
    public Heading getHeading(){
        return heading;
    }

    @Override
    public boolean doAction(GameController gameController, Space space) {
        Heading push = this.heading;
        Space neighbour = gameController.board.getNeighbour(space, push);
        try {
            gameController.moveToSpace(space.getPlayer(), neighbour, push);
            return true;
        }
        catch(Exception e){}

        return false;
    }
}
