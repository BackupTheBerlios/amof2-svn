package hub.sam.mof.test;

import cmof.reflection.Extent;
import cmof.Property;
import warehouse.WarehouseModel;

public class QualifierTest extends AbstractRepository{

    public QualifierTest() {
        super("qualifier tests");
    }

    private Extent m2Extent = null;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        m2Extent = WarehouseModel.createModel();
        repository.loadMagicDrawXmiIntoExtent(m2Extent, m3, "resources/models/test/qualifier-test.mdxml");
    }

    public void testImport() throws Exception {
        Property superAttr = (Property)m2Extent.query("Package:QualifierTest/Class:Source/Property:superAttr");
        assertNotNull(superAttr.getQualifier());
        assertEquals(superAttr.getQualifier().getName(), "access");
    }

}
