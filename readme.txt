Project:  TweetFollow
Author:  Richard S. Stansbury


This is a simple project to query twitter with a simple keyword / keyphrase search
and have it update the result in a ListView using the Twitter Streaming API.


Requires twitter4j library, copied to your app/lib folder.  Download twitter4j at:
http://twitter4j.org/en/index.html#download.  Note: designed for Twitter4j Version 4.0.2

For setup, you need to create an app/src/main folder.  In that folder, create a file
named twitter4j.txt.  This file should have four lines.
    <OAuthConsumerKey>
    <OAuthConsumerSecret>
    <OAuthAccessToken>
    <OAuthAccessTokenSecret>


 The MIT License (MIT)

 Copyright (c) 2015 Richard S. Stansbury

 Permission is hereby granted, free of charge, to any person obtaining a copy of
 this software and associated documentation files (the "Software"), to deal in
 the Software without restriction, including without limitation the rights to use,
 copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
 Software, and to permit persons to whom the Software is furnished to do so,
 subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.