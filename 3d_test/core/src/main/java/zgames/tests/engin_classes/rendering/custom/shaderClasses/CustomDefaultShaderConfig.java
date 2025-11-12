package zgames.tests.engin_classes.rendering.custom.shaderClasses;

import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;

public class CustomDefaultShaderConfig extends DefaultShader.Config {
    //where the max bones for models are set
    public CustomDefaultShaderConfig() {
        super();
        this.numBones = 64;
    }

    public CustomDefaultShaderConfig(final String vertexShader, final String fragmentShader) {
        super(vertexShader, fragmentShader);
        this.numBones = 64;
    }

}
