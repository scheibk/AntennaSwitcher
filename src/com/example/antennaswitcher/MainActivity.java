package com.example.antennaswitcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements DataChangeReactor {
	
	private DataConnectionChangeListener listener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getAntennaType();
		registerListener();
		
		final Button button = (Button)findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
				startActivity(intent);
				
			}
		});
		
	}
	
	private void getAntennaType() {
		TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		int mobileType = tm.getNetworkType();
		
		setConnectionType(mobileType);
	}
	
	private void registerListener() {
		if(listener == null) {
			listener = new DataConnectionChangeListener();
			listener.addReactor(this);
		}
		TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		tm.listen(listener, PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
	}
	
	private void setConnectionType(int networkType) {
		String type;
		switch(networkType) {
			case TelephonyManager.NETWORK_TYPE_1xRTT: 
				type = "1xRTT (CDMA)";
				break;
			case TelephonyManager.NETWORK_TYPE_EDGE:
				type = "EDGE";
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
				type = "EVDO 0";
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
				type = "EVDO A";
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_B :
				type = "EVDO B";
				break;
			case TelephonyManager.NETWORK_TYPE_CDMA	:
				type = "3G (CDMA)";
				break;
			case TelephonyManager.NETWORK_TYPE_EHRPD : 
				type = "3G (EHRPD)";
				break;
			case TelephonyManager.NETWORK_TYPE_LTE :
				type = "4G (LTE)";
				break;
			default:
				type = "unknown";
		}
		
		TextView textView = (TextView)findViewById(R.id.textView1);
		textView.setText(type);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return false;
	}
	
	protected void onResume() {
		super.onResume();
		getAntennaType();
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);
	}
	
	@Override
	public void onChange(int state, int type) {
		setConnectionType(type);
		
	}

}
