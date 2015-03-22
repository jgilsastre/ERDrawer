package es.test.jgilsastre.activities;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import es.test.jgilsastre.R;
import es.test.jgilsastre.view.menu.UtilMenu;

public class InitActivity extends Activity {

	private DrawerLayout navDrawerLayout;
	//private ListView navList;
	//private TypedArray navIcons;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init);
		//Obtener Drawer Layout
		navDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		UtilMenu.mountMenu(this);
	}
	
	
}
