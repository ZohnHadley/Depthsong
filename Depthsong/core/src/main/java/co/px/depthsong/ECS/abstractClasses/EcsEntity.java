package co.px.depthsong.ECS.abstractClasses;

import co.px.depthsong.ECS.components.core.ComponentList;
import co.px.depthsong.ECS.components.ComponentTransform;
import co.px.depthsong.ECS.entityContext.EntityContext;
import co.px.depthsong.layers.managers.GameManager;
import lombok.*;

import java.awt.geom.Dimension2D;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@ToString
@AllArgsConstructor
@Getter
@Setter
public abstract class EcsEntity {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final EntityContext context = EntityContext.getInstance();
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final GameManager gameManager = GameManager.getInstance();


    private Long id = 0L;
    private String name;
    private EcsEntity parent = null;
    private List<EcsEntity> listChildren = new ArrayList<>();
    private Boolean isVisible = false;

    private ComponentTransform componentTransform;

    private ComponentList componentList;
    private List<EcsTag> entityTags = new ArrayList<>();

    public EcsEntity()
    {
        name = "Untitled_Entity";
        parent = null;
        componentList = new ComponentList(this);

        componentTransform = new ComponentTransform();

        componentList.add(componentTransform);
        context.addEntity(this);
    }

    public EcsEntity(String param_name)
    {
        name = param_name;
        parent = null;
        componentList = new ComponentList(this);

        componentTransform = new ComponentTransform();

        componentList.add(componentTransform);
        context.addEntity(this);
    }

    public ComponentTransform getTransform()
    {
        if (componentTransform == null)
        {
            componentTransform = (ComponentTransform) componentList.get(ComponentTransform.class);
        }
        return componentTransform;
    }

    public List<EcsEntity> getChildren()
    {
        if (listChildren == null)
        {
            throw new NullPointerException("Children list cannot be null.");
        }
        return listChildren;
    }
    public Dimension2D getScale()
    {
        return componentTransform.getScale();
    }

    public void setParent(EcsEntity param_parent)
    {
        parent = param_parent;
        componentTransform.setParent(param_parent.getTransform());
    }

    public void removeParent()
    {
        if (parent == null)
        {
            throw new NullPointerException("Parent cannot be null.");
        }
        parent = null;
        componentTransform.setParent(null);
    }
    public void setTransform(ComponentTransform param_componentTransform)
    {
        if (param_componentTransform == null)
        {
            throw new NullPointerException("componentTransform cannot be null.");
        }
        componentTransform = param_componentTransform;
    }

    // Tags management
    public void addTag(EcsTag param_tag)
    {
        if (param_tag == null)
        {
            throw new NullPointerException("Tag cannot be null.");
        }

        if (entityTags.contains(param_tag))
        {
            throw new RuntimeException("Tag already exists in entity.");
        }

        entityTags.add(param_tag);
    }
    public void removeTag(EcsTag param_tag)
    {
        if (param_tag == null)
        {
            throw new NullPointerException("Tag cannot be null.");
        }

        if (!entityTags.contains(param_tag))
        {
            throw new RuntimeException("Tag not found in entity.");
        }

        entityTags.remove(param_tag);
    }

    public EcsTag getTag(Type param_tag_type)
    {
        for(EcsTag tag : entityTags)
        {
            if (tag.getClass() == param_tag_type)
            {
                return tag;
            }
        }
        throw new RuntimeException("Tag of type " + param_tag_type.getTypeName() + " not found in entity.");
    }
    public Boolean hasTag(EcsTag param_tag)
    {
        return entityTags.contains(param_tag);
    }

    public void addChild(EcsEntity child)
    {
        if (child == null)
        {
            throw new NullPointerException("Child entity cannot be null.");
        }

        listChildren.add(child);
        child.setParent(this);
    }
}

