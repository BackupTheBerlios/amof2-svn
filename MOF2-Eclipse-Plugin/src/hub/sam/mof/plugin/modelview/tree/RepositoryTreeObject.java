package hub.sam.mof.plugin.modelview.tree;

import hub.sam.mof.plugin.modelview.ObjectKind;
import hub.sam.mof.reflection.client.ClientRepository;
import java.util.*;

public class RepositoryTreeObject extends TreeParent {

	private final ClientRepository repository;	
	
	public RepositoryTreeObject(ClientRepository repository, TreeParent parent) {
		super(repository, parent);
		this.repository = repository;
	}

	@Override
	protected Collection<TreeObject> retrieveChildren() {
		Collection<String> extentNames = repository.getExtentNames();
		Collection<TreeObject> result = new ArrayList<TreeObject>(extentNames.size());
		
		for (String extentName: extentNames) {
			result.add(new ExtentTreeObject(repository.getExtent(extentName), extentName, this));
		}
		return result;
	}

	@Override
	public String getText() {
		return "Repository";
	}	
	
	@Override
	public ObjectKind getKind() {
		return ObjectKind.Repository;
	}
}
