package hub.sam.mof.merge;

import cmof.Operation;
import cmof.Parameter;
import cmof.ParameterDirectionKind;
import cmof.Property;
import cmof.Type;
import cmof.Constraint;
import core.abstractions.ownerships.Element;
import core.abstractions.namespaces.NamedElement;
import core.abstractions.visibilities.VisibilityKind;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

/**
 * This is the default implementation, suitable for cmof package merges.
 */
public class DefaultMergeConfiguration implements MergeConfiguration {

    private static final Map<String, Object> knownConflicts = new HashMap<String, Object>();
    static {
        knownConflicts.put("InfrastructureLibrary.Core.Constructs.Property.subsettingContext.null.type, UML.Classes.Kernel.Property.subsettingContext.null.type", "UML.Classes.Kernel.Type");
        knownConflicts.put("UML.Activities.BasicActivities.Pin.isAbstract, UML.Activities.FundamentalActivities.Pin.isAbstract", Boolean.FALSE);
        knownConflicts.put("UML.Classes.Interfaces.BehavioralFeature.raisedException.type, UML.CommonBehaviors.Communications.BehavioralFeature.raisedException.type", "UML.Classes.Interfaces.Type");
        knownConflicts.put("UML.Actions.BasicActions.Pin.isAbstract, UML.Activities.BasicActivities.Pin.isAbstract, UML.Activities.FundamentalActivities.Pin.isAbstract, null.isAbstract", Boolean.FALSE);
    }

    private final Map<String, Object> conflicts;
    private final Map<NamedElement, String> alternativeNames = new HashMap<NamedElement, String>();

    public DefaultMergeConfiguration(Map<String, Object> conflicts) {
        super();
        this.conflicts = conflicts;
    }

    DefaultMergeConfiguration() {
        super();
        this.conflicts = knownConflicts;
    }

    public String getAlternativeName(NamedElement forElement) {
        String result = alternativeNames.get(forElement);
        if (result == null) {
            result = getNewAlternativeName(forElement);
            if (result != null) {
                alternativeNames.put(forElement, result);
            }
        }
        return result;
    }

    @SuppressWarnings({"OverlyLongMethod"})
    private String getNewAlternativeName(NamedElement forElement) {
        if (forElement instanceof cmof.Association) {
            List<String> names = new Vector<String>(2);
            for (Property end : ((cmof.Association)forElement).getMemberEnd()) {
                String name = end.getName();
                if (name == null) {
                    name = end.getType().getName();
                }
                names.add(name);
            }
            Collections.sort(names);
            return names.toString();
        } else if (forElement instanceof cmof.Property) {
            String name = forElement.getName();
            if (name == null) {
                String typeName = ((Property)forElement).getType().getName();
                return typeName.substring(0, 1).toLowerCase() + typeName.substring(1);
            } else {
                return name;
            }
        } else if (forElement instanceof Operation) {
            Operation op = (Operation)forElement;
            StringBuffer opName = new StringBuffer(op.getName());
            for (Parameter parameter : op.getFormalParameter()) {
                if (!parameter.getDirection().equals(ParameterDirectionKind.RETURN)) {
                    opName.append("_");
                    opName.append(parameter.getType().getQualifiedName());
                }
            }
            return opName.toString();
        } else if (forElement instanceof cmof.Parameter) {
            if (((Parameter)forElement).getDirection() == ParameterDirectionKind.RETURN) {
                return "return";
            } else {
                Type type = (cmof.Type)((Parameter)forElement).getType();
                if (type != null) {
                    return type.getName();
                } else {
                    return null;
                }
            }
        } else if (forElement instanceof cmof.Constraint) {
            String name = forElement.getName();
            if (name == null) {
                Constraint constraint = (Constraint)forElement;
                Loop:
                for(Element constraintedElement: constraint.getConstrainedElement()) {
                    if (constraintedElement instanceof Operation) {
                        if (((Operation)constraintedElement).getPrecondition().contains(constraint)) {
                            name = "pre";
                        } else if (((Operation)constraintedElement).getPostcondition().contains(constraint)) {
                            name = "post";
                        } else if (((Operation)constraintedElement).getBodyCondition().equals(constraint)) {
                            name = "body";
                        }
                    }
                    break Loop;
                }
            }
            return name;
        } else {
            return null;
        }
    }

