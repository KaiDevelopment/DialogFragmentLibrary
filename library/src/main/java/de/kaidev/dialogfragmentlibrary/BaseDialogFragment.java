package de.kaidev.dialogfragmentlibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kai on 04.07.2015.
 */
public abstract class BaseDialogFragment extends DialogFragment {
    public static final String BUILDER = "builder";
    Dialog dialog;
    boolean positive = true;
    boolean neutral = true;
    boolean negative = true;
    private CancelListener cancelListener;
    private DismissListener dismissListener;

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
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog1) {
                BaseDialogFragment.this.registerListener((AlertDialog) dialog1);
                ((AlertDialog) dialog1).getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(positive);
                ((AlertDialog) dialog1).getButton(DialogInterface.BUTTON_NEUTRAL).setEnabled(neutral);
                ((AlertDialog) dialog1).getButton(DialogInterface.BUTTON_NEGATIVE).setEnabled(negative);
            }
        });
        return dialog;
    }

    @Override
    public Dialog getDialog() {
        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        saveCallback(getTargetFragment() == null ? activity : getTargetFragment());
    }

    public void registerListener(AlertDialog dialog) {
        dialog.setOnDismissListener(this);
        dialog.setOnCancelListener(this);
    }

    public void show(FragmentManager manager){
        show(manager, "default");
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        if (getArguments() == null) setArguments(new Bundle());
        getArguments().putString("tag", tag);
    }

    public void show(Fragment manager){
        show(manager, "default");
    }

    public void show(Fragment f, String tag){
        setTargetFragment(f, 0);
        show(f.getFragmentManager(), tag);
    }

    public String getTagFromArguments(){
        return getArguments().getString("tag");
    }

    public void saveCallback(Object object) {
        if (object instanceof DismissListener)
            dismissListener = (DismissListener) object;
        if (object instanceof CancelListener)
            cancelListener = (CancelListener) object;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null)
            dismissListener.onDismiss(getTagFromArguments(), getDialog(), getArguments().getBundle("data"));
        dialog = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dialog = null;
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