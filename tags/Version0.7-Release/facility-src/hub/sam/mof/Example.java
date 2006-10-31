package hub.sam.mof;

import cmof.reflection.*;
import cmofimpl.reflection.*;

public class Example {

    public static void main(String[] args) throws Exception {
        System.out.println("init");
        Repository facility = new Repository();
        ExtentImpl mof = (ExtentImpl)facility.getExtent("CMOF");
        cmof.Package cmofPackage = (cmof.Package)mof.query("Package:cmof");
        //for(cmof.reflection.Object object: mof.objectsOfType(null, false)) {
        //    if (object instanceof cmof.Package) {
        //        if (((cmof.Package)object).getName().equals("cmof")) {
        //            cmofPackage = (cmof.Package)object;
        //        }
        //    }
        //}
        if (cmofPackage == null) {
            throw new Exception("Bad M3 model.");
        }
       
        
        Extent myExtent = facility.createExtent("myExtent", cmofPackage);
        System.out.println("load xmi");
        facility.loadXmiIntoExtent(myExtent, cmofPackage, "models/b4-model.xmi");
        
        System.out.println("generate code");
        facility.generateCode(myExtent, "test-out");

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
        
        System.out.println("additional tests");
        Extent exampleExtent = facility.createExtent("exampleExtent", cmofPackage);
        cmof.cmofFactory myFactory = (cmof.cmofFactory)facility.createFactory(exampleExtent, cmofPackage);        
        cmof.UmlClass myClass = myFactory.createUmlClass();
        myClass.setName("Auto");        
        facility.writeExtentToXmi("test-out/example-model.xmi", exampleExtent.objectsOfType(null, false), exampleExtent);
    }

}
