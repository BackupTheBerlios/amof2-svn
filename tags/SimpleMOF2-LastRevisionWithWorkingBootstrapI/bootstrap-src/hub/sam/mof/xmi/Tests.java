package hub.sam.mof.xmi;
/*
import hub.sam.mof.instancemodel.*;
import hub.sam.mof.instancemodel.conversion.Converter;
import hub.sam.mof.Repository;
import cmof.*;
import cmof.reflection.*;
*/
public class Tests extends junit.framework.TestCase {
/*    private InstanceModel<XmiClassifier,String,String> model = null;
    private XmiReader reader = null;
    private Converter<XmiClassifier,String,String,UmlClass,Property,Type,DataType,java.lang.Object> converter = null;
    private Repository repository = new Repository();
    
    public Tests(String name) {
        super(name);
    }
    
    public void setUp() throws Exception {
        model = new InstanceModel<XmiClassifier,String,String>();
        reader = new XmiReader(model);
        
        Extent m3Extent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
        Extent m2Extent = repository.createExtent("m2Extent", (cmof.Package)m3Extent.query("Package:cmof"));
        converter = new Converter<XmiClassifier,String,String,UmlClass,Property,Type,DataType,java.lang.Object>(
                new XmiToCMOF(m2Extent,(cmof.Package)m3Extent.query("Package:cmof")));        
    }
   
    public void testXmiRead() throws Exception {
        reader.read(new java.io.File("models/b4-model2.xmi"));
        converter.convert(model, new InstanceModel<UmlClass,Property,java.lang.Object>());
    }
*/
}
