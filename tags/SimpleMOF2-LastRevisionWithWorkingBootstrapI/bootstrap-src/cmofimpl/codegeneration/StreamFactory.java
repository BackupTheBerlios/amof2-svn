package cmofimpl.codegeneration;

import java.io.*;
import java.util.*;


public class StreamFactory {
    
    private String dir = null;
    
    public StreamFactory(String dir) {
        this.dir = dir;
    }
    
    public OutputStream createStream(List<String> path, String name, String ext) throws IOException {
        String fileName = dir + "/";
        Iterator<String> dirs = path.iterator();
        while (dirs.hasNext()) {
            fileName += dirs.next() + "/";
        }
        
        new File(fileName).mkdirs();
        
        fileName += name + "." + ext;
        
        File theFile = new File(fileName);
        if (!theFile.exists()) {
            theFile.createNewFile();
        }
        
        return new FileOutputStream(theFile, false);
    }

}
