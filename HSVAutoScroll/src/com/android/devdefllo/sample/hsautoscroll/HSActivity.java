package com.android.devdefllo.sample.hsautoscroll;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HSActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static class PlaceholderFragment extends Fragment {
		
		HorizontalScrollView mHS;
		LinearLayout mLL;
		TextView num; EditText edit;
		
		public PlaceholderFragment() {
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			mHS = (HorizontalScrollView)rootView.findViewById(R.id.hs);
		    mLL = (LinearLayout)rootView.findViewById(R.id.ll);
		    
		    edit = (EditText)rootView.findViewById(R.id.edit); 
		    edit.setOnKeyListener(new View.OnKeyListener() {
	            public boolean onKey(View v, int keyCode, KeyEvent event) {
	                if (keyCode == KeyEvent.KEYCODE_ENTER) {
	                    if(!"".equals(edit.getText().toString()) 
	                    				&& Integer.parseInt(edit.getText().toString()) <= 15) {
	            		    mHS.post(new Runnable() {
	            		        @Override
	            		        public void run() {
	            		        	autoScroll(Integer.parseInt(edit.getText().toString()));
	            		        }
	            		    });
	                    } else
	                    	Toast.makeText(getActivity(), "Insert correct value (only \"1\" to \"15\").", 
	                    				Toast.LENGTH_LONG).show();
	                }
					return false;
	            }
		    });
		    
			return rootView;
		}
		
		private void autoScroll(int i) {
            // Width of the screen
            DisplayMetrics metrics = getActivity().getResources()
                                     .getDisplayMetrics();
            int widthScreen = metrics.widthPixels;

            // Width of one child (Button)
            int widthChild = mLL.getChildAt(i-1).getWidth();

            // Nb children in screen    
            int nbChildInScreen = widthScreen / widthChild;
            
            // Child position left    
            int positionLeftChild = mLL.getChildAt(i-1).getLeft();
			
            // Auto scroll to the middle
            mHS.smoothScrollTo( ( positionLeftChild - ( ( nbChildInScreen * widthChild ) / 2 ) + widthChild/2 ), 0);
		}
	}

}
