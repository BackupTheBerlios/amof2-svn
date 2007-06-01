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

package hub.sam.mas.editor.actions;

import hub.sam.mas.management.MasContext;
import hub.sam.mas.management.MasRepository;
import hub.sam.mof.emf.ECoreModelCache;
import hub.sam.mof.management.SaveException;
import hub.sam.tef.TEFEditor;
import hub.sam.tef.emf.AbstractEMFDocumentProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IElementStateListener;

import cmof.UmlClass;
import cmof.reflection.Extent;
import editortest.emf.ocl.OclDocument;

public abstract class EditOclImplementationAction<Element extends cmof.reflection.Object> extends Mof2ObjectPluginAction<Element> {
    
	private ECoreModelCache modelCache = null;
	
	protected abstract String getCurrentOclImplementation();
	protected abstract void setOclImplementation(String ocl);
	protected abstract UmlClass getContext();
	
    public void run(IAction action) {    	        
        String oldOclImplementation = getCurrentOclImplementation();
        if (oldOclImplementation == null) {
        	oldOclImplementation = "";
        }
        
		File tempFile = null;
		try {
			tempFile = File.createTempFile("mastemp", ".ocl");
			tempFile.createNewFile();
			FileWriter tempFileWriter = new FileWriter(tempFile);
			tempFileWriter.write(oldOclImplementation);
			tempFileWriter.close();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		final File fTempFile = tempFile;
		
		IEditorInput input;
		try {
			input = new FileEditorInput(createTempFile(tempFile));
		} catch (CoreException e1) {
			throw new RuntimeException(e1);
		}			
		
		IEditorPart editorPart = null;
        try {
            editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
            		input, "tef.emf.ocl.texteditor");	            
        }
        catch (PartInitException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
        EditingDomain emfDomain = ((AbstractEMFDocumentProvider)((TEFEditor)editorPart).getDocumentProvider()).getEditingDomain();
		ECoreModelCache model = getModel(currentElement, emfDomain);
		EObject context = model.getContext(getContext());					    
        ((OclDocument)((TEFEditor)editorPart).getDocument()).setContext(context);
        
        ((TEFEditor)editorPart).getDocumentProvider().addElementStateListener(new IElementStateListener() {

			public void elementContentAboutToBeReplaced(Object element) {
				// empty				
			}

			public void elementContentReplaced(Object element) {
				// empty				
			}

			public void elementDeleted(Object element) {
				// empty				
			}

			public void elementDirtyStateChanged(Object element, boolean isDirty) {
				if (!isDirty) {
					String newOclImplementation = getContents(fTempFile);
					setOclImplementation(newOclImplementation);
					try {
						getMASContextFromSelection().getSyntaxModel().save();						
					} catch (SaveException ex) {						
						throw new RuntimeException(ex);
					}
				}					
			}

			public void elementMoved(Object originalElement, Object movedElement) {
				// emtpy				
			}        	
			
			private String getContents(File aFile) {
			    //...checks on aFile are elided
			    StringBuffer contents = new StringBuffer();

			    //declared here only to make visible to finally clause
			    BufferedReader input = null;
			    try {
			      //use buffering, reading one line at a time
			      //FileReader always assumes default encoding is OK!
			      input = new BufferedReader( new FileReader(aFile) );
			      String line = null; //not declared within while loop
			      /*
			      * readLine is a bit quirky :
			      * it returns the content of a line MINUS the newline.
			      * it returns null only for the END of the stream.
			      * it returns an empty String if two newlines appear in a row.
			      */
			      while (( line = input.readLine()) != null){
			        contents.append(line);
			        contents.append(System.getProperty("line.separator"));
			      }
			    }
			    catch (FileNotFoundException ex) {
			      throw new RuntimeException(ex);
			    }
			    catch (IOException ex){
			      throw new RuntimeException(ex);
			    }
			    finally {
			      try {
			        if (input!= null) {
			          //flush and close both "input" and its underlying FileReader
			          input.close();
			        }
			      }
			      catch (IOException ex) {
			        throw new RuntimeException(ex);
			      }
			    }
			    return contents.toString();
			  }
        });
    }
	
	protected ECoreModelCache getModel(cmof.reflection.Object abitraryObject, EditingDomain domain) {
		if (modelCache == null) {
			modelCache = new ECoreModelCache(abitraryObject, domain);
		}
		return modelCache;
	}
	
    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        super.selectionChanged(action, selection);
        if (getCurrentOclImplementation() == null) {
            action.setText("Create OCL implementation");
        }
        else {
            action.setText("Edit OCL implementation");
        }
    }
    
    protected IFile createTempFile(File tempFile) throws CoreException {
    	IWorkspace ws = ResourcesPlugin.getWorkspace();
    	   IProject project = ws.getRoot().getProject("External Files");
    	   if (!project.exists())
    	      project.create(null);
    	   if (!project.isOpen())
    	      project.open(null);    	   
    	   IPath location = new Path(tempFile.getAbsolutePath());
    	   IFile file = project.getFile(location.lastSegment());
    	   file.createLink(location, IResource.NONE, null);    	   
    	   return file;
    }
    
    protected MasContext getMASContextFromSelection() {
    	if (selection != null) {
	        Extent syntaxExtent = currentElement.getExtent();
	        return MasRepository.getInstance().getMasContext(syntaxExtent);
    	} else {
    		return null;
    	}
    }
	
}
