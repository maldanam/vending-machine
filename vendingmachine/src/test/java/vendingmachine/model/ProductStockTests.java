package vendingmachine.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import vendingmachine.exceptions.InsufficientStockException;

public class ProductStockTests {

	private ProductStock stock;
	
	@Before
	public void setUp() throws Exception {
		this.stock = new ProductStock(new Coke(), 0);
	}

	@Test
	public void testStockIncrement() {
		this.stock.increment(1);
		this.stock.increment(1);
		this.stock.increment(4);
		
		assertTrue("Stock has not been incremented correctly.", this.stock.getQuantity() == 6);
	}

	@Test
	public void testStockDecrement() throws InsufficientStockException {
		this.stock.increment(1);
		this.stock.increment(1);
		
		this.stock.decrement(2);
		
		assertTrue("Stock has not been decremented correctly.", this.stock.getQuantity() == 0);
	}
	
	@Test
	public void testInsufficientStock() {
		
		try {
			this.stock.decrement(1);
		} catch (InsufficientStockException e) {
			assertTrue("Stock has been decremented when it was insufficient.", this.stock.getQuantity() == 0);
		}
	}	

}
