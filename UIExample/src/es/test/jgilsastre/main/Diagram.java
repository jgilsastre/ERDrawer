package es.test.jgilsastre.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.test.jgilsastre.exception.ERException;
import es.test.jgilsastre.exception.OutOfDrawingRangeException;
import es.test.jgilsastre.parser.ParserConsts;
import es.test.jgilsastre.parser.ParserUtils;

import android.graphics.Point;

/**
 * Definición del objeto Diagrama 
 * @author Jorge Gil Sastre
 * @version 0.1
 * identifier: Identificador
 * ERName: Nombre del diagrama
 * version: Versión del diagrama
 * editable: se puede modificar
 * path: ruta del fichero
 * entities: map de entidades
 * unions: map de líneas de union entre elementos
 * attributes: map de attributos
 */
public class Diagram {

	public static final int TYPE_ENTITY = 1;
	public static final int TYPE_UNION = 2;
	public static final int TYPE_ATTRIBUTE = 3;
	public static final int TYPE_RELATION = 4;
	
	private String identifier;
	private String ERName;
	private double version;
	private boolean editable;
	private String path;
	private Date createdDate;
	private Date lastModifiedDate;
	private int elementCounter;
	private Map<String, Entity> entities;
	private Map<String, Union> unions;
	private Map<String, Attribute> attributes;
	private Map<String, Domain> domains;
	private Map<String, Relation> relations;
	private Point limitPoint;
	private Point initPosition;
	private Point digramCenter;
	
	/**
	 * 
	 * @param identifier
	 * @param ERName
	 */
	public Diagram(String identifier, String ERName, Date createdDate, Date lastModifiedDate){
		this(identifier, ERName, Double.parseDouble("1.0"), createdDate, lastModifiedDate);
	}
	
	/**
	 * 
	 * @param identifier
	 * @param ERName
	 * @param version
	 * @param createdDate
	 * @param lastModifiedDate
	 */
	public Diagram(String identifier, String ERName, double version, Date createdDate, Date lastModifiedDate){
		super();
		this.identifier = identifier;
		this.version = version;
		this.ERName = ERName;
		this.editable = true;
		this.path = "";
		this.elementCounter = 0;
		if(createdDate != null)
			this.createdDate = createdDate;
		else
			this.createdDate = new Date();
		if(lastModifiedDate != null)
			this.lastModifiedDate = lastModifiedDate;
		else
			this.lastModifiedDate = new Date();
		entities = new HashMap<String, Entity>();
		unions = new HashMap<String, Union>();
		attributes = new HashMap<String, Attribute>();
		domains = new HashMap<String, Domain>();
		relations = new HashMap<String, Relation>();
		initPosition = new Point(0, 0);
		limitPoint = new Point(-1, -1);
		digramCenter = new Point(-1, -1);
		if(createdDate == null && lastModifiedDate == null)
			addInitialDomains();
	}
	
