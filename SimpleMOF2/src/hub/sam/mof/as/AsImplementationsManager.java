package hub.sam.mof.as;

import hub.sam.mof.Repository;
import hub.sam.mof.reflection.Implementations;
import hub.sam.mof.reflection.ImplementationsManager;
import hub.sam.mof.reflection.ObjectDlg;

import java.util.List;

import cmof.reflection.Extent;

public class AsImplementationsManager extends ImplementationsManager {

	private final AsExecutionEnvironment environment;
	
	public AsImplementationsManager(Extent m1Extent, Extent m2Extent, Repository repo) {		
		this.environment = new AsExecutionEnvironment(m1Extent, m2Extent, repo);
	}

	@Override
	protected Implementations createImplementations(List<ObjectDlg> delegates) {
		return new AsImplementations(delegates, environment);
	}
}
