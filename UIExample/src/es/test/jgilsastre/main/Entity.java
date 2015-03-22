package es.test.jgilsastre.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import es.test.jgilsastre.exception.ERException;
import es.test.jgilsastre.exception.MissingMandatoryParameterException;
import es.test.jgilsastre.parser.ParserConsts;
import es.test.jgilsastre.parser.ParserUtils;

public class Entity extends Element{

	/**
	 * Objetos necesarios para dibujar la entidad
	 */
	private Rect shadowRect;
	private Rect intRect;
	
	/**
	 * Lista de identificadores de los atributos relacionados con la entidad
	 */
	private List<String> attributeIds;
	
	/**
	 * Parametros relativos a si la entidad es debil (por defecto no lo es)
	 */
	private boolean weak;
	private String StrongRelationId;
	
	/**
	 * Construye una entidad
	 * @param identifier: Identificador de la entidad
	 * @param initialPoint: Posicion de la entidad (Punto central)
	 * @param name: Nombre de la entidad (Texto que aparece en la entidad)
	 * @param width: Anchura de la entidad en píxels
	 * @param height: Altura de la entidad en píxels
	 * @param weak: Es una entidad débil
	 * @param attributeIds: Lista de atributos de la entidad
	 */
	public Entity(String identifier, int elementNum, Point initialPoint, String name, int width, int height, ArrayList<String> attributeIds) {
		super("E_" + identifier + "_" + elementNum, initialPoint, width, height, name);
		//Creación del texto asociado en el centro del rectangulo
		this.weak = false;
		this.attributeIds = attributeIds;  
		this.StrongRelationId = "";
	}
	
	
	/**
	 * Construye una entidad con el identificador recibido
	 * @param identifier
	 */
	public Entity(String identifier, int elementNum){
		super("E_" + identifier + "_" + elementNum, "");
		this.weak = false;
		this.attributeIds = new ArrayList<String>();  
		this.StrongRelationId = "";
	}

	/**
	 * Construye una entidad a partir de un punto con una anchura y una altura por defecto
	 * @param identifier: Identificador de la entidad
	 * @param initialPoint: Posicion de la entidad (Punto central)
	 * @param name: Nombre de la entidad (Texto que aparece en la entidad)
	 */
	public Entity(String identifier, int elementNum, Point point, String name) {
		super("E_" + identifier + "_" + elementNum, point, ERItem.WIDTH_DEFAULT, ERItem.HEIGHT_DEFAULT, name);
		this.weak = false;
		this.attributeIds = new ArrayList<String>();  
		this.StrongRelationId = "";
	}


	@Override
	public boolean touched(Point touchedPoint, Point initialPoint) {
		boolean result = getShadowRect(initialPoint).contains(touchedPoint.x, touchedPoint.y);
		if(!result)
			this.finishEdition();
		return result;
	}
	
	/**
	 * Añade un atributo a la entidad
	 * @param attributeId
	 */
	public void addAttribute(String attributeId){
		attributeIds.add(attributeId);
	}
	
	/**
	 * Quita un atributo de la entidad
	 * @param attributeId
	 */
	public void removeAttribute(String attributeId){
		attributeIds.remove(attributeId);
	}
	
	/**
	 * Devuelve la lista de identificadores de los atributos relacionados con al entidad
	 * @return
	 */
	public List<String> getAttributeIds(){
		return attributeIds;
	}
	
	/**
	 * Develve si la entidad es debil
	 * @return cierto para entidad débil y falso en caso contrario
	 */
	public boolean isWeak() {
		return weak;
	}

	/**
	 * Convierte la entidad en débil y la asocia a la relación cuyo identificador se recibe
	 * @param weak: boolean para marcar que la entidad va a ser débil, si es cierto, el identificador de la relación asociada es obligatorio
	 * @param strongRelationId: identificador de la relación asociada, obligatorio cuando weak es cierto
	 * @throws ERException en caso de que no se reciba la relación asociada siendo weak cierto
	 */
	public void setWeak(boolean weak, String strongRelationId) throws ERException{
		if(weak){
			if(strongRelationId == null){
				weak = false;
				throw new MissingMandatoryParameterException(ERException.ERROR_CODE_MISSING_STRONG_RELATION_ID, "Can't make the entity " + getIdentifier() + " weak without a relation");
			}else{
				StrongRelationId = strongRelationId;
			}
		}else{
			StrongRelationId = null;
		}
		this.weak = weak;
	}

