package com.bravesoft.android_develop.apputil;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import com.bravesoft.android_develop.R;

/**
 * Created by SCY on 2017/7/4 16:15.
 */

public class LoadingProgressDialog implements DialogInterface.OnCancelListener {

    private Dialog mDialog;
    private Context mContext;
    private DialogCancelListener mCancelListener;

    public LoadingProgressDialog(Context context) {
        this.mContext = context;
        mDialog = new Dialog(mContext, R.style.NobackDialog);
        mDialog.setContentView(R.layout.view_dialog_progress);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(true);
        mDialog.setOnCancelListener(this);
        TextView textView= (TextView) mDialog.findViewById(R.id.progress_text);
        if (AppUtil.isEn(context)){
            textView.setText(R.string.loading_en);
        }else if (AppUtil.isJp(context)){
            textView.setText(R.string.loading_jp);
        }else {
            textView.setText(R.string.loading_zh);
        }
    }

    public void showDialog() {

        mDialog.show();
    }

    public void removeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (mCancelListener != null) {
            mCancelListener.onDialogCancel();
        }
    }

    public void setOnCancelListener(DialogCancelListener listener) {
        this.mCancelListener = listener;
    }

    public interface DialogCancelListener {
        void onDialogCancel();
    }
}
