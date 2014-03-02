


enum Currency{
	
	PENNY(1),NICKEL(5),DIME(10),QUARTER(25);

	private int value;
	
	private Currency(int value) {
		this.value = value;
	}
	
	public int getValue(Currency c){
		return c.value;
	}
}

public class Help{
	
	public static void main(String[] args) {
		
		Currency c = Currency.DIME;
		System.out.println(c);
		
		System.out.println(c.getValue(c));
	}
}
