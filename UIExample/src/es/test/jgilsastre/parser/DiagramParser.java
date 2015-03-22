package es.test.jgilsastre.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

import android.graphics.Point;
import es.test.jgilsastre.main.Attribute;
import es.test.jgilsastre.main.Diagram;
import es.test.jgilsastre.main.Domain;
import es.test.jgilsastre.main.Entity;
import es.test.jgilsastre.main.Relation;
import es.test.jgilsastre.main.RelationShip;

public class DiagramParser extends DefaultHandler implements LexicalHandler{

	private Diagram diagram;
	private Domain domain;
	private Entity entity;
	private Attribute attribute;
	private Relation relation;
	private RelationShip relationShip;
	
	/**
	 * Indicador común a varios elementos
	 */
	private boolean inName = false;
	/**
	 * Indicador de Diagrama
	 */
	private boolean inDiagram = false;
	
	/**
	 * Indicadores de dominio
	 */
	private boolean inDomains = false;
	private boolean inDomain = false;
	private boolean inRestrictions = false;
	private boolean inRestriction = false;
	private boolean inDataType = false;
	private boolean inDefaultValue = false;
	
	/**
	 * Indicadores de entidades
	 */
	private boolean inEntities = false;
	private boolean inEntity = false;
	private boolean inWeak = false;
	
	/**
	 * Indicadores de relaciones
	 */
	private boolean inRelations = false;
	private boolean inRelation = false;
	private boolean inRelatedEntities = false;
	private boolean inEntityId = false;
	
