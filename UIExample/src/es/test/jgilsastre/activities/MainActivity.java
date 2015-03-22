package es.test.jgilsastre.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;
import es.test.jgilsastre.R;
import es.test.jgilsastre.activities.adding.SelectTypeDialog;
import es.test.jgilsastre.exception.ERException;
import es.test.jgilsastre.main.Diagram;
import es.test.jgilsastre.main.Domain;
import es.test.jgilsastre.main.Element;
import es.test.jgilsastre.main.Entity;
import es.test.jgilsastre.main.Relation;
import es.test.jgilsastre.util.ParamsConst;
import es.test.jgilsastre.view.MainSurface;

public class MainActivity extends Activity {

	private ImageButton btnAdd;
	private ImageButton btnRemove;
	private ImageButton btnEdit;
	private Element elementSelected;
	private Diagram diagram;
	private int idHead;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //oculto titulo 
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //ocultamos barras de notifiaciones       
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.init);
        
//        InputStream input =getResources().openRawResource(R.raw.ejemplo);
//        Diagram d1 = DownloadHelper.parseDiagram(input);
//        d1.getDomain("dom1");
        elementSelected = null;
        diagram = new Diagram("test", "Testing", null, null);
        idHead = diagram.hashCode();
        
        MainSurface mainSurface = new MainSurface(this, diagram);
        FrameLayout frm = (FrameLayout)findViewById(R.id.frame);
        frm.addView(mainSurface);
        
        btnAdd = (ImageButton) findViewById(R.id.addBtn);
        btnRemove = (ImageButton) findViewById(R.id.removeBtn);
        
        btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), SelectTypeDialog.class);
				intent.putExtra(ParamsConst.CREATE_ATTRIBUTES_ENABLED, (Boolean)(elementSelected instanceof Entity || elementSelected instanceof Relation));
				intent.putExtra(ParamsConst.RELATIONS, "id1::relacion1##id2::relacion2");
				intent.putExtra(ParamsConst.ENTITIES, "id1::entity1##id2::entity2");
				String algo = diagram.getDomainsSerialized();
				intent.putExtra(ParamsConst.DOMAINS, algo);
				//TODO: pasar tambien las entidades serializadas para 
				startActivityForResult(intent, ParamsConst.RC_ADD_DIALOG);
			}
		});
        
        btnRemove.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Eliminar", Toast.LENGTH_SHORT).show();
				
			}
		});
        btnRemove.setVisibility(Button.INVISIBLE);
        //btnEdit.setVisibility(Button.INVISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.init, menu);
        return true;
    }
    
    public void manageRemoveButtonVisibility(boolean visibility){
    	btnRemove.setVisibility(visibility?Button.VISIBLE:Button.INVISIBLE);
    }
    
    public void handleButtons(String elementSelectedId){
    	if(elementSelectedId != null && !"".equals(elementSelectedId)){
    		elementSelected = diagram.getElement(elementSelectedId);
    		if(elementSelected != null){
    			btnRemove.setVisibility(Button.VISIBLE);
    	        //btnEdit.setVisibility(Button.VISIBLE);
    		}
    	}else{
    		//Ocultar los botones que necesitan seleccion y poner la marca para no mostrar el botón de añadir atributo
    		btnRemove.setVisibility(Button.INVISIBLE);
            //btnEdit.setVisibility(Button.INVISIBLE);
    	}
    }


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == ParamsConst.RC_ADD_DIALOG){
			//sActivity.resu
			if(resultCode == RESULT_OK){
				String elementName = data.getStringExtra(ParamsConst.ELEMENT_NAME);
				int elementType = data.getIntExtra(ParamsConst.ELEMENT_TYPE, -1);
				
				switch (elementType){
				case ParamsConst.ELEMENT_TYPE_DOMAIN:
					String defValue = data.getStringExtra(ParamsConst.DOMAIN_DEFVALUE);
					String restrictions = data.getStringExtra(ParamsConst.DOMAIN_RESTRICTIONS);
					Domain dom = new Domain(String.valueOf(idHead) + "_" + diagram.getElementCounter(), elementName, defValue);
					if(restrictions.length() > 0)
						for(String s : restrictions.split("##"))
							dom.addRestriction(s);
					diagram.putDomain(dom);
					break;
				case ParamsConst.ELEMENT_TYPE_ENTITY:
					Entity ent = new Entity(String.valueOf(idHead), diagram.getElementCounter(), new Point(diagram.getDiagramCenter()), elementName);
					if(data.getBooleanExtra(ParamsConst.ENTITY_WEAK, false)){
						try {
							ent.setWeak(true, data.getStringExtra(ParamsConst.WEAK_OF_RELATION));
						} catch (ERException e) {}
					}
					diagram.putElement(ent);
					break;
				case ParamsConst.ELEMENT_TYPE_RELATION:
					
					break;
				case ParamsConst.ELEMENT_TYPE_ATTRIBUTE:
					break;
				}
				
				if(elementType != -1){
					if(elementType == ParamsConst.ELEMENT_TYPE_ENTITY){
						
					}
				}
				Toast.makeText(getApplicationContext(), elementName, Toast.LENGTH_SHORT).show();
			}
		}
	}

    
}
