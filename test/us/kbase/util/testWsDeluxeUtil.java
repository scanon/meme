package us.kbase.util;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import us.kbase.auth.AuthException;
import us.kbase.auth.AuthService;
import us.kbase.auth.AuthToken;
import us.kbase.sequences.SequenceSet;

public class testWsDeluxeUtil {
	
	private static final String USER_NAME = "aktest";
	private static final String PASSWORD = "1475rokegi";
	private static final String workspaceName = "AKtest";
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
		String description = "Test";
		
		SequenceSet result = SequenceSetImporter.importSequenceSetFromFile(fileName, workspaceName, description, token.toString());
		
		
		System.out.println(result.getSequenceSetId());
		System.out.println(result.getDescription());
		System.out.println(result.getSequences().toString());
		
		assertNotNull(result);
		assertEquals(result.getDescription(), "Test");
	}

}
