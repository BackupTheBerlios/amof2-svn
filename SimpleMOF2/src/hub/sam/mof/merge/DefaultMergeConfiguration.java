package hub.sam.mof.merge;

import cmof.Operation;
import cmof.Parameter;
import cmof.ParameterDirectionKind;
import cmof.Property;
import cmof.Type;
import core.abstractions.elements.Element;
import core.abstractions.namespaces.NamedElement;
import core.abstractions.visibilities.VisibilityKind;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * This is the default implementation, suitable for cmof package merges.
 */
public class DefaultMergeConfiguration implements MergeConfiguration {

    public String getAlternativeName(NamedElement forElement) {
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
            String typeName = ((Property)forElement).getType().getName();
            return typeName.substring(0, 1).toLowerCase() + typeName.substring(1);
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
        } else {
            return null;
        }
    }

    @SuppressWarnings({"unchecked", "RedundantCast"})
    public Object valueForConflictingValues(Property property, Collection<Object> values, Element mergingElement) {
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
            throw new MergeException(
                    "conflicting values for merging property " + property + " in mergin element " +
                            mergingElement);
        }
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
}
