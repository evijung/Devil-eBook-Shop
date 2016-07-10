package com.hitachi_tstv.samsen.tunyaporn.devilebookshop;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;

//Main Class
public class DetailActivity extends AppCompatActivity {
    //Explicit
    private TextView bookTextView, priceTextView;
    private ImageView iconImageView;
    private String bookString, priceString, iconString, nameString, surnameString;
    private String urlPHP;

    @Override
    //Main Method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        MyConstant myConstant = new MyConstant();
        urlPHP = myConstant.getUrlAddOrder();

        //Bind Widget
        bookTextView = (TextView) findViewById(R.id.textView10);
        priceTextView = (TextView) findViewById(R.id.textView11);
        iconImageView = (ImageView) findViewById(R.id.imageView3);

        nameString = getIntent().getStringExtra("Name");
        surnameString = getIntent().getStringExtra("Surname");
        priceString = getIntent().getStringExtra("Price");
        bookString = getIntent().getStringExtra("Book");
        iconString = getIntent().getStringExtra("Icon");

        //Show View
        bookTextView.setText(bookString);
        priceTextView.setText(priceString + " ฿");
        Picasso.with(this).load(iconString).resize(250,300).into(iconImageView);


    }

    public void clickBack(View view) {
        finish();
    }

    public void clickOrder(View view) {
        if (checkSpace()) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่าง", "กรุณากรอกข้อมูลให้ครบด้วยครับ");
        } else {
            updateNewOrderToServer(this);
        }
    }

    private void updateNewOrderToServer(final Context context) {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("Name", nameString)
                .add("Surname", surnameString)
                .add("BookName", bookString)
                .add("Price", priceString).build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlPHP).post(requestBody).build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                finish();
            }
        });

    }

    private boolean checkSpace() {
        boolean status = false;

        status = nameString.equals("") || surnameString.equals("") || bookString.equals("") || priceString.equals("");

        return status;

    }
}
