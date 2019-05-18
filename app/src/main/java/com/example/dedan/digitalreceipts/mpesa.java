/*
package com.example.dedan.digitalreceipts;

import android.app.DownloadManager;
import android.util.Base64;
import android.view.Menu;
import android.view.textclassifier.TextSelection;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class mpesa {
    //To authenticate your app and get an OAuth access token, use this code. An access token expires in 3600 seconds or 1 hour

    // Use base64 to encode the consumer key and secret.
    String app_key = "YOUR_CONSUMER_KEY";
    String app_secret = "YOUR_CONSUMER_SECRET";
    String appKeySecret = app_key + ":" + app_secret;
    byte[] bytes = appKeySecret.getBytes("ISO-8859-1");
    String auth = String.valueOf(Base64.encode(bytes,0));

    OkHttpClient client = new OkHttpClient();

    DownloadManager.Request request = new TextSelection.Request.Builder()
            .url("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials")
            .get()
            .addHeader("authorization", "Basic " + auth)
            .addHeader("cache-control", "no-cache")
            .build();

    Response response = client.newCall(request).execute();

    public mpesa() throws UnsupportedEncodingException {
    }
    //To make an API call, you will need to authenticate your app.
    // We have provided an OAuth API for you to generate an access token, we support client_credentials grant type.
    // To authorize your API call to the OAuth API, you will need a Basic Auth over HTTPS authorization token.
    // The Basic Auth string is a base64 encoded string of your app’s client key and client secret.
    // We have provided a means to obtain the Basic Auth string for your sandbox apps; while you are in the OAuth API’s sandbox.
    // Click on ‘HTTP Basic Set Credentials’ button.

    //HTTP Basic Set Credentials

    //In the pop-up menu that appears, enter your consumer key and consumer secret.
    // You have an option to save these credentials for 30 days and therefore,
    // you do not need to set them every time you need an access token

    //Set Credentials Menu

   // The OAuth access token expires after an hour, after which, you will need to generate another access token.
    // On a production app, use a base64 library of the programming language you are using to build your app to get the Basic Auth string that you will then use to invoke our OAuth API to get an access token.
}
*/
