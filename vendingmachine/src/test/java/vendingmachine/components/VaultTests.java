package vendingmachine.components;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import vendingmachine.model.coins.FiftyCents;
import vendingmachine.model.coins.OneEuro;
import vendingmachine.model.coins.TwoEuro;

public class VaultTests {

	private static IVault vault;
		
	@BeforeClass
	public static void setUp() throws Exception {
		vault = ComponentFactory.createVault();
	}

	@Test
	public void testAddingACoin() {

		double amountBefore = vault.getAmount();
		
		vault.add(new TwoEuro());
		
		double amountAfter = vault.getAmount();
		
		assertTrue(String.format("Vault current amount is incorrect (%s).", amountAfter), amountAfter == amountBefore+2);
	}

	@Test
	public void testAddingSomeCoins() {

		double amountBefore = vault.getAmount();
		
		vault.add(Arrays.asList(new TwoEuro(), new OneEuro(), new FiftyCents() ));
		
		double amountAfter = vault.getAmount();
		
		assertTrue(String.format("Vault current amount is incorrect (%s).", amountAfter), amountAfter == amountBefore+3.5);
	}

}
