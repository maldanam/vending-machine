package vendingmachine.components;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import vendingmachine.model.coins.Coin;

/**
 * A CreditHolder implementation
 * 
 * @author marceloaldanamato
 *
 */
public class CreditHolder implements ICreditHolder {

	private List<Coin> store = new ArrayList<>();
	private BigDecimal total;

	CreditHolder() {
		this.total = BigDecimal.ZERO;
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
