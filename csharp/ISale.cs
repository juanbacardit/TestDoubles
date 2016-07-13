namespace TestDouble
{
public interface ISale {
	string getIdentifier();
	void addProduct(IProduct product, int quantity);
	void popProduct(IProduct product);
	void Total();
	double getTotal();
	string getInvoiceIdentifier();
}
}