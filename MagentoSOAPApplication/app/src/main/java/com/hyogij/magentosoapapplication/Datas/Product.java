package com.hyogij.magentosoapapplication.datas;

import org.ksoap2.serialization.SoapObject;

/**
 * A class for Product data.
 */
public class Product {
    private String name = null;
    private String product_id = null;
    private String type = null;
    private String price = null;
    private String image = null;
    private String file = null;

    public Product(SoapObject property) {
        product_id = property.getProperty("product_id").toString();
        name = property.getProperty("name").toString();
        type = property.getProperty("type").toString();
        price = "";
        image = "";
        file = "";
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getType() {
        return type;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", product_id='" + product_id + '\'' +
                ", price='" + price + '\'' +
                ", type='" + type + '\'' +
                ", file='" + file + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
