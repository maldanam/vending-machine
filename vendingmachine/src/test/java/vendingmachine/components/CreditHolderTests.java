package vendingmachine.components;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import vendingmachine.model.coins.FiftyCents;
import vendingmachine.model.coins.FiveCents;
import vendingmachine.model.coins.OneEuro;
import vendingmachine.model.coins.TenCents;
import vendingmachine.model.coins.TwentyCents;
import vendingmachine.model.coins.TwoEuro;

public class CreditHolderTests {

	private ICreditHolder credit;
	
	@Before
	public void setUp() throws Exception {
		this.credit = CreditHolder.getInstance();
		this.credit.empty();
	}

	@Test
	public void testStoresCreditCorrectly() {
		this.credit.add(new TwoEuro());
		this.credit.add(new OneEuro());
		this.credit.add(new FiftyCents());
		this.credit.add(new TwentyCents());
		this.credit.add(new TenCents());
		this.credit.add(new FiveCents());
		
		assertTrue("Credit has not been stored correctly.", this.credit.getAmount() == 3.85);
	}

	@Test
	public void testEmptiesCreditCorrectly() {
		this.credit.add(new TwoEuro());
		this.credit.add(new OneEuro());
		this.credit.add(new FiftyCents());
		this.credit.add(new TwentyCents());
		
		this.credit.empty();
		
		assertTrue("Credit has not been stored correctly.", this.credit.getAmount() == 0);
	}

}
