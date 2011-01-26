package bapi.views;

import java.util.ArrayList;

import com.sap.conn.jco.*;

//Kapselt den Zugriff auf das SAP System.
public class SAPProxy 
{	
	String[] bapis;
	SAPSession logon = new SAPSession("501",
			"devk-100",
			"b34a5p1i",
			"DE",
			"/H/saprouter2.hcc.uni-magdeburg.de/S/3299/H/M14Z",
			"14",
			"3",
			"10");
	
	//Gibt eine Liste aller im System vorhandenen BAPI´s zurück
	public void createBAPIList()
	{
		ArrayList table = new ArrayList();//complete table
//		ArrayList record = new ArrayList();//one record
		
		
		table = logon.getTable("SBAPIS");
		String[] bapis = new String[table.size()];
//		ListIterator iterTable = table.listIterator();
		for(int i = 0;i < table.size();++i) {
			String obj = (String)(((ArrayList)(table.get(i))).get(2));//nur Funktionsbaustein herausholen
			if(obj != null)
				bapis[i] = obj;
		}
		this.bapis = bapis;
	}

	public String[] getBapiTable(){
		return bapis;
	}
	
	//Gibt zu einem gegebenen BAPI die Liste der erforderlichen Aufruf Parameter zurück
	public Object[][] getParameterlistByBAPIName(String Name)
	{
		JCoFunction function;
		JCoMetaData md = null;
	    try{
	    	function = logon.destination.getRepository().getFunction(Name);
	    	md = function.getImportParameterList().getMetaData();
	    }catch(Exception x){}
	       
	    int length = md.getFieldCount();
		Object params[][] = new Object[length][2];

		for(int i = 0;i < params.length;i++){
			try {
				params[i][0] = md.getName(i);
				params[i][1] = getJavaType(md.getType(i));
				System.out.println("ParamName: "+params[i][0]+" ParamTyp: "+params[i][1]);	
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		return params;
	}
	public int getJavaType(int abapType) throws Exception {
		int INT = 0;
		int DOUBLE = 1;
		int STRING = 2;
		int DATE = 3;
		int BYTE = 4;
		int BIGDECIMAL = 5;
		
		int res = 0;
		switch(abapType) {
			case 0: res = STRING;break; //CHAR
			case 1: res = DATE;break; //DATE
			case 2: res = BIGDECIMAL;break; //BCD
			case 3: res = DATE;break; //TIME
			case 4: res = BYTE;break; //BYTE
			case 6: res = STRING;break; //NUM
			case 7: res = DOUBLE;break; //FLOAT
			case 8: res = INT;break; //INT
			case 9: res = INT;break; //INT2
			case 10:res = INT;break; //INT1
			case 29:res = STRING;break; //STRING
			case 30:res = BYTE;break; //XSTRING
			default: throw new Exception("Typ konnte nicht zugeordnet werden");
		}
		
		return res;
	}
	protected Object[][][] invokeBAPI( String bapiName, Object[][] parameters )
	{
		Object[][][] res;
		try {
			JCoFunction function = logon.destination.getRepository().getFunction(bapiName);
			for(int i = 0;i < parameters.length;++i) {
				function.getImportParameterList().setValue((String)parameters[i][0],parameters[i][1]);	
			}
			function.execute(logon.destination);
			JCoTable codes = function.getTableParameterList().getTable("BOOKING_LIST");
			
			//Anzahl der Ergebnistabellen über MetaData . getFieldCount() Ziel: Über nummerischen Index alle Tabellen auslesen
			//ein Array von Tabellen erzeugen --> 3-dim.
			JCoMetaData md = function.getTableParameterList().getMetaData();
			System.out.println(md.getFieldCount());
			
			//Object Array: 1.Dim: Anzahl der Ergebnisarrays, 
			//2.Dim: Zeilen der jew. Tabelle, 
			//3.Dim: Spalten der jew. Tabelle
			res = new Object[md.getFieldCount()][0][0];
			for(int i = 0;i < md.getFieldCount();++i) {
				JCoTable table = function.getTableParameterList().getTable(i);
				String[][] s = new String[table.getNumRows()][table.getNumColumns()];
				for(int j = 0;j < table.getNumRows();++j) {
					table.setRow(j);
					
					for(int k = 0;k < table.getNumColumns();++k) {
						s[j][k] = table.getString(k);
					}
				}
				//Die 2. und 3. Dim von res wird auf das 2Dim-Array "s" gesetzt
				res[i] = s;
			}
			return res;
//			res = new String[codes.getNumRows()][codes.getNumColumns()];
//			for(int i = 0;i < codes.getNumRows();++i) {
//				codes.setRow(i);
//				for(int j = 0;j < codes.getNumColumns();++j) {
//					res[i][j] = codes.getString(j);
//				}
//			}
		}catch(Exception e) {
			System.out.println("Es ist ein Fehler aufgetreten");
			return null;
		}
		
	}
	
	public void generateProxyCode( int JcoType, String Path, String[] SelectedBAPIs )
	{
		
	}
}
