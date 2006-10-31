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

package hub.sam.mof;

import cmof.reflection.Extent;

public class ExampleForXmi1Uml1Import {

    public static void main(String[] args) throws Exception {
        System.out.println("init");
        Repository facility = new Repository();
        Extent mof = facility.getExtent(Repository.CMOF_EXTENT_NAME);
        cmof.Package cmofPackage = (cmof.Package)mof.query("Package:cmof");
        if (cmofPackage == null) {
            throw new Exception("Bad M3 model.");
        }
                        
        Extent myExtent = facility.createExtent("myExtent");   
        facility.loadUnisysXmiIntoExtent(myExtent, cmofPackage, "convert-xmi/test-files/CeeJay.xml");
        
        System.out.println("generate code");
        facility.generateCode(myExtent, "test-out");
    }
}
