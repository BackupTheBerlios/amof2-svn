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

package hub.sam.mas.editor.editpolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;

import hub.sam.mas.editor.MaseEditDomain;
import hub.sam.mas.model.mas.ValueNode;

public class ValueNodeDirectEditPolicy extends AbstractDirectEditPolicy {

    @Override
    protected Command getDirectEditCommand(DirectEditRequest request) {
        String newValue = (String) request.getCellEditor().getValue();

        if (newValue == null)
            return null;

        MaseEditDomain editDomain = (MaseEditDomain) getHost().getRoot().getViewer().getEditDomain();
        return editDomain.getCommandFactory().createValueNodeDirectEditCommand(newValue, (ValueNode) getHost().getModel());
    }
    
}
