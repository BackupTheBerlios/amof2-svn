package hub.sam.mof.ant;

import cmof.Type;
import cmof.TypedElement;
import cmof.reflection.Extent;
import hub.sam.mof.Repository;
import hub.sam.mof.codegeneration.GenerationException;
import hub.sam.mof.xmi.Xmi1Reader;
import hub.sam.mof.xmi.XmiException;
import hub.sam.util.AbstractClusterableException;
import hub.sam.util.MultiMap;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import java.io.File;
import java.util.Collection;
import java.util.Vector;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class MergeModel extends Task {
    private String model1Kind = null;
    private String model2Kind = null;
    private File model1 = null;
    private File model2 = null;

    private File outModel = null;

    @Override
    public void execute() throws BuildException {
        if (this.model1Kind == null) {
            model1Kind = "mof";
        }
        if (this.model2Kind == null) {
            model2Kind = "mof";
        }
        if (model1 == null) {
            throw new BuildException("Parameter model1 is mandatory");
        }
        if (model2 == null) {
            throw new BuildException("Parameter model2 is mandatory");
        }
        if (outModel == null) {
            throw new BuildException("Parameter outModel is mandatory");
        }

        Xmi1Reader.XmiKind model1Kind = null;
        Xmi1Reader.XmiKind model2Kind = null;
        for(Xmi1Reader.XmiKind kind: Xmi1Reader.XmiKind.class.getEnumConstants()) {
            if (this.model1Kind.equals(kind.toString())) {
                model1Kind = kind;
            }
            if (this.model2Kind.equals(kind.toString())) {
                model2Kind = kind;
            }
        }
        if ((model1Kind == null) || (model2Kind == null)) {
            throw new BuildException("Unknown xmi kind used");
        }

        if (outModel.lastModified() > model2.lastModified() && outModel.lastModified() > model1.lastModified()) {
            return;
        }

        Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
        try {
            System.out.println("Initializing.");
            Repository repo = Repository.getLocalRepository();
            Extent m3 = repo.getExtent(Repository.CMOF_EXTENT_NAME);
            cmof.Package cmof = (cmof.Package)m3.query("Package:cmof");

            System.out.println("Reading model.");
            Extent m2 = repo.createExtent("m2");
            repo.loadXmiIntoExtent(m2, cmof, model1.getCanonicalPath(), model1Kind);
            repo.loadXmiIntoExtent(m2, cmof, model2.getCanonicalPath(), model1Kind);

            extractDoubleTypes(m2);

            System.out.println("Writing model.");
            repo.writeExtentToXmi(outModel.getCanonicalPath(), cmof, m2, Xmi1Reader.XmiKind.mof);
        } catch (XmiException xe) {
            AbstractClusterableException.printReport(xe, System.err);
            throw new BuildException(xe);
        } catch (GenerationException ge) {
            AbstractClusterableException.printReport(ge, System.err);
            throw new BuildException(ge);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new BuildException(e);
        }
    }

    private void extractDoubleTypes(Extent m2) {
        MultiMap<Type, TypedElement> typeMap = new MultiMap<Type, TypedElement>();
        for(cmof.reflection.Object obj: m2.getObject()) {
            if (obj instanceof TypedElement) {
                if (((Type)((TypedElement)obj).getType()).getName() != null) {
                    typeMap.put(((TypedElement)obj).getType(), (TypedElement)obj);
                }
            }
            if (obj instanceof Type) {
                if (((Type)obj).getName() != null) {
                    typeMap.putAll((Type)obj, Collections.EMPTY_LIST);
                }
            }
        }
        Collection<Type> types = typeMap.getKeys();
        Map<String, Type> singleTypes = new HashMap<String, Type>();
        Collection<String> singleTypeNames = new HashSet<String>();
        Collection<Type> doubleTypes = new Vector<Type>();
        for(Type type: types) {
            if (singleTypeNames.contains(type.getQualifiedName())) {
                doubleTypes.add(type);
            } else {
                singleTypeNames.add(type.getQualifiedName());
                singleTypes.put(type.getQualifiedName(), type);
            }
        }

        for(Type doubleType: doubleTypes) {
            for(TypedElement typedElement: typeMap.get(doubleType)) {
                typedElement.setType(singleTypes.get(doubleType.getQualifiedName()));
            }            
            ((cmof.reflection.Object)doubleType).delete();
        }
    }

    public void setModel1Kind(String model1Kind) {
        this.model1Kind = model1Kind;
    }

    public void setModel2Kind(String model2Kind) {
        this.model2Kind = model2Kind;
    }

    public void setModel1(File model1) {
        this.model1 = model1;
    }

    public void setModel2(File model2) {
        this.model2 = model2;
    }

    public void setOutModel(File outModel) {
        this.outModel = outModel;
    }

}
