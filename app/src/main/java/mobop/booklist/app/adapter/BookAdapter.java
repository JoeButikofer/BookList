package mobop.booklist.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import mobop.booklist.app.R;
import mobop.booklist.app.data.generic.IAdatper;
import mobop.booklist.app.data.generic.IBook;
import mobop.booklist.app.task.LoadImageTask;

import java.util.List;

//voir http://mickael-lt.developpez.com/tutoriels/android/personnaliser-listview/
public class BookAdapter extends BaseAdapter implements IAdatper<IBook>
{
    private final List<IBook> listBook;
    private final Context context;
    private final LayoutInflater inflater;
    private final String name;

    public BookAdapter(Context context, List<IBook> listBook, String name)
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
    public IBook getItem(int position) {
        return listBook.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GridLayout layoutItem;

        if(convertView == null)
        {
            layoutItem = (GridLayout) inflater.inflate(R.layout.view_book, parent, false);
        }
        else
        {
            layoutItem = (GridLayout)convertView;
        }

        //Get the fields
        ImageView bookImage = (ImageView) layoutItem.findViewById(R.id.book_image);
        TextView bookName = (TextView) layoutItem.findViewById(R.id.book_name);
        TextView bookGenre = (TextView) layoutItem.findViewById(R.id.book_genre);
        TextView bookPages = (TextView) layoutItem.findViewById(R.id.book_pages);
        TextView bookRatings = (TextView) layoutItem.findViewById(R.id.book_ratings);

        //Fills it

        // Load image with Picasso : http://square.github.io/picasso/
        Picasso.with(context).load(listBook.get(position).getImagePath()).into(bookImage);

        bookName.setText(listBook.get(position).getName());
        bookGenre.setText(listBook.get(position).getGenre());
        bookPages.setText(listBook.get(position).getPages()+ " pages");
        bookRatings.setText(""+listBook.get(position).getRatings());

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
