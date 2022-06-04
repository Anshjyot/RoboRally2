package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.RoboRally;
import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadBoard;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AppController {
    final private List<Integer> PLAYER_NUMBER_OPTIONS = Arrays.asList(2, 3, 4, 5, 6);
    final private List<String> PLAYER_COLORS = Arrays.asList("red", "green", "blue", "orange", "grey", "magenta");

    private GameController gameController;

    RoboRally roboRally;

    public AppController(RoboRally roboRally) {
        this.roboRally = roboRally;
    }

    // TODO most methods missing here!

    //probably shouldn't be static.
    static void gameFinished(String winnerName){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(winnerName + " has reached all checkpoints and has WON!");
        alert.setTitle("Congratulations");
        alert.setContentText("Press OK to exit the game.");
        Optional<ButtonType> result = alert.showAndWait();
        System.exit(0);
    }

    public void exit() {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit RoboRally?");
            alert.setContentText("Are you sure you want to exit RoboRally?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isEmpty() || result.get() != ButtonType.OK) {
                return; // return without exiting the application
            }
            else{
                System.exit(0);
            }
    }



    public void newGame() {

        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(PLAYER_NUMBER_OPTIONS.get(0), PLAYER_NUMBER_OPTIONS);
        dialog.setTitle("Player number");
        dialog.setHeaderText("Select number of players");
        Optional<Integer> result = dialog.showAndWait();

        if (result.isPresent()) {
            if (gameController != null) {
                // The UI should not allow this, but in case this happens anyway.
                // give the user the option to save the game or abort this operation!
                if (!stopGame()) {
                    return;
                }
            }

            // XXX the board should eventually be created programmatically or loaded from a file
            //     here we just create an empty board with the required number of players.
            gameController = new GameController(LoadBoard.loadBoard("startercourse"));
            if(gameController.board==null){
                System.out.println("Cant load board");
            }
            int no = result.get();
            for (int i = 0; i < no; i++) {
                Player player = new Player(gameController.board, PLAYER_COLORS.get(i), "Player " + (i + 1));
                gameController.board.addPlayer(player);
                player.setSpace(gameController.board.getSpace(i % gameController.board.width, i));
            }

            // XXX: V2
            // board.setCurrentPlayer(board.getPlayer(0));
            gameController.startProgrammingPhase();

            roboRally.createBoardView(gameController);
        }
    }

    public boolean stopGame() {
        if (gameController != null) {

            // here we save the game (without asking the user).
            saveGame();

            gameController = null;
            roboRally.createBoardView(null);
            return true;
        }
        return false;
    }

    public void saveGame() {
    }

    public void loadGame() {
        // XXX needs to be implememted eventually
        // for now, we just create a new game
        if (gameController == null) {
            newGame();
        }
    }

    public boolean isGameRunning() {
        return gameController != null;
    }
}
