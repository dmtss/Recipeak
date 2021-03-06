package com.example.recipeak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class StartActivity extends AppCompatActivity {
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
       logout = findViewById(R.id.loginbutton);
        final SharedPreferences sharedPreferences = getSharedPreferences("User info", MODE_PRIVATE);
       logout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.putString(getResources().getString(R.string.pLoginState), "Logged out");
               editor.apply();
               startActivity(new Intent(StartActivity.this, MainActivity.class));
               finish();
           }
       });
    }
}
