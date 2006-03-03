package cmofimpl.bootstrap;

import java.io.FileInputStream;
import core.abstractions.elements.Element;
import cmof.reflection.*;
import cmofimpl.xmi.*;
import cmofimpl.codegeneration.*;

public class Bootstrap {
 
    Extent b2Model = null;
    XmiReader reader = null;
    StreamFactory streamFactory = null;

    
    private void phase1() throws Exception {
        System.out.println("Bootstrap phase 1");
        b2Model = new cmofimpl.reflection.ExtentImpl("B2Model", "cmofimpl.bootstrap");
        reader = new XmiReader(b2Model, B1M2Model.bootstrapModel());
        try {
            reader.readXmi(new FileInputStream("models/b2-model.xmi"));
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            System.exit(1);
        }
        new Analyser().analyse(b2Model);
        streamFactory = new StreamFactory("./bootstrap2");             
                
        for (Element element: b2Model.objectsOfType(null, true)) {      
            if (element instanceof cmof.Package) {
                if (((cmof.Package)element).getOwner() == null) {                    
                    new PackageGenerator(streamFactory, false).generate(new java.util.Vector<String>(), (cmof.Package)element);    
                }                
            }                        
        }        
    }
    
    private void phase2() throws Exception {
        System.out.println("Bootstrap phase 2");
        Extent b3Model = new cmofimpl.reflection.ExtentImpl("cmofb3");
        cmof.UmlClass umlClass = null;
        cmof.UmlClass property = null;
        for (cmof.reflection.Object object: b2Model.objectsOfType(null, true)) {
            if (object instanceof cmof.UmlClass) {
                cmof.UmlClass search = (cmof.UmlClass)object;
                if (search.getName().equals("Class") && search.getPackage().getName().equals("cmof")) {
                    umlClass = search;
                }
            }
            if (object instanceof cmof.UmlClass) {
                cmof.UmlClass search = (cmof.UmlClass)object;
                if (search.getName().equals("Property") && search.getPackage().getName().equals("cmof")) {
                    property = search;
                }
            }                
        }
        
        
        cmof.Package constructs = null;
        for (java.lang.Object element: b2Model.objectsOfType(null, true)) {
            if (element instanceof cmof.Package) {
                if (((cmof.Package)element).getName().equals("cmof")) {
                    constructs = (cmof.Package)element;
                }
            }
        }
        if (constructs == null) {
            System.err.println("Phase 1 failed");
            System.exit(1);
        }
        reader = new XmiReader(b3Model, constructs);
        try {
            reader.readXmi(new FileInputStream("models/b3-model.xmi"));
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            System.exit(1);
        }
         
        new Analyser().analyse(b3Model);
        streamFactory = new StreamFactory("./bootstrap3");
        for (Element element: b3Model.objectsOfType(null, true)) {                      
            if (element instanceof cmof.Package) {
                if (((cmof.Package)element).getOwner() == null) {                    
                    new PackageGenerator(streamFactory, false).generate(new java.util.Vector<String>(), (cmof.Package)element);    
                }                
            }
        }            
                    
        cmofimpl.reflection.ExtentImpl.writeStaticModel("./bootstrap3/cmof/" + b3Model.getName() + ".java", "cmof", b3Model);                         
    }
    
    private void phase3() throws Exception {        
        System.out.println("Bootstrap phase 3");
        java.lang.Class cmofb3 = Thread.currentThread().getContextClassLoader().loadClass("cmof.cmofb3");
        Extent mof = (Extent)cmofb3.getMethod("createModel", new java.lang.Class[]{String.class}).invoke(null, "cmofb3");
        cmof.Package cmofPackage = null;
        for(cmof.reflection.Object object: mof.objectsOfType(null, false)) {
            if (object instanceof cmof.Package) {
                if (((cmof.Package)object).getName().equals("cmof")) {
                    cmofPackage = (cmof.Package)object;
                }
            }
        }
        if (cmofPackage == null) {
            throw new Exception("Bad M3 model.");
        }
       
        Extent extent = new cmofimpl.reflection.ExtentImpl("cmofb4");
        cmofimpl.xmi.XmiReader reader = new cmofimpl.xmi.XmiReader(extent, mof);        
        reader.readXmi(new java.io.FileInputStream("models/b4-model.xmi"));  
        
        new Analyser().analyse(extent);
        StreamFactory streamFactory = new StreamFactory("./bootstrap4/");
        for (Element element: extent.objectsOfType(null, true)) {                      
            if (element instanceof cmof.Package) {
                if (((cmof.Package)element).getOwner() == null) {                    
                    new PackageGenerator(streamFactory, false).generate(new java.util.Vector<String>(), (cmof.Package)element);    
                }                
            }
        }            
                    
        cmofimpl.reflection.ExtentImpl.writeStaticModel("./bootstrap4/cmof/" + extent.getName() + ".java", "cmof", extent);
    }
    
    public static void main(String[] args) throws Exception {
        int phase = new Integer(args[0]);    
        Bootstrap instance = new Bootstrap();
        
        if (phase >= 1 && phase <= 2) {
            instance.phase1();
        }
        
        if (phase == 2) {
            instance.phase2();
        }
        
        if (phase == 3) {
            instance.phase3();
        }        
    }
}
