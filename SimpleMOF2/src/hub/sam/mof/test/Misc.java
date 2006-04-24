package hub.sam.mof.test;

import cmof.Operation;
import cmof.Parameter;
import cmof.cmofFactory;
import cmof.reflection.Extent;

import java.util.Iterator;

public class Misc extends AbstractRepository {

    public Misc() {
        super("misc");
    }

    private cmofFactory factory = null;
    private Extent extent = null;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        extent = repository.createExtent("test");
        factory = (cmofFactory)repository.createFactory(extent, m3);
    }

    public void testParameterOrder() throws Exception {
        Operation op = factory.createOperation();
        op.setName("op");
        for (int i = 0; i < 50; i++) {
            Parameter param = factory.createParameter();
            param.setName("p" + i);
            op.getOwnedParameter().add(param);
        }

        for (int i = 0; i < 50; i++) {
            assertTrue(op.getOwnedParameter().get(i).getName().equals("p" + i));
        }

        repository.writeExtentToXmi("resources/models/work/ParameterOrderTest.xml", m3, extent);
        Extent extentReloaded = repository.createExtent("reloaded");
        repository.loadXmiIntoExtent(extentReloaded, m3, "resources/models/work/ParameterOrderTest.xml");

        Operation opreloaded = (Operation)extentReloaded.query("Operation:op");
        Iterator<? extends core.basic.Parameter> it = opreloaded.getOwnedParameter().iterator();
        core.basic.Parameter p;
        for (int i = 0; i < 50; i++) {
            p = it.next();
            assertTrue(p.getName().equals("p" + i));
        }
    }
}
