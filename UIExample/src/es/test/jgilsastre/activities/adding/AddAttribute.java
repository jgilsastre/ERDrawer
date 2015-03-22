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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import es.test.jgilsastre.R;
import es.test.jgilsastre.main.Element;
import es.test.jgilsastre.util.ParamsConst;
import es.test.jgilsastre.util.SpinnerAdapterItem;

public class AddAttribute extends Activity {

	private Intent intent;
	private Context context;
	/**
	 * Botones aceptar/cancelar
	 */
	private Button btnAccept;
	private Button btnCancel;
	
	private EditText elementName;
	
	private List<SpinnerAdapterItem> domains;
	private Spinner spDomains;
	private CheckBox checkPK;
	private CheckBox checkNullable;
	private CheckBox checkUnique;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adding_attribute);
		
		intent = getIntent();
		context = this;
		
		getData(intent.getExtras());
		prepareMainControls();
		prepareEndButtons();
	}
	
	private void getData(Bundle params) {
		String data = params.getString(ParamsConst.DOMAINS);
		domains = new ArrayList<SpinnerAdapterItem>();
		if(data != null && !"".equals(data)){
			String[] elems = data.split(Element.ELEMENT_SEPARATOR);
			for(String e : elems){
				String[] pair = e.split(Element.ELEMENT_ID_NAME_SEPARATOR);
				if(pair.length == 2)
					domains.add(new SpinnerAdapterItem(pair[0], pair[1]));
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
		spDomains = (Spinner) findViewById(R.id.spDomains);
		checkPK = (CheckBox) findViewById(R.id.checkPrimaryKey);
		checkNullable = (CheckBox) findViewById(R.id.checkNullable);
		checkUnique = (CheckBox) findViewById(R.id.checkUnique);
		
		ArrayAdapter<String> domainsAdapter =  new ArrayAdapter <String> (this, android.R.layout.simple_spinner_item );
		domainsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for(SpinnerAdapterItem s : domains)
			domainsAdapter.add(s.toString());
		spDomains.setAdapter(domainsAdapter);
		
		checkPK.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				boolean pk = checkPK.isChecked();
				checkNullable.setChecked(pk);
				checkUnique.setChecked(pk);
				checkNullable.setEnabled(!pk);
				checkUnique.setEnabled(!pk);
			}
		});
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
					StringBuilder sb = new StringBuilder();
					sb.append(checkPK.isChecked()?"1":"0");
					sb.append(checkNullable.isChecked()?"1":"0");
					sb.append(checkUnique.isChecked()?"1":"0");
					intent.putExtra(ParamsConst.ATTRIBUTE_PARAMS, sb.toString());
					intent.putExtra(ParamsConst.ATTRIBUTE_DOMAIN, domains.get(spDomains.getSelectedItemPosition()).getIdentifier());
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
