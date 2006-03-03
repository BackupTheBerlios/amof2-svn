package cmofimpl.bootstrap;

import cmof.*;
import cmof.common.ReflectiveSequence;
import cmof.common.ReflectiveCollection;

public class B1Property extends B1Element implements cmof.Property {

    private Element owner;
    private boolean isUnique, isOrdered, isReadOnly;
    private int lower;
    private long upper;
    private ReflectiveSequence<cmof.Property> subsettedPropertys;
    private cmof.Property opposite;
    private Type type;
    private String defaultValue;
    private cmof.Association association;
    private cmof.UmlClass umlClass;    
    
    public B1Property(String name) {
        super(name);
    }
    
    public B1Property init( Type type, boolean isUnique, 
            boolean isOrdered, boolean isReadOnly, int lower, int upper,
            cmof.Property[] subsettedPropertys,String defaultValue) {        
        this.opposite = null;
        this.isUnique = isUnique;
        this.isOrdered = isOrdered;
        this.lower = lower;
        this.upper = upper;
        this.isReadOnly = isReadOnly;
        if (subsettedPropertys == null) {
            subsettedPropertys = new cmof.Property[]{};
        }
        this.subsettedPropertys = cmofimpl.util.ListImpl.asList(subsettedPropertys);
        this.type = type;
        this.defaultValue = defaultValue;
        return this;
    }
    
    public B1Property init(Type type, String defaultValue) {        
        this.opposite = null;
        this.isUnique = true;
        this.isOrdered = false;
        if (defaultValue != null) {
            this.lower = 1;
        } else {
            this.lower = 0;
        }
        this.upper = 1;
        this.isReadOnly = false;
        this.subsettedPropertys = new cmofimpl.util.ListImpl<cmof.Property>();
        this.type = type;
        this.defaultValue = defaultValue;
        return this;
    }

    public cmof.Association getAssociation() {
        if (owner instanceof cmof.Association) {
            return (cmof.Association)owner;
        } else {
            return null;
        }
    }
   
    public cmof.UmlClass getUmlClass() {        
        return this.umlClass;
    }
    
    public boolean isOrdered() {
        return isOrdered;
    }
    
    public boolean isUnique() {
        return isUnique;
    }

    public int getLower() {
        return lower;
    }

    public long getUpper() {
        return upper;
    }

    public Element getOwner() {
        return owner;
    }
    
    public void setOwner(cmof.Element owner) {
        this.owner = owner;
        if (owner instanceof cmof.UmlClass) {
            this.umlClass = (cmof.UmlClass)owner;            
        } else if (owner instanceof cmof.Association) {
            setAssociation((cmof.Association)owner);
            this.umlClass = null;
        }
    }
    
    public boolean isReadOnly() {
        return isReadOnly;
    }

    public ReflectiveCollection<cmof.Property> getSubsettedProperty() {
        return subsettedPropertys;        
    }
    
    public ReflectiveCollection<cmof.Property> getRedefinedProperty() {    
        return new cmofimpl.util.ListImpl<cmof.Property>();
    }
    
    public void setReadOnly(boolean readOnly) {
        notInBootstrap();
    }

    public cmof.Property getOpposite() {
        return opposite;        
    }

    public void setOposite(cmof.Property value) {
        this.opposite = value;
    }

    public void setUmlClass(cmof.UmlClass value) {
        setOwner(value);
    }

    public cmof.Association getOwningAssociation() {
        if (owner == association) {
            return this.association;   
        } else {
            return null;
        }
    }
    
    public void setAssociation(cmof.Association value) {
        this.association = value;
        for (cmof.Property end: association.getMemberEnd()) {
            if (end != this) {
                this.opposite = end;
            }                
        }
    }

    public void setOwningAssociation(cmof.Association value) {
        setOwner(value);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type value) {
        this.type = value;
    }
    
    public void setType(core.abstractions.typedelements.Type value) {
        
    }

    public void setOrdered(boolean isOrdered) {
        this.isOrdered = isOrdered;
    }
    
    public void setLower(int value) {
        this.lower = value;
    }

    public void setUpper(long upper) {
        this.upper = upper;
    }

    public void setUnique(boolean value) {
        this.isUnique = value;
    }

    public boolean isDerived() {        
        return false;
    }

    public boolean isComposite() {
        //notInBootstrap();
        return false;
    }
    
    public String getDefault() {
        return defaultValue;
    }
    
    public void setDefault(String value) {
        this.defaultValue = value;
    }

    public ReflectiveCollection<? extends cmof.Classifier> getRedefinitionContext() {
        return null;
    }

    public ReflectiveCollection<? extends RedefinableElement> getRedefinedElement() {
        return getRedefinedProperty();
    }

    public ReflectiveCollection<? extends cmof.Classifier> getFeaturingClassifier() {
        return null;
    }

    public void setIsReadOnly(boolean value) {
    }

    public boolean isDerivedUnion() {
        return false;
    }

    public void setIsDerivedUnion(boolean value) {
    }

    public void setOpposite(Property value) {
    }

    public DataType getDatatype() {
        return null;
    }

    public void setDatatype(DataType value) {
    }

    public void setIsOrdered(boolean value) {
    }

    public void setIsUnique(boolean value) {
    }

    public void setIsComposite(boolean value) {
    }

    public void setIsDerived(boolean value) {
    }

    public void setUmlClass(core.basic.UmlClass value) {
    }

    public void setOpposite(core.basic.Property value) {
    }

    public Property oppositeOperation() {
        return null;
    }

    public boolean isConsistentWith(RedefinableElement redefinee) {
        return false;
    }

    public Classifier subsettingContext() {
        return null;
    }

    public boolean isConsistentWith(core.abstractions.redefinitions.RedefinableElement redefinee) {
        return false;
    }

    public boolean isRedefinitionContextValid(core.abstractions.redefinitions.RedefinableElement redefinable) {
        return false;
    }

    public int lowerBound() {
        return 0;
    }

    public long upperBound() {
        return 0;
    }

    public boolean isMultivalued() {
        return false;
    }

    public boolean includesCardinality(int C) {
        return false;
    }

    public boolean includesMultiplicity(core.abstractions.multiplicities.MultiplicityElement M) {
        return false;
    }
}
