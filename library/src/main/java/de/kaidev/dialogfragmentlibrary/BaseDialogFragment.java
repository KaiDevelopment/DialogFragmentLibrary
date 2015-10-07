package de.kaidev.dialogfragmentlibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import java.io.Serializable;

/**
 * Created by Kai on 04.07.2015.
 */
public abstract class BaseDialogFragment extends DialogFragment {
    public static final String BUILDER = "builder";

    private CancelListener cancelListener;
    private DismissListener dismissListener;

    Dialog dialog;

    boolean positive = true;
    boolean neutral = true;
    boolean negative = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getView();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        System.out.println("BaseDialogFragment.onCreateDialog");
        dialog = createDialog();
        dialog.setOnShowListener(dialog1 -> {
            registerListener((AlertDialog) dialog1);
            ((AlertDialog) dialog1).getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(positive);
            ((AlertDialog) dialog1).getButton(DialogInterface.BUTTON_NEUTRAL).setEnabled(neutral);
            ((AlertDialog) dialog1).getButton(DialogInterface.BUTTON_NEGATIVE).setEnabled(negative);
        });
        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        saveCallback(activity);
    }

    public void registerListener(AlertDialog dialog) {
        dialog.setOnDismissListener(this);
        dialog.setOnCancelListener(this);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        getArguments().putString("tag", tag);
    }

    public String getTagFromArguments(){
        return getArguments().getString("tag");
    }

    public void saveCallback(Activity activity){
        if (activity instanceof DismissListener)
            dismissListener = (DismissListener) activity;
        if (activity instanceof CancelListener)
            cancelListener = (CancelListener) activity;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null)
            dismissListener.onDismiss(getTagFromArguments(), getDialog(), getArguments().getBundle("data"));
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (cancelListener != null)
            cancelListener.onCancel(getTagFromArguments(), getDialog(), getArguments().getBundle("data"));
    }

    public abstract Dialog createDialog();

    public interface CancelListener{
        void onCancel(String tag, Dialog dialog, Bundle args);
    }

    public interface DismissListener{
        void onDismiss(String tag, Dialog dialog, Bundle args);
    }
}