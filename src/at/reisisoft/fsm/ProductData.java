package at.reisisoft.fsm;

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

	private final int mjVers = 0;
	private final int minVers = 0;
	private final int micVers = 1;
	private final String product = "04 FSM";

	public int getMajor() {
		return mjVers;
	}

	public int getMinor() {
		return minVers;
	}

	public int getMicro() {
		return micVers;
	}

	public int getMjVers() {
		return mjVers;
	}

	public int getMinVers() {
		return minVers;
	}

	public int getMicVers() {
		return micVers;
	}

	public String getProduct() {
		return product;
	}

	@Override
	public String toString() {
		return getProduct() + " v. " + getVersion();
	}

	public String getVersion() {
		return getMajor() + "." + getMinor() + '.' + getMicro();
	}

}
