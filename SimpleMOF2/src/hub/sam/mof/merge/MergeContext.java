package hub.sam.mof.merge;

import cmof.Package;
import cmof.PackageMerge;
import cmof.Property;
import cmof.cmofFactory;
import cmof.Association;
import core.abstractions.namespaces.Namespace;
import core.abstractions.ownerships.Element;
import hub.sam.mof.Repository;
import hub.sam.mof.mofinstancemodel.MofClassSemantics;
import hub.sam.mof.mofinstancemodel.MofClassifierSemantics;
import hub.sam.util.MultiMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * The merge context shares information between different merges that belong to each other. Usually the user initiates
 * a single merge, this merges creates other merges, all running in the same context.
 * <p/>
 * It is the responsibility of this class to allow the use of references to merged values in a merge that does not
 * itself merge the values but represents a merge of the same context.
 */
public final class MergeContext {

    /**
     * Creates a merge context and executes all necessary merges for a package merge. The given package is
     * searched for package merged, which will be executed.
     *
     * @param thePackage The package with package merges.
     * @param factory    A cmof factory for the extend that contain all the merged packages.
     */
    @SuppressWarnings("unchecked")
    public static void mergePackages(Package thePackage, cmofFactory factory) {
        Collection<Namespace> mergedPackages = new Vector<Namespace>();
        Collection<PackageMerge> merges = new Vector<PackageMerge>();
        for (PackageMerge merge : thePackage.getPackageMerge()) {
            mergePackages(merge.getMergedPackage(), factory);
            mergedPackages.add(merge.getMergedPackage());
            merges.add(merge);
        }
        for (PackageMerge merge : merges) {
            ((cmof.reflection.Object)merge).delete();
        }
        if (mergedPackages.size() == 0) {
            return;
        }

        System.out.println("Merging packages: " + mergedPackages + " into " + thePackage.getQualifiedName());

        Property mergingProperty = (Property)Repository.getLocalRepository().getExtent(Repository.CMOF_EXTENT_NAME)
                .query("Package:core/Package:abstractions/Package:namespaces/Class:Namespace/Property:member");
        MergeContext context = new MergeContext(mergingProperty, factory, new DefaultMergeConfiguration());
        context.getToplevelMergedNamespaces().add(thePackage);
        context.getToplevelMergedNamespaces().addAll(mergedPackages);
        context.merge(thePackage, (Collection)mergedPackages, false);
        context.executeUpdates();
    }

    private final cmofFactory factory;
    private final Property mergingProperty;
    private final Collection<Namespace> toplevelMergedNamespaces = new Vector<Namespace>();
    private final Map<Element, Element> mergedElementForElement = new HashMap<Element, Element>();
    private final Collection<Merge> merges = new Vector<Merge>();
    private final MergeConfiguration configuration;
    private final MultiMap<Association, Link> linksCreatedInThisContext = new MultiMap<Association, Link>();

    /**
     * Creates a new merge context.
     *
     * @param mergingProperty The property for that and for all subsetted properties the values are considered to be merged with
     *                        other values. This is usually
     *                        InfraStructureLibrary::Core::Abstraction::Namespaces::Namespace::ownedMember.
     * @param factory         The factory for the extend that all the merging is executed in.
     */
    public MergeContext(Property mergingProperty, cmofFactory factory, MergeConfiguration configuration) {
        super();
        this.factory = factory;
        this.mergingProperty = mergingProperty;
        this.configuration = configuration;
    }

    /**
     * Executes a merge. The merging element and merged elements are remembered to retreave the merged value for
     * a value ({@link MergeContext#getMergedElement(core.abstractions.ownerships.Element)}).
     *
     * @param mergingElement The element that is merged into.
     * @param mergedElements The elements that are merged into the merging element.
     * @param copy           The merge to execute is actually just a copy (all values will be removed from the merging element
     *                       before the merge is executed).
     */
    public void merge(Element mergingElement, Collection<Element> mergedElements, boolean copy) {
        assert mergingElement != null;

        Merge merge = new Merge(mergingElement, mergedElements, this, copy);
        merge.execute();
        mergedElementForElement.put(mergingElement, mergingElement);
        for (Element mergedElement : mergedElements) {
            mergedElementForElement.put(mergedElement, mergingElement);
        }
        merges.add(merge);
    }

