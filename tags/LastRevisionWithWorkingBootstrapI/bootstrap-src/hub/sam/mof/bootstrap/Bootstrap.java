package hub.sam.mof.bootstrap;

import core.abstractions.elements.Element;
import cmofimpl.codegeneration.Analyser;
import cmofimpl.codegeneration.PackageGenerator;
import cmofimpl.codegeneration.StreamFactory;
import hub.sam.mof.instancemodel.*;
import hub.sam.mof.instancemodel.conversion.Converter;
import hub.sam.mof.xmi.*;

public class Bootstrap {    
    public static void main(String[] args) throws Exception {
        InstanceModel<XmiClassifier,String,String> xmiModel = new InstanceModel<XmiClassifier,String,String>();
        XmiReader xmiReader = new XmiReader(xmiModel);
        System.out.println("parsing m3 xmi");
        xmiReader.read(new java.io.File("models/b4-model2.xmi"));
        InstanciatedXmiModel instantiatedXmiModel = new InstanciatedXmiModel(xmiModel);
        Instantiation conversion = new Instantiation(xmiModel, instantiatedXmiModel);
        Converter<XmiClassifier,String,String,
                ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,
                ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,java.lang.Object> 
                converterForInstantiation = new Converter<XmiClassifier,String,String,
                        ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,
                        ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,java.lang.Object>(
                        conversion);
        System.out.println("instantiating xmi based model");
        converterForInstantiation.convert(xmiModel, instantiatedXmiModel);     
        
        SelfClassification selfClassification = new SelfClassification();
        Converter<ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,Object,
                ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,
                ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,java.lang.Object> 
                converterForSelfClassification = new Converter<ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,Object,
                        ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,
                        ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,java.lang.Object>(selfClassification);
        selfClassification.setConverter(converterForInstantiation,converterForSelfClassification);
        BootstrapModel bootstrapModel = new BootstrapModel();
        System.out.println("create self classification");
        converterForSelfClassification.convert(instantiatedXmiModel, bootstrapModel);
        
        bootstrapModel.setPropertyNames(selfClassification.getPropertyNames());
        bootstrapModel.setDependendProperties();
        System.out.println("create mof instance of mof");
        BootstrapExtent extent = new BootstrapExtent(bootstrapModel);
        
        
        /*for(cmof.reflection.Object o: extent.getObject()) {
            if (o instanceof cmof.Package) {
                if (((cmof.Package)o).getName().equals("cmof")) {
                    System.out.println("---" + ((cmof.Package)o).getOwnedMember());
                    System.out.println("!!!" + ((cmof.Package)o).getOwnedElement());
                }
            }
        }*/
        System.out.println("analyse mof m3");
        new Analyser().analyse(extent);
        System.out.println("generate mof repository");
        StreamFactory streamFactory = new StreamFactory("./bootstrap2-test/");
        for (Element element: extent.objectsOfType(null, true)) {                      
            if (element instanceof cmof.Package) {
                if (((cmof.Package)element).getOwner() == null) {                    
                    new PackageGenerator(streamFactory, false).generate(new java.util.Vector<String>(), (cmof.Package)element);    
                }                
            }
        }            
        
        System.out.println("generate static mof model");            
        cmofimpl.reflection.ExtentImpl.writeStaticModel("./bootstrap2-test/cmof/" + extent.getName() + ".java", "cmof", extent);
    }
}
