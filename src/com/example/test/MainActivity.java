package com.example.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
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

public class MainActivity extends ActionBarActivity {

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

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
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
	                // TODO Auto-generated method stub
	                if (keyCode == KeyEvent.KEYCODE_ENTER) { //Whenever you got user click enter. Get text in edittext and check it equal test1. If it's true do your code in listenerevent of button3
	                    if(!"".equals(edit.getText().toString())) {
	                    	// Do a Runnable on the inflated view
	            		    mHS.post(new Runnable() {
	            		        @Override
	            		        public void run() {
	            		        	autoScroll(Integer.parseInt(edit.getText().toString()));
	            		        }
	            		    });
	                    }
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
            
            Log.v("","Width screen total = " + widthScreen);
            // Width of the container (LinearLayout)
            int widthContainer = mLL.getWidth();
            Log.v("","Width container total = " + widthContainer );

            // Width of one child (Button)
            int widthChild = mLL.getChildAt(i-1).getWidth();
            Log.v("","Width child = " + widthChild);

            // Nb children in screen    
            int nbChildInScreen = widthScreen / widthChild;
            Log.v("","Width screen total / Width child = " + nbChildInScreen);
            
            // 11th Child position left    
            int positionLeftChild = mLL.getChildAt(i-1).getLeft();
            Log.v("","6th Child position left = " + positionLeftChild);

            Log.v("","6th Child position left - (nb child in screen*width child / 2) - child width / 2  = "
            		+( positionLeftChild - ( (nbChildInScreen * widthChild) / 2 ) + widthChild/2 ));
			
            // Auto scroll to the middle
            mHS.smoothScrollTo( ( positionLeftChild - ( ( nbChildInScreen * widthChild ) / 2 ) + widthChild/2 ), 0);
		}
	}

}
