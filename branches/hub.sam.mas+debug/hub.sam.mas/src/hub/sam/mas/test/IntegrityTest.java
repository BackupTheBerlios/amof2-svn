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

package hub.sam.mas.test;

import hub.sam.mas.management.MasContext;
import hub.sam.mas.management.MasModelContainer;
import hub.sam.mas.management.MasRepository;
import hub.sam.mas.management.SimpleMasContextFile;
import hub.sam.mof.Repository;
import hub.sam.mof.management.SaveException;
import junit.framework.TestCase;

public class IntegrityTest extends TestCase {

		
	private MasModelContainer masModelContainer;
    private SimpleMasContextFile contextFile;

    public IntegrityTest() {
		super("MAS integrity test"); 
	}
	@Override
	protected void setUp() throws Exception {
        Repository repository = Repository.getLocalRepository();
        repository.reset();
        Repository.getConfiguration().setWarnAboutForeignExtentObjectUsage(false);
        Repository.getConfiguration().setGenerousXMI(true);
        
        // load xmi files for syntax and semantic from a mas context file
        contextFile = new SimpleMasContextFile("resources/models/tests/preserve-integrity.masctx");

        // create a new mas model container
        masModelContainer = new MasModelContainer(repository);
        masModelContainer.loadMasModel(contextFile.getMasFile());
        masModelContainer.loadSyntaxModelForEditing(contextFile.getSyntaxFile(), "Package:test");
	}

	public void testPreservedIntegrity() {
        MasContext masContext = MasRepository.getInstance().createMasContext(masModelContainer, contextFile);
        // TODO
        try {
            masContext.save();
        }
        catch (SaveException e) {
            e.printStackTrace();
        }
	}
	
}
