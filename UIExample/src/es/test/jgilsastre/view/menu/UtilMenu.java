package es.test.jgilsastre.view.menu;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.TypedArray;
import android.view.View;
import android.widget.ListView;
import es.test.jgilsastre.R;

public class UtilMenu {

	public static void mountMenu(Activity activity){
		//Obtener la lista
		ListView navList = (ListView) activity.findViewById(R.id.lista);
		//Declarar el header y asignarlo a la lista
		View header = activity.getLayoutInflater().inflate(R.layout.menu_header, null);
		navList.addHeaderView(header);
		//Obtener los valores para la lista
		TypedArray navIcons = activity.getResources().obtainTypedArray(R.array.menu_icons);
		String[] navTexts = activity.getResources().getStringArray(R.array.menu_options);
		ArrayList<MenuItem> items = new ArrayList<MenuItem>();
		for(int i=0;i<navTexts.length;i++)
			items.add(new MenuItem(navTexts[i], navIcons.getResourceId(i, -1)));
		
		navIcons.recycle();
		
		NavigationAdapter navAdapter = new NavigationAdapter(activity, items);
		navList.setAdapter(navAdapter);
	}
	
}
