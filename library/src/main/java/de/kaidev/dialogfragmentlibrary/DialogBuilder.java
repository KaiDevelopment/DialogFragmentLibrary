package de.kaidev.dialogfragmentlibrary;

import android.app.Dialog;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ArrayRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

/**
 * Created by Kai on 07.10.2015.
 */
public class DialogBuilder implements Parcelable {
    protected DialogBuilder(Parcel in) {
    }

    public DialogBuilder(){}

    public DialogBuilder(Context c){
        context = c;
    }

    public static final Creator<DialogBuilder> CREATOR = new Creator<DialogBuilder>() {
        @Override
        public DialogBuilder createFromParcel(Parcel in) {
            return new DialogBuilder(in);
        }

        @Override
        public DialogBuilder[] newArray(int size) {
            return new DialogBuilder[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    Context context;

    public Dialog build(Context context){
        return new AlertDialog.Builder(context)
                .setItems()
                .create();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setNegativeButton(String negativeButton) {
        this.negativeButton = negativeButton;
    }

    public void setNeutralButton(String neutralButton) {
        this.neutralButton = neutralButton;
    }

    public void setPositiveButton(String positiveButton) {
        this.positiveButton = positiveButton;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setNegativeButtonId(@StringRes int negativeButtonId) {
        this.negativeButtonId = negativeButtonId;
    }

    public void setNeutralButtonId(@StringRes int neutralButtonId) {
        this.neutralButtonId = neutralButtonId;
    }

    public void setPositiveButtonId(@StringRes int positiveButtonId) {
        this.positiveButtonId = positiveButtonId;
    }

    public void setIcon(@DrawableRes int icon){
        this.iconId = icon;
    }

    public void setTitleId(@StringRes int titleId) {
        this.titleId = titleId;
    }

    public void setMessageId(@StringRes int messageId) {
        this.messageId = messageId;
    }

    public void setViewId(@IdRes int viewId) {
        this.viewId = viewId;
    }

    public void setCancelable(boolean cancelable){
        this.cancelable = cancelable;
    }

    public void setItems()

    String negativeButton;
    @StringRes int negativeButtonId;
    String neutralButton;
    @StringRes int neutralButtonId;
    String positiveButton;
    @StringRes int positiveButtonId;

    @DrawableRes int iconId;
    String title;
    @StringRes int titleId;
    String message;
    @StringRes int messageId;

    CharSequence[] items;
    @ArrayRes int itemsId;
    boolean[] checkedItems;
    int checkedItem;
    ChoiceMode choiceMode;

    @IdRes int viewId;

    boolean cancelable;
}
