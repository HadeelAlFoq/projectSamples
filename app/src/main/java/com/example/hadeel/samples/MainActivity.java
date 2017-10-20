package com.example.hadeel.samples;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText password,email;
    Button login;
    String Password,Email,devise_token,device_os;
     final String url="http://api.chal01jo2017011.dev.dot.jo/v1/user/users/login";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        password=(EditText)findViewById(R.id.input_password);
        email=(EditText)findViewById(R.id.input_email);
        login=(Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Password=password.getText().toString();
                Email=email.getText().toString();
                device_os=String.valueOf(2);
                devise_token="login";
                boolean submit= submitForm();
                if(submit==true){
                    loggedInToMainPage(Email,Password,devise_token,device_os);
                }else {
                    Toast.makeText(getApplication(),"Please Enter Email && Password ",Toast.LENGTH_LONG).show();

                }

            }
        });
    }
    private boolean submitForm() {
        


        if (!validateEmail()) {
            return false;
        }

        if (!validatePassword()) {
            return false;
        }
        else return true;


    }

    private boolean validatePassword() {
        String passwordCheck = password.getText().toString().trim();

        if (passwordCheck.isEmpty() ) {

            return false;
        } else {
        }

        return true;
    }

    private boolean validateEmail() {
        String emailCheck = email.getText().toString().trim();

        if (emailCheck.isEmpty() ) {
            
            return false;
        } else {
        }

        return true;
    }


    private void loggedInToMainPage(final String emailName, final String passwordName,final String device_token,final String device_os) {

        String tag_string_req = "req_login";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 Toast.makeText(getApplication(),"Response: " + response.toString(),Toast.LENGTH_LONG).show();  try {
                    JSONObject jsonObject = new JSONObject(response);
                    String error = jsonObject.getString("code");
                    if (error.matches("R200")) {

                        Toast.makeText(getApplication(),"No seccessful Login ",Toast.LENGTH_LONG).show();

                    }else {


                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Seccessful Login", Toast.LENGTH_SHORT).show();



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("volley Error .................");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", Email);
                params.put("password", Password);
                params.put("device_token", devise_token);
                params.put("device_os", device_os);
                return params;
            }

        };



        MySingletone.getInstance(MainActivity.this).addTorequestqueue(stringRequest);
    }
}

