package cmofimpl.bootstrap;

import cmof.*;
import cmof.common.ReflectiveSequence;
import cmof.common.ReflectiveCollection;
import cmof.reflection.Object;
import cmof.reflection.Argument;
import core.abstractions.visibilities.VisibilityKind;

public class B1Element implements cmof.reflection.Object, NamedElement {

    private String name;    
    
    public B1Element(String name) {
        this.name = name;
    }
    
    protected void notInBootstrap() {
        throw new RuntimeException("not implemented for bootstrap");
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String value) {
        this.name = value;
    }

    public String getQualifiedName() {
        return name;
    }
    
    public Namespace getNamespace() {
        notInBootstrap();
        return null;
    }
    
    public VisibilityKind getVisibility() {
        notInBootstrap();
        return null;
    }

    public void setVisibility(VisibilityKind value) {
        notInBootstrap();
    }

    public ReflectiveCollection<Comment> getOwnedComment() {
        notInBootstrap();
        return null;
    }

    public ReflectiveCollection<Element> getOwnedElement() {
        notInBootstrap();
        return null;
    }

    public Element getOwner() {
        return null;
    }
    
    public cmof.UmlClass getMetaClass() {
        notInBootstrap();
        return null;
    }

    public Object getContainer() {
        notInBootstrap();
        return null;
    }

    public boolean isSet(cmof.Property property) throws IllegalArgumentException {
        notInBootstrap();
        return false;
    }

    public void unset(cmof.Property property) throws IllegalArgumentException {
        notInBootstrap();
    }

    public void delete() {
        notInBootstrap();
    }

    public Object invokeOperation(Operation op, ReflectiveSequence<Argument> arguments) {
        notInBootstrap();
        return null;
    }

    public boolean isInstanceOfType(cmof.UmlClass type, boolean includeSubTypes) {
        notInBootstrap();
        return false;
    }
    
    public java.lang.Object get(cmof.Property property) {
        notInBootstrap();
        return null;
    }
    
    public void set(cmof.Property property, java.lang.Object object) {
        notInBootstrap();        
    }
    
    public boolean equals(Object element) {
        notInBootstrap();
        return false;
    }

    public String toString() {
        return getName();
    }

    public Object container() {
        return null;
    }

    public Object getOutermostContainer() {
        return null;
    }

    
    // implementation of unnessesary functionality
    public Element allOwnedElements() {
        return null;
    }

    public boolean mustBeOwned() {
        return false;
    }

    public Namespace allNamespaces() {
        return null;
    }

    public String separator() {
        return null;
    }

    public String qualifiedNameOperation() {
        return null;
    }

    public boolean isDistinguishableFrom(core.abstractions.namespaces.NamedElement n, core.abstractions.namespaces.Namespace ns) {
        return false;
    }

	public ReflectiveCollection<? extends Object> getComponents() {
		// TODO Auto-generated method stub
		return null;
	}
}
