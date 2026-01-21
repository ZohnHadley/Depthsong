package co.px.depthsong.enginUtils;

import co.px.depthsong.screens.GameScreenGUIOver;
import co.px.depthsong.screens.GUIScreenMainMenu;
import co.px.depthsong.screens.single_player_screens.GUIScreenSinglePlayerMenu;
import co.px.depthsong.screens.inGame_screens.GUIScreenCharacterCreator;
import co.px.depthsong.screens.inGame_screens.GameScreenInGUI;
import co.px.depthsong.screens.local_game_screens.GameScreenHostLocalGUI;
import co.px.depthsong.screens.local_game_screens.GameScreenJoinLocalGUI;
import co.px.depthsong.screens.local_game_screens.GameScreenLocalGUIMenu;

public class GameScreensList {
    public static GUIScreenMainMenu mainMenu = new GUIScreenMainMenu("mainMenu");
    public static GUIScreenSinglePlayerMenu playOfflineMenu = new GUIScreenSinglePlayerMenu("playOfflineMenu");
    public static GameScreenInGUI inGameScreen = new GameScreenInGUI("inGameScreen");
    public static GameScreenGUIOver gameOverMenu = new GameScreenGUIOver("gameOverMenu");
    public static GameScreenLocalGUIMenu localGameMenu = new GameScreenLocalGUIMenu("localGameMenu");
    public static GameScreenHostLocalGUI hostLocalGameMenu = new GameScreenHostLocalGUI("hostLocalGameMenu");
    public static GameScreenJoinLocalGUI joinLocalGameMenu = new GameScreenJoinLocalGUI("joinLocalGameMenu");
    public static GUIScreenCharacterCreator characterCreator = new GUIScreenCharacterCreator("characterCreator");
}
