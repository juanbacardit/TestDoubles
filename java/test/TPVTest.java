import static org.junit.Assert.*;

import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import Exceptions.NoCustomerAvailableToBuyException;
import Exceptions.NoCurrentSaleAvailable;

public class  TPVTest {

	TPV tpv;
	IRepository<String,ICustomer> customers;
	IRepository<String, IProduct> products;
	IRepository<String,ISale> historicalSales;
	IRepository<String,ISale> openSales;
	IInvoiceService invoiceService;
	INotificationService notificationService;
	
	
	@Before
	public void setUp(){
		
		//TODO: set mocks, stubs, dummies, fakes and spies...
		//customers = ....
		//products = ....
		//historicalSales = ....
		//openSales = ....
		//invoiceService = ....
		//nofificationService = .....
		
		tpv = new TPV(notificationService,invoiceService,historicalSales, openSales,customers,products);
	}
	
	@Test
	public void WhenAllSalesAreCloseTotalOpenSalesMustBeZero() throws NoCustomerAvailableToBuyException, NoCurrentSaleAvailable {

//		Please use mock, stubs, spies, etc.. to pass current test.
		
//		Uncomment next test code.
//		tpv.forCustomer("Joan")
//			.buys()
//			.product("Banana", 2)
//			.product("Apples", 4)
//			.total()
//			.invoice();
//		
//		tpv.forCustomer("Manel")
//			.buys()
//			.product("Cherries", 2)
//			.product("Potatoes", 4)
//			.total()
//			.invoice()
//			.sendInvoice();
		
//		assertEquals("When all sales are close, opensales must to be zero",tpv.printOpenSalesTotal(), "0.00€");
		assertTrue(true);	
	}
	
	/*
	 * Example of mocking using Mockito framework
	 * We're mocking all dependencies of TPV used in this test
	 */
	@Test
	public void WhenUsingMockitoToMockAllDependencies(){
		ISale sale = mock(ISale.class);
		when(sale.getInvoiceIdentifier()).thenReturn("Test");
		ICustomer _customer = mock(ICustomer.class);
		when(_customer.getIdentifier()).thenReturn("Joan");
		assertEquals(_customer.getIdentifier(),"Joan");		
	}
}
