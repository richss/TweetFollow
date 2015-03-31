package com.richardstansbury.tweetfollow;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * @author Richard S. Stansbury
 *
 * This is a batch of unit tests for the application.
 *
 */
public class ActivityTest  extends
        ActivityInstrumentationTestCase2<MainActivity> {


    //Instrumentation
    Instrumentation _instrumentation;
    MainActivity _activity;

    //List Adapter
    ArrayAdapter<String> _listAdapter;
    ArrayList<String> _list;

    //Fields
    Button _followButton;
    Button _clearButton;
    Button _stopButton;
    EditText _searchTextField;
    ListView _listView;


    /**
     * Defines a default constructor calls superclass constructor with the
     * activity class being tested.
     */
    public ActivityTest() {
        super(MainActivity.class);

    }


    /**
     * Configures the Test Case
     */
    @Override
    public void setUp() throws Exception{
        Log.i("Test", "setUp started.");

        _instrumentation = this.getInstrumentation();
        setActivityInitialTouchMode(true);

        _activity = getActivity();

        _followButton = (Button) _activity.findViewById(R.id.search_button);
        _clearButton = (Button) _activity.findViewById(R.id.clear_button);
        _stopButton = (Button) _activity.findViewById(R.id.stop_button);

        _searchTextField = (EditText) _activity.findViewById((R.id.search_text));
        _listView = _activity.getListView();
        _listAdapter = (ArrayAdapter<String>) _listView.getAdapter();
        _list = _activity._tweets;

        Log.i("Test", "setUp complete.");

    }

    /**
     * Checks initialization.
     */
    public void testPreConditions() {

        assertEquals("", _searchTextField.getText().toString());
        assertEquals(0, _listAdapter.getCount());
    }

    /**
     * Tests that a query produces results.
     */
    public void testQuery() {

        //Perform Query

        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _searchTextField.setText("PS4");
                //_followButton.performClick(); //trigger click handler
            }
        });


        //Note, performClick commented out above.  Alternatively, you can try to simulate a user
        //  clicking with the TouchUtils.
        TouchUtils.clickView(this, _followButton);

        //Wait 5 seconds to collect data.
        try {
            Thread.sleep(5000);
        } catch(Exception e) {
            Log.e("Test","Exception in wait.");
        }

        TouchUtils.clickView(this, _stopButton);

        //Alternatively, you can just call the perform click function
        // while in the UI thread.
        /*_activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _stopButton.performClick();
            }
        });
        */


        int count = _listAdapter.getCount();

        assertTrue(count > 0);

        //Wait
        try {
            Thread.sleep(1000);
        } catch(Exception e) {
            Log.e("Test","Exception in wait.");
        }

        //Confirm stop worked.
        assertEquals(count, _listAdapter.getCount());
    }

    /**
     * Tests that a clear will actually clear the list adapter.
     */
    public void testClear() {

        //Add Items to the List and notify ListAdapter of change
        _list.add("Test1");
        _list.add("Test2");

        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _listAdapter.notifyDataSetChanged();
            }
        });

        assertEquals(2, _listAdapter.getCount()); //Confirm Add worked

        //Simulate pressing clear button
        TouchUtils.clickView(this, _clearButton);

        assertEquals(0, _listAdapter.getCount());
    }

}
