package mobop.booklist.app;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Stack;

import mobop.booklist.app.data.api.SearchManager;
import mobop.booklist.app.data.database.BookManager;
import mobop.booklist.app.data.generic.IApiSearchManager;
import mobop.booklist.app.data.generic.IPersistentSearchManager;
import mobop.booklist.app.data.generic.book.IApiBook;
import mobop.booklist.app.data.generic.ISearchManager;
import mobop.booklist.app.data.generic.book.IPersistentBookManager;

public class BookMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LanguageDialogFragment.LanguageDialogListener {

    private BookListFragment mBookListFragment;
    private IApiSearchManager<IApiBook> mApiSearch;
    private IPersistentSearchManager<IApiBook> mDatabaseSearch;
    private IPersistentBookManager mPersistentManager;

    private BeforeIsbnScanState mBeforeIsbnScanState = null;
    private NavigationView mNavigationView;
    private int mCurrentMenuId;

    private static class BeforeIsbnScanState {
        public CharSequence title;
        public int menuId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Queue.getInstance(this);
        mCurrentMenuId = R.id.nav_wish;
        mBookListFragment = new BookListFragment();
        setContentView(R.layout.activity_book_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mApiSearch = new SearchManager(this);
        {
            BookManager bookManager = new BookManager(this);
            mPersistentManager = bookManager;
            mDatabaseSearch = bookManager;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setCheckedItem(mCurrentMenuId);
        mNavigationView.setNavigationItemSelectedListener(this);

        setFragment(mBookListFragment);
        loadMenu(mCurrentMenuId);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mBeforeIsbnScanState != null) {
            setTitle(mBeforeIsbnScanState.title);
            mNavigationView.setCheckedItem(mBeforeIsbnScanState.menuId);
            loadMenu(mBeforeIsbnScanState.menuId);

            for (int i = 0 ; i < mNavigationView.getMenu().size() ; i++) {
                if (mNavigationView.getMenu().getItem(i).getItemId() == mBeforeIsbnScanState.menuId) {
                    mNavigationView.getMenu().getItem(i).setChecked(true);
                }
            }
            mBeforeIsbnScanState = null;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.book_main, menu);
        //Search view listener setup

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(getApplicationContext(),
                        R.string.search_starting, Toast.LENGTH_SHORT).show();

                mApiSearch.filter(query, false);

                changeSearch(mApiSearch);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {

            public ISearchManager<IApiBook> previousManager;

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                previousManager = mBookListFragment.getManager();
                return true; //true to allow open
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                changeSearch(previousManager);
                return true; //true to allow close
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_scan:
                IntentIntegrator integrator = new IntentIntegrator(this);
                integrator.initiateScan(IntentIntegrator.PRODUCT_CODE_TYPES);
                return true;

            case R.id.action_language:

                // Create an instance of the dialog fragment and show it
                DialogFragment dialog = new LanguageDialogFragment();
                dialog.show(this.getFragmentManager(), "LanguageDialogFragment");

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadMenu(int menu_id) {
        mCurrentMenuId = menu_id;
        switch (menu_id) {
            case R.id.nav_wish:
                mDatabaseSearch.filterWish();
                changeSearch(mDatabaseSearch);
                setTitle(R.string.list_wish);
                break;
            case R.id.nav_library:
                mDatabaseSearch.filterOwn();
                changeSearch(mDatabaseSearch);
                setTitle(R.string.list_library);
                break;
            case R.id.nav_to_read:
                mDatabaseSearch.filterToRead();
                changeSearch(mDatabaseSearch);
                setTitle(R.string.list_to_read);
                break;
            case R.id.nav_nav_favorites:
                mDatabaseSearch.filterFavorite();
                changeSearch(mDatabaseSearch);
                setTitle(R.string.list_favorites);
                break;
            default:
                throw new IllegalStateException("ID " + menu_id + " unknown !");
        }
    }

    private void changeSearch(ISearchManager<IApiBook> manager) {
        mBookListFragment.setManager(manager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            scanResult.getFormatName();
            mApiSearch.filterIsbn(scanResult.getContents());
            if (mBeforeIsbnScanState == null) {
                mBeforeIsbnScanState = new BeforeIsbnScanState();
                mBeforeIsbnScanState.title = getTitle();
                mBeforeIsbnScanState.menuId = mCurrentMenuId;

            }
            for (int i = 0 ; i < mNavigationView.getMenu().size() ; i++) {
                mNavigationView.getMenu().getItem(i).setChecked(false);
            }
            setTitle(scanResult.getContents());
            changeSearch(mApiSearch);
            Log.d("QRCODE", "'" + scanResult.getContents() + "' '" + scanResult.toString() + "'");
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        loadMenu(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLanguageItemClick(String languageCode) {
        mApiSearch.setLanguage(languageCode);
    }

    @Override
    public String getCurrentLanguage() {
        return mApiSearch.getLanguage();
    }
}
