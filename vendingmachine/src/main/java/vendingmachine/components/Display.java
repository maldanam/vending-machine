package vendingmachine.components;

import java.util.Optional;

/**
 * IDisplay implementation as a Singleton.
 * 
 * @author marceloaldanamato
 *
 */
public class Display implements IDisplay {

	private static Optional<Display> instance = Optional.empty();

	private Display() {
	}

	public static IDisplay getInstance() {
		if (!instance.isPresent()) {
			instance = Optional.of(new Display());
		}
		return instance.get();
	}
	
	@Override
	public void showMessage(String message) {
		System.out.println("DISPLAY: " + message);
	}

}
