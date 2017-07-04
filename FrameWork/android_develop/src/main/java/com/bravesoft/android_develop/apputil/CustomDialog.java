package com.bravesoft.android_develop.apputil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SCY on 2017/7/4 16:07.
 */

public class CustomDialog {

    private OnPositive onPositive;
    /**
     * 没有标题的dialog,按钮为绿色
     * @param context
     * @param msg
     */
    public static void showMsgDialog(final Context context,String s, String msg){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setPositiveButton(s, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                button.setTextColor(Color.parseColor("#33A3E9"));
                button.setTextSize(16);
                TextView viewById = (TextView) alertDialog.findViewById(android.R.id.message);
                viewById.setPadding(0, 80, 0, 80);
                viewById.setTextSize(16);
                viewById.setTextColor(Color.parseColor("#FF000000"));
                viewById.setGravity(Gravity.CENTER);
            }
        });
        alertDialog.show();

    }

    // 共同提示信息 Toast
    public static void showToastMessage(Context mContext, String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * AlertDialog共用弹出信息,带有自定义标题
     * @param mContext
     * @param msg
     */
    public static void showDialogMessage(Context mContext,String s ,String msg) {
        TextView mTextView = new TextView(mContext);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setPadding(0, 50, 0, 50);
        mTextView.setText(msg);
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext)
                .setCustomTitle(mTextView).setPositiveButton(s, null);
        dialog.create();
        dialog.show();
    }

    /**
     *  有两个按钮并且没有标题的dialog
     * @param context
     * @param negative
     * @param positive
     * @param msg
     */
    private void showTowBtnDialog(Context context,String negative,String positive,String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage(msg);
        builder.setNegativeButton(negative,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onPositive.onPositiveClick(dialogInterface);
                dialogInterface.dismiss();

            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                Button button1 = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                button.setTextColor(Color.parseColor("#33A3E9"));
                button.setTextSize(16);
                button1.setTextColor(Color.parseColor("#33A3E9"));
                button1.setTextSize(16);
                TextView viewById = (TextView) alertDialog.findViewById(android.R.id.message);
                viewById.setPadding(0, 80, 0, 80);
                viewById.setTextSize(16);
                viewById.setTextColor(Color.parseColor("#FF000000"));
                viewById.setGravity(Gravity.CENTER);
            }
        });
        alertDialog.show();
    }

    public interface OnPositive{
        void onPositiveClick(DialogInterface dialogInterface);
    }
    public void setOnPositiveClickListener(OnPositive onPositive){
        this.onPositive=onPositive;
    }
}
