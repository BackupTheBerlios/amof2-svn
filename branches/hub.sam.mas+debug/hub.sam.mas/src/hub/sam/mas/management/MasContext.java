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

package hub.sam.mas.management;

import hub.sam.mas.MasPlugin;
import hub.sam.mas.editor.MaseEditor;
import hub.sam.mas.model.mas.Activity;
import hub.sam.mas.model.mas.ObjectIdentifier;
import hub.sam.mof.Repository;
import hub.sam.mof.management.MofModel;
import hub.sam.mof.management.SaveException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import cmof.Comment;
import cmof.Operation;
import cmof.UmlClass;
import cmof.cmofFactory;
import cmof.reflection.Extent;

/**
 * A MasContext manages the connection between a syntax and a semantic model.
 * It offers functionality for:
 * - saving and closing models
 * - preserving integrity betweens models
 * - creating, deleting and retrieving links for operations and activities
 * 
 * A MasContext can only be created via the {@link MasRepository}.
 * 
 */
public class MasContext {
    
    private static Logger logger = Logger.getLogger(MasContext.class.getName());
    private Map<String, MasLink> links = new HashMap<String, MasLink>();
    private final MofModel syntaxModel;
    private final MofModel masModel;
    private static final String linkIdPrefix = "mas-id-";
    private Map<String, Operation> operations;
    private Map<String, Activity> activities;
    private boolean syntaxModelNeedsSaving = false;
    private IMasContextFileResource contextFile;
    private final ObjectIdentifierManager objectIdentifierManager;
    
    /**
     * Creates a MAS Context.
     * 
     * @param modelContainer
     */
    protected MasContext(IMasModelContainer modelContainer) {
        MasPlugin.configureLog4j();
        
        syntaxModel = modelContainer.getSyntaxModel();
        masModel = modelContainer.getMasModel();
        checkMasModel();
        
        operations = getOperations(syntaxModel.getExtent());
        activities = getActivities(masModel.getExtent());
        preserveIntegrity(operations, activities);
        
        objectIdentifierManager = new ObjectIdentifierManager(masModel);
        checkMasObjectIds();
    }
    
    /**
     * Creates a MAS Context that keeps a reference to its MAS Context File.
     * 
     * @param modelContainer
     * @param contextFile
     */
    protected MasContext(IMasModelContainer modelContainer, IMasContextFileResource contextFile) {
        this(modelContainer);
        this.contextFile = contextFile;
    }
    
    /**
     * Returns the MAS Context File that this MAS Context was created from or NULL if no such file was specified.
     * 
     * @return
     */
    public IMasContextFileResource getContextFile() {
        return contextFile;
    }
    
    private void checkMasModel() {
        if (logger.isEnabledFor(Level.WARN)) {
            for(cmof.reflection.Object obj: getMasModel().getExtent().outermostComposites()) {
                if (!(obj instanceof Activity)) {
                    logger.warn("found object outside of the activity");
                }
            }
        }
    }
    
    /**
     * Creates a physical connection and returns a virtual MasLink.
     * 
     * @param operation
     * @param activity
     * @return
     */
    public MasLink createLink(Operation operation, Activity activity) {
        String linkId = computeLinkId(operation);
        createLink(operation, linkId);
        createLink(activity, linkId);
        setSyntaxModelNeedsSaving(true);
        MasLink link = cacheLink(linkId, operation, activity);
        return link;
    }
    
    private String computeLinkId(Operation operation) {
        return linkIdPrefix + operation.toString().hashCode();
    }
    
    private void createLink(Operation operation, String id) {
        cmofFactory syntaxFactory = (cmofFactory) getSyntaxModel().getFactory();
        Comment com = syntaxFactory.createComment();
        com.setBody(id);
        operation.getOwnedComment().add(com);
    }
    
    private void createLink(Activity activity, String id) {
        activity.setLinkId(id);
    }
    
