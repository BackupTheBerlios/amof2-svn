package hub.sam.mof.test.ocl;

import cmof.reflection.Extent;
import warehouse.WarehouseModel;
import hub.sam.mof.Repository;
import hub.sam.mof.management.LoadException;
import hub.sam.mof.management.MofModelManager;
import hub.sam.mof.ocl.OclException;
import hub.sam.mof.ocl.OclHelper;
import hub.sam.mof.reflection.CheckedPrePostConditionsImplementationsManagerImpl;
import hub.sam.mof.reflection.ExtentImpl;
import hub.sam.mof.test.ocl.model.Diagram;
import hub.sam.mof.test.ocl.model.Figure;
import hub.sam.mof.test.ocl.model.FigureType;
import hub.sam.mof.test.ocl.model.modelFactory;
import hub.sam.mof.xmi.Xmi1Reader.XmiKind;
import junit.framework.TestCase;

public class OclTests extends TestCase {
    
    private MofModelManager modelManager = null;

	public OclTests() {
		super("OclTests");
	}

	private MofModelManager getModelManager() {
	    if (modelManager == null) {
	        modelManager = new MofModelManager(Repository.getLocalRepository());
	        try {
                modelManager.loadM2Model("resources/models/test/ocl-tests.mdxml", XmiKind.md, "ocl-tests", "Package:model");
                modelManager.getM2Model().setJavaPackagePrefix("hub.sam.mof.test.ocl");
            }
            catch (LoadException e) {
                e.printStackTrace();
            }
	    }
	    return modelManager;
	}
    
    @Override
	public void setUp() {
        MofModelManager modelManager = getModelManager();
        modelManager.createM1Model("ocl-tests-m1");
        ((ExtentImpl) modelManager.getM1Model().getExtent()).setCustomImplementationsManager(new CheckedPrePostConditionsImplementationsManagerImpl());
    }
    
    @Override
    public void tearDown() {
        getModelManager().getM1Model().close();
        getModelManager().getM2Model().close();
    }
    
    private Diagram getSimpleModel() {
        modelFactory factory = (modelFactory) getModelManager().getM1Model().getFactory();
        Diagram diagram = factory.createDiagram();
        return diagram;
    }
    
    public void testSimple() {
        Diagram diagram = getSimpleModel();
        Figure figure = diagram.addFigure(1, 1, FigureType.RECTANGLE);
        
        OclHelper oclHelper = modelManager.getAdapter(OclHelper.class);
        oclHelper.addInvariant(diagram.getMetaClass(), "self.figure->size() = 1");
        assertTrue(oclHelper.checkAllInvariants());
        
        assertTrue(diagram.containsFigure(1, 1, FigureType.RECTANGLE));
        
        diagram.removeFigure(figure);
        assertFalse(diagram.containsFigure(1, 1, FigureType.RECTANGLE));
	}
    
    public void testParameters() {
        OclHelper oclHelper = modelManager.getAdapter(OclHelper.class);

        Diagram diagram = getSimpleModel();
        Figure figure = diagram.addFigure(1, 1, FigureType.RECTANGLE);
        
        oclHelper.addPrecondition(diagram.getMetaClass(), "removeFigure", "self.figure->includes(f)");
        try {
            diagram.removeFigure(figure);
        }
        catch (OclException ex) {
            fail(ex.getMessage());
        }
        assertFalse(diagram.containsFigure(1, 1, FigureType.RECTANGLE));
    }
    
    public void testParameters2() {
        OclHelper oclHelper = modelManager.getAdapter(OclHelper.class);

        Diagram diagram = getSimpleModel();
        Figure figure = diagram.addFigure(1, 1, FigureType.RECTANGLE);
        
        oclHelper.addPrecondition(diagram.getMetaClass(), "removeFigure", "self.figure->collect(e | e.type)->includes(f.type)");
        try {
            diagram.removeFigure(figure);
        }
        catch (OclException ex) {
            fail(ex.getMessage());
        }
        assertFalse(diagram.containsFigure(1, 1, FigureType.RECTANGLE));
    }

    // TODO also use a parameter of type classifier
    public void testModelOperations() {
        Diagram diagram = getSimpleModel();
        
        OclHelper oclHelper = modelManager.getAdapter(OclHelper.class);
        oclHelper.addPrecondition(diagram.getMetaClass(), "addFigure", "self.containsFigure(x,y,type) = false");

        diagram.addFigure(1, 1, FigureType.RECTANGLE);
        
        try {
            diagram.addFigure(1, 1, FigureType.RECTANGLE);
        }
        catch (OclException ex) {
            return;
        }
        fail("expected OclException");
    }
    
    // TODO test the use of 'result' in the postconditions of two model operations from the meta-model class that
    // depend on each other.
    public void xxxtestModelOperationsWithResultInPostcondition() {
        
    }
    
    // TODO use runtime instance as context attribute type
    public void xxxtestAdditionalContextAttributeOfTypeRuntime() {
        
    }
    
    // TODO see below
    public void xxxtestAdditionalPropertyNameConflict() {
        Diagram diagram = getSimpleModel();
        Figure figure = diagram.addFigure(1, 1, FigureType.RECTANGLE);
        
        OclHelper oclHelper = modelManager.getAdapter(OclHelper.class);
        // the second 'figure' (parameter of operation removeFigure) is added as an additional property
        // which overwrites access to the attribute 'figure' of diagram.
        oclHelper.addPrecondition(diagram.getMetaClass(), "removeFigure", "self.figure->includes(figure) = false");

        try {
            diagram.removeFigure(figure);
        }
        catch (OclException ex) {
            fail("caught OclException");
        }
    }
    
}
