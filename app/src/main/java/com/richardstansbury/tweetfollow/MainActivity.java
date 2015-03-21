package com.richardstansbury.tweetfollow;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

/**
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

    Button followButton;
    EditText searchTextField;
    TwitterHelper twitter;

    String searchString;

    ArrayAdapter<String> _adapter;
    ArrayList<String> _tweets;
    int listCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        followButton = (Button) findViewById(R.id.search_button);
        searchTextField = (EditText) findViewById(R.id.search_text);

        searchString = "";

        _tweets = new ArrayList<>();
        listCount = 0;

        _adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                _tweets);
        setListAdapter(_adapter);

        twitter = new TwitterHelper(_tweets, this, _adapter);

    }


    public void queryHandler(View v) {
        String newSearchString = searchTextField.getText().toString();


        Log.i("ButtonEvent", "Button was pressed.");
        Toast toast = Toast.makeText(getApplicationContext(), "search=" + newSearchString, Toast.LENGTH_SHORT);
        toast.show();

        if (!newSearchString.equals("")) {
            searchString = newSearchString;
            twitter.start(new String[]{newSearchString});
        }
    }

    public void pauseHandler(View v)
    {
        Log.d("Pause Handler", "Paused");
        Toast toast = Toast.makeText(getApplicationContext(), "Stopped", Toast.LENGTH_SHORT);
        toast.show();
        twitter.stop();
    }
}
