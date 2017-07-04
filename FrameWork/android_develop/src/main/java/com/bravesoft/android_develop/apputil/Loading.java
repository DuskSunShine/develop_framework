package com.bravesoft.android_develop.apputil;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bravesoft.android_develop.R;


/**
 * Created by SCY on 2017/7/4 14:55.
 */

public class Loading {

    public static Dialog startLoading(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading, null);// load view
        LinearLayout layout = (LinearLayout) v
                .findViewById(R.id.load_ll);// Load layout
        ImageView imageView= (ImageView) v.findViewById(R.id.load);
        TextView textView= (TextView) v.findViewById(R.id.load_text);
        if (AppUtil.isEn(context)){
            textView.setText(R.string.loading_en);
        }else if (AppUtil.isJp(context)){
            textView.setText(R.string.loading_jp);
        }else {
            textView.setText(R.string.loading_zh);
        }
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.loading);
        animation.setInterpolator(new LinearInterpolator());//It's important, or the rotation will pause at 360 degrees
        imageView.startAnimation(animation);
        Dialog loadingDialog = new Dialog(context, R.style.MyDialogStyle);// Create custom Styles dialog
        loadingDialog.setCancelable(true); // whether press the return key to disappear
        loadingDialog.setCanceledOnTouchOutside(false); // Click the area outside the loading box
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));// set view
        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);
        loadingDialog.show();

        return loadingDialog;
    }
    public static void endLoading(Dialog mDialogUtils) {
        if (mDialogUtils != null && mDialogUtils.isShowing()) {
            mDialogUtils.dismiss();
        }
    }
}
