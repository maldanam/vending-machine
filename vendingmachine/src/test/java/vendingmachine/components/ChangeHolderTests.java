package vendingmachine.components;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import vendingmachine.exceptions.InsufficientChangeException;
import vendingmachine.model.coins.Coin;
import vendingmachine.model.coins.FiftyCents;
import vendingmachine.model.coins.OneEuro;
import vendingmachine.model.coins.TenCents;
import vendingmachine.model.coins.TwentyCents;
import vendingmachine.model.coins.TwoEuro;

public class ChangeHolderTests {

	private IChangeHolder change;
	
	@Before
	public void setUp() throws Exception {
		this.change = ChangeHolder.getInstance();
		this.change.empty();
	}

	@Test
	public void testGetSomeChangeIfEmpty() {
		try {
			this.change.get(1);
			fail("The expected exception was not thrown.");
		} catch (InsufficientChangeException e) {
		}
	}

	@Test
	public void testGetSomeChangeIfInsufficientChange() {
		try {
			this.change.add(Arrays.asList(new FiftyCents(), new TwentyCents()));
			this.change.get(1);
			fail("The expected exception was not thrown.");
		} catch (InsufficientChangeException e) {
		}
	}

	@Test
	public void testGetChangeAvailableButNotReturnable() {
		try {
			this.change.add(Arrays.asList(new OneEuro(), new OneEuro(), new TwentyCents(), new TwentyCents(), new TenCents()));
			this.change.get(0.80);
			fail("The expected exception was not thrown.");
		} catch (InsufficientChangeException e) {
		}
	}

	@Test
	public void testGetTwoEuroAddedPreviously() {
		try {
			this.change.add(Arrays.asList(new TwoEuro()));
			this.change.get(2);
			fail("The expected exception was not thrown.");
		} catch (InsufficientChangeException e) {
		}
	}

	@Test
	public void testGetChangeCorrectly() throws InsufficientChangeException {
		this.change.add(Arrays.asList(new FiftyCents(), new FiftyCents(), new TwentyCents(), new TwentyCents(), new TenCents()));
		double requestedAmount = 0.80;
		List<Coin> returnedChange = this.change.get(requestedAmount);
		double returnedAmount = 0;
		for (Coin coin : returnedChange) {
			returnedAmount += coin.getAmount();
		}
		assertTrue("The returned change amount is different from the requested amount.", requestedAmount-returnedAmount < 0.05);
	}

	@Test
	public void testGetAllChangeCorrectly() throws InsufficientChangeException {
		this.change.add(Arrays.asList(new FiftyCents(), new FiftyCents(), new TwentyCents(), new TwentyCents(), new TenCents()));
		double requestedAmount = 1.50;
		List<Coin> returnedChange = this.change.get(requestedAmount);
		double returnedAmount = 0;
		for (Coin coin : returnedChange) {
			returnedAmount += coin.getAmount();
		}
		assertTrue("The returned change amount is different from the requested amount.", requestedAmount-returnedAmount < 0.05);
	}

}
