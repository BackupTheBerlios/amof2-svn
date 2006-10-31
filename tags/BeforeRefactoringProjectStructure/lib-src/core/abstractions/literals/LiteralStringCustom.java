/*
A MOF 2 Java -- The MOF Repository tool for Java
Copyright (C) 2005 Markus Scheidgen

    This library is free software; you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as published by the
Free Software Foundation; either version 2.1 of the License, or any later
version.

    This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.

    You should have received a copy of the GNU Lesser General Public License
along with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
*/

package core.abstractions.literals;

import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.reflection.ExtentImpl;
import hub.sam.mof.reflection.Identifier;

public class LiteralStringCustom extends LiteralStringDlg {

	public LiteralStringCustom(ClassInstance instance, ExtentImpl extent) {
		super(instance, extent);
	}

	public LiteralStringCustom(Identifier id, Identifier metaId,
			String implementationClassName, String[] delegateClassNames) {
		super(id, metaId, implementationClassName, delegateClassNames);
	}
	
	/**
	 * The query isComputable() is redefined to be true.
	 */
	
	public boolean isComputable() {
		return true;
	}

    /**
     * <b>stringValue</b>, multiplicity=(0,1)
     * 
     * The query stringValue() gives the value.
     */
    public String stringValue() {
    	return getValue();
    }

}
