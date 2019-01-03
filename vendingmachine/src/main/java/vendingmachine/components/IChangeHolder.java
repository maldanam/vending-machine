package vendingmachine.components;

import java.util.List;

import vendingmachine.exceptions.InsufficientChangeException;
import vendingmachine.model.coins.Coin;

public interface IChangeHolder {

	/**
	 * Gets the requested amount if available.
	 * 
	 * @param amount requested amount to be returned.
	 * @return some coins that sum the requested amount.
	 * @throws InsufficientChangeException if there are insufficient coins 
	 * 		   to provide the amount requested.
	 */
	List<Coin> get(double amount) throws InsufficientChangeException;
	
	/** 
	 * Provide coins to be available for change return.
	 * 
	 * @param someCoins coins provided for change return.
	 */
	void add(List<Coin> someCoins);
	
	/**
	 * Empties all change available.
	 * 
	 */
	void empty();
}
