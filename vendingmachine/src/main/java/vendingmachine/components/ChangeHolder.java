package vendingmachine.components;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import vendingmachine.exceptions.InsufficientChangeException;
import vendingmachine.model.coins.Coin;
import vendingmachine.model.coins.FiftyCents;
import vendingmachine.model.coins.FiveCents;
import vendingmachine.model.coins.OneEuro;
import vendingmachine.model.coins.TenCents;
import vendingmachine.model.coins.TwentyCents;

/**
 * Machine change holder implemented as a Singleton.
 * 
 * @author marceloaldanamato
 *
 */
public class ChangeHolder implements IChangeHolder {

	private static Optional<ChangeHolder> instance = Optional.empty();
	
	private Map<Double, List<Coin>> store = new HashMap<>();
	private List<Coin> acceptedCoins = Arrays.asList(new OneEuro(), 
													  new FiftyCents(), 
													  new TwentyCents(),
													  new TenCents(),
													  new FiveCents());
	private BigDecimal total;
	
	private IVault vault;
	
	private ChangeHolder() {
		this.vault = Vault.getInstance();
		for (Coin aCoin : acceptedCoins) {
			store.put(aCoin.getAmount(), new ArrayList<>());
		}
		this.total = BigDecimal.ZERO;
	}
	
	public static IChangeHolder getInstance() {
		if (!instance.isPresent()) {
			instance = Optional.of(new ChangeHolder());
		}
		return instance.get();
	}
	
	public List<Coin> get(double requestedAmount) throws InsufficientChangeException {
		//Use BigDecimal for calculations to be more precise
		BigDecimal bdRequestedAmount = new BigDecimal(requestedAmount, new MathContext(2));
		
		if (this.total.compareTo(bdRequestedAmount) < 0) {
			throw new InsufficientChangeException();
		}
				
		BigDecimal currentAmount = BigDecimal.ZERO;
		List<Coin> selectedCoins = new ArrayList<>();
		for (Coin anAcceptedCoin : this.acceptedCoins) {
			BigDecimal bdAcceptedCoinAmount = new BigDecimal(anAcceptedCoin.getAmount(), new MathContext(2));
			if (currentAmount.add(bdAcceptedCoinAmount).compareTo(bdRequestedAmount) <= 0) {
				List<Coin> availableCoins = this.store.get(anAcceptedCoin.getAmount());
				for (Coin anAvailableCoin : availableCoins) {
					if (currentAmount.add(bdAcceptedCoinAmount).compareTo(bdRequestedAmount) <= 0) {
						currentAmount = currentAmount.add(bdAcceptedCoinAmount);
						selectedCoins.add(anAvailableCoin);
					} else {
						break;
					}
				}
			}
		}
		
		if (currentAmount.compareTo(bdRequestedAmount) < 0) {			
			//We do not have enough coins to return the requested amount
			throw new InsufficientChangeException();
		}
		
		remove(selectedCoins);
		
		return selectedCoins;
	}

	public void add(List<Coin> someCoins) {
		for (Coin aCoin : someCoins) {
			if (!this.acceptedCoins.contains(aCoin)) {
				this.vault.add(aCoin);
			} else {
				List<Coin> coinStore = this.store.get(aCoin.getAmount());
				coinStore.add(aCoin);
				this.total = this.total.add(new BigDecimal(aCoin.getAmount()));
			}
		}
	}

	@Override
	public void empty() {
		for (List<Coin> coins : store.values()) {
			coins.clear();
		}
		this.total = BigDecimal.ZERO;
	}

	private void remove(List<Coin> selectedCoins) {
		for (Coin aCoin : selectedCoins) {
			this.store.get(aCoin.getAmount()).remove(0);
			this.total = this.total.subtract(new BigDecimal(aCoin.getAmount()));
		}
	}

}
