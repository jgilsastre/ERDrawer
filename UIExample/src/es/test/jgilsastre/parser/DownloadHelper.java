package es.test.jgilsastre.parser;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import es.test.jgilsastre.main.Diagram;

public class DownloadHelper {

	
	public static Diagram downloadDiagram(String name){
		
		
		
		return null;
	}
	
	public static Diagram parseDiagram(InputStream input){
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		Diagram diagram = null;
		try {
			SAXParser saxParser = spf.newSAXParser();
			DiagramParser parser = new DiagramParser();
			saxParser.setProperty("http://xml.org/sax/properties/lexical-handler", parser);
			
			// Obtenemos el Reader
			XMLReader xr = saxParser.getXMLReader();
			xr.setContentHandler(parser);
			
			// Parseamos el contenido
			InputSource is;
			is = new InputSource(input);
			is.setEncoding("ISO-8859-1");
			xr.parse(is);
			
			diagram = parser.getDiagram();
			input.close();
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return diagram;
	}
}
