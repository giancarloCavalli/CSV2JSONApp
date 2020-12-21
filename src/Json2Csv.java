import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Json2Csv {
	private String originalText;
	private String csv;
	private ArrayList<JsonObject> jsonObjects = new ArrayList<>();
	
	public Json2Csv(String jsonText) {
		this.originalText = jsonText.strip();
		setJsonObjects();
	}
	
	private String[] splitJsonAttributes(String jsonObjectString) {
		String[] sArr = jsonObjectString.split(",");
		for (int i = 0; i < sArr.length; i++) {
			sArr[i] = sArr[i].stripIndent();
		}
		return sArr;
	}

	public String getCsv() {
		if (csv == null)
			setCsv();
		return csv;
	}
	
	private void setCsv() {
		String s = generateCsvHeader();
		s += generateCsvLines();
		this.csv = s;
	}
	
	private String generateCsvLines() {
		String s = "";
		String[] values;
		for(JsonObject jo : jsonObjects) {
			values = jo.valuesToString().split(",");
			s += "\n"+values[0].strip();
			for (int i = 1; i < values.length; i++) {
				s += ";"+values[i].strip();
			}
		}
		return s;
	}

	public String generateCsvHeader() {
		String s = "";
		String[] header = jsonObjects.get(0).attributeNamesToString().split(",");
		s += header[0].strip();
		for (int i = 1; i < header.length; i++) {
			s += ";"+header[i].strip();
		}
		return s;
	}
	
	private void setJsonObjects() {
		String[] sArr = breakJsonArrayIntoObjects();
		String[] pairs;
		String[] nameValue;
		for (int i = 1; i < sArr.length; i++) {
			JsonObject jo = new JsonObject();
			pairs = splitJsonAttributes(sArr[i]);
			for(String pa : pairs) {
				 Pattern p = Pattern.compile("\n*\s*\"|\"\s*\n*|\"");
				 Matcher m = p.matcher(pa);
				 StringBuffer sb = new StringBuffer();
				 while (m.find()) {
				     m.appendReplacement(sb, "");
				 }
				 m.appendTail(sb);
				 pa = sb.toString();
				 nameValue = pa.split(":");
				 jo.setPair(nameValue[0].strip(), nameValue[1].strip());
			}
			jsonObjects.add(jo);
		}
		
	}
	
	private String[] breakJsonArrayIntoObjects() {
		return originalText.split("\s*\\[\n*\t*\\{\n*\s*|\n*\t*\\},*\n*\t*\\{\n*|\n*\t*\\}\n*\\]");
	}
}
