package vendingmachine.components;

import java.util.HashMap;
import java.util.Map;

import vendingmachine.exceptions.InsufficientStockException;
import vendingmachine.model.Coke;
import vendingmachine.model.Product;
import vendingmachine.model.ProductKind;
import vendingmachine.model.ProductStock;
import vendingmachine.model.Sprite;
import vendingmachine.model.Water;

/**
 * A Product Holder implementation.
 * 
 * @author marceloaldanamato
 *
 */
public class ProductHolder implements IProductHolder {

	private Map<ProductKind, ProductStock> stock = new HashMap<>();
	
	ProductHolder() {
		this.stock.put(ProductKind.COKE, new ProductStock(new Coke(), 0));
		this.stock.put(ProductKind.SPRITE, new ProductStock(new Sprite(), 0));
		this.stock.put(ProductKind.WATER, new ProductStock(new Water(), 0));
	}
	
	public double getPrice(ProductKind productKind) {
		ProductStock productStock = this.stock.get(productKind);
		return productStock.getProduct().getPrice();
	}

	public int availableStock(ProductKind productKind) {
		ProductStock productStock = this.stock.get(productKind);
		return productStock.getQuantity();
	}

	public Product sell(ProductKind productKind) throws InsufficientStockException {
		ProductStock productStock = this.stock.get(productKind);
		productStock.decrement(1);
		return productStock.getProduct();
	}

	public void add(ProductKind productKind, int quantity) {
		ProductStock productStock = this.stock.get(productKind);
		productStock.increment(quantity);
	}

	@Override
	public void empty() {
		this.stock.get(ProductKind.COKE).empty();
		this.stock.get(ProductKind.SPRITE).empty();
		this.stock.get(ProductKind.WATER).empty();
	}

}
