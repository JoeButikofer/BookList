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
import mobop.booklist.app.adapter.BookAdapter;
import mobop.booklist.app.data.generic.IBook;
import mobop.booklist.app.data.generic.IManager;
import mobop.booklist.app.task.ParseJSONTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SearchManager //implements IManager<IBook>
{

    private final static String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private List<IBook> listBook;
    private Context context;
    private RequestQueue queue;

    public SearchManager(Context context)
    {
        this.context = context;

        // Instantiate the RequestQueue.
        this.queue = Volley.newRequestQueue(context);
        this.listBook = new LinkedList<>();
    }

    /*
    @Override
    public List<IBook> list() {
        return listBook;
    }
    */

   // @Override
    public BookAdapter search(String text) {

        listBook.clear();
        final BookAdapter bookAdapter = new BookAdapter(context, listBook); //TODO voir si le final ne fout pas la merde

        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, API_URL+text, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                ObjectMapper mapper = new ObjectMapper();

                try {
                    //all the books returned
                    JSONArray volumes = response.getJSONArray("items");

                    for(int i=0;i<volumes.length();i++)
                    {
                        //parse and add each book in the list
                        new ParseJSONTask(listBook, bookAdapter).execute(((JSONObject) volumes.get(i)).toString());
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

        queue.add(jsonRequest);

        return bookAdapter;
    }

   /* @Override
    public Book add(IBook item) {
        //TODO throw exception
        return null;
    }

    @Override
    public void update(IBook item) {
        //TODO throw exception
    }*/
}
