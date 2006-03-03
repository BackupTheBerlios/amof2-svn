package hub.sam.util;

import java.util.*;

public abstract class AbstractFluxBox<Key, Value, Params> {

    private Map<Key,Value> fluxBox = new HashMap<Key,Value>();
    
    protected abstract Value createValue(Key key, Params params);
    
    public Value getObject(Key key, Params params) {
        if (key == null) {
            return createValue(key, params);
        }
        Value result = fluxBox.get(key);
        if (result == null) {
            result = createValue(key, params);
            fluxBox.put(key, result);
        }
        return result;
    }
}
