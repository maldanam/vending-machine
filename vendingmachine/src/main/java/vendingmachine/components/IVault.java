package vendingmachine.components;

import java.util.List;

import vendingmachine.model.coins.Coin;

/**
 * Interface to access the vending machine vault.
 * 
 * @author marceloaldanamato
 *
 */
public interface IVault {

	/**
	 * Adds a coin to the vault.
	 * 
	 * @param aCoin
	 */
	void add(Coin aCoin);
	
	/**
	 * Adds some coins to the vault.
	 * 
	 * @param coins
	 */
	void add(List<Coin> coins);
	
	/**
	 * Gets the total amount in the vault.
	 * 
	 * @return
	 */
	double getAmount();
}
