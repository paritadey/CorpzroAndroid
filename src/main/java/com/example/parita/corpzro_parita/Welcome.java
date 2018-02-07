package com.example.parita.corpzro_parita;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Welcome extends AppCompatActivity {
    String EmailHolder;
    TextView etUsername;
    Button bLogout;
 //   public static final String UserEmail = "";
    TextView fbemail,fbname, fbtime, fbtype, fbappid, gplusmail, gplusname,gplustime,gplustype, gplusappid;
    String fb_Email, fb_name, fb_time, fb_type, fb_appid;
    String gplus_email, gplus_name, gplus_time, gplus_type, gplus_Appid;
    boolean typeoflogin=true;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult ;
    String HttpURL="https://paritasampa95.000webhostapp.com/Corpzpro/Sociallogin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        etUsername = (TextView) findViewById(R.id.etUsername); //email

        bLogout = (Button) findViewById(R.id.bLogout);

    //    typeoflogin=(TextView)findViewById(R.id.logintype);
        //Intent frommanuallogin=getIntent();
//        typeoflogin.setText(frommanuallogin.getCharSequenceExtra("Type"));

        fbemail=(TextView)findViewById(R.id.fbemail);
        fbname=(TextView)findViewById(R.id.fbname);
        fbtime=(TextView)findViewById(R.id.fbtime);
        fbtype=(TextView)findViewById(R.id.fbtype);

        fbappid=(TextView)findViewById(R.id.fbappid);
        fb_appid=fbappid.getText().toString();

        gplusmail=(TextView)findViewById(R.id.gplusmail);
        gplusname=(TextView)findViewById(R.id.gplusname);
        gplustime=(TextView)findViewById(R.id.gplustime);
        gplustype=(TextView)findViewById(R.id.gplustype);

        gplusappid=(TextView)findViewById(R.id.gplusappid);
        gplusappid.setText(R.string.gplus_Oauth_id);
        gplus_Appid=gplusappid.getText().toString();

        Intent intent = getIntent();
        EmailHolder = intent.getStringExtra(LoginPanel.UserEmail);
        etUsername.setText(EmailHolder);

        Intent getfromfb=getIntent();
        fbemail.setText(getfromfb.getCharSequenceExtra("UserEmail"));
        fbname.setText(getfromfb.getCharSequenceExtra("Username"));
        fbtime.setText(getfromfb.getCharSequenceExtra("Time"));
        fbtype.setText(getfromfb.getCharSequenceExtra("Type"));

        fb_Email=fbemail.getText().toString();
        fb_name=fbname.getText().toString();
        fb_time=fbtime.getText().toString();
        fb_type=fbtype.getText().toString();

        Intent getfromgplus=getIntent();
        gplusmail.setText(getfromgplus.getCharSequenceExtra("UserGemail"));
        gplusname.setText(getfromgplus.getCharSequenceExtra("UserGname"));
        gplustime.setText(getfromgplus.getCharSequenceExtra("Usertime"));
        gplustype.setText(getfromgplus.getCharSequenceExtra("Type"));

        gplus_email=gplusmail.getText().toString();
        gplus_name=gplusname.getText().toString();
        gplus_time=gplustime.getText().toString();
        gplus_type=gplustype.getText().toString();

        Storedatatodatabase();

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

                Intent intent = new Intent(Welcome.this, MainActivity.class);

                startActivity(intent);

                Toast.makeText(Welcome.this, "Log Out Successfully", Toast.LENGTH_LONG).show();
            }
        });


    }
    public void Storedatatodatabase(){

            DataStore(fb_Email, fb_name, fb_time, fb_type, fb_appid);
            DataStore( gplus_email, gplus_name, gplus_time, gplus_type, gplus_Appid);

    }

    public void DataStore(final String email,  final String name, final String time ,final String type, final String id){

        class DataStoreFunctionClass extends AsyncTask<String,Void,String> {


            @Override
            protected String doInBackground(String... params) {

                hashMap.put("email",params[0]);

                hashMap.put("name",params[1]);

                hashMap.put("time", params[2]);

                hashMap.put("type", params[3]);

                hashMap.put("id", params[4]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }

        }

        DataStoreFunctionClass dataStoreFunctionClass = new DataStoreFunctionClass();

        dataStoreFunctionClass.execute(email,name, time, type, id);
    }

}
