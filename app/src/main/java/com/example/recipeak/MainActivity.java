package com.example.recipeak;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
          EditText email, password;
          TextView skip;
 Button login, register;
 SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.loginbutton);
        register = findViewById(R.id.registerbutton);
        skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestSkip.class));
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, Register.class));
                finish();

            }


        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail = email.getText().toString();
                String txtPassword = password.getText().toString();
                if(TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)){
                    Toast.makeText(MainActivity.this, "All fields must be filled.", Toast.LENGTH_SHORT).show();

                }
                else{
                    login(txtEmail,txtPassword);
                }
            }
        });



}
   private void login (final String email, final String password){
       final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
       progressDialog.setTitle("Logging in to your account");
       progressDialog.show();
       String url= "http://10.0.2.2/loginregister/login.php";
       StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
                 if(response.equals(("Login successful"))){
                     progressDialog.dismiss();
                     Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                     SharedPreferences.Editor editor = sharedPreferences.edit();
                     startActivity(new Intent(MainActivity.this, StartActivity.class));
                     editor.apply();
                 }

                 else {
                     progressDialog.dismiss();
                     Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();

                 }

           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               progressDialog.dismiss();
               Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
           }
       }) {
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               HashMap<String, String> param = new HashMap<>();
               param.put("email", email);
               param.put("password", password);
               return param;
           }

       };
       req.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       Single.getMyInstance(MainActivity.this).addToRequestQueue(req);
   }
}
