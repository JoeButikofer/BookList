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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BookMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RequestQueue queue;
    private final static String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    private Map<Integer, Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragments = new HashMap<>();
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        loadFragment(R.id.nav_wish);

        queue = Volley.newRequestQueue(this);
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

        MenuItem item = menu.findItem(R.id.action_search);
        View actionView = item.getActionView();
        SearchView sv = (SearchView) actionView;
        sv.setOnQueryTextListener(null);
        SearchView searchView = (SearchView) ((MenuItem )menu.findItem(R.id.action_search)).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(getApplicationContext(),
                        "search...", Toast.LENGTH_SHORT).show();

                JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, API_URL + query, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO parser la reponse et peupler la liste de livre

                        //listBook.clear();

                        //ObjectMapper mapper = new ObjectMapper();

                        try {
                            //all the books returned
                            JSONArray volumes = response.getJSONArray("items");

                            for (int i = 0; i < volumes.length(); i++) {
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

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_settings:

                return true;
            case R.id.action_scan:
                //TODO QR code scan
                IntentIntegrator integrator = new IntentIntegrator(this);

                integrator.initiateScan();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(int menu_id) {
        Fragment fragment;
        if (mFragments.containsKey(menu_id)) {
            fragment = mFragments.get(menu_id);
        } else {
            if (menu_id == R.id.nav_wish) {
                // Handle the camera action
            } else if (menu_id == R.id.nav_library) {

            } else if (menu_id == R.id.nav_to_read) {

            } else if (menu_id == R.id.nav_nav_favorites) {

            } else if (menu_id == R.id.nav_share) {

            } else if (menu_id == R.id.nav_send) {

            }
            Log.d("Book", "new fragment for " + menu_id);
            fragment = new BookListFragment();
            mFragments.put(menu_id, fragment);
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            scanResult.getFormatName();
            Log.d("QRCODE", "'" + scanResult.getContents() + "' '" + scanResult.toString() + "'");
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        loadFragment(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
