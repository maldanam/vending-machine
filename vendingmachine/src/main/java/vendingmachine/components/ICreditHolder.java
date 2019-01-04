package vendingmachine.components;

import java.util.List;

import vendingmachine.model.coins.Coin;

public interface ICreditHolder {

	/**
	 * Gets the current credit amount.
	 * 
	 * @return the amount of credit provided by the machine user
	 */
	double getAmount();
	
	/**
	 * Add a coin to the current credit amount
	 * 
	 * @param aCoin the coin to be added
	 */
	void add(Coin aCoin);
	
	/**
	 * Empties the credit store
	 * 
	 * @return all the coins currently stored
	 */
	List<Coin> empty();
	
}
