package hub.sam.sdlplus.ant;

import cmof.reflection.*;
import cmof.Tag;
import hub.sam.mof.Repository;
import hub.sam.mof.util.ListImpl;
import hub.sam.mof.javamapping.JavaMapping;

/**
 * A specialized ant tast to create MOF2 conform XMI models from native MagaicDraw files. The specialization are custom
 * actions that add additional Tags to the SDL metamodels
 */
public class UpgradeXmi extends hub.sam.mof.ant.UpgradeXmi {

    /**
     * Overwride this callback with functionality to lower all packagename in case and add "hub.sam.models" to each
     * toplevel package for the Java mapping using Tags.
     */
    @Override
    public void custom(Extent extent, cmof.Package m3Model, Repository repository) {
        for(cmof.reflection.Object o: new ListImpl<cmof.reflection.Object>(extent.getObject())) {
            if (o instanceof cmof.Package) {
                cmof.Package aPackage = (cmof.Package)o;
                cmof.cmofFactory factory = (cmof.cmofFactory)repository.createFactory(extent, m3Model);
                Tag renameTag = factory.createTag();
                renameTag.setName(JavaMapping.SubstituteNameTagName);
                renameTag.setValue(aPackage.getName().toLowerCase());
                aPackage.getTag().add(renameTag);
                if (aPackage.getOwner() == null) {
                    Tag prefixTag = factory.createTag();
                    prefixTag.setName(JavaMapping.PackagePrefixTagName);
                    prefixTag.setValue("hub.sam.models");
                    aPackage.getTag().add(prefixTag);
                }
            }
        }
    }
}
