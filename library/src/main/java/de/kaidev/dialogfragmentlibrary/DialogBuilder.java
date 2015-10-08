package de.kaidev.dialogfragmentlibrary;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

/**
 * Created by Kai on 07.10.2015.
 */
public class DialogBuilder{

    public AlertDialog build(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (title != null) builder.setTitle(title);
        if (titleId != 0) builder.setTitle(titleId);

        if (negativeButton != null) builder.setNegativeButton(negativeButton, null);
        if (negativeButtonId != 0) builder.setNegativeButton(negativeButtonId, null);
        if (neutralButton != null) builder.setNeutralButton(neutralButton, null);
        if (neutralButtonId != 0) builder.setNeutralButton(neutralButtonId, null);
        if (positiveButton != null) builder.setPositiveButton(positiveButton, null);
        if (positiveButtonId != 0) builder.setPositiveButton(positiveButtonId, null);

        builder.setIcon(iconId);

        builder.setCancelable(cancelable);

        switch (contentViewMode){
            case MESSAGE:
                if (message != null) builder.setMessage(message);
                if (messageId != 0) builder.setMessage(messageId);
                break;
            case CUSTOM_VIEW:
                builder.setView(viewId);
                break;
            case LIST:
                switch (choiceMode){
                    case NONE:
                        if (items != null) builder.setItems(items, null);
                        if (itemsId != 0) builder.setItems(itemsId, null);
                        break;
                    case SINGLE:
                        if (items != null) builder.setSingleChoiceItems(items, checkedItem, null);
                        if (itemsId != 0) builder.setSingleChoiceItems(itemsId, checkedItem, null);
                        break;
                    case MULTI:
                        if (items != null) builder.setMultiChoiceItems(items, checkedItems, null);
                        if (itemsId != 0) builder.setMultiChoiceItems(itemsId, checkedItems, null);
                        break;
                }
        }

        return builder.create();
    }

    private String s(@StringRes int id, Context c){
        return c.getString(id);
    }

    String negativeButton;
    @StringRes int negativeButtonId;
    String neutralButton;
    @StringRes int neutralButtonId;
    String positiveButton;
    @StringRes int positiveButtonId;

    public DialogBuilder setNegativeButton(String negativeButton) {
        this.negativeButton = negativeButton;
        return this;
    }

    public DialogBuilder setNeutralButton(String neutralButton) {
        this.neutralButton = neutralButton;
        return this;
    }

    public DialogBuilder setPositiveButton(String positiveButton) {
        this.positiveButton = positiveButton;
        return this;
    }

    public DialogBuilder setNegativeButtonId(@StringRes int negativeButtonId) {
        this.negativeButtonId = negativeButtonId;
        return this;
    }

    public DialogBuilder setNeutralButtonId(@StringRes int neutralButtonId) {
        this.neutralButtonId = neutralButtonId;
        return this;
    }

    public DialogBuilder setPositiveButtonId(@StringRes int positiveButtonId) {
        this.positiveButtonId = positiveButtonId;
        return this;
    }

    ContentViewMode contentViewMode;

    @DrawableRes int iconId;
    String title;
    @StringRes int titleId;
    String message;
    @StringRes int messageId;
    @IdRes int viewId;
    boolean cancelable;


    public DialogBuilder setIcon(@DrawableRes int icon){
        this.iconId = icon;
        return this;
    }

    public DialogBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public DialogBuilder setTitleId(@StringRes int titleId) {
        this.titleId = titleId;
        return this;
    }

    public DialogBuilder setMessage(String message) {
        contentViewMode = ContentViewMode.MESSAGE;
        this.message = message;
        return this;
    }

    public DialogBuilder setMessageId(@StringRes int messageId) {
        contentViewMode = ContentViewMode.MESSAGE;
        this.messageId = messageId;
        return this;
    }

    public DialogBuilder setViewId(@IdRes int viewId) {
        contentViewMode = ContentViewMode.CUSTOM_VIEW;
        this.viewId = viewId;
        return this;
    }

    public DialogBuilder setCancelable(boolean cancelable){
        this.cancelable = cancelable;
        return this;
    }

    CharSequence[] items;
    @ArrayRes int itemsId;
    boolean[] checkedItems;
    int checkedItem;
    ChoiceMode choiceMode;


    public DialogBuilder setItems(CharSequence[] items){
        choiceMode = ChoiceMode.NONE;
        contentViewMode = ContentViewMode.LIST;
        this.items = items;
        return this;
    }

    public DialogBuilder setItems(@ArrayRes int itemsId){
        choiceMode = ChoiceMode.NONE;
        contentViewMode = ContentViewMode.LIST;
        this.itemsId = itemsId;
        return this;
    }

    public DialogBuilder setMultiChoiceItems(int itemsId, boolean[] checkedItems){
        choiceMode = ChoiceMode.MULTI;
        contentViewMode = ContentViewMode.LIST;
        this.itemsId = itemsId;
        this.checkedItems = checkedItems;
        return this;
    }

    public DialogBuilder setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems) {
        choiceMode = ChoiceMode.MULTI;
        contentViewMode = ContentViewMode.LIST;
        this.items = items;
        this.checkedItems = checkedItems;
        return this;
    }

    public DialogBuilder setSingleChoiceItems(int itemsId, int checkedItem) {
        choiceMode = ChoiceMode.SINGLE;
        contentViewMode = ContentViewMode.LIST;
        this.itemsId = itemsId;
        this.checkedItem = checkedItem;
        return this;
    }

    public DialogBuilder setSingleChoiceItems(CharSequence[] items, int checkedItem) {
        choiceMode = ChoiceMode.SINGLE;
        contentViewMode = ContentViewMode.LIST;
        this.items = items;
        this.checkedItem = checkedItem;
        return this;
    }
}
