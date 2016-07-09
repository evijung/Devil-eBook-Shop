package com.hitachi_tstv.samsen.tunyaporn.devilebookshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

//Main Class
public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, surnameEditText, userEditText, passwordEditText;
    private String nameString, surnameString, userString, passwordString;


    @Override
    //Main Method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind Widget คือการผูกตัวแปรกับ Widget บน Activity
        nameEditText = (EditText) findViewById(R.id.editText);
        surnameEditText = (EditText) findViewById(R.id.editText2);
        userEditText = (EditText) findViewById(R.id.editText3);
        passwordEditText = (EditText) findViewById(R.id.editText4);

    }

    //Click Sign Method
    public void clickSignUpSU(View view) {

        //Get Value from EditText
        nameString = nameEditText.getText().toString().trim();
        surnameString = surnameEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check space
        if (checkSpace()) {
            //True Have Space
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this,"มีช่องว่าง","กรุณากรอกข้อมูลให้ครบด้วยครับ");
        } else {
            //False No Space


        }
    }

    private boolean checkSpace() {
        boolean status = false;

        status = nameString.equals("") || surnameString.equals("") || userString.equals("") || passwordString.equals("");

        return status;
    }


}
