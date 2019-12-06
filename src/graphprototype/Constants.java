package graphprototype;

import java.awt.Color;

public class Constants {
    // Vertex/Node statistics
    public static int INITIAL_NUMBER_OF_NODES = 6;
    public static int NODE_PHYSICAL_WIDTH = 120;
    public static int NODE_PHYSICAL_HEIGHT = NODE_PHYSICAL_WIDTH;
    public static Color DEFAULT_NODE_COLOR = Color.WHITE;
    public static Color DEFAULT_NODE_BORDER_COLOR = Color.WHITE;
    public static int MINIMUM_NODE_VALUE = -4;
    public static int MAXIMUM_NODE_VALUE = 7;
    
    // Edge statistics
    public static int INITIAL_NUMBER_OF_EDGES = INITIAL_NUMBER_OF_NODES + 3;
    public static Color DEFUALT_EDGE_COLOR = Color.GRAY;
    
    // Game board statistics
    public static String GAME_WINDOW_TITLE = "The Dollar Game - Prototype 1.0.0.1";
    public static Color GAME_FIELD_BACKGROUND_COLOR = Color.BLACK;
    public static int GAME_FIELD_MAXIMUM_X = 700;
    public static int GAME_FIELD_MAXIMUM_Y = 700;
    public static int GAME_FIELD_MINIMUM_X = 0;
    public static int GAME_FIELD_MINIMUM_Y = 0;
    public static int GAME_FIELD_WIDTH = GAME_FIELD_MAXIMUM_X - GAME_FIELD_MINIMUM_X;
    public static int GAME_FIELD_HEIGHT = GAME_FIELD_MAXIMUM_Y - GAME_FIELD_MINIMUM_Y;
    
    public static int SPAWN_AREA_MARGIN = NODE_PHYSICAL_WIDTH;
    
    // Spawn area
    public static int SPAWN_AREA_MINIMUM_X = GAME_FIELD_MINIMUM_X + SPAWN_AREA_MARGIN;
    public static int SPAWN_AREA_MINIMUM_Y = GAME_FIELD_MINIMUM_Y + SPAWN_AREA_MARGIN;
    public static int SPAWN_AREA_MAXIMUM_X = GAME_FIELD_MAXIMUM_X - SPAWN_AREA_MARGIN;
    public static int SPAWN_AREA_MAXIMUM_Y = GAME_FIELD_MAXIMUM_Y - SPAWN_AREA_MARGIN;
    
    // Controls characteristics
    public static String BUTTON_INITIAL_TEXT = "NEW";
    
}
