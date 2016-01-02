package mobop.booklist.app.task;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

import mobop.booklist.app.data.api.ApiBook;
import mobop.booklist.app.data.api.SearchManager;

import java.io.IOException;

public class ParseJSONTask extends AsyncTask<String, Void, ApiBook> {

    private static final ObjectMapper mapper = new ObjectMapper();

    private final SearchManager searchManager;
    private final int queryId;

    public ParseJSONTask(SearchManager searchManager, int queryId) {
        this.searchManager = searchManager;
        this.queryId = queryId;
    }

    @Override
    protected ApiBook doInBackground(String... params) {
        try {
            ApiBook book = mapper.readValue(params[0], ApiBook.class);
            return book;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ApiBook result) {
        if (result != null) {
            searchManager.add(result, queryId);
        }
    }
}
