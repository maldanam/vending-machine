package vendingmachine.exceptions;

public class InsufficientStockException extends VendingMachineException {

	public InsufficientStockException() {
		super();
	}

	public InsufficientStockException(String message) {
		super(message);
	}

}
