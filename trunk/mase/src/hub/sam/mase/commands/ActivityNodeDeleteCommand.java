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

package hub.sam.mase.commands;

import hub.sam.mas.model.mas.Activity;
import hub.sam.mas.model.mas.ActivityNode;

public class ActivityNodeDeleteCommand extends MofDeleteCommand {

    private final ActivityNode node;
    private Activity activity;

    public ActivityNodeDeleteCommand(ActivityNode node) {
        super(node);
        this.node = node;
    }

    public void execute() {
        activity = node.getActivityAsNode();
        redo();
    }

    public void redo() {
        super.redo();
        activity.getNode().remove(node);
    }
    
    public void undo() {
        super.undo();
        activity.getNode().add(node);
    }

}