    /**
     * Returns a virtual MasLink or null if no virtual link or physical connection exists.
     * 
     * @param operation
     * @return
     */
    public MasLink getLink(Operation operation) {
        String linkId = getLinkId(operation);
        if (linkId == null) {
            return null;
        }
        if (links.containsKey(linkId)) {
            return links.get(linkId);
        }
        Activity activity = getActivity(linkId);
        if (activity == null) {
            return null;
        }
        MasLink link = cacheLink(linkId, operation, activity);
        return link;
    }
    
    /**
     * Returns a virtual MasLink or null if no virtual link or physical connection exists.
     * 
     * @param activity
     * @return
     */
    public MasLink getLink(Activity activity) {
        String linkId = getLinkId(activity);
        if (linkId == null) {
            return null;
        }
        if (links.containsKey(linkId)) {
            return links.get(linkId);
        }
        Operation operation = getOperation(linkId);
        if (operation == null) {
            return null;
        }
        MasLink link = cacheLink(linkId, operation, activity);
        return link;
    }
    
    private MasLink cacheLink(String linkId, Operation operation, Activity activity) {
        MasLink link = new MasLink(linkId, this, operation, activity);
        links.put(linkId, link);
        return link;
    }
    
    private String getLinkId(Operation operation) {
        String id = null;
        for(Comment com: operation.getOwnedComment()) {
            if (com.getBody() != null && com.getBody().startsWith(linkIdPrefix)) {
                return com.getBody();
            }
        }
        return id;
    }
    
    private String getLinkId(Activity activity) {
        return activity.getLinkId();
    }
    
    private Operation getOperation(String linkId) {
        return operations.get(linkId);
    }
    
    private Activity getActivity(String linkId) {
        return activities.get(linkId);
    }

    public MofModel getMasModel() {
        return masModel;
    }

    public MofModel getSyntaxModel() {
        return syntaxModel;
    }
    
    private void warnUser(String title, String message) {
        if (PlatformUI.isWorkbenchRunning() && PlatformUI.getWorkbench() != null && PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null) {
            Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart().getSite().getShell();
            MessageDialog.openWarning(shell, title, message);
        }
        else {
            System.out.println("(" + title + ") Warning: " + message);
        }
    }
    
    /**
     * Preserves reference integrity between operations and activities.
     * 
     * @param syntaxExtent
     * @param semanticExtent
     */
    private void preserveIntegrity(Map<String, Operation> operations, Map<String, Activity> activities) {
        List<String> alteredOperationNames = new ArrayList<String>();
        Map<String, Operation> renamedOperations = new HashMap<String, Operation>();
        for(String id: operations.keySet()) {
            // check id
            Operation operation = operations.get(id);
            if (!id.equals(computeLinkId(operation))) {
                // recompute id
                Activity activity = getActivity(id);
                if (activity == null) {
                    deleteLinkId(operation);
                    alteredOperationNames.add("deleted " + operations.get(id).getQualifiedName());
                    continue;
                }
                else {
                    deleteLinkId(operation);
                    deleteLinkId(activity);
                    activities.remove(activity);
                    MasLink link = createLink(operation, activity);
                    renamedOperations.put(link.getLinkId(), operation);
                    activities.put(link.getLinkId(), activity);
                    alteredOperationNames.add("repaired " + operations.get(id).getQualifiedName());
                    continue;
                }
            }
            // check if corresponding activity exists
            if (activities.containsKey(id)) {
                continue;
            }
            // no activity was found, delete id in operation
            deleteLinkId(operations.get(id));
            alteredOperationNames.add(operations.get(id).getQualifiedName());
        }
        operations.putAll(renamedOperations);
        
        if (!alteredOperationNames.isEmpty()) {
            String deletedOperationsAsString = "";
            for (int i=0; i<alteredOperationNames.size()-1; i++) {
                deletedOperationsAsString += alteredOperationNames.get(i) + ", ";
            }
            deletedOperationsAsString += alteredOperationNames.get(alteredOperationNames.size()-1);
            warnUser("Preserved Reference Integrity",
                    "Modifications in syntax extent: " + deletedOperationsAsString + ".");
        }

        int deleted = 0;
        for(String id: activities.keySet()) {
            if (operations.containsKey(id)) {
                continue;
            }
            // no operation was found, delete activity
            activities.get(id).delete();
            deleted++;
        }
        if (deleted > 0) {
            warnUser("Preserved Reference Integrity", "Modifications in semantic extent: deleted " + deleted + " unreferenced activities.");
        }
    }
    
