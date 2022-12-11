package projetshop.ma;

import java.util.ArrayList;

public class Product {
    String id;
    String id_categoriy;
    ArrayList<String>photos;
    String size;
    String label;
    int quantity;
    String unit_price;
    String description;
    String date;
    int nbr_rating;
    int promotion;

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public int getNbr_rating() {
        return nbr_rating;
    }

    public int getPromotion() {
        return promotion;
    }

    public String getId_categoriy() {
        return id_categoriy;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public String getSize() {
        return size;
    }
    public String getFirstPhoto(){
        return photos.get(0);
    }

    public Product(String id, String id_categoriy, ArrayList<String> photos, String size, String label, String unit_price) {
        this.id = id;
        this.id_categoriy = id_categoriy;
        this.photos = photos;
        this.size = size;
        this.label = label;

        this.unit_price = unit_price;

    }
}
