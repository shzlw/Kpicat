package com.kpicat.kpicat.screen;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class CustomProgressDialog {

    private ProgressDialog dialog;

    public CustomProgressDialog(Context c, String message) {
        dialog = new ProgressDialog(c);
        dialog.setMessage(message);
        dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e2e2e2")));
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7cacf9")));
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(false);
    }

    public ProgressDialog getDialog() {
        return dialog;
    }

    public void setDialog(ProgressDialog dialog) {
        this.dialog = dialog;
    }
}
