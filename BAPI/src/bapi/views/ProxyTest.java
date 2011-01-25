package bapi.views;

public class ProxyTest extends SAPProxy {
	
	public Object[][][] BAPI_FLBOOKING_GETLIST( String AIRLINE, String CUSTOMER_NUMBER, String MAX_ROWS, String TRAVEL_AGENCY)
	{
		String[][] pars = new String[4][2];
		
		pars[0][0] = "AIRLINE";
		pars[0][1] = AIRLINE;
		
		pars[1][0] = "CUSTOMER_NUMBER";
		pars[1][1] = CUSTOMER_NUMBER;
		
		pars[2][0] = "MAX_ROWS";
		pars[2][1] = MAX_ROWS;
		
		pars[3][0] = "TRAVEL_AGENCY";
		pars[3][1] = TRAVEL_AGENCY;
		
		return invokeBAPI( "BAPI_FLBOOKING_GETLIST" , pars );
	}
	
}
