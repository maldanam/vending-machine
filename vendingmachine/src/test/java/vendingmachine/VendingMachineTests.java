package vendingmachine;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static vendingmachine.utils.AmountUtils.calculateAmount;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
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

	private static IVendingMachine machine;
	
	@BeforeClass
	public static void setUp() throws Exception {
		machine = VendingMachine.getInstance();
	}

	@Before
	public void reset() throws Exception {
		
		machine.reset();
	}

	@Test
	public void testReset() {
		System.out.println("testReset ----------------");
		
		machine.addCredit(new TwoEuro());
		assertTrue("Credit has been stored incorrectly.", machine.getCreditAmount() == 2);

		machine.fill(Arrays.asList(new OneEuro(), new OneEuro(), new FiftyCents()));
		assertTrue("Change has been stored incorrectly.", machine.getChangeAmount() == 2.50);

		machine.fill(ProductKind.COKE, 10);
		assertTrue("Product %s has been stored incorrectly.", machine.getStock(ProductKind.COKE) == 10);

		machine.reset();
		assertTrue("Credit has been emptied incorrectly.", machine.getCreditAmount() == 0);
		assertTrue("Change has been emptied incorrectly.", machine.getChangeAmount() == 0);
		assertTrue("Product %s has been emptied incorrectly.", machine.getStock(ProductKind.COKE) == 0);
		
	}

	@Test
	public void testAddSomeCredit() {
		System.out.println("testAddSomeCredit ----------------");
		
		machine.addCredit(new TwoEuro());
		machine.addCredit(new OneEuro());
		machine.addCredit(new FiftyCents());

		assertTrue("Credit has been stored incorrectly.", machine.getCreditAmount() == 3.50);
	}

	@Test
	public void testRefundNoCredit() {
		System.out.println("testRefundNoCredit ----------------");
		
		double amountRefunded = calculateAmount(machine.refund()).doubleValue();
		
		assertTrue("Credit refunded when machine is empty.", amountRefunded == 0);
	}

	@Test
	public void testRefundCurrentCredit() {
		System.out.println("testRefundCurrentCredit ----------------");

		machine.addCredit(new TwoEuro());
		machine.addCredit(new OneEuro());
		machine.addCredit(new FiftyCents());
		
		double amountRefunded = calculateAmount(machine.refund()).doubleValue();
		
		assertTrue("Credit has been stored incorrectly.", amountRefunded == 3.50);
	}

	@Test
	public void testFillSomeCoins() {
		System.out.println("testFillSomeCoins ----------------");
		
		machine.fill(Arrays.asList(new OneEuro(), new OneEuro(), new FiftyCents(), new FiftyCents(), new FiftyCents()));

		assertTrue("Change has been stored incorrectly.", machine.getChangeAmount() == 3.50);
	}

	@Test
	public void testFillSomeProducts() {
		System.out.println("testFillSomeProducts ----------------");
		
		machine.fill(ProductKind.COKE, 10);
		assertTrue("Product %s has been stored incorrectly.", machine.getStock(ProductKind.COKE) == 10);

		machine.fill(ProductKind.SPRITE, 10);
		assertTrue("Product %s has been stored incorrectly.", machine.getStock(ProductKind.SPRITE) == 10);

		machine.fill(ProductKind.WATER, 10);
		assertTrue("Product %s has been stored incorrectly.", machine.getStock(ProductKind.WATER) == 10);

	}

	@Test
	public void testSaleWithInsufficientCredit() {
		System.out.println("testSellWithInsufficientCredit ----------------");

		machine.fill(Arrays.asList(new OneEuro(), new OneEuro(), new FiftyCents()));
		assertTrue("Change has been stored incorrectly.", machine.getChangeAmount() == 2.50);

		machine.fill(ProductKind.COKE, 10);
		assertTrue("Product %s has been stored incorrectly.", machine.getStock(ProductKind.COKE) == 10);

		try {
			machine.select(ProductKind.COKE);
			fail("Expected exception not detected.");
		} catch (InsufficientCreditException e) {
		} catch (VendingMachineException e) {
			fail(String.format("Raised exception of different type (%s)", e.getClass().getName()));
		}
		assertTrue("Credit has been modified on sale operation.", machine.getCreditAmount() == 0);
		assertTrue("Change has been modified on sale operation.", machine.getChangeAmount() == 2.50);
		assertTrue("Product %s stock has been modified on sale operation.", machine.getStock(ProductKind.COKE) == 10);
		
	}

	@Test
	public void testSaleWithInsufficientChange() {
		System.out.println("testSellWithInsufficientChange ----------------");

		machine.addCredit(new TwoEuro());
		assertTrue("Credit has been stored incorrectly.", machine.getCreditAmount() == 2);

		machine.fill(ProductKind.COKE, 10);
		assertTrue("Product %s has been stored incorrectly.", machine.getStock(ProductKind.COKE) == 10);

		try {
			machine.select(ProductKind.COKE);
			fail("Expected exception not detected.");
		} catch (InsufficientChangeException e) {
		} catch (VendingMachineException e) {
			fail(String.format("Raised exception of different type (%s)", e.getClass().getName()));
		}
		assertTrue("Credit has been modified on sale operation.", machine.getCreditAmount() == 2);
		assertTrue("Change has been modified on sale operation.", machine.getChangeAmount() == 0);
		assertTrue("Product %s stock has been modified on sale operation.", machine.getStock(ProductKind.COKE) == 10);
		
	}
	
	@Test
	public void testSaleWithInsufficientStock() {
		System.out.println("testSellWithInsufficientStock ----------------");

		machine.addCredit(new TwoEuro());
		assertTrue("Credit has been stored incorrectly.", machine.getCreditAmount() == 2);

		machine.fill(Arrays.asList(new OneEuro(), new OneEuro(), new FiftyCents()));
		assertTrue("Change has been stored incorrectly.", machine.getChangeAmount() == 2.50);

		try {
			machine.select(ProductKind.COKE);
			fail("Expected exception not detected.");
		} catch (InsufficientStockException e) {
		} catch (VendingMachineException e) {
			fail(String.format("Raised exception of different type (%s)", e.getClass().getName()));
		}
		assertTrue("Credit has been modified on sale operation.", machine.getCreditAmount() == 2);
		assertTrue("Change has been modified on sale operation.", machine.getChangeAmount() == 2.50);
		assertTrue("Product %s stock has been modified on sale operation.", machine.getStock(ProductKind.COKE) == 0);
		
	}
	
	@Test
	public void testCorrectSale() {
		System.out.println("testCorrectSale ----------------");

		machine.addCredit(new TwoEuro());
		assertTrue("Credit has been stored incorrectly.", machine.getCreditAmount() == 2);

		machine.fill(Arrays.asList(new OneEuro(), new OneEuro(), new FiftyCents()));
		assertTrue("Change has been stored incorrectly.", machine.getChangeAmount() == 2.50);

		machine.fill(ProductKind.COKE, 10);
		assertTrue("Product %s has been stored incorrectly.", machine.getStock(ProductKind.COKE) == 10);

		try {

			Optional<Sale> result = machine.select(ProductKind.COKE);
			Sale theSale = result.get();
			
			assertTrue("Returned product is incorrect.", ProductKind.COKE.equals(theSale.getProduct().getKind()));
			assertTrue("Returned change is incorrect.", calculateAmount(theSale.getChange()).doubleValue() == 0.50);			
			
		} catch (Exception e) {
			fail(String.format("Raised exception unexpectedly (%s)", e.getClass().getName()));
		}
		assertTrue("Credit has been modified incorrectly.", machine.getCreditAmount() == 0);
		assertTrue("Change has been modified incorrectly.", machine.getChangeAmount() == 2);
		assertTrue("Product %s stock has been modified incorrectly.", machine.getStock(ProductKind.COKE) == 9);
		
	}
	
}
