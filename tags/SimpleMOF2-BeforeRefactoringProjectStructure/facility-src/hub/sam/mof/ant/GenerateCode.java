package hub.sam.mof.ant;

import java.io.*;
import org.apache.tools.ant.*;
import hub.sam.mof.*;
import cmof.reflection.*;

public class GenerateCode extends Task {
        
    private boolean unisys = false;
    private java.lang.Class staticModel = null;
    private File src = null;
    private File destdir = null;
    
    @Override
	public void execute() throws BuildException {   
        if (src == null) {
            throw new BuildException("Parameter src is mandatory");
        }
        if (destdir == null) {
            throw new BuildException("Parameter destdir is mandatory");            
        }
        if (!src.isFile()) {
            throw new BuildException(src.toString() + " is not a file");
        }
        if (!destdir.isDirectory()) {
            throw new BuildException(destdir.toString() + " is not a directory");
        }
        
        Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader()); 
        try {
            System.out.println("Initializing.");
            Repository repository = new Repository();
            cmof.Package cmof = (cmof.Package)repository.getExtent(Repository.CMOF_EXTENT_NAME).
                    query("Package:cmof");
            
            System.out.println("Reading model.");
            Extent extent = repository.createExtent("extent");            
            if (!unisys) {
                repository.loadXmiIntoExtent(extent, cmof, src.toString());
            } else {
                repository.loadUnisysXmiIntoExtent(extent, cmof, src.toString());
            }
            System.out.println("Generating code.");
            repository.generateCode(extent, destdir.toString());
            if (staticModel != null) {
                System.out.println("Generating code for static model.");
                repository.generateStaticModel(extent, staticModel.getName(), destdir.toString());
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new BuildException(e);
        }
    }

    public void setDestdir(File destdir) {
        this.destdir = destdir;
    }

    public void setSrc(File src) {
        this.src = src;
    }

    public void setStaticModel(java.lang.Class staticModel) {
        this.staticModel = staticModel;
    }

    public void setUnisys(boolean unisys) {
        this.unisys = unisys;
    }  
}