	private void addInitialDomains() {
		domains.put("initDom_" + this.hashCode() + "_char", new Domain("initDom_" + this.hashCode() + "_char", "CHAR"));
		domains.put("initDom_" + this.hashCode() + "_varchar2", new Domain("initDom_" + this.hashCode() + "_varchar2", "VARCHAR2"));
		domains.put("initDom_" + this.hashCode() + "_text", new Domain("initDom_" + this.hashCode() + "_text", "TEXT"));
		domains.put("initDom_" + this.hashCode() + "_blob", new Domain("initDom_" + this.hashCode() + "_blob", "BLOB"));
		domains.put("initDom_" + this.hashCode() + "_tinyint", new Domain("initDom_" + this.hashCode() + "_tinyint", "TINYINT"));
		domains.put("initDom_" + this.hashCode() + "_smallint", new Domain("initDom_" + this.hashCode() + "_smallint", "SMALLINT"));
		domains.put("initDom_" + this.hashCode() + "_mediumint", new Domain("initDom_" + this.hashCode() + "_mediumint", "MEDIUMINT"));
		domains.put("initDom_" + this.hashCode() + "_int", new Domain("initDom_" + this.hashCode() + "_int", "INT"));
		domains.put("initDom_" + this.hashCode() + "_bigint", new Domain("initDom_" + this.hashCode() + "_bigint", "BIGINT"));
		domains.put("initDom_" + this.hashCode() + "_float", new Domain("initDom_" + this.hashCode() + "_float", "FLOAT"));
		domains.put("initDom_" + this.hashCode() + "_double", new Domain("initDom_" + this.hashCode() + "_double", "DOUBLE"));
		domains.put("initDom_" + this.hashCode() + "_decimal", new Domain("initDom_" + this.hashCode() + "_decimal", "DECIMAL"));
		domains.put("initDom_" + this.hashCode() + "_date", new Domain("initDom_" + this.hashCode() + "_date", "DATE"));
		domains.put("initDom_" + this.hashCode() + "_time", new Domain("initDom_" + this.hashCode() + "_time", "TIME"));
		domains.put("initDom_" + this.hashCode() + "_datetime", new Domain("initDom_" + this.hashCode() + "_datetime", "DATETIME"));
		domains.put("initDom_" + this.hashCode() + "_timestamp", new Domain("initDom_" + this.hashCode() + "_timestamp", "TIMESTAMP"));
		domains.put("initDom_" + this.hashCode() + "_autoincrement", new Domain("initDom_" + this.hashCode() + "_autoincrement", "AUTOINCREMENT"));
	}

	public void putElement(ERItem elem){
		elementCounter++;
		if(elem instanceof Entity){
			entities.put(elem.getIdentifier(), (Entity) elem);
		}else if(elem instanceof Attribute){
			attributes.put(elem.getIdentifier(), (Attribute)elem);
		}else if(elem instanceof Union){
			unions.put(elem.getIdentifier(), (Union)elem);
		}else if(elem instanceof Relation){
			relations.put(elem.getIdentifier(), (Relation)elem);
		}
	}
	
	public void putDomain(Domain dom){
		domains.put(dom.getIdentifier(), dom);
	}
	
	public ERItem getElement(String key, int type){
		switch(type){
		case TYPE_ENTITY:
			return entities.get(key);
		case TYPE_UNION:
			return unions.get(key);
		case TYPE_ATTRIBUTE:
			return attributes.get(key);
		case TYPE_RELATION:
			return relations.get(key);
		default:
			return null;	
		}
	}
	