	/**
	 * Indicadores de atributos
	 */
	private boolean inAttributes = false;
	private boolean inAttribute = false;
	private boolean inLength = false;
	private boolean inAttDomain = false;
	
//	private int elementCounter = 0;
		
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		try{
			if (localName.equalsIgnoreCase(ParserConsts.DIAGRAM)){
				diagram = startDiagram(attributes);
				inDiagram = true;
			} else if(localName.equalsIgnoreCase(ParserConsts.NAME)){
				//TODO: leer y montar el tamaño del texto
				inName = true;
			} else if(localName.equalsIgnoreCase(ParserConsts.POINT)){
				if(inAttribute)
					attribute.setPoint(readPoint(attributes));
				else if(inEntity)
					entity.setPoint(readPoint(attributes));
				else if(inRelation)
					relation.setPoint(readPoint(attributes));
				else
					throw new SAXException("Wrong file: Node Point can't be assigned");
			} else if(localName.equalsIgnoreCase(ParserConsts.PROPORTION)){
				if(inAttribute)
					attribute.setProportion(readProportions(attributes));
				else if(inEntity)
					entity.setProportion(readProportions(attributes));
				else if(inRelation)
					relation.setProportion(readProportions(attributes));
				else
					throw new SAXException("Wrong file: Node proportion can't be assigned");
			} else if(localName.equalsIgnoreCase(ParserConsts.DOMAINS)){
				if(!inDiagram) throw new SAXException("Wrong file: Node domains is outside of the diagram");
				inDomains = true;
			} else if(localName.equalsIgnoreCase(ParserConsts.DOMAIN)){
				if(!inDiagram || !inDomains) throw new SAXException("Wrong file: Node domain is outside of domains");
				domain = startDomain(attributes);
				inDomain = true;
			} else if(localName.equalsIgnoreCase(ParserConsts.DATA_TYPE)){
				if(!inDiagram || !inDomains || !inDomain) throw new SAXException("Wrong file: Node data_type is outside of a domain");
				inDataType = true;
			} else if(localName.equalsIgnoreCase(ParserConsts.DEFAULT_VALUE)){
				if(!inDiagram || !inDomains || !inDomain) throw new SAXException("Wrong file: Node default_value is outside of a domain");
				inDefaultValue = true;
			} else if(localName.equalsIgnoreCase(ParserConsts.RESTRICTIONS)){
				if(!inDiagram || !inDomains || !inDomain) throw new SAXException("Wrong file: Node restrictions is outside of a domain");
				inRestrictions = true;
			} else if(localName.equalsIgnoreCase(ParserConsts.RESTRICTION)){
				if(!inDiagram || !inDomains || !inDomain || !inRestrictions) throw new SAXException("Wrong file: Node restriction is outside of a domain");
				inRestriction = true;
			} else if(localName.equalsIgnoreCase(ParserConsts.ATTRIBUTES)){
				if(!inDiagram || (!inEntity && !inRelation)) throw new SAXException("Wrong file: Node attributes is outside an entity or relation node");
				inAttributes = true;	
			} else if(localName.equalsIgnoreCase(ParserConsts.ATTRIBUTE)){
				if(!inDiagram || !inAttributes) throw new SAXException("Wrong file: Node attribute is outside of an attributes node");		
				attribute = startAttribute(attributes);
				inAttribute = true;
			} else if(localName.equalsIgnoreCase(ParserConsts.LENGTH)){
				if(!inDiagram || !inAttribute) throw new SAXException("Wrong file: Node length is outside of an attribute node");
				inLength = true;
			} else if(localName.equalsIgnoreCase(ParserConsts.ATT_DOMAIN)){
				if(!inDiagram || !inAttribute) throw new SAXException("Wrong file: Node att_domain is outside of an attribute node");
				inAttDomain = true;
			} else if(localName.equalsIgnoreCase(ParserConsts.ENTITIES)){
				if(!inDiagram) throw new SAXException("Wrong file: Node entities isn't inside the diagram");
				inEntities = true;	
			} else if(localName.equalsIgnoreCase(ParserConsts.ENTITY)){
				if(!inDiagram || !inEntities) throw new SAXException("Wrong file: Node entity isn't inside the entities node");
				entity = startEntity(attributes);
				inEntity = true;	
			} else if(localName.equalsIgnoreCase(ParserConsts.WEAK)){
				if(!inDiagram || !inEntities || !inEntity) throw new SAXException("Wrong file: Node weak isn't inside an entity node");
				inWeak = true;	
			} else if(localName.equalsIgnoreCase(ParserConsts.RELATIONS)){
				if(!inDiagram) throw new SAXException("Wrong file: Node relations isn't inside the diagram");
				inRelations = true;	
			} else if(localName.equalsIgnoreCase(ParserConsts.RELATION)){
				if(!inDiagram || !inRelations) throw new SAXException("Wrong file: Node relation isn't inside the relations node");
				relation = startRelation(attributes);
				inRelation = true;	
			} else if(localName.equalsIgnoreCase(ParserConsts.RELATED_ENTITIES)){
				if(!inDiagram || !inRelations || !inRelation) throw new SAXException("Wrong file: Node related_entities is outside a relation");
				inRelatedEntities = true;	
			} else if(localName.equalsIgnoreCase(ParserConsts.ENTITY_ID)){
				if(!inDiagram || !inRelations || !inRelation || !inRelatedEntities) throw new SAXException("Wrong file: Node entity_id is not inside a related_entities node");
				relationShip = new RelationShip("", readCardinality(attributes));
				inEntityId = true;	
			} else {
				throw new SAXException("Wrong file: Unknown node: " + localName);
			}
		}catch (Exception e){
			throw new SAXException(e.getMessage());
		}
		super.startElement(uri, localName, qName, attributes);
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		try{
			if(localName.equalsIgnoreCase(ParserConsts.DIAGRAM)){  //Se ha terminado el XML
				inDiagram = false;
			} else if(localName.equalsIgnoreCase(ParserConsts.NAME)){
				inName = false;
			} else if(localName.equalsIgnoreCase(ParserConsts.DOMAIN)){
				if(diagram != null && domain != null) 
					diagram.putDomain(domain); 
				else 
					throw new SAXException("Error adding a domain to the diagram");
				inDomain = false;
			} else if(localName.equalsIgnoreCase(ParserConsts.DOMAINS)){
				inDomains = false;
			} else if (localName.equalsIgnoreCase(ParserConsts.DATA_TYPE)){
				inDataType = false;
			} else if (localName.equalsIgnoreCase(ParserConsts.DEFAULT_VALUE)){
				inDefaultValue = false;
			} else if (localName.equalsIgnoreCase(ParserConsts.RESTRICTIONS)){
				inRestrictions = false;
			} else if (localName.equalsIgnoreCase(ParserConsts.DATA_TYPE)){
				inRestriction = false;
			} else if (localName.equalsIgnoreCase(ParserConsts.ATTRIBUTES)){
				inAttributes = false;
			} else if (localName.equalsIgnoreCase(ParserConsts.ATTRIBUTE)){
				if(inEntity && !inRelation && entity != null && attribute != null)
					entity.addAttribute(attribute.getIdentifier());
				else if(inRelation && !inEntity && relation != null && attribute != null)
					relation.addAttribute(attribute.getIdentifier());
				if(diagram != null && attribute != null)
					diagram.putElement(attribute);
				else
					throw new SAXException("Error adding an attribute to the diagram");
			} else if (localName.equalsIgnoreCase(ParserConsts.LENGTH)){
				inLength = false;
			} else if (localName.equalsIgnoreCase(ParserConsts.ATT_DOMAIN)){
				inAttDomain= false;
			} else if (localName.equalsIgnoreCase(ParserConsts.ENTITIES)){
				inEntities = false;
			} else if (localName.equalsIgnoreCase(ParserConsts.ENTITY)){
				if(diagram != null && entity != null)
					diagram.putElement(entity);
				else
					throw new SAXException("Error adding an entity to the diagram");
				inEntity = false;
			} else if (localName.equalsIgnoreCase(ParserConsts.WEAK)){
				inWeak = false;
			} else if (localName.equalsIgnoreCase(ParserConsts.RELATIONS)){
				inRelations = false;
			} else if (localName.equalsIgnoreCase(ParserConsts.RELATION)){
				if(diagram != null && entity != null)
					diagram.putElement(entity);
				else
					throw new SAXException("Error adding an entity to the diagram");
				inRelation = false;
			} else if (localName.equalsIgnoreCase(ParserConsts.RELATED_ENTITIES)){
				inRelatedEntities = false;
			} else if (localName.equalsIgnoreCase(ParserConsts.ENTITY_ID)){
				relation.getRelationships().add(relationShip);
				inEntityId = false;
			}
		}catch (Exception e){
			throw new SAXException(e.getMessage());
		}
		super.endElement(uri, localName, qName);
	}

	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		try{
			String word = new String(ch, start, length);
			
			if(inDiagram && !inDomains && !inEntities && !inRelations && inName ) //Nombre del diagrama
				diagram.rename(word);
			if(inDiagram && !inEntities && !inRelations && inDomains && inDomain && inName) //Nombre de un dominio
				domain.setName(word);
//			if(inDiagram && !inEntities && !inRelations && inDomains && inDomain && inDataType) //Dominio.tipodedato
//				domain.setDataType(word);
			if(inDiagram && !inEntities && !inRelations && inDomains && inDomain && inDefaultValue) //Dominio.Valorpordefecto
				domain.setDefaultValue(word);
			if(inDiagram && !inEntities && !inRelations && inDomains && inDomain && inRestrictions && inRestriction) //Dominio.restriccion
				domain.addRestriction(word);
			if(inDiagram && inEntities && !inRelations && !inDomains && inEntity && inName) //Entidad.nombre
				entity.setName(word);
			if(inDiagram && inEntities && !inRelations && !inDomains && inEntity && inWeak) //Entidad.debil
//				entity.setWeak("1".equals(word));
			if(inDiagram && !inDomains && inAttributes && inAttribute && inName) //Atributo.nombre
				attribute.setName(word);
			if(inDiagram && !inDomains && inAttributes && inAttribute && inLength) //Atributo.tamaño
				attribute.setLength(Integer.parseInt(word));
			if(inDiagram && !inDomains && inAttributes && inAttribute && inAttDomain) //Atributo.dominio
				attribute.setDomain(word); 
			if(inDiagram && !inDomains && !inEntities && inRelations && inRelations && inName)  //Relacion.nombre
				relation.setName(word);
			if(inDiagram && !inDomains && !inEntities && inRelations && inRelations && inEntityId)  //Relacion.idDeLaEntidadRelacionada
				relationShip.setEntity(word);
		}catch (Exception e){
			throw new SAXException(e.getMessage());
		}
		super.characters(ch, start, length);
	}

	@Override
	public void comment(char[] ch, int start, int length) throws SAXException {}

	@Override
	public void endCDATA() throws SAXException {}

	@Override
	public void endDTD() throws SAXException {}

	@Override
	public void endEntity(String name) throws SAXException {}

	@Override
	public void startCDATA() throws SAXException {}

	@Override
	public void startDTD(String name, String publicId, String systemId)
			throws SAXException {}

	@Override
	public void startEntity(String name) throws SAXException {}

	public Diagram getDiagram(){
		return diagram;
	}
	
	/**
	 * Metodo que inicia la lectura de un atributo
	 * @param attributes = Lista de atributos del nodo attribute
	 * @return atributo con los datos básicos para seguir rellenandolo
	 * @throws SAXException
	 */
	private Attribute startAttribute(Attributes attributes) throws SAXException{
		Attribute att = null;
		try{
			if(attributes.getLength() > 1){
				att = new Attribute(attributes.getValue(attributes.getIndex(ParserConsts.ID)));
				String primary = attributes.getValue(attributes.getIndex(ParserConsts.PRIMARY));
				if(primary.equals("1"))
					att.setPrimary();
				else
					att.setNonPrimary();
				if(attributes.getIndex(ParserConsts.NULLABLE) != -1){
					if(attributes.getValue(attributes.getIndex(ParserConsts.NULLABLE)).equals("1"))
						att.setNullable();
					else
						att.setNonNullable();
				}
				if(attributes.getIndex(ParserConsts.UNIQUE) != -1){
					if(attributes.getValue(attributes.getIndex(ParserConsts.UNIQUE)).equals("1"))
						att.setUnique();
					else
						att.setNonUnique();
				}
			}else{
				throw new SAXException("Wrong file: Attribute missed parameters");
			}
		}catch(Exception e){
			throw new SAXException("Error parsing parameteres of an attribute");
		}
		if(inEntity)
			att.setRelatedElementId(entity.getIdentifier());
		else if(inRelation)
			att.setRelatedElementId(relation.getIdentifier());
		return att;
	}
	
	private Domain startDomain(Attributes attributes) throws SAXException{
		if(attributes.getLength() == 1)
			return new Domain(attributes.getValue(attributes.getIndex(ParserConsts.ID)));
		else
			throw new SAXException("Wrong file: missed entity id ");
	}
	
	/**
	 * Inicia la lectura de un diagrama
	 * @param attributes: Lista de atributos del nodo diagram
	 * @return El diagrama iniciado
	 * @throws SAXException
	 */
	private Diagram startDiagram(Attributes attributes) throws SAXException{
		Diagram d = null;
		try{
			if (attributes.getLength() == 2){
				String attId = attributes.getValue(attributes.getIndex(ParserConsts.ID));
				double attVersion = Double.parseDouble(attributes.getValue(attributes.getIndex(ParserConsts.VERSION)));
				//TODO: leer las fechas y colocarlas en el constructor
				if(attId != null && !attId.equals(""))
					d = new Diagram(attId, "", attVersion, null, null);
			}else{
				throw new SAXException("The diagram has no identifier or version");
			}
		}catch(NumberFormatException e){
			throw new SAXException("Error parsing version");
		}
		return d;
	}
	
	/**
	 * Inicia la lectura de una nueva entidad
	 * @param attributes: Lista de atributos del nodo entidad
	 * @return Una entidad
	 * @throws SAXException
	 */
	private Entity startEntity(Attributes attributes) throws SAXException{
		if(attributes.getLength() == 1)
			return new Entity(attributes.getValue(attributes.getIndex(ParserConsts.ID)), -1);
		else
			throw new SAXException("Wrong file: missed entity id");
	}
	
	/**
	 * Inicia la lectura de una relación
	 * @param attributes: Lista de atributos del nodo relation
	 * @return
	 * @throws SAXException
	 */
	private Relation startRelation(Attributes attributes) throws SAXException{
		if(attributes.getLength() == 1)
			return new Relation(attributes.getValue(attributes.getIndex(ParserConsts.ID)));
		else
			throw new SAXException("Wrong file: missed relation id ");
	}
	
	/**
	 * Lee un punto
	 * @param attributes
	 * @throws SAXException
	 */
	private Point readPoint(Attributes attributes) throws SAXException{
		try{
			if(attributes.getLength() == 2)
				return new Point(Integer.parseInt(attributes.getValue(attributes.getIndex(ParserConsts.POINT_X)))
						, Integer.parseInt(attributes.getValue(attributes.getIndex(ParserConsts.POINT_Y))));
			else
				throw new SAXException("Wrong file: Point missed parameter");
		}catch(NumberFormatException e){
			throw new SAXException("Error parsing point");
		}
	}
	
	/**
	 * Lee las proporciones de un elemento
	 * @param attributes
	 * @return
	 * @throws SAXException
	 */
	private int[] readProportions(Attributes attributes) throws SAXException{
		int[] proportions = new int[2];
		try{
			if(attributes.getLength() == 2){
				proportions[0] = Integer.parseInt(attributes.getValue(attributes.getIndex(ParserConsts.WIDTH)));
				proportions[1] = Integer.parseInt(attributes.getValue(attributes.getIndex(ParserConsts.HEIGHT)));
			}else{
				throw new SAXException("Wrong file: Proportion missed parameter");
			}
		}catch(NumberFormatException e){
			throw new SAXException("Error parsing proportions");
		}
		return proportions;
	}
	
	private String readCardinality(Attributes attributes) throws SAXException{
		if(attributes.getLength() == 1)
			return attributes.getValue(attributes.getIndex(ParserConsts.CARDINALITY));
		else
			throw new SAXException("Wrong file: missed cardinality of a relation ");	
	}

}
