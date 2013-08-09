package com.example.nfc_mifareclassic;





import com.nfc_mifareclassic.api.NFC_Mifare_classic;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Test extends Activity {

	private NFC_Mifare_classic puceNFC;
	private NfcAdapter mAdapter;
	private PendingIntent mPendingIntent;
	private IntentFilter[] mFilters;
	private String[][] mTechLists;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Interface graphique
		final float scale = this.getResources().getDisplayMetrics().density;
		 int pad = (int) (5 * scale + 0.5f); // 5dp to px.
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setBackgroundColor(0xFF34495e);
		linearLayout.setPadding(10*pad, 10*pad, 10*pad, pad);
		
		TextView textView = new TextView(this);
		textView.setText("Application test pour API NFC");
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
		textView.setGravity(Gravity.CENTER_HORIZONTAL);
		textView.setPadding(pad,pad, pad, 6*pad);
		textView.setTextColor(0xFFecf0f1);
		//textView.setLayoutParams(lp);
		
		linearLayout.addView(textView);
		
		Button button = new Button(this);
		button.setClickable(true);
		button.setText("Read a TAG");

		
		button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
		button.setHeight(300);
		button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               
                Intent intent = new Intent(Test.this, ReadActivity.class);
         
                Test.this.startActivity(intent);
            }
        });
		linearLayout.addView(button);
		
		Button buttonWrite = new Button(this);
		buttonWrite.setClickable(true);
		buttonWrite.setText("Write On TAG");
		buttonWrite.setHeight(300);
		buttonWrite.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
		buttonWrite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               
                Intent intent = new Intent(Test.this, WriteActivity.class);
         
                Test.this.startActivity(intent);
            }
        });
		linearLayout.addView(buttonWrite);

		setContentView(linearLayout);
		
		
		
		
		
		// FIN Interface graphique
		
		mAdapter = NfcAdapter.getDefaultAdapter(this);
//
		mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

		IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);

		try {
			ndef.addDataType("*/*");
		} catch (MalformedMimeTypeException e) {
			throw new RuntimeException("fail", e);
		}
		
		mFilters = new IntentFilter[] { ndef, };

		// Setup a tech list for all MifareClassic tags
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
		//this method gets called when the user attaches a Tag to the device.
		puceNFC = new NFC_Mifare_classic();
		// Check to see that the Activity started due to an Android Beam
		puceNFC.treatAsNewTag(intent);

		Toast.makeText(getApplicationContext(), "UID ::: " + puceNFC.getId(), Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onPause() {
	    super.onPause();
	    mAdapter.disableForegroundDispatch(this);
	 }
	

}
