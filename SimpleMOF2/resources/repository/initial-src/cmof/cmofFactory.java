package cmof;


public interface cmofFactory extends cmof.reflection.Factory {

    public cmof.Tag createTag();

    public cmof.Association createAssociation();

    public cmof.UmlClass createUmlClass();

    public cmof.Property createProperty();

    public cmof.DataType createDataType();

    public cmof.Enumeration createEnumeration();

    public cmof.EnumerationLiteral createEnumerationLiteral();

    public cmof.PrimitiveType createPrimitiveType();

    public cmof.Constraint createConstraint();

    public cmof.OpaqueExpression createOpaqueExpression();

    public cmof.Operation createOperation();

    public cmof.Parameter createParameter();

    public cmof.ElementImport createElementImport();

    public cmof.Package createPackage();

    public cmof.PackageImport createPackageImport();

    public cmof.PackageMerge createPackageMerge();

    public cmof.Comment createComment();

    public cmof.Expression createExpression();

}

