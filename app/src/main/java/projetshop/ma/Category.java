package projetshop.ma;

public class Category {
    private int id;

    public int getId() {
        return id;
    }

    private String path;

    public String getPath() {
        return path;
    }

    private String title;

    public String getTitle() {
        return title;
    }


    public Category(String title, String path , int id) {
        this.title = title;
        this.path = path;
        this.id = id;
    }
}

