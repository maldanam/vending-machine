package vendingmachine.components;

import java.util.ArrayList;
import java.util.List;

import vendingmachine.model.coins.Coin;

/**
 * Machine vault implementation.
 * 
 * @author marceloaldanamato
 *
 */
public class Vault implements IVault {

	private List<Coin> coins;
	private double amount;
	
	Vault() {
		this.coins = new ArrayList<>();
	}
	
	public void add(Coin aCoin) {
		this.coins.add(aCoin);
		amount += aCoin.getAmount();
	}

	public void add(List<Coin> coins) {
		for (Coin aCoin : coins) {
			this.coins.add(aCoin);
			amount += aCoin.getAmount();			
		}
	}

	public double getAmount() {
		return this.amount;
	}

}
