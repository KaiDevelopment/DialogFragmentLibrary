package de.kaidev.dialogfragmentlibrary;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

/**
 * Created by Kai on 07.10.2015.
 */
public abstract class AlertDialogFragment extends BaseDialogFragment {
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
    public AlertDialog getDialog() {
        return (AlertDialog) super.getDialog();
    }

    @Override
    public abstract AlertDialog createDialog();
}
