package mobop.booklist.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import mobop.booklist.app.R;
import mobop.booklist.app.data.generic.IAdapter;
import mobop.booklist.app.data.generic.book.IApiBook;

import java.util.List;

//voir http://mickael-lt.developpez.com/tutoriels/android/personnaliser-listview/
public class BookAdapter extends BaseAdapter implements IAdapter<IApiBook>
{
    private final List<IApiBook> listBook;
    private final Context context;
    private final LayoutInflater inflater;
    private final String name;

    public BookAdapter(Context context, List<IApiBook> listBook, String name)
    {
        this.context = context;
        this.listBook = listBook;
        this.inflater = LayoutInflater.from(context);
        this.name = name;
    }

    @Override
    public int getCount() {
        return listBook.size();
    }

    @Override
    public IApiBook getItem(int position) {
        return listBook.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RelativeLayout layoutItem;

        if(convertView == null) {
            layoutItem = (RelativeLayout) inflater.inflate(R.layout.view_book, parent, false);
        } else {
            layoutItem = (RelativeLayout) convertView;
        }

        //Get the fields
        ImageView bookImage = (ImageView) layoutItem.findViewById(R.id.book_image);
        TextView bookName = (TextView) layoutItem.findViewById(R.id.book_name);
        TextView bookGenre = (TextView) layoutItem.findViewById(R.id.book_genre);
        TextView bookPages = (TextView) layoutItem.findViewById(R.id.book_pages);
        RatingBar bookRatings = (RatingBar) layoutItem.findViewById(R.id.book_ratings);

        //Fills it
        IApiBook book = listBook.get(position);

        // Load image with Picasso : http://square.github.io/picasso/
        Picasso.with(context).load(book.getImagePath()).into(bookImage);

        bookName.setText(book.getName());
        bookName.setSelected(true); //For the auto scroll
        bookGenre.setText(book.getGenre());
        bookGenre.setSelected(true); //For the auto scroll

        int pages = book.getPages();
        if(pages > 0) {
            bookPages.setText(String.format(context.getString(R.string.book_pages), pages));
        }

        double rating = book.getRatings();
        if(rating > 0) {
            bookRatings.setRating((float) rating);
        }

        return layoutItem;
    }

    @Override
    public String toString() {
        return "BookAdapter{" +
                "name='" + name + '\'' +
                ", super=" + super.toString() +
                '}';
    }
}
