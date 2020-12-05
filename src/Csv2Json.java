
public class Csv2Json {
	private String[] header;
	
	public Csv2Json(String firstLine) {
		header = firstLine.split(";");
	}
	
	public String getJsonObject(String csvData) {
		String[] csvFields = csvData.split(";");
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		String value;
		for (int i = 0; i < header.length-1; i++) {
			value = csvFields[i];
			if(isNumeric(value))
				sb.append("\n \""+header[i]+"\" : "+convertToNumber(value)+", ");
			else
				sb.append("\n \""+header[i]+"\" : \""+value+"\", ");
		}
		value = csvFields[header.length-1];
		if(isNumeric(value))
			sb.append("\n \""+header[header.length-1]+"\" : "+convertToNumber(value));
		else
			sb.append("\n \""+header[header.length-1]+"\" : \""+value+"\"");
		sb.append("\n}\n");
		return String.valueOf(sb);
	}
	
	private boolean isNumeric(String value) {
		try {
			Double.parseDouble(convertToNumber(value));
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	private String convertToNumber(String value) {		
		return value.replace(',', '.');
	}
}
