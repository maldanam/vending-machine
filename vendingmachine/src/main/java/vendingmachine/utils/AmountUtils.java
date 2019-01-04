package vendingmachine.utils;

import java.math.BigDecimal;
import java.util.List;

import vendingmachine.model.coins.Coin;

public class AmountUtils {

	public static BigDecimal calculateAmount(List<Coin> someCoins) {
		BigDecimal amount = BigDecimal.ZERO;
		for (Coin coin : someCoins) {
			amount = amount.add(new BigDecimal(coin.getAmount()));
		}
		return amount;
	}

}