    @SuppressWarnings({"unchecked", "RedundantCast"})
    public Object valueForConflictingValues(Property property, Collection<Object> values, Element mergingElement,
                                            Collection<Element> mergedElements) {
        String propertyName = property.getName();
        if (propertyName.equals("lower")) {
            return Collections.max((Collection)values);
        } else if (propertyName.equals("upper")) {
            Collection<Long> uppers = new Vector<Long>(values.size());
            for (Object value : values) {
                if ((Long)value != -1) {
                    uppers.add((Long)value);
                }
            }
            return Collections.min((Collection)uppers);
        } else if (propertyName.equals("isUnique") || propertyName.equals("isOrdered") ||
                propertyName.equals("isComposite")) {
            return Boolean.TRUE;
        } else if (propertyName.equals("isReadOnly")) {
            return Boolean.FALSE;
        } else if (propertyName.equals("isDerived") || propertyName.equals("isDerivedUnion")) {
            return Boolean.TRUE;
        } else if (propertyName.equals("visibility")) {
            // only PRIVATE and PUBLIC exist in cmof
            return VisibilityKind.PUBLIC;
        } else if (propertyName.equals("name")) {
            return null;
        } else if (values.iterator().next() instanceof cmof.Package) {
            // TODO: here is a more comprehensive check needed, only when the container of a toplevel merging element
            // is requested, null should be returned.
            return null;
        } else {
            return resolveBasedOnConflicts(mergingElement, mergedElements, values, property);
        }
    }

    private Object resolveBasedOnConflicts(Element mergingElement, Collection<Element> mergedElements, Collection<Object> values, Property property) {
        String propertyName = property.getName();
        String serializedConflictKey = null;
        if (mergingElement instanceof NamedElement) {
            String[] conflictKey = new String[mergedElements.size() + 1];
            conflictKey[0] = getElementUnique(mergingElement) + "." + propertyName;
            int i = 1;
            for (Element mergedElement: mergedElements) {
                conflictKey[i] = getElementUnique(mergedElement) + "." + propertyName;
                i++;
            }
            Arrays.sort(conflictKey);
            StringBuffer serializedConflictKeyBuffer = new StringBuffer();
            boolean first = true;
            for (String conflictKeyPart: conflictKey) {
                if (!first) {
                    serializedConflictKeyBuffer.append(", ");
                } else {
                    first = false;
                }
                serializedConflictKeyBuffer.append(conflictKeyPart);
            }
            serializedConflictKey = serializedConflictKeyBuffer.toString();
            if (conflicts.get(serializedConflictKey) != null) {
                Object solution = conflicts.get(serializedConflictKey);
                for(Object value: values) {
                    if (value instanceof NamedElement) {
                        if (solution.equals(((NamedElement)value).getQualifiedName())) {
                            return value;
                        }
                    } else {
                        if (solution.equals(value)) {
                            return value;
                        }
                    }
                }
            }
        }

        throw new MergeException("conflicting values for merging property " + property.getQualifiedName() +
                " in merging element " + mergingElement + ". The conflect key is " + serializedConflictKey + ".");
    }

    public void customMerge(Property property, Collection<Collection<Object>> valueCollections) {
        if (property.getQualifiedName().equals(cmof.Parameter.class.getCanonicalName())) {
            customMergeOperationParameter(valueCollections);
        }
    }

    /**
     * Executes a custom merge for parameter. It simply puts all result parameter to the ends of the value collections,
     * because the return parameter position has no semantic influence but can result in a merge conflict.
     *
     * @param valueCollections The value collections to manipulate.
     */
    private void customMergeOperationParameter(Collection<Collection<Object>> valueCollections) {
        for (Collection<Object> values : valueCollections) {
            Parameter result = null;
            for (Object value : values) {
                Parameter parameter = (Parameter)value;
                if (parameter.getDirection().equals(ParameterDirectionKind.RETURN)) {
                    result = parameter;
                }
            }
            values.remove(result);
            values.add(result);
        }
    }

    private String getElementUnique(Element element) {
        if (element instanceof NamedElement) {
            return ((NamedElement)element).getQualifiedName();
        } else {
            return getElementUnique(element.getOwner()) + ".null";
        }
    }
}
