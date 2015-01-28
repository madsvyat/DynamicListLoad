package com.madsvyat.dynamiclisttest;

import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CursorAdapter;
import android.widget.ListView;


public class HomeActivity extends ActionBarActivity 
        implements LoaderManager.LoaderCallbacks<Cursor> {
    
    private static final int LOADER_ID = 1;
    private static final int PAGE_SIZE = 50;
    
    private ListView itemsListView;
    private View footerView;
    private CursorAdapter itemsAdapter;
    private int previousLast;
    private int limit  = PAGE_SIZE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        itemsListView = (ListView) findViewById(R.id.items_ListView);
        View emptyView = findViewById(R.id.empty_textView);
        itemsListView.setEmptyView(emptyView);

        footerView = getLayoutInflater().inflate(R.layout.footer_items_list, null);
        itemsListView.addFooterView(footerView);
        
        itemsAdapter = new ItemsCursorAdapter(this);
        itemsListView.setAdapter(itemsAdapter); 
        itemsListView.removeFooterView(footerView);
        
        
        itemsListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    if (previousLast != lastItem) { //to avoid multiple calls for last item
                        previousLast = lastItem;
                        limit += PAGE_SIZE;
                        if (itemsListView.getFooterViewsCount() == 0) {
                            itemsListView.addFooterView(footerView);
                        }
                        getSupportLoaderManager().restartLoader(LOADER_ID, null, HomeActivity.this);
                    }
                }
            }
        });
        
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        if (id == LOADER_ID) {
            Uri queryUri = DBData.URI_ITEMS.buildUpon()
                    .appendQueryParameter(ItemsContentProvider.QUERY_PARAMETER_LIMIT, String.valueOf(limit))
                    .appendQueryParameter(ItemsContentProvider.QUERY_PARAMETER_OFFSET, String.valueOf(0))
                    .build();
            return new CursorLoader(this, queryUri, null, null, null, null);
        } 
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if (cursorLoader.getId() == LOADER_ID) {
            itemsAdapter.swapCursor(cursor);
            itemsListView.removeFooterView(footerView);            
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        if (cursorLoader.getId() == LOADER_ID) {
            itemsAdapter.swapCursor(null);
        }
    }
}
