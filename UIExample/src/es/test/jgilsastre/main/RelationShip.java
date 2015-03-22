package es.test.jgilsastre.main;

public class RelationShip {

	private String entity;
	private String cardinality;
	
	/**
	 * @param entity
	 * @param cadinality
	 */
	public RelationShip(String entity, String cadinality) {
		super();
		this.entity = entity;
		this.cardinality = cadinality;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getCardinality() {
		return cardinality;
	}
	public void setCardinality(String cadinality) {
		this.cardinality = cadinality;
	}
	
	
}
