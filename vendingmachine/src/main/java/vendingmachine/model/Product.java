package vendingmachine.model;

public abstract class Product {

	private ProductKind kind;
	private double price;
	
	public Product(ProductKind kind, double price) {
		this.kind = kind;
		this.price = price;
	}

	public ProductKind getKind() {
		return kind;
	}

	public double getPrice() {
		return price;
	}
	
}
