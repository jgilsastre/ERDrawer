package es.test.jgilsastre.main;

import java.util.HashMap;
import java.util.Map;

import es.test.jgilsastre.parser.ParserConsts;
import es.test.jgilsastre.parser.ParserUtils;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;

public class Attribute extends Element {
	
	private int length;
	private String domain;
	private RectF intRectF;
	private RectF shadowrectF;
	private String relatedElementId;
	private boolean unique;
	private boolean primary;
	private boolean nullable;
	
	/**
	 * Constructor de un atributo
	 * @param identifier: Identificador del atributo
	 * @param initialPoint: Punto central desde donde se dibujará
	 * @param name: Nombre del atributo
	 * @param relatedEntityId: identificador de la entidad/relacion relacionada
	 * @param primary: el atributo es primario
	 * @param unique: el atributo es unico
	 * @param nullable: el atributo se puede dejar nulo o no
	 */
	public Attribute(String identifier, Point initialPoint,	String name, String relatedElementId, boolean primary, boolean unique, boolean nullable) {
		super(identifier, initialPoint);
		
		this.relatedElementId = relatedElementId;
	
		this.primary = primary;
		this.unique = unique;
		this.nullable = nullable;
				
	}
	
	/**
	 * Constructor de un atributo (not null por defecto)
	 * @param identifier: Identificador del atributo
	 * @param initialPoint: Punto central desde donde se dibujará
	 * @param name: Nombre del atributo
	 * @param relatedEntityId: identificador de la entidad/relacion relacionada
	 */
	public Attribute(String identifier, Point initialPoint,	String name, String relatedElementId) {
		this(identifier, initialPoint, name, relatedElementId, false, false, true);
	}
	
	public Attribute(String identifier){
		this(identifier, new Point(0,0), "", "");
	}

	
	
	


	@Override
	public boolean touched(Point touchedPoint, Point initialPoint) {
		if(shadowrectF != null)
			return shadowrectF.contains(touchedPoint.x, touchedPoint.y);
		else
			return false;
	}
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getRelatedEntityId() {
		return relatedElementId;
	}

	public void setRelatedElementId(String relatedEntityId) {
		this.relatedElementId = relatedEntityId;
	}
	

	public RectF getShadowRectF(){
		if(shadowrectF == null)
			shadowrectF = new RectF(getCentralPoint().x - (getWidth() / 2)
					, getCentralPoint().y - (getHeight() / 2)
					, getCentralPoint().x + (getWidth() / 2)
					, getCentralPoint().y + (getHeight() / 2));
		else
			shadowrectF.set(getCentralPoint().x - (getWidth() / 2)
					, getCentralPoint().y - (getHeight() / 2)
					, getCentralPoint().x + (getWidth() / 2)
					, getCentralPoint().y + (getHeight() / 2));
		return shadowrectF;
	}
	
	public RectF getInternalRectF(){
		if(intRectF == null)
			intRectF = new RectF(getCentralPoint().x - (getWidth() / 2) - 2
					, getCentralPoint().y - (getHeight() / 2) - 2
					, getCentralPoint().x + (getWidth() / 2) - 2
					, getCentralPoint().y + (getHeight() / 2) - 2);
		else
			intRectF.set(getCentralPoint().x - (getWidth() / 2) - 2
					, getCentralPoint().y - (getHeight() / 2) - 2
					, getCentralPoint().x + (getWidth() / 2) - 2
					, getCentralPoint().y + (getHeight() / 2) - 2);
		return intRectF;
	}
	
	
	public Point getTextPoint(Point point){
		return new Point(point.x ,point.y + (getHeight() / 10));
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique() {
		this.unique = true;
	}
	
	public void setNonUnique(){
		this.unique = false;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary() {
		this.primary = true;
	}
	
	public void setNonPrimary(){
		this.primary = false;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable() {
		this.nullable = true;
	}
	
	public void setNonNullable() {
		this.nullable = false;
	}

	@Override
	public void draw(Canvas canvas, Point initialPoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toXML() {
		StringBuilder xmlAtt = new StringBuilder();
		Map<String, String> tagAttributes = new HashMap<String, String>();
		tagAttributes.put(ParserConsts.ID, this.getIdentifier());
		tagAttributes.put(ParserConsts.PRIMARY, this.primary?"1":"0");
		tagAttributes.put(ParserConsts.NULLABLE, this.nullable?"1":"0");
		tagAttributes.put(ParserConsts.UNIQUE, this.unique?"1":"0");
		xmlAtt.append(ParserUtils.openTag(ParserConsts.ATTRIBUTE, tagAttributes)); //Tag de inicio del atributo
		tagAttributes.clear();
		tagAttributes.put(ParserConsts.POINT_X, String.valueOf(this.getCentralPoint().x));
		tagAttributes.put(ParserConsts.POINT_Y, String.valueOf(this.getCentralPoint().y));
		xmlAtt.append(ParserUtils.simpleTag(ParserConsts.POINT, null, false, tagAttributes));
		xmlAtt.append(this.getName().toXML()); //Tag del nombre
		xmlAtt.append(ParserUtils.simpleTag(ParserConsts.LENGTH, String.valueOf(this.length), false, null));
		xmlAtt.append(ParserUtils.simpleTag(ParserConsts.ATT_DOMAIN, this.domain, true, null));
		tagAttributes.clear();
		tagAttributes.put(ParserConsts.WIDTH, String.valueOf(this.getWidth()));
		tagAttributes.put(ParserConsts.HEIGHT, String.valueOf(this.getHeight()));
		xmlAtt.append(ParserUtils.simpleTag(ParserConsts.PROPORTION, null, false, tagAttributes));
		xmlAtt.append(ParserUtils.closeTag(ParserConsts.ATTRIBUTE));
		return xmlAtt.toString();
	}
	
}
