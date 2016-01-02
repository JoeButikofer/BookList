package mobop.booklist.app.data.api;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import mobop.booklist.app.Queue;
import mobop.booklist.app.adapter.BookAdapter;
import mobop.booklist.app.data.generic.IAdatper;
import mobop.booklist.app.data.generic.IApiSearchManager;
import mobop.booklist.app.data.generic.book.IApiBook;
import mobop.booklist.app.task.ParseJSONTask;
import mobop.booklist.app.tools.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

public class SearchManager implements IApiSearchManager<IApiBook> {

    private final static String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private final BookAdapter mBookAdapter;
    private final List<IApiBook> listBook;
    private final Context context;
    private String language;
    private String query;

    private static final String AUTHOR_SEARCH = "inauthor:";
    private static final String ISBN_SEARCH = "isbn:";
    private static final String TITLE_SEARCH = "intitle:";

    public SearchManager(Context context) {
        this.context = context;
        this.language = PreferenceManager.getDefaultSharedPreferences(context).getString("search_language", "en"); //Retrieve the saved language or set the default language to english

        // Instantiate
        listBook = new LinkedList<>();
        mBookAdapter = new BookAdapter(context, listBook, "SearchManager");
    }

    public void filterTitle(String text)
    {
        filter(TITLE_SEARCH + text);
    }

    public void filterAuthor(String text)
    {
        filter(AUTHOR_SEARCH + text);
    }

    public void filterIsbn(String text)
    {
        filter(ISBN_SEARCH + text);
    }


    @Override
    public void filter(String text) {
        this.query = text;
        reload();
    }

    @Override
    public void reload() {
        if (query == null) {
            throw new IllegalStateException("Filters must no be null !");
        }
        listBook.clear();

        String encodedText = "";
        try {
            encodedText = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, API_URL + encodedText + "&country=" +  Country.getUserCountry(context)+ "&langRestrict=" + language, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //all the books returned
                    JSONArray volumes = response.getJSONArray("items");

                    for (int i = 0; i < volumes.length(); i++) {
                        //parse and add each book in the list
                        new ParseJSONTask(listBook, mBookAdapter).execute((volumes.get(i)).toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //nothing
            }
        });

        Queue.getInstance(context).add(jsonRequest);
        mBookAdapter.notifyDataSetChanged();
    }

    @Override
    public IAdatper<IApiBook> adapter() {
        return mBookAdapter;
    }

    @Override
    public void clearFilter() {
        // nothing
    }

    public String getLanguage() {return this.language;}
    public void setLanguage(String languageCode)
    {
        this.language = languageCode;
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("search_language", languageCode).apply(); //Save this language
    }
}
