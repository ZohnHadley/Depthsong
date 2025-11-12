package zgames.tests.engin_classes.rendering.custom.shaderClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import zgames.tests.engin_classes.rendering.custom.ShaderAttributes.DoubleColorAttribute;
import zgames.tests.engin_classes.rendering.custom.ShaderAttributes.NormalColorAttribute;

public class CustomShader implements Shader {
    ShaderProgram program;
    Camera camera;
    RenderContext context;
    CustomDefaultShaderConfig config;
    //vertex
    int u_projViewTrans;
    int u_worldTrans;
    //fragment
    int u_colorU;
    int u_colorV;


    @Override
    public void init() {
        this.config = new CustomDefaultShaderConfig(); // set cstom shader config
        String vert = Gdx.files.internal("data/mainShader/main.vertex.glsl").readString();
        String frag = Gdx.files.internal("data/mainShader/main.fragment.glsl").readString();
        program = new ShaderProgram(vert, frag);

        if (!program.isCompiled())
            throw new GdxRuntimeException(program.getLog());
        u_projViewTrans = program.getUniformLocation("u_projViewTrans");
        u_worldTrans = program.getUniformLocation("u_worldTrans");
        u_colorU = program.getUniformLocation("u_colorU");
        u_colorV = program.getUniformLocation("u_colorV");

    }

    @Override
    public void dispose() {
        program.dispose();
    }

    @Override
    public void begin(Camera camera, RenderContext context) {
            this.camera = camera;
            this.context = context;
            program.bind();
            program.setUniformMatrix(u_projViewTrans, camera.combined);
            context.setDepthTest(GL20.GL_LEQUAL);
            context.setCullFace(GL20.GL_BACK);
    }

    @Override
    public void render(Renderable renderable) {
        program.setUniformMatrix(u_worldTrans, renderable.worldTransform);
        //set random colors //program.setUniformf(u_color, MathUtils.random(), MathUtils.random(), MathUtils.random());
        //set color without using material attribute but instance data//Color color = (Color)renderable.userData;

        //Color color = ((NormalColorAttribute) renderable.material.get(attributeType)).color;
        Color color_u = ((NormalColorAttribute) renderable.material.get(NormalColorAttribute.NormalColor)).color;
        program.setUniformf(u_colorU, color_u.r, color_u.g, color_u.b);
        //program.setUniformf(u_colorV, color_u.r, color_u.g, color_u.b);
        renderable.meshPart.render(program);
    }

    @Override
    public void end() {
    }

    @Override
    public int compareTo(Shader other) {
        return 0;
    }

    //TODO : note
    //THIS MAKES SURE renderable has the data neede to use shade
    //We can make sure that our shader is only used for a renderable containing a specific material attribute,
    // by implementing the canRender method.
    //Now the shader will only be used if the material contains a ColorAttribute.Diffuse attribute.
    //Otherwise ModelBatch will fall back to the default shader.

    @Override
    public boolean canRender(Renderable renderable) {
        //return renderable.material.has(ColorAttribute.Diffuse);
        //since we are using a custom attribute

        if (!renderable.material.has(NormalColorAttribute.NormalColor)) {
            Gdx.app.error("CustomShader", "can't render " + renderable.material.toString());
        }
        return renderable.material.has(NormalColorAttribute.NormalColor);

    }
}
