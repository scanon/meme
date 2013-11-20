package us.kbase.meme;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import us.kbase.auth.AuthService;
import us.kbase.auth.AuthToken;
//import us.kbase.common.service.JsonClientCaller;
import us.kbase.common.service.UObject;
//		import us.kbase.workspace.ObjectData;
//		import us.kbase.workspace.ObjectIdentity;
import us.kbase.workspaceservice.GetObjectOutput;
import us.kbase.workspaceservice.GetObjectParams;
import us.kbase.generaltypes.Sequence;
import us.kbase.generaltypes.SequenceSet;
import us.kbase.util.WSUtil;

public class MemeClientTest {
	
	private SequenceSet testSequenceSet = new SequenceSet();
	private MemeRunResult memeRunResult = new MemeRunResult();
//	private String serverUrl = "http://140.221.84.195:7049";
//	private String serverUrl = "http://140.221.84.191/services/meme/";
//	private String serverUrl = "http://kbase.us/services/meme/";
	private String serverUrl = "http://127.0.0.1:7108";
	private static final String USER_NAME = "aktest";
	private static final String PASSWORD = "1475rokegi";
	
	@Before
	public void setUp() throws Exception {
    	Sequence seq1 = new Sequence();
    	seq1.setSequenceId("209110 upstream");
    	seq1.setSequence("GCCGGGCACGGGCCACCTCATCATCCGAGACTGCGACGTCTTTCATGGGGTCTCCGGTTGCTCAAGTATGAGGGTACGATGCCTCCACTCCTGCCCCAAGTCCAGCCGTGCGTGAATGCGGTCACGTTCGTCACCATGAGGGTGACCGGGTTGCCGGGTGCGATACGCAGGGCTAACGCTGCCATAATCGGGAGAGGAGTATCCACGCTTCCGGTCATGCATCATCCACCCGCATCCGCAAGGAGGCCCC");
    	Sequence seq2 = new Sequence();
    	seq2.setSequenceId("209112 upstream");
    	seq2.setSequence("AGAGTGTGAAGCGGCGGAGGAAGGCGAAGCGTGATGACATGGACATGGGGCCTCCTTGCGGATGCGGGTGGATGATGCATGACCGGAAGCGTGGATACTCCTCTCCCGATTATGGCAGCGTTAGCCCTGCGTATCGCACCCGGCAACCCGGTCACCCTCATGGTGACGAACGTGACCGCATTCACGCACGGCTGGACTTGGGGCAGGAGTGGAGGCATCGTACCCTCATACTTGAGCAACCGGAGACCCC");
    	Sequence seq3 = new Sequence();
    	seq3.setSequenceId("209114 upstream");
    	seq3.setSequence("AGGGCAGCCTCTCCCCGCGCATGCCCCTTTCCGGTCACCACCCGGCAACATTCCGTGACCATGTTGCCCCGGCACCGCCACTCTCCGCATAGTCGCACATGCTCCCGTGCCCGCGGGCGCAAACCGGGACAACGGGGCGGCTGAGGCTGACGCCCGCCCAACGCACCACCGCCACACAGGCACTCCCCATGGGACGACGGGCAAGGGGCGTACGCCACGCATCCACATGACACCATAACCGGGAAGACCC");
    	Sequence seq4 = new Sequence();
    	seq4.setSequenceId("393587 upstream");
    	seq4.setSequence("GCTCCGCATCCAGCAGCTTGACCCCCTCCGGCACCACAAAAAGTGCATGCGGCGCTATTCTGCCGCCCGCCGGACGGCCGGACCGTACTGTTGTGCCGGTTGTCGTCATGGCTGCTCCCGTAAACTGGTTTTGTCACGATTTTCAGGACATTCGTGACCGCGTTGGCAGACGATACACAACTTCGTAAGTGCGTACATGCAGTAAATACATACTCGCACTTCTGCACACGCATCAAGGAGGATTCATCCC");
    	Sequence seq5 = new Sequence();
    	seq5.setSequenceId("7532041 upstream");
    	seq5.setSequence("TATCCTGCTGCAAATATGTAGAAACCCACATCGTAGTCCGTCCGAAAAGGAGCGGATATCATCGCGGCTACCGGTCACGCTTTTCCGCGCTACCGTGACCGGCTTGAGCTCAACGGACCGGAAAGCTTATAGGATATGAACGTCGGAATCTGCGGTTTCGAGAACACCTTCCTGCGGCCCGGTTGTTGCTTGAGAGCCTGTAAACACCCTCGGCGGAACACCGCCCAACCTTCGCCAACGGACAATGCGA");
    	Sequence seq6 = new Sequence();
    	seq6.setSequenceId("8501762 upstream");
    	seq6.setSequence("GGGGCACCCTCCCCCAAAAACCTTTATTCGTATTGTCCTATTGTTGCGCAGGGGAAGGGCCACACGGCCCTTCCCCTTTTTCTTTGGCGAATCGGGGCATTCCTGTGGGCGCCACGCCCGCAGGCATCACGCCGGGGGCCTTTTCCGACAGCATGCCGCTGGCCGTGTCACTGCCCCGTGCCACGGTCACCAAGACGAAAGTTTTCGTGCCTCTGTTGCGGCCCCCCGGCCTTTTCGCCACAGTCGGGCC");
    	List<Sequence> sequences = new ArrayList<Sequence>();
    	sequences.add(seq1);
    	sequences.add(seq2);
    	sequences.add(seq3);
    	sequences.add(seq4);
    	sequences.add(seq5);
    	sequences.add(seq6);
    	testSequenceSet.setSequences(sequences);
    	testSequenceSet.setSequenceSetId("KBase.SequenceSet.12345");

	}

