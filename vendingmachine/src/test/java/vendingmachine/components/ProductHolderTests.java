package vendingmachine.components;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import vendingmachine.exceptions.InsufficientStockException;
import vendingmachine.model.ProductKind;

public class ProductHolderTests {
	
	private static IProductHolder holder;

	@BeforeClass
	public static void setUp() throws Exception {
		
		holder = ComponentFactory.createProductHolder();
	}

	@Test
	public void testSellProductWithNoStockAvailable() {
		ProductKind productKind = ProductKind.COKE;
		int stockBefore = holder.availableStock(productKind);
		try {
			holder.sell(productKind);
		} catch (InsufficientStockException e) {
			//Checks the stock correctly, ensure it has not been modified.
			int stockAfter = holder.availableStock(productKind);			
			assertTrue("Product stock has been modified.", stockBefore == stockAfter);
		}
	}

	@Test
	public void testSellProductWithAvailableStock() throws InsufficientStockException {
		ProductKind productKind = ProductKind.COKE;
		holder.add(productKind, 1);
		int stockBefore = holder.availableStock(productKind);
		
		holder.sell(productKind);
		
		int stockAfter = holder.availableStock(productKind);			
		assertTrue("Product stock has been modified incorrectly.", stockBefore == stockAfter+1);
	}

	@Test
	public void testIncreaseAndEmptyProductStock() {
		holder.add(ProductKind.COKE, 10);
		assertTrue("Product %s has been stored incorrectly.", holder.availableStock(ProductKind.COKE) == 10);
		
		holder.empty();;
		assertTrue("Product %s has been emptied incorrectly.", holder.availableStock(ProductKind.COKE) == 0);
	}

}
