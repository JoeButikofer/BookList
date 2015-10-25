package mobop.booklist.app.menu;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import mobop.booklist.app.BookListFragment;
import mobop.booklist.app.R;

public class MenuAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;

    public MenuAdapter(Context _context) {
        this.context = _context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layoutItem;

        if (convertView == null) {
            layoutItem = inflater.inflate(R.layout.drawer_item_row, parent, false);
        } else {
            layoutItem = convertView;
        }

        ImageView image = (ImageView) layoutItem.findViewById(R.id.drawer_item_row_image);
        TextView text = (TextView) layoutItem.findViewById(R.id.drawer_item_row_text);

        text.setText(getTextForPosition(position));
        image.setImageResource(getDrawableForPosition(position));

        return layoutItem;
    }

    public int getTextForPosition(int position) {
        switch (position) {
            case 0:
                return R.string.list_wish;
            case 1:
                return R.string.list_library;
            case 2:
                return R.string.list_to_read;
            case 3:
                return R.string.list_favorites;
            default:
                return 0;
        }
    }

    public int getDrawableForPosition(int position) {
        return android.R.drawable.ic_menu_compass;
    }
    public Fragment getFragmentForPosition(int position) {
        return new BookListFragment();
    }
}
