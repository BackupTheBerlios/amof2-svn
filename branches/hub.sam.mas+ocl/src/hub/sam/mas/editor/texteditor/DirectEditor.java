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

import org.eclipse.gef.EditPart;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;

public class DirectEditor {

	/**
	 * A generic closer class used to monitor various interface events in order to determine whether
	 * content-assist should be terminated and all associated windows closed.
	 */
	class Closer implements ControlListener, MouseListener, DisposeListener {

		/** The shell that a <code>ControlListener</code> is registered with. */
		private Shell fShell;
		/**
		 * The control that a <code>MouseListener</code>, a<code>FocusListener</code> and a
		 * <code>DisposeListener</code> are registered with.
		 */
		private Control fControl;

		/**
		 * Installs this closer on it's viewer's text widget.
		 */
		protected void install() {
			Control control= fSubjectControlAdaptor.getControl();
			fControl= control;
			if (Helper.okToUse(control)) {

				Shell shell= control.getShell();
				fShell= shell;
				shell.addControlListener(this);

				control.addMouseListener(this);

				/*
				 * 1GGYYWK: ITPJUI:ALL - Dismissing editor with code assist up causes lots of
				 * Internal Errors
				 */
				control.addDisposeListener(this);
			}
		}

		/**
		 * Uninstalls this closer from the viewer's text widget.
		 */
		protected void uninstall() {
			Control shell= fShell;
			fShell= null;
			if (Helper.okToUse(shell))
				shell.removeControlListener(this);

			Control control= fControl;
			fControl= null;
			if (Helper.okToUse(control)) {

				control.removeMouseListener(this);

				/*
				 * 1GGYYWK: ITPJUI:ALL - Dismissing editor with code assist up causes lots of
				 * Internal Errors
				 */
				control.removeDisposeListener(this);
			}	
		}

		/*
		 * @see ControlListener#controlResized(ControlEvent)
		 */
		public void controlResized(ControlEvent e) {
			hide();
		}

		/*
		 * @see ControlListener#controlMoved(ControlEvent)
		 */
		public void controlMoved(ControlEvent e) {
			hide();
		}

		/*
		 * @see MouseListener#mouseDown(MouseEvent)
		 */
		public void mouseDown(MouseEvent e) {
			hide();
		}

		/*
		 * @see MouseListener#mouseUp(MouseEvent)
		 */
		public void mouseUp(MouseEvent e) {
		}

		/*
		 * @see MouseListener#mouseDoubleClick(MouseEvent)
		 */
		public void mouseDoubleClick(MouseEvent e) {
			hide();
		}

		/*
		 * @see FocusListener#focusGained(FocusEvent)
		 */
		public void focusGained(FocusEvent e) {
		}

		/*
		 * @seeDisposeListener#widgetDisposed(DisposeEvent)
		 */
		public void widgetDisposed(DisposeEvent e) {
			/*
			 * 1GGYYWK: ITPJUI:ALL - Dismissing editor with code assist up causes lots of Internal
			 * Errors
			 */
			hide();
		}	
	}
	
	private final TextEditorPopup fTextEditorPopup;
	private final DirectEditorSubjectControlAdaptor fSubjectControlAdaptor;	
	//private final Closer fCloser = new Closer();
	
	public DirectEditor(IEditorPart editorPart) {		
		this.fSubjectControlAdaptor = new DirectEditorSubjectControlAdaptor(editorPart);
		this.fTextEditorPopup = new TEFTextEditorPopup(this, editorPart);
	}
	
	public void addToLayout() {
		
	}
	
	public void hide() {
		fTextEditorPopup.hideEditor();
		//fCloser.uninstall();
	}
	
	public void run(int x, int y, String value, IValueAdaptor valueAdaptor, Object context) {
		//fCloser.install();
		fTextEditorPopup.setContext(context);
		fTextEditorPopup.setValue(value);
		fTextEditorPopup.setValueAdaptor(valueAdaptor);
		fTextEditorPopup.showEditor(x, y);
	}
}
