package mypackage;

import java.util.Arrays;

public class Admin {
    private int id;
    private String name;
    private Double price;
    private byte[] imagepath;
    private String category;

    public Admin(int id, String name, Double price, byte[] imagepath, String category) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.imagepath = imagepath;
		this.category = category;
	}

	@Override
	public String toString() {
		return "Admin [id=" + id + ", name=" + name + ", price=" + price + ", imagepath=" + Arrays.toString(imagepath)
				+ ", category=" + category + "]";
	}

	// Getters and Setters for all fields including category
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public byte[] getImagepath() {
        return imagepath;
    }

    public void setImagepath(byte[] imagepath) {
        this.imagepath = imagepath;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