	public Element getElement(String key){
		Element e = null;
		e = entities.get(key);
		if(e != null)
			return e;
		e = attributes.get(key);
		if(e != null)
			return e;		
		e = relations.get(key);
		if(e != null)
			return e;
		return null;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public String getRelationsSerialized(){
		StringBuilder sb = new StringBuilder();
		if(relations.size() > 0){
			for(Relation r: relations.values())
				sb.append(r.toString() + Relation.ELEMENT_SEPARATOR);
			return sb.toString().substring(0, sb.length() - 3);
		}else{
			return "";
		}
	}
	
	public String getDomainsSerialized(){
		StringBuilder sb = new StringBuilder();
		if(domains.size() > 0){
			for(Domain d:domains.values())
				sb.append(d.toString() + Element.ELEMENT_SEPARATOR);
			return sb.toString().substring(0, sb.length() - 3);
		}else{
			return "";
		}
	}
	
	public Set<String> getDomainsKeySet(){
		return domains.keySet();
	}

	public Point getInitPosition() {
		return initPosition;
	}

	public Point getLimit(){
		return limitPoint;
	}

	public int getElementCounter(){
		return elementCounter;
	}
	
	public void setDiagramCenter(int x, int y){
		digramCenter.set(x + initPosition.x, y + initPosition.y);
	}
	
	public Point getPointToAdd(){
		//Mientras el punto este ocupado, hay que ir recorriendo todos los elementos y cambiandolo, 100 abajo y 100 a la derecha
		boolean occupied = true;
		Point p = new Point(digramCenter.x, digramCenter.y);
		List<Element> elements = getElements();
		while(occupied){
			occupied = false;
			boolean pointFound = false;
			int i = 0;
			while(!pointFound && i<elements.size()){
				if(elements.get(i).touched(p, initPosition)){
					occupied = true;
					pointFound = true;
				}
			}
			if(p.x < initPosition.x + 800)
				p.set(p.x + 100, p.y);
			else
				p.set(100, p.y + 100);
		}
		return p;
	}
	
	public void setLimit(Point p) {
		if(limitPoint.x == -1 || limitPoint.y == -1){
			limitPoint = p;
			digramCenter.set(limitPoint.x /2, limitPoint.y/2);
		}
	}

	public Domain getDomain(String key){
		if(domains.containsKey(key))
			return domains.get(key);
		else
			return null;
	}
	
	public void rename(String newName){
		this.ERName = newName;
	}
	
	public String getName(){
		return ERName;
	}
	
	public double getVersion(){
		return version;
	}
	
	public void setVersion(double version){
		this.version = version;
	}
	
	public boolean isEditable() {
		return editable;
	}

	public void setReadOnly() {
		this.editable = false;
	}
	
	public void setEditable(){
		this.editable = true;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public Point getDiagramCenter(){
		return digramCenter;
	}

	public void removeElement(ERItem elem){
		if(elem instanceof Entity){
			entities.remove(elem.getIdentifier());
		}else if(elem instanceof Attribute){
			attributes.remove(elem.getIdentifier());
		}else if(elem instanceof Union){
			unions.remove(elem.getIdentifier());
		}else if(elem instanceof Relation){
			relations.remove(elem.getIdentifier());
		}
	}
	
	public void removeElement(String key, int type){
		switch(type){
		case TYPE_ENTITY:
			entities.remove(key);
			break;
		case TYPE_UNION:
			unions.remove(key);
			break;
		case TYPE_ATTRIBUTE:
			attributes.remove(key);
			break;
		case TYPE_RELATION:
			relations.remove(key);
			break;
		}
	}	
	
	public List<ERItem> getERItems(){
		List<ERItem> elements = new ArrayList<ERItem>();
		elements.addAll(unions.values());
		elements.addAll(entities.values());
		elements.addAll(attributes.values());
		elements.addAll(relations.values());
		return elements;
	}
	
	public List<Element> getElements(){
		List<Element> elements = new ArrayList<Element>();
		elements.addAll(entities.values());
		elements.addAll(attributes.values());
		elements.addAll(relations.values());
		return elements;
	}
	
	public List<Union> getAllUnions(){
		List<Union> u = new ArrayList<Union>();
		u.addAll(unions.values());
		return u;
	}
	
	public List<Union> findUnion(String elementId){
		List<Union> locatedUnions = new ArrayList<Union>();
		for(Union u:unions.values())
			if(u.getInitialElement().equals(elementId) || u.getFinalElement().equals(elementId))
				locatedUnions.add(u);
		return locatedUnions;
	}
		
	public String onTouch(Point touched){
		if(touched == null)
			return null;		
		String selectedElement = null;
		for(Element e : getElements()){
			if(e.touched(touched, getInitPosition())){
				e.standOut();
				selectedElement = e.getIdentifier();
			}else{
				e.standIn();
			}
			if(!e.touched(touched, getInitPosition()) 
					|| e.touchedEditionPoint(touched, getInitPosition()) == Element.NO_POINT){
				e.finishEdition();
			}
		}
		return selectedElement;
	}
	
	public String onMove(Point touched, Point previousTouched) throws ERException{
		if(touched == null || previousTouched == null)
			return null;		
		String selectedElement = null;
		for(Element e : getElements()){
			if(e.touched(touched, initPosition)){
				selectedElement = e.getIdentifier();
				e.standOut();
				moveElement(e, touched, previousTouched);
			}else{
				e.standIn();
			}
		}
		if(selectedElement == null){
			scroll(touched, previousTouched);
		}
		return selectedElement;
	}
	
	public void moveElement(Element e, Point touched, Point previousTouched) throws ERException{	
		Point[] cornersAfterMove = e.getCornersAfterMove(touched, initPosition);
		if(cornersAfterMove[ERItem.CORNER_UP_LEFT].x < 0 || cornersAfterMove[ERItem.CORNER_UP_LEFT].y < 0)
			throw new OutOfDrawingRangeException(1, "");
		
		if(cornersAfterMove[ERItem.CORNER_BOTTOM_RIGHT].x > limitPoint.x || cornersAfterMove[ERItem.CORNER_BOTTOM_RIGHT].y > limitPoint.y)
			throw new OutOfDrawingRangeException(1, "");
		
		e.move(touched.x - previousTouched.x, touched.y - previousTouched.y);
		
		//TODO: arreglar el movimiento de los unions
		for(Union u : findUnion(e.getIdentifier()))
			if(u != null)
				u.move(touched.x - previousTouched.x, touched.y - previousTouched.y);
			
	}
	
	public void scroll(Point touched, Point previousTouched){
		int offsetX = touched.x - previousTouched.x;
		int offsetY = touched.y - previousTouched.y;
		initPosition.x = initPosition.x - offsetX;
		initPosition.y = initPosition.y - offsetY;
		if(initPosition.x < 0)
			initPosition.x = 0;
		if(initPosition.x > getLimit().x)
			initPosition.x = getLimit().x;
		if(initPosition.y < 0)
			initPosition.y = 0;
		if(initPosition.y > getLimit().y)
			initPosition.y = getLimit().y;
	}
	
	public String onLongPress(Point touched){
		for(Element e: getElements()){
			if(e.touched(touched, initPosition)){
				e.startEdition();
				return e.getIdentifier();
			}
		}
		return null;
	}
	
	public String toXML(){
		StringBuilder xml = new StringBuilder();
		Map<String, String> tagAttributes = new HashMap<String, String>();
		xml.append(ParserConsts.XML_HEAD);
		tagAttributes.put(ParserConsts.VERSION, String.valueOf(this.version));
		tagAttributes.put(ParserConsts.ID, this.identifier);
		xml.append(ParserUtils.openTag(ParserConsts.DIAGRAM, tagAttributes));
		
		xml.append(ParserUtils.simpleTag(ParserConsts.NAME, this.ERName, true, null));
		if(domains.size() > 0){
			xml.append(ParserUtils.openTag(ParserConsts.DOMAINS, null));
			for(Domain d : domains.values())
				xml.append(d.toXML());
			xml.append(ParserUtils.closeTag(ParserConsts.DOMAINS));
		}
		if(entities.size() > 0){
			xml.append(ParserUtils.openTag(ParserConsts.ENTITIES, null));
			for(Entity e : entities.values()){
				String sEnt = e.toXML();
				String atts = "";
				for(String att : e.getAttributeIds()){
					atts = atts + attributes.get(att).toXML();
				}
				sEnt.replace(ParserConsts.ATTRIBUTES_FLAG, atts);
				xml.append(sEnt);
			}
			xml.append(ParserUtils.closeTag(ParserConsts.ENTITIES));
		}
		
		if(relations.size() > 0){
			xml.append(ParserUtils.openTag(ParserConsts.RELATIONS, null));
			for(Relation r : relations.values()){
				String sRel = r.toXML();
				String atts = "";
				for(String att : r.getAttributeIds()){
					atts = atts + attributes.get(att).toXML();
				}
				sRel.replace(ParserConsts.ATTRIBUTES_FLAG, atts);
				xml.append(sRel);
			}
			xml.append(ParserUtils.closeTag(ParserConsts.RELATIONS));
		}
		xml.append(ParserUtils.closeTag(ParserConsts.DIAGRAM));
		return null;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}

