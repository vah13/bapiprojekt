package bapi.views;

//Die Basis Klasse für unsere angepasste Proxy Klasse. Sie enthält die Service-Methoden um ein BAPI auf dem SAP System aufzurufen
public class BAPIProxyBase 
{
	//Mit dieser Methode wird ein BAPI mit einem gegebenen Namen und einer gegebenen Parameterliste variabler länge auf dem SAP System aufgerufen. dieser Vorgang erfolgt synchron!
	protected Object InvokeBAPI( String Name, Object[][] Params)
	{
		return null;
	}
}
