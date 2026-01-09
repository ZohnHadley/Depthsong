package co.px.depthsong.enginUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.lang.reflect.Type;

@Getter
@Setter
public class JsonUtil {
    private static JsonUtil instance;
    ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtil(){
    }

    public static JsonUtil getInstance() {
        if (instance == null) {
            instance = new JsonUtil();
        }
        return instance;
    }

    public <T> String toJson(T object ){
        try{
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJson(String json, Class<T> objClass) {
        try {
            if (json == null || json.isBlank() || objClass == null) {
                throw new RuntimeException("JsonUtil : err fromJson");
            }
            return objectMapper.readValue(json, objClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
