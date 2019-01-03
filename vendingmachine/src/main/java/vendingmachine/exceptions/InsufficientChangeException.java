package vendingmachine.exceptions;

public class InsufficientChangeException extends VendingMachineException {

	public InsufficientChangeException() {
		super();
	}

	public InsufficientChangeException(String message) {
		super(message);
	}

}
