package vendingmachine;

import java.util.List;
import java.util.Optional;

import vendingmachine.components.ComponentFactory;
import vendingmachine.components.IChangeHolder;
import vendingmachine.components.ICreditHolder;
import vendingmachine.components.IDisplay;
import vendingmachine.components.IProductHolder;
import vendingmachine.exceptions.InsufficientCreditException;
import vendingmachine.exceptions.VendingMachineException;
import vendingmachine.model.Product;
import vendingmachine.model.ProductKind;
import vendingmachine.model.Sale;
import vendingmachine.model.coins.Coin;

/**
 * A Vending Machine implemented as a Singleton.
 * 
 * @author marceloaldanamato
 *
 */
public class VendingMachine implements IVendingMachine {

	private static Optional<VendingMachine> instance = Optional.empty();

	private IDisplay display;
	private ICreditHolder creditHolder;
	private IChangeHolder changeHolder;
	private IProductHolder productHolder;
	
	private VendingMachine() {
		this.display = ComponentFactory.createDisplay();
		this.creditHolder = ComponentFactory.createCreditHolder();
		this.changeHolder = ComponentFactory.createChangeHolder();
		this.productHolder = ComponentFactory.createProductHolder();
	}

	public static IVendingMachine getInstance() {
		if (!instance.isPresent()) {
			instance = Optional.of(new VendingMachine());
		}
		return instance.get();
	}
	
	@Override
	public void addCredit(Coin aCoin) {
		
		this.creditHolder.add(aCoin);
		
		this.display.showMessage(String.format("Current credit is %s euros", this.creditHolder.getAmount()));
	}

	@Override
	public Optional<Sale> select(ProductKind aProductKind) throws VendingMachineException {
		Optional<Sale> result = Optional.empty();
		
		//Decrement a stock unit
		Product unit = this.productHolder.sell(aProductKind);
		
		//Show product price on the display
		this.display.showMessage(String.format("One unit of %s is %s euros", ProductKind.COKE, unit.getPrice()));

		try {
			
			//Check there is enough credit to buy the product
			if (this.creditHolder.getAmount() < unit.getPrice()) {
				throw new InsufficientCreditException();
			}
			
			//Get the change to return to the user
			double changeToReturn = this.creditHolder.getAmount()-unit.getPrice();
			List<Coin> change = this.changeHolder.get(changeToReturn);
			
			result = Optional.of(new Sale(unit, change));
			
			//All the credit is loaded on change amount
			this.changeHolder.add(this.creditHolder.empty());
			
			//Show a message on the display
			this.display.showMessage(String.format("One unit of %s has been sold", ProductKind.COKE));
			
		} catch (VendingMachineException e) {
			
			//Return the product to the stock
			this.productHolder.add(aProductKind, 1);

			throw e;
		}		
		
		return result;
	}

	@Override
	public List<Coin> refund() {
		this.display.showMessage(String.format("Refunding %s euros", this.creditHolder.getAmount()));

		return this.creditHolder.empty();
	}

	@Override
	public void fill(List<Coin> someCoins) {

		this.changeHolder.add(someCoins);
		this.display.showMessage(String.format("%s euros of change loaded", this.changeHolder.getAmount()));
	}

	@Override
	public void fill(ProductKind aProductKind, int quantity) {

		this.productHolder.add(aProductKind, quantity);
		this.display.showMessage(String.format("%s units of %s loaded", quantity, aProductKind));
	}

	@Override
	public void reset() {
		this.creditHolder.empty();
		this.productHolder.empty();
		this.changeHolder.empty();
		this.display.showMessage("Reset done");
	}

	@Override
	public double getCreditAmount() {
		return this.creditHolder.getAmount();
	}

	@Override
	public double getChangeAmount() {
		return this.changeHolder.getAmount();
	}

	@Override
	public int getStock(ProductKind aProductKind) {
		return this.productHolder.availableStock(aProductKind);
	}

}
