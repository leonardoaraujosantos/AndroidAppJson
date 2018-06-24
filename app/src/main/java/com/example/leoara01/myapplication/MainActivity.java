package com.example.leoara01.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);
        final TextView mTextView = (TextView) findViewById(R.id.textView);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                Log.d(TAG, "OnClick");
                mTextView.setText("JSON");
                //Toast.makeText("")
                Toast.makeText(getApplicationContext(), "Exemplo Toast", Toast.LENGTH_SHORT).show();

                //String url = "http://127.0.0.1:8080/list_persons";
                String url = "http://10.45.68.72:8080/list_persons";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                mTextView.setText("Response: " + response.toString());
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                mTextView.setText(error.getMessage());
                            }
                        });

                // Access the RequestQueue through your singleton class.
                requestQueue.add(jsonObjectRequest);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                Log.d(TAG, "OnClick");
                mTextView.setText("JSON");
                //Toast.makeText("")
                Toast.makeText(getApplicationContext(), "Exemplo Toast", Toast.LENGTH_SHORT).show();

                //String url = "http://127.0.0.1:8080/list_persons";
                String url = "http://10.45.68.72:8080/delete_person";

                // Json parameters
                Map<String, String> params = new HashMap();
                params.put("person", "James");
                JSONObject parameters = new JSONObject(params);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                mTextView.setText("Response: " + response.toString());
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                mTextView.setText(error.getMessage());
                            }
                        });

                // Access the RequestQueue through your singleton class.
                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}
