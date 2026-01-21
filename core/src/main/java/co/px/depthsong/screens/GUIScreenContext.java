package co.px.depthsong.screens;

import co.px.depthsong.engineCore.models.GUIScreen;
import lombok.Getter;

import java.util.Hashtable;
import java.util.List;

@Getter
public class GUIScreenContext {
    private static GUIScreenContext instance = null;
    private static Long contextEntityCount = 0L;
    private Hashtable<Long, GUIScreen> screens = new Hashtable<>();

    private Hashtable<String, List<GUIScreen>> screenComponentGroups;
    public static GUIScreenContext getInstance(){
        if(instance == null){
            instance = new GUIScreenContext();
        }
        return instance;
    }

    public void addScreen(GUIScreen screen) {
        screen.setId(contextEntityCount);
        screens.put(screen.getId(), screen);
        contextEntityCount++;
    }

    public void removeEntity(GUIScreen screen) {
        if (screen == null) {
            throw new NullPointerException("Entity cannot be null.");
        }

        if (!screens.contains(screen.getId())) {
            throw new RuntimeException("Screen with ID " + screen.getId() + " does not exist.");
        }

        screens.remove(screen.getId());
    }
}
