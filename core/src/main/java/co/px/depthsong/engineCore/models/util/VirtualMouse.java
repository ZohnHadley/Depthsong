package co.px.depthsong.engineCore.models.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import lombok.Getter;

import java.awt.*;
import java.awt.geom.Dimension2D;

@Getter
public class VirtualMouse {
    private final GameCamera gameCamera = GameCamera.getInstance();
    private static VirtualMouse instance;

    private Vector2 position;
    private Vector2 dimensions;

    private boolean isInUi = false;

    private VirtualMouse() {
        dimensions = new Vector2(3, 3);
        position = new Vector2(0, 0);
    }

    public static VirtualMouse getInstance() {
        if (instance == null) {
            instance = new VirtualMouse();
        }
        return instance;
    }

    public void setIsInUi(boolean isInUi) {
        this.isInUi = isInUi;
    }

    public void update(float deltaTime) {
        // Screen-space coordinates (origin top-left)
        float screenX = Gdx.input.getX();
        float screenY = Gdx.input.getY();

        // Convert to world space (camera-adjusted)
        Vector3 world = new Vector3(screenX, screenY, 0);
        world = gameCamera.getCamera().unproject(world);
        // Apply to virtual mouse position
        position.set(
            world.x - dimensions.x * 0.5f +0.65f,
            world.y - dimensions.y * 0.5f -0.65f
        );
    }



}
