package mobop.booklist.app.data.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import mobop.booklist.app.Queue;
import mobop.booklist.app.adapter.BookAdapter;
import mobop.booklist.app.data.generic.IAdatper;
import mobop.booklist.app.data.generic.IBook;
import mobop.booklist.app.data.generic.ISearchManager;
import mobop.booklist.app.task.ParseJSONTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class SearchManager implements ISearchManager<IBook> {

    private final static String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private final BookAdapter mBookAdapter;
    private final List<IBook> listBook;
    private final Context context;

    private static final String AUTHOR_SEARCH = "+inauthor";
    private static final String ISBN_SEARCH = "+isbn";
    private static final String TITLE_SEARCH = "+intitle";

    public SearchManager(Context context) {
        this.context = context;

        // Instantiate
        listBook = new LinkedList<>();
        mBookAdapter = new BookAdapter(context, listBook, "SearchManager");
    }

    public void filterTitle(String text)
    {
        filter(text + TITLE_SEARCH);
    }

    public void filterAuthor(String text)
    {
        filter(text + AUTHOR_SEARCH);
    }

    public void filterIsbn(String text)
    {
        filter(text + ISBN_SEARCH);
    }


    @Override
    public void filter(String text) {

        listBook.clear();

        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, API_URL + text, new Response.Listener<JSONObject>() {
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
    public IAdatper<IBook> adapter() {
        return mBookAdapter;
    }

    @Override
    public void clearFilter() {
        // TODO
    }
}
