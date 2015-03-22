package es.test.jgilsastre.view.menu;

public class MenuItem {

	private String text;
	private int icon;
	
	/**
	 * @param title
	 * @param icon
	 */
	public MenuItem(String text, int icon) {
		super();
		this.text = text;
		this.icon = icon;
	}
	
	public String getText() {
		return text;
	}
	public int getIcon() {
		return icon;
	}
	
}
