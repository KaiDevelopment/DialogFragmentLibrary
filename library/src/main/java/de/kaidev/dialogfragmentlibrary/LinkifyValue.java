package de.kaidev.dialogfragmentlibrary;

import android.support.annotation.IntDef;
import android.text.util.Linkify;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Kai on 17.10.2015.
 */
@IntDef(flag = true, value = {
        Linkify.EMAIL_ADDRESSES,
        Linkify.MAP_ADDRESSES,
        Linkify.PHONE_NUMBERS,
        Linkify.WEB_URLS
})
@Retention(RetentionPolicy.SOURCE)
public @interface LinkifyValue {}
