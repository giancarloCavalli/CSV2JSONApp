
public class Csv2Json {
	private String[] header;
	
	public Csv2Json(String firstLine) {
		header = firstLine.split(";");
	}
	
	public String getJsonObject(String csvData) {
		String[] csvFields = csvData.split(";");
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (int i = 0; i < header.length-1; i++) {
			sb.append("'"+header[i]+"' : '"+csvFields[i]+"', ");
		}
		sb.append("'"+header[header.length-1]+"' : '"+csvFields[header.length-1]+"'");
		sb.append("}");
		return String.valueOf(sb);
	}
}
