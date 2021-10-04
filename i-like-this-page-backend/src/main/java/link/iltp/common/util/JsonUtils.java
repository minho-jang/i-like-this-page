package link.iltp.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import link.iltp.exception.JsonException;

import java.util.Map;

public class JsonUtils {

	static final TypeReference<Map<String, Object>> typeOfMap = new TypeReference<Map<String, Object>>() {};
	private static final ObjectMapper om = new ObjectMapper();

	public static <T> T jsonStringToObject(String json, Class<T> type) {
		try {
			return om.readValue(json, type);
		} catch (Exception e) {
			throw new JsonException("Error converting json string to object.", e);
		}
	}

	public static String objectToJsonString(Object obj) {
		try {
			return om.writeValueAsString(obj);
		} catch (Exception e) {
			throw new JsonException("Error converting object to json string", e);
		}
	}

	public static Map<String, Object> jsonStringToMap(String json) {
		try {
			return om.readValue(json, typeOfMap);
		} catch (Exception e) {
			throw new JsonException("Error converting json string to map", e);
		}
	}

}