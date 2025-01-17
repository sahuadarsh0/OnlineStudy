package com.techipinfotech.onlinestudy1.utils;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;

import com.techipinfotech.onlinestudy1.R;

public class ProcessDialog {
    Context context;
    Dialog dialog;
    ImageView imageView;

    public ProcessDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.process_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}