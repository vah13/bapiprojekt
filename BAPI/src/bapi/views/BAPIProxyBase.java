package bapi.views;

//Die Basis Klasse f�r unsere angepasste Proxy Klasse. Sie enth�lt die Service-Methoden um ein BAPI auf dem SAP System aufzurufen
public class BAPIProxyBase 
{
	//Mit dieser Methode wird ein BAPI mit einem gegebenen Namen und einer gegebenen Parameterliste variabler l�nge auf dem SAP System aufgerufen. dieser Vorgang erfolgt synchron!
	protected Object InvokeBAPI( String Name, Object[][] Params)
	{
		return null;
	}
}
