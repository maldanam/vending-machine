package vendingmachine;

import java.util.List;
import java.util.Optional;

import vendingmachine.exceptions.VendingMachineException;
import vendingmachine.model.ProductKind;
import vendingmachine.model.Sale;
import vendingmachine.model.coins.Coin;

public interface IVendingMachine {

	/**
	 * Resets the vending machine to its initial status:
	 *   - No user credit.
	 *   - No change available.
	 *   - No product available.
	 * 
	 */
	void reset();
	
	/**
	 * Returns the credit amount currently inserted in the machine.
	 * 
	 * @return the current credit amount available in the machine
	 */
	double getCreditAmount();
	
	/**
	 * Returns the change amount currently available.
	 * 
	 * @return the current change amount available in the machine
	 */
	double getChangeAmount();
	
	/**
	 * Returns the stock available of a product kind.
	 * 
	 * @param aProductKind the product kind queried
	 * @return the number of product units available for sale
	 */
	int getStock(ProductKind aProductKind);
	
	/**
	 * Add a coin to increase stored credit.
	 * 
	 * @param aCoin a coin inserted in the machine
	 */
	void addCredit(Coin aCoin);
	
	/**
	 * Selects a product of a kind to be returned. Its price will be shown in the display and the product will
	 * be returned if the user has provided enough credit previously. When the sale can be done, the product and
	 * remaining change are returned.
	 * 
	 * @param aProductKind to be sold
	 * @return a Sale object if the sale could be completed
	 * @throws VendingMachineException if an error was detected during sale execution
	 */
	Optional<Sale> select(ProductKind aProductKind) throws VendingMachineException;
	
	/**
	 * Refunds the credit provided by the user.
	 * 
	 * @return all coins previously provided by the user
	 */
	List<Coin> refund();
	
	/**
	 * Add some coins to the machine to increase available change amount.
	 * 
	 * @param someCoins provided change
	 */
	void fill(List<Coin> someCoins);
	
	/**
	 * Supply some products of a kind to increase machine available stock.
	 * 
	 * @param aProductKind the product kind supplied
	 * @param quantity number of units supplied
	 */
	void fill(ProductKind aProductKind, int quantity);
	
}
