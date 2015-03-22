package es.test.jgilsastre.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Canvas;
import android.graphics.Point;
import es.test.jgilsastre.exception.ERException;
import es.test.jgilsastre.parser.ParserConsts;
import es.test.jgilsastre.parser.ParserUtils;

/**
 * Clase que representa el objeto dominio
 * @author Jorge Gil Sastre
 * @version 0.1
 * Identificador
 * Nombre
 * Tipo de dato
 * Valor por defecto
 * Restricciones (ArrayList de strings)
 */
public class Domain implements ERItem{
	
	private String identifier;
	private String name;
	private String defaultValue;
	private List<String> restrictions;

	/**
	 * Constructor de un dominio
	 * @param identifier: Identificador
	 * @param name: Nombre
	 * @param dataType: tipo de dato
	 * @param defaultValue: valor por defecto
	 */
	public Domain(String identifier, String name, String defaultValue) {
		this.identifier = identifier;
		this.name = name;
		this.defaultValue = defaultValue;
		this.restrictions = new ArrayList<String>();
	}
	
	/**
	 * Constructor de un dominio por defecto
	 * @param identifier: Identificador
	 */
	public Domain(String identifier){
		this(identifier, "", "");
	}

	public Domain(String identifier, String name){
		this(identifier, name, "");
	}
	
	public String getIdentifier(){
		return identifier;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public List<String> getRestrictions() {
		return restrictions;
	}

	public void addRestriction(String restriction){
		restrictions.add(restriction);
	}
	
	public void removeRestriction(String restriction){
		restrictions.remove(restriction);
	}
	
	@Override
	public String toString(){
		return identifier + Element.ELEMENT_ID_NAME_SEPARATOR + name;
	}

	@Override
	public int move(Point newPoint, Point initPosition) throws ERException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int move(int offsetX, int offsetY) throws ERException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void standOut() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void standIn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean touched(Point touchedPoint, Point initialPoint) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void scale(float newScale) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Canvas canvas, Point initialPoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toXML() {
		StringBuilder xmlDom = new StringBuilder();
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put(ParserConsts.ID, this.identifier);
		xmlDom.append(ParserUtils.openTag(ParserConsts.DOMAIN, attributes)); //Tag de inicio del dominio
		xmlDom.append(ParserUtils.simpleTag(ParserConsts.NAME, this.name, true, null)); //Tag del nombre
		xmlDom.append(ParserUtils.simpleTag(ParserConsts.DEFAULT_VALUE, this.defaultValue, true, null)); //Tag del valor por defecto
		if(restrictions.size() > 0){
			xmlDom.append(ParserUtils.openTag(ParserConsts.RESTRICTIONS, null));
			for(String res : restrictions){
				xmlDom.append(ParserUtils.simpleTag(ParserConsts.RESTRICTION, res, true, null));
			}
			xmlDom.append(ParserUtils.closeTag(ParserConsts.RESTRICTIONS));
		}
		xmlDom.append(ParserUtils.closeTag(ParserConsts.DOMAIN));
		return xmlDom.toString();
	}
}
