package vendingmachine.components;

public class ComponentFactory {

	public static IDisplay createDisplay() {
		return new Display();
	}
	
	public static ICreditHolder createCreditHolder() {
		return new CreditHolder();
	}
	
	public static IChangeHolder createChangeHolder() {
		return new ChangeHolder(new Vault());
	}
	
	public static IProductHolder createProductHolder() {
		return new ProductHolder();
	}

	public static IVault createVault() {
		return new Vault();
	}
}
