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

import hub.sam.mof.javamapping.JavaMapping;
import java.util.*;
import cmof.*;

public class MofClassSemantics extends MofClassifierSemantics {

    private final Map<String, Operation> operationNames = new HashMap<String, Operation>();
    private final UmlClass classifier;
    private JavaMapping javaMapping = hub.sam.mof.javamapping.JavaMapping.mapping;
    
    public  MofClassSemantics(cmof.UmlClass classifier) {
        super(classifier);
        this.classifier = classifier;
        initialize();
    }        
    
    protected  void initialize() {
        super.initialize();
        if (classifier == null) {
            return;
        }
        for (Element e: classifier.getMember()) {
            if (e instanceof Operation) {
                Operation op = (Operation)e;
            
                StringBuffer opName = new StringBuffer();
                opName.append(op.getName());
                for (Parameter param: op.getParameter()) {
                    opName.append("_");
                    opName.append(param.getName());
                }
                operationNames.put(opName.toString(), op);
            }
        }
    }
    
    public cmof.UmlClass getClassifier() {
        return (cmof.UmlClass)super.getClassifier();
    }

    public Operation getFinalOperation(String name) {
        Operation op = operationNames.get(name);
        return op;
    }    

    protected Collection<? extends Property> ownedProperties(Classifier c) {
        return toCollection(((UmlClass)c).getOwnedAttribute());
    }
    
    protected Collection<? extends Classifier> superClasses(Classifier c) {        
        return toCollection(((UmlClass)c).getSuperClass());
    }
}
