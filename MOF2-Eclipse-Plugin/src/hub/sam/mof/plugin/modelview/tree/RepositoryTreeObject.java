package hub.sam.mof.plugin.modelview.tree;

import hub.sam.mof.Repository;
import hub.sam.mof.plugin.modelview.Images;
import hub.sam.mof.plugin.modelview.ModelView;

import java.util.*;

import org.eclipse.swt.graphics.Image;

public class RepositoryTreeObject extends TreeParent {

	private final Repository repository;	
	
	public RepositoryTreeObject(Repository repository, TreeParent parent, ModelView view) {
		super(repository, parent, view);
		this.repository = repository;
	}

	@Override
	protected Collection<TreeObject> retrieveChildren() {
		super.retrieveChildren();
		Collection<String> extentNames = repository.getExtentNames();
		Collection<TreeObject> result = new ArrayList<TreeObject>(extentNames.size());
		
		for (String extentName: extentNames) {
			result.add(new ExtentTreeObject(repository.getExtent(extentName), extentName, this, getView()));
		}
		return result;
	}

	@Override
	public String getText() {
		return "LocalRepository";
	}	
	
	@Override
	public Repository getElement() {		
		return repository;
	}

	@Override
	public Image getImage() {
		return Images.getDefault().getRepository();
	}	
}
