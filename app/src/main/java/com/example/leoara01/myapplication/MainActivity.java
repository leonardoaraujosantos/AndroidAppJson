package com.example.leoara01.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ImageView imgFace;
    Bitmap bmpFace;
    String imgStringEncoded = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        bmpFace = (Bitmap)data.getExtras().get("data");
        imgFace.setImageBitmap(bmpFace);
        imgStringEncoded = encodeToBase64(bmpFace);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize widgets from resource file
        Button btListPerson = (Button) findViewById(R.id.btListPerson);
        Button btDeletePerson = (Button) findViewById(R.id.btDeletePerson);
        Button btAddPerson = (Button) findViewById(R.id.btAddPerson);
        final TextView mTextView = (TextView) findViewById(R.id.textView);
        imgFace = (ImageView) findViewById(R.id.imgFace);

        // Get list of persons
        btListPerson.setOnClickListener(new View.OnClickListener() {
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

        // Delete Person
        btDeletePerson.setOnClickListener(new View.OnClickListener() {
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

        // Get Picture
        btAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
    }

    // Encode image to PNG base64
    public static String encodeToBase64(Bitmap image) {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.e(TAG, imageEncoded);
        return imageEncoded;
    }
}
