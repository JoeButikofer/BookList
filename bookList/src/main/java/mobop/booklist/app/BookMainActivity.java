package mobop.booklist.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import mobop.booklist.app.menu.MenuAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookMainActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView mDrawerListView;

    private final static String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private RequestQueue queue;
    private MenuAdapter mDrawerMenuAdapter;

    private Fragment[] mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_main);


        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);

        //Navigation Drawer
        setupNavigationDrawer();

        mFragments = new Fragment[mDrawerMenuAdapter.getCount()];

        selectItem(0);
    }

    private void setupNavigationDrawer() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
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
        mDrawerLayout.setDrawerListener(drawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);



        mDrawerMenuAdapter = new MenuAdapter(this);
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);
        mDrawerListView.setAdapter(mDrawerMenuAdapter);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectItem(i);
            }
        });

    }
    private void selectItem(int position) {
        Log.d("Book", "selectItem(" + position  + ")");
        if (mFragments[position] == null) {
            mFragments[position] = mDrawerMenuAdapter.getFragmentForPosition(position);
        }


        if (mFragments[position] != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, mFragments[position]).commit();

            mDrawerListView.setItemChecked(position, true);
            mDrawerListView.setSelection(position);
            getActionBar().setTitle(mDrawerMenuAdapter.getTextForPosition(position));
            mDrawerLayout.closeDrawer(mDrawerListView);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
        Log.d("Book", "end selectItem(" + position  + ")");
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

                        //listBook.clear();

                        //ObjectMapper mapper = new ObjectMapper();

                        try {
                            //all the books returned
                            JSONArray volumes = response.getJSONArray("items");

                            for(int i=0;i<volumes.length();i++)
                            {
                                //parse and add each book in the list
                                // mobop.booklist.app.data.api.Book book = mapper.readValue(((JSONObject) volumes.get(i)).toString(), mobop.booklist.app.data.api.Book.class);
                                // listBook.add(book);
                                //TODO remove comment
                                // new ParseJSONTask(listBook, bookAdapter).execute(((JSONObject) volumes.get(i)).toString());

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
