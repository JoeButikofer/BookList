package mobop.booklist.app;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.support.v7.widget.Toolbar;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mobop.booklist.app.adapter.BookAdapter;
import mobop.booklist.app.data.database.Book;
import mobop.booklist.app.data.database.BookManager;
import mobop.booklist.app.data.generic.IBook;
import mobop.booklist.app.task.ParseJSONTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import mobop.booklist.app.data.generic.IManager;


public class BookListActivity extends Activity {


    private List<IBook> listBook;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private RequestQueue queue;
    private BookAdapter bookAdapter;

    public final static String EXTRA_BOOK = "mobop.booklist.app.BOOK";
    private final static String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        //Navigation Drawer
        setupNavigationDrawer();

        listBook = new ArrayList<IBook>();

        //TODO REMOVE THIS
        //TEST
        IBook testBook = new Book();
        testBook.setName("A really good book");
        testBook.setGenre("action");
        testBook.setPages(42);
        testBook.setRatings(12);

        listBook.add(testBook);
        //TEST

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);

        bookAdapter = new BookAdapter(this, listBook);

        IManager<IBook> databaseBookManager = new BookManager(this);
        databaseBookManager.add(testBook);
        testBook.setPages(2);
        databaseBookManager.update(testBook);
        listBook = databaseBookManager.list();

        //BookAdapter adapter = new BookAdapter(this, listBook);

        ListView listViewBook = (ListView) findViewById(R.id.list_books);
        listViewBook.setAdapter(bookAdapter);
        listViewBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IBook selectedBook = listBook.get(position);
                Intent intent = new Intent(BookListActivity.this, BookDetailsActivity.class);
                intent.putExtra(EXTRA_BOOK, selectedBook);
                startActivity(intent);
            }
        });
        listViewBook.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                IBook selectedBook = listBook.get(position);

                //TODO Long click : add/remove from lists
                return true; //true if the callback consumed the long click, false otherwise
            }
        });
    }

    private void setupNavigationDrawer() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(R.string.title_activity_book_list);
                invalidateOptionsMenu();
                syncState();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(R.string.navigation_drawer_title);
                invalidateOptionsMenu();
                syncState();
            }
        };

        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(drawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        String[] listArray = {getResources().getString(R.string.list_wish),getResources().getString(R.string.list_library),getResources().getString(R.string.list_to_read),getResources().getString(R.string.list_favorites) };
        ListAdapter drawerListAdapter = new ArrayAdapter<String>(this, R.layout.view_list_drawer, listArray);
        ListView drawerListView = (ListView) findViewById(R.id.left_drawer);
        drawerListView.setAdapter(drawerListAdapter);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // This to ensure the navigation icon is displayed as
        // burger instead of arrow.
        // Call syncState() from your Activity's onPostCreate
        // to synchronize the indicator with the state of the
        // linked DrawerLayout after onRestoreInstanceState
        // has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // This method should always be called by your Activity's
        // onConfigurationChanged method.
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_list, menu);

        //Search view listener setup
        SearchView searchView = (SearchView) ((MenuItem )menu.findItem(R.id.action_search)).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(getApplicationContext(),
                        "search...", Toast.LENGTH_SHORT).show();

                JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, API_URL+query, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO parser la reponse et peupler la liste de livre

                        listBook.clear();

                        //ObjectMapper mapper = new ObjectMapper();

                        try {
                            //all the books returned
                            JSONArray volumes = response.getJSONArray("items");

                            for(int i=0;i<volumes.length();i++)
                            {
                                //parse and add each book in the list
                               // mobop.booklist.app.data.api.Book book = mapper.readValue(((JSONObject) volumes.get(i)).toString(), mobop.booklist.app.data.api.Book.class);
                               // listBook.add(book);
                                new ParseJSONTask(listBook, bookAdapter).execute(((JSONObject) volumes.get(i)).toString());

                            }

                            //Update the list view
                           //bookAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO voir quoi faire
                        Toast.makeText(getApplicationContext(),
                                "error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(jsonRequest);

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

        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        // This handle among other things open & close the drawer
        // when the navigation icon(burger/arrow) is clicked on.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle other action bar items...
        switch (item.getItemId()) {
            case R.id.action_settings:

                return true;
            case R.id.action_scan:
                //TODO QR code scan
                IntentIntegrator integrator = new IntentIntegrator(this);

                integrator.initiateScan();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            scanResult.getFormatName();
            Log.d("QRCODE", "'" + scanResult.getContents() + "' '" + scanResult.toString()+ "'");
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
