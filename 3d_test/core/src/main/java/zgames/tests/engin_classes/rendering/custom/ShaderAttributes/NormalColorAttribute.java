package zgames.tests.engin_classes.rendering.custom.ShaderAttributes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;

public class NormalColorAttribute extends Attribute {
    public final static String NormalColorAlias = "normalColor";
    public final static long NormalColor = register(NormalColorAlias);

    public final Color color = new Color();

    public NormalColorAttribute(long type, Color color) {
        super(type);
        this.color.set(color);
    }

    @Override
    public Attribute copy() {
        return new NormalColorAttribute(type, color);
    }

    @Override
    public int compareTo(Attribute other) {
        if (type != other.type)
            return (int) (type - other.type);
        NormalColorAttribute attr = (NormalColorAttribute) other;
        return color.toIntBits() - attr.color.toIntBits();
    }
}
