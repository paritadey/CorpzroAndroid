package com.example.parita.corpzro_parita;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    TextView loginlink;
    Button reg;
    EditText fullname, email, phone,password;
    public String msg="Email Already Exist";
    String flag;
    Boolean CheckEditText,passwordmatch ;
    ProgressDialog progressDialog;

    String uname, uemail, uphone, upass;
    String finalResult ;
    String HttpURL="https://paritasampa95.000webhostapp.com/Corpzpro/Registration.php";
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    Context apContext, ctx;
    String myemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        loginlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login=new Intent(MainActivity.this, LoginPanel.class);
                startActivity(login);
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextIsEmptyOrNot();
                Checkpassword();
                if (CheckEditText && passwordmatch) {

                    // If EditText is not empty and CheckEditText = True then this block will execute.

                    UserRegisterFunction(uemail,uname, uphone, upass);


                } else {
                    // If EditText is empty then this block will execute .
                    Toast.makeText(MainActivity.this, "Please fill all form fields correctly.",
                            Toast.LENGTH_LONG).show();

                }
            }
        });

    }
    public void UserRegisterFunction(final String email,  final String name, final String phone,final String password){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                isConnectingToInternet(apContext);
                progressDialog = ProgressDialog.show(MainActivity.this,"Registering...",null,
                        true,true);
            }



            @Override
            protected String doInBackground(String... params) {

                hashMap.put("email",params[0]);

                hashMap.put("name",params[1]);

                hashMap.put("phone",params[2]);

                hashMap.put("password", params[3]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                isInternet(ctx);
                flag=httpResponseMsg;
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Server Result: "+httpResponseMsg, Toast.LENGTH_LONG).show();
                clearTextFields();
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(email,name, phone, password);
    }

public void CheckEditTextIsEmptyOrNot(){
        uname=fullname.getText().toString().trim();
        uemail=email.getText().toString().trim();
        myemail=email.getText().toString().trim();
        uphone=phone.getText().toString().trim();
        upass=password.getText().toString().trim();

        if(TextUtils.isEmpty(uemail) || TextUtils.isEmpty(uname) || TextUtils.isEmpty(uphone) || TextUtils.isEmpty(upass))
        {
            CheckEditText = false;
        }
        else {
            CheckEditText = true;
        }
    }
    public void Checkpassword(){
        if(password.length()>0 ){
            passwordmatch=true;
        }
        else{
            passwordmatch = false;
        }
    }

    public void clearTextFields(){
        fullname.setText("");
        email.setText("");
        phone.setText("");
        password.setText("");
    }
    public void init(){
        fullname=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.mailid);
        phone=(EditText)findViewById(R.id.userphoneno);
        loginlink=(TextView)findViewById(R.id.loginlink);
        password=(EditText)findViewById(R.id.password);
        reg=(Button)findViewById(R.id.register);
    }
    //check whether the application is connected to the internet or not
    private boolean isConnectingToInternet(Context applicationContext){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            Toast.makeText(getApplicationContext(), "no internet", Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this,"Connect to the Wifi/Mobile Data",Toast.LENGTH_LONG).show();
            return false;
        } else
            return true;

    }

    private boolean isInternet(Context applicationContext){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            Toast.makeText(getApplicationContext(), "Registration cannot be possible without Internet", Toast.LENGTH_SHORT).show();
            return false;
        } else{
            if(msg==flag)
            Toast.makeText(MainActivity.this, "Successfully Registered !!!",Toast.LENGTH_LONG).show();
            return true;
        }

    }
    @Override
    public void onBackPressed() {

            //super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Exit from the application ");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        builder.show();

    }

}