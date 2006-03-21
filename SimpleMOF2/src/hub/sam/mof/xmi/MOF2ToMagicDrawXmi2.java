/*
A MOF 2 Java -- The MOF Repository tool for Java
Copyright (C) 2005 Markus Scheidgen

    This library is free software; you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as published by the
Free Software Foundation; either version 2.1 of the License, or any later
version.

    This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.

    You should have received a copy of the GNU Lesser General Public License
along with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/

package hub.sam.mof.xmi;

import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.instancemodel.InstanceModel;
import hub.sam.mof.instancemodel.ValueSpecification;
import hub.sam.mof.instancemodel.ValueSpecificationImpl;
import hub.sam.mopa.Name;
import hub.sam.mopa.Pattern;
import hub.sam.mopa.PatternClass;
import hub.sam.mopa.PatternList;
import hub.sam.mopa.trees.TreeNode;

import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

public class MOF2ToMagicDrawXmi2 extends PatternClass implements XmiTransformator {

    private String getDataValue(ClassInstance<XmiClassifier,String,String> from, String key, int index) {
        if (from == null || from.get(key) == null || from.get(key).getValues().size() <= index || from.get(key).getValues().get(index) == null || from.get(key).getValues().get(index).asDataValue() == null) {
            return null;
        }
        try {
            return from.get(key).getValues().get(index).asDataValue().getValue();
        } catch (NullPointerException e) {
            return null;
        }
    }

    private void removeAttribute(ClassInstance<XmiClassifier,String,String> from, String attr) {
        if (from.get(attr) != null) {
            if (from.get(attr).getValues().size() > 0) {
                for(ValueSpecification<XmiClassifier,String,String> value: from.get(attr).getValues()) {
                    if (value.asInstanceValue() != null) {
                        value.asInstanceValue().getInstance().setComposite(null);
                    }
                }
                from.get(attr).getValues().clear();
            }
        }
    }

    private final InstanceModel<XmiClassifier,String,String> model;
    private ClassInstance<XmiClassifier,String,String> umlModelElement = null;
    private final Collection visistedNodes = new HashSet();

    public MOF2ToMagicDrawXmi2(InstanceModel<XmiClassifier,String,String> model) {
        super();
        this.model = model;
        for (ClassInstance<XmiClassifier, String, String> instance : model.getInstances()) {
            instance.getClassifier().setNamespacePrefix("uml");
        }
        umlModelElement = model.createInstance(null, new XmiClassifier("Model", "uml"), null);
    }

    public Collection<ClassInstance<XmiClassifier,String,String> > getTopLevelElements() {
        // has to be implemented but is not needed
        return null;
    }

    public void transform(TreeNode node) throws XmiException {
        Collection<TreeNode> nodes = new Vector<TreeNode>(1);
        nodes.add(node);
        try {
            run(nodes, null, "", 0);
        } catch (XmiException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    //Model.Package.Class.Association.DataType.Enumeration.PrimitiveType
    @SuppressWarnings({"unchecked"})
    @Pattern ( order = 100, atype = "Package.Class.Association.DataType.Enumeration.PrimitiveType")
    public void classifier() throws Throwable {
        if (!visistedNodes.contains(actualNode())) {
            visistedNodes.add(actualNode());
            dive();
        } else {
            breakPattern();
        }
    }

    //p=Package provided (p.getComposite() == null)
    @Pattern ( order = 99, atype = "Package", variable = "p")
    public void compositePackage(@Name("p") ClassInstance<XmiClassifier,String,String> p) {
        provided(p.getComposite() == null);

        umlModelElement.addValue("ownedElement", model.createInstanceValue(p));
        p.setComposite(umlModelElement);
    }

    //p=Package(ot=ownedType:Class.Association.DataType.PrimitiveType.Enumeration) -> {
    @PatternList( order = 98, value = {
        @Pattern( atype = "Package", variable = "p", children = "type"),
        @Pattern( atype = "Class.Association.DataType.PrimitiveType.Enumeration", variable = "ot", property = "ownedType", name = "type")})
    public void ownedType(
            @Name("p")  ClassInstance<XmiClassifier,String,String> p,
            @Name("ot") ClassInstance<XmiClassifier,String,String> ot) {
        p.get("ownedType").getValues().remove(model.createInstanceValue(ot));
        p.addValue("ownedMember", model.createInstanceValue(ot));
    }

    //c=Class
    @Pattern ( order = 97, atype = "Class", variable = "c")
    public void classifier(@Name("c") ClassInstance<XmiClassifier,String,String> c) {
        if (c.get("superClass") != null) {
            for (ValueSpecificationImpl<XmiClassifier, String, String> value: c.get("superClass").getValues()) {
                ClassInstance<XmiClassifier,String,String> generalization =
                        model.createInstance(null, new XmiClassifier("Generalization", "uml"), c);
                c.addValue("generalization", model.createInstanceValue(generalization));
                generalization.addValue("general", value);
            }
            //removeAttribute(c, "superClass");
        }
    }

    //p=Property
    @Pattern ( order = 96, atype = "Property", variable = "p")
    public void property(@Name("p") ClassInstance<XmiClassifier,String,String> p) {
        String upper = getDataValue(p, "upper", 0);
        if (upper != null) {
            ClassInstance<XmiClassifier,String,String> upperValue =
                    model.createInstance(null, new XmiClassifier("LiteralString", "uml"), p);
            p.addValue("upperValue", model.createInstanceValue(upperValue));
            if (upper.equals("-1")) {
                upperValue.addValue("value", model.createPrimitiveValue("*"));
            } else {
                upperValue.addValue("value", model.createPrimitiveValue(upper));
            }
            removeAttribute(p, "upper");
        }

        String lower = getDataValue(p, "lower", 0);
        if (lower != null) {
            ClassInstance<XmiClassifier,String,String> lowerValue =
                    model.createInstance(null, new XmiClassifier("LiteralString", "uml"), p);
            p.addValue("lowerValue", model.createInstanceValue(lowerValue));
            lowerValue.addValue("value", model.createPrimitiveValue(lower));
            removeAttribute(p, "lower");
        }

        String composite = getDataValue(p, "isComposite", 0);
        if (composite != null) {
            if (composite.equals("true")) {
                p.addValue("aggregation", model.createPrimitiveValue("composite"));
            }
            removeAttribute(p, "isComposite");
        }
    }
}
