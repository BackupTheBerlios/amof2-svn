package hub.sam.mof.as.layers;

import cmof.Association;
import cmof.Classifier;
import cmof.Operation;
import cmof.Package;
import cmof.Property;
import cmof.UmlClass;
import cmof.cmofFactory;
import cmof.common.ReflectiveCollection;
import cmof.exception.MetaModelException;
import hub.sam.mof.mofinstancemodel.MofClassSemantics;
import hub.sam.mof.mofinstancemodel.MofClassifierSemantics;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

/**
 * Contains functionality to create implicit model elements in the semantic part of a meta-model.
 * This means that you can search in a model for "double-instance" classifiers, create the instance
 * association between them an their corresponding general classifier and create create and delete
 * operations for "double-instance" manipulation.
 */
public class M1SemanticModel {

    private static String CREATE_NAME = "metaCreate";
    private static String DELETE_NAME = "metaDelete";
    public static String INSTANCE_NAME = "metaInstance";
    public static String CLASSIFIER_NAME = "metaClassifier";

    public static String getCreateOperationName(UmlClass instanceClassifier) {
        return CREATE_NAME;
    }

    @SuppressWarnings({"UNUSED_SYMBOL"})
    public static String getDestroyOperationName(UmlClass instanceClassifier) {
        return DELETE_NAME;
    }

    private final cmofFactory factory;

    public M1SemanticModel(cmofFactory factory) {
        super();
        this.factory = factory;
    }

    public void createImplicitElements(Collection<Package> model) {
        List<Classifier> allInstanceClassifier = allInstanceClassifier(model);
        ClassifierParentsComparator cmp = new ClassifierParentsComparator();
        hub.sam.tools.Util.sortHO(allInstanceClassifier, cmp);
        for (Classifier instanceClassifier: allInstanceClassifier) {
            if (instanceClassifier instanceof UmlClass) {
                createImplicitElements((UmlClass)instanceClassifier);
            }
        }
    }

    private void createImplicitElements(UmlClass instanceClassifier) {
        UmlClass classifierClassifier = (UmlClass)instanceClassifier.getMetaClassifier();
        Collection<MofClassSemantics> parentInstanceClassifier = getParentInstanceClassifier(instanceClassifier);
        Collection<MofClassSemantics> parentClassifierClassifier = new HashSet<MofClassSemantics>();
        for(MofClassSemantics parentInstance: parentInstanceClassifier) {
            parentClassifierClassifier.add(MofClassifierSemantics.createNewClassClassifierForUmlClass(
                    (UmlClass)parentInstance.getClassifier().getMetaClassifier()));
        }
        if (parentClassifierClassifier.size() != parentInstanceClassifier.size()) {
            throw new MetaModelException("InstanceOf not allowed in this spezialization context: " + instanceClassifier.toString());
        }

        Operation create = factory.createOperation();
        create.setType(instanceClassifier);
        create.setName(getCreateOperationName(instanceClassifier));
        classifierClassifier.getOwnedOperation().add(create);
        for(MofClassSemantics parent: parentClassifierClassifier) {
            add(create.getRedefinedOperation(), parent.getFinalOperation(CREATE_NAME));
        }

        Operation delete = factory.createOperation();
        delete.setName(getDestroyOperationName(instanceClassifier));
        instanceClassifier.getOwnedOperation().add(delete);
        for(MofClassSemantics parent: parentInstanceClassifier) {
            add(delete.getRedefinedOperation(), parent.getFinalOperation(DELETE_NAME));
        }

        Association instanceOf = factory.createAssociation();
        instanceOf.setName(instanceClassifier.getName() + "InstanceOf");
        instanceClassifier.getPackage().getOwnedType().add(instanceOf);

        Property instance = createProperty(INSTANCE_NAME, instanceClassifier, classifierClassifier, instanceOf, 0, -1);
        for(MofClassSemantics parent: parentClassifierClassifier) {
            add(instance.getRedefinedProperty(),parent.getProperty(INSTANCE_NAME));
        }

        Property classifier = createProperty(CLASSIFIER_NAME, classifierClassifier, instanceClassifier, instanceOf, 0, 1);
        for(MofClassSemantics parent: parentInstanceClassifier) {
            add(classifier.getRedefinedProperty(), parent.getProperty(CLASSIFIER_NAME));
        }
        instance.setOpposite(classifier);
        classifier.setOpposite(instance);
    }

    private void add(ReflectiveCollection col, Object o) {
        if (o == null) {
            throw new MetaModelException("assert");
        }
        col.add(o);
    }

    private Collection<MofClassSemantics> getParentInstanceClassifier(UmlClass instanceClassifier) {
        if (instanceClassifier.getName().equals("SdlProcedureFrame")) {
            System.out.println();
        }
        Collection<MofClassSemantics> parentInstanceClassifier = new HashSet<MofClassSemantics>();
        List<core.abstractions.umlsuper.Classifier> parentClassifier = new Vector<core.abstractions.umlsuper.Classifier>();
        for (core.abstractions.umlsuper.Classifier parent: instanceClassifier.allParents()) {
            parentClassifier.add(parent);
        }
        ClassifierParentsComparator cmp = new ClassifierParentsComparator();
        hub.sam.tools.Util.sortHO(parentClassifier, cmp);
        Collection<core.abstractions.umlsuper.Classifier> forbidden = new HashSet<core.abstractions.umlsuper.Classifier>();
        for (int i = parentClassifier.size() - 1; i >= 0; i--) {
            if (isInstanceClassifier((UmlClass)parentClassifier.get(i))) {
                if (!forbidden.contains(parentClassifier.get(i))) {
                    for(core.abstractions.umlsuper.Classifier parent: parentClassifier.get(i).allParents()) {
                        forbidden.add(parent);
                    }
                    parentInstanceClassifier.add(MofClassifierSemantics.createNewClassClassifierForUmlClass(
                            (UmlClass)parentClassifier.get(i)));
                }
            }
        }
        return parentInstanceClassifier;
    }

    private Property createProperty(
            String name, UmlClass type, UmlClass owner, Association association, int lower, int upper) {
        Property classifier = factory.createProperty();
        classifier.setType(type);
        classifier.setLower(lower);
        classifier.setUpper(upper);
        classifier.setName(name);
        classifier.setUmlClass(owner);
        classifier.setAssociation(association);
        return classifier;
    }

    private List<Classifier> allInstanceClassifier(Iterable<? extends Package> model) {
        List<Classifier> result = new Vector<Classifier>();
        for (Package aPackage: model) {
            result.addAll(allInstanceClassifier(aPackage.getNestedPackage()));
            for (Object member: aPackage.getOwnedMember()) {
                if (member instanceof Classifier) {
                    Classifier aClass = (Classifier)member;
                    if (isInstanceClassifier(aClass)) {
                        result.add(aClass);
                    }
                }
            }
        }

        return result;
    }

    private boolean isInstanceClassifier(Classifier classifier) {
        return classifier.getMetaClassifier() != null;
    }

    class ClassifierParentsComparator implements Comparator<core.abstractions.umlsuper.Classifier> {
        public int compare(core.abstractions.umlsuper.Classifier o1, core.abstractions.umlsuper.Classifier o2) {
            if (o2.allParents().contains(o1)) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
