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

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.gef.commands.Command;
import hub.sam.mase.m2model.ActivityEdge;

public class ActivityEdgeDeleteBendpointCommand extends Command {

    private final ActivityEdge edge;
    private final int bendpointIndex;
    private final Bendpoint bendpoint;

    public ActivityEdgeDeleteBendpointCommand(ActivityEdge edge, int bendpointIndex) {
        this.edge = edge;
        this.bendpointIndex = bendpointIndex;
        this.bendpoint = edge.getBendpoints().get(bendpointIndex);
    }

    public void execute() {
        redo();
    }

    public void redo() {
        edge.getBendpoints().remove(bendpointIndex);
    }

    public void undo() {
        edge.getBendpoints().add(bendpointIndex, bendpoint);
    }

}
