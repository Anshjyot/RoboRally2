package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.RoboRally;
import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadBoard;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class AppController {
    final private List<Integer> PLAYER_NUMBER_OPTIONS = Arrays.asList(2, 3, 4, 5, 6);
    final private List<String> PLAYER_COLORS = Arrays.asList("blue", "orange", "green", "magenta", "cyan", "pink");
    final private List<Integer> PLAYER_ROBOT = Arrays.asList(1,2,3,4,5,6);
    final private List<String> PLAYER_NAMES = Arrays.asList("blueToaster", "orangeStomper", "greenBulldozer", "magentaOister", "cyanUFO", "pinkNigiri");

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
                return; // return without exiting the application.
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

            ChoiceDialog<String> choice = new ChoiceDialog<>("defaultboard","defaultboard","startercourse","lovecourse","advanced");
            dialog.setTitle("RoboRally Course");
            dialog.setHeaderText("Select the course you want to play on");
            Optional<String> boardChoice = choice.showAndWait();

            if(boardChoice.isPresent()) {
                String boardName = boardChoice.get();

                // XXX the board should eventually be created programmatically or loaded from a file
                //     here we just create an empty board with the required number of players.
                gameController = new GameController(LoadBoard.loadBoard(boardName));
                if (gameController.board == null) {
                    System.out.println("Cant load board");
                }
                int no = result.get();
                for (int i = 0; i < no; i++) {
                    Player player = new Player(gameController.board, PLAYER_COLORS.get(i), PLAYER_ROBOT.get(i),PLAYER_NAMES.get(i));
                    gameController.board.addPlayer(player);
                    player.setSpace(gameController.board.getStartpoints()[i]);
                }

                // XXX: V2
                // board.setCurrentPlayer(board.getPlayer(0));
                gameController.startProgrammingPhase();

                roboRally.createBoardView(gameController);
            }
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
        TextInputDialog saveName = new TextInputDialog("");
        saveName.setHeaderText("Type name for your saved game");
        saveName.setContentText("Enter name");
        saveName.setTitle("Save Game");

        Optional<String> input = saveName.showAndWait();

        if(input.isPresent()) {
            String saveFileName = saveName.getResult();
            LoadBoard.saveBoard(gameController.board, saveFileName);
        }
    }

    public void loadGame() {
        ChoiceDialog<String> choice = new ChoiceDialog<>("Saved game","Saved Game");
        choice.setTitle("RoboRally Course");
        choice.setHeaderText("Select the course you want to play on");
        Optional<String> boardChoice = choice.showAndWait();

        if(boardChoice.isPresent()) {
            String boardName = boardChoice.get();

            // XXX the board should eventually be created programmatically or loaded from a file
            //     here we just create an empty board with the required number of players.
            gameController = new GameController(LoadBoard.loadBoard(boardName));
            if (gameController.board == null) {
                System.out.println("Cant load board");
            }
            int no = gameController.board.getPositions().length;
            for (int i = 0; i < no; i++) {
                Player player = new Player(gameController.board, PLAYER_COLORS.get(i), PLAYER_ROBOT.get(i),PLAYER_NAMES.get(i));
                gameController.board.addPlayer(player);
                player.setSpace(gameController.board.getPositions()[i]);
            }

            // XXX: V2
            // board.setCurrentPlayer(board.getPlayer(0));
            gameController.startProgrammingPhase();

            roboRally.createBoardView(gameController);
        }
        // XXX needs to be implememted eventually
        // for now, we just create a new game
        /*if (gameController == null) {
            newGame();
        } */
    }

    public boolean isGameRunning() {
        return gameController != null;
    }
}
