package utils;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Token {
 
	public static List<String> getUsuarioYTipo(String jwtToken) {
		final List<String> data = new ArrayList<String>();
		final java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
		final String[] parts = jwtToken.split("\\.");
		final String payloadJson = new String(decoder.decode(parts[1]));
		final JSONParser parser = new JSONParser();
		JSONObject json = null;
		try {
			json = (JSONObject) parser.parse(payloadJson);
			data.add(json.get("user_name").toString());
			data.add(json.get("TipoUsuario").toString());
			return data;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
