import java.util.Calendar;
import java.util.List;

import Exceptions.NoCurrentSaleAvailable;
import Exceptions.NoCustomerAvailableToBuyException;

public class TPV {
	
	IRepository<String,ICustomer> _customers;
	IRepository<String, IProduct> _products;
	IRepository<String,ISale> _historicalSales;
	IRepository<String,ISale> _openSales;
	IInvoiceService _invoiceService;
	INotificationService _notificationService;
	
	ISale _currentSale;
	ICustomer _currentCustomer;
	
	public TPV(INotificationService notificationService, IInvoiceService invoiceService
			,IRepository<String,ISale> historical, IRepository<String,ISale> openSales
			,IRepository<String,ICustomer> customers,IRepository<String, IProduct> products) {

		_customers = customers;
		_products = products;
		_notificationService = notificationService;
		_invoiceService = invoiceService;
		_historicalSales = historical;
		_openSales = openSales;
	}
	
	public TPV forCustomer(String Name){
		_currentCustomer = _customers.getById(Name);
		_currentSale = null;
		return this;
	}
	
	public TPV buys() throws NoCustomerAvailableToBuyException{
		if(_currentCustomer == null)
			throw new NoCustomerAvailableToBuyException();
		
		if(_openSales.contains(_currentCustomer.getIdentifier()))
			 _currentSale = _openSales.getById(_currentCustomer.getIdentifier());
		else
			 _currentSale = _openSales.getNew();
		return this;
	}
	
	public TPV product(String productName, int quantity) throws NoCustomerAvailableToBuyException, NoCurrentSaleAvailable{
		if(_currentCustomer == null )
			throw new NoCustomerAvailableToBuyException();
		if(_currentSale == null)
			throw new NoCurrentSaleAvailable();
		IProduct product = _products.getById(productName);
		_currentSale.addProduct(product, quantity);
		return this;
	}

	public TPV total(){
		_currentSale.Total();
		_historicalSales.add(_currentSale);
		_openSales.remove(_currentSale);
		return this;
	}
	
	public TPV invoice(){
		_invoiceService.generateInvoice(_currentSale,_currentCustomer);
		return this;
	}
	
	public void sendInvoice(){
		IInvoice invoice = _invoiceService.getInvoice(_currentSale.getInvoiceIdentifier());
		_notificationService.send(invoice);
	}

	public int resendInvoice(String identifier){
		IInvoice invoice = _invoiceService.getInvoice(identifier);
		_notificationService.send(invoice);
		return _notificationService.howManyTimesInvoiceHasBeenSended(invoice);
	}
	
	public List<IInvoice> getTodayResume(){
		return _invoiceService.getAllInvoicesByDay(Calendar.DAY_OF_MONTH);
	}

	public String printGrandTotal(){
		double total = 0.0;
		for(ISale sale : _historicalSales.getAll()){
			total+= sale.getTotal();
		}
		return String.format("%10.2f€", total);
	}
	
	public String printOpenSalesTotal(){
		double total = 0.0;
		for(ISale sale : _openSales.getAll()){
			total+= sale.getTotal();
		}
		return String.format("%10.2f€", total);
	}
}
