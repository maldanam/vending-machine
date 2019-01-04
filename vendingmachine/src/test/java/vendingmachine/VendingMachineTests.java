package vendingmachine;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static vendingmachine.utils.AmountUtils.calculateAmount;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import vendingmachine.exceptions.InsufficientChangeException;
import vendingmachine.exceptions.InsufficientCreditException;
import vendingmachine.exceptions.InsufficientStockException;
import vendingmachine.exceptions.VendingMachineException;
import vendingmachine.model.ProductKind;
import vendingmachine.model.Sale;
import vendingmachine.model.coins.FiftyCents;
import vendingmachine.model.coins.OneEuro;
import vendingmachine.model.coins.TwoEuro;

public class VendingMachineTests {

	private IVendingMachine machine;
	
	@Before
	public void setUp() throws Exception {
		this.machine = VendingMachine.getInstance();
		
		this.machine.reset();
	}

	@Test
	public void testReset() {
		System.out.println("testReset ----------------");
		
		this.machine.addCredit(new TwoEuro());
		assertTrue("Credit has been stored incorrectly.", this.machine.getCreditAmount() == 2);

		this.machine.fill(Arrays.asList(new OneEuro(), new OneEuro(), new FiftyCents()));
		assertTrue("Change has been stored incorrectly.", this.machine.getChangeAmount() == 2.50);

		this.machine.fill(ProductKind.COKE, 10);
		assertTrue("Product %s has been stored incorrectly.", this.machine.getStock(ProductKind.COKE) == 10);

		this.machine.reset();
		assertTrue("Credit has been emptied incorrectly.", this.machine.getCreditAmount() == 0);
		assertTrue("Change has been emptied incorrectly.", this.machine.getChangeAmount() == 0);
		assertTrue("Product %s has been emptied incorrectly.", this.machine.getStock(ProductKind.COKE) == 0);
		
	}

	@Test
	public void testAddSomeCredit() {
		System.out.println("testAddSomeCredit ----------------");
		
		this.machine.addCredit(new TwoEuro());
		this.machine.addCredit(new OneEuro());
		this.machine.addCredit(new FiftyCents());

		assertTrue("Credit has been stored incorrectly.", this.machine.getCreditAmount() == 3.50);
	}

	@Test
	public void testRefundNoCredit() {
		System.out.println("testRefundNoCredit ----------------");
		
		double amountRefunded = calculateAmount(this.machine.refund()).doubleValue();
		
		assertTrue("Credit has been stored incorrectly.", amountRefunded == 0);
	}

	@Test
	public void testRefundCurrentCredit() {
		System.out.println("testRefundCurrentCredit ----------------");

		this.machine.addCredit(new TwoEuro());
		this.machine.addCredit(new OneEuro());
		this.machine.addCredit(new FiftyCents());
		
		double amountRefunded = calculateAmount(this.machine.refund()).doubleValue();
		
		assertTrue("Credit has been stored incorrectly.", amountRefunded == 3.50);
	}

	@Test
	public void testFillSomeCoins() {
		System.out.println("testFillSomeCoins ----------------");
		
		this.machine.fill(Arrays.asList(new OneEuro(), new OneEuro(), new FiftyCents(), new FiftyCents(), new FiftyCents()));

		assertTrue("Change has been stored incorrectly.", this.machine.getChangeAmount() == 3.50);
	}

	@Test
	public void testFillSomeProducts() {
		System.out.println("testFillSomeProducts ----------------");
		
		this.machine.fill(ProductKind.COKE, 10);
		assertTrue("Product %s has been stored incorrectly.", this.machine.getStock(ProductKind.COKE) == 10);

		this.machine.fill(ProductKind.SPRITE, 10);
		assertTrue("Product %s has been stored incorrectly.", this.machine.getStock(ProductKind.SPRITE) == 10);

		this.machine.fill(ProductKind.WATER, 10);
		assertTrue("Product %s has been stored incorrectly.", this.machine.getStock(ProductKind.WATER) == 10);

	}

