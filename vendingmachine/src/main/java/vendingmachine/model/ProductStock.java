package vendingmachine.model;

import vendingmachine.exceptions.InsufficientStockException;

/**
 * Class to store some products of the same kind.
 * 
 * @author marceloaldanamato
 *
 */
public class ProductStock {

	private Product product;
	private int quantity;

	public ProductStock(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void increment(int quantity) {
		this.quantity += quantity;
	}
	
	public void decrement(int quantity) throws InsufficientStockException {
		if (this.quantity < quantity) {
			throw new InsufficientStockException(String.format("Current stock is less than %d units.", quantity));
		}
		this.quantity -= quantity;
	}
	
}
