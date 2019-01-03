package vendingmachine.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import vendingmachine.model.coins.Coin;

/**
 * Machine vault implemented as a Singleton.
 * 
 * @author marceloaldanamato
 *
 */
public class Vault implements IVault {

	private static Optional<Vault> instance = Optional.empty();

	private List<Coin> coins;
	private double amount;
	
	private Vault() {
		this.coins = new ArrayList<Coin>();
	}
	
	public static IVault getInstance() {
		if (!instance.isPresent()) {
			instance = Optional.of(new Vault());
		}
		return instance.get();
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
