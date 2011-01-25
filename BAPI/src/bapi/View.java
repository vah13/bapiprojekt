package bapi;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import bapi.views.SAPProxy;

public class View extends ViewPart {

	private static TableViewer viewer;
	private TableViewerColumn tvc;
	private Text routerString;
	private Label status;
	private Label status_Value;
	private MyContentProvider myContProv = new MyContentProvider();
	public static ArrayList<String> bapis = new ArrayList<String>();

	public void createPartControl(Composite parent) {
		GridLayout gl = new GridLayout();
		gl.numColumns = 4;
		parent.setLayout(gl);

		new Label(parent, SWT.NONE).setText("Router-String:");

		routerString = new Text(parent, SWT.SINGLE | SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL, GridData.CENTER, true,
				false);
		gridData.horizontalSpan = 2;
		routerString.setLayoutData(gridData);

		Button button_Connect = new Button(parent, SWT.PUSH);
		GridData gridData2 = new GridData(GridData.FILL, GridData.CENTER, true,
				false);
		button_Connect.setLayoutData(gridData2);
		button_Connect.setText("Verbinden");
		button_Connect.addListener(SWT.Activate, new Listener() {

			@Override
			public void handleEvent(Event event) {
				status_Value.setText("Verbunden");

				SAPProxy proxy = new SAPProxy();
				String[] bapisa = proxy.getBAPIList();
				for (int i = 0; i < bapisa.length/* 3349 */; i++) {
					bapis.add(bapisa[i]);
				}
				viewer.setInput(bapis);
				// viewer.setInput(bapis);
				// viewer.addFilter(new ViewerFilter() {
				//
				// @Override
				// public boolean select(Viewer viewer, Object parentElement,
				// Object element) {
				// TODO Auto-generated method stub
				// myContProv.getElements(bapis);
				// for(int i = 0; i < bapis.length; i++)
				// element = bapis[i];
				// return false;
			}
		});
		// }
		// });

		status = new Label(parent, SWT.NONE);
		status.setText("Status: ");

		status_Value = new Label(parent, SWT.NONE);
		GridData gridData3 = new GridData(GridData.FILL, GridData.CENTER, true,
				false);
		gridData3.horizontalSpan = 3;
		status_Value.setLayoutData(gridData3);
		status_Value.setText("Nicht Verbunden!");

		Label nichts = new Label(parent, SWT.NONE);
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

		Label bapi_Übersicht = new Label(parent, SWT.NONE);
		GridData gridData5 = new GridData(GridData.FILL, GridData.CENTER, true,
				false);
		gridData5.horizontalSpan = 4;
		bapi_Übersicht.setText("BAPI-Übersicht");
		bapi_Übersicht.setLayoutData(gridData5);

		Composite c = new Composite(parent, SWT.NONE);
		c.setLayout(new GridLayout());
		// GridData gridData6 = new GridData(GridData.FILL_BOTH,
		// GridData.FILL_VERTICAL, true,
		// false);
		// gridData6.horizontalSpan = 4;
		GridData rd = new GridData(SWT.FILL, SWT.FILL, true, true);
		rd.horizontalSpan = 4;
		c.setLayoutData(rd);

		viewer = new TableViewer(c, SWT.H_SCROLL | SWT.V_SCROLL);
		tvc = new TableViewerColumn(viewer, SWT.BORDER);
		tvc.getColumn().setText("BAPI");
		tvc.getColumn().setWidth(750);
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		viewer.setContentProvider(myContProv);
		// viewer.setContentProvider(new ArrayContentProvider());
		// viewer.setSorter(new ViewerSorter());
		viewer.setLabelProvider(new ViewLabelProvider());
		// viewer.setInput(getViewSite());

		GridData gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;

		viewer.getControl().setLayoutData(gd);
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
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
		// return new String[] { "Hier", "werden", "unsere", "BAPI´s",
		// "aufgelistet", "!", "Hier", "werden", "unsere", "BAPI´s",
		// "aufgelistet", "!", "Hier", "werden", "unsere", "BAPI´s",
		// "aufgelistet", "!" };

		// ArrayList list = new ArrayList();
		// list.addAll(Arrays.asList(View.bapis));
		String[] o = new String[View.bapis.size()];
		for (int i = 0; i < o.length; i++) {
			o[i] = View.bapis.get(i);
		}
		return o;
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
return img;
//		return PlatformUI.getWorkbench().getSharedImages()
//				.getImage("C:/alt_window_16.gif");
	}
}