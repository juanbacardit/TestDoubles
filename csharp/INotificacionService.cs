namespace TestDouble
{
public interface INotificationService {
	
	void send(IInvoice invoice);
	int howManyTimesInvoiceHasBeenSended(IInvoice invoice);

}
}
