package com.example.caloriecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

        private EditText et1, et2;
        private TextView tv_result;
        private Button btn;
        SignInButton signInButton;
        private GoogleApiClient googleApiClient;
        TextView textView;
        private static final int RC_SIGN_IN = 1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                et1=(EditText)findViewById(R.id.et_weight);
                et2=(EditText)findViewById(R.id.et_height);
                tv_result = (TextView)findViewById(R.id.tv_result);

                btn = (Button)findViewById(R.id.button);

                btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                bmi();
                        }
                });
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                googleApiClient = new GoogleApiClient.Builder(this)
                        .enableAutoManage(this, this)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();

                signInButton = (SignInButton) findViewById(R.id.sign_in_button);
                signInButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                                startActivityForResult(intent, RC_SIGN_IN);
                        }
                });


        }

        {

        }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if (requestCode == RC_SIGN_IN) {
                        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                        handleSignInResult(result);
                }
        }


        private void handleSignInResult(GoogleSignInResult result) {
                if (result.isSuccess()) {
                        gotoProfile();


                } else {
                        Toast.makeText(getApplicationContext(), "Sign in cancel", Toast.LENGTH_LONG).show();
                }
        }


        private void gotoProfile() {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
        }


        @SuppressLint("ShowToast")
        private void bmi () {
                float a, b, c;
                a = Float.parseFloat(et2.getText().toString())/100;
                b = Float.parseFloat(et1.getText().toString());
                c = b / (a*a);
                tv_result.setText("" +c);


                if  (c <= 18.8) {
                        Toast.makeText(getApplicationContext(), "You are underweight", Toast.LENGTH_SHORT).show();
                }
                if  (( c >= 18.8 ) &&( c<25 )) {
                        Toast.makeText(getApplicationContext(), "You are normal", Toast.LENGTH_SHORT).show();
                }
                if  (c >=25 ){
                        Toast.makeText(getApplicationContext(), "You are overweight", Toast.LENGTH_SHORT).show();
                }
        }






        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }
}