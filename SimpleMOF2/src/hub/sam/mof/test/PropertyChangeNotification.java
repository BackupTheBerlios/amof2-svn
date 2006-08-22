package hub.sam.mof.test;

import warehouse.WarehouseModel;
import warehouse.warehouseFactory;
import cmof.reflection.Extent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PropertyChangeNotification extends AbstractRepository
	implements PropertyChangeListener {

	public PropertyChangeNotification() {
		super("PropertyChangeNotification");
	}
	
    warehouse.warehouseFactory factory = null;
    
    @Override
	public void setUp() throws Exception {
        super.setUp();
        Extent m2Extent = WarehouseModel.createModel();
        factory = (warehouseFactory)repository.createFactory(m2Extent, (cmof.Package)m2Extent.query("Package:warehouse"));
    }
    
    private boolean notified = false;
    
    public void testPropertyChangeNotification() {
        warehouse.ItemImpl i = (warehouse.ItemImpl) factory.createItem();
        i.addListener(this);
        i.setIdentifier("test");
        assertTrue(notified);
    }

	public void propertyChange(PropertyChangeEvent ev) {
		if (ev.getPropertyName() == "identifier") {
			notified = true;
		}
	}

}
