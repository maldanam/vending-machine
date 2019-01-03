package vendingmachine.exceptions;

public abstract class VendingMachineException extends Exception {

	public VendingMachineException() {
		super();
	}

	public VendingMachineException(String message) {
		super(message);
	}

}
