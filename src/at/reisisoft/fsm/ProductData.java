package at.reisisoft.fsm;

/**
 * {@link #toString()} and {@link #getProduct()} return the name of the product
 * 
 * @author Florian
 *
 */
public class ProductData {
	private ProductData() {

	}

	private static ProductData productData = null;

	public static ProductData getInstance() {
		if (productData == null) {
			productData = new ProductData();
		}
		return productData;
	}

	private final String product = "04 FSM";

	public String getProduct() {
		return product;
	}

	@Override
	public String toString() {
		return getProduct();
	}

}
