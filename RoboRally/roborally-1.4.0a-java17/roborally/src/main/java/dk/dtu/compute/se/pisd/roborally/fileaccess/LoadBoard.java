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
package dk.dtu.compute.se.pisd.roborally.fileaccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dk.dtu.compute.se.pisd.roborally.fileaccess.model.BoardTemplate;
import dk.dtu.compute.se.pisd.roborally.fileaccess.model.SpaceTemplate;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.model.boardelements.Reboot;

import java.io.*;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class LoadBoard {

    private static final String BOARDSFOLDER = "boards";
    private static final String SAVEDBOARDSFOLDER = "saved";

    private static final String DEFAULTBOARD = "defaultboard";
    private static final String JSON_EXT = "json";

    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;
    private static int startpointCount = 0;

    /**
     * Loads a json board given it's name from recources.
     * boolean isSaved is used to identify wheter loadboard
     * should load a newGame or a saved file.
     * @author Mathilde Elia s215811
     */
    public static Board loadBoard(String boardname, boolean isSaved) {
        if (boardname == null) {
            boardname = DEFAULTBOARD;
        }
        InputStream inputStream;
        ClassLoader classLoader = LoadBoard.class.getClassLoader();
        if (!isSaved) {
            inputStream = classLoader.getResourceAsStream(BOARDSFOLDER + "/" + boardname + "." + JSON_EXT);
            if (inputStream == null) {
                return new Board(WIDTH, HEIGHT);
            }
        } else {
            inputStream = classLoader.getResourceAsStream(SAVEDBOARDSFOLDER + "/" + boardname + "." + JSON_EXT);
            if (inputStream == null) {
                return new Board(WIDTH, HEIGHT);
            }
        }

        // In simple cases, we can create a Gson object with new Gson():
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = simpleBuilder.create();

        Board result;
        // FileReader fileReader = null;
        JsonReader reader = null;
        try {
            // fileReader = new FileReader(filename);
            reader = gson.newJsonReader(new InputStreamReader(inputStream));
            BoardTemplate template = gson.fromJson(reader, BoardTemplate.class);
            result = new Board(template.width, template.height);
            result.setNoOfCheckpoints(template.noOfCheckpoints);
            result.setStep(template.step);

            //indsæt result.boardname = boardname hvis det bliver relevant
            for (SpaceTemplate spaceTemplate : template.spaces) {
                Space space = result.getSpace(spaceTemplate.x, spaceTemplate.y);
                if (space != null) {
                    space.setStartPoint(spaceTemplate.startPoint);
                    space.getActions().addAll(spaceTemplate.actions);
                    if (!space.getActions().isEmpty() && space.getActions().get(0) instanceof Reboot) {
                        result.setRebootXY(space.x, space.y);
                    }
                    space.getWalls().addAll(spaceTemplate.walls);
                    if (spaceTemplate.playerNo != 0) {
                        int playerNo = spaceTemplate.playerNo;
                        result.setPositions(space, playerNo - 1);
                        result.setCheckpoints(template.checkpointsReached[playerNo - 1], playerNo - 1);
                    }
                    if (space.getStartPoint()) {
                        result.setStartpoints(space, startpointCount);
                        startpointCount++;
                    }
                }
            }
            reader.close();
            startpointCount = 0;
            return result;
        } catch (IOException e1) {
            if (reader != null) {
                try {
                    reader.close();
                    inputStream = null;
                } catch (IOException e2) {
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                }
            }
        }
        return null;
    }

    public static Board loadBoardFromJson(String json) {
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>()).setPrettyPrinting();
        Gson gson = simpleBuilder.create();

        JsonReader reader = gson.newJsonReader(new StringReader(json));
        BoardTemplate template = gson.fromJson(reader, BoardTemplate.class);
        Board result;

        result = new Board(template.width, template.height);
        result.setNoOfCheckpoints(template.noOfCheckpoints);
        result.setStep(template.step);

        //indsæt result.boardname = boardname hvis det bliver relevant
        for (SpaceTemplate spaceTemplate : template.spaces) {
            Space space = result.getSpace(spaceTemplate.x, spaceTemplate.y);
            if (space != null) {
                space.setStartPoint(spaceTemplate.startPoint);
                space.getActions().addAll(spaceTemplate.actions);
                if (!space.getActions().isEmpty() && space.getActions().get(0) instanceof Reboot) {
                    result.setRebootXY(space.x, space.y);
                }
                space.getWalls().addAll(spaceTemplate.walls);
                if (spaceTemplate.playerNo != 0) {
                    int playerNo = spaceTemplate.playerNo;
                    result.setPositions(space, playerNo - 1);
                    result.setCheckpoints(template.checkpointsReached[playerNo - 1], playerNo - 1);
                }
                if (space.getStartPoint()) {
                    result.setStartpoints(space, startpointCount);
                    startpointCount++;
                }

            }
        }
        startpointCount = 0;
        return result;
    }

    /**
     * Saves a board into a json file as the name given into
     * resource folder "saved". Currently saves board with player
     * positions and the number of checkpoints reched.
     * @author Mathilde Elia s215811
     */
    public static void saveBoard(Board board, String name) {
        BoardTemplate template = new BoardTemplate();
        template.width = board.width;
        template.height = board.height;
        template.step = board.getStep();
        template.noOfCheckpoints = board.getNoOfCheckpoints();

        for (int i = 0; i < board.width; i++) {
            for (int j = 0; j < board.height; j++) {
                Space space = board.getSpace(i, j);

                if (!space.getWalls().isEmpty() || !space.getActions().isEmpty()
                        || space.getStartPoint() || space.getPlayer() != null) {
                    SpaceTemplate spaceTemplate = new SpaceTemplate();
                    spaceTemplate.startPoint = space.startPoint;
                    spaceTemplate.x = space.x;
                    spaceTemplate.y = space.y;
                    spaceTemplate.actions.addAll(space.getActions());
                    spaceTemplate.walls.addAll(space.getWalls());
                    if (space.getPlayer() != null) {
                        spaceTemplate.playerNo = space.getPlayer().getRobot();
                        template.positions[spaceTemplate.playerNo - 1] = spaceTemplate;
                        template.checkpointsReached[spaceTemplate.playerNo - 1] = space.getPlayer().getNoCheckpointReached();
                    }

                    template.spaces.add(spaceTemplate);
                }
            }
        }

        String filename =
                "RoboRally/roborally-1.4.0a-java17/roborally/src/main/resources/saved/" + name + "." + JSON_EXT;

        // In simple cases, we can create a Gson object with new:
        //
        //   Gson gson = new Gson();
        //
        // But, if you need to configure it, it is better to create it from
        // a builder (here, we want to configure the JSON serialisation with
        // a pretty printer):
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>()).
                setPrettyPrinting();
        Gson gson = simpleBuilder.create();

        FileWriter fileWriter = null;
        JsonWriter writer = null;
        try {
            fileWriter = new FileWriter(filename);
            writer = gson.newJsonWriter(fileWriter);
            gson.toJson(template, template.getClass(), writer);
            writer.close();
        } catch (IOException e1) {
            if (writer != null) {
                try {
                    writer.close();
                    fileWriter = null;
                } catch (IOException e2) {
                }
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e2) {
                }
            }
        }
    }
    /**
     * Saves a board to json as a string.
     * This is used to send to server.
     * @author Mathilde Elia s215811
     */

    public static String saveBoardToJson(Board board) {
        BoardTemplate template = new BoardTemplate();
        template.width = board.width;
        template.height = board.height;
        template.step = board.getStep();
        template.noOfCheckpoints = board.getNoOfCheckpoints();

        for (int i = 0; i < board.width; i++) {
            for (int j = 0; j < board.height; j++) {
                Space space = board.getSpace(i, j);

                if (!space.getWalls().isEmpty() || !space.getActions().isEmpty()
                        || space.getStartPoint() || space.getPlayer() != null) {
                    SpaceTemplate spaceTemplate = new SpaceTemplate();
                    spaceTemplate.startPoint = space.startPoint;
                    spaceTemplate.x = space.x;
                    spaceTemplate.y = space.y;
                    spaceTemplate.actions.addAll(space.getActions());
                    spaceTemplate.walls.addAll(space.getWalls());
                    if (space.getPlayer() != null) {
                        spaceTemplate.playerNo = space.getPlayer().getRobot();
                        template.positions[spaceTemplate.playerNo - 1] = spaceTemplate;
                        template.checkpointsReached[spaceTemplate.playerNo - 1] = space.getPlayer().getNoCheckpointReached();
                    }

                    template.spaces.add(spaceTemplate);
                }
            }
        }
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>()).
                setPrettyPrinting();

        Gson gson = simpleBuilder.create();
        return gson.toJson(template);
    }

}
