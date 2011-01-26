package bapi;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import bapi.views.SAPProxy;
import bapi.views.SAPSession;

public class View extends ViewPart {

	private static TableViewer viewer;
	private TableViewerColumn tvc;
	private Label status;
	private Label status_Value;

	private Text tClient;
	private Text tUser;
	private Text tPW;
	private Text tPeakLim;
	private Text tLang;
	private Text tAsHost;
	private Text tSysNr;
	private Text tPoolCap;
	private Combo combo;
	SAPSession logon;
	String[] bapisa;
	static String[] temp = { "3", "1", "2", "3", "1", "2", "abc3def" };
	Text tSearch;
	BapiFilter filter = new BapiFilter();

	private MyContentProvider myContProv = new MyContentProvider();
	public static ArrayList<String> bapis = new ArrayList<String>();

	public String getSearchString() {
		return tSearch.getText();
	}

	public void fillInTable(String clnt, String user, String pass, String lang,
			String host, String sysn, String capt, String limt) {

		try {
			logon = new SAPSession(clnt, user, pass, lang, host, sysn, capt,
					limt);
			status_Value.setText("Verbindung mit " + host
					+ " wurde hergestellt!");
			Thread.sleep(1500);
			SAPProxy proxy = new SAPProxy();
			Long start = System.currentTimeMillis();
			status_Value.setText("BAPI-Liste wird abgerufen...");
			proxy.createBAPIList();
			Long end = System.currentTimeMillis();
			Long sec = (end - start);
			status_Value.setText("BAPI-Liste wurde geladen in "
					+ sec.toString() + " ms");
			bapisa = proxy.getBapiTable();
			for (int i = 0; i < 2750; i++) { // PROBLEM ---- bapisa.length;
				bapis.add(bapisa[i]);
			}
			viewer.setInput(bapisa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getBapi() {
		String s = "test";
		return s;
	}

	Composite comp = null;

	public void createPartControl(Composite parent) {
		ScrolledComposite sc = new ScrolledComposite(parent, SWT.V_SCROLL
				| SWT.H_SCROLL);
		comp = new Composite(sc, SWT.NONE);
		GridLayout gl = new GridLayout();
		gl.numColumns = 4;
		comp.setLayout(gl);
		sc.setContent(comp);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setMinSize(comp.computeSize(500, 500));
		
		Label lConnectionType = new Label(comp, SWT.NONE);
		lConnectionType.setText("Verbindungsart: ");
		GridData gridData_lConnectionType = new GridData(GridData.BEGINNING,
				GridData.CENTER, true, false);
		gridData_lConnectionType.horizontalSpan = 3;
		lConnectionType.setLayoutData(gridData_lConnectionType);

		
		combo = new Combo(comp, SWT.BEGINNING);
		GridData gridData_Combo = new GridData(GridData.BEGINNING,
				GridData.CENTER, true, false);
		combo.add("JCo 2");
		combo.add("JCo 3");
		gridData_Combo.horizontalSpan = 1;
		combo.setLayoutData(gridData_Combo);

		Label lClient = new Label(comp, SWT.NONE);
		lClient.setText("Client:");
		GridData gridData_lClient = new GridData(GridData.BEGINNING,
				GridData.CENTER, true, false);
		gridData_lClient.horizontalSpan = 3;
		lClient.setLayoutData(gridData_lClient);

		tClient = new Text(comp, SWT.NONE);
		tClient.setText("501");
		GridData gridData_tClient = new GridData(GridData.FILL,
				GridData.CENTER, true, false);
		gridData_tClient.horizontalSpan = 1;
		tClient.setLayoutData(gridData_tClient);

		Label lUser = new Label(comp, SWT.NONE);
		lUser.setText("User:");
		GridData gridData_lUser = new GridData(GridData.BEGINNING,
				GridData.CENTER, true, false);
		gridData_lUser.horizontalSpan = 3;
		lUser.setLayoutData(gridData_lUser);

		tUser = new Text(comp, SWT.NONE);
		tUser.setText("devk-100");
		GridData gridData_tUser = new GridData(GridData.FILL, GridData.CENTER,
				true, false);
		gridData_tUser.horizontalSpan = 1;
		tUser.setLayoutData(gridData_tUser);

		Label lPW = new Label(comp, SWT.NONE);
		lPW.setText("Passwort:");
		GridData gridData_lPW = new GridData(GridData.BEGINNING,
				GridData.CENTER, true, false);
		gridData_lPW.horizontalSpan = 3;
		lPW.setLayoutData(gridData_lPW);

		tPW = new Text(comp, SWT.PASSWORD);
		tPW.setText("b34a5p1i");
		GridData gridData_tPW = new GridData(GridData.FILL, GridData.CENTER,
				true, false);
		gridData_tPW.horizontalSpan = 1;
		tPW.setLayoutData(gridData_tPW);

		Label lLang = new Label(comp, SWT.NONE);
		lLang.setText("Sprache:");
		GridData gridData_lLang = new GridData(GridData.BEGINNING,
				GridData.CENTER, true, false);
		gridData_lLang.horizontalSpan = 3;
		lLang.setLayoutData(gridData_lLang);

		tLang = new Text(comp, SWT.NONE);
		tLang.setText("DE");
		GridData gridData_tLang = new GridData(GridData.FILL, GridData.CENTER,
				true, false);
		gridData_tLang.horizontalSpan = 1;
		tLang.setLayoutData(gridData_tLang);

		Label lAsHost = new Label(comp, SWT.NONE);
		lAsHost.setText("Host:");
		GridData gridData_lAsHost = new GridData(GridData.FILL,
				GridData.CENTER, true, false);
		gridData_lAsHost.horizontalSpan = 3;
		lAsHost.setLayoutData(gridData_lAsHost);

		tAsHost = new Text(comp, SWT.NONE);
		tAsHost.setText("/H/saprouter2.hcc.uni-magdeburg.de/S/3299/H/M14Z");
		GridData gridData_tAsHost = new GridData(GridData.FILL,
				GridData.CENTER, true, false);
		gridData_tAsHost.horizontalSpan = 1;
		tAsHost.setLayoutData(gridData_tAsHost);

		Label lSysNr = new Label(comp, SWT.NONE);
		lSysNr.setText("System-Nummer:");
		GridData gridData_lSysNr = new GridData(GridData.BEGINNING,
				GridData.CENTER, true, false);
		gridData_lSysNr.horizontalSpan = 3;
		lSysNr.setLayoutData(gridData_lSysNr);

		tSysNr = new Text(comp, SWT.NONE);
		tSysNr.setText("14");
		GridData gridData_tSysNr = new GridData(GridData.FILL, GridData.CENTER,
				true, false);
		gridData_tSysNr.horizontalSpan = 1;
		tSysNr.setLayoutData(gridData_tSysNr);

		Label lPoolCap = new Label(comp, SWT.NONE);
		lPoolCap.setText("Kapazität:");
		GridData gridData_lPoolCap = new GridData(GridData.BEGINNING,
				GridData.CENTER, true, false);
		gridData_lPoolCap.horizontalSpan = 3;
		lPoolCap.setLayoutData(gridData_lPoolCap);

		tPoolCap = new Text(comp, SWT.NONE);
		tPoolCap.setText("3");
		GridData gridData_tPoolCap = new GridData(GridData.FILL,
				GridData.CENTER, true, false);
		gridData_tPoolCap.horizontalSpan = 1;
		tPoolCap.setLayoutData(gridData_tPoolCap);

		Label lPeakLim = new Label(comp, SWT.NONE);
		lPeakLim.setText("Limit:");
		GridData gridData_lPeakLim = new GridData(GridData.BEGINNING,
				GridData.CENTER, true, false);
		gridData_lPeakLim.horizontalSpan = 3;
		lPeakLim.setLayoutData(gridData_lPeakLim);

		tPeakLim = new Text(comp, SWT.NONE);
		tPeakLim.setText("10");
		GridData gridData_tPeakLim = new GridData(GridData.FILL,
				GridData.CENTER, true, false);
		gridData_tPeakLim.horizontalSpan = 1;
		tPeakLim.setLayoutData(gridData_tPeakLim);

		Button button_Connect = new Button(comp, SWT.PUSH);
		GridData gridData2 = new GridData(GridData.BEGINNING, GridData.CENTER,
				true, false);
		gridData2.horizontalSpan = 4;
		button_Connect.setLayoutData(gridData2);
		button_Connect.setText("Verbinden");
		button_Connect.addListener(SWT.Activate, new Listener() {

			@Override
			public void handleEvent(Event event) {
				fillInTable(tClient.getText(), tUser.getText(), tPW.getText(),
						tLang.getText(), tAsHost.getText(), tSysNr.getText(),
						tPoolCap.getText(), tPeakLim.getText());
			}
		});

		status = new Label(comp, SWT.NONE);
		status.setText("Status: ");

		status_Value = new Label(comp, SWT.NONE);
		GridData gridData3 = new GridData(GridData.FILL, GridData.CENTER, true,
				false);
		gridData3.horizontalSpan = 3;
		status_Value.setLayoutData(gridData3);
		status_Value.setText("Nicht Verbunden!");

		Label nichts = new Label(comp, SWT.NONE);
		GridData gridData4 = new GridData(GridData.FILL, GridData.CENTER, true,
				false);
		gridData4.horizontalSpan = 4;
		nichts.setLayoutData(gridData4);

		// Group grp = new Group(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		// GridData gridData5 = new GridData(GridData.FILL, GridData.CENTER,
		// true,
		// false);
		// gridData5.horizontalSpan = 4;
		// grp.setText("BAP Übersicht");
		// grp.setLayoutData(gridData4);

		Label bapi_Übersicht = new Label(comp, SWT.NONE);
		GridData gridData5 = new GridData(GridData.FILL, GridData.CENTER, true,
				false);
		gridData5.horizontalSpan = 1;
		bapi_Übersicht.setText("BAPI-Übersicht");
		bapi_Übersicht.setLayoutData(gridData5);

		final Button sortieren = new Button(comp, SWT.CHECK);
		GridData gridData_Sort = new GridData(GridData.FILL, GridData.CENTER,
				true, false);
		gridData_Sort.horizontalSpan = 1;
		sortieren.setText("Tabelle sortieren");
		sortieren.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if (sortieren.getSelection() == true) {
					viewer.setSorter(new ViewerSorter());
					viewer.refresh();
				} else {
					viewer.getTable().clearAll();
					viewer.setInput(temp);
					viewer.refresh();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		sortieren.setLayoutData(gridData_Sort);

		Label lSearch = new Label(comp, SWT.NONE);
		GridData gridData_Search = new GridData(GridData.FILL, GridData.CENTER,
				true, false);
		gridData_Search.horizontalSpan = 1;
		lSearch.setText("Tabelle filtern:");

		tSearch = new Text(comp, SWT.SEARCH);
		GridData gridData_tSearch = new GridData(GridData.FILL,
				GridData.CENTER, true, false);
		gridData_tSearch.horizontalSpan = 1;

		tSearch.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				filter.setSearchText(tSearch.getText());
				viewer.refresh();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		Composite c = new Composite(comp, SWT.NONE);
		c.setLayout(new GridLayout());
		GridData rd = new GridData(SWT.NONE, SWT.FILL, true, true);
		rd.horizontalSpan = 4;
		c.setLayoutData(rd);

		viewer = new TableViewer(c, SWT.H_SCROLL | SWT.V_SCROLL);
		tvc = new TableViewerColumn(viewer, SWT.BORDER);
		 TableViewerColumn tvc2 = new TableViewerColumn(viewer, SWT.BORDER);
		tvc.getColumn().setText("BAPI");
		tvc.getColumn().setWidth(700);
		 tvc2.getColumn().setText("Auswahl");
		 tvc2.getColumn().setWidth(75);
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		viewer.setContentProvider(myContProv);
		// viewer.setSorter(new ViewerSorter());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(temp);
		viewer.addFilter(filter);
		GridData gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;

		viewer.getControl().setLayoutData(gd);
	}

	// @Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}

class BapiFilter extends ViewerFilter {

	private String searchString;

	public void setSearchText(String s) {
		this.searchString = ".*" + s + ".*";
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true;
		}
		if (((String) element).matches(searchString)) {
			return true;
		}
		return false;
	}
}

class MyContentProvider implements IStructuredContentProvider {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
	}

	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		return (String[]) inputElement;

		// String[] o = new String[View.bapis.size()];
		// for (int i = 0; i < o.length; i++) {
		// o[i] = View.bapis.get(i);
		// }
		// return o;
	}
}

class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {

	public String getColumnText(Object obj, int index) {
		return getText(obj);
	}

	public Image getColumnImage(Object obj, int index) {
		return getImage(obj);
	}

	public Image getImage(Object obj) {
		 ImageData imgd = new ImageData("/bapi.png");
		 Image img = new Image(null, imgd);
		 System.out.println(img.getBounds().width);

		 return img;		 
//		return PlatformUI.getWorkbench().getSharedImages()
//				.getImage(ISharedImages.IMG_TOOL_UP);
	}

}