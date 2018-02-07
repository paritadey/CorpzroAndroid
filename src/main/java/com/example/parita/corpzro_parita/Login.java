package com.example.parita.corpzro_parita;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Login extends AppCompatActivity {
//TextView tv;
//String keyhash;
    private LoginButton btnLogin;
    private CallbackManager callbackManager;
    private ProfilePictureView profilePictureView;
    private LinearLayout infoLayout;
    private TextView email;
    private TextView gender;
    TextView date, type;
    private TextView facebookName;
    Button homepage;
    String loginemail, loginname, logindate, logintype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd/MM/yyyy : HH:mm:ss");
        Date myDate = new Date();
        String filename = timeStampFormat.format(myDate);
        date=(TextView)findViewById(R.id.dateofcreation);
        date.setText(filename);

        logindate=date.getText().toString().trim();

        type=(TextView)findViewById(R.id.type);

        logintype=type.getText().toString().trim();

        homepage=(Button)findViewById(R.id.homepage) ;
        homepage.setVisibility(View.INVISIBLE);
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                senddata();

            }
        });

      /*  tv = (TextView) findViewById(R.id.hashkey);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.parita.corpzro_parita",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyhash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                tv.setText(keyhash);
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/
        email = (TextView)findViewById(R.id.email);
        facebookName = (TextView)findViewById(R.id.name);
        gender = (TextView)findViewById(R.id.gender);
        infoLayout = (LinearLayout)findViewById(R.id.layout_info);
        profilePictureView = (ProfilePictureView)findViewById(R.id.image);
        btnLogin= (LoginButton) findViewById(R.id.login_button);

        // btnLogin.setReadPermissions(Arrays.asList(EMAIL));
        btnLogin.setReadPermissions(Arrays.asList("public_profile, email, user_birthday"));
        callbackManager = CallbackManager.Factory.create();

        // Callback registration
        btnLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("Main", response.toString());
                                setProfileToView(object);
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(Login.this, "error to Login Facebook", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void setProfileToView(JSONObject jsonObject) {
        try {
            email.setText(jsonObject.getString("email"));
            loginemail=email.getText().toString().trim();
            gender.setText(jsonObject.getString("gender"));
            facebookName.setText(jsonObject.getString("name"));
            loginname=facebookName.getText().toString().trim();

            profilePictureView.setPresetSize(ProfilePictureView.NORMAL);
            profilePictureView.setProfileId(jsonObject.getString("id"));
            infoLayout.setVisibility(View.VISIBLE);
            homepage.setVisibility(View.VISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void senddata(){
        Intent go=new Intent(Login.this, Welcome.class);
        go.putExtra("Username", loginname);
        go.putExtra("UserEmail", loginemail);
        go.putExtra("Time", logindate);
        go.putExtra("Type", logintype);
        startActivity(go);
    }
}