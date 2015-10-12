package mobop.booklist.app.data.database;

import mobop.booklist.app.data.generic.IBook;

public class Book implements IBook {
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String value) {
       name = value;
    }
}
