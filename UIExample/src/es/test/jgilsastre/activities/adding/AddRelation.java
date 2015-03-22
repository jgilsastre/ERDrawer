package es.test.jgilsastre.activities.adding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import es.test.jgilsastre.R;
import es.test.jgilsastre.main.Element;
import es.test.jgilsastre.util.AssociationAdapterItem;
import es.test.jgilsastre.util.ParamsConst;
import es.test.jgilsastre.util.SpinnerAdapterItem;

public class AddRelation extends Activity {

	private Intent intent;
	private Context context;
	/**
	 * Botones aceptar/cancelar
	 */
	private Button btnAccept;
	private Button btnCancel;
	
	private EditText elementName;
	
	private ImageButton btnAddAssoc;
//	private ImageButton btnEditAssoc;
	private ImageButton btnRemoveAssoc;
	private ImageButton btnAcceptAssoc;
	
	private ArrayAdapter<SpinnerAdapterItem> entities;
	private ArrayAdapter<SpinnerAdapterItem> assocAdapter;
	
	private LinearLayout llAddAssociation;
	private Spinner spAssociations;
	private Spinner spEntities;
	
	private RadioButton rbAssoc0;
	private RadioButton rbAssoc1;
	private RadioButton rbAssocN;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adding_relation);
		
		intent = getIntent();
		context = this;
		
		getData(intent.getExtras());
		prepareMainControls();
		prepareEndButtons();
		prepareAssociations();
		
	}
	
	private void getData(Bundle params) {
		//entities = new AraryAdapter<SpinnerAdapterItem>();
		entities = new ArrayAdapter<SpinnerAdapterItem>(context,  android.R.layout.simple_spinner_item);
		entities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		String data = params.getString(ParamsConst.ENTITIES);
		if(data != null && !"".equals(data)){
			String[] elems = data.split(Element.ELEMENT_SEPARATOR);
			for(String e : elems){
				String[] pair = e.split(Element.ELEMENT_ID_NAME_SEPARATOR);
				if(pair.length == 2)
					entities.add(new SpinnerAdapterItem(pair[0], pair[1]));
			}
		}
	}

	private boolean checkErrors(){
		boolean error = false;
		if("".equals(elementName.getText().toString())){
			error = true;
			Toast.makeText(context, getString(R.string.error_name_needed), Toast.LENGTH_LONG).show();
		}
		return error;
	}
	private void prepareMainControls() {
		elementName = (EditText) findViewById(R.id.etName);
		
	}

	private void prepareEndButtons() {
		btnAccept = (Button) findViewById(R.id.btnAccept);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		
		btnAccept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!checkErrors()) {
					intent.putExtra(ParamsConst.ELEMENT_TYPE, ParamsConst.ELEMENT_TYPE_DOMAIN);
					intent.putExtra(ParamsConst.ELEMENT_NAME, elementName.getText().toString());
					StringBuilder sAssocs = new StringBuilder();
					for(int i=0; i<assocAdapter.getCount();i++){
						sAssocs.append(assocAdapter.getItem(i).toString());
						if(i != assocAdapter.getCount() - 1)
							sAssocs.append("##");
					}
					intent.putExtra(ParamsConst.RELATION_ASSOCIATIONS, sAssocs.toString());
					setResult(RESULT_OK, intent);
					finish();
				} 
			}
		});
		
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED, intent);
				finish();
			}
		});
	}
	
	private void prepareAssociations() {
		llAddAssociation = (LinearLayout) findViewById(R.id.optionAddAssoc);
		spAssociations = (Spinner) findViewById(R.id.spAssociations);
		spEntities = (Spinner) findViewById(R.id.spEntityAssoc);
		btnAddAssoc = (ImageButton) findViewById(R.id.btnAddAssoc);
//		btnEditAssoc = (ImageButton) findViewById(R.id.btnEditAssoc);
		btnRemoveAssoc = (ImageButton) findViewById(R.id.btnRemoveAssoc);
		btnAcceptAssoc = (ImageButton) findViewById(R.id.btnAcceptAssoc);
		rbAssoc0 = (RadioButton) findViewById(R.id.rbAssoc0);
		rbAssoc1 = (RadioButton) findViewById(R.id.rbAssoc1);
		rbAssocN = (RadioButton) findViewById(R.id.rbAssocN);
		
		assocAdapter = new ArrayAdapter<SpinnerAdapterItem>(context,  android.R.layout.simple_spinner_item);
		assocAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spAssociations.setAdapter(assocAdapter);
		
		spEntities.setAdapter(entities);
		
//		btnEditAssoc.setEnabled(false);
		btnRemoveAssoc.setEnabled(false);
		
		btnAddAssoc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				llAddAssociation.setVisibility(LinearLayout.VISIBLE);
			}
		});
		
//		btnEditAssoc.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if(spAssociations.getSelectedItem() != null){
//					llAddAssociation.setVisibility(LinearLayout.VISIBLE);
//					SpinnerAdapterItem item = (SpinnerAdapterItem)spAssociations.getSelectedItem();
//					editingAssoc = item.getId();
//					
//					//spEntities.setSelection();
//					//TODO buscar la entidad de esa asociacion y marcarla en el spinner
//				}
//			}
//		});
		
		btnRemoveAssoc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(spAssociations.getSelectedItem() != null){
					assocAdapter.remove((SpinnerAdapterItem)spAssociations.getSelectedItem());
					spAssociations.setAdapter(assocAdapter);
					if(assocAdapter.getCount() == 0){
//						btnEditAssoc.setEnabled(false);
						btnRemoveAssoc.setEnabled(false);
					}				
				}
			}
		});
		
		btnAcceptAssoc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				btnEditAssoc.setEnabled(true);
				btnRemoveAssoc.setEnabled(true);
				SpinnerAdapterItem selected = (SpinnerAdapterItem)spEntities.getSelectedItem();
				String cardinality = "";
				if(rbAssoc0.isChecked())
					cardinality = "0";
				else if(rbAssoc1.isChecked())
					cardinality = "1";
				else
					cardinality = "N";
				AssociationAdapterItem assoc = new AssociationAdapterItem(assocAdapter.getCount() + 1, selected.getName(), cardinality);
				assocAdapter.add(assoc);
				spAssociations.setAdapter(assocAdapter);
				llAddAssociation.setVisibility(LinearLayout.INVISIBLE);
//				assocAdapter.add(selectedEntity);
				
//				if(!"".equals(restrictionValue.getText().toString())){
//					if(editingRestriction == -1){
//						SpinnerAdapterItem newItem = new SpinnerAdapterItem(adapter.getCount() + 1, restrictionValue.getText().toString());
//						adapter.add(newItem);
//					}else{
//						for(int i=0;i<adapter.getCount();i++){
//							if(adapter.getItem(i).getId() == editingRestriction)
//								adapter.getItem(i).setName(restrictionValue.getText().toString());
//						}
//					}
//					spRestrictions.setAdapter(adapter);
//					restrictionValue.getText().clear();
//					llAddRestriction.setVisibility(LinearLayout.INVISIBLE);
//				}else{
//					Toast.makeText(context, getString(R.string.error_name_needed), Toast.LENGTH_LONG).show();
//				}
					
			}
		});
	}
		
}
