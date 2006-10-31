package cmofimpl.bootstrap;

import java.util.*;
import cmof.*;

public class B1M2Model {

    private static Map<String, UmlClassImpl> bootstrapXmiNames;
    
    private static cmofimpl.reflection.ExtentImpl b1Extent;
    
    static {
        b1Extent = new cmofimpl.reflection.ExtentImpl("b1Extent", true);

        B1PrimitiveType string = new B1PrimitiveType("String");
        B1PrimitiveType bool = new B1PrimitiveType("Boolean");
        B1PrimitiveType integer = new B1PrimitiveType("Integer");
        B1PrimitiveType unlimitedNatural = new B1PrimitiveType("UnlimitedNatural");
        
        B1Class umlClass = new B1Class("Class");
        B1Class property = new B1Class("Property");
        B1Class packageClass = new B1Class("Package");
        B1Class primitiveType = new B1Class("PrimitiveType");
        B1Class association = new B1Class("Association");
        
        b1Extent.addObject(umlClass);
        b1Extent.addObject(property);    
        b1Extent.addObject(primitiveType);
        b1Extent.addObject(packageClass);
        b1Extent.addObject(association);
        
        B1Property ownerProperty = null;
        B1Property ownerUmlClass = null; 
        B1Property owningAssociation = null;
        // class
        {
            B1Property member = new B1Property("member").init(property, true, false, false, 0, -1, null, null);
            B1Property ownedMember = new B1Property("ownedMember").init(property, false, false, false, 0, -1, new cmof.Property[]{member}, null);
            ownerProperty = new B1Property("owner").init(umlClass, true, false, false, 0, 1, null, null);
            owningAssociation = new B1Property("owningAssociation").init(association, true, false, false, 0, 1, new cmof.Property[]{ownerProperty}, null);
            B1Association ownership = new B1Association("ownership", ownerProperty, ownedMember);
            ownerUmlClass = new B1Property("owner").init(umlClass, true, false, false, 0, 1, null, null);
            umlClass.init(new cmof.Property[]{
                new B1Property("name").init(string, false, false, false, 0, 1, null, null),
                member, ownedMember, ownerUmlClass,
                new B1Property("ownedAttribute").init(property, true, false, false, 0, -1, new cmof.Property[]{ownedMember}, null),
                new B1Property("isAbstract").init(bool, "false"),               
                new B1Property("superClass").init(property, true, false, false, 0, -1, null, null),
            });                    
            b1Extent.addObject(ownership);
        }
                
        // property
        {            
            property.init(new cmof.Property[]{
                new B1Property("name").init(string, false, false, false, 0, 1,null, null),                
                new B1Property("type").init(umlClass, false, false, false, 0, 1, null, null),
                new B1Property("subsettedProperty").init(property, true, false, false, 0, -1, null, null),
                new B1Property("redefinedProperty").init(property, true, false, false, 0, -1, null, null),
                new B1Property("isUnique").init(bool, "true"),
                new B1Property("isReadOnly").init(bool, "false"),
                new B1Property("isOrdered").init(bool, "false"),
                new B1Property("upper").init(unlimitedNatural, "1"),
                new B1Property("lower").init(integer, "1"),
                new B1Property("default").init(string, null),
                new B1Property("isDerived").init(bool, "false"),
                new B1Property("isDerivedUnion").init(bool, "false"),
                new B1Property("opposite").init(property, true, false, false, 0, 1, null, null),
                ownerProperty, owningAssociation,
                new B1Property("class").init(umlClass, true, false, false, 0, 1, new cmof.Property[]{ownerProperty}, null),
                new B1Property("association").init(association, true, false, false, 0, 1, null, null),
                new B1Property("isComposite").init(bool, "false")
            });
        }
        
        // package
        {
            B1Class packageImport = new B1Class("PackageImport");            
            B1Property ownedMemberPackage = new B1Property("ownedMemberPackage").init(umlClass, true, false, false, 0, -1, null, null);            
            B1Association ownership = new B1Association("ownership", ownerUmlClass, ownedMemberPackage);
            packageClass.init(new cmof.Property[]{
                ownedMemberPackage,
                //new B1Property("name").init(string, true, false, false, 0, 1, null, null),
                new B1Property("ownedType").init(umlClass, true, false, false, 0, -1, new cmof.Property[]{ownedMemberPackage}, null),
                new B1Property("packageImport").init(packageImport, true, false, false, 0, -1, new cmof.Property[]{ownedMemberPackage}, null),
                new B1Property("nestedPackage").init(packageClass, true, false, false, 0, -1, new cmof.Property[]{ownedMemberPackage}, null)
            });
            packageClass.getSuperClass().add(umlClass);
                        
            packageImport.init(new cmof.Property[]{
                new B1Property("importedPackage").init(packageClass, null)                
            });
            packageImport.getSuperClass().add(umlClass);
            b1Extent.addObject(packageImport);
            b1Extent.addObject(ownership);
        }
        
        // type
        {
            primitiveType.init(new cmof.Property[]{
                //new B1Property("name").init(string, false, false, false, 0, 1, null, null)
            });
            
            B1Class enumerationLiteral = new B1Class("EnumerationLiteral").init(new cmof.Property[]{
                new B1Property("name").init(string, false, false, false, 0, 1, null, null)
            });
            B1Class enumeration = new B1Class("Enumeration").init(new cmof.Property[]{               
                //new B1Property("name").init(string, true, false, false, 0, 1, null, null),
                new B1Property("ownedLiteral").init(enumerationLiteral, true, false, false, 0, -1, null, null)
            });
            enumeration.getSuperClass().add(umlClass);
            primitiveType.getSuperClass().add(umlClass);
            b1Extent.addObject(enumerationLiteral);
            b1Extent.addObject(enumeration);
        }
        
        {
            B1Property memberEnd = new B1Property("memberEnd").init(property, true, false, false, 0, 2, null, null);
            B1Property ownedEnd = new B1Property("ownedEnd").init(property, true, false, false, 0, 2, new Property[]{memberEnd}, null);
            B1Association ownership = new B1Association("ownership", owningAssociation, ownedEnd); 
            association.init(new cmof.Property[]{
                memberEnd, ownedEnd, 
                //new B1Property("name").init(string, true, false, false, 0, 1, null, null),
                new B1Property("ownedEnd").init(property, true, false, false, 0, 2, new cmof.Property[]{memberEnd}, null)
            });            
            association.getSuperClass().add(umlClass);
            b1Extent.addObject(ownership);
        }               
    }
    
    public static cmof.reflection.Extent bootstrapModel() {
        return b1Extent;
    }

}
