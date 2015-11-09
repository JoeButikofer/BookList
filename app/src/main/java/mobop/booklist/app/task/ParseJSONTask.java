package mobop.booklist.app.task;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

import mobop.booklist.app.adapter.BookAdapter;
import mobop.booklist.app.data.api.Book;
import mobop.booklist.app.data.generic.IBook;

import java.io.IOException;
import java.util.List;

public class ParseJSONTask extends AsyncTask<String, Void, Boolean> {

    private final ObjectMapper mapper;
    private final List<IBook> mListBook;
    private final BookAdapter mBookAdapter;

    public ParseJSONTask(List<IBook> listBook, BookAdapter bookAdapter) {
        this.mapper = new ObjectMapper();
        this.mListBook = listBook;
        this.mBookAdapter = bookAdapter;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            Book book = mapper.readValue(params[0], Book.class);
            mListBook.add(book);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            mBookAdapter.notifyDataSetChanged();
        }
    }
}
