import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.security.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Csv2Json {
	private String[] header;
	private String originalContent;
	
	public Csv2Json(String csvText) {
		setOriginalContent(csvText.strip());
	}
	
	public String getJsonObjectArray() {
		if(!isValidCsv())
			throw new InvalidParameterException("CSV inválido!");
		StringBuilder json = new StringBuilder();
		json.append("[\n");
		BufferedReader reader = new BufferedReader(new StringReader(this.originalContent));
		try {
			setHeader(reader.readLine());
			String str;
			while((str = reader.readLine()) != null) {
				json.append("\t"+getJsonObject(str)+",\n");
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		json.replace(json.lastIndexOf(","), json.lastIndexOf(",")+1, "");
		json.append("]");
		return String.valueOf(json);
	}
	
	private boolean isValidCsv() {
		final String CSV_PATTERN = ";";
		Pattern p = Pattern.compile(CSV_PATTERN);
		Matcher m = p.matcher(originalContent);
		return m.find();
	}

	private String getJsonObject(String csvData) {
		String[] csvFields = csvData.split(";");
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		String value;
		for (int i = 0; i < header.length-1; i++) {
			value = csvFields[i];
			if(isNumeric(value))
				sb.append("\n\t\t\""+header[i]+"\" : "+convertToNumber(value)+", ");
			else
				sb.append("\n\t\t\""+header[i]+"\" : \""+value+"\", ");
		}
		value = csvFields[header.length-1];
		if(isNumeric(value))
			sb.append("\n\t\t\""+header[header.length-1]+"\" : "+convertToNumber(value));
		else
			sb.append("\n\t\t\""+header[header.length-1]+"\" : \""+value+"\"");
		sb.append("\n\t}");
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
	
	
	private String getOriginalContent() {
		return originalContent;
	}

	private void setOriginalContent(String originalContent) {
		this.originalContent = originalContent;
	}

	private String[] getHeader() {
		return header;
	}

	private void setHeader(String header) {
		this.header = header.split(";");
	}

	private String convertToNumber(String value) {		
		return value.replace(',', '.');
	}
}
