package bapi.views;


import java.util.Properties;
import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class SAPSession {
	
	JCoDestination destination;

    public SAPSession(String clnt,String user,String pass,String lang,String host,String sysn,String capt,String limt) {
    	
    	Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, clnt);
        connectProperties.setProperty(DestinationDataProvider.JCO_USER, user);
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, pass);
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG, lang);
		connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, host);
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, sysn); 
        connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, capt);
        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, limt);
        
        createDataFile("ABAP_AS_POOLED", "jcoDestination", connectProperties);
        
        try {
        	destination = JCoDestinationManager.getDestination("ABAP_AS_POOLED");	
        }catch(JCoException e) {
        	e.printStackTrace();	
        }
        	
    }
    
    private void createDataFile(String name, String suffix, Properties properties) {
        File cfg = new File(name+"."+suffix);
        if(!cfg.exists()) {
            try {
                FileOutputStream fos = new FileOutputStream(cfg, false);
                properties.store(fos, "SAP Login Data");
                fos.close();
            }catch (Exception e) {
                throw new RuntimeException("Unable to create the destination file " + cfg.getName(), e);
            }
        }
    }
    
    public ArrayList getTable(String destTable) {
    	
    	ArrayList table = new ArrayList();
    	try {
    		SAPBapiList[] bapis = SAPBapiList.getList(destination,destTable);
    		for(int i = 0;i < bapis.length;++i) {
    			ArrayList record = new ArrayList();
    			record.add(bapis[i].getObject());
    			record.add(bapis[i].getBapi());
    			record.add(bapis[i].getFunction());
    			table.add(record);
    		}
    	}catch(JCoException e) {
    		e.printStackTrace();
    	}
    	return table;
    }
	 
}