	@After
	public void tearDown() throws Exception {
		testSequenceSet = null;
		memeRunResult = null;
	}

	@Test
	public final void testKbasememeClient() throws Exception {
		URL serviceUrl = new URL(serverUrl);
		MEMEClient client = new MEMEClient(serviceUrl, USER_NAME, PASSWORD);
		client.setAuthAllowedForHttp(true);
		assertNotNull(client);
	}

	@Test
	public final void testFindMotifsWithMeme() throws Exception {
		URL serviceUrl = new URL(serverUrl);
		MEMEClient client = new MEMEClient(serviceUrl, USER_NAME, PASSWORD);
		client.setAuthAllowedForHttp(true);
		
		//set params
		MemeRunParameters params = new MemeRunParameters();
		params.setMod("oops");
		params.setNmotifs(2L);
		params.setMinw(14L);
		params.setMaxw(24L);
		params.setNsites(0L);
		params.setMinsites(0L);
		params.setMaxsites(0L);
		params.setPal(1L);
		params.setRevcomp(0L);
		
		MemeRunResult result = client.findMotifsWithMeme(testSequenceSet, params);

		assertEquals(Long.valueOf("0"),result.getSeed());
		assertEquals(Long.valueOf("1"),result.getSeqfrac());
		assertEquals("+",result.getStrands());
		assertEquals(Long.valueOf("1500"),result.getN());
		assertEquals(Long.valueOf("6"),result.getNCap());
		assertEquals(Double.valueOf("0.00001"),result.getDistance());
		assertEquals("dirichlet",result.getPrior());
		assertEquals(Double.valueOf("0.01"),result.getB());
		assertEquals(Long.valueOf("50"),result.getMaxiter());
		assertEquals("yes",result.getSubstring());
		assertEquals("no",result.getBranching());
		assertEquals("no",result.getWbranch());
		assertEquals(Long.valueOf("1"),result.getProb());
		assertEquals("uni",result.getSpmap());
		assertEquals("0.5",result.getSpfuzz());
		assertEquals(Long.valueOf("6"),result.getMinsites());
		assertEquals(Long.valueOf("6"),result.getMaxsites());
		assertEquals(Double.valueOf("0.8"),result.getWnsites());
		assertEquals(Long.valueOf("11"),result.getWg());
		assertEquals(Long.valueOf("1"),result.getWs());
		assertEquals("yes",result.getEndgaps());
		assertEquals(Long.valueOf("14"),result.getMinw());
		assertEquals(Long.valueOf("24"),result.getMaxw());
		assertEquals(Double.valueOf("0.00"),result.getMinic());
		assertEquals("E-value of product of p-values",result.getObjectFunction());
		assertEquals("oops",result.getMod());
		assertEquals(Long.valueOf("2"),result.getNmotifs());
		assertEquals("inf",result.getEvt());
		assertNotNull(result.getMotifs().get(0).getId());
		assertEquals(Long.valueOf("24"),result.getMotifs().get(0).getWidth());
		assertEquals(6,result.getMotifs().get(0).getSites().size());
		assertEquals(Double.valueOf("90"),result.getMotifs().get(0).getLlr());
		assertEquals(Double.valueOf("2300"),result.getMotifs().get(0).getEvalue());
		assertEquals("393587",result.getMotifs().get(0).getSites().get(0).getSourceSequenceId());
		assertEquals(Long.valueOf("134"),result.getMotifs().get(0).getSites().get(0).getStart());
		assertEquals(Double.valueOf("0.000000000152"),result.getMotifs().get(0).getSites().get(0).getPvalue());
		assertEquals("ACTGGTTTTG",result.getMotifs().get(0).getSites().get(0).getLeftFlank());
		assertEquals("TCACGATTTTCAGGACATTCGTGA",result.getMotifs().get(0).getSites().get(0).getSequence());
		assertEquals("CCGCGTTGGC",result.getMotifs().get(0).getSites().get(0).getRightFlank());
	}

