package cmof;


public interface PackageMerge extends cmof.DirectedRelationship
{

    public cmof.Package getMergingPackage();

    public void setMergingPackage(cmof.Package value);

    public cmof.Package getMergedPackage();

    public void setMergedPackage(cmof.Package value);

}

