package vendingmachine.components;

import vendingmachine.exceptions.InsufficientStockException;
import vendingmachine.model.Product;
import vendingmachine.model.ProductKind;

/**
 * Available behavior to interact with our Product stock.
 * 
 * @author marceloaldanamato
 *
 */
public interface IProductHolder {
	
	/**
	 * Gets the unit price of a product kind.
	 * 
	 * @param productKind the product kind we want to know the price
	 * @return the price of a unit of the product kind
	 */
	double getPrice(ProductKind productKind);
	
	/**
	 * Returns the current stock available of a product kind.
	 * 
	 * @param productKind the product kind we want to know the available stock
	 * @return the stock currently available of the product kind
	 */
	int availableStock(ProductKind productKind);
	
	/**
	 * Sell one product unit of a kind to the client.
	 * 
	 * @param productKind the product kind we want to sell
	 * @throws InsufficientStockException thrown if there is insufficient stock available
	 */
	Product sell(ProductKind productKind) throws InsufficientStockException;
	
	/**
	 * Add some products to the stock when the supplier refills the machine.
	 * 
	 * @param productKind the product kind we want to increase the stock
	 * @param quantity the quantity of products to add to he stock
	 */
	void add(ProductKind productKind, int quantity);
	
}
