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

package hub.sam.mof.mofinstancemodel;

import java.util.*;
import hub.sam.mof.instancemodel.ValueSpecification;
import cmof.*;

public class UpdateGraphNode {

    private final ValueSpecification<UmlClass,Property,java.lang.Object> value;
    private UpdateGraphNode adjacentReason = null;
    private Collection<UpdateGraphNode> adjacentReasonings = new HashSet<UpdateGraphNode>();
    private final MofValueSpecificationList owner;
    
    public UpdateGraphNode(ValueSpecification<UmlClass,Property,java.lang.Object> value, MofValueSpecificationList owner) {
        this.value = value;
        this.owner = owner;
    }
    
    public void setAdjacentReason(UpdateGraphNode node) {
        if (adjacentReason != null) {
            throw new RuntimeException("assert");
        }
        adjacentReason = node;
    }
    
    public void addAjacentReasoning(UpdateGraphNode node) {
        if (node != null) {                    
            adjacentReasonings.add(node);
            node.setAdjacentReason(this);
        }
    }

    public UpdateGraphNode getAdjacentReason() {
        return adjacentReason;
    }

    public Collection<UpdateGraphNode> getAdjacentReasonings() {
        return adjacentReasonings;
    }
    
    public boolean equals(Object o) {
        if (o instanceof ValueSpecification) {
            return o.equals(value);
        } else if (o instanceof UpdateGraphNode) {
            return o.equals(this);
        } else {
            return false;
        }
    }

    public ValueSpecification<UmlClass, Property, java.lang.Object> getValue() {
        return value;
    }

    public void setAdjacentReasonings(Collection<UpdateGraphNode> adjacentReasonings) {
        this.adjacentReasonings = adjacentReasonings;
    }
    
    public boolean primaryAdd() {
        boolean result = owner.primaryAdd(this);
        for (UpdateGraphNode adjacent: adjacentReasonings) {
            adjacent.secondaryAdd();
        }
        return result;
    }
    
    public void primaryAdd(int index) {
        owner.primaryAdd(index, this);
        for (UpdateGraphNode adjacent: adjacentReasonings) {
            adjacent.secondaryAdd();
        }
    }
    
    public void primaryRemove() {
        owner.primaryRemove(this);
        UpdateGraphNode root = this;
        while (root.adjacentReason != null) {
            root = root.adjacentReason;
        }
        root.secondaryRemove(this);
    }
    
    public void primaryRemove(int index) {
        owner.primaryRemove(index, this);
        UpdateGraphNode root = this;
        while (root.adjacentReason != null) {
            root = root.adjacentReason;
        }
        root.secondaryRemove(this);
    }
    
    public void secondaryAdd() {
        owner.secondaryAdd(this);
        for (UpdateGraphNode adjacent: adjacentReasonings) {
            adjacent.secondaryAdd();
        }
    }
    
    public void secondaryRemove(UpdateGraphNode except) {
        if (except != this) {
            // was already removed with primary remove
            owner.secondaryRemove(this);
        }
        for (UpdateGraphNode adjacent: adjacentReasonings) {
            adjacent.secondaryRemove(except);
        }
    }
}
