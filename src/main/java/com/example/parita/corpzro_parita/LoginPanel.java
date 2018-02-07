package com.example.parita.corpzro_parita;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class LoginPanel extends AppCompatActivity {

    Button fb,gplus;
    EditText useremail, userpassword;
    Button login;
    String PasswordHolder, EmailHolder;
    boolean CheckEditText;
    TextView logintype;
    String finalResult ;
    String HttpURL="https://paritasampa95.000webhostapp.com/Corpzpro/Login.php\n";
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    public static final String UserEmail = "";
    String type;
    Context apContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_panel);

        init();
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(LoginPanel.this, Login.class);
                startActivity(n);
            }
        });
        gplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r = new Intent(LoginPanel.this, GooglePlus.class);
                startActivity(r);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextIsEmptyOrNot();

                if (CheckEditText) {

                    UserLoginFunction(EmailHolder, PasswordHolder);

                } else {

                    Toast.makeText(LoginPanel.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    public void UserLoginFunction(final String email, final String password){

        class UserLoginClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                isConnectingToInternet(apContext);
                progressDialog = ProgressDialog.show(LoginPanel.this,"Authenticating...",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                if(httpResponseMsg.equalsIgnoreCase("Data Matched")){

                    finish();

                    Intent intent = new Intent(LoginPanel.this, Welcome.class);
                    intent.putExtra(UserEmail, email);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(LoginPanel.this,"Error Occured",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("email",params[0]);

                hashMap.put("password",params[1]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(email,password);
    }

    //check whether the application is connected to the internet or not
    private boolean isConnectingToInternet(Context applicationContext){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            Toast.makeText(getApplicationContext(), "no internet", Toast.LENGTH_LONG).show();
            return false;
        } else
            return true;

    }
    public void CheckEditTextIsEmptyOrNot(){

        EmailHolder = useremail.getText().toString();
        PasswordHolder = userpassword.getText().toString();

        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
        {
            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }
    }

    public void init(){
        useremail=(EditText)findViewById(R.id.mailid);
        userpassword=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);

        fb=(Button)findViewById(R.id.facebook);
        gplus=(Button)findViewById(R.id.googleplus);

    }
}
