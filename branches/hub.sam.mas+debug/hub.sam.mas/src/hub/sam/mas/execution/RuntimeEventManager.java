/***********************************************************************
 * MASE -- MOF Action Semantics Editor
 * Copyright (C) 2007 Andreas Blunk
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301  USA
 ***********************************************************************/

package hub.sam.mas.execution;

import hub.sam.mas.model.mas.ObjectIdentifier;

import java.util.HashSet;
import java.util.Set;

public class RuntimeEventManager {
    
    private Set<RuntimeEventListener> listeners = new HashSet<RuntimeEventListener>();
    private static RuntimeEventManager eventManager;
    
    private RuntimeEventManager() {
        // singleton
    }
    
    public static RuntimeEventManager getDefault() {
        if (eventManager == null) {
            eventManager = new RuntimeEventManager();
        }
        return eventManager;
    }

    public void addListener(RuntimeEventListener listener) {
        listeners.add(listener);
    }
    
    public void removeListener(RuntimeEventListener listener) {
        listeners.remove(listener);
    }

    public void fireObjectReached(ObjectIdentifier objectId) {
        for (RuntimeEventListener listener: listeners) {
            listener.objectReached(objectId);
        }
    }
    
}
