package com.codeforlite.virdlerim.Popup_Classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.codeforlite.virdlerim.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AlertView_SetAyetGroupTitle extends AlertDialog.Builder {

    private EditText editText_Title;
    private FloatingActionButton button_setTitle;
    private AlertDialog alertDialog;
    private View dialogView;


    public AlertView_SetAyetGroupTitle(Context context) {
        super(context);

        dialogView = ((Activity) context).getLayoutInflater().inflate(R.layout.alertview_set_ayetgrouptitle, null);
        setView(dialogView);
        alertDialog = create();

        editText_Title = dialogView.findViewById(R.id.editText_title);
        button_setTitle = dialogView.findViewById(R.id.button_setTitle);

        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.coloredborder_7);

        alertDialog.show();
    }

    public void setEditText_Title(EditText editText_Title) {
        this.editText_Title = editText_Title;
    }

    public void setButton_setTitle(FloatingActionButton button_setTitle) {
        this.button_setTitle = button_setTitle;
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }

    public void setAlertDialog(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }

    public View getDialogView() {
        return dialogView;
    }

    public void setDialogView(View dialogView) {
        this.dialogView = dialogView;
    }

    public EditText getEditText_Title() {
        return editText_Title;
    }

    public FloatingActionButton getButton_setTitle() {
        return button_setTitle;
    }


}
