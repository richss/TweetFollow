package com.richardstansbury.tweetfollow;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import twitter4j.*;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author Richard S. Stansbury
 *
 * This is a support class to handle interactions with Twitter4J API for Twiter.
 *
 * It creates a _twitter stream class that will can be started using an array of keywords.
 *
 * It also implements the listener, which will receive the _tweets and add them to an array
 * list at location (0), i.e. front of the list.
 *
 * This assumes that it is being created by the main activity and receives the main
 * activity as its _parent.  This is necessary to 1) access the assets folder for configuration
 * and 2) allow the onStatus listener to trigger a UI update on the UI thread.
 *
 * Please see the readme.txt file in the root folder to learn how to configure your
 * assets/twitter4j.txt file for your Twitter API keys.
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


public class TwitterHelper implements StatusListener {

    private TwitterStream _twitter;
    private List<String> _tweets;
    private Activity _parent;
    private ArrayAdapter<String> _adapter;


    /**
     * Constructor
     * @param tweets - an array list to store _tweets received by this class
     * @param parentActivity - _parent activity that this helper is running on
     * @param adapter - array _adapter that is being updated by this class.
     */
    public TwitterHelper(List<String> tweets,
                         Activity parentActivity,
                         ArrayAdapter<String> adapter)
    {
        //Set up the Twitter Stream FActor and listener.
        this._twitter = new TwitterStreamFactory(readConfiguration(parentActivity)).getInstance();
        this._twitter.addListener(this);

        //Copy parameters to class variables
        this._tweets = tweets;
        this._parent = parentActivity;
        this._adapter = adapter;
    }

    /**
     * Creates an authentication configuration for accessing the _twitter streaming
     * API.
     *
     * See readme.txt in root folder for location and format of twitter4j.txt.
     *
     * @param c - context to access the asset's folder
     * @return configuration object based upon keys read from asset's folder
     */
    private Configuration readConfiguration(Context c)
    {

        //Create a Configuration Builder
        ConfigurationBuilder cb = new ConfigurationBuilder();
        try {

            //Create an input stream for the properties file
            BufferedReader in = new BufferedReader(new InputStreamReader(c.getAssets().open("twitter4j.txt")));

            //Set up the configuration
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey(in.readLine())
                    .setOAuthConsumerSecret(in.readLine())
                    .setOAuthAccessToken(in.readLine())
                    .setOAuthAccessTokenSecret(in.readLine());


        } catch(IOException io) {
            Log.e("TwitterHelper", "Error opening input file");
            System.exit(-1);
        }

        //Build the configuration and return the object
        return cb.build();
    }

    /**
     * Starts a filter query of the Twitter Streaming API.
     *
     * @param keywords array of string keywords
     */
    public void start(String [] keywords) {
        _twitter.filter((new FilterQuery()).track(keywords));
    }

    /**
     * Stops the filter query of the Twitter Streaming API
     */
    public void stop() {
        _twitter.cleanUp();
    }

    /**
     * Process's received Twitter status updates based upon the filter query.
     *
     * Implemented as part of the Status Listener Class.
     * @param status - a status update received by the listener.
     */
    @Override
    public void onStatus(Status status) {
        String tweetStr = status.getUser().getScreenName() + ": " + status.getText() + "(" + status.getCreatedAt() + ")";
        _tweets.add(0, tweetStr);
        _parent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _adapter.notifyDataSetChanged();
            }
        });
    }


    //////////////////////////
    //  Unused methods from the StatusListener interface

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    @Override
    public void onTrackLimitationNotice(int i) {

    }

    @Override
    public void onScrubGeo(long l, long l2) {

    }

    @Override
    public void onStallWarning(StallWarning stallWarning) {

    }

    @Override
    public void onException(Exception e) {
        e.printStackTrace();
    }
}
