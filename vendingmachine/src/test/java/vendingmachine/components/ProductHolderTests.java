package vendingmachine.components;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import vendingmachine.exceptions.InsufficientStockException;
import vendingmachine.model.ProductKind;

public class ProductHolderTests {
	
	private IProductHolder holder;

	@Before
	public void setUp() throws Exception {
		this.holder = ProductHolder.getInstance();
	}

	@Test
	public void testSellProductWithNoStockAvailable() {
		ProductKind productKind = ProductKind.COKE;
		int stockBefore = this.holder.availableStock(productKind);
		try {
			this.holder.sell(productKind);
		} catch (InsufficientStockException e) {
			//Checks the stock correctly, ensure it has not been modified.
			int stockAfter = this.holder.availableStock(productKind);			
			assertTrue("Product stock has been modified.", stockBefore == stockAfter);
		}
	}

	@Test
	public void testSellProductWithAvailableStock() throws InsufficientStockException {
		ProductKind productKind = ProductKind.COKE;
		this.holder.add(productKind, 1);
		int stockBefore = this.holder.availableStock(productKind);
		
		this.holder.sell(productKind);
		
		int stockAfter = this.holder.availableStock(productKind);			
		assertTrue("Product stock has been modified incorrectly.", stockBefore == stockAfter+1);
	}

	@Test
	public void testIncreaseAndEmptyProductStock() {
		this.holder.add(ProductKind.COKE, 10);
		assertTrue("Product %s has been stored incorrectly.", this.holder.availableStock(ProductKind.COKE) == 10);
		
		this.holder.empty();;
		assertTrue("Product %s has been emptied incorrectly.", this.holder.availableStock(ProductKind.COKE) == 0);
	}

}
