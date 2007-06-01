/***********************************************************************
 * MASE -- MOF Action Semantics Editor
 * Copyright (C) 2007 Markus Scheidgen
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

import hub.sam.mas.editor.IMaseEditorInput;
import hub.sam.mas.editor.MaseEditor;
import hub.sam.mas.management.MasLink;
import hub.sam.mas.model.mas.OpaqueAction;
import hub.sam.mof.emf.ECoreModelCache;
import hub.sam.tef.TEFEditor;
import hub.sam.tef.documents.TEFDocumentProvider;
import hub.sam.tef.emf.AbstractEMFDocumentProvider;

import java.io.File;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.FileEditorInput;

import editortest.emf.ocl.OclDocument;
import editortest.emf.ocl.OclEditor;
import editortest.emf.ocl.OclStringDocumentProvider;

public class TEFTextEditorPopup extends TextEditorPopup {
	
	private OpaqueAction context = null;
	private ECoreModelCache modelCache = null; // TODO should be bound to the maseContext
	
	public TEFTextEditorPopup(DirectEditor textEditor, IEditorPart editorPart) {
		super(textEditor, editorPart);
	}

	@Override
	protected Object createTextEditor() {	
		TEFEditor tefEditor = new OclEditor() {
			@Override
			protected TEFDocumentProvider createDocumentProvider() {
				OclStringDocumentProvider result = new OclStringDocumentProvider();					
				result.setContents(getValue());
				return result;
			}			
		};
		try {				
			try {							
				tefEditor.init(fSubjectControlAdaptor.getSite(), 
						new FileEditorInput(ResourcesPlugin.getWorkspace().getRoot().getFile(
								new Path(File.createTempFile("mastemp", ".ocl").getAbsolutePath()))));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			tefEditor.createPartControl(fShell);
			
			MasLink link = ((IMaseEditorInput)((MaseEditor)fSubjectControlAdaptor.getEditor()).getEditorInput()).getLink();
			EditingDomain emfDomain = ((AbstractEMFDocumentProvider)tefEditor.getDocumentProvider()).getEditingDomain();
			ECoreModelCache model = getModel(link.getOperation(), emfDomain);
			EObject context = model.getContext(link.getOperation().getUmlClass());			
			((OclDocument)tefEditor.getDocument()).setContext(context);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}				
		return tefEditor;
	}
	
	private ECoreModelCache getModel(cmof.reflection.Object abitraryObject, EditingDomain domain) {
		if (modelCache == null) {
			modelCache = new ECoreModelCache(abitraryObject, domain);
		}
		return modelCache;
	}

	protected Control getControl(Object textEditor) {
		return ((TEFEditor)textEditor).getWidget();
	}
	
	@Override
	protected String getTextEditorContents(Object textEditor) {
		return ((TEFEditor)textEditor).getDocument().get();
	}

	@Override
	public void setContext(Object context) {
		this.context = (OpaqueAction)context;
	}	
}
