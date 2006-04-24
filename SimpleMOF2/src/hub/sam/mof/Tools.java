package hub.sam.mof;

import cmof.reflection.Extent;
import cmof.Property;
import cmof.Association;
import cmof.PackageImport;
import cmof.NamedElement;
import hub.sam.mof.util.ListImpl;

public class Tools {
    public static void setOppositeValues(Extent extent) {
        for (Object o: extent.getObject()) {
            if (o instanceof Property) {
                Property property = (Property)o;
                Property opposite = property.getOpposite();
                Association association = property.getAssociation();
                if (opposite == null && association != null) {
                    for (Property end: association.getMemberEnd()) {
                        if (!end.equals(property)) {
                            opposite = end;
                        }
                    }

                    if (opposite == null) {
                        // a class associated with itself
                        property.setOpposite(property);
                    } else {
                        property.setOpposite(opposite);
                    }
                }
            }
        }
    }

    public static void removeFoldedImports(Extent extent) {
        for (Object o: new ListImpl<cmof.reflection.Object>(extent.getObject())) {
            if (o instanceof PackageImport) {
                PackageImport packageImport = (PackageImport)o;
                if (packageImport.getImportedPackage().equals(packageImport.getImportingNamespace())) {
                    ((cmof.reflection.Object)packageImport).delete();
                }
            }
        }
    }

    public static void repairBadOMGUmlMultiplicities(Extent extent) {
        for (Object o: new ListImpl<cmof.reflection.Object>(extent.getObject())) {
            if (o instanceof Property && (((Property)o).getOwner() instanceof Association)) {
                Property property = (Property)o;
                if (property.getLower() == 1) {
                    property.setLower(0);
                }
                if (property.getUpper() == 1) {
                    property.setUpper(-1);
                }
            }
        }
    }

      public static void showPropertiesWithoutType(Extent extent) {
        for (Object o: new ListImpl<cmof.reflection.Object>(extent.getObject())) {
            if (o instanceof Property) {
                Property property = (Property)o;
                if (property.getType() == null) {
                    System.out.println("Has no type: " + property.getQualifiedName());
                }
                if (property.getType() instanceof cmof.PrimitiveType &&
                        ((NamedElement)((cmof.reflection.Object)property).getOutermostContainer()).getName().equals("uml")) {
                    System.out.println("Has primitiveType: " + property.getQualifiedName() + "  :  " +
                            property.getType().getQualifiedName());
                }
            }
        }
    }
}
