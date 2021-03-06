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
package dk.dtu.compute.se.pisd.roborally.model.boardelements;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.FieldAction;
import org.jetbrains.annotations.NotNull;

/**
 * @author GreenConveyerBelt Mathilde Elia, s215811@dtu.dk & BlueConveyerBelt and Speed Marco Miljkov Hansen, s194302@dtu.dk
 * This is the code-logic regarding a field-action board-element.
 * Conveyor-belt moves the player one or two spaces depending on the type of conveyor-belt
 */
public class ConveyorBelt extends FieldAction {

    private Heading heading;
    private int speed;

    public int getSpeed() {
        return speed;
    }

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    //public ConveyorBelt (Heading heading, )

    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        Space target = gameController.board.getNeighbour(space, this.heading);

        if (target != null) {
            switch (speed) {
                case 1:
                    try {
                        gameController.moveToSpace(space.getPlayer(), target, heading);
                    } catch (GameController.ImpossibleMoveException e) {

                    }
                    break;
                case 2:
                    if (target.getActions().get(0) instanceof ConveyorBelt) {
                        Heading targetHeading = ((ConveyorBelt) target.getActions().get(0)).getHeading();

                        Space target2 = gameController.board.getNeighbour(target, targetHeading);
                        if (target2 != null) {
                            try {
                                gameController.moveToSpace(space.getPlayer(), target2, targetHeading);
                            } catch (GameController.ImpossibleMoveException e) {

                            }
                        }
                    } else
                        try {
                            gameController.moveToSpace(space.getPlayer(), target, heading);
                        } catch (GameController.ImpossibleMoveException e) {

                        }
                    break;
            }
        }
        return true;
    }
}
