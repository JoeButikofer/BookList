package mobop.booklist.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
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

import mobop.booklist.app.data.api.SearchManager;
import mobop.booklist.app.data.database.BookManager;
import mobop.booklist.app.data.generic.book.IApiBook;
import mobop.booklist.app.data.generic.IPersistentManager;
import mobop.booklist.app.data.generic.ISearchManager;
import mobop.booklist.app.data.generic.book.IPersistentBookManager;

public class BookMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BookListFragment mBookListFragment;
    private ISearchManager<IApiBook> mApiSearch;
    private ISearchManager<IApiBook> mDatabaseSearch;
    private IPersistentBookManager mPersistentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Queue.getInstance(this);
        mBookListFragment = new BookListFragment();
        setContentView(R.layout.activity_book_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setFragment(mBookListFragment);
        loadMenu(R.id.nav_wish);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
                        "search...", Toast.LENGTH_SHORT).show();

                mApiSearch.filter(query);

                changeSerach(mApiSearch);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //TODO suggestions ???
                return false;
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
            case R.id.action_settings:

                return true;
            case R.id.action_scan:
                IntentIntegrator integrator = new IntentIntegrator(this);
                integrator.initiateScan(IntentIntegrator.PRODUCT_CODE_TYPES);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadMenu(int menu_id) {
        switch (menu_id) {
            case R.id.nav_wish:
                mDatabaseSearch.filter(BookManager.SEARCH_WISH);
                changeSerach(mDatabaseSearch);
                break;
            case R.id.nav_library:
                mDatabaseSearch.filter(BookManager.SEARCH_LIBRARY);
                changeSerach(mDatabaseSearch);
                break;
            case R.id.nav_to_read:
                mDatabaseSearch.filter(BookManager.SEARCH_TO_READ);
                changeSerach(mDatabaseSearch);
                break;
            case R.id.nav_nav_favorites:
                mDatabaseSearch.filter(BookManager.SEARCH_FAVORITES);
                changeSerach(mDatabaseSearch);
                break;
            case R.id.nav_share:

                break;
            case R.id.nav_send:

                break;
            default:
                throw new IllegalStateException("ID " + menu_id + " unknown !");
        }
    }

    private void changeSerach(ISearchManager<IApiBook> manager) {
        mBookListFragment.setManager(manager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            scanResult.getFormatName();
            mApiSearch.filterIsbn(scanResult.getContents());
            Log.d("QRCODE", "'" + scanResult.getContents() + "' '" + scanResult.toString() + "'");
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        loadMenu(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
