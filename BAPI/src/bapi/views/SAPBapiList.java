package bapi.views;

	
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import java.io.*;

public class SAPBapiList {
	
	private String object;
	private String bapi;
	private String function;
	
    public SAPBapiList(String object,String bapi,String function) {
    	this.object = object;
    	this.bapi = bapi;
    	this.function = function;
    }
    
    public String getObject() {
    	return object;
    }
    public String getBapi() {
    	return bapi;
    }
    public String getFunction() {
    	return function;
    }
    
    public static SAPBapiList[] getList(JCoDestination destination,String destTable) throws JCoException {
    		 	
    	JCoFunction function = destination.getRepository().getFunction("RFC_READ_TABLE");
    	if(function == null)
    		throw new RuntimeException("RFC_READ_TABLE not found in SAP.");	        
    	function.getImportParameterList().setValue("QUERY_TABLE",destTable);
    	function.execute(destination);

    	JCoTable data = function.getTableParameterList().getTable("DATA");
    	SAPBapiList[] list = new SAPBapiList[data.getNumRows()];

//		xmlOutput(data);

		for(int i = 0;i < data.getNumRows();++i) {
			data.setRow(i);
			
			//splitting the large string and saving in variables
			String[] split = data.getString("WA").split(" ");
			String object = null;
			String bapi = null;
			String funct = null;
			int cnt = 0;
		
			//filtering the space signs
			for(int j = 0;j < split.length;++j) {
				if(split[j].length() > 1) {
					if(cnt == 0) {
						object = split[j];
						cnt++;	
					} else if(cnt == 1) {
						bapi = split[j];
						cnt++;	
					} else if(cnt == 2) {
						funct = split[j];
						cnt++;	
					}	
				}
			}
			list[i] = new SAPBapiList(object,bapi,funct);
	    }
		return list;
	}

	public static void xmlOutput(JCoTable data) {
		System.out.print("Writing into XML-file...");
		try {
			FileWriter f = new FileWriter("./output.xml");
			BufferedWriter b = new BufferedWriter(f);
			PrintWriter p = new PrintWriter(b);
			
			p.println(data.toXML());
			p.close();	
		}catch(IOException e) {
			e.printStackTrace();
		}
		System.out.println(" finished");
	}  
	    
}
