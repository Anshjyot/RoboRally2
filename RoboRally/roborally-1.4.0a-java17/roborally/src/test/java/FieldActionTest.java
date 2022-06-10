import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static java.awt.Color.RED;

public class FieldActionTest {
    private final int SPACE_WIDTH = 10;
    private final int SPACE_HEIGHT = 10;

    private GameController gameController;
    private Player player;
    private Board board;
    private ArrayList cardRegister;

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
    void terminate() {gameController = null;}


    /**
     *  @author Anshjyot Singh, s215806
     *  the damageCards() test, determines whether the player has gotten a SPAM-card when using the setDamagecards.
     */
    @Test
    void damageCards() {
        board = gameController.board;
        player.setDamagecards(Command.SPAM);
        cardRegister = new ArrayList<>(player.getDamagecards());
        Command playerCommand = (Command) cardRegister.get(0);
        Assertions.assertEquals(1, cardRegister.size(),"");
        Assertions.assertEquals(Command.SPAM, playerCommand , "");
    }


}
