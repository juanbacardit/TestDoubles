using System.Collections.Generic;

namespace TestDouble
{
public interface IInvoiceService {
	IInvoice getInvoice(string identifier);
	void generateInvoice(ISale _currentSale, ICustomer _currentCustomer);
	List<IInvoice> getAllInvoicesByDay(int dayOfMonth);
}
}