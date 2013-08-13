/**
 * 
 */
package com.example.antennaswitcher;

import java.util.ArrayList;
import java.util.List;

import android.telephony.PhoneStateListener;

/**
 * @author Kevin
 *
 */
public class DataConnectionChangeListener extends PhoneStateListener {
	
	List<DataChangeReactor> reactors;
	
	public DataConnectionChangeListener() {
		super();
		reactors = new ArrayList<DataChangeReactor>();
	}
	
	@Override
	public void onDataConnectionStateChanged(int state, int networkType) {
		super.onDataConnectionStateChanged(state, networkType);
		
		for(DataChangeReactor dcr: reactors) {
			dcr.onChange(state, networkType);
		}
	}
	
	public void addReactor(DataChangeReactor dcr)  {
		if(dcr != null) {
			reactors.add(dcr);
		}
	}

}
