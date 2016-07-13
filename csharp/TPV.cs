using System;
using TestDouble.Exceptions;
using System.Collections.Generic;


namespace TestDouble {

public class TPV {
	
	IRepository<string,ICustomer> _customers;
	IRepository<string, IProduct> _products;
	IRepository<string,ISale> _historicalSales;
	IRepository<string,ISale> _openSales;
	IInvoiceService _invoiceService;
	INotificationService _notificationService;
	ISale _currentSale;
	ICustomer _currentCustomer;
	
	public TPV(INotificationService notificationService, IInvoiceService invoiceService
			,IRepository<string,ISale> historical, IRepository<string,ISale> openSales
			,IRepository<string,ICustomer> customers,IRepository<string, IProduct> products) {

		_customers = customers;
		_products = products;
		_notificationService = notificationService;
		_invoiceService = invoiceService;
		_historicalSales = historical;
		_openSales = openSales;
	}
	
	public TPV forCustomer(string Name){
		_currentCustomer = _customers.getById(Name);
		_currentSale = null;
		return this;
	}
	
	public TPV buys() {
		if(_currentCustomer == null)
			throw new NoCustomerAvailableToBuyException();
		
		if(_openSales.contains(_currentCustomer.getIdentifier()))
			 _currentSale = _openSales.getById(_currentCustomer.getIdentifier());
		else
			 _currentSale = _openSales.getNew();
		return this;
	}
	
	public TPV product(string productName, int quantity) {
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

	public int resendInvoice(string identifier){
		IInvoice invoice = _invoiceService.getInvoice(identifier);
		_notificationService.send(invoice);
		return _notificationService.howManyTimesInvoiceHasBeenSended(invoice);
	}
	
	public List<IInvoice> getTodayResume(){
		return _invoiceService.getAllInvoicesByDay(DateTime.Now.Day);
	}

	public string printGrandTotal(){
		double total = 0.0;
		foreach(ISale sale in _historicalSales.getAll()){
			total+= sale.getTotal();
		}
		return string.Format("%10.2f€", total);
	}
	
	public string printOpenSalesTotal(){
		double total = 0.0;
		foreach(ISale sale in _openSales.getAll()){
			total+= sale.getTotal();
		}
		return string.Format("%10.2f€", total);
	}
}
}