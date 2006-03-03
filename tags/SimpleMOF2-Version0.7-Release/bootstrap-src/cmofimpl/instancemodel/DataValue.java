package cmofimpl.instancemodel;

import core.abstractions.expressions.*;

public interface DataValue extends ElementInstance, ValueSpecification {

    public cmof.common.ReflectiveSequence<? extends cmof.DataType> getClassifier();
}
