package Main;

import bapi.views.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SAPProxy proxy = new SAPProxy();
		proxy.createBAPIList();
		String[] bapis = proxy.getBapiTable();
		for (int i = 0; i < bapis.length; ++i)
			System.out.println(bapis[i]);

		// Object[][] params =
		// proxy.GetParameterlistByBAPIName("BAPI_FLBOOKING_GETLIST");
		// for(int i = 0;i < params.length;++i) {
		// System.out.println(params[i][0]+" "+params[i][1]);
		// }
		//
		// ProjektBapi.ProxyTest test = new ProjektBapi.ProxyTest();
		// Object[][][] obj = test.BAPI_FLBOOKING_GETLIST( "LH",null,null,null);
		// test.getParameterlistByBAPIName("BAPI_FLBOOKING_GETLIST");

		// for(int i = 0;i < obj.length;++i) {
		// for(int j = 0;j < obj[i].length;++j) {
		// for(int k = 0;k < obj[i][j].length;++k) {
		// System.out.print(obj[i][j][k]+" ");
		// }
		// System.out.println();
		// }
		// }
	}
}
