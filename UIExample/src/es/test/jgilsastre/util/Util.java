package es.test.jgilsastre.util;

import java.util.HashMap;
import java.util.Map;

import es.test.jgilsastre.main.Element;

public class Util {

	public static Map<String, String> convertPairsToMap(String elements){
		Map<String, String> map = new HashMap<String, String>();
		String[] elems = elements.split(Element.ELEMENT_SEPARATOR);
		for(String e : elems){
			String[] pair = e.split(Element.ELEMENT_ID_NAME_SEPARATOR);
			if(pair.length == 2)
				map.put(pair[0], pair[1]);
		}
		return map;	
	}
}
