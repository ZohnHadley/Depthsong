package co.px.depthsong.layers.services;

import co.px.depthsong.layers.services.level.GameLevelService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class JsonService {
    private static JsonService instance;
    private final GsonBuilder builder;
    private final Gson gson;

    private JsonService(){
        builder = new GsonBuilder();
        gson = builder.excludeFieldsWithoutExposeAnnotation().create();
    }
    public static JsonService getInstance() {
        if (instance == null) {
            instance = new JsonService();
        }
        return instance;
    }

    public String toJson(Object object){
        return gson.toJson(object);
    }

    public Type fromJson(String json){
        return gson.fromJson(json, Type.class);
    }
}
