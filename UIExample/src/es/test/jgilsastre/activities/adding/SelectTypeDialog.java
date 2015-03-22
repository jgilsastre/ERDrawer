package es.test.jgilsastre.activities.adding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import es.test.jgilsastre.R;
import es.test.jgilsastre.util.ParamsConst;

public class SelectTypeDialog extends Activity {
	
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adding_select_type);
		
		intent = getIntent();
					
		Bundle params = getIntent().getExtras();
		final String rels = params.getString(ParamsConst.RELATIONS);
		final String ents = params.getString(ParamsConst.ENTITIES);
		final String doms = params.getString(ParamsConst.DOMAINS);
		boolean showAttribute = params.getBoolean(ParamsConst.CREATE_ATTRIBUTES_ENABLED);
		
		
		ImageButton btnDomain = (ImageButton) findViewById(R.id.btnDomain);
		btnDomain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), AddDomain.class);
				startActivityForResult(intent, ParamsConst.RC_ADD_DIALOG);
			}
		});
		
		ImageButton btnEntity = (ImageButton) findViewById(R.id.btnEntity);
		btnEntity.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent= new Intent();
				intent.setClass(getApplicationContext(), AddEntity.class);
				intent.putExtra(ParamsConst.RELATIONS, rels);
				startActivityForResult(intent, ParamsConst.RC_ADD_DIALOG);
			}
		});
		
		ImageButton btnRelation = (ImageButton) findViewById(R.id.btnRelation);
		btnRelation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), AddRelation.class);
				intent.putExtra(ParamsConst.ENTITIES, ents);
				startActivityForResult(intent, ParamsConst.RC_ADD_DIALOG);
			}
		});
		
		ImageButton btnAttribute = (ImageButton) findViewById(R.id.btnAttribute);
		if(showAttribute){
			btnAttribute.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(getApplicationContext(), AddAttribute.class);
					intent.putExtra(ParamsConst.DOMAINS, doms);
					startActivityForResult(intent, ParamsConst.RC_ADD_DIALOG);
				}
			});
		}else{
			btnAttribute.setEnabled(false);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		intent.putExtras(data.getExtras());
		setResult(resultCode, intent);
		finish();
	}
	
}
		
		//typeButtons[ParamsConst.ELEMENT_TYPE_ATTRIBUTE].setEnabled(showAttribute);
		
		
//		typeSeleted = -1;
//		typeLayouts = new LinearLayout[4];
//		btnAccept = (Button) findViewById(R.id.btnAccept);
//		btnCancel = (Button) findViewById(R.id.btnCancel);
//		elementName = (EditText) findViewById(R.id.etName);
//		btnsAC = (LinearLayout) findViewById(R.id.acceptCancel);
//		common = (LinearLayout) findViewById(R.id.common);
		
//		getData();	
//		getIds();
//		createTypeButtons();
//		createEndButtons();
//	}
	
