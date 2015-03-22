package es.test.jgilsastre.view;

import es.test.jgilsastre.activities.MainActivity;

public class MainActivityControl implements Runnable {

	private MainActivity activity;
	private String selectedElement;
	
	public MainActivityControl(MainActivity act){
		activity = (MainActivity)act;
	}
		
	@Override
	public void run() {
		activity.handleButtons(selectedElement);
		
	}

	public String getSelectedElement() {
		return selectedElement;
	}

	public void setSelectedElement(String selectedElement) {
		this.selectedElement = selectedElement;
	}
	
	

}
