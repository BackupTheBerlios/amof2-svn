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

package hub.sam.mof.test.lib;

import hub.sam.mof.test.lib.abstractions.behavioralfeatures.TestIsDistinguishAbleFrom;
import hub.sam.mof.test.lib.abstractions.classifier.TestAllFeatures;
import hub.sam.mof.test.lib.abstractions.generalzations.TestConformsTo;
import hub.sam.mof.test.lib.abstractions.generalzations.TestParents;
import hub.sam.mof.test.lib.abstractions.generalzations.TestGetGeneral;
import hub.sam.mof.test.lib.abstractions.multiplicities.TestMultiplicity;
import hub.sam.mof.test.lib.abstractions.namespaces.TestNamedElement;
import hub.sam.mof.test.lib.abstractions.namespaces.TestNamespace;
import hub.sam.mof.test.lib.abstractions.ownerships.TestElement;
import hub.sam.mof.test.lib.abstractions.redefinitions.TestRedfinableElement;
import hub.sam.mof.test.lib.abstractions.umlsuper.TestClassifier;
import junit.framework.*;

public class Main extends TestCase {    
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(TestIsDistinguishAbleFrom.class);
        suite.addTestSuite(TestAllFeatures.class);
        suite.addTestSuite(TestGetGeneral.class);
        suite.addTestSuite(TestParents.class);
        suite.addTestSuite(TestConformsTo.class);
        suite.addTestSuite(TestMultiplicity.class);
        suite.addTestSuite(TestNamedElement.class);
        suite.addTestSuite(TestNamespace.class);
        suite.addTestSuite(TestElement.class);
        suite.addTestSuite(TestRedfinableElement.class);
        suite.addTestSuite(TestClassifier.class);
        return suite;
    }
}