    /**
     * Checks that every object of type MasIdentifier has an id and adds one if necessary.
     *
     */
    private void checkMasObjectIds() {
        for (ObjectIdentifier idObject: objectIdentifierManager.getAllIdObjects()) {
            if (idObject.getObjectId() == null) {
                objectIdentifierManager.addId(idObject);
            }
        }
    }
    
    /**
     * Destroys a physical connection at the syntax side.
     * 
     * @param operation
     */
    private void deleteLinkId(Operation operation) {
        for(Comment com: operation.getOwnedComment()) {
            if (com.getBody().startsWith(linkIdPrefix)) {
                operation.getOwnedComment().remove(com);
                com.delete();
                return;
            }
        }
    }
    
    /**
     * Destroys a physical connection at the semantic side.
     * 
     * @param activity
     */
    private void deleteLinkId(Activity activity) {
        activity.setLinkId(null);
    }

    /**
     * Destroys a physical connection on both sides (syntax and semantic model),
     * includes the removal of the activity.
     * 
     * @param link
     */
    protected void deleteLink(MasLink link) {
        deleteLinkId(link.getOperation());
        deleteLinkId(link.getActivity());
        link.getActivity().delete();
        getLinks().remove(link.getLinkId());
        setSyntaxModelNeedsSaving(true);
    }
    
    /**
     * Returns a HashMap that maps ids to operations.
     * 
     * @param syntaxExtent
     * @return
     */
    private Map<String, Operation> getOperations(Extent syntaxExtent) {
        Map<String, Operation> operations = new HashMap<String, Operation>();
    	for(Object obj: syntaxExtent.objectsOfType((UmlClass)Repository.getFromCmofModel("Package:cmof/Class:Operation"), false)) {
    		Operation op = (Operation)obj;
            String id = getLinkId(op);
            if (id != null) {
                operations.put(id, op);
            }
    	}
        return operations;
    }

    /**
     * Returns a HashMap that maps ids to activities.
     * 
     * @param semanticExtent
     * @return
     */
    private Map<String, Activity> getActivities(Extent semanticExtent) {
        Map<String, Activity> activities = new HashMap<String, Activity>();
        for(Object obj: semanticExtent.outermostComposites()) {
            if (obj instanceof Activity) {
                Activity act = (Activity) obj;
                if (act.getLinkId() != null) {
                    activities.put(act.getLinkId(), act);
                }
            }
        }
        return activities;
    }

    public Extent getContextId() {
        return syntaxModel.getExtent();
    }
    
    public void close() {
        getSyntaxModel().close();
        getMasModel().close();
    }
    
    /**
     * Saves the syntax and semantic model.
     * (the semantic model is always saved, whereas the syntax model is only saved if needed)
     * 
     * @throws SaveException 
     */
    public void save() throws SaveException {
        checkMasModel();
        getMasModel().save();
        
        if(syntaxModelNeedsSaving) {
            getSyntaxModel().save();
        }
        
        // mark associated mas editors as saved for all mas links
        for(MasLink link: getLinks().values()) {
            MaseEditor editor = link.getAssociatedEditor();
            if (editor != null) {
                editor.setDirty(false);
            }
        }
    }

    private Map<String, MasLink> getLinks() {
        return links;
    }

    protected void setSyntaxModelNeedsSaving(boolean syntaxModelNeedsSaving) {
        this.syntaxModelNeedsSaving = syntaxModelNeedsSaving;
    }
    
    public ObjectIdentifierManager getObjectIdentifierManager() {
        return objectIdentifierManager;
    }

}
