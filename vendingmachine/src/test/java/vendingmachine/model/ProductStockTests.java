package vendingmachine.model;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import vendingmachine.exceptions.InsufficientStockException;

public class ProductStockTests {

	private static ProductStock stock;
	
	@BeforeClass
	public static void setUp() throws Exception {
		stock = new ProductStock(new Coke(), 0);
	}

	@Before
	public void reset() throws Exception {
		stock.empty();;
	}

	@Test
	public void testStockIncrement() {
		stock.increment(1);
		stock.increment(1);
		stock.increment(4);
		
		assertTrue("Stock has not been incremented correctly.", stock.getQuantity() == 6);
	}

	@Test
	public void testStockDecrement() throws InsufficientStockException {
		stock.increment(1);
		stock.increment(1);
		
		stock.decrement(2);
		
		assertTrue("Stock has not been decremented correctly.", stock.getQuantity() == 0);
	}
	
	@Test
	public void testEmptyStock() throws InsufficientStockException {
		stock.increment(1);
		stock.increment(1);
		
		stock.empty();
		
		assertTrue("Stock has not been emptied correctly.", stock.getQuantity() == 0);
	}
	
	@Test
	public void testInsufficientStock() {
		
		try {
			stock.decrement(1);
		} catch (InsufficientStockException e) {
			assertTrue("Stock has been decremented when it was insufficient.", stock.getQuantity() == 0);
		}
	}	

}
