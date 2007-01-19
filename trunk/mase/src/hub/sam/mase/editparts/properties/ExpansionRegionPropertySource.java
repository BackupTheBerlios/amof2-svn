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

package hub.sam.mase.editparts.properties;

import java.util.Set;

import org.eclipse.ui.views.properties.*;

import hub.sam.mase.m2model.ExpansionRegion;
import hub.sam.mase.m2model.ExpansionKind;

public class ExpansionRegionPropertySource extends CommentedNodePropertySource {

    private final ExpansionRegion model;
    private enum PROPERTY_ID {MODE};
    
    public ExpansionRegionPropertySource(ExpansionRegion model) {
        super(model);
        this.model = model;
    }

    protected Set<IPropertyDescriptor> getRawPropertyDescriptors() {
        if (rawDescriptors == null) {
            Set<IPropertyDescriptor> rawDescriptors = super.getRawPropertyDescriptors();
    
            String[] comboBoxLabels= new String[ExpansionKind.values().length];
            for(ExpansionKind kind: ExpansionKind.values()) {
                comboBoxLabels[kind.ordinal()] = kind.toString();
            }
            rawDescriptors.add( new ComboBoxPropertyDescriptor(PROPERTY_ID.MODE, "mode", comboBoxLabels) );
        }
        return rawDescriptors;
    }
    
    public void setPropertyValue(Object id, Object val) throws IllegalArgumentException {
        if (id instanceof PROPERTY_ID) {
            switch((PROPERTY_ID) id) {
            case MODE:
                if(val instanceof Integer) {
                    model.setMode(ExpansionKind.values()[(Integer) val]);
                }
                else {
                    throw new java.lang.IllegalArgumentException("val must be an Integer if used in a ComboBox");
                }
                break;
            }
        }
        else {
            super.setPropertyValue(id, val);
        }
    }

    public Object getPropertyValue(Object id) {
        if (id instanceof PROPERTY_ID) {
            switch((PROPERTY_ID) id) {
            case MODE:
                return model.getMode().ordinal();
            }
        }
        else {
            return super.getPropertyValue(id);
        }
        return null;
    }    
    
}
