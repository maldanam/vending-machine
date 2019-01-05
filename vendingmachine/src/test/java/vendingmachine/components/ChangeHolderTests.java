package vendingmachine.components;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static vendingmachine.utils.AmountUtils.calculateAmount;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import vendingmachine.exceptions.InsufficientChangeException;
import vendingmachine.model.coins.Coin;
import vendingmachine.model.coins.FiftyCents;
import vendingmachine.model.coins.OneEuro;
import vendingmachine.model.coins.TenCents;
import vendingmachine.model.coins.TwentyCents;
import vendingmachine.model.coins.TwoEuro;

public class ChangeHolderTests {

	private static IChangeHolder holder;
	
	@BeforeClass
	public static void setUp() throws Exception {
		holder = ComponentFactory.createChangeHolder();
	}

	@Before
	public void reset() throws Exception {
		holder.empty();
	}

	@Test
	public void testGetSomeChangeIfEmpty() {
		try {
			holder.get(1);
			fail("The expected exception was not thrown.");
		} catch (InsufficientChangeException e) {
		}
	}

	@Test
	public void testGetSomeChangeIfInsufficientChange() {
		try {
			holder.add(Arrays.asList(new FiftyCents(), new TwentyCents()));
			holder.get(1);
			fail("The expected exception was not thrown.");
		} catch (InsufficientChangeException e) {
		}
	}

	@Test
	public void testGetChangeAvailableButNotReturnable() {
		try {
			holder.add(Arrays.asList(new OneEuro(), new OneEuro(), new TwentyCents(), new TwentyCents(), new TenCents()));
			holder.get(0.80);
			fail("The expected exception was not thrown.");
		} catch (InsufficientChangeException e) {
		}
	}

	@Test
	public void testGetTwoEuroAddedPreviously() {
		try {
			holder.add(Arrays.asList(new TwoEuro()));
			holder.get(2);
			fail("The expected exception was not thrown.");
		} catch (InsufficientChangeException e) {
		}
	}

	@Test
	public void testGetChangeCorrectly() throws InsufficientChangeException {
		holder.add(Arrays.asList(new FiftyCents(), new FiftyCents(), new TwentyCents(), new TwentyCents(), new TenCents()));
		
		double requestedAmount = 0.80;
		List<Coin> returnedChange = this.holder.get(requestedAmount);
		BigDecimal returnedAmount = calculateAmount(returnedChange);

		assertTrue("The returned change amount is different from the requested amount.", 
				returnedAmount.compareTo(new BigDecimal(requestedAmount)) != 0);
	}

	@Test
	public void testGetAllChangeCorrectly() throws InsufficientChangeException {
		holder.add(Arrays.asList(new FiftyCents(), new FiftyCents(), new TwentyCents(), new TwentyCents(), new TenCents()));
		
		double requestedAmount = 1.50;
		List<Coin> returnedChange = holder.get(requestedAmount);
		BigDecimal returnedAmount = calculateAmount(returnedChange);

		assertTrue("The returned change amount is different from the requested amount.", 
				returnedAmount.compareTo(new BigDecimal(requestedAmount)) != 0);
	}

	@Test
	public void testLoadAndEmptyChange() throws InsufficientChangeException {
		holder.add(Arrays.asList(new FiftyCents(), new FiftyCents(), new TwentyCents(), new TwentyCents(), new TenCents()));		
		assertTrue("The change has not been loaded correctly.", this.holder.getAmount() == 1.50);

		holder.empty();
		assertTrue("The change has not been emptied correctly.", this.holder.getAmount() == 0);
	}

}