//	public void getData(){
//		Bundle params = intent.getExtras();
//		
//	}
//
//	public void getIds(){
//		drawableIds = new int[4][2];
//		drawableIds[ParamsConst.ELEMENT_TYPE_DOMAIN][IMGBTN_NOT_PRESSED] = R.drawable.domain;
//		drawableIds[ParamsConst.ELEMENT_TYPE_DOMAIN][IMGBTN_PRESSED] = R.drawable.domain_pressed; 
//		drawableIds[ParamsConst.ELEMENT_TYPE_ENTITY][IMGBTN_NOT_PRESSED] = R.drawable.entity;
//		drawableIds[ParamsConst.ELEMENT_TYPE_ENTITY][IMGBTN_PRESSED] = R.drawable.entity_pressed; 
//		drawableIds[ParamsConst.ELEMENT_TYPE_RELATION][IMGBTN_NOT_PRESSED] = R.drawable.relation;
//		drawableIds[ParamsConst.ELEMENT_TYPE_RELATION][IMGBTN_PRESSED] = R.drawable.relation_pressed; 
//		drawableIds[ParamsConst.ELEMENT_TYPE_ATTRIBUTE][IMGBTN_NOT_PRESSED] = R.drawable.attribute;
//		drawableIds[ParamsConst.ELEMENT_TYPE_ATTRIBUTE][IMGBTN_PRESSED] = R.drawable.attribute_pressed; 
//		
//		ids = new int[4][2];
//		ids[ParamsConst.ELEMENT_TYPE_DOMAIN][LAYOUT_ID] = R.id.optionsDomain;
//		ids[ParamsConst.ELEMENT_TYPE_DOMAIN][BUTTON_ID] = R.id.btnDomain; 
//		ids[ParamsConst.ELEMENT_TYPE_ENTITY][LAYOUT_ID] = R.id.optionsEntity;
//		ids[ParamsConst.ELEMENT_TYPE_ENTITY][BUTTON_ID] = R.id.btnEntity; 
//		ids[ParamsConst.ELEMENT_TYPE_RELATION][LAYOUT_ID] = R.id.optionsRelation;
//		ids[ParamsConst.ELEMENT_TYPE_RELATION][BUTTON_ID] = R.id.btnRelation; 
//		ids[ParamsConst.ELEMENT_TYPE_ATTRIBUTE][LAYOUT_ID] = R.id.optionsAttribute;
//		ids[ParamsConst.ELEMENT_TYPE_ATTRIBUTE][BUTTON_ID] = R.id.btnAttribute; 
//		
//		titles = new int[4];
//		titles[ParamsConst.ELEMENT_TYPE_DOMAIN] = R.string.title_activity_add_dialog_domain;
//		titles[ParamsConst.ELEMENT_TYPE_ENTITY] = R.string.title_activity_add_dialog_entity;
//		titles[ParamsConst.ELEMENT_TYPE_RELATION] = R.string.title_activity_add_dialog_relation;
//		titles[ParamsConst.ELEMENT_TYPE_ATTRIBUTE] = R.string.title_activity_add_dialog_attribute;
//	}
//	
//	public void createTypeButtons() {
//		
//		typeButtons = new ImageButton[4];
//		for(int i=ParamsConst.ELEMENT_TYPE_DOMAIN;i<=ParamsConst.ELEMENT_TYPE_ATTRIBUTE;i++){
//			final int option = i;
//			typeButtons[option] = (ImageButton) findViewById(ids[option][BUTTON_ID]);
//			typeButtons[option].setOnClickListener(new OnClickListener() {
//				public void onClick(View v) {
//					selectType(option);
//				}
//			});
//		}
//		
//		
//		
//		
//	}
//
//	public void selectType(int _typeSelected) {
//		
//		
//		
////		this.setTitle(getString(titles[_typeSelected]));
////		common.setVisibility(LinearLayout.VISIBLE);
////		btnsAC.setVisibility(LinearLayout.VISIBLE);
////		this.typeSeleted = _typeSelected;
////				
////		for(int i=ParamsConst.ELEMENT_TYPE_DOMAIN;i<=ParamsConst.ELEMENT_TYPE_ATTRIBUTE;i++){
////			if(typeLayouts[i] != null)
////				typeLayouts[i].setVisibility(LinearLayout.INVISIBLE);
////			typeButtons[i].setImageResource(drawableIds[i][IMGBTN_NOT_PRESSED]);
////		}			
////		
////		typeLayouts[_typeSelected] = (LinearLayout)findViewById(ids[_typeSelected][LAYOUT_ID]);
////		typeButtons[_typeSelected].setImageResource(drawableIds[_typeSelected][IMGBTN_PRESSED]);
////		typeLayouts[_typeSelected].setVisibility(LinearLayout.VISIBLE);
////		prepareLayout(_typeSelected);
//	}
//
//	public void createEndButtons(){
//		
//		btnAccept.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (!"".equals(elementName.getText().toString())) {
//					Toast.makeText(getApplicationContext(),
//							"Voy a mandar " + elementName.getText().toString(),
//							Toast.LENGTH_SHORT).show();
//					intent.putExtra(ParamsConst.ELEMENT_NAME, elementName
//							.getText().toString());
//					intent.putExtra(ParamsConst.ELEMENT_TYPE, typeSeleted);
//					setResult(RESULT_OK, intent);
//				} else {
//					Toast.makeText(getApplicationContext(), getString(R.string.error_name_needed) ,Toast.LENGTH_LONG).show();
//				}
//				finish();
//			}
//		});
//		
//		btnCancel.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				setResult(RESULT_CANCELED, intent);
//				finish();
//			}
//		});
//		btnAccept.setEnabled(false);
//	}
//		
//	public void prepareLayout(int _typeSelected){
//		switch(_typeSelected){
//		case ParamsConst.ELEMENT_TYPE_ENTITY:
//			weakSpinner = (Spinner) findViewById(R.id.spWeakOfRelation);
//			weakCheckBox = (CheckBox) findViewById(R.id.checkWeak);
//			TextView tbx = (TextView) findViewById(R.id.tvNoRelations);
//			
//			if(relations.size() > 0){
//				tbx.setVisibility(TextView.INVISIBLE);
//				weakCheckBox.setVisibility(CheckBox.VISIBLE);
//				weakSpinner.setVisibility(Spinner.VISIBLE);
//				weakSpinner.setEnabled(false);
//				ArrayAdapter<String> Relationsadapter =  new ArrayAdapter <String> (this, android.R.layout.simple_spinner_item );
//				Relationsadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//				for(String s : relations.keySet())
//					Relationsadapter.add(s);
//				weakSpinner.setAdapter(Relationsadapter);
//				//sp.setEnabled(true);
//				weakCheckBox.setEnabled(true);
//				
//				weakCheckBox.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						weakSpinner.setEnabled(weakCheckBox.isChecked());
//					}
//				});
//			}else{
//				weakCheckBox.setVisibility(CheckBox.INVISIBLE);
//				weakSpinner.setVisibility(Spinner.INVISIBLE);
//				tbx.setVisibility(TextView.VISIBLE);
//			}
//			break;
//		case ParamsConst.ELEMENT_TYPE_RELATION:
//			asocSpinner = (Spinner) findViewById(R.id.spAsociations);
//			entitiesSpinner = (Spinner) findViewById(R.id.spEntityAsoc);
//			btnAddAsoc = (ImageButton) findViewById(R.id.btnAddAsoc);
//			TextView noEntitiesTV = (TextView) findViewById(R.id.tvNoEntities);
//			llAsociation = (LinearLayout) findViewById(R.id.optionAddAsoc);
//			btnAcceptAsoc = (ImageButton) findViewById(R.id.btnAcceptAsoc);
//			btnRemoveAsoc = (ImageButton) findViewById(R.id.btnRemoveAsoc);
//			rbAsoc0 = (RadioButton) findViewById(R.id.rbAsoc0);
//			rbAsoc1 = (RadioButton) findViewById(R.id.rbAsoc1);
//			rbAsocN = (RadioButton) findViewById(R.id.rbAsocN);
//			if(entities.size() > 0){
//				asociations = new ArrayList<String>();
//				noEntitiesTV.setVisibility(TextView.INVISIBLE);
//				asocAdapter =  new ArrayAdapter <String> (this, android.R.layout.simple_spinner_item );
//				asocAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//				asocAdapter.add("");
//				asocSpinner.setAdapter(asocAdapter);
//				
//				asocSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//					@Override
//					public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//						if(!asocSpinner.getSelectedItem().toString().equals("")){
//							String entitySelected = asocSpinner.getSelectedItem().toString();
//							entitySelected = entitySelected.replace("[", "");
//							entitySelected = entitySelected.replace("]", "");
//							String[] asociation = entitySelected.split(":");
//							for(int i=0;i<entitiesadapter.getCount();i++)
//								if(entitiesadapter.getItem(i) != null && entitiesadapter.getItem(i).equals(asociation[0]))
//									entitiesSpinner.setSelection(i);
//							rbAsoc0.setChecked(asociation[1].equals("0"));
//							rbAsoc1.setChecked(asociation[1].equals("1"));
//							rbAsocN.setChecked(asociation[1].equals("N"));
//							llAsociation.setVisibility(LinearLayout.VISIBLE);
//						}
//					}
//
//					@Override
//					public void onNothingSelected(AdapterView<?> arg0) {
//						
//					}
//					
//				});
//					
//				btnAddAsoc.setOnClickListener(new OnClickListener() {
//					public void onClick(View v) {
//						llAsociation.setVisibility(LinearLayout.VISIBLE);
//					}
//				});
//				
//				entitiesadapter =  new ArrayAdapter <String> (this, android.R.layout.simple_spinner_item );
//				entitiesadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//				for(String s : entities.keySet())
//					entitiesadapter.add(s);
//				entitiesSpinner.setAdapter(entitiesadapter);
//				
//				btnAcceptAsoc.setOnClickListener(new OnClickListener() {
//					public void onClick(View v) {
//						String selectedEntity = "[" + (String)entitiesSpinner.getSelectedItem() + "]";
//						if(rbAsoc0.isChecked())
//							selectedEntity = selectedEntity + ":[0]";
//						else if(rbAsoc1.isChecked())
//							selectedEntity = selectedEntity + ":[1]";
//						else
//							selectedEntity = selectedEntity + ":[N]";
//						asocAdapter.add(selectedEntity);
//						asocSpinner.setAdapter(asocAdapter);
//						llAsociation.setVisibility(LinearLayout.INVISIBLE);
//					}
//				});
//				
//				btnRemoveAsoc.setOnClickListener(new OnClickListener() {
//					public void onClick(View v) {
//						
//					}
//				});
//				
//			}else{
//				asocSpinner.setVisibility(Spinner.INVISIBLE);
//				btnAddAsoc.setVisibility(ImageButton.INVISIBLE);
//				noEntitiesTV.setVisibility(TextView.VISIBLE);
//			}
//			break;
//		case ParamsConst.ELEMENT_TYPE_ATTRIBUTE:
//			checkPK = (CheckBox) findViewById(R.id.checkPrimaryKey);
//			checkNullable = (CheckBox) findViewById(R.id.checkNullable);
//			checkUnique = (CheckBox) findViewById(R.id.checkUnique);
//			
//			checkPK.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//				
//				@Override
//				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//					boolean pk = checkPK.isChecked();
//					checkNullable.setChecked(pk);
//					checkUnique.setChecked(pk);
//					checkNullable.setEnabled(!pk);
//					checkNullable.setEnabled(!pk);
//				}
//			});
//			break;
//		}
//		
//	}
//}
