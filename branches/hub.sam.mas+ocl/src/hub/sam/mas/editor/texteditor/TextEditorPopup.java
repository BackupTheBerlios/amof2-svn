/***********************************************************************
 * MASE -- MOF Action Semantics Editor
 * Copyright (C) 2007 Andreas Blunk
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301  USA
 ***********************************************************************/

package hub.sam.mas.editor.texteditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;


public class TextEditorPopup {
	protected Composite fShell;
	private Control fTextEditorControl;
	private Object fTextEditor;
	protected final DirectEditorSubjectControlAdaptor fSubjectControlAdaptor;
	
	private String value = null;
	private IValueAdaptor valueAdaptor = null;
	
	public TextEditorPopup(DirectEditor textEditor, IEditorPart editorPart) {		
		this.fSubjectControlAdaptor = new DirectEditorSubjectControlAdaptor(editorPart);
	}
	
	public void showEditor(int x, int y) {
		createEditor(x, y);
		displayEditor();
	}
	
	protected Object createTextEditor() {
		Text textEditor = new Text(fShell, SWT.H_SCROLL | SWT.V_SCROLL);
		textEditor.setText(getValue());
		if (!"carbon".equals(SWT.getPlatform())) {
			textEditor.setBackground(fShell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		}
		return textEditor;
	}
	
	protected Control getControl(Object textEditor) {
		return (Control)textEditor;
	}
	
	protected String getTextEditorContents(Object textEditor) {
		if (textEditor != null) {
			return ((Text)textEditor).getText();
		} else {
			return "ERROR";
		}
	}
	
	private void createEditor(int x, int y) {
		if (Helper.okToUse(fShell))
			return;
		
		Control control = fSubjectControlAdaptor.getControl();		
		fShell= new Shell(control.getShell(), SWT.RESIZE );		
		fShell.setLayout(new FillLayout());
		fShell.setLocation(new Point(x,y));		
				
		fTextEditor =  createTextEditor();
		fTextEditorControl = getControl(fTextEditor);		
		
		fShell.pack();
		fShell.setSize(500, 300);
				
		fShell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				valueAdaptor.setValue(getTextEditorContents(fTextEditor));
			}			
		});
	}	
	
	private void displayEditor() {
		if (!Helper.okToUse(fShell) ||  !Helper.okToUse(fTextEditorControl))
			return;		
		
		fShell.setVisible(true); // may run event loop on GTK			
		fShell.setFocus(); // may run event loop on GTK ??		
	}
	
	/**
	 * Hides this popup.
	 */
	public void hideEditor() {				
		if (Helper.okToUse(fShell)) {						
			fShell.setVisible(false);
			fShell.dispose();
			fShell= null;
		}		
	}
	
	protected String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setValueAdaptor(IValueAdaptor valueAdaptor) {
		this.valueAdaptor = valueAdaptor;
	}
		
	public void setContext(Object context) {
		
	}
	
}
