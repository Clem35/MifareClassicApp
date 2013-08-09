package com.example.nfc_mifareclassic;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
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

public class WriteActivity extends Activity {

	private String[][] mTechLists;
	private IntentFilter[] mFilters;
	private PendingIntent mPendingIntent;
	private NfcAdapter mAdapter;
	private NFC_Mifare_classic puceNFC;
	private Spinner spinner;
	private Spinner spinnerB;
	ArrayAdapter<String> adp, adpB;
	private ArrayAdapter<String> adpAccesBits;
	private Spinner spinnerAccessBits0;
	private Spinner spinnerAccessBits1;
	private Spinner spinnerAccessBits2;
	private Spinner spinnerAccessBits3;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Interface graphique
		final float scale = this.getResources().getDisplayMetrics().density;
		int pad = (int) (5 * scale + 0.5f); // 5dp to px.

		// LINEAR LAYOUT VERTICAL GLOBAL
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);

		// Ligne 1
		LinearLayout linearLayoutH1 = new LinearLayout(this);
		linearLayoutH1.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.addView(linearLayoutH1);

		// Secteur text view
		TextView textView = new TextView(this);
		textView.setText("Secteur : ");
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		textView.setGravity(Gravity.CENTER_HORIZONTAL);
		textView.setPadding(pad, pad, pad, pad);
		linearLayoutH1.addView(textView);

		// Spinner secteur
		spinner = new Spinner(this);
		spinner.setId(100);
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

		// Ligne 2 Bloc

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
		spinnerB.setId(101);
		final List<String> listB = new ArrayList<String>();

		adpB = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listB);
		adpB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		/** Defining a click event listener for the button "Add" */

		spinnerB.setAdapter(adpB);

		linearLayoutH2.addView(spinnerB);

		// Ligne 3 Key
		LinearLayout linearLayoutH3 = new LinearLayout(this);
		linearLayoutH3.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.addView(linearLayoutH3);

		TextView textView3 = new TextView(this);
		textView3.setText("Key A : ");
		textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		textView3.setGravity(Gravity.CENTER_HORIZONTAL);
		textView3.setPadding(pad, pad, pad, pad);
		linearLayoutH3.addView(textView3);

		EditText textFieldKeyA = new EditText(this);
		textFieldKeyA.setText("FFFFFFFFFFFF");
		textFieldKeyA.setId(102);
		textFieldKeyA.setMinimumWidth(100);
		textFieldKeyA.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		textFieldKeyA.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		textFieldKeyA.setGravity(Gravity.CENTER_HORIZONTAL);
		textFieldKeyA.setPadding(pad, pad, pad, pad);
		linearLayoutH3.addView(textFieldKeyA);

		TextView textViewKeyB = new TextView(this);
		textViewKeyB.setText("Key B : ");
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
		checkUseKeyB.setId(109);
		linearLayoutH3.addView(checkUseKeyB);

		// Ligne 4 AccessBits

		LinearLayout linearLayoutH3bis = new LinearLayout(this);
		linearLayoutH3bis.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.addView(linearLayoutH3bis);

		final List<String> listAccessBits = new ArrayList<String>();
		listAccessBits.add("1");
		listAccessBits.add("2");
		listAccessBits.add("3");
		listAccessBits.add("4");
		listAccessBits.add("5");
		listAccessBits.add("6");
		listAccessBits.add("7");
		listAccessBits.add("8");

		final List<String> listAccessTrailer = new ArrayList<String>();
		listAccessTrailer.add("9");
		listAccessTrailer.add("10");
		listAccessTrailer.add("11");
		listAccessTrailer.add("12");
		listAccessTrailer.add("13");
		listAccessTrailer.add("14");
		listAccessTrailer.add("15");
		listAccessTrailer.add("16");

		TextView textViewAccessBits = new TextView(this);
		textViewAccessBits.setText("AccessBits : ");
		textViewAccessBits.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		textViewAccessBits.setGravity(Gravity.CENTER_HORIZONTAL);
		textViewAccessBits.setPadding(pad, pad, pad, pad);
		linearLayoutH3bis.addView(textViewAccessBits);

		TextView textViewAccessBitsB0 = new TextView(this);
		textViewAccessBitsB0.setText("B0 : ");
		textViewAccessBitsB0.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		textViewAccessBitsB0.setGravity(Gravity.CENTER_HORIZONTAL);
		textViewAccessBitsB0.setPadding(pad, pad, pad, pad);
		linearLayoutH3bis.addView(textViewAccessBitsB0);

		spinnerAccessBits0 = new Spinner(this);
		spinnerAccessBits0.setId(104);
		adpAccesBits = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listAccessBits);
		adpAccesBits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinnerAccessBits0.setAdapter(adpAccesBits);
		linearLayoutH3bis.addView(spinnerAccessBits0);

		TextView textViewAccessBitsB1 = new TextView(this);
		textViewAccessBitsB1.setText("B1 : ");
		textViewAccessBitsB1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		textViewAccessBitsB1.setGravity(Gravity.CENTER_HORIZONTAL);
		textViewAccessBitsB1.setPadding(pad, pad, pad, pad);
		linearLayoutH3bis.addView(textViewAccessBitsB1);

		spinnerAccessBits1 = new Spinner(this);
		spinnerAccessBits1.setId(105);
		adpAccesBits = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listAccessBits);
		adpAccesBits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinnerAccessBits1.setAdapter(adpAccesBits);
		linearLayoutH3bis.addView(spinnerAccessBits1);

		TextView textViewAccessBitsB2 = new TextView(this);
		textViewAccessBitsB2.setText("B2 : ");
		textViewAccessBitsB2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		textViewAccessBitsB2.setGravity(Gravity.CENTER_HORIZONTAL);
		textViewAccessBitsB2.setPadding(pad, pad, pad, pad);
		linearLayoutH3bis.addView(textViewAccessBitsB2);

		spinnerAccessBits2 = new Spinner(this);
		spinnerAccessBits2.setId(106);
		adpAccesBits = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listAccessBits);
		adpAccesBits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinnerAccessBits2.setAdapter(adpAccesBits);
		linearLayoutH3bis.addView(spinnerAccessBits2);

		TextView textViewAccessBitsB3 = new TextView(this);
		textViewAccessBitsB3.setText("B3 : ");
		textViewAccessBitsB3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		textViewAccessBitsB3.setGravity(Gravity.CENTER_HORIZONTAL);
		textViewAccessBitsB3.setPadding(pad, pad, pad, pad);
		linearLayoutH3bis.addView(textViewAccessBitsB3);

		spinnerAccessBits3 = new Spinner(this);
		spinnerAccessBits3.setId(107);
		adpAccesBits = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listAccessTrailer);
		adpAccesBits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinnerAccessBits3.setAdapter(adpAccesBits);
		linearLayoutH3bis.addView(spinnerAccessBits3);

		// Ligne 5 - Data
		LinearLayout linearLayoutH3ter = new LinearLayout(this);
		linearLayoutH3ter.setOrientation(LinearLayout.HORIZONTAL);
		linearLayoutH3ter.setGravity(Gravity.CENTER);
		linearLayoutH3ter.setPadding(pad, pad, pad, pad);
		linearLayout.addView(linearLayoutH3ter);

		TextView textViewData = new TextView(this);
		textViewData.setText("Data : ");
		textViewData.setId(3);
		textViewData.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		textViewData.setGravity(Gravity.CENTER_HORIZONTAL);
		textViewData.setPadding(pad, pad, pad, pad);
		linearLayoutH3ter.addView(textViewData);

		EditText data = new EditText(this);
		data.setText("");
		data.setId(108);
		data.setMinimumWidth(500);
		data.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		data.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		data.setGravity(Gravity.CENTER_HORIZONTAL);
		data.setPadding(pad, pad, pad, pad);
		linearLayoutH3ter.addView(data);

		// L6 Bouton première rangée
		LinearLayout linearLayoutH4 = new LinearLayout(this);
		linearLayoutH4.setOrientation(LinearLayout.HORIZONTAL);
		linearLayoutH4.setGravity(Gravity.CENTER);
		linearLayoutH4.setPadding(pad, pad, pad, pad);
		linearLayout.addView(linearLayoutH4);

		Button buttonblock = new Button(this);
		buttonblock.setClickable(true);
		buttonblock.setText("Write in block");
		buttonblock.setWidth(250);
		buttonblock.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		buttonblock.setHeight(100);
		buttonblock.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (((Spinner) findViewById(100)).getSelectedItem() != null && ((Spinner) findViewById(101)).getSelectedItem() != null) {
					CheckBox checkBox = (CheckBox) findViewById(109);
					boolean x;
					if (checkBox.isChecked()) {
						x = true;
					} else {
						x = false;
					}
					int sector = Integer.parseInt(((Spinner) findViewById(100)).getSelectedItem().toString());
					int block = Integer.parseInt(((Spinner) findViewById(101)).getSelectedItem().toString());
					String key = ((EditText) findViewById(102)).getText().toString();
					String data = puceNFC.toHex(((EditText) findViewById(108)).getText().toString());
					System.out.println("Sector ::: " + sector + "  |  Block ::: " + block + "  |  Key ::: " + key);
					boolean result = false;
					try {
						result = puceNFC.writeInABlock(sector, block, data, Common.hexStringToByteArray(key), x);
					} catch (TagActionException e) {
						Toast.makeText(WriteActivity.this, "Erreur : " + e.getMessage(), Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
					((TextView) findViewById(4)).setText("Resultat Ecriture : \n" + result);
				} else {
					Toast.makeText(WriteActivity.this, "Veuillez Remplir tous les paramètres", Toast.LENGTH_LONG).show();
				}
			}
		});
		linearLayoutH4.addView(buttonblock);

		Button buttonSector = new Button(this);
		buttonSector.setClickable(true);
		buttonSector.setText("Write in Sector");
		buttonSector.setHeight(100);
		buttonSector.setWidth(250);
		buttonSector.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		buttonSector.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (((Spinner) findViewById(100)).getSelectedItem() != null) {
					CheckBox checkBox = (CheckBox) findViewById(109);
					boolean x;
					if (checkBox.isChecked()) {
						x = true;
					} else {
						x = false;
					}
					int sector = Integer.parseInt(((Spinner) findViewById(100)).getSelectedItem().toString());
					String key = ((EditText) findViewById(102)).getText().toString();
					String data = puceNFC.toHex(((EditText) findViewById(108)).getText().toString());

					boolean result = false;
					try {
						result = puceNFC.writeInASector(sector, data, Common.hexStringToByteArray(key), x);
					} catch (TagActionException e) {
						Toast.makeText(WriteActivity.this, "Erreur : " + e.getMessage(), Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
					((TextView) findViewById(4)).setText("Resultat Ecriture : \n" + result);
				} else {
					Toast.makeText(WriteActivity.this, "Veuillez Remplir tous les paramètres", Toast.LENGTH_LONG).show();
				}
			}
		});

		linearLayoutH4.addView(buttonSector);

		Button buttonAll = new Button(this);
		buttonAll.setClickable(true);
		buttonAll.setText("Write in All space");
		buttonAll.setHeight(100);
		buttonAll.setWidth(250);
		buttonAll.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		buttonAll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CheckBox checkBox = (CheckBox) findViewById(109);
				boolean x;
				if (checkBox.isChecked()) {
					x = true;
				} else {
					x = false;
				}
				String key = ((EditText) findViewById(102)).getText().toString();
				String data = puceNFC.toHex(((EditText) findViewById(108)).getText().toString());

				boolean result = false;
				try {
					result = puceNFC.writeInAllDataSpace(data, Common.hexStringToByteArray(key), x);
				} catch (TagActionException e) {
					Toast.makeText(WriteActivity.this, "Erreur : " + e.getMessage(), Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
				((TextView) findViewById(4)).setText("Resultat Ecriture : \n" + result);
			}
		});
		linearLayoutH4.addView(buttonAll);

		// Ligne 7 deuxième rangée

		LinearLayout linearLayoutH5 = new LinearLayout(this);
		linearLayoutH5.setOrientation(LinearLayout.HORIZONTAL);
		linearLayoutH5.setGravity(Gravity.CENTER);
		linearLayoutH5.setPadding(pad, pad, pad, pad);
		linearLayout.addView(linearLayoutH5);

		Button buttonInfo = new Button(this);
		buttonInfo.setClickable(true);
		buttonInfo.setText("Write Key A");
		buttonInfo.setHeight(100);
		buttonInfo.setWidth(250);
		buttonInfo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		buttonInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (((Spinner) findViewById(100)).getSelectedItem() != null) {
					int sector = Integer.parseInt(((Spinner) findViewById(100)).getSelectedItem().toString());
					String keyA = ((EditText) findViewById(102)).getText().toString();
					String keyB = ((EditText) findViewById(103)).getText().toString();
					String newKey = ((EditText) findViewById(108)).getText().toString();
					boolean result = false;
					try {
						result = puceNFC.writeKeyA(sector, Common.hexStringToByteArray(keyA), Common.hexStringToByteArray(keyB),
								Common.hexStringToByteArray(newKey));
					} catch (TagActionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					((TextView) findViewById(4)).setText("Resultat Ecriture : \n" + result);
				} else {
					Toast.makeText(WriteActivity.this, "Veuillez remplir tous les paramètres", Toast.LENGTH_LONG).show();
				}
			}

		});
		linearLayoutH5.addView(buttonInfo);

		Button buttonWriteKeyB = new Button(this);
		buttonWriteKeyB.setClickable(true);
		buttonWriteKeyB.setText("Write Key B");
		buttonWriteKeyB.setHeight(100);
		buttonWriteKeyB.setWidth(250);
		buttonWriteKeyB.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		buttonWriteKeyB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (((Spinner) findViewById(100)).getSelectedItem() != null) {
					int sector = Integer.parseInt(((Spinner) findViewById(100)).getSelectedItem().toString());
					String keyA = ((EditText) findViewById(102)).getText().toString();
					String keyB = ((EditText) findViewById(103)).getText().toString();
					String data = ((EditText) findViewById(108)).getText().toString();
					boolean result = false;
					try {
						result = puceNFC.writeKeyB(sector, Common.hexStringToByteArray(keyA), Common.hexStringToByteArray(keyB),
								Common.hexStringToByteArray(data));
					} catch (TagActionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					((TextView) findViewById(4)).setText("Resultat Ecriture : \n" + result);
				} else {
					Toast.makeText(WriteActivity.this, "Veuillez remplir tous les paramètres", Toast.LENGTH_LONG).show();
				}
			}
		});
		linearLayoutH5.addView(buttonWriteKeyB);

		Button ButtonWriteAccessBits = new Button(this);
		ButtonWriteAccessBits.setClickable(true);
		ButtonWriteAccessBits.setText("Write AccessBits");
		ButtonWriteAccessBits.setHeight(100);
		ButtonWriteAccessBits.setWidth(250);
		ButtonWriteAccessBits.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		ButtonWriteAccessBits.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// ALERT DIALOG
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WriteActivity.this);

				// set title
				alertDialogBuilder.setTitle("Alert");

				// set dialog message
				alertDialogBuilder.setMessage("Cette action peut être irréversible pour la puce NFC !!").setCancelable(false)
						.setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// AUTORISATION
								puceNFC.setAuthorizationToWriteInSectorTrailer(true);
								if (((Spinner) findViewById(104)).getSelectedItem() != null
										&& ((Spinner) findViewById(105)).getSelectedItem() != null
										&& ((Spinner) findViewById(106)).getSelectedItem() != null
										&& ((Spinner) findViewById(107)).getSelectedItem() != null
										&& ((Spinner) findViewById(100)).getSelectedItem() != null) {

									int acB0 = Integer.parseInt(((Spinner) findViewById(104)).getSelectedItem().toString());
									int acB1 = Integer.parseInt(((Spinner) findViewById(105)).getSelectedItem().toString());
									int acB2 = Integer.parseInt(((Spinner) findViewById(106)).getSelectedItem().toString());
									int acB3 = Integer.parseInt(((Spinner) findViewById(107)).getSelectedItem().toString());
									int sector = Integer.parseInt(((Spinner) findViewById(100)).getSelectedItem().toString());
									String keyA = ((EditText) findViewById(102)).getText().toString();
									String keyB = ((EditText) findViewById(103)).getText().toString();
									byte[] newAccessBit = puceNFC.createAccessBit(acB0, acB1, acB2, acB3);
									boolean result = false;
									try {
										result = puceNFC.writeAccesBit(sector, Common.hexStringToByteArray(keyA), Common.hexStringToByteArray(keyB),
												newAccessBit);
									} catch (TagActionException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									((TextView) findViewById(4)).setText("Changement d'AccessBits effectué : " + result);
								} else {
									Toast.makeText(WriteActivity.this, "Veuillez remplir tous les paramètres", Toast.LENGTH_LONG).show();
								}
							}
						}).setNegativeButton("Abandonner", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

							}
						});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}
		});
		linearLayoutH5.addView(ButtonWriteAccessBits);

		// Ligne 8 résultat
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
