/**
 * 
 */
package com.example.antennaswitcher;

import android.telephony.TelephonyManager;

/**
 * @author Kevin
 *
 */
public class NetworkTypeTranslator {
	
	public static String translateNetworkType(int networkType) {
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
		
		return type;
	}

}
