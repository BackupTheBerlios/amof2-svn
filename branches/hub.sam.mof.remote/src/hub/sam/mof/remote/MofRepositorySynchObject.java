package hub.sam.mof.remote;

import hub.sam.srmi.AbstractSynchObject;
import hub.sam.srmi.EventDispatchThread;
import hub.sam.srmi.IEventDispatcher;
import hub.sam.srmi.Repository;
import hub.sam.srmi.SequentialEventDispatcher;

public class MofRepositorySynchObject extends AbstractSynchObject {
    
    private static MofRepositorySynchObject instance;

    private MofRepositorySynchObject() {
        super("hub.sam.mof.remote");
    }
    
    public static MofRepositorySynchObject getInstance() {
        if (instance == null) {
            instance = new MofRepositorySynchObject();
        }
        return instance;
    }

    public IEventDispatcher getEventDispatcher() {
        IEventDispatcher dispatcher = Repository.getInstance().getEventDispatcher(getDispatcherName());
        if (dispatcher == null) {
            dispatcher = new SequentialEventDispatcher();
            Repository.getInstance().addEventDispatcher(getDispatcherName(), new EventDispatchThread(), dispatcher);
        }
        return dispatcher;
    }

}
