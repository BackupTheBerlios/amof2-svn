package hub.sam.mof.reflection;

import java.util.List;

import cmof.UmlClass;

public class CheckedPrePostConditionsImplementationsManagerImpl extends ImplementationsManagerImpl {

    @Override
    protected Implementations createImplementations(List<ObjectDlg> delegates, UmlClass forMetaClass) {
        return new CheckedPrePostConditionsImplementationsImpl(delegates, null);
    }

}
