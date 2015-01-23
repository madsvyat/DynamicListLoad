package com.madsvyat.dynamiclisttest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class ItemsContentProvider extends ContentProvider {

    public static final String QUERY_PARAMETER_LIMIT = "limit";
    public static final String QUERY_PARAMETER_OFFSET = "offset";
    private static final UriMatcher URI_MATCHER;
    private static final int CODE_ITEMS = 1;
    private DBOpenHelper openHelper;
    
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(DBData.AUTHORITY, "items", CODE_ITEMS);
    }

    @Override
    public boolean onCreate() {
        openHelper = new DBOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (URI_MATCHER.match(uri)) {
            case CODE_ITEMS:
                queryBuilder.setTables(DBData.Items.TABLE_NAME);
                break;
            default:
                throw new IllegalArgumentException("Illegal Uri: " + uri);
        }

        SQLiteDatabase db = openHelper.getReadableDatabase();

        String limit = uri.getQueryParameter(QUERY_PARAMETER_LIMIT);
        String offset = uri.getQueryParameter(QUERY_PARAMETER_OFFSET);

        String limitString = null;
        if (limit != null && offset != null) {
            limitString = offset + ',' + limit;
        }
        
        return queryBuilder.query(db, projection, selection, selectionArgs, null, null, 
                sortOrder, limitString);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
