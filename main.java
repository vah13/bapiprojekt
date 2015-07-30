package sap_rfc;

import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;



public class Example {


	public void exampleTest() {
		JCO.Client connClient = null;
		IRepository mRepository;
		connClient = JCO.createClient("001", "SAP*", "asdQWE123", "EN", "172.16.0.90", "00");
		try {
			connClient.connect();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}

		mRepository = new JCO.Repository("Hell", connClient);
		JCO.Function strHELLO = mRepository.getFunctionTemplate("RFC_READ_TABLE").getFunction();
		JCO.ParameterList importParam = strHELLO.getImportParameterList();
		importParam.setValue("RSMPTEXTS", "QUERY_TABLE");

		try {
			connClient.execute(strHELLO);
		} catch (JCO.AbapException ex) {
			System.out.println(ex.getMessage());
		}

	}

	public static void main(String[] args) {
		Example anExample = new Example();
		try {
			anExample.exampleTest();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}