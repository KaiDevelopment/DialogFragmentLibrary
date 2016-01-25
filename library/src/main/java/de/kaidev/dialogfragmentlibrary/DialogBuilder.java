package de.kaidev.dialogfragmentlibrary;

import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ArrayRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

/**
 * Created by Kai on 07.10.2015.
 */
public class DialogBuilder implements Parcelable{

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
    String negativeButton;
    @StringRes
    int negativeButtonId;
    String neutralButton;
    @StringRes
    int neutralButtonId;
    String positiveButton;
    @StringRes
    int positiveButtonId;
    ContentViewMode contentViewMode;
    @DrawableRes
    int iconId;
    String title;
    @StringRes
    int titleId;
    String message;
    @StringRes
    int messageId;
    int linkify;
    @LayoutRes
    int viewId;
    boolean cancelable;
    CharSequence[] items;
    @ArrayRes
    int itemsId;
    boolean[] checkedItems;
    int checkedItem;
    ChoiceMode choiceMode;

    public DialogBuilder() {
    }
    protected DialogBuilder(Parcel in) {
        negativeButton = in.readString();
        negativeButtonId = in.readInt();
        neutralButton = in.readString();
        neutralButtonId = in.readInt();
        positiveButton = in.readString();
        positiveButtonId = in.readInt();
        iconId = in.readInt();
        title = in.readString();
        titleId = in.readInt();
        message = in.readString();
        messageId = in.readInt();
        linkify = in.readInt();
        viewId = in.readInt();
        cancelable = in.readByte() != 0;
        itemsId = in.readInt();
        checkedItems = in.createBooleanArray();
        checkedItem = in.readInt();
        contentViewMode = (ContentViewMode) in.readSerializable();
        choiceMode = (ChoiceMode) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(negativeButton);
        dest.writeInt(negativeButtonId);
        dest.writeString(neutralButton);
        dest.writeInt(neutralButtonId);
        dest.writeString(positiveButton);
        dest.writeInt(positiveButtonId);
        dest.writeInt(iconId);
        dest.writeString(title);
        dest.writeInt(titleId);
        dest.writeString(message);
        dest.writeInt(messageId);
        dest.writeInt(linkify);
        dest.writeInt(viewId);
        dest.writeByte((byte) (cancelable ? 1 : 0));
        dest.writeInt(itemsId);
        dest.writeBooleanArray(checkedItems);
        dest.writeInt(checkedItem);
        dest.writeSerializable(contentViewMode);
        dest.writeSerializable(choiceMode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public AlertDialog build(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(idOrValue(context, titleId, title));

        String negativeButtonText = idOrValue(context, negativeButtonId, negativeButton);
        if (negativeButtonText != null)
            builder.setNegativeButton(negativeButtonText, null);
        String positiveButtonText = idOrValue(context, positiveButtonId, positiveButton);
        if (positiveButtonText != null)
            builder.setPositiveButton(positiveButtonText, null);
        String neutralButtoText = idOrValue(context, neutralButtonId, neutralButton);
        if (neutralButtoText != null)
            builder.setNeutralButton(neutralButtoText, null);


        builder.setIcon(iconId);

        builder.setCancelable(cancelable);

        switch (contentViewMode){
            case MESSAGE:
                final TextView tx1=new TextView(context, null, R.style.AlertDialog_AppCompat);
                tx1.setText(idOrValue(context, messageId, message));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tx1.setTextAppearance(R.style.TextAppearance_AppCompat_Subhead);
                } else {
                    tx1.setTextAppearance(context,R.style.TextAppearance_AppCompat_Subhead);
                }
                if (linkify != 0) tx1.setAutoLinkMask(linkify);
                tx1.setMovementMethod(LinkMovementMethod.getInstance());
                float scale = context.getResources().getDisplayMetrics().density;
                builder.setView(tx1, (int) (24 * scale), (int) (18 * scale), (int) (24 * scale), 0);
                break;
            case CUSTOM_VIEW:
                builder.setView(viewId);
                break;
            case LIST:
                switch (choiceMode){
                    case NONE:
                        builder.setItems(idOrValue(context, itemsId, items), null);
                        break;
                    case SINGLE:
                        builder.setSingleChoiceItems(idOrValue(context, itemsId, items), checkedItem, null);
                        break;
                    case MULTI:
                        builder.setMultiChoiceItems(idOrValue(context, itemsId, items), checkedItems, null);
                        break;
                }
        }

        return builder.create();
    }

    private String idOrValue(Context c, @StringRes int id, String value){
        if (id != 0) return c.getString(id);
        else if (value != null) return value;
        else return null;
    }

    private CharSequence[] idOrValue(Context c, @ArrayRes int id, CharSequence[] value){
        if (id != 0) return c.getResources().getTextArray(id);
        else if (value != null) return value;
        else return null;
    }

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

    public DialogBuilder setNegativeButton(@StringRes int negativeButtonId) {
        this.negativeButtonId = negativeButtonId;
        return this;
    }

    public DialogBuilder setNeutralButton(@StringRes int neutralButtonId) {
        this.neutralButtonId = neutralButtonId;
        return this;
    }

    public DialogBuilder setPositiveButton(@StringRes int positiveButtonId) {
        this.positiveButtonId = positiveButtonId;
        return this;
    }

    public DialogBuilder setIcon(@DrawableRes int icon){
        this.iconId = icon;
        return this;
    }

    public DialogBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public DialogBuilder setTitle(@StringRes int titleId) {
        this.titleId = titleId;
        return this;
    }

    public DialogBuilder setMessage(String message) {
        contentViewMode = ContentViewMode.MESSAGE;
        this.message = message;
        return this;
    }

    public DialogBuilder setMessage(@StringRes int messageId) {
        contentViewMode = ContentViewMode.MESSAGE;
        this.messageId = messageId;
        return this;
    }

    public DialogBuilder setLinkify(@LinkifyValue int linkify) {
        this.linkify = linkify;
        return this;
    }

    public DialogBuilder setView(@LayoutRes int viewId) {
        contentViewMode = ContentViewMode.CUSTOM_VIEW;
        this.viewId = viewId;
        return this;
    }

    public DialogBuilder setCancelable(boolean cancelable){
        this.cancelable = cancelable;
        return this;
    }

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