	@Test
	public final void testFindMotifsWithMemeFromWs() throws MalformedURLException, Exception {
		
		//AuthToken token = JsonClientCaller.requestTokenFromKBase(USER_NAME, PASSWORD.toCharArray());
		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		System.out.println(token.toString());
		String id = "KBase.SequenceSet.12345";
		URL serviceUrl = new URL(serverUrl);
//		MEMEClient client = new MEMEClient(serviceUrl, USER_NAME, PASSWORD);
		MEMEClient client = new MEMEClient(serviceUrl, token);
		client.setAuthAllowedForHttp(true);
		
/*		System.out.println(input.getSequenceSetId());
		System.out.println(input.getSequences().get(0).getSequenceId());
		System.out.println(input.getSequences().get(0).getSequence());
*/		
		//set params
		MemeRunParameters params = new MemeRunParameters();
		params.setMod("oops");
		params.setNmotifs(2L);
		params.setMinw(14L);
		params.setMaxw(24L);
		params.setNsites(0L);
		params.setMinsites(0L);
		params.setMaxsites(0L);
		params.setPal(1L);
		params.setRevcomp(0L);

		
		String resultId = client.findMotifsWithMemeFromWs("AKtest", id, params);
//Read result from WS
		
//		List<ObjectIdentity> objectIds = new ArrayList<ObjectIdentity>();
//		ObjectIdentity objectIdentity = new ObjectIdentity().withWorkspace("AKtest").withName(resultId);
//		objectIds.add(objectIdentity);
//		List<ObjectData> output = MemeServerImpl.wsClient(token.toString()).getObjects(objectIds);
		
//		MemeRunResult result = UObject.transformObjectToObject(output.get(0).getData(), MemeRunResult.class);

		
		
		GetObjectParams objectParams = new GetObjectParams().withType("MemeRunResult").withId(resultId).withWorkspace(WSUtil.workspaceName).withAuth(WSUtil.authToken().toString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(objectParams);
		MemeRunResult result = UObject.transformObjectToObject(output.getData(), MemeRunResult.class);

		assertEquals(Long.valueOf("0"),result.getSeed());
		assertEquals(Long.valueOf("1"),result.getSeqfrac());
		assertEquals("+",result.getStrands());
		assertEquals(Long.valueOf("1500"),result.getN());
		assertEquals(Long.valueOf("6"),result.getNCap());
		assertEquals(Double.valueOf("0.00001"),result.getDistance());
		assertEquals("dirichlet",result.getPrior());
		assertEquals(Double.valueOf("0.01"),result.getB());
		assertEquals(Long.valueOf("50"),result.getMaxiter());
		assertEquals("yes",result.getSubstring());
		assertEquals("no",result.getBranching());
		assertEquals("no",result.getWbranch());
		assertEquals(Long.valueOf("1"),result.getProb());
		assertEquals("uni",result.getSpmap());
		assertEquals("0.5",result.getSpfuzz());
		assertEquals(Long.valueOf("6"),result.getMinsites());
		assertEquals(Long.valueOf("6"),result.getMaxsites());
		assertEquals(Double.valueOf("0.8"),result.getWnsites());
		assertEquals(Long.valueOf("11"),result.getWg());
		assertEquals(Long.valueOf("1"),result.getWs());
		assertEquals("yes",result.getEndgaps());
		assertEquals(Long.valueOf("14"),result.getMinw());
		assertEquals(Long.valueOf("24"),result.getMaxw());
		assertEquals(Double.valueOf("0.00"),result.getMinic());
		assertEquals("E-value of product of p-values",result.getObjectFunction());
		assertEquals("oops",result.getMod());
		assertEquals(Long.valueOf("2"),result.getNmotifs());
		assertEquals("inf",result.getEvt());
		assertEquals(Long.valueOf("24"),result.getMotifs().get(0).getWidth());
		assertEquals(6,result.getMotifs().get(0).getSites().size());
		assertEquals(Double.valueOf("90"),result.getMotifs().get(0).getLlr());
		assertEquals(Double.valueOf("2300"),result.getMotifs().get(0).getEvalue());
		assertEquals("393587",result.getMotifs().get(0).getSites().get(0).getSourceSequenceId());
		assertEquals(Long.valueOf("134"),result.getMotifs().get(0).getSites().get(0).getStart());
		assertEquals(Double.valueOf("0.000000000152"),result.getMotifs().get(0).getSites().get(0).getPvalue());
		assertEquals("ACTGGTTTTG",result.getMotifs().get(0).getSites().get(0).getLeftFlank());
		assertEquals("TCACGATTTTCAGGACATTCGTGA",result.getMotifs().get(0).getSites().get(0).getSequence());
		assertEquals("CCGCGTTGGC",result.getMotifs().get(0).getSites().get(0).getRightFlank());
	}

	@Test
	public final void testFindMotifsWithMemeJobFromWs() throws MalformedURLException, Exception {
		
		//AuthToken token = JsonClientCaller.requestTokenFromKBase(USER_NAME, PASSWORD.toCharArray());
		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		System.out.println(token.toString());
		String id = "KBase.SequenceSet.12345";
		URL serviceUrl = new URL(serverUrl);
//		MEMEClient client = new MEMEClient(serviceUrl, USER_NAME, PASSWORD);
		MEMEClient memeClient = new MEMEClient(serviceUrl, token);
		memeClient.setAuthAllowedForHttp(true);

//TODO: complete this test		
		
		
/*		System.out.println(input.getSequenceSetId());
		System.out.println(input.getSequences().get(0).getSequenceId());
		System.out.println(input.getSequences().get(0).getSequence());
*/		
		//set params
		MemeRunParameters params = new MemeRunParameters();
		params.setMod("oops");
		params.setNmotifs(2L);
		params.setMinw(14L);
		params.setMaxw(24L);
		params.setNsites(0L);
		params.setMinsites(0L);
		params.setMaxsites(0L);
		params.setPal(1L);
		params.setRevcomp(0L);

		
		String jobId = memeClient.findMotifsWithMemeJobFromWs("AKtest", id, params);
		
//Read result from WS
		
/*		List<ObjectIdentity> objectIds = new ArrayList<ObjectIdentity>();
		ObjectIdentity objectIdentity = new ObjectIdentity().withWorkspace("AKtest").withName(resultId);
		objectIds.add(objectIdentity);
		List<ObjectData> output = MemeServerImpl.wsClient(token.toString()).getObjects(objectIds);
		
		MemeRunResult result = UObject.transformObjectToObject(output.get(0).getData(), MemeRunResult.class);
*/
		String resultId = null;
		
		GetObjectParams objectParams = new GetObjectParams().withType("MemeRunResult").withId(resultId).withWorkspace(WSUtil.workspaceName).withAuth(WSUtil.authToken().toString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(objectParams);
		MemeRunResult result = UObject.transformObjectToObject(output.getData(), MemeRunResult.class);
		
		assertNotNull(jobId);

		assertEquals(Long.valueOf("0"),result.getSeed());
		assertEquals(Long.valueOf("1"),result.getSeqfrac());
		assertEquals("+",result.getStrands());
		assertEquals(Long.valueOf("1500"),result.getN());
		assertEquals(Long.valueOf("6"),result.getNCap());
		assertEquals(Double.valueOf("0.00001"),result.getDistance());
		assertEquals("dirichlet",result.getPrior());
		assertEquals(Double.valueOf("0.01"),result.getB());
		assertEquals(Long.valueOf("50"),result.getMaxiter());
		assertEquals("yes",result.getSubstring());
		assertEquals("no",result.getBranching());
		assertEquals("no",result.getWbranch());
		assertEquals(Long.valueOf("1"),result.getProb());
		assertEquals("uni",result.getSpmap());
		assertEquals("0.5",result.getSpfuzz());
		assertEquals(Long.valueOf("6"),result.getMinsites());
		assertEquals(Long.valueOf("6"),result.getMaxsites());
		assertEquals(Double.valueOf("0.8"),result.getWnsites());
		assertEquals(Long.valueOf("11"),result.getWg());
		assertEquals(Long.valueOf("1"),result.getWs());
		assertEquals("yes",result.getEndgaps());
		assertEquals(Long.valueOf("14"),result.getMinw());
		assertEquals(Long.valueOf("24"),result.getMaxw());
		assertEquals(Double.valueOf("0.00"),result.getMinic());
		assertEquals("E-value of product of p-values",result.getObjectFunction());
		assertEquals("oops",result.getMod());
		assertEquals(Long.valueOf("2"),result.getNmotifs());
		assertEquals("inf",result.getEvt());
		assertEquals(Long.valueOf("24"),result.getMotifs().get(0).getWidth());
		assertEquals(6,result.getMotifs().get(0).getSites().size());
		assertEquals(Double.valueOf("90"),result.getMotifs().get(0).getLlr());
		assertEquals(Double.valueOf("2300"),result.getMotifs().get(0).getEvalue());
		assertEquals("393587",result.getMotifs().get(0).getSites().get(0).getSourceSequenceId());
		assertEquals(Long.valueOf("134"),result.getMotifs().get(0).getSites().get(0).getStart());
		assertEquals(Double.valueOf("0.000000000152"),result.getMotifs().get(0).getSites().get(0).getPvalue());
		assertEquals("ACTGGTTTTG",result.getMotifs().get(0).getSites().get(0).getLeftFlank());
		assertEquals("TCACGATTTTCAGGACATTCGTGA",result.getMotifs().get(0).getSites().get(0).getSequence());
		assertEquals("CCGCGTTGGC",result.getMotifs().get(0).getSites().get(0).getRightFlank());
	}
	
	
	@Test
	public final void testCompareMotifsWithTomtomByCollection() throws Exception {
		URL serviceUrl = new URL(serverUrl);
		MEMEClient client = new MEMEClient(serviceUrl, USER_NAME, PASSWORD);
		client.setAuthAllowedForHttp(true);
		
		//set params
		MemeRunParameters params = new MemeRunParameters();
		params.setMod("oops");
		params.setNmotifs(2L);
		params.setMinw(14L);
		params.setMaxw(24L);
		params.setNsites(0L);
		params.setMinsites(0L);
		params.setMaxsites(0L);
		params.setPal(1L);
		params.setRevcomp(0L);
		
		memeRunResult = client.findMotifsWithMeme(testSequenceSet, params);
		MemePSPMCollection memePspmCollection = client.getPspmCollectionFromMemeResult(memeRunResult);
//		String distTomtom = new String("pearson".getBytes("UTF-8"), "ISO-8859-1");
		
		TomtomRunParameters paramsTomtom = new TomtomRunParameters();
		paramsTomtom.setDist("pearson");
		paramsTomtom.setThresh(0.0000001);
		paramsTomtom.setEvalue(0L);
		paramsTomtom.setInternal(1L);
		paramsTomtom.setMinOverlap(0L);
				
		TomtomRunResult result = client.compareMotifsWithTomtomByCollection(memePspmCollection, memePspmCollection, "", paramsTomtom);
		assertNotNull(result.getHits().get(0).getTargetPspmId());
		assertEquals(result.getHits().get(0).getTargetPspmId(),result.getHits().get(0).getQueryPspmId());
		assertEquals(Long.valueOf("0"),result.getHits().get(0).getOptimalOffset());
		assertEquals(Double.valueOf("3.89353E-37"),result.getHits().get(0).getPvalue());
		assertEquals(Double.valueOf("7.78706e-37"),result.getHits().get(0).getEvalue());
		assertEquals(Double.valueOf("7.78706e-37"),result.getHits().get(0).getQvalue());
		assertEquals(Long.valueOf("24"),result.getHits().get(0).getOverlap());
		assertEquals("TCACGCTCGTCATGACGAGCGTGA",result.getHits().get(0).getQueryConsensus());
		assertEquals("TCACGCTCGTCATGACGAGCGTGA",result.getHits().get(0).getTargetConsensus());
		assertEquals("+",result.getHits().get(0).getStrand());
	}

	@Test
	public final void testCompareMotifsWithTomtomByCollectionFromWs() throws Exception {
		
		URL serviceUrl = new URL(serverUrl);
		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		System.out.println(token.toString());

		MEMEClient client = new MEMEClient(serviceUrl, token);
		client.setAuthAllowedForHttp(true);

		
		TomtomRunParameters paramsTomtom = new TomtomRunParameters();
		paramsTomtom.setDist("pearson");
		paramsTomtom.setThresh(0.00001);
		paramsTomtom.setEvalue(0L);
		paramsTomtom.setInternal(0L);
		paramsTomtom.setMinOverlap(0L);
				
//		String resultId = client.compareMotifsWithTomtomByCollectionFromWs("AKtest", "kb|memepspmcollection.2", "RegPreciseMotifs_20131006", "", paramsTomtom);
		String resultId = client.compareMotifsWithTomtomByCollectionFromWs("AKtest", "kb|memepspmcollection.2", "kb|memepspmcollection.2", "", paramsTomtom);

/*		
		List<ObjectIdentity> objectIds = new ArrayList<ObjectIdentity>();
		ObjectIdentity objectIdentity = new ObjectIdentity().withWorkspace("AKtest").withName(resultId);
		objectIds.add(objectIdentity);
		List<ObjectData> output = MemeServerImpl.wsClient(token.getTokenData()).getObjects(objectIds);
		
		TomtomRunResult result = UObject.transformObjectToObject(output.get(0).getData(), TomtomRunResult.class);
*/		
		
		GetObjectParams params = new GetObjectParams().withType("TomtomRunResult").withId(resultId).withWorkspace("AKtest").withAuth(token.toString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(params);
		TomtomRunResult result = UObject.transformObjectToObject(output.getData(), TomtomRunResult.class);

		
		assertNotNull(result.getHits().get(0).getTargetPspmId());
		assertEquals(result.getHits().get(0).getTargetPspmId(),result.getHits().get(0).getQueryPspmId());
		assertEquals(Long.valueOf("0"),result.getHits().get(0).getOptimalOffset());
		assertEquals(Double.valueOf("3.89353E-37"),result.getHits().get(0).getPvalue());
		assertEquals(Double.valueOf("7.78706e-37"),result.getHits().get(0).getEvalue());
		assertEquals(Double.valueOf("7.78706e-37"),result.getHits().get(0).getQvalue());
		assertEquals(Long.valueOf("24"),result.getHits().get(0).getOverlap());
		assertEquals("TCACGCTCGTCATGACGAGCGTGA",result.getHits().get(0).getQueryConsensus());
		assertEquals("TCACGCTCGTCATGACGAGCGTGA",result.getHits().get(0).getTargetConsensus());
		assertEquals("+",result.getHits().get(0).getStrand());
	}
	
	
	@Test
	public final void testFindSitesWithMastByMotifCollection() throws Exception {
		URL serviceUrl = new URL(serverUrl);
		MEMEClient client = new MEMEClient(serviceUrl, USER_NAME, PASSWORD);
		client.setAuthAllowedForHttp(true);

		//set params
		MemeRunParameters params = new MemeRunParameters();
		params.setMod("oops");
		params.setNmotifs(2L);
		params.setMinw(14L);
		params.setMaxw(24L);
		params.setNsites(0L);
		params.setMinsites(0L);
		params.setMaxsites(0L);
		params.setPal(1L);
		params.setRevcomp(0L);

		memeRunResult = client.findMotifsWithMeme(testSequenceSet, params);
		MemePSPMCollection memePspmCollection = client.getPspmCollectionFromMemeResult(memeRunResult);
		MastRunResult result = client.findSitesWithMastByCollection(memePspmCollection, testSequenceSet, "", 0.0005);
		assertNotNull(result);
		assertFalse(result.getHits().size() == 0);
		assertEquals("209110", result.getHits().get(0).getSequenceId());
		assertEquals("+", result.getHits().get(0).getStrand());
		assertEquals("2", result.getHits().get(0).getPspmId());
		assertEquals(Long.valueOf("65"), result.getHits().get(0).getHitStart());
		assertEquals(Long.valueOf("78"), result.getHits().get(0).getHitEnd());
		assertEquals(Double.valueOf("1416.26"), result.getHits().get(0).getScore());
		assertEquals(Double.valueOf("0.0000101"), result.getHits().get(0).getHitPvalue());
	}

	@Test
	public final void testFindSitesWithMastByMotifCollectionFromWs() throws Exception {
		String seqId = "KBase.SequenceSet.12345";
		String pspmCollectionId = "KBase.MemePSPMCollection.1380921747486";
		URL serviceUrl = new URL(serverUrl);
		MEMEClient client = new MEMEClient(serviceUrl, USER_NAME, PASSWORD);
		client.setAuthAllowedForHttp(true);
		
		String resultId = client.findSitesWithMastByCollectionFromWs("AKtest", pspmCollectionId, seqId, "", 0.0005);

		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		
/*		List<ObjectIdentity> objectIds = new ArrayList<ObjectIdentity>();
		ObjectIdentity objectIdentity = new ObjectIdentity().withWorkspace("AKtest").withName(resultId);
		objectIds.add(objectIdentity);
		List<ObjectData> output = MemeServerImpl.wsClient(token.getTokenData()).getObjects(objectIds);
		
		MastRunResult result = UObject.transformObjectToObject(output.get(0).getData(), MastRunResult.class);
*/		
		GetObjectParams objectParams = new GetObjectParams().withType("MastRunResult").withId(resultId).withWorkspace("AKtest").withAuth(token.toString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(objectParams);
		MastRunResult result = UObject.transformObjectToObject(output.getData(), MastRunResult.class);
		
		
		assertNotNull(result);
		assertFalse(result.getHits().size() == 0);
		assertEquals("209110", result.getHits().get(0).getSequenceId());
		assertEquals("+", result.getHits().get(0).getStrand());
		assertEquals("2", result.getHits().get(0).getPspmId());
		assertEquals(Long.valueOf("65"), result.getHits().get(0).getHitStart());
		assertEquals(Long.valueOf("78"), result.getHits().get(0).getHitEnd());
		assertEquals(Double.valueOf("1416.26"), result.getHits().get(0).getScore());
		assertEquals(Double.valueOf("0.0000101"), result.getHits().get(0).getHitPvalue());
	}

	@Test
	public final void testFindSitesWithMast() throws Exception {
		String memePspmId = "kb|memepspm.14"; 
		URL serviceUrl = new URL(serverUrl);
		MEMEClient client = new MEMEClient(serviceUrl, USER_NAME, PASSWORD);
		client.setAuthAllowedForHttp(true);

		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		
/*		List<ObjectIdentity> objectIds = new ArrayList<ObjectIdentity>();
		ObjectIdentity objectIdentity = new ObjectIdentity().withWorkspace("AKtest").withName(memePspmId);
		objectIds.add(objectIdentity);
		List<ObjectData> output = MemeServerImpl.wsClient(token.getTokenData()).getObjects(objectIds);
		
		MemePSPM pspm = UObject.transformObjectToObject(output.get(0).getData(), MemePSPM.class);
*/		
		
		GetObjectParams objectParams = new GetObjectParams().withType("MemePSPM").withId(memePspmId).withWorkspace("AKtest").withAuth(token.toString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(objectParams);
		MemePSPM pspm = UObject.transformObjectToObject(output.getData(), MemePSPM.class);

		MastRunResult result = client.findSitesWithMast(pspm, testSequenceSet, 0.0005);
		assertNotNull(result);
		assertFalse(result.getHits().size() == 0);
		assertEquals("209110", result.getHits().get(0).getSequenceId());
		assertEquals("+", result.getHits().get(0).getStrand());
		assertEquals("1", result.getHits().get(0).getPspmId());
		assertEquals(Long.valueOf("122"), result.getHits().get(0).getHitStart());
		assertEquals(Long.valueOf("145"), result.getHits().get(0).getHitEnd());
		assertEquals(Double.valueOf("2594.71"), result.getHits().get(0).getScore());
		assertEquals(Double.valueOf("0.000000000582"), result.getHits().get(0).getHitPvalue());
	}
	
	@Test
	public final void testFindSitesWithMastFromWs() throws Exception {
		URL serviceUrl = new URL(serverUrl);
		MEMEClient client = new MEMEClient(serviceUrl, USER_NAME, PASSWORD);
		client.setAuthAllowedForHttp(true);

		String resultId = client.findSitesWithMastFromWs("AKtest", "kb|memepspm.14", "KBase.SequenceSet.12345", 0.0005);
		
		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		
/*		List<ObjectIdentity> objectIds = new ArrayList<ObjectIdentity>();
		ObjectIdentity objectIdentity = new ObjectIdentity().withWorkspace("AKtest").withName(resultId);
		objectIds.add(objectIdentity);
		List<ObjectData> output = MemeServerImpl.wsClient(token.getTokenData()).getObjects(objectIds);
		
		MastRunResult result = UObject.transformObjectToObject(output.get(0).getData(), MastRunResult.class);
*/
		
		GetObjectParams objectParams = new GetObjectParams().withType("MastRunResult").withId(resultId).withWorkspace("AKtest").withAuth(token.toString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(objectParams);
		MastRunResult result = UObject.transformObjectToObject(output.getData(), MastRunResult.class);

		
		assertNotNull(result);
		assertFalse(result.getHits().size() == 0);
		assertEquals("209110", result.getHits().get(0).getSequenceId());
		assertEquals("+", result.getHits().get(0).getStrand());
		assertEquals("1", result.getHits().get(0).getPspmId());
		assertEquals(Long.valueOf("122"), result.getHits().get(0).getHitStart());
		assertEquals(Long.valueOf("145"), result.getHits().get(0).getHitEnd());
		assertEquals(Double.valueOf("2594.71"), result.getHits().get(0).getScore());
		assertEquals(Double.valueOf("0.000000000582"), result.getHits().get(0).getHitPvalue());
	}

	
	@Test
	public final void testGetPspmCollectionFromMemeResultFromWs() throws Exception {
		String id = "KBase.MemeRunResult.1380917552760";
		URL serviceUrl = new URL(serverUrl);
		MEMEClient client = new MEMEClient(serviceUrl, USER_NAME, PASSWORD);
		client.setAuthAllowedForHttp(true);
		
		String resultId = client.getPspmCollectionFromMemeResultFromWs("AKtest", id);

		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		
/*		List<ObjectIdentity> objectIds = new ArrayList<ObjectIdentity>();
		ObjectIdentity objectIdentity = new ObjectIdentity().withWorkspace("AKtest").withName(resultId);
		objectIds.add(objectIdentity);
		List<ObjectData> output = MemeServerImpl.wsClient(token.getTokenData()).getObjects(objectIds);
		
		MemePSPMCollection result = UObject.transformObjectToObject(output.get(0).getData(), MemePSPMCollection.class);
*/
		//set params
		GetObjectParams objectParams = new GetObjectParams().withType("MemePSPMCollection").withId(resultId).withWorkspace("AKtest").withAuth(token.toString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(objectParams);
		MemePSPMCollection result = UObject.transformObjectToObject(output.getData(), MemePSPMCollection.class);

		assertNotNull(result);
		assertFalse(result.getPspms().size() == 0);
		assertEquals("ACGT", result.getAlphabet());
	}

	@Test
	public final void testGetPspmCollectionFromMemeResult() throws Exception {
		String id = "KBase.MemeRunResult.1380917552760";
		URL serviceUrl = new URL(serverUrl);
		MEMEClient client = new MEMEClient(serviceUrl, USER_NAME, PASSWORD);
		client.setAuthAllowedForHttp(true);

		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		
/*		List<ObjectIdentity> objectIds = new ArrayList<ObjectIdentity>();
		ObjectIdentity objectIdentity = new ObjectIdentity().withWorkspace("AKtest").withName(id);
		objectIds.add(objectIdentity);
		List<ObjectData> output = MemeServerImpl.wsClient(token.getTokenData()).getObjects(objectIds);
		
		MemeRunResult input = UObject.transformObjectToObject(output.get(0).getData(), MemeRunResult.class);
*/
		GetObjectParams memeParams = new GetObjectParams().withType("MemeRunResult").withId(id).withWorkspace("AKtest").withAuth(token.toString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(memeParams);
		MemeRunResult input = UObject.transformObjectToObject(output.getData(), MemeRunResult.class);

		
		MemePSPMCollection result = client.getPspmCollectionFromMemeResult(input);
		assertNotNull(result);
		assertNotNull(result.getId());
		assertFalse(result.getPspms().size() == 0);
		assertEquals("ACGT", result.getAlphabet());
		//Write result to WS
		MemeServerImpl.saveObjectToWorkspace(UObject.transformObjectToObject(result, UObject.class), result.getClass().getSimpleName(), "AKtest", result.getId(), token.toString());

		
//		WSUtil.saveObject(result.getId(), result, false);
		
		assertNotNull(result);
		assertFalse(result.getPspms().size() == 0);
		assertEquals("ACGT", result.getAlphabet());
	}

}
