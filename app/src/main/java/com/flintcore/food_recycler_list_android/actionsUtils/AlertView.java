package com.flintcore.food_recycler_list_android.actionsUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

public abstract class AlertView {

    @FunctionalInterface
    public interface CallOnClick {
        void doAction();
    }

    public static AlertDialog getDialog(Context context, @LayoutRes int view,
                                        @IdRes int idText, @StringRes int message,
                                        CallOnClick positiveCall, CallOnClick negativeCall){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        View viewLayout = LayoutInflater.from(context).inflate(view,
                null, false);

        ((TextView) viewLayout.findViewById(idText)).setText(message);

        dialog.setView(viewLayout);

        dialog.setPositiveButton("Si", (dialogInterface, i) -> {
            positiveCall.doAction();
            dialogInterface.dismiss();
        });
        dialog.setNegativeButton("No", (dialogInterface, i) -> {
            negativeCall.doAction();
            dialogInterface.dismiss();
        });

        return dialog.create();
    }
}
