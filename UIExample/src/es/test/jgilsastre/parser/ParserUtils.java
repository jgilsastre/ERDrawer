package es.test.jgilsastre.parser;

import java.util.Map;

public class ParserUtils {

	public static final String openStartTag = "<";
	public static final String closeTag = ">";
	public static final String openEndTag = "</";
	public static final String closeAutoConclusiveTag = "/>";
	public static final String openCDATA = "<![CDATA[";
	public static final String closeCDATA = "]]>";
	
	public static String simpleTag(String tagName, String value, boolean valueInCDATA, Map<String, String> attributes){
		StringBuilder sb = new StringBuilder();
		sb.append(openStartTag);
		sb.append(tagName);
		if(attributes != null && attributes.size() != 0){
			for(String s : attributes.keySet()){
				sb.append(" ");
				sb.append(s);
				sb.append("=\"");
				sb.append(attributes.get(s));
				sb.append("\"");
			}
		}
		if(value == null){
			sb.append(closeAutoConclusiveTag);
		}else{
			sb.append(closeTag);
			if(valueInCDATA){
				sb.append(openCDATA);
				sb.append(value);
				sb.append(closeCDATA);
			}else{
				sb.append(value);
			}
			sb.append(openEndTag);
			sb.append(tagName);
			sb.append(closeTag);
		}
		return sb.toString();
	}

	public static String openTag(String tagName, Map<String, String> attributes){
		if(attributes == null)
			return openStartTag + tagName + closeTag;
		StringBuilder sb = new StringBuilder();
		sb.append(openStartTag);
		sb.append(tagName);
		if(attributes.size() != 0){
			for(String s : attributes.keySet()){
				sb.append(" ");
				sb.append(s);
				sb.append("=\"");
				sb.append(attributes.get(s));
				sb.append("\"");
			}
		}
		sb.append(closeTag);
		return sb.toString();
	}
	
	public static String closeTag(String tagName){
		return openEndTag + tagName + closeTag;
	}
	
}