    /**
     * Executes all necessary updates in all merges in this context. This is the final step of a merge; now that
     * all updates have been collected and all merges have been executed, the merged values can actually deliver the
     * finally merged values and the updates can thus be executed.
     */
    public void executeUpdates() {
        for (Merge merge : merges) {
            merge.clearMergingElement();
        }
        for (Merge merge : merges) {
            merge.executeUpdates();
        }
    }

    /**
     * Retrieves a semantics instance based on the meta-class of a given element.
     *
     * @param element The element for that semantics is needed for.
     * @return The semantics for the meta-class of the element.
     */
    MofClassSemantics semanticsForElement(Element element) {
        return MofClassifierSemantics
                .createClassClassifierForUmlClass(((cmof.reflection.Object)element).getMetaClass());
    }

    Collection<Namespace> getToplevelMergedNamespaces() {
        return toplevelMergedNamespaces;
    }

    /**
     * The merging property of this merge context. It does not change for a single context.
     *
     * @return The merging property for all merges in this context.
     */
    Property getMergingProperty() {
        return mergingProperty;
    }

    /**
     * Creates a new empty merged value that is merged with the given element. It is intended to be used
     * to create copyies of values.
     *
     * @param original The value to copy. It must be the value of a property of a merged element.
     * @return The merged value.
     * @see MergedValue
     */
    MergedValue createCopy(Object original, boolean isRunForMergingProperty) {
        MergedValue result = new MergedValue(null, this, isRunForMergingProperty);
        result.addToMerge(original);
        return result;
    }

    /**
     * Creates a merged value.
     *
     * @param original  The original value for a composite value or the first value for a reference value.
     * @param composite True if a composite value should be create, false for a reference value.
     * @see MergedValue
     */
    MergedValue createValue(Object original, boolean composite, boolean isRunForMergingProperty) {
        if (original == null) {
            throw new MergeException("Attempt to create composite merged value without original value.");
        }
        if (composite) {
            return new MergedValue(original, this, isRunForMergingProperty);
        } else {
            MergedValue result = new MergedValue(this, isRunForMergingProperty);
            result.addToMerge(original);
            return result;
        }
    }

    cmofFactory getFactory() {
        return factory;
    }

    /**
     * Returns the merged element for an element. If the element was merged to a merging element in this context
     * this method will return the merging element.
     *
     * @param forElement The element that was used in merge.
     */
    Element getMergedElement(Element forElement) {
        Element result = mergedElementForElement.get(forElement);
        if (result == null) {
            return forElement;
        } else {
            return result;
        }
    }

    MergeConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Determines wheather the opposite to this link was already added in this context. This is used to detect
     * duplicate links that would be created due to association semantics.
     * @param property
     *        The property that would cause the duplication.
     * @param propertyValue
     *        The value on the property side.
     * @param oppositeValue
     *        The object that the property is set to.
     */
    boolean isNewLink(Property property, Object propertyValue, Object oppositeValue) {
        if (property.getOpposite() == null) {
            return true;
        } else {
            boolean alreadyExist = linksCreatedInThisContext.get(property.getAssociation()).contains(
                    new Link(propertyValue, oppositeValue));
            if (alreadyExist) {
                return false;
            } else {
                linksCreatedInThisContext.put(property.getAssociation(), new Link(oppositeValue, propertyValue));
                return true;
            }
        }
    }

    class Link {
        private final Object o1;
        private final Object o2;

        Link(Object o1, Object o2) {
            super();
            this.o1 = o1;
            this.o2 = o2;
        }

        @Override
        public int hashCode() {
            return o1.hashCode() + o2.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Link) {
                return ((Link)obj).o1 == this.o1 && ((Link)obj).o2 == this.o2;
            } else {
                return false;
            }
        }
    }
}
