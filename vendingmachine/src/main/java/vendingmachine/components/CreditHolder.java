package vendingmachine.components;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import vendingmachine.model.coins.Coin;

public class CreditHolder implements ICreditHolder {

	private static Optional<CreditHolder> instance = Optional.empty();
	
	private List<Coin> store = new ArrayList<>();
	private BigDecimal total;

	private CreditHolder() {
		this.total = BigDecimal.ZERO;
	}
	
	public static ICreditHolder getInstance() {
		if (!instance.isPresent()) {
			instance = Optional.of(new CreditHolder());
		}
		return instance.get();
	}
	
	@Override
	public double getAmount() {
		return this.total.doubleValue();
	}

	@Override
	public void add(Coin aCoin) {
		this.store.add(aCoin);
		this.total = this.total.add(new BigDecimal(aCoin.getAmount(), new MathContext(2)));
	}

	@Override
	public List<Coin> empty() {
		List<Coin> result = this.store;
		this.store = new ArrayList<>();
		this.total = BigDecimal.ZERO;
		
		return result;
	}

}
