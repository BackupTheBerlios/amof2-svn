package hub.sam.mof;

import cmof.reflection.Extent;
import cmof.Property;
import cmof.Association;
import cmof.PackageImport;
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
                            opposite = property;
                        }
                    }

                    property.setOpposite(opposite);
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
}
