package co.px.depthsong.ECS.abstractClasses;

import co.px.depthsong.ECS.components.core.ComponentList;
import co.px.depthsong.ECS.components.ComponentTransform;
import co.px.depthsong.ECS.entityContext.EntityContext;
import co.px.depthsong.core.engine_managers.GameManager;
import lombok.*;

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
    private Boolean isVisible = false;

    private ComponentList componentList;
    private List<EcsTag> entityTags = new ArrayList<>();

    public EcsEntity()
    {
        name = "Untitled_Entity";
        parent = null;
        componentList = new ComponentList(this);

        context.addEntity(this);
    }

    // Tags management TODO:Add Taglist class to manage tags
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
}

