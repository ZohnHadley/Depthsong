package co.px.depthsong.gameUtils;

import co.px.depthsong.screens.GameScreenGUIBaseOver;
import co.px.depthsong.screens.GUIBaseScreenMainMenu;
import co.px.depthsong.screens.single_player_screens.GUIBaseScreenSinglePlayerMenu;
import co.px.depthsong.screens.inGame_screens.GUIBaseScreenCharacterCreator;
import co.px.depthsong.screens.inGame_screens.GameScreenInGUIBase;
import co.px.depthsong.screens.local_game_screens.GameScreenHostLocalGUIBase;
import co.px.depthsong.screens.local_game_screens.GameScreenJoinLocalGUIBase;
import co.px.depthsong.screens.local_game_screens.GameScreenLocalGUIBaseMenu;

public class StructGameScreens {
    public static GUIBaseScreenMainMenu mainMenu = new GUIBaseScreenMainMenu("mainMenu");
    public static GUIBaseScreenSinglePlayerMenu playOfflineMenu = new GUIBaseScreenSinglePlayerMenu("playOfflineMenu");
    public static GameScreenInGUIBase inGameScreen = new GameScreenInGUIBase("inGameScreen");
    public static GameScreenGUIBaseOver gameOverMenu = new GameScreenGUIBaseOver("gameOverMenu");
    public static GameScreenLocalGUIBaseMenu localGameMenu = new GameScreenLocalGUIBaseMenu("localGameMenu");
    public static GameScreenHostLocalGUIBase hostLocalGameMenu = new GameScreenHostLocalGUIBase("hostLocalGameMenu");
    public static GameScreenJoinLocalGUIBase joinLocalGameMenu = new GameScreenJoinLocalGUIBase("joinLocalGameMenu");
    public static GUIBaseScreenCharacterCreator characterCreator = new GUIBaseScreenCharacterCreator("characterCreator");
}
