package com.richardstansbury.tweetfollow;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;

/**
 * Project: TweetFollow
 *
 * This ListActivity represents the main activity of a simple twitter application
 * that given a keyword will show the live stream of tweets including the keyword.
 *
 * This is created for demonstration purposes for my SE 395/595 curriculum at
 * Embry-Riddle Aeronautical University; however, was written on a personal computer
 * during my own free time.  Therefore, I accept personal ownership of the project
 * and release it under the MIT open source license.
 *
 * @author Richard S. Stansbury
 *
 */

/* The MIT License (MIT)

 Copyright (c) 2015 Richard S. Stansbury

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 and associated documentation files (the "Software"), to deal in the Software without restriction,
 including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial
 portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.*/



public class MainActivity extends ListActivity {

    Button _followButton;
    EditText _searchTextField;
    TwitterHelper _twitter;
    ArrayAdapter<String> _adapter;
    ArrayList<String> _tweets;

    /**
     * Creates the activity
     * @param savedInstanceState - saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //Grab fields from layout
        _followButton = (Button) findViewById(R.id.search_button);
        _searchTextField = (EditText) findViewById(R.id.search_text);

        //Creates array list for the list adapter
        _tweets = new ArrayList<>();

        //Creates an array adapter type list adapter
        _adapter = new ArrayAdapter<>(this,
                R.layout.mylist,
                _tweets);
        //Set listener
        setListAdapter(_adapter);

        //Create an instance of the twitter helper class to handle streaming API.
        _twitter = new TwitterHelper(_tweets, this, _adapter);

    }


    /**
     * Handles press of the Search/Query button
     * @param v - view from button required for onClick handler
     */
    public void queryHandler(View v) {

        //Get query string
        String newSearchString = _searchTextField.getText().toString();

        //If string is not empty, do query
        if (!newSearchString.equals("")) {
            _twitter.start(new String[]{newSearchString});
        }
    }

    /**
     * Handles press of the pause button
     * @param v - view of pause button
     */
    public void pauseHandler(View v)
    {
        _twitter.stop();
    }


    /**
     * Handles press of clear button by clearning list and
     * notifying the adapter.
     * @param v - view of clear button
     */
    public void clearHandler(View v) {
        _tweets.clear();
        _adapter.notifyDataSetChanged();
    }
}
