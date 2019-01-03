package vendingmachine.components;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import vendingmachine.model.coins.FiftyCents;
import vendingmachine.model.coins.OneEuro;
import vendingmachine.model.coins.TwoEuro;

public class VaultTests {

	private IVault vault;
		
	@Before
	public void setUp() throws Exception {
		this.vault = Vault.getInstance();
	}

	@Test
	public void testAddingACoin() {

		double amountBefore = this.vault.getAmount();
		
		this.vault.add(new TwoEuro());
		
		double amountAfter = this.vault.getAmount();
		
		assertTrue(String.format("Vault current amount is incorrect (%s).", amountAfter), amountAfter == amountBefore+2);
	}

	@Test
	public void testAddingSomeCoins() {

		double amountBefore = this.vault.getAmount();
		
		this.vault.add(Arrays.asList(new TwoEuro(), new OneEuro(), new FiftyCents() ));
		
		double amountAfter = this.vault.getAmount();
		
		assertTrue(String.format("Vault current amount is incorrect (%s).", amountAfter), amountAfter == amountBefore+3.5);
	}

}
