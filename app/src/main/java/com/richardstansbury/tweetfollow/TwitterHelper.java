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
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author Richard S. Stansbury
 *
 * This is a class to handle Twitter4J method calls to provide twitter functionality for app.
 */
public class TwitterHelper implements StatusListener {

    TwitterStream twitter;
    List<String> tweets;
    Activity parent;
    ArrayAdapter<String> adapter;

    public TwitterHelper(List<String> tweets, Activity parentActivity, ArrayAdapter<String> adapter)
    {
        ConfigurationBuilder cb = readConfiguration(parentActivity);
        this.twitter = new TwitterStreamFactory(cb.build()).getInstance();
        this.tweets = tweets;
        this.parent = parentActivity;
        this.adapter = adapter;
        twitter.addListener(this);
    }

    private ConfigurationBuilder readConfiguration(Context c)
    {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(c.getAssets().open("twitter4j.txt")));


            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey(in.readLine())
                    .setOAuthConsumerSecret(in.readLine())
                    .setOAuthAccessToken(in.readLine())
                    .setOAuthAccessTokenSecret(in.readLine());


        } catch(IOException io) {
            Log.e("TwitterHelper", "Error opening input file");
            System.exit(-1);
        }
        return cb;
    }

    public void start(String [] keywords) {
        twitter.filter((new FilterQuery()).track(keywords));
    }

    public void stop() {
        twitter.cleanUp();
    }

    @Override
    public void onStatus(Status status) {
        String tweetStr = status.getUser().getScreenName() + ": " + status.getText() + "(" + status.getCreatedAt() + ")";
        tweets.add(0, tweetStr);
        parent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

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