package es.test.jgilsastre.activities.adding;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import es.test.jgilsastre.R;
import es.test.jgilsastre.main.Element;
import es.test.jgilsastre.util.ParamsConst;
import es.test.jgilsastre.util.SpinnerAdapterItem;

public class AddEntity extends Activity {

	private Intent intent;
	private Context context;
	/**
	 * Botones aceptar/cancelar
	 */
	private Button btnAccept;
	private Button btnCancel;
	
	private EditText elementName;
	
	private List<SpinnerAdapterItem> relations;
	private CheckBox weakCheckBox;
	private Spinner weakSpinner;
	private TextView noRelations;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adding_entity);
		intent = getIntent();
		context = this;
		
		getData(intent.getExtras());
		prepareMainControls();
		prepareEndButtons();
	}
	
	private void getData(Bundle params) {
		relations = new ArrayList<SpinnerAdapterItem>();
		String data = params.getString(ParamsConst.RELATIONS);
		if(data != null && !"".equals(data)){
			String[] elems = data.split(Element.ELEMENT_SEPARATOR);
			for(String e : elems){
				String[] pair = e.split(Element.ELEMENT_ID_NAME_SEPARATOR);
				if(pair.length == 2)
					relations.add(new SpinnerAdapterItem(pair[0], pair[1]));
			}
		}
//		relations = Util.convertPairsToMap(params.getString(ParamsConst.RELATIONS));
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
		weakSpinner = (Spinner) findViewById(R.id.spWeakOfRelation);
		weakCheckBox = (CheckBox) findViewById(R.id.checkWeak);
		noRelations = (TextView) findViewById(R.id.tvNoRelations);
		
		if(relations.size() > 0){
			noRelations.setVisibility(TextView.INVISIBLE);
			weakSpinner.setEnabled(false);
			ArrayAdapter<String> Relationsadapter =  new ArrayAdapter <String> (this, android.R.layout.simple_spinner_item );
			Relationsadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			for(SpinnerAdapterItem s : relations)
				Relationsadapter.add(s.toString());
			weakSpinner.setAdapter(Relationsadapter);
			weakCheckBox.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					weakSpinner.setEnabled(weakCheckBox.isChecked());
				}
			});
		}else{
			weakCheckBox.setVisibility(CheckBox.INVISIBLE);
			weakSpinner.setVisibility(Spinner.INVISIBLE);
			noRelations.setVisibility(TextView.VISIBLE);
		}
	}
	
	private void prepareEndButtons() {
		btnAccept = (Button) findViewById(R.id.btnAccept);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		
		btnAccept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!checkErrors()) {
					intent.putExtra(ParamsConst.ELEMENT_TYPE, ParamsConst.ELEMENT_TYPE_ENTITY);
					intent.putExtra(ParamsConst.ELEMENT_NAME, elementName.getText().toString());
					intent.putExtra(ParamsConst.ENTITY_WEAK, weakCheckBox.isChecked());
					if(weakCheckBox.isChecked()){
						intent.putExtra(ParamsConst.WEAK_OF_RELATION, relations.get(weakSpinner.getSelectedItemPosition()).getIdentifier());
					}
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
}
