package com.hitachi_tstv.samsen.tunyaporn.devilebookshop;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

//Main Class
public class ServiceActivity extends AppCompatActivity {
    //Explicit
    private TextView textView;
    private ListView listView;
    private String nameString, surnameString, urlJSON;

    @Override
    //Main Method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        MyConstant myConstant = new MyConstant();
        urlJSON = myConstant.getUrlJSONProduct();

        //Bind Widget
        textView = (TextView) findViewById(R.id.textView7);
        listView = (ListView) findViewById(R.id.listView);

        //Show View
        nameString = getIntent().getStringExtra("Name");
        surnameString = getIntent().getStringExtra("Surname");

        textView.setText("Welcome " + nameString + " " + surnameString);

        //Sync and Create ListView
        SyncProduct syncProduct = new SyncProduct(this, urlJSON, listView);
        syncProduct.execute();

    }

    //SyncProduct Class
    private class SyncProduct extends AsyncTask<Void, Void, String> {
        //Explicit
        private Context context;
        private String myURL;
        private ListView myListView;
        private String[] bookString, priceStrings, iconStrings;

        public SyncProduct(Context context, String myURL, ListView myListView) {
            this.context = context;
            this.myURL = myURL;
            this.myListView = myListView;
        }

        @Override
        //Do in back Method คือการทำงานอยู่เบื้องหลัง
        protected String doInBackground(Void... params) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(myURL).build();
                Response response = okHttpClient.newCall(request).execute();

                return response.body().string();
            } catch (Exception e) {
                Log.d("ShopV2", "e doInBack ==> " + e.toString());
                return null;
            }

        }

        @Override
        //On post Method คือสิ่งที่ทำหลังทำงานเสร็จ
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ShopV2", "JSON ==> " + s);

            try {
                JSONArray jsonArray = new JSONArray(s);

                bookString = new String[jsonArray.length()];
                priceStrings = new String[jsonArray.length()];
                iconStrings = new String[jsonArray.length()];

                for (int i = 0;i < jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    bookString[i] = jsonObject.getString("Name");
                    priceStrings[i] = jsonObject.getString("Price");
                    iconStrings[i] = jsonObject.getString("Cover");
                }

                MyAdaptor myAdaptor = new MyAdaptor(context,bookString,priceStrings,iconStrings);
                myListView.setAdapter(myAdaptor);

            } catch (Exception e) {
                Log.d("ShopV2", "e onPost ==> " + e.toString());
            }
        }
    }


}
