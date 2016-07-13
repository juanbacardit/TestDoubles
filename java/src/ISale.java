
public interface ISale {
	String getIdentifier();
	void addProduct(IProduct product, int quantity);
	void popProduct(IProduct product);
	void Total();
	double getTotal();
	String getInvoiceIdentifier();
}
