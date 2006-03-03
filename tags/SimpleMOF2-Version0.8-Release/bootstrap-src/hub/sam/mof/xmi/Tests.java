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

package hub.sam.mof.xmi;
/*
import hub.sam.mof.instancemodel.*;
import hub.sam.mof.instancemodel.conversion.Converter;
import hub.sam.mof.Repository;
import cmof.*;
import cmof.reflection.*;
*/
public class Tests extends junit.framework.TestCase {
/*    private InstanceModel<XmiClassifier,String,String> model = null;
    private XmiReader reader = null;
    private Converter<XmiClassifier,String,String,UmlClass,Property,Type,DataType,java.lang.Object> converter = null;
    private Repository repository = new Repository();
    
    public Tests(String name) {
        super(name);
    }
    
    public void setUp() throws Exception {
        model = new InstanceModel<XmiClassifier,String,String>();
        reader = new XmiReader(model);
        
        Extent m3Extent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
        Extent m2Extent = repository.createExtent("m2Extent", (cmof.Package)m3Extent.query("Package:cmof"));
        converter = new Converter<XmiClassifier,String,String,UmlClass,Property,Type,DataType,java.lang.Object>(
                new XmiToCMOF(m2Extent,(cmof.Package)m3Extent.query("Package:cmof")));        
    }
   
    public void testXmiRead() throws Exception {
        reader.read(new java.io.File("models/b4-model2.xmi"));
        converter.convert(model, new InstanceModel<UmlClass,Property,java.lang.Object>());
    }
*/
}