	public String getStrongRelationId() {
		return StrongRelationId;
	}

	@Override
	protected Point getTextPoint(Point point){
		return new Point(point.x ,point.y + (getHeight() / 10));
	}
	
	public Rect getShadowRect(Point initialPoint){
		Point relativePoint = getRelativePoint(getCentralPoint(), initialPoint);
		if(shadowRect == null)
			shadowRect = new Rect(relativePoint.x - (getWidth() / 2)
					, relativePoint.y - (getHeight() / 2)
					, relativePoint.x + (getWidth() / 2)
					, relativePoint.y + (getHeight() / 2));
		else
			shadowRect.set(relativePoint.x - (getWidth() / 2)
					, relativePoint.y - (getHeight() / 2)
					, relativePoint.x + (getWidth() / 2)
					, relativePoint.y + (getHeight() / 2));
		return shadowRect;
	}
	
	private Rect getInternalRect(Point initialPoint){
		Point relativePoint = getRelativePoint(getCentralPoint(), initialPoint);
		if(intRect == null)
			intRect = new Rect(relativePoint.x - (getWidth() / 2) - 2
					, relativePoint.y - (getHeight() / 2) - 2
					, relativePoint.x + (getWidth() / 2) - 2
					, relativePoint.y + (getHeight() / 2) - 2);
		else
			intRect.set(relativePoint.x - (getWidth() / 2) - 2
					, relativePoint.y - (getHeight() / 2) - 2
					, relativePoint.x + (getWidth() / 2) - 2
					, relativePoint.y + (getHeight() / 2) - 2);
		return intRect;
	}
	
	@Override
	public void draw(Canvas canvas, Point initialPoint){
		canvas.drawRect(getShadowRect(initialPoint), getShadowPaint());
		canvas.drawRect(getInternalRect(initialPoint), getIntPaint());
		getName().draw(canvas, initialPoint);
		if(this.isInEdition())
			drawEditionPoints(canvas, initialPoint);
	}


	@Override
	public String toXML() {
		StringBuilder xmlEnt = new StringBuilder();
		Map<String, String> tagAttributes = new HashMap<String, String>();
		tagAttributes.put(ParserConsts.ID, this.getIdentifier());
		xmlEnt.append(ParserUtils.openTag(ParserConsts.ENTITY, tagAttributes)); //Tag de inicio de la entidad
		tagAttributes.clear();
		tagAttributes.put(ParserConsts.POINT_X, String.valueOf(this.getCentralPoint().x));
		tagAttributes.put(ParserConsts.POINT_Y, String.valueOf(this.getCentralPoint().y));
		xmlEnt.append(ParserUtils.simpleTag(ParserConsts.POINT, null, false, tagAttributes));
		xmlEnt.append(this.getName().toXML()); //Tag del nombre
		xmlEnt.append(ParserUtils.simpleTag(ParserConsts.WEAK, this.weak?"1":"0", false, null));
		//TODO : falta lo de la entidad debil de relacion
		//xmlEnt.append(ParserUtils.simpleTag(ParserConsts.ATT_DOMAIN, this.domain, true, null));  
		tagAttributes.clear();
		tagAttributes.put(ParserConsts.WIDTH, String.valueOf(this.getWidth()));
		tagAttributes.put(ParserConsts.HEIGHT, String.valueOf(this.getHeight()));
		xmlEnt.append(ParserUtils.simpleTag(ParserConsts.PROPORTION, null, false, tagAttributes));
		if(attributeIds.size() > 0){
			xmlEnt.append(ParserUtils.openTag(ParserConsts.ATTRIBUTES, null));
			xmlEnt.append(ParserConsts.ATTRIBUTES_FLAG);
			xmlEnt.append(ParserUtils.closeTag(ParserConsts.ATTRIBUTES));
		}
		xmlEnt.append(ParserUtils.closeTag(ParserConsts.ENTITY));
		return xmlEnt.toString();
	}
}
