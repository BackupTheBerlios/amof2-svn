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

import cmof.reflection.*;

public class Example {
    public static void main(String[] args) throws Exception {
        System.out.println("init");
        Repository facility = new Repository();
        Extent mof = facility.getExtent(Repository.CMOF_EXTENT_NAME);
        cmof.Package cmofPackage = (cmof.Package)mof.query("Package:cmof");
        if (cmofPackage == null) {
            throw new Exception("Bad M3 model.");
        }
                        
        Extent myExtent = facility.createExtent("myExtent");
        System.out.println("load xmi");
        facility.loadXmiIntoExtent(myExtent, cmofPackage, "models/b4-model2.xmi");        
        
        System.out.println("write xmi");
        facility.writeExtentToXmi("test.xml", cmofPackage, myExtent);
        
        Extent myExtent2 = facility.createExtent("myExtent2");
        System.out.println("reload xmi");
        facility.loadXmiIntoExtent(myExtent2, cmofPackage, "test.xml");
        
        System.out.println("rewrite xmi");
        facility.writeExtentToXmi("test1.xml", cmofPackage, myExtent2);
        
                
        System.out.println("generate code from reread xmi");
        facility.generateCode(myExtent2, "test-out");
        

        //cmof.common.ReflectiveCollection<? extends cmof.reflection.Object> export = new cmofimpl.util.SetImpl<cmof.reflection.Object>();        
        //for(cmof.reflection.Object object: myExtent.objectsOfType(null, false)) {
        //    if (object instanceof cmof.Element) {
        //        if (object.getComposite() == null) {
        //            export.add(object);
        //        }
        //    }
        //}
        
        //System.out.println("write xmi");
        //facility.writeExtentToXmi("test-out/model.xmi", export, mof);
       
        /*
        System.out.println("additional tests");
        Extent exampleExtent = facility.createExtent("exampleExtent", cmofPackage);
        cmof.cmofFactory myFactory = (cmof.cmofFactory)facility.createFactory(exampleExtent, cmofPackage);        
        cmof.UmlClass myClass = myFactory.createUmlClass();
        myClass.setName("Auto");        
        facility.writeExtentToXmi("test-out/example-model.xmi", exampleExtent.objectsOfType(null, false), exampleExtent);
        */
    }

}
