package org.emobile.myitmarket.manager.gson;

import org.emobile.myitmarket.model.BaseModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

/**
 * Created by Risto Muchev on 30-Aug-16.
 */
public class JSON {

    private static Gson instance = null;

    private JSON(){

    }

    private static Gson getGson(){
        if(instance == null){

//			RuntimeTypeAdapterFactory<BaseModel> shapeAdapterFactory = RuntimeTypeAdapterFactory.of(BaseModel.class).registerSubtype(User.class);
            GsonBuilder gb = new GsonBuilder();
            gb.registerTypeAdapter(BaseModel.class, new BaseSerializer());
            instance = gb.create();
        }
        return instance;
    }

    public static String toJson(BaseModel model){
        return getGson().toJson(model, BaseModel.class);
    }

    public static String toJson(Object model, Type t){
        return getGson().toJson(model, t);
    }

    public static Object fromJson(String model, Type t){
        return getGson().fromJson(model, t);
    }

    public static Object fromJson(JsonObject model, Type t){
        return getGson().fromJson(model, t);
    }


}
