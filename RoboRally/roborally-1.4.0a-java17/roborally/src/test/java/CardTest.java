import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static java.awt.Color.RED;
/**
 *  @author Anshjyot Singh, s215806
 *  tests the different cards available in the RoboRally game
 */

public class CardTest {
    private final int SPACE_WIDTH = 10;
    private final int SPACE_HEIGHT = 10;

    private GameController gameController;
    private Player player;
    private Board board;

    /**
     *  @author Anshjyot Singh, s215806
     *  init() must be called before each of the tests that I will run
     *  Initialises objects
     */
    @BeforeEach
    void init() {
        board = new Board(SPACE_WIDTH, SPACE_HEIGHT);
        gameController = new GameController(board);
        player = new Player(board, null, 1, "Player" + 1);
        board.addPlayer(player);
        player.setSpace(board.getSpace(0, 0));
        player.setHeading(Heading.DOWN);
    }

    /**
     *  @author Anshjyot Singh, s215806
     *  terminate()  must be called after each of these tests.
     *  sets gameController = null;
     */
    @AfterEach
    void terminate() {
        gameController = null;
    }

    /**
     *  @author Anshjyot Singh, s215806
     *  the moveForward() test, determines whether if the player moves 1 space down
     */
    @Test
    void moveForward() {
        board = gameController.board;
        gameController.moveForward(player);

        Assertions.assertEquals(player, board.getSpace(0, 1).getPlayer(), "");
        Assertions.assertEquals(Heading.DOWN, player.getHeading(), "PLAYER NEED TO GO 1 SPACE DOWN, FIX IT!");
        Assertions.assertNull(board.getSpace(0, 0).getPlayer(), "");
    }

    /**
     *  @author Anshjyot Singh, s215806
     *  the doubleForward() test, determines whether if the player moves 2 spaces down
     */
    @Test
    void doubleForward() {
        board = gameController.board;
        gameController.doubleForward(player);

        Assertions.assertEquals(player, board.getSpace(0, 2).getPlayer(), "");
        Assertions.assertEquals(Heading.DOWN, player.getHeading(), "PLAYER NEED TO GO 2 SPACES DOWN, FIX IT!");
        Assertions.assertNull(board.getSpace(0, 0).getPlayer(), "");
    }

    /**
     *  @author Anshjyot Singh, s215806
     *  the tripleForward() test, determines whether if the player moves 3 space down
     */
    @Test
    void tripleForward() {
        board = gameController.board;
        gameController.tripleForward(player);

        Assertions.assertEquals(player, board.getSpace(0, 3).getPlayer(), "");
        Assertions.assertEquals(Heading.DOWN, player.getHeading(), "PLAYER NEED TO GO 3 SPACES DOWN, FIX IT!");
        Assertions.assertNull(board.getSpace(0, 0).getPlayer(), "");
    }

    /**
     *  @author Anshjyot Singh, s215806
     *  the turnRight() test, determines if the player header has turned right
     */
    @Test
    void turnRight() {
        Board board = gameController.board;
        gameController.turnRight(player);

        Assertions.assertEquals(player, board.getSpace(0, 0).getPlayer(), "");
        Assertions.assertEquals(Heading.LEFT, player.getHeading(), "Player header should be RIGHT!, FIX IT");
        Assertions.assertNotNull(board.getSpace(0, 0).getPlayer(), "");
    }
    /**
     *  @author Anshjyot Singh, s215806
     *  the turnLeft() test, determines if the player header has turned left
     */
    @Test
    void turnLeft() {
        Board board = gameController.board;
        gameController.turnLeft(player);

        Assertions.assertEquals(player, board.getSpace(0, 0).getPlayer(), "");
        Assertions.assertEquals(Heading.RIGHT, player.getHeading(), "Player header should be LEFT!, FIX IT");
        Assertions.assertNotNull(board.getSpace(0, 0).getPlayer(), "");


    }
    /**
     *  @author Anshjyot Singh, s215806
     *  the uTurn() test, determines if the player header has turned up
     */
    @Test
    void uTurn() {
        Board board = gameController.board;
        gameController.uTurn(player);

        Assertions.assertEquals(player, board.getSpace(0, 0).getPlayer(), "");
        Assertions.assertEquals(Heading.UP, player.getHeading(), "Player header should be UP!, FIX IT");
        Assertions.assertNotNull(board.getSpace(0, 0).getPlayer(), "");
    }
}
