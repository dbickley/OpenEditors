package com.deepnoodle.openeditors.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class JsonUtils {

	private static Gson gson = createGson();
	private static Gson gsonWithPrettyPrinting = createGsonWithPrettyPrinting();
	
	private JsonUtils() {
	}

	public static <T> String toJson(T object) {
		return gson.toJson(object);
	}
	
	public static <T> String toJsonPrettyPrint(T object) {
		return gsonWithPrettyPrinting.toJson(object);
	}
	
	public static <T> T fromJson(String json, Class<T> clazz) {
		return gson.fromJson(json, clazz);
	}
	
	private static Gson createGson() {
		return new GsonBuilder().create();
	}
	
	private static Gson createGsonWithPrettyPrinting() {
		return new GsonBuilder().setPrettyPrinting().create();
	}
}
