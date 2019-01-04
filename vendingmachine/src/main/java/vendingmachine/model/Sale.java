package vendingmachine.model;

import java.util.List;

import vendingmachine.model.coins.Coin;

public class Sale {
	
	private Product product;
	private List<Coin> change;

	public Sale(Product aProduct, List<Coin> change) {
		this.product = aProduct;
		this.change = change;
	}

	public Product getProduct() {
		return product;
	}

	public List<Coin> getChange() {
		return change;
	}

}
