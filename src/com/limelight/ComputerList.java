package com.limelight;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.limelight.nvstream.NvComputer;
import com.limelight.nvstream.NvmDNS;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ComputerList extends Activity {
	
	public class ComputerListAdapter extends ArrayAdapter<NvComputer> {
		
		private HashMap<NvComputer, Integer> mIdMap = new HashMap<NvComputer, Integer>();
		
		public ComputerListAdapter(Context context, int textViewResourceId, List<NvComputer> objects) {
			super(context, textViewResourceId, objects);
			int i = 0;
			for (NvComputer computer : objects) {
				this.mIdMap.put(computer, i);
				i++;
			}
		}
		
		@Override
		public boolean hasStableIds() {
			return true;
		}
	}
	
	private NvmDNS nvmDNS;
	private LinkedList<NvComputer> computerList;
	private ComputerListAdapter computerListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_computer_list);
		
		final ListView listView = (ListView) findViewById(R.id.computerList);
		this.computerList = new LinkedList<NvComputer>();
		this.computerListAdapter = new ComputerListAdapter(this, android.R.layout.simple_list_item_1, computerList);
		
		listView.setAdapter(computerListAdapter);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		this.nvmDNS = new NvmDNS(this, this.computerList, this.computerListAdapter);
		this.nvmDNS.sendQuery();
		this.nvmDNS.waitForResponses();
	}
	
	@Override 
	protected void onPause() {
		super.onPause();
		
		this.nvmDNS.cleanup();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.computer_list, menu);
		return true;
	}

}
