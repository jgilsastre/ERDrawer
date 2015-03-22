package es.test.jgilsastre.parser;

import java.util.Map;

/**
 * Lista de constantes para el parser
 * @author Jorge Gil Sastre
 * @version 0.1
 */
public class ParserConsts {

	
	/**
	 * Nodos comunes
	 */
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String TEXTSIZE = "textsize";
	public static final String POINT = "point";
	public static final String POINT_X = "x";
	public static final String POINT_Y = "y";
	public static final String PROPORTION = "proportion";
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	
	/**
	 * Nodos de diagrama
	 */
	public static final String DIAGRAM = "diagram";
	public static final String VERSION = "version";
	
	/**
	 * Nodos de dominios
	 */
	public static final String DOMAINS = "domains";
	public static final String DOMAIN = "domain";
	public static final String DATA_TYPE = "data_type";
	public static final String DEFAULT_VALUE = "default_value";
	public static final String RESTRICTIONS = "restrictions";
	public static final String RESTRICTION = "restriction";	
	
	/**
	 * Nodos de entidades
	 */
	public static final String ENTITIES = "entities";
	public static final String ENTITY = "entity";
	public static final String WEAK = "weak";
	
	/**
	 * Nodos y atributosde relaciones
	 */
	public static final String RELATIONS = "relations";
	public static final String RELATION = "relation";
	public static final String RELATED_ENTITIES = "related_entities";
	public static final String ENTITY_ID = "entity_id";	
	public static final String CARDINALITY = "cardinality";
	
	/**
	 * Nodos de atributos
	 */
	public static final String ATTRIBUTES = "attributes";
	public static final String ATTRIBUTE = "attribute";
	public static final String PRIMARY = "primary";
	public static final String NULLABLE = "nullable";
	public static final String UNIQUE = "unique";
	public static final String LENGTH = "length";
	public static final String ATT_DOMAIN = "att_domain";
	
	public static final String ATTRIBUTES_FLAG = "##ATTRIBUTES##";
	public static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
}
