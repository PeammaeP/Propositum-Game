package Project3_136.GameMechanism;

interface TheGameConstants {
        //SCREEN SETTINGS PART , using in The2DGamePanel class
        int ORIGIN_TILE_SIZE = 16; // 16 x 16 bits tile
        int CHARACTER_SCALE = 3;
        int GAME_TILE_SIZE = ORIGIN_TILE_SIZE * CHARACTER_SCALE; // 48 x 48 tile
        int maxMap = 10;
        int MAX_SCREEN_COLUMN = 21, MAX_SCREEN_ROW = 15;
        int MAX_WORLD_COLUMN = 50, MAX_WORLD_ROW = 50;
        int SCREEN_WIDTH = GAME_TILE_SIZE * MAX_SCREEN_COLUMN; // 1,008 pixels
        int SCREEN_HEIGHT = GAME_TILE_SIZE * MAX_SCREEN_ROW; // 720 pixels
        // total resolution is 768 x 576
        // new resolution is 1,008 x 720
        //full screen settings
        int GAME_FPS = 60;
}

interface PlayerSkinRed {
     String PATH_PLAYER_WALK = "src/main/java/Project3_136/resources/Player/RedWalkingSprites/";
     String PATH_PLAYER_WALK_UP1 = PATH_PLAYER_WALK + "boy_up_1.png";
     String PATH_PLAYER_WALK_UP2 = PATH_PLAYER_WALK + "boy_up_2.png";
     String PATH_PLAYER_WALK_DOWN1 = PATH_PLAYER_WALK + "boy_down_1.png";
     String PATH_PLAYER_WALK_DOWN2 = PATH_PLAYER_WALK + "boy_down_2.png";
     String PATH_PLAYER_WALK_LEFT1 = PATH_PLAYER_WALK + "boy_left_1.png";
     String PATH_PLAYER_WALK_LEFT2 = PATH_PLAYER_WALK + "boy_left_2.png";
     String PATH_PLAYER_WALK_RIGHT1 = PATH_PLAYER_WALK + "boy_right_1.png";
     String PATH_PLAYER_WALK_RIGHT2 = PATH_PLAYER_WALK + "boy_right_2.png";
}
interface PlayerSkinGreen {
    String PATH_PLAYER_WALK = "src/main/java/Project3_136/resources/Player/GreenWalkingSprites/";
    String PATH_PLAYER_WALK_UP1 = PATH_PLAYER_WALK + "boy_up_1.png";
    String PATH_PLAYER_WALK_UP2 = PATH_PLAYER_WALK + "boy_up_2.png";
    String PATH_PLAYER_WALK_DOWN1 = PATH_PLAYER_WALK + "boy_down_1.png";
    String PATH_PLAYER_WALK_DOWN2 = PATH_PLAYER_WALK + "boy_down_2.png";
    String PATH_PLAYER_WALK_LEFT1 = PATH_PLAYER_WALK + "boy_left_1.png";
    String PATH_PLAYER_WALK_LEFT2 = PATH_PLAYER_WALK + "boy_left_2.png";
    String PATH_PLAYER_WALK_RIGHT1 = PATH_PLAYER_WALK + "boy_right_1.png";
    String PATH_PLAYER_WALK_RIGHT2 = PATH_PLAYER_WALK + "boy_right_2.png";
}

interface PlayerSkinBlue {
    String PATH_PLAYER_WALK = "src/main/java/Project3_136/resources/Player/BlueWalkingSprites/";
    String PATH_PLAYER_WALK_UP1 = PATH_PLAYER_WALK + "boy_up_1.png";
    String PATH_PLAYER_WALK_UP2 = PATH_PLAYER_WALK + "boy_up_2.png";
    String PATH_PLAYER_WALK_DOWN1 = PATH_PLAYER_WALK + "boy_down_1.png";
    String PATH_PLAYER_WALK_DOWN2 = PATH_PLAYER_WALK + "boy_down_2.png";
    String PATH_PLAYER_WALK_LEFT1 = PATH_PLAYER_WALK + "boy_left_1.png";
    String PATH_PLAYER_WALK_LEFT2 = PATH_PLAYER_WALK + "boy_left_2.png";
    String PATH_PLAYER_WALK_RIGHT1 = PATH_PLAYER_WALK + "boy_right_1.png";
    String PATH_PLAYER_WALK_RIGHT2 = PATH_PLAYER_WALK + "boy_right_2.png";
}
interface GameMonster {

