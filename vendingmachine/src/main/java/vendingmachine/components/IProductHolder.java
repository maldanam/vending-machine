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
	 * @param productKind
	 * @return
	 */
	double getPrice(ProductKind productKind);
	
	/**
	 * Returns the current stock available of a product kind.
	 * 
	 * @param productKind
	 * @return
	 */
	int availableStock(ProductKind productKind);
	
	/**
	 * Sell one product unit of a kind to the client.
	 * 
	 * @param productKind
	 * @throws InsufficientStockException
	 */
	Product sell(ProductKind productKind) throws InsufficientStockException;
	
	/**
	 * Add some products to the stock when the supplier refills the machine.
	 * 
	 * @param productKind
	 * @param quantity
	 */
	void add(ProductKind productKind, int quantity);
	
}
