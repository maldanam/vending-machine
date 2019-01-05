package vendingmachine.components;

/**
 * Display implementation.
 * 
 * @author marceloaldanamato
 *
 */
public class Display implements IDisplay {

	Display() {
	}

	@Override
	public void showMessage(String message) {
		System.out.println("DISPLAY: " + message);
	}

}
