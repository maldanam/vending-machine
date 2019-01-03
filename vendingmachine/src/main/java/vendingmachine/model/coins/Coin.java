package vendingmachine.model.coins;

public abstract class Coin {

	private double amount;
	
	public Coin(double amount) {
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Coin) {
			return (this.amount == ((Coin) obj).amount);
		}
		return false;
	}
	
}
