package com.example.cleanchip;



import com.example.nfc_mifareclassic.Common;
import com.example.nfc_mifareclassic.NFC_Mifare_classic;
import com.example.nfc_mifareclassic.TagActionException;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private NFC_Mifare_classic puceNFC;
	private NfcAdapter mAdapter;
	private PendingIntent mPendingIntent;
	private IntentFilter[] mFilters;
	private String[][] mTechLists;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			
		// Interface graphique
				final float scale = this.getResources().getDisplayMetrics().density;
				int pad = (int) (30 * scale + 0.5f); // 5dp to px.
				LinearLayout linearLayout = new LinearLayout(this);
				linearLayout.setOrientation(LinearLayout.VERTICAL);
				linearLayout.setBackgroundColor(0xFF34495e);
				linearLayout.setPadding(pad, 0, pad, pad);
				
				TextView textView = new TextView(this);
				textView.setText("Clean TAG");
				textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
				textView.setGravity(Gravity.CENTER_HORIZONTAL);
				textView.setPadding(pad, pad, pad, pad*5);
				textView.setTextColor(0xFFecf0f1);
				
				linearLayout.addView(textView);

				Button button = new Button(this);
				button.setClickable(true);
				button.setText("Effacer le TAG NFC");
		
				button.setBackgroundColor(0xFFecf0f1);
				button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
				button.setHeight(300);
				button.setOnClickListener(new OnClickListener() {
		            @Override
		            public void onClick(View v) {
		               
		            	try {
							puceNFC.writeInAllDataSpace("", Common.hexStringToByteArray("FFFFFFFFFFFF"), false);
						} catch (TagActionException e) {
							Toast.makeText(MainActivity.this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show();
							e.printStackTrace();
							return;
						}
		            	Toast.makeText(MainActivity.this, "Puce effacée", Toast.LENGTH_SHORT).show();
		            }
		        });
				linearLayout.addView(button);
				
			

				setContentView(linearLayout);
		
		
		
		
		
		
		
		
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	public void onNewIntent(Intent intent) {
		puceNFC = new NFC_Mifare_classic();
		puceNFC.treatAsNewTag(intent);

		Toast.makeText(getApplicationContext(), "UID ::: " + puceNFC.getId(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onResume() {
		super.onResume();
		mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);
	}
	
}
