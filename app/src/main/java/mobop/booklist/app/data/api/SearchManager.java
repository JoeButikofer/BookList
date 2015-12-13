package mobop.booklist.app.data.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import mobop.booklist.app.Queue;
import mobop.booklist.app.adapter.BookAdapter;
import mobop.booklist.app.data.generic.IAdatper;
import mobop.booklist.app.data.generic.IApiSearchManager;
import mobop.booklist.app.data.generic.book.IApiBook;
import mobop.booklist.app.task.ParseJSONTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class SearchManager implements IApiSearchManager<IApiBook> {

    private final static String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private final BookAdapter mBookAdapter;
    private final List<IApiBook> listBook;
    private final Context context;
    private String language;

    private static final String AUTHOR_SEARCH = "inauthor:";
    private static final String ISBN_SEARCH = "isbn:";
    private static final String TITLE_SEARCH = "intitle:";

    public SearchManager(Context context) {
        this.context = context;
        this.language = "en"; //Set the default language to english //TODO save choice

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

        listBook.clear();

        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, API_URL + text + "&langRestrict=" + language, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                ObjectMapper mapper = new ObjectMapper();

                try {
                    //all the books returned
                    JSONArray volumes = response.getJSONArray("items");

                    for (int i = 0; i < volumes.length(); i++) {
                        //parse and add each book in the list
                        new ParseJSONTask(listBook, mBookAdapter).execute(((JSONObject) volumes.get(i)).toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO voir quoi faire
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
        // TODO
    }

    public String getLanguage() {return this.language;}
    public void setLanguage(String languageCode)
    {
        this.language = languageCode;
    }
}
