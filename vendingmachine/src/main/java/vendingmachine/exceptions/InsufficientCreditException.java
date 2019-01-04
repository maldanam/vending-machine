package vendingmachine.exceptions;

public class InsufficientCreditException extends VendingMachineException {

	public InsufficientCreditException() {
		super();
	}

	public InsufficientCreditException(String message) {
		super(message);
	}

}
