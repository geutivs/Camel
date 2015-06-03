package com.sahara.camel;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NfcSigninActivity extends Activity {

	private TextView activityTitle;
	private Button mButtonBack;
	private NfcAdapter mAdapter;
	private PendingIntent mPendingIntent;
    private AlertDialog mDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfs_signin);
		initTitleBar();
		
		mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            showMessage(R.string.error, R.string.no_nfc);
            finish();
            return;
        }

        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
	}
	
	 private void showMessage(int title, int message) {
	        mDialog.setTitle(title);
	        mDialog.setMessage(getText(message));
	        mDialog.show();
	    }


	private void initTitleBar() {
		activityTitle = (TextView) findViewById(R.id.textview_title);
		activityTitle.setText(R.string.title_nfc_sigin);

		mButtonBack = (Button) findViewById(R.id.button_back);
		mButtonBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				NfcSigninActivity.this.finish();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mAdapter != null) {
			if (!mAdapter.isEnabled()) {
				showWirelessSettingsDialog();
			}
			mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
//			mAdapter.enableForegroundNdefPush(this, mNdefPushMessage);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mAdapter != null) {
			mAdapter.disableForegroundDispatch(this);
//			mAdapter.disableForegroundNdefPush(this);
		}
	}

	private void showWirelessSettingsDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.nfc_disabled);
		builder.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogInterface, int i) {
						Intent intent = new Intent(
								Settings.ACTION_WIRELESS_SETTINGS);
						startActivity(intent);
					}
				});
		builder.setNegativeButton(android.R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogInterface, int i) {
						finish();
					}
				});
		builder.create().show();
		return;
	}
	
	 @Override
	    public void onNewIntent(Intent intent) {
	        setIntent(intent);
	        resolveIntent(intent);
	    }
	 
	 private void resolveIntent(Intent intent) {
	        String action = intent.getAction();
	        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
	                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
	                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
	            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
	            NdefMessage[] msgs;
	            if (rawMsgs != null) {
	                msgs = new NdefMessage[rawMsgs.length];
	                for (int i = 0; i < rawMsgs.length; i++) {
	                    msgs[i] = (NdefMessage) rawMsgs[i];
	                }
	            } else {
	                // Unknown tag type
	                byte[] empty = new byte[0];
	                byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
	                Parcelable tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
	                byte[] payload = dumpTagData(tag).getBytes();
	                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id, payload);
	                NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
	                msgs = new NdefMessage[] { msg };
	            }
	            // Setup the views
	            List<String> ndefData = parseNdefMsg(msgs[0]);
	            
	            StringBuilder builder = new StringBuilder();
	            for(String d : ndefData) {
	            	builder.append(d);
	            	builder.append("_");
	            }
	            
	            String text =  builder.toString();
	            text = text.substring(0, text.length()-1);
	            
	            Toast.makeText(NfcSigninActivity.this, text, Toast.LENGTH_LONG).show();	            
	            finish();
	        }
	    }
	 
	  private List<String> parseNdefMsg(NdefMessage message) {
		  
		  List<String> ndefData = new ArrayList<String>();
		  
		  NdefRecord[] records = message.getRecords();
		  for(NdefRecord record : records) {
			  ndefData.add(new String(record.getPayload()));
		  }
		  
		  return ndefData;  
	  }
	 
	  private String dumpTagData(Parcelable p) {
	        StringBuilder sb = new StringBuilder();
	        Tag tag = (Tag) p;
	        byte[] id = tag.getId();
	        sb.append("Tag ID (hex): ").append(getHex(id)).append("\n");
	        sb.append("Tag ID (dec): ").append(getDec(id)).append("\n");
	        sb.append("ID (reversed): ").append(getReversed(id)).append("\n");

	        String prefix = "android.nfc.tech.";
	        sb.append("Technologies: ");
	        for (String tech : tag.getTechList()) {
	            sb.append(tech.substring(prefix.length()));
	            sb.append(", ");
	        }
	        sb.delete(sb.length() - 2, sb.length());
	        for (String tech : tag.getTechList()) {
	            if (tech.equals(MifareClassic.class.getName())) {
	                sb.append('\n');
	                MifareClassic mifareTag = MifareClassic.get(tag);
	                String type = "Unknown";
	                switch (mifareTag.getType()) {
	                case MifareClassic.TYPE_CLASSIC:
	                    type = "Classic";
	                    break;
	                case MifareClassic.TYPE_PLUS:
	                    type = "Plus";
	                    break;
	                case MifareClassic.TYPE_PRO:
	                    type = "Pro";
	                    break;
	                }
	                sb.append("Mifare Classic type: ");
	                sb.append(type);
	                sb.append('\n');

	                sb.append("Mifare size: ");
	                sb.append(mifareTag.getSize() + " bytes");
	                sb.append('\n');

	                sb.append("Mifare sectors: ");
	                sb.append(mifareTag.getSectorCount());
	                sb.append('\n');

	                sb.append("Mifare blocks: ");
	                sb.append(mifareTag.getBlockCount());
	            }

	            if (tech.equals(MifareUltralight.class.getName())) {
	                sb.append('\n');
	                MifareUltralight mifareUlTag = MifareUltralight.get(tag);
	                String type = "Unknown";
	                switch (mifareUlTag.getType()) {
	                case MifareUltralight.TYPE_ULTRALIGHT:
	                    type = "Ultralight";
	                    break;
	                case MifareUltralight.TYPE_ULTRALIGHT_C:
	                    type = "Ultralight C";
	                    break;
	                }
	                sb.append("Mifare Ultralight type: ");
	                sb.append(type);
	            }
	        }

	        return sb.toString();
	    }
	  
	  private String getHex(byte[] bytes) {
	        StringBuilder sb = new StringBuilder();
	        for (int i = bytes.length - 1; i >= 0; --i) {
	            int b = bytes[i] & 0xff;
	            if (b < 0x10)
	                sb.append('0');
	            sb.append(Integer.toHexString(b));
	            if (i > 0) {
	                sb.append(" ");
	            }
	        }
	        return sb.toString();
	    }

	  
	  private long getDec(byte[] bytes) {
	        long result = 0;
	        long factor = 1;
	        for (int i = 0; i < bytes.length; ++i) {
	            long value = bytes[i] & 0xffl;
	            result += value * factor;
	            factor *= 256l;
	        }
	        return result;
	    }

	    private long getReversed(byte[] bytes) {
	        long result = 0;
	        long factor = 1;
	        for (int i = bytes.length - 1; i >= 0; --i) {
	            long value = bytes[i] & 0xffl;
	            result += value * factor;
	            factor *= 256l;
	        }
	        return result;
	    }
}
