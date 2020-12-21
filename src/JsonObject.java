import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Pattern;

public class JsonObject {
	private HashMap<String, String> pair = new HashMap<>();
	
	public JsonObject() {
		
	}
	
	public void setPair(String name, String value) {
		pair.put(name, value);
	}
	
	public void setPair(String name, double value) {
		pair.put(name, String.valueOf(value));
	}
	
	public void setPair(String name, int value) {
		pair.put(name, String.valueOf(value));
	}
	
	public void setPair(String name, float value) {
		pair.put(name, String.valueOf(value));
	}
	
	public String getValue(String name) {
		return pair.get(name);
	}
	
	public String attributeNamesToString() {
		String s = String.valueOf(pair.keySet());
		s = s.substring(s.indexOf("[")+1, s.lastIndexOf("]"));
		return s;
	}
	
	public String valuesToString() {
		String s = String.valueOf(pair.values());
		s = s.substring(s.indexOf("[")+1, s.lastIndexOf("]"));
		return s;
	}
}
