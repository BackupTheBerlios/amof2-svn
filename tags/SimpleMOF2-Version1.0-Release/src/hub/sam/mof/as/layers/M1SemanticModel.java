package hub.sam.mof.as.layers;

import cmof.Association;
import cmof.Classifier;
import cmof.Operation;
import cmof.Package;
import cmof.Property;
import cmof.UmlClass;
import cmof.cmofFactory;

import java.util.Collection;
import java.util.Vector;

/**
 * Contains functionality to create implicit model elements in the semantic part of a meta-model.
 * This means that you can search in a model for "double-instance" classifiers, create the instance
 * association between them an their corresponding general classifier and create create and delete
 * operations for "double-instance" manipulation.
 */
public class M1SemanticModel {

    public static String getCreateOperationName(UmlClass instanceClassifier) {
        return "create" + instanceClassifier.getName().substring(0, 1).toUpperCase() +
                instanceClassifier.getName().substring(1, instanceClassifier.getName().length());
    }

    @SuppressWarnings({"UNUSED_SYMBOL"})
    public static String getDestroyOperationName(UmlClass instanceClassifier) {
        return "destroy";
    }

    private final cmofFactory factory;

    public M1SemanticModel(cmofFactory factory) {
        super();
        this.factory = factory;
    }

    public void createImplicitElements(Collection<Package> model) {
        for (Classifier instanceClassifier: allInstanceClassifier(model)) {
            if (instanceClassifier instanceof UmlClass) {
                createImplicitElements((UmlClass)instanceClassifier);
            }
        }
    }

    private void createImplicitElements(UmlClass instanceClassifier) {
        UmlClass classifierClassifier = (UmlClass)instanceClassifier.getMetaClassifier();

        Operation create = factory.createOperation();
        create.setType(instanceClassifier);
        create.setName("create" + instanceClassifier.getName());
        classifierClassifier.getOwnedOperation().add(create);

        Operation delete = factory.createOperation();
        delete.setName("destroy");
        instanceClassifier.getOwnedOperation().add(delete);

        Association instanceOf = factory.createAssociation();
        instanceOf.setName(instanceClassifier.getName() + "InstanceOf");
        instanceClassifier.getPackage().getOwnedType().add(instanceOf);

        Property instance = factory.createProperty();
        instance.setType(instanceClassifier);
        instance.setLower(0);
        instance.setUpper(-1);
        instance.setName("metaInstance");
        instance.setUmlClass(classifierClassifier);
        instance.setAssociation(instanceOf);

        Property classifier = factory.createProperty();
        classifier.setType(classifierClassifier);
        classifier.setLower(0);
        classifier.setUpper(1);
        classifier.setName("metaClassifier");
        classifier.setUmlClass(instanceClassifier);
        classifier.setAssociation(instanceOf);
        classifier.setOpposite(instance);
    }

    private Collection<Classifier> allInstanceClassifier(Iterable<? extends Package> model) {
        Collection<Classifier> result = new Vector<Classifier>();
        for (Package aPackage: model) {
            result.addAll(allInstanceClassifier(aPackage.getNestedPackage()));
            for (Object member: aPackage.getOwnedMember()) {
                if (member instanceof Classifier) {
                    Classifier aClass = (Classifier)member;
                    if (aClass.getMetaClassifier() != null) {
                        result.add(aClass);
                    }
                }
            }
        }
        return result;
    }
}
