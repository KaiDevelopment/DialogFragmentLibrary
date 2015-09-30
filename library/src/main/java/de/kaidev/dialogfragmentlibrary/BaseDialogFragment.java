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

    AlertDialog dialog;

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
        Builder builder = getBuilder();
        dialog = builder.create();
        dialog.setOnShowListener(dialog1 -> {
            registerListener((AlertDialog) dialog1);
            ((AlertDialog) dialog1).getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(positive);
            ((AlertDialog) dialog1).getButton(DialogInterface.BUTTON_NEUTRAL).setEnabled(neutral);
            ((AlertDialog) dialog1).getButton(DialogInterface.BUTTON_NEGATIVE).setEnabled(negative);
        });
        return dialog;
    }

    @Override
    public AlertDialog getDialog() {
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

    public void setButtonEnabled(int which, boolean enabled){
        AlertDialog dialog = getDialog();
        if (dialog != null){
            Button button = dialog.getButton(which);
            if (button != null){
                button.setEnabled(enabled);
                return;
            }
        }
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                positive = enabled;
                break;
            case DialogInterface.BUTTON_NEUTRAL:
                neutral = enabled;
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                negative = enabled;
                break;
        }
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

    public abstract Builder getBuilder();

    static public class Builder extends AlertDialog.Builder implements Serializable{
        public Builder(Context context) {
            super(context);
        }

        public Builder(Context context, int theme) {
            super(context, theme);
        }

        @Override
        @Deprecated
        public Builder setNegativeButton(CharSequence text, DialogInterface.OnClickListener listener) {
            super.setNegativeButton(text, null);
            return this;
        }

        public Builder setNegativeButton(CharSequence text) {
            super.setNegativeButton(text, null);
            return this;
        }

        @Deprecated
        @Override
        public Builder setNeutralButton(CharSequence text, DialogInterface.OnClickListener listener) {
            super.setNeutralButton(text, null);
            return this;
        }

        public Builder setNeutralButton(CharSequence text) {
            super.setNeutralButton(text, null);
            return this;
        }

        @Deprecated
        @Override
        public Builder setPositiveButton(CharSequence text, DialogInterface.OnClickListener listener) {
            super.setPositiveButton(text, null);
            return this;
        }

        public Builder setPositiveButton(CharSequence text) {
            super.setPositiveButton(text, null);
            return this;
        }

        @Override
        public Builder setTitle(CharSequence title) {
            super.setTitle(title);
            return this;
        }

        @Override
        public Builder setMessage(CharSequence message) {
            super.setMessage(message);
            return this;
        }

        @Deprecated
        @Override
        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            return this;
        }

        @Deprecated
        @Override
        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            return this;
        }

        @Deprecated
        @Override
        public Builder setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
            return this;
        }

        @Override
        public Builder setItems(CharSequence[] items, DialogInterface.OnClickListener listener) {
            super.setItems(items, null);
            return this;
        }

        @Override
        public Builder setItems(int itemsId, DialogInterface.OnClickListener listener) {
            super.setItems(itemsId, null);
            return this;
        }

        @Override
        public Builder setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems, DialogInterface.OnMultiChoiceClickListener listener) {
            super.setMultiChoiceItems(items, checkedItems, null);
            return this;
        }

        @Override
        public Builder setSingleChoiceItems(CharSequence[] items, int checkedItem, DialogInterface.OnClickListener listener) {
            super.setSingleChoiceItems(items, checkedItem, listener);
            return this;
        }

        @Override
        public Builder setCancelable(boolean cancelable) {
            super.setCancelable(cancelable);
            return this;
        }

        @Override
        public Builder setView(View view) {
            super.setView(view);
            return this;
        }

        @Override
        public Builder setView(int layoutResId) {
            super.setView(layoutResId);
            return this;
        }
    }

    public interface CancelListener{
        void onCancel(String tag, AlertDialog dialog, Bundle args);
    }

    public interface DismissListener{
        void onDismiss(String tag, AlertDialog dialog, Bundle args);
    }
}