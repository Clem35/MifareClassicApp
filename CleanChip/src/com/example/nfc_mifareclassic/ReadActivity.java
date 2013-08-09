package com.example.nfc_mifareclassic;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ReadActivity extends Activity {

	private String[][] mTechLists;
	private IntentFilter[] mFilters;
	private PendingIntent mPendingIntent;
	private NfcAdapter mAdapter;
	private NFC_Mifare_classic puceNFC;
	private Spinner spinner;
	private Spinner spinnerB;
	ArrayAdapter<String> adp, adpB;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Interface graphique
		final float scale = this.getResources().getDisplayMetrics().density;
		int pad = (int) (5 * scale + 0.5f); // 5dp to px.
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);

		// L1
		LinearLayout linearLayoutH1 = new LinearLayout(this);
		linearLayoutH1.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.addView(linearLayoutH1);

		TextView textView = new TextView(this);
		textView.setText("Secteur : ");
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		textView.setGravity(Gravity.CENTER_HORIZONTAL);
		textView.setPadding(pad, pad, pad, pad);
		linearLayoutH1.addView(textView);

		spinner = new Spinner(this);
		spinner.setId(1);
		final List<String> list = new ArrayList<String>();

		adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner.setAdapter(adp);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				System.out.println("Spinner Click");
				int j = Integer.parseInt(arg0.getItemAtPosition(arg2).toString());

				// List Sector
				adpB.clear();
				for (int i = 0; i < puceNFC.getBlockCountInSector(j); i++) {
					adpB.add("" + i);
				}
				// Set adapter again
				spinnerB.setAdapter(adpB);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		linearLayoutH1.addView(spinner);

		// FIN LIGNE 1

		// L2

		LinearLayout linearLayoutH2 = new LinearLayout(this);
		linearLayoutH2.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.addView(linearLayoutH2);

		TextView textView2 = new TextView(this);
		textView2.setText("Block : ");
		textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		textView2.setGravity(Gravity.CENTER_HORIZONTAL);
		textView2.setPadding(pad, pad, pad, pad);
		linearLayoutH2.addView(textView2);

		spinnerB = new Spinner(this);
		spinnerB.setId(2);
		final List<String> listB = new ArrayList<String>();

		adpB = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listB);
		adpB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		/** Defining a click event listener for the button "Add" */

		spinnerB.setAdapter(adpB);

		linearLayoutH2.addView(spinnerB);

		// L3
		LinearLayout linearLayoutH3 = new LinearLayout(this);
		linearLayoutH3.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.addView(linearLayoutH3);

		TextView textView3 = new TextView(this);
		textView3.setText("Key A: ");
		textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		textView3.setGravity(Gravity.CENTER_HORIZONTAL);
		textView3.setPadding(pad, pad, pad, pad);
		linearLayoutH3.addView(textView3);

		EditText textField3 = new EditText(this);
		textField3.setText("FFFFFFFFFFFF");
		textField3.setId(3);
		textField3.setMinimumWidth(100);
		textField3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		textField3.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		textField3.setGravity(Gravity.CENTER_HORIZONTAL);
		textField3.setPadding(pad, pad, pad, pad);
		linearLayoutH3.addView(textField3);

		TextView textViewKeyB = new TextView(this);
		textViewKeyB.setText("Key B: ");
		textViewKeyB.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		textViewKeyB.setGravity(Gravity.CENTER_HORIZONTAL);
		textViewKeyB.setPadding(pad, pad, pad, pad);
		linearLayoutH3.addView(textViewKeyB);

		EditText textFieldKeyB = new EditText(this);
		textFieldKeyB.setText("FFFFFFFFFFFF");
		textFieldKeyB.setId(103);
		textFieldKeyB.setMinimumWidth(100);
		textFieldKeyB.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		textFieldKeyB.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		textFieldKeyB.setGravity(Gravity.CENTER_HORIZONTAL);
		textFieldKeyB.setPadding(pad, pad, pad, pad);
		linearLayoutH3.addView(textFieldKeyB);

		CheckBox checkUseKeyB = new CheckBox(this);
		checkUseKeyB.setText("Use Key B");
		checkUseKeyB.setId(209);
		linearLayoutH3.addView(checkUseKeyB);

		// */

		// L4
		LinearLayout linearLayoutH4 = new LinearLayout(this);
		linearLayoutH4.setOrientation(LinearLayout.HORIZONTAL);
		linearLayoutH4.setGravity(Gravity.CENTER);
		linearLayoutH4.setPadding(pad, pad, pad, pad);
		linearLayout.addView(linearLayoutH4);

		Button buttonblock = new Button(this);
		buttonblock.setClickable(true);
		buttonblock.setText("Read a block");
		buttonblock.setWidth(250);
		buttonblock.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		buttonblock.setHeight(100);
		buttonblock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (((Spinner) findViewById(1)).getSelectedItem() != null && ((Spinner) findViewById(2)).getSelectedItem() != null) {
					CheckBox checkBox = (CheckBox) findViewById(209);
					boolean x;
					if (checkBox.isChecked()) {
						x = true;
					} else {
						x = false;
					}
					int sector = Integer.parseInt(((Spinner) findViewById(1)).getSelectedItem().toString());
					int block = Integer.parseInt(((Spinner) findViewById(2)).getSelectedItem().toString());
					String key = ((EditText) findViewById(3)).getText().toString();
					System.out.println("Sector ::: " + sector + "  |  Block ::: " + block + "  |  Key ::: " + key);
					String result = "";
					try {
						result = puceNFC.readABlock(sector, block, Common.hexStringToByteArray(key), x);
					} catch (TagActionException e) {
						Toast.makeText(ReadActivity.this, "Erreur : " + e.getMessage(), Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
					((TextView) findViewById(4)).setText("Resultat Lecture Block : \n" + result);
				} else {
					Toast.makeText(ReadActivity.this, "Veuillez Remplir tous les paramètres", Toast.LENGTH_LONG).show();
				}
			}
		});
		linearLayoutH4.addView(buttonblock);

		Button buttonSector = new Button(this);
		buttonSector.setClickable(true);
		buttonSector.setText("Read a Sector");
		buttonSector.setHeight(100);
		buttonSector.setWidth(250);
		buttonSector.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		buttonSector.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (((Spinner) findViewById(1)).getSelectedItem() != null) {
					CheckBox checkBox = (CheckBox) findViewById(209);
					boolean x;
					if (checkBox.isChecked()) {
						x = true;

					} else {
						x = false;

					}
					int sector = Integer.parseInt(((Spinner) findViewById(1)).getSelectedItem().toString());

					String key = ((EditText) findViewById(3)).getText().toString();
					System.out.println("Sector ::: " + sector + "  |  Key ::: " + key);
					String result = "";
					try {
						result = puceNFC.readASector(sector, Common.hexStringToByteArray(key), x);
					} catch (TagActionException e) {
						Toast.makeText(ReadActivity.this, "Erreur : " + e.getMessage(), Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
					((TextView) findViewById(4)).setText("Resultat Lecture Sector : \n" + result);
				} else {
					Toast.makeText(ReadActivity.this, "Veuillez Remplir tous les paramètres", Toast.LENGTH_LONG).show();
				}
			}
		});

		linearLayoutH4.addView(buttonSector);

		Button buttonAll = new Button(this);
		buttonAll.setClickable(true);
		buttonAll.setText("Read All");
		buttonAll.setHeight(100);
		buttonAll.setWidth(250);
		buttonAll.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		buttonAll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CheckBox checkBox = (CheckBox) findViewById(209);
				boolean x;
				if (checkBox.isChecked()) {
					x = true;
				} else {
					x = false;
				}
				String key = ((EditText) findViewById(3)).getText().toString();

				String result = "";
				try {
					result = puceNFC.readAllSpace(Common.hexStringToByteArray(key), x);
				} catch (TagActionException e) {
					Toast.makeText(ReadActivity.this, "Erreur : " + e.getMessage(), Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
				((TextView) findViewById(4)).setText("Resultat Lecture Puce : \n" + result);
			}
		});
		linearLayoutH4.addView(buttonAll);

		// L5

		LinearLayout linearLayoutH5 = new LinearLayout(this);
		linearLayoutH5.setOrientation(LinearLayout.HORIZONTAL);
		linearLayoutH5.setGravity(Gravity.CENTER);
		linearLayoutH5.setPadding(pad, pad, pad, pad);
		linearLayout.addView(linearLayoutH5);

		Button buttonInfo = new Button(this);
		buttonInfo.setClickable(true);
		buttonInfo.setText("Read Information");
		buttonInfo.setHeight(100);
		buttonInfo.setWidth(250);
		buttonInfo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		buttonInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (((Spinner) findViewById(1)).getSelectedItem() != null) {

					int sector = Integer.parseInt(((Spinner) findViewById(1)).getSelectedItem().toString());

					String result1 = puceNFC.getId();
					int result2 = puceNFC.getBlockCount();
					int result3 = puceNFC.getBlockCountInSector(sector);
					int result4 = puceNFC.getSectorCount();
					((TextView) findViewById(4)).setText("ID : " + result1 + "\n Nombre de bloc : " + result2 + "\n Nombre de bloc dans le secteur "
							+ sector + "  : " + result3 + "\n Nombre de secteur : " + result4);
				} else {
					Toast.makeText(ReadActivity.this, "Veuillez Remplir tous les paramètres", Toast.LENGTH_LONG).show();
				}
			}
		});
		linearLayoutH5.addView(buttonInfo);

		Button buttonInfoForBlock = new Button(this);
		buttonInfoForBlock.setClickable(true);
		buttonInfoForBlock.setText("Read Information for block");
		buttonInfoForBlock.setHeight(100);
		buttonInfoForBlock.setWidth(250);
		buttonInfoForBlock.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		buttonInfoForBlock.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (((Spinner) findViewById(1)).getSelectedItem() != null && ((Spinner) findViewById(2)).getSelectedItem() != null) {
					CheckBox checkBox = (CheckBox) findViewById(209);
					boolean x;
					if (checkBox.isChecked()) {
						x = true;
					} else {
						x = false;
					}
					int sector = Integer.parseInt(((Spinner) findViewById(1)).getSelectedItem().toString());
					int block = Integer.parseInt(((Spinner) findViewById(2)).getSelectedItem().toString());
					String key = ((EditText) findViewById(3)).getText().toString();

					int result = -1;
					try {
						result = puceNFC.getInfoForBlock(sector, block, Common.hexStringToByteArray(key), x);
					} catch (TagActionException e) {
						Toast.makeText(ReadActivity.this, "Erreur : " + e.getMessage(), Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}

					((TextView) findViewById(4)).setText("Info for bloc " + block + " du secteur " + sector + " : " + puceNFC.GetStringInfo(result));
				} else {
					Toast.makeText(ReadActivity.this, "Veuillez Remplir tous les paramètres", Toast.LENGTH_LONG).show();
				}
			}
		});
		linearLayoutH5.addView(buttonInfoForBlock);

		// L6
		ScrollView scrollView = new ScrollView(this);

		LinearLayout linearLayoutH6 = new LinearLayout(this);
		linearLayoutH6.setOrientation(LinearLayout.HORIZONTAL);
		linearLayoutH6.setGravity(Gravity.CENTER);
		linearLayoutH6.setPadding(pad, pad, pad, pad);
		linearLayout.addView(scrollView);
		scrollView.addView(linearLayoutH6);
		TextView textViewResult = new TextView(this);
		textViewResult.setText("");
		textViewResult.setId(4);
		textViewResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		textViewResult.setGravity(Gravity.CENTER_HORIZONTAL);
		textViewResult.setPadding(pad, pad, pad, pad);
		linearLayoutH6.addView(textViewResult);

		setContentView(linearLayout);
		// FIN Interface graphique

		
		mAdapter = NfcAdapter.getDefaultAdapter(this);

		mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

		IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);

		try {
			ndef.addDataType("*/*");
		} catch (MalformedMimeTypeException e) {
			throw new RuntimeException("fail", e);
		}
		mFilters = new IntentFilter[] { ndef, };

		// Setup a tech list for all NfcF tags
		mTechLists = new String[][] { new String[] { MifareClassic.class.getName() } };

		Intent intent = getIntent();

		onNewIntent(intent);
	}

	@Override
	public void onResume() {
		super.onResume();
		mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);
	}

	@Override
	public void onNewIntent(Intent intent) {
		puceNFC = new NFC_Mifare_classic();
		Common.treatAsNewTag(intent, null);

		// List Sector
		adp.clear();
		for (int i = 0; i < puceNFC.getSectorCount(); i++) {
			adp.add("" + i);
		}
		// Set adapter again
		spinner.setAdapter(adp);

		Toast.makeText(getApplicationContext(), "UID ::: " + puceNFC.getId(), Toast.LENGTH_LONG).show();
	}

}
