package us.kbase.util;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import us.kbase.auth.AuthException;
import us.kbase.auth.AuthService;
import us.kbase.auth.AuthToken;
import us.kbase.kbasesequences.SequenceSet;

public class testWsDeluxeUtil {
	
	private static final String USER_NAME = "aktest";
	private static final String PASSWORD = "1475rokegi";
	private static final String workspaceName = "AKtest";//"ENIGMA_KBASE";//
	private static AuthToken token = null;

	@Before
	public void setUp() throws Exception {
		try {
			token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		} catch (AuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testSequenceSetImport() throws Exception {
		String fileName = "/home/kbase/dev_container/modules/meme/test/seqs.fasta";
		String description = "mod genes from Desulfovibrio spp.";//"Idr2 regulated genes from Halobacterium sp. NRC-1";
		String setName = "mod_desulfovibrio";//"Halobacterium_sp_NRC-1_Idr2_regulon";//set to null for KBase ID assignment
		SequenceSet result = SequenceSetImporter.importSequenceSetFromFile(fileName, workspaceName, setName, description, token.toString());
		
		
		System.out.println(result.getSequenceSetId());
		System.out.println(result.getDescription());
		System.out.println(result.getSequences().toString());
		
		assertNotNull(result);
		assertEquals(result.getDescription(), description);
	}

}
