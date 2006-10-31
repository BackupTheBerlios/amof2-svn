package cmof.reflection;

import cmof.*;
import cmof.common.ReflectiveSequence;

/**
 * An Element may be created from a Factory. A Factory is an instance of the MOF
 * Factory class. A Factory creates instances of the types in a Package.
 */
public interface Factory extends Object {

	public java.lang.Object createFromString(DataType dataType, String string);

	public String convertToString(DataType dataType, java.lang.Object object);

	public cmof.reflection.Object create(UmlClass metaClass);

	public cmof.reflection.Package getPackage();

	public cmof.reflection.Object createObject(UmlClass umlClass,
			ReflectiveSequence<Argument> arguments);

	public cmof.reflection.Link createLink(Association association,
			cmof.reflection.Object firstObject,
			cmof.reflection.Object secondObject);
}
