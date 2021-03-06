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
import hub.sam.mof.ocl.OclImplementationsHelper;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;

import cmof.Property;
import cmof.UmlClass;
import cmof.cmofFactory;

public class EditOclPropertyImplementationAction extends EditOclImplementationAction<Property> {
	
	
	
    public EditOclPropertyImplementationAction() {
		super();	
	}

	@Override
    protected boolean shouldEnable() {
        return currentElement.isDerived() && !currentElement.isDerivedUnion();
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
    
    @Override
    protected String getCurrentOclImplementation() {
    	if (currentElement == null) {
    		return null;
    	}    	
    	return OclImplementationsHelper.getOclImplementation(currentElement);
    }
    
    @Override
    protected void setOclImplementation(String ocl) {
    	MasContext masContext = getMASContextFromSelection();
    	cmofFactory factory = (cmofFactory)masContext.getSyntaxModel().getFactory();
    	OclImplementationsHelper.setOclImplementation(currentElement, factory, ocl);
    }

	@Override
	protected UmlClass getContext() {
		return currentElement.getUmlClass();
	}
    
    
}
