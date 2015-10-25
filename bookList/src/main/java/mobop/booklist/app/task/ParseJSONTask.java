package mobop.booklist.app.task;

import android.os.AsyncTask;
import com.fasterxml.jackson.databind.ObjectMapper;
import mobop.booklist.app.adapter.BookAdapter;
import mobop.booklist.app.data.generic.IBook;

import java.io.IOException;
import java.util.List;

public class ParseJSONTask extends AsyncTask<String, Void, Boolean> {

    ObjectMapper mapper;
    List<IBook> listBook;
    BookAdapter bookAdapter;

    public ParseJSONTask(List<IBook> listBook, BookAdapter bookAdapter)
    {
        this.mapper = new ObjectMapper();
        this.listBook = listBook;
        this.bookAdapter = bookAdapter;
    }

    @Override
    protected Boolean doInBackground(String... params) {

        try {
            mobop.booklist.app.data.api.Book book = mapper.readValue(params[0], mobop.booklist.app.data.api.Book.class);
            listBook.add(book);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {

        if(result)
        {
            bookAdapter.notifyDataSetChanged();
        }
    }
}
