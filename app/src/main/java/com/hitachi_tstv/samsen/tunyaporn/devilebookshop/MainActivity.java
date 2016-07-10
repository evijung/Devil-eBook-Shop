package com.hitachi_tstv.samsen.tunyaporn.devilebookshop;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

//Main Class
public class MainActivity extends AppCompatActivity {
    //Explicit
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;
    private static final String urlJSON = "http://swiftcodingthai.com/9july/get_user_bell.php";

    @Override
    //Main Method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        userEditText = (EditText) findViewById(R.id.editText5);
        passwordEditText = (EditText) findViewById(R.id.editText6);

    }

    //SyncUserTable Class
    private class SyncUserTable extends AsyncTask<Void, Void, String> {
        //Explicit
        private Context context;
        private String myURL, myUserString, myPasswordString, truePassword;
        private boolean statusABoolean = true;

        public SyncUserTable(Context context, String myURL, String myUserString, String myPasswordString) {
            this.context = context;
            this.myURL = myURL;
            this.myUserString = myUserString;
            this.myPasswordString = myPasswordString;
        }

        @Override
        //Do in background
        protected String doInBackground(Void... params) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(myURL).build();
                Response response = okHttpClient.newCall(request).execute();

                return response.body().string();

            } catch (Exception e) {
                Log.d("ShopV1", "e doInBack ==> " + e.toString());
                return null;
            }

        }

        @Override
        //On post
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ShopV1", "JSON ==> " + s);

            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0;i < jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (myUserString.equals(jsonObject.getString("User"))) {
                        truePassword = jsonObject.getString("Password");
                        statusABoolean = false;
                    }
                }
                if (statusABoolean) {
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context, "ไม่พบผู้ใช้", "ไม่มี " + myUserString + " ในฐานข้อมูล");
                } else if (!myPasswordString.equals(truePassword)) {
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context, "รหัสผ่านผิด", "กรุณากรอกรหัสผ่านใหม่");
                } else {
                    Toast.makeText(context,"Welcome", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d("ShopV1", "e onPost ==> " + e.toString());
            }
        }
    }


    //Click Sign In
    public void clickSignIn(View view) {
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check Space
        if (userString.equals("") || passwordString.equals("")) {
            //True Have Space
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this,"มีค่าว่าง","กรุณากรอกข้อมูลให้ครบ");

        } else {
            //False No Space
            SyncUserTable syncUserTable = new SyncUserTable(this, urlJSON, userString, passwordString);
            syncUserTable.execute();
        }
    }

    //Click Sign Up in Main Activity
    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }

}
