package com.hitachi_tstv.samsen.tunyaporn.devilebookshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

//Main Class
public class DetailActivity extends AppCompatActivity {
    //Explicit
    private TextView bookTextView, priceTextView;
    private ImageView iconImageView;
    private String bookString, priceString, iconString, nameString, surnameString;

    @Override
    //Main Method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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

    }
}