    String PATH_MONS = "src/main/java/Project3_136/resources/Monster/";
    String SLIME_DOWN_1 = PATH_MONS + "greenslime_down_1.png";
    String SLIME_DOWN_2 = PATH_MONS + "greenslime_down_2.png";
    String ORC_UP_1 = PATH_MONS + "orc_up_1.png";
    String ORC_UP_2 = PATH_MONS + "orc_up_2.png";
    String ORC_DOWN_1 = PATH_MONS + "orc_down_1.png";
    String ORC_DOWN_2 = PATH_MONS + "orc_down_2.png";
    String ORC_LEFT_1 = PATH_MONS + "orc_left_1.png";
    String ORC_LEFT_2 = PATH_MONS + "orc_left_2.png";
    String ORC_RIGHT_1 = PATH_MONS + "orc_right_1.png";
    String ORC_RIGHT_2 = PATH_MONS + "orc_right_2.png";
    String KIRBY_UP_1 = PATH_MONS + "kirby.png";
    String KIRBY_UP_2 = PATH_MONS + "kirby_krajone.png";
    String KIRBY_DOWN_1 = PATH_MONS + "kirby.png";
    String KIRBY_DOWN_2 = PATH_MONS + "kirby_krajone.png";
    String KIRBY_LEFT_1 = PATH_MONS + "kirby.png";
    String KIRBY_LEFT_2 = PATH_MONS + "kirby_krajone.png";
    String KIRBY_RIGHT_1 = PATH_MONS + "kirby.png";
    String KIRBY_RIGHT_2 = PATH_MONS + "kirby_krajone.png";
}

interface GameInterface {
     String PATH_UI = "src/main/java/Project3_136/resources/InterfaceGame/TitleScreen/";
     String GAME_ICON = PATH_UI + "SmallAstronaut_Idle.png";
     String GAME_BACKGROUND = PATH_UI + "background.png";
}

interface TheGameTiles {
     String PATH_TILES = "src/main/java/Project3_136/resources/Tiles/";
     String GRASS_00 = PATH_TILES + "grass00.png";
     String GRASS_01 = PATH_TILES + "grass01.png";
     String WALL = PATH_TILES + "wall.png";
     String TREE = PATH_TILES + "tree.png";
     String EARTH = PATH_TILES + "earth.png";
     String HUT = PATH_TILES + "hut.png";
     String WATER_00 = PATH_TILES + "water00.png";
     String WATER_01 = PATH_TILES + "water01.png";
     String WATER_02 = PATH_TILES + "water02.png";
     String WATER_03 = PATH_TILES + "water03.png";
     String WATER_04 = PATH_TILES + "water04.png";
     String WATER_05 = PATH_TILES + "water05.png";
     String WATER_06 = PATH_TILES + "water06.png";
     String WATER_07 = PATH_TILES + "water07.png";
     String WATER_08 = PATH_TILES + "water08.png";
     String WATER_09 = PATH_TILES + "water09.png";
     String WATER_10 = PATH_TILES + "water10.png";
     String WATER_11 = PATH_TILES + "water11.png";
     String WATER_12 = PATH_TILES + "water12.png";
     String WATER_13 = PATH_TILES + "water13.png";
     String ROAD_00 = PATH_TILES + "road00.png";
     String ROAD_01 = PATH_TILES + "road01.png";
     String ROAD_02 = PATH_TILES + "road02.png";
     String ROAD_03 = PATH_TILES + "road03.png";
     String ROAD_04 = PATH_TILES + "road04.png";
     String ROAD_05 = PATH_TILES + "road05.png";
     String ROAD_06 = PATH_TILES + "road06.png";
     String ROAD_07 = PATH_TILES + "road07.png";
     String ROAD_08 = PATH_TILES + "road08.png";
     String ROAD_09 = PATH_TILES + "road09.png";
     String ROAD_10 = PATH_TILES + "road10.png";
     String ROAD_11 = PATH_TILES + "road11.png";
     String ROAD_12 = PATH_TILES + "road12.png";
}

interface TheGameMap {
    String PATH_MAP = "src/main/java/Project3_136/resources/eachMap/";
    String PATH_MAP1 = PATH_MAP + "MAP_1.txt";
    String PATH_WORLD_MAP_2 = PATH_MAP + "GameWorldMap02.txt";
    String PATH_WORLD_MAP_1 = PATH_MAP + "GameWorldMap01.txt";
    String PATH_WORLD_MAP_3 = PATH_MAP + "GameWorldMap03.txt";
}

interface MyObjectGame {
    String PATH_OBJECT = "src/main/java/Project3_136/resources/GameObject/";
    String COIN_OBJECT = PATH_OBJECT + "GemCounter.png";
    String BOOTS_OBJECT = PATH_OBJECT + "boots.png";
    String POTION_OBJECT = PATH_OBJECT + "potion_red.png";
    String WOOD_DOOR_OBJECT = PATH_OBJECT + "door.png";
    String CHEST_OBJECT = PATH_OBJECT + "chest.png";
    String HEART_FULL = PATH_OBJECT + "heart_full.png";
    String HEART_HALF = PATH_OBJECT + "heart_half.png";
    String HEART_BLANK = PATH_OBJECT + "heart_blank.png";
}