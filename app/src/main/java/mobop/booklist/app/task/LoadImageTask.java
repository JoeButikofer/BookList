package mobop.booklist.app.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import mobop.booklist.app.tools.BitmapTools;

public class LoadImageTask extends AsyncTask<String, Void, Bitmap>
{
    private int reqWidth;
    private int reqHeight;
    private ImageView imageView;

    public LoadImageTask(ImageView imageView, int reqWidth, int reqHeight)
    {
        this.imageView = imageView;
        this.reqWidth = reqWidth;
        this.reqHeight = reqHeight;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        return BitmapTools.decodeSampledBitmap(params[0], reqWidth, reqHeight);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