	@Test
	public void testSaleWithInsufficientCredit() {
		System.out.println("testSellWithInsufficientCredit ----------------");

		this.machine.fill(Arrays.asList(new OneEuro(), new OneEuro(), new FiftyCents()));
		assertTrue("Change has been stored incorrectly.", this.machine.getChangeAmount() == 2.50);

		this.machine.fill(ProductKind.COKE, 10);
		assertTrue("Product %s has been stored incorrectly.", this.machine.getStock(ProductKind.COKE) == 10);

		try {
			this.machine.select(ProductKind.COKE);
			fail("Expected exception not detected.");
		} catch (InsufficientCreditException e) {
		} catch (VendingMachineException e) {
			fail(String.format("Raised exception of different type (%s)", e.getClass().getName()));
		}
		assertTrue("Credit has been modified on sale operation.", this.machine.getCreditAmount() == 0);
		assertTrue("Change has been modified on sale operation.", this.machine.getChangeAmount() == 2.50);
		assertTrue("Product %s stock has been modified on sale operation.", this.machine.getStock(ProductKind.COKE) == 10);
		
	}

	@Test
	public void testSaleWithInsufficientChange() {
		System.out.println("testSellWithInsufficientChange ----------------");

		this.machine.addCredit(new TwoEuro());
		assertTrue("Credit has been stored incorrectly.", this.machine.getCreditAmount() == 2);

		this.machine.fill(ProductKind.COKE, 10);
		assertTrue("Product %s has been stored incorrectly.", this.machine.getStock(ProductKind.COKE) == 10);

		try {
			this.machine.select(ProductKind.COKE);
			fail("Expected exception not detected.");
		} catch (InsufficientChangeException e) {
		} catch (VendingMachineException e) {
			fail(String.format("Raised exception of different type (%s)", e.getClass().getName()));
		}
		assertTrue("Credit has been modified on sale operation.", this.machine.getCreditAmount() == 2);
		assertTrue("Change has been modified on sale operation.", this.machine.getChangeAmount() == 0);
		assertTrue("Product %s stock has been modified on sale operation.", this.machine.getStock(ProductKind.COKE) == 10);
		
	}
	
	@Test
	public void testSaleWithInsufficientStock() {
		System.out.println("testSellWithInsufficientStock ----------------");

		this.machine.addCredit(new TwoEuro());
		assertTrue("Credit has been stored incorrectly.", this.machine.getCreditAmount() == 2);

		this.machine.fill(Arrays.asList(new OneEuro(), new OneEuro(), new FiftyCents()));
		assertTrue("Change has been stored incorrectly.", this.machine.getChangeAmount() == 2.50);

		try {
			this.machine.select(ProductKind.COKE);
			fail("Expected exception not detected.");
		} catch (InsufficientStockException e) {
		} catch (VendingMachineException e) {
			fail(String.format("Raised exception of different type (%s)", e.getClass().getName()));
		}
		assertTrue("Credit has been modified on sale operation.", this.machine.getCreditAmount() == 2);
		assertTrue("Change has been modified on sale operation.", this.machine.getChangeAmount() == 2.50);
		assertTrue("Product %s stock has been modified on sale operation.", this.machine.getStock(ProductKind.COKE) == 0);
		
	}
	
	@Test
	public void testCorrectSale() {
		System.out.println("testCorrectSale ----------------");

		this.machine.addCredit(new TwoEuro());
		assertTrue("Credit has been stored incorrectly.", this.machine.getCreditAmount() == 2);

		this.machine.fill(Arrays.asList(new OneEuro(), new OneEuro(), new FiftyCents()));
		assertTrue("Change has been stored incorrectly.", this.machine.getChangeAmount() == 2.50);

		this.machine.fill(ProductKind.COKE, 10);
		assertTrue("Product %s has been stored incorrectly.", this.machine.getStock(ProductKind.COKE) == 10);

		try {

			Optional<Sale> result = this.machine.select(ProductKind.COKE);
			Sale theSale = result.get();
			
			assertTrue("Returned product is incorrect.", ProductKind.COKE.equals(theSale.getProduct().getKind()));
			assertTrue("Returned change is incorrect.", calculateAmount(theSale.getChange()).doubleValue() == 0.50);			
			
		} catch (Exception e) {
			fail(String.format("Raised exception unexpectedly (%s)", e.getClass().getName()));
		}
		assertTrue("Credit has been modified incorrectly.", this.machine.getCreditAmount() == 0);
		assertTrue("Change has been modified incorrectly.", this.machine.getChangeAmount() == 2);
		assertTrue("Product %s stock has been modified incorrectly.", this.machine.getStock(ProductKind.COKE) == 9);
		
	}
	
}
