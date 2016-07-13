import java.util.List;

public interface IInvoiceService {
	IInvoice getInvoice(String identifier);
	void generateInvoice(ISale _currentSale, ICustomer _currentCustomer);
	List<IInvoice> getAllInvoicesByDay(int dayOfMonth);
}
