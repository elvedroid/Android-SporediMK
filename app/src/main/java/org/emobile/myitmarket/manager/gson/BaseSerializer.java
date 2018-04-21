package org.emobile.myitmarket.manager.gson;

import org.emobile.myitmarket.model.BaseModel;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;


public class BaseSerializer implements JsonSerializer<BaseModel> {

	@Override
	public JsonElement serialize(BaseModel src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jo = context.serialize(src).getAsJsonObject();
		if(src.getId() == null || src.getId() <= 0){
			jo.remove("id");
		}
		if(src.getCreationDate() <= 0){
			jo.remove("creationDate");
		}
		if(src.getLastModificationDate() <= 0){
			jo.remove("lastModificationDate");
		}
		return jo;
	}

}
