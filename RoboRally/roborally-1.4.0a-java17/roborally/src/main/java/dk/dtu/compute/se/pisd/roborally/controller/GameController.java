/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.RESTfulAPI.Web;
import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.*;
import org.jetbrains.annotations.NotNull;


/**
 * ...
 *
 * @author Ekkart Kindler + Nick Tahmasebi
 */
public class GameController {

    public class ImpossibleMoveException extends Exception {
        private Player player;
        private Space space;
        private Heading heading;

        public ImpossibleMoveException(Player player, Space space, Heading heading) {
            this.player = player;
            this.space = space;
            this.heading = heading;
        }
    }

    final public Board board;

    public GameController(@NotNull Board board) {
        this.board = board;
    }

    //this method has been made as comment so it can be used, if we want to test
    //the game in the future.

    public void moveCurrentPlayerToSpace(@NotNull Space space)  {
        if (space != null && space.board == board) {
            Player currentPlayer = board.getCurrentPlayer();
            if (currentPlayer != null && space.getPlayer() == null) {
                currentPlayer.setSpace(space);
                int playerNumber = (board.getPlayerNumber(currentPlayer) + 1) % board.getPlayersNumber();
                board.setCurrentPlayer(board.getPlayer(playerNumber));
            }
        }
    }

    // XXX: V2
    public void startProgrammingPhase() {
        board.setPhase(Phase.PROGRAMMING);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            player.setRebooting(false);
            if (player != null) {
                for (int j = 0; j < Player.NO_REGISTERS; j++) {
                    CommandCardField field = player.getProgramField(j);
                    field.setCard(null);
                    field.setVisible(true);
                }
                for (int j = 0; j < Player.NO_CARDS; j++) {
                    CommandCardField field = player.getCardField(j);
                    if (!player.getDamagecards().isEmpty() && player.getDamagecards().size() > j) {
                        field.setCard(new CommandCard(player.getDamagecards().get(j)));
                    } else {
                        field.setCard(generateRandomCommandCard());
                    }
                    field.setVisible(true);
                }
            }
        }
    }

    // XXX: V2
    private CommandCard generateRandomCommandCard() {
        //damagecards skal ikke være med her
        Command[] commands = Command.values();
        int random = (int) (Math.random() * (commands.length - 4));
        return new CommandCard(commands[random]);
    }

    // XXX: V2
    public void finishProgrammingPhase() {
        makeProgramFieldsInvisible();
        makeProgramFieldsVisible(0);
        board.setPhase(Phase.ACTIVATION);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);
    }

    // XXX: V2
    private void makeProgramFieldsVisible(int register) {
        if (register >= 0 && register < Player.NO_REGISTERS) {
            for (int i = 0; i < board.getPlayersNumber(); i++) {
                Player player = board.getPlayer(i);
                CommandCardField field = player.getProgramField(register);
                field.setVisible(true);
            }
        }
    }

    // XXX: V2
    private void makeProgramFieldsInvisible() {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            for (int j = 0; j < Player.NO_REGISTERS; j++) {
                CommandCardField field = player.getProgramField(j);
                field.setVisible(false);
            }
        }
    }

    // XXX: V2
    public void executePrograms() {
        board.setStepMode(false);
        continuePrograms();
    }

    // XXX: V2
    public void executeStep() {
        board.setStepMode(true);
        continuePrograms();
    }

    // XXX: V2
    private void continuePrograms() {
        do {
            executeNextStep();
        } while (board.getPhase() == Phase.ACTIVATION && !board.isStepMode());
    }

    // XXX: V2
    private void executeNextStep() {
        Player currentPlayer = board.getCurrentPlayer();
        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null) {
            int step = board.getStep();
            if (step >= 0 && step < Player.NO_REGISTERS) {
                CommandCard card = currentPlayer.getProgramField(step).getCard();
                if (card != null) {
                    Command command = card.command;
                    executeCommand(currentPlayer, command);
                    Web web = new Web();
                    web.transferBoard(board, "test");
                }
                int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
                if (nextPlayerNumber < board.getPlayersNumber()) {
                    board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
                } else {
                    //after each register, board-elements activate.
                    activateFieldAction();
                    winnerCheck();
                    step++;
                    if (step < Player.NO_REGISTERS) {
                        makeProgramFieldsVisible(step);
                        board.setStep(step);
                        board.setCurrentPlayer(board.getPlayer(0));
                    } else {
                        startProgrammingPhase();
                    }
                }
            } else {
                // this should not happen
                assert false;
            }
        } else {
            // this should not happen
            assert false;
        }
    }

    private void activateFieldAction() {

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player currentPlayer = board.getPlayer(i);
            Space currentSpace = currentPlayer.getSpace();
            for (FieldAction fa : currentSpace.getActions()) {
                if (fa instanceof ConveyorBelt) {
                    fa.doAction(this, currentSpace);
                }
            }
        }
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player currentPlayer = board.getPlayer(i);
            Space currentSpace = currentPlayer.getSpace();
            for (FieldAction fa : currentSpace.getActions()) {
                if (fa instanceof PushPanels) {
                    fa.doAction(this, currentSpace);
                }
            }
        }
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player currentPlayer = board.getPlayer(i);
            Space currentSpace = currentPlayer.getSpace();
            for (FieldAction fa : currentSpace.getActions()) {
                if (fa instanceof Gear) {
                    fa.doAction(this, currentSpace);
                }
            }
        }
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player currentPlayer = board.getPlayer(i);
            Space currentSpace = currentPlayer.getSpace();
            for (FieldAction fa : currentSpace.getActions()) {
                if (fa instanceof Laser) {
                    fa.doAction(this, currentSpace);
                }
            }
        }
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player currentPlayer = board.getPlayer(i);
            Space currentSpace = currentPlayer.getSpace();
            for (FieldAction fa : currentSpace.getActions()) {
                if (fa instanceof Pit) {
                    moveToRebootToken(currentPlayer);
                }
            }
        }
        //der må være en bedre måde at gøre det på, kigger på det senere :)
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Space currentSpace = board.getPlayer(i).getSpace();
            for (FieldAction fa : currentSpace.getActions()) {
                if (fa instanceof Checkpoint) {
                    fa.doAction(this, currentSpace);
                }
            }
        }
    }

    private void reboot(Space rebootSpace) {
        for (FieldAction fa : rebootSpace.getActions()) {
            if (fa instanceof Reboot) {
                fa.doAction(this, rebootSpace);
            }
        }
    }

    private void moveToRebootToken(Player player) {
        player.setRebooting(true);
        Space rebootSpace = board.getSpace(board.getRebootXY()[0], board.getRebootXY()[1]);
        if (rebootSpace.getPlayer() != null) {
            Space target = board.getNeighbour(rebootSpace, rebootSpace.getPlayer().getHeading());
            try {
                moveToSpace(rebootSpace.getPlayer(), target, rebootSpace.getPlayer().getHeading());
            } catch (ImpossibleMoveException e) {
                e.printStackTrace();
            }
        }
        player.setSpace(rebootSpace);
        reboot(rebootSpace);
        board.resetOutOfBoard();
    }

    // XXX: V2
    private void executeCommand(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {
            // XXX This is a very simplistic way of dealing with some basic cards and
            //     their execution. This should eventually be done in a more elegant way
            //     (this concerns the way cards are modelled as well as the way they are executed).

            switch (command) {
                case FORWARD:
                    this.moveForward(player);
                    break;
                case RIGHT:
                    this.turnRight(player);
                    break;
                case LEFT:
                    this.turnLeft(player);
                    break;
                case DOUBLE_FORWARD:
                    this.doubleForward(player);
                    break;
                case TRIPLE_FORWARD:
                    this.tripleForward(player);
                    break;
                case U_TURN:
                    this.uTurn(player);
                    break;
                case MOVE_BACK:
                    this.moveBack(player);
                    break;
                case SPAM:
                    this.spam(player);
                    break;
                case TROJANHORSE:
                    this.trojanhorse(player);
                    break;
                case WORM:
                    this.worm(player);
                    break;
                    /*
                case VIRUS:
                    this.virus(player);
                    break; */


                default:
                    // DO NOTHING (for now)
            }
        }
    }

    public void moveForward(@NotNull Player player) {
        Space space = player.getSpace();
        if (player != null && player.board == board && space != null && !player.isRebooting()) {
            Heading heading = player.getHeading();
            Space target = board.getNeighbour(space, heading);
            if (target != null) {
                try {
                    moveToSpace(player, target, heading);
                } catch (ImpossibleMoveException e) {

                }
                //target.setPlayer(player);
            }
            //if player falls out of the board, they should reboot.
            if (board.isOutOfBoard()) {
                moveToRebootToken(player);
            }
        }
    }

    public void moveToSpace(@NotNull Player player, @NotNull Space space, @NotNull Heading heading) throws ImpossibleMoveException {
        assert board.getNeighbour(player.getSpace(), heading) == space; // valid move or not
        Player other = space.getPlayer();
        if (other != null) {
            Space target = board.getNeighbour(space, heading);
            if (target != null) {
                moveToSpace(other, target, heading);

                assert target.getPlayer() == null : target;
            } else {
                throw new ImpossibleMoveException(player, space, heading);
            }
        }
        player.setSpace(space);
    }

    // Move forward twice in current facing direction
    public void doubleForward(@NotNull Player player) {
        moveForward(player);
        moveForward(player);
    }

    // Move forward 3 times in current facing direction
    public void tripleForward(@NotNull Player player) {
        moveForward(player);
        moveForward(player);
        moveForward(player);
    }

    // TODO: V2
    public void turnRight(@NotNull Player player) {
        if (player != null && player.board == board) {
            player.setHeading(player.getHeading().next());
        }
    }

    // TODO: V2
    public void turnLeft(@NotNull Player player) {
        if (player != null && player.board == board) {
            player.setHeading(player.getHeading().prev());
        }
    }

    // uTurn using Lambda expressions
    public void uTurn(@NotNull Player player) {
        switch (player.getHeading()) {
            case DOWN -> player.setHeading(Heading.UP);
            case RIGHT -> player.setHeading(Heading.LEFT);
            case LEFT -> player.setHeading(Heading.RIGHT);
            case UP -> player.setHeading(Heading.DOWN);
        }
    }

    // Player rotates, then moves backwards, then rotates to ensure facing direction is correct
    public void moveBack(@NotNull Player player) {
        uTurn(player);
        moveForward(player);
        uTurn(player);
    }

    public boolean moveCards(@NotNull CommandCardField source, @NotNull CommandCardField target) {
        CommandCard sourceCard = source.getCard();
        CommandCard targetCard = target.getCard();
        if (sourceCard != null && targetCard == null) {
            target.setCard(sourceCard);
            source.setCard(null);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @author Anshjyot Singh S215806
     * spam() is used when a player hits the lasers
     * The number of spam cards that is given depends on the number of lasers hit
     */
    public void spam(Player player) {
        player.getDamagecards().remove(Command.SPAM);
        CommandCard card = generateRandomCommandCard();

        executeCommand(player, card.command);
    }

    public void trojanhorse(Player player) {
        player.setDamagecards(Command.SPAM);
        player.setDamagecards(Command.SPAM);
    }

    public void worm(Player player) {
        player.getDamagecards().remove(Command.WORM);
        moveToRebootToken(player);
    }

    /**
     * A method called when no corresponding controller operation is implemented yet. This
     * should eventually be removed.
     */
    public void notImplemented() {
        // XXX just for now to indicate that the actual method is not yet implemented
        assert false;
    }

    /**
     * @author Mathilde Elia S215811
     * winnerCheck() is used when each register has ended.
     * If a player has reached all checkpoint on the board, then the game finishes.
     */

    public void winnerCheck() {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player currentPlayer = board.getPlayer(i);

            if (currentPlayer.getNoCheckpointReached() >= board.getNoOfCheckpoints()) {
                //there is a winner!
                AppController.gameFinished(currentPlayer.getName());
            }
        }
    }
}
