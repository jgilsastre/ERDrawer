package es.test.jgilsastre.util;

public class AssociationAdapterItem extends SpinnerAdapterItem {

	private String cardinality;
	
	public AssociationAdapterItem(int id, String name, String cardinality) {
		super(id, name);
		this.cardinality = cardinality;
	}

	public String getCardinality() {
		return cardinality;
	}

	public void setCardinality(String cardinality) {
		this.cardinality = cardinality;
	}

	@Override
	public String toString() {
		return "[" + getName() + "]:[" + cardinality + "]";
	}
		
}
