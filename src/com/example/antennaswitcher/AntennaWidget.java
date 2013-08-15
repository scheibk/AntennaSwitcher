package com.example.antennaswitcher;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class AntennaWidget extends AppWidgetProvider implements DataChangeReactor {
	private Context c;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// There may be multiple widgets active, so update all of them
		this.c = context;
		
		TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		int networkType = tm.getNetworkType();
		String widgetText = NetworkTypeTranslator.translateNetworkType(networkType);
		
		
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			updateAppWidget(context, appWidgetManager, appWidgetIds[i], widgetText);
		}
	}
	

	@Override
	public void onEnabled(Context context) {
		DataConnectionChangeListener.getInstance().addReactor(this);
	}

	@Override
	public void onDisabled(Context context) {
		// Enter relevant functionality for when the last widget is disabled
	}

	static void updateAppWidget(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId, String widgetText) {

		
		
		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		
		// Construct the RemoteViews object
		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.antenna_widget);
		views.setTextViewText(R.id.appwidget_text, widgetText);
		views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

		// Instruct the widget manager to update the widget
		appWidgetManager.updateAppWidget(appWidgetId, views);
		
	}
	
	
	@Override
	public void onChange(int state, int type) {
		if(c != null) {
			String widgetText = NetworkTypeTranslator.translateNetworkType(type);
			
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(c);
			
			ComponentName cn = new ComponentName(c, com.example.antennaswitcher.AntennaWidget.class);
			int[] ids = appWidgetManager.getAppWidgetIds(cn);
			for(int i = 0; i < ids.length; i++) {
				updateAppWidget(c, appWidgetManager, ids[i], widgetText);
			}
		}
		
	}

	
	


}
