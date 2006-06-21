package hub.sam.mof.as.layers;

import cmof.Classifier;
import cmof.Operation;
import cmof.UmlClass;
import cmof.Element;
import cmof.reflection.Factory;
import hub.sam.mof.reflection.Implementation;
import hub.sam.mof.reflection.Implementations;
import hub.sam.mof.reflection.ImplementationsImpl;
import hub.sam.mof.reflection.ImplementationsManagerImpl;
import hub.sam.mof.reflection.ObjectDlg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.HashSet;

public class MultiLevelImplementationsManager extends ImplementationsManagerImpl {

    private final Factory factory;

    public MultiLevelImplementationsManager(Factory factory) {
        super();
        this.factory = factory;
    }

    private Iterable<Operation> collectAllRedefined(Operation op, Collection<Operation> redefineds) {
        for (Operation redefined: op.getRedefinedOperation()) {
            redefineds.add(redefined);
            collectAllRedefined(redefined, redefineds);
        }
        return redefineds;
    }

    protected static Classifier getMetaClassifier(UmlClass aClass) {
        if (aClass.getMetaClassifier() != null) {
            return aClass.getMetaClassifier();
        } else {
            for(Object superClass: aClass.allParents()) {
                if (((UmlClass)superClass).getMetaClassifier() != null) {
                    return ((UmlClass)superClass).getMetaClassifier();
                }
            }
        }
        return null;
    }

    private Map<Object, Implementation> getPredefinedImplementations(UmlClass forMetaClass) {
        Map<Object, Implementation> result = new HashMap<Object, Implementation>();
        if (getMetaClassifier(forMetaClass) != null) {
            for (Element member: forMetaClass.getMember()) {
                if (member instanceof Operation) {
                    Operation operation = (Operation)member;
                    if (operation.getName().equals(M1SemanticModel.getDestroyOperationName(forMetaClass))) {
                        result.put(operation, new DestroyImpl((UmlClass)forMetaClass));
                        for (Operation redefined: collectAllRedefined(operation, new HashSet<Operation>())) {
                            result.put(redefined, new DestroyImpl((UmlClass)forMetaClass));
                        }
                    }
                }
            }
        }
        for (Classifier metaInstance: forMetaClass.getMetaInstances()) {
            for (Operation operation: forMetaClass.getOwnedOperation()) {
                if (operation.getName().equals(M1SemanticModel.getCreateOperationName((UmlClass)metaInstance))) {
                    result.put(operation, new CreateImpl(factory, (UmlClass)metaInstance));
                }
                if (operation.getName().equals(M1SemanticModel.getGenericCreateOperationName((UmlClass)metaInstance))) {
                    result.put(operation, new GenericCreateImpl(factory, (UmlClass)metaInstance));
                }
            }
        }
        return result;
    }

    @Override
    protected Implementations createImplementations(List<ObjectDlg> delegates, UmlClass forMetaClass) {
        return new ImplementationsImpl(delegates, getPredefinedImplementations(forMetaClass));
    }
}
