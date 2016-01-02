package mobop.booklist.app.task;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import mobop.booklist.app.adapter.BookAdapter;
import mobop.booklist.app.data.api.ApiBook;
import mobop.booklist.app.data.generic.book.IApiBook;

import java.io.IOException;
import java.util.List;

public class ParseJSONTask extends AsyncTask<String, Void, ApiBook> {

    private final ObjectMapper mapper;
    private final List<IApiBook> mListBook;
    private final BookAdapter mBookAdapter;

    public ParseJSONTask(List<IApiBook> listBook, BookAdapter bookAdapter) {
        this.mapper = new ObjectMapper();
        this.mListBook = listBook;
        this.mBookAdapter = bookAdapter;
    }

    @Override
    protected ApiBook doInBackground(String... params) {
        try {
            ApiBook book = mapper.readValue(params[0], ApiBook.class);
            Log.d("Api", book.toString());
            return book;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ApiBook result) {
        if (result != null) {
            mListBook.add(result);
            mBookAdapter.notifyDataSetChanged();
        }
    }
}
