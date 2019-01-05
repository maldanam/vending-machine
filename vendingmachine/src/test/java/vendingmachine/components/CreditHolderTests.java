package vendingmachine.components;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import vendingmachine.model.coins.FiftyCents;
import vendingmachine.model.coins.FiveCents;
import vendingmachine.model.coins.OneEuro;
import vendingmachine.model.coins.TenCents;
import vendingmachine.model.coins.TwentyCents;
import vendingmachine.model.coins.TwoEuro;

public class CreditHolderTests {

	private static ICreditHolder holder;
	
	@BeforeClass
	public static void setUp() throws Exception {
		holder = ComponentFactory.createCreditHolder();
		holder.empty();
	}

	@Test
	public void testStoresCreditCorrectly() {
		holder.add(new TwoEuro());
		holder.add(new OneEuro());
		holder.add(new FiftyCents());
		holder.add(new TwentyCents());
		holder.add(new TenCents());
		holder.add(new FiveCents());
		
		assertTrue("Credit has not been stored correctly.", holder.getAmount() == 3.85);
	}

	@Test
	public void testEmptiesCreditCorrectly() {
		holder.add(new TwoEuro());
		holder.add(new OneEuro());
		holder.add(new FiftyCents());
		holder.add(new TwentyCents());
		
		holder.empty();
		
		assertTrue("Credit has not been stored correctly.", holder.getAmount() == 0);
	}

}
