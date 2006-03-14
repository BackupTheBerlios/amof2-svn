package hub.sam.mof.merge;

import cmof.Package;
import cmof.Property;
import cmof.cmofFactory;
import cmof.reflection.Extent;
import hub.sam.mof.Repository;
import junit.framework.TestCase;

import java.util.Collection;
import java.util.Vector;

public class MergeTest extends TestCase {
    private Repository repository;
    private Package m3;
    private Extent testModel;
    private cmofFactory factory;
    private Compare compare;

    public MergeTest() throws Exception {
        super("Merge tests");
    }

    @Override
    protected void setUp() throws Exception {
        repository = Repository.getLocalRepository();
        Extent m3Extent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
        m3 = (Package)m3Extent.query("Package:cmof");

        testModel = repository.createExtent("testModel");
        factory = (cmofFactory)repository.createFactory(testModel, m3);
        repository.loadMagicDrawXmiIntoExtent(testModel, m3, "resources/models/test/merge-tests.mdxml");

        Collection<Property> doNotCompare = new Vector<Property>();
        doNotCompare.add((Property)m3Extent.query("Package:cmof/Class:NamedElement/Property:name"));
        doNotCompare.add((Property)m3Extent.query("Package:cmof/Class:NamedElement/Property:namespace"));
        doNotCompare.add((Property)m3Extent.query("Package:cmof/Class:Element/Property:owner"));
        doNotCompare.add((Property)m3Extent.query("Package:cmof/Class:Type/Property:package"));
        compare = new Compare(doNotCompare);
    }

    private void post() throws Exception {
        repository.writeExtentToXmi("resources/models/work/mergetest.xml", m3, testModel);
    }

    private void aTest(int number) throws Exception {
        Package A = (Package)testModel.query("Package:MergeTest" + number + "/Package:A");

        MergeContext.mergePackages(A, factory);

        Package Result = (Package)testModel.query("Package:MergeTest" + number + "/Package:Result");
        Difference difference = compare.compare(Result, A);
        if (difference != null) {
            System.err.println("Merge result does not match expected results: " + difference.getMessage());
        }
        assertNull(difference);
    }


    public void test1() throws Exception {
        aTest(1);
    }

    public void test2() throws Exception {
        aTest(2);
    }

    public void test3() throws Exception {
        aTest(3);
    }

    public void test4() throws Exception {
        aTest(4);
    }

    public void test5() throws Exception {
        aTest(5);
    }

    public void test6() throws Exception {
        aTest(6);
    }

    public void test7() throws Exception {
        aTest(7);
    }

    public void test8() throws Exception {
        aTest(8);
    }

    @Override
    protected void tearDown() throws Exception {
        post();
    }
}
