package es.test.jgilsastre.util;

public class SpinnerAdapterItem {

	private int id;
	private String identifier;
	private String name;
	
	/**
	 * @param id
	 * @param name
	 */
	public SpinnerAdapterItem(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public SpinnerAdapterItem(String identifier, String name){
		super();
		this.identifier = identifier;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
}
