package mobop.booklist.app.data.generic.book;

import java.io.Serializable;
import java.util.List;

public interface IApiBook extends Serializable { //Parcelable would be more efficient but we don't need it for just one object

    String getId();
    void setId(String value);

    String getName();
    void setName(String value);

    String getGenre();
    void setGenre(String value);

    int getPages();
    void setPages(int value);

    double getRatings();
    void setRatings(double value);

    String getImagePath();
    void setImagePath(String value);

    List<String> getAuthors();
    void setAuthors(List<String> authors);

    String getDescription();
    void setDescription(String description);
}
