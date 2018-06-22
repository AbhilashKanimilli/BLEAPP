package com.example.user.bleapp;

import android.app.Service;
import java.sql.Timestamp;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 7/5/2018.
 */
public class MessengerService extends Service {
    String ServerURL = "http://ec2-13-126-21-75.ap-south-1.compute.amazonaws.com/insert_ble.php" ;
    private  RequestQueue requestQueue;

    /** Command to the service to display a message */
    static final int MSG_SAY_HELLO = 1;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void onCreate() {
       requestQueue = Volley.newRequestQueue(MessengerService.this);
    }

    /**
     * Handler of incoming messages from clients.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    //Toast.makeText(getApplicationContext(), "hello!", Toast.LENGTH_SHORT).show();
                    String result = msg.getData().getString("data");
                    String result1 = msg.getData().getString("address");
                    Log.v("Received in service", ""+result1+"--------"+result);
                    String ts  = dateFormat.format(new Date());
                    if(result != null) {
                        if (result.contains(":")) {
                            //
                            String[] parts = result.split(":");
                            if (parts.length != 2&&isOnline()) {
                                //throw new IllegalArgumentException(TAG + " - invalid format!");
                            } else {
                                String part1 = parts[0];
                                String part2 = parts[1];
                                InsertDatatomysql(result1, part2, part1,ts);
                            }
                        } else {
                            System.out.println("Corrupted data as: ");
                        }
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    /**
     * When binding to the service, we return an interface to our messenger
     * for sending messages to the service.
     */
    @Override
    public IBinder onBind(Intent intent) {
        //Toast.makeText(getApplicationContext(), "binding", Toast.LENGTH_SHORT).show();
        return mMessenger.getBinder();
    }
    public void InsertDatatomysql( final String address, final String heart, final String hand, final String t){
        // Creating Volley newRequestQueue
        //

        // Showing progress dialog at user registration time.
        // progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        // progressDialog.show();

        // Calling method to get value from EditText.
        // GetValueFromEditText();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        //progressDialog.dismiss();

                        // Showing response message coming from server.
                        //Toast.makeText(MainActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        Log.v("Server response: ", ""+ServerResponse);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        // progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        //Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                        Log.v("Server Error response: ", ""+volleyError.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("address",address);
                params.put("heart", heart);
                params.put("hand", hand);
                params.put("time", t);


                return params;
            }

        };
        if (requestQueue == null) {
            // Creating RequestQueue.
            requestQueue = Volley.newRequestQueue(MessengerService.this);

            // Adding the StringRequest object into requestQueue.

        }
        requestQueue.add(stringRequest);
    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }





}