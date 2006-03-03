package hub.sam.tools.geninfraforunisys;

import java.io.*;
import cmof.reflection.*;
import hub.sam.mof.Repository;
import hub.sam.mof.mopatree.*;

public class GenerateInfrastructure {
    
    public static void main(String[] args) throws Exception {
        Repository repository = new Repository();
        Extent extent = repository.createExtent("work");
        repository.loadXmiIntoExtent(extent,(cmof.Package)repository.getExtent(Repository.CMOF_EXTENT_NAME).query("Package:cmof"), "models/b4-model2.xmi");
     
        PrintWriter out = new PrintWriter(new File("models/infrastructure-unisys.xmi"));

        out.println("<?xml version = '1.0' encoding = 'ISO-8859-1' ?>");
        out.println("<!-- <!DOCTYPE XMI SYSTEM 'UMLX13-11.dtd' > -->");
        out.println("<XMI xmi.version = '1.1' xmlns:UML='href://org.omg/UML/1.3' timestamp = 'Wed Jun 29 16:01:20 2005' >");
        out.println(" <XMI.header>");
        out.println("  <XMI.documentation>");
        out.println("  <XMI.exporter>Unisys.JCR.1</XMI.exporter>");
        out.println("  <XMI.exporterVersion>1.3.4</XMI.exporterVersion>");
        out.println("  </XMI.documentation>");
        out.println("  <XMI.metamodel xmi.name = 'UML' xmi.version = '1.3'/>");
        out.println(" </XMI.header>");
        out.println("<XMI.content>");
        out.println("<!-- ==================== test    [Model] ==================== -->");
        out.println("<UML:Model xmi.id = 'G.0' ");
        out.println("  name = 'test' visibility = 'public' isSpecification = 'false' ");
        out.println("  isRoot = 'false' isLeaf = 'false' isAbstract = 'false' >");
        out.println("  <UML:Namespace.ownedElement>");

        Rules.writeXml(out, Mof2TreeNode.createNode(extent.query("Package:core")));

        out.println("  </UML:Namespace.ownedElement>");
        out.println("</UML:Model>");
        out.println("</XMI.content>");
        out.println("</XMI>");
        out.close();
    }
}
