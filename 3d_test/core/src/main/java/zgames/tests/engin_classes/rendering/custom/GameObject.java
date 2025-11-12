package zgames.tests.engin_classes.rendering.custom;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import zgames.tests.engin_classes.rendering.custom.shaderClasses.MyDefaultShader;

public class GameObject {

    private ModelInstance modelInstance;
    private Shader modelShader;

    private Vector3 rotation = new Vector3(0, 0, 0);
    private Vector3 position = new Vector3(0, 0, 0);
    private Vector3 center = new Vector3(0, 0, 0);
    private Vector3 dimensions = new Vector3(1, 1, 1);
    private float radius = 1f;

    private final static BoundingBox bounds = new BoundingBox();

    public GameObject() {
    }

    public GameObject(Model model) {
        modelShader = new MyDefaultShader();
        modelShader.init();
        modelInstance = new ModelInstance(model);
    }

    public GameObject(Model param_model,
                      Vector3 param__position,
                      Vector3 param_rotation,
                      Vector3 param_dimensions,
                      Vector3 param_center) {
        modelShader = new MyDefaultShader();
        modelShader.init();
        modelInstance = new ModelInstance(param_model);
        rotation = param_rotation;
        position = param__position;
        dimensions = param_dimensions;
        center = param_center;

        modelInstance.transform.setTranslation(position);
        modelInstance.transform.setFromEulerAngles(rotation.x, rotation.y, rotation.z);

        radius = dimensions.len() / 2f;
        bounds.getCenter(new Vector3(position.x + center.x, position.y + center.y, position.z + center.z));
        bounds.getDimensions(dimensions);
        modelInstance.calculateBoundingBox(bounds);
    }

    public Shader getModelShader() {
        return modelShader;
    }

    public ModelInstance getModelInstance() {
        return modelInstance;
    }

    public Vector3 getRotation() {
        return rotation;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getCenter() {
        return center;
    }

    public Vector3 getDimensions() {
        return dimensions;
    }

    public float getRadius() {
        return radius;
    }

    public void setModelShader(Shader modelShader) {
        this.modelShader.end();
        this.modelShader = modelShader;
        this.modelShader.init();
    }

    public void setModelInstance(ModelInstance param_modelInstance) {
        this.modelInstance = param_modelInstance;
    }

    public void setRotation(Vector3 param_rotation) {

        modelInstance.transform.setFromEulerAngles(param_rotation.x, param_rotation.y, param_rotation.z);
        this.rotation = param_rotation;
    }

    public void setPosition(Vector3 param_position) {
        modelInstance.transform.setTranslation(param_position);
        this.position = param_position;
    }

    public void setCenter(Vector3 center) {
        this.center = center;
    }

    public void setDimensions(Vector3 dimensions) {
        this.dimensions.set(dimensions);
    }

    public void update(float delta) {

    }

    public void dispose() {

    }
}
