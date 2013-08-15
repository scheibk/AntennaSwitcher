package com.example.antennaswitcher;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
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
		
		DataConnectionChangeListener.getInstance().addReactor(this);
		TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		tm.listen(DataConnectionChangeListener.getInstance(),
				PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
	}
	
	private void setConnectionType(int networkType) {
		String type = NetworkTypeTranslator.translateNetworkType(networkType);
		
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
		DataConnectionChangeListener.getInstance().removeReactor(this);
	}
	
	@Override
	public void onChange(int state, int type) {
		setConnectionType(type);
		
		Intent intent = new Intent(this,AntennaWidget.class);
		intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
		
		AppWidgetManager widgetManager = AppWidgetManager.getInstance(getApplicationContext());
		int[] widgetIds = widgetManager.getAppWidgetIds(new ComponentName(this, AntennaWidget.class));
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds);
		sendBroadcast(intent);
		
	}

}
