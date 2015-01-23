package com.madsvyat.dynamiclisttest;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by N1 on 23.01.2015.
 */
public class DBData {

    public static final String CONTENT = "content://";
    public static final String AUTHORITY = "com.madsvyat.provider.ItemsData";

    public static final Uri URI_ITEMS = Uri.parse(CONTENT + AUTHORITY + "/items");


    public static final class Items implements BaseColumns {
        public static final String TABLE_NAME = "items";
        public static final String NAME = "name";        
    }
}
