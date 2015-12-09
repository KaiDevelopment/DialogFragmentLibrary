package de.kaidev.dialogfragmentlibrary;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by Kai on 05.07.2015.
 */
public class BuilderDialogFragment extends AlertDialogFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    static public BuilderDialogFragment newInstance(DialogBuilder builder){
        Bundle args = new Bundle();
        args.putParcelable(BUILDER, builder);

        BuilderDialogFragment dialog = new BuilderDialogFragment();
        dialog.setArguments(args);
        return dialog;
    }

    static public BuilderDialogFragment newInstance(DialogBuilder builder, Bundle data){
        Bundle args = new Bundle();
        args.putBundle("data", data);
        args.putParcelable(BUILDER, builder);

        BuilderDialogFragment dialog = new BuilderDialogFragment();
        dialog.setArguments(args);
        return dialog;
    }

    private ButtonListener buttonListener;
    private ListItemListener listItemListener;

    @Override
    public void saveCallback(Object activity) {
        super.saveCallback(activity);
        if (activity instanceof ButtonListener)
            buttonListener = (ButtonListener) activity;
        if (activity instanceof ListItemListener)
            listItemListener = (ListItemListener) activity;
    }

    @Override
    public void registerListener(final AlertDialog dialog) {
        super.registerListener(dialog);
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(this);
        dialog.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(this);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(this);
        final ListView listView = dialog.getListView();
        if (listView != null){
            listView.setOnItemClickListener(this);
        }
    }

    @Override
    public AlertDialog createDialog() {
        Parcelable parcelable = getArguments().getParcelable("builder");
        DialogBuilder builder = (DialogBuilder) parcelable;
        return builder.build(getContext());
    }

    @Override
    public void onClick(View v) {
        dismissAllowingStateLoss();
        if (buttonListener != null)
            buttonListener.onButtonClick(getTagFromArguments(), getButtonType(v), getDialog(), getArguments().getBundle("data"));
    }

    private int getButtonType(View button){
        for (int i : new int[]{DialogInterface.BUTTON_POSITIVE, DialogInterface.BUTTON_NEUTRAL, DialogInterface.BUTTON_NEGATIVE}){
            if (button == getDialog().getButton(i))
                return i;
        }
        return 0;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (getDialog().getListView().getChoiceMode() == AbsListView.CHOICE_MODE_NONE)
            dismiss();
        if (listItemListener != null)
            listItemListener.onItemClick(getTagFromArguments(), position, (String) parent.getAdapter().getItem(position), getDialog(), getArguments().getBundle("data"));
    }

    public interface ButtonListener{
        void onButtonClick(String tag, int buttonType, AlertDialog dialog, Bundle arguments);
    }

    public interface ListItemListener{
        void onItemClick(String tag, int position, String item, AlertDialog dialog, Bundle arguments);
    }
}