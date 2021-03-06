package com.example.recipeak;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import java.lang.*;

import static android.app.ProgressDialog.*;

public class Register extends AppCompatActivity {

    EditText name,email,password;
    TextView already;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.registerbutton2);
        already = findViewById(R.id.textView2);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String txtUserName = name.getText().toString();
                String txtEmail = email.getText().toString();
                String txtPassword = password.getText().toString();
                if(TextUtils.isEmpty(txtUserName) || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)){
                    Toast.makeText(Register.this, "You can't leave the fields blank.", Toast.LENGTH_SHORT).show();
                }
                else {
                    registerNewAccount(txtUserName,txtEmail,txtPassword);
                }

            }


        });

        already.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Register.this, MainActivity.class));
                finish();

            }


        });


    }
    private void registerNewAccount(final String name, final String email, final String password){
        final ProgressDialog progressDialog = new ProgressDialog(Register.this);
        progressDialog.setTitle("Setting up your account");
        progressDialog.show();
        String url= "http://10.0.2.2/loginregister/register.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Registration completed")){
                    progressDialog.dismiss();
                    Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Register.this, MainActivity.class));
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Register.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("name", name);
                param.put("email", email);
                param.put("password", password);
                return param;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Single.getMyInstance(Register.this).addToRequestQueue(request);

    }

}
