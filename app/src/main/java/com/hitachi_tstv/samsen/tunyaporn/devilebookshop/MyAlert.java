package com.hitachi_tstv.samsen.tunyaporn.devilebookshop;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by tunyaporns on 9/7/2559.
 */

//Main Class
public class MyAlert {

    public void myDialog(Context context, String strTitle, String strMessage) {
        //Create constant or object
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.rat48);
        builder.setTitle(strTitle);
        builder.setMessage(strMessage);
        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //ปิด dialog
                dialog.dismiss();
            }
        });
        builder.show();

    }

}
