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
import android.widget.Spinner;
import android.widget.Toast;
import es.test.jgilsastre.R;
import es.test.jgilsastre.util.ParamsConst;
import es.test.jgilsastre.util.SpinnerAdapterItem;

public class AddDomain extends Activity {

	private Intent intent;
	private Context context;
	/**
	 * Botones aceptar/cancelar
	 */
	private Button btnAccept;
	private Button btnCancel;
	
	private EditText elementName;
	private EditText defaultValue;
	
	private Spinner spRestrictions;
	private ImageButton btnAddRes;
//	private ImageButton btnEditRes;
	private ImageButton btnRemoveRes;
	
	private EditText restrictionValue;
	private LinearLayout llAddRestriction;
	private ImageButton btnAcceptRes;
	private ArrayAdapter<SpinnerAdapterItem> adapter;
	private int editingRestriction;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adding_domain);
		
		intent = getIntent();
		context = this;
		
		getData(intent.getExtras());
		prepareMainControls();
		prepareEndButtons();
		prepareRestrictions();
				
	}

	private void getData(Bundle params) {
		
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
		defaultValue = (EditText) findViewById(R.id.etDefaultValue);
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
					intent.putExtra(ParamsConst.DOMAIN_DEFVALUE, defaultValue.getText().toString());
					StringBuilder sRes = new StringBuilder();
					for(int i=0; i<adapter.getCount();i++){
						sRes.append(adapter.getItem(i).getName());
						if(i != adapter.getCount() - 1)
							sRes.append("##");
					}
					intent.putExtra(ParamsConst.DOMAIN_RESTRICTIONS, sRes.toString());
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

	private void prepareRestrictions() {

		llAddRestriction = (LinearLayout) findViewById(R.id.addRestriction);
		spRestrictions = (Spinner) findViewById(R.id.spRestrictions);
		btnAddRes = (ImageButton) findViewById(R.id.btnAddRes);
//		btnEditRes = (ImageButton) findViewById(R.id.btnEditRes);
		btnRemoveRes = (ImageButton) findViewById(R.id.btnRemoveRes);
		restrictionValue = (EditText) findViewById(R.id.etAddRestriction);
		btnAcceptRes = (ImageButton) findViewById(R.id.btnAcceptRes);
		
		editingRestriction = -1;
		adapter = new ArrayAdapter<SpinnerAdapterItem>(context,  android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spRestrictions.setAdapter(adapter);
		
//		btnEditRes.setEnabled(false);
		btnRemoveRes.setEnabled(false);
		
		btnAddRes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				llAddRestriction.setVisibility(LinearLayout.VISIBLE);
			}
		});
		
//		btnEditRes.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if(spRestrictions.getSelectedItem() != null){
//					llAddRestriction.setVisibility(LinearLayout.VISIBLE);
//					SpinnerAdapterItem item = (SpinnerAdapterItem)spRestrictions.getSelectedItem();
//					editingRestriction = item.getId();
//					restrictionValue.setText(item.getName());
//				}
//			}
//		});
		
		btnRemoveRes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(spRestrictions.getSelectedItem() != null){
					adapter.remove((SpinnerAdapterItem)spRestrictions.getSelectedItem());
					spRestrictions.setAdapter(adapter);
					if(adapter.getCount() == 0){
//						btnEditRes.setEnabled(false);
						btnRemoveRes.setEnabled(false);
					}				
				}
			}
		});
		
		btnAcceptRes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				btnEditRes.setEnabled(true);
				btnRemoveRes.setEnabled(true);
				if(!"".equals(restrictionValue.getText().toString())){
					if(editingRestriction == -1){
						SpinnerAdapterItem newItem = new SpinnerAdapterItem(adapter.getCount() + 1, restrictionValue.getText().toString());
						adapter.add(newItem);
					}else{
						for(int i=0;i<adapter.getCount();i++){
							if(adapter.getItem(i).getId() == editingRestriction)
								adapter.getItem(i).setName(restrictionValue.getText().toString());
						}
					}
					spRestrictions.setAdapter(adapter);
					restrictionValue.getText().clear();
					llAddRestriction.setVisibility(LinearLayout.INVISIBLE);
				}else{
					Toast.makeText(context, getString(R.string.error_name_needed), Toast.LENGTH_LONG).show();
				}
					
			}
		});
	}
	
}

