package com.example.entrylog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class logentrypage extends AppCompatActivity {
        EditText e1,e2,e3,e4;
        AppCompatButton b1,b2;
        //link copied from api pdf
        String apiUrl="http://10.0.4.16:3000/api/students";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logentrypage);

        e1=(EditText) findViewById(R.id.name);
        e2=(EditText) findViewById(R.id.admno);
        e3=(EditText) findViewById(R.id.sysno);
        e4=(EditText) findViewById(R.id.dept);
        b1=(AppCompatButton) findViewById(R.id.add);
        b2=(AppCompatButton) findViewById(R.id.logoutbtn);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getName=e1.getText().toString();
                String getAdmno=e2.getText().toString();
                String getSysno=e3.getText().toString();
                String getDept=e4.getText().toString();
            //creating JASON OBJECT
                JSONObject student=new JSONObject();
                try {
                    student.put("name",getName);
                    student.put("admission_number",getAdmno);
                    student.put("system_number",getSysno);
                    student.put("department",getDept);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                //jason object request creation
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                        Request.Method.POST,
                        apiUrl,
                        student,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                            }
                        }
                );
                //Request QUEUE
                //req q space obj = volley...
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonObjectRequest);

            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences=getSharedPreferences("logapp",MODE_PRIVATE);
                SharedPreferences.Editor editor= preferences.edit();
                editor.clear();
                editor.apply();
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
    }
}