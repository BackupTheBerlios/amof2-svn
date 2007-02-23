package hub.sam.mof.plugin.modelview.tree;

import hub.sam.mof.plugin.modelview.Images;

import java.util.Collection;
import java.util.Vector;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;

import cmof.reflection.Extent;

public class ExtentTreeObject extends ManTreeObject {

	private String extentName;
	private final Extent extent;
	
	private static final ExtendableFactory factory = new ExtendableFactory();
	
    static {
		factory.addFactory(new StdBuilderFactory());
		factory.addFactory(new CMOFBuilderFactory());
	}
	
	public ExtentTreeObject(Extent extent, String extentName, TreeParent parent, TreeViewer view) {
		super(extent, parent, factory, view);
		this.extent = extent;
		this.extentName = extentName;
	}

	@Override
	protected Collection<TreeObject> retrieveChildren() {
		super.retrieveChildren();
		Collection<TreeObject> result = new Vector<TreeObject>();		
		Collection<cmof.reflection.Object> outermostComposites = new Vector<cmof.reflection.Object>();
		loop: for (cmof.reflection.Object aObject: extent.getObject()) {
			if (aObject == null) {
				continue loop;
			}
			if (aObject.container() == null) {
				outermostComposites.add(aObject);
			}
		}
		for (cmof.reflection.Object aObject: outermostComposites) {
			result.add(build(aObject));
		}
		return result;
	}
	
	@Override
	public String getText() {
		return extentName;
	}
	
	@Override
	public Image getImage() {
		return Images.getDefault().getExtent();
	}
}
