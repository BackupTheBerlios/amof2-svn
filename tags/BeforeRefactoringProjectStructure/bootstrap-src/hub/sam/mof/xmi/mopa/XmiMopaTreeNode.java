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

package hub.sam.mof.xmi.mopa;

import java.util.*;
import hub.sam.mopa.trees.*;
import hub.sam.mof.instancemodel.*;
import hub.sam.mof.xmi.XmiClassifier;

public class XmiMopaTreeNode implements TreeNode {
    private final ClassInstance<XmiClassifier,String,String> node;
    private final static Map<ClassInstance<XmiClassifier,String,String>, XmiMopaTreeNode> nodes = new HashMap<ClassInstance<XmiClassifier,String,String>, XmiMopaTreeNode>();
    private final Collection<TreeNode> children = new Vector<TreeNode>();
    
    public static XmiMopaTreeNode createNode(ClassInstance<XmiClassifier,String,String> node) {
        XmiMopaTreeNode result = nodes.get(node);
        if (result == null) {
            result = new XmiMopaTreeNode(node);
            nodes.put(node, result);
        }
        return result;
    }
    
    private XmiMopaTreeNode(ClassInstance<XmiClassifier,String,String> node) {
        this.node = node;
        
        for (StructureSlot<XmiClassifier,String,String> slot: node.getSlots()) {
            for (ValueSpecificationImpl<XmiClassifier,String,String> value: slot.getValues()) {
                if (value.asInstanceValue() != null) {
                    children.add(createNode(value.asInstanceValue().getInstance()));
                }
            }
        }
    }
    
    public TreeNode getParent() {
        return createNode(node.getComposite());        
    }

    public Iterable<TreeNode> getChildren() {
        return children;
    }

    public Iterable<TreeNode> getReferences(String arg0) {
        StructureSlot<XmiClassifier,String,String> slot = node.get(arg0);
        Collection<TreeNode> result = new Vector<TreeNode>();
        if (slot == null) {
            return result;
        }        
        for (ValueSpecificationImpl<XmiClassifier,String,String> value: slot.getValues()) {
            if (value.asInstanceValue() != null) {
                result.add(createNode(value.asInstanceValue().getInstance()));
            }
        }
        return result;
    }

    public Object getElement() {
        return node;
    }

    public boolean match(String arg0) {
        if (arg0 == null) {
            return false;
        } else if (node.getClassifier().getName() == null) {
            return false;
        } else {
            if (arg0.contains(".")) {
                String[] elements = arg0.split("\\.");
                for (String element : elements) {
                    if (node.getClassifier().getName().equals(element)) {
                        return true;
                    }
                }
                return false;
            } else {
                return node.getClassifier().getName().equals(arg0);
            }
        }
    }    
}
