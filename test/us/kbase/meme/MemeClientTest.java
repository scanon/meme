package us.kbase.meme;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import us.kbase.UObject;
import us.kbase.util.WSUtil;
import us.kbase.workspaceservice.GetObjectOutput;
import us.kbase.workspaceservice.GetObjectParams;
import us.kbase.generaltypes.Sequence;
import us.kbase.generaltypes.SequenceSet;

public class MemeClientTest {
	
	private SequenceSet testSequenceSet = new SequenceSet();
	private MemeRunResult memeRunResult = new MemeRunResult();
//	private String serverUrl = "http://140.221.84.195:7049";
	private String serverUrl = "http://127.0.0.1:1111";

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
		MemeClient client = new MemeClient(serverUrl, "aktest", "1475rokegi");
		client.setAuthAllowedForHttp(true);
//    	WSUtil.saveObject(testSequenceSet.getSequenceSetId(), testSequenceSet, false);
		assertNotNull(client);
	}

	@Test
	public final void testSearchMotifsFromSequencesWithMeme() throws Exception {
		MemeClient client = new MemeClient(serverUrl, "aktest", "1475rokegi");
		client.setAuthAllowedForHttp(true);
		
		//set params
		MemeRunParameters params = new MemeRunParameters();
		params.setMod("oops");
		params.setNmotifs(2);
		params.setMinw(14);
		params.setMaxw(24);
		params.setNsites(0);
		params.setMinsites(0);
		params.setMaxsites(0);
		params.setPal(1);
		params.setRevcomp(0);
		
		MemeRunResult result = client.findMotifsWithMeme(testSequenceSet, params);

		assertEquals(Integer.valueOf("0"),result.getSeed());
		assertEquals(Integer.valueOf("1"),result.getSeqfrac());
		assertEquals("+",result.getStrands());
		assertEquals(Integer.valueOf("1500"),result.getN());
		assertEquals(Integer.valueOf("6"),result.getNCap());
		assertEquals(Double.valueOf("0.00001"),result.getDistance());
		assertEquals("dirichlet",result.getPrior());
		assertEquals(Double.valueOf("0.01"),result.getB());
		assertEquals(Integer.valueOf("50"),result.getMaxiter());
		assertEquals("yes",result.getSubstring());
		assertEquals("no",result.getBranching());
		assertEquals("no",result.getWbranch());
		assertEquals(Integer.valueOf("1"),result.getProb());
		assertEquals("uni",result.getSpmap());
		assertEquals("0.5",result.getSpfuzz());
		assertEquals(Integer.valueOf("6"),result.getMinsites());
		assertEquals(Integer.valueOf("6"),result.getMaxsites());
		assertEquals(Double.valueOf("0.8"),result.getWnsites());
		assertEquals(Integer.valueOf("11"),result.getWg());
		assertEquals(Integer.valueOf("1"),result.getWs());
		assertEquals("yes",result.getEndgaps());
		assertEquals(Integer.valueOf("14"),result.getMinw());
		assertEquals(Integer.valueOf("24"),result.getMaxw());
		assertEquals(Double.valueOf("0.00"),result.getMinic());
		assertEquals("E-value of product of p-values",result.getObjectFunction());
		assertEquals("oops",result.getMod());
		assertEquals(Integer.valueOf("2"),result.getNmotifs());
		assertEquals("inf",result.getEvt());
		assertNotNull(result.getMotifs().get(0).getId());
		assertEquals(Integer.valueOf("24"),result.getMotifs().get(0).getWidth());
		assertEquals(6,result.getMotifs().get(0).getSites().size());
		assertEquals(Double.valueOf("90"),result.getMotifs().get(0).getLlr());
		assertEquals(Double.valueOf("2300"),result.getMotifs().get(0).getEvalue());
		assertEquals("393587",result.getMotifs().get(0).getSites().get(0).getSourceSequenceId());
		assertEquals(Integer.valueOf("134"),result.getMotifs().get(0).getSites().get(0).getStart());
		assertEquals(Double.valueOf("0.000000000152"),result.getMotifs().get(0).getSites().get(0).getPvalue());
		assertEquals("ACTGGTTTTG",result.getMotifs().get(0).getSites().get(0).getLeftFlank());
		assertEquals("TCACGATTTTCAGGACATTCGTGA",result.getMotifs().get(0).getSites().get(0).getSequence());
		assertEquals("CCGCGTTGGC",result.getMotifs().get(0).getSites().get(0).getRightFlank());
	}

	@Test
	public final void testSearchMotifsFromWorkspaceWithMeme() throws MalformedURLException, Exception {
		
		String id = "KBase.SequenceSet.12345";
		GetObjectParams objectParams = new GetObjectParams().withType("SequenceSet").withId(id).withWorkspace(WSUtil.workspaceName).withAuth(WSUtil.authToken().toString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(objectParams);
		SequenceSet input = UObject.transform(output.getData(), SequenceSet.class);
		MemeClient client = new MemeClient(serverUrl, "aktest", "1475rokegi");
		client.setAuthAllowedForHttp(true);
		
/*		System.out.println(input.getSequenceSetId());
		System.out.println(input.getSequences().get(0).getSequenceId());
		System.out.println(input.getSequences().get(0).getSequence());
*/		
		//set params
		MemeRunParameters params = new MemeRunParameters();
		params.setMod("oops");
		params.setNmotifs(2);
		params.setMinw(14);
		params.setMaxw(24);
		params.setNsites(0);
		params.setMinsites(0);
		params.setMaxsites(0);
		params.setPal(1);
		params.setRevcomp(0);

		
		MemeRunResult result = client.findMotifsWithMeme(input, params);
//Write result to WS
		WSUtil.saveObject(result.getId(), result, false);
		
		assertEquals(Integer.valueOf("0"),result.getSeed());
		assertEquals(Integer.valueOf("1"),result.getSeqfrac());
		assertEquals("+",result.getStrands());
		assertEquals(Integer.valueOf("1500"),result.getN());
		assertEquals(Integer.valueOf("6"),result.getNCap());
		assertEquals(Double.valueOf("0.00001"),result.getDistance());
		assertEquals("dirichlet",result.getPrior());
		assertEquals(Double.valueOf("0.01"),result.getB());
		assertEquals(Integer.valueOf("50"),result.getMaxiter());
		assertEquals("yes",result.getSubstring());
		assertEquals("no",result.getBranching());
		assertEquals("no",result.getWbranch());
		assertEquals(Integer.valueOf("1"),result.getProb());
		assertEquals("uni",result.getSpmap());
		assertEquals("0.5",result.getSpfuzz());
		assertEquals(Integer.valueOf("6"),result.getMinsites());
		assertEquals(Integer.valueOf("6"),result.getMaxsites());
		assertEquals(Double.valueOf("0.8"),result.getWnsites());
		assertEquals(Integer.valueOf("11"),result.getWg());
		assertEquals(Integer.valueOf("1"),result.getWs());
		assertEquals("yes",result.getEndgaps());
		assertEquals(Integer.valueOf("14"),result.getMinw());
		assertEquals(Integer.valueOf("24"),result.getMaxw());
		assertEquals(Double.valueOf("0.00"),result.getMinic());
		assertEquals("E-value of product of p-values",result.getObjectFunction());
		assertEquals("oops",result.getMod());
		assertEquals(Integer.valueOf("2"),result.getNmotifs());
		assertEquals("inf",result.getEvt());
		assertEquals(Integer.valueOf("24"),result.getMotifs().get(0).getWidth());
		assertEquals(6,result.getMotifs().get(0).getSites().size());
		assertEquals(Double.valueOf("90"),result.getMotifs().get(0).getLlr());
		assertEquals(Double.valueOf("2300"),result.getMotifs().get(0).getEvalue());
		assertEquals("393587",result.getMotifs().get(0).getSites().get(0).getSourceSequenceId());
		assertEquals(Integer.valueOf("134"),result.getMotifs().get(0).getSites().get(0).getStart());
		assertEquals(Double.valueOf("0.000000000152"),result.getMotifs().get(0).getSites().get(0).getPvalue());
		assertEquals("ACTGGTTTTG",result.getMotifs().get(0).getSites().get(0).getLeftFlank());
		assertEquals("TCACGATTTTCAGGACATTCGTGA",result.getMotifs().get(0).getSites().get(0).getSequence());
		assertEquals("CCGCGTTGGC",result.getMotifs().get(0).getSites().get(0).getRightFlank());
	}

	@Test
	public final void testCompareMotifsWithTomtomByCollection() throws Exception {
		MemeClient client = new MemeClient(serverUrl, "aktest", "1475rokegi");
		client.setAuthAllowedForHttp(true);
		
		//set params
		MemeRunParameters params = new MemeRunParameters();
		params.setMod("oops");
		params.setNmotifs(2);
		params.setMinw(14);
		params.setMaxw(24);
		params.setNsites(0);
		params.setMinsites(0);
		params.setMaxsites(0);
		params.setPal(1);
		params.setRevcomp(0);
		
		memeRunResult = client.findMotifsWithMeme(testSequenceSet, params);
		MemePSPMCollection memePspmCollection = client.getPspmCollectionFromMemeResult(memeRunResult);
//		String distTomtom = new String("pearson".getBytes("UTF-8"), "ISO-8859-1");
		
		TomtomRunParameters paramsTomtom = new TomtomRunParameters();
		paramsTomtom.setDist("pearson");
		paramsTomtom.setThresh(0.00);
		paramsTomtom.setEvalue(0);
		paramsTomtom.setInternal(0);
		paramsTomtom.setMinOverlap(0);
				
		TomtomRunResult result = client.compareMotifsWithTomtomByCollection(memePspmCollection, memePspmCollection, "", paramsTomtom);
		assertNotNull(result.getHits().get(0).getTargetPspmId());
		assertEquals(result.getHits().get(0).getTargetPspmId(),result.getHits().get(0).getQueryPspmId());
		assertEquals(Integer.valueOf("0"),result.getHits().get(0).getOptimalOffset());
		assertEquals(Double.valueOf("3.89353E-37"),result.getHits().get(0).getPvalue());
		assertEquals(Double.valueOf("7.78706e-37"),result.getHits().get(0).getEvalue());
		assertEquals(Double.valueOf("7.78706e-37"),result.getHits().get(0).getQvalue());
		assertEquals(Integer.valueOf("24"),result.getHits().get(0).getOverlap());
		assertEquals("TCACGCTCGTCATGACGAGCGTGA",result.getHits().get(0).getQueryConsensus());
		assertEquals("TCACGCTCGTCATGACGAGCGTGA",result.getHits().get(0).getTargetConsensus());
		assertEquals("+",result.getHits().get(0).getStrand());
	}

	@Test
	public final void testCompareMotifsWithTomtomByCollectionFromWs() throws Exception {
		MemeClient client = new MemeClient(serverUrl, "aktest", "1475rokegi");
		client.setAuthAllowedForHttp(true);
		
		
		TomtomRunParameters paramsTomtom = new TomtomRunParameters();
		paramsTomtom.setDist("pearson");
		paramsTomtom.setThresh(0.00);
		paramsTomtom.setEvalue(0);
		paramsTomtom.setInternal(0);
		paramsTomtom.setMinOverlap(0);
				
		String resultId = client.compareMotifsWithTomtomByCollectionFromWs("AKtest", "KBase.MemePSPM.1380920063267", "KBase.MemePSPM.1380920063267", "", paramsTomtom);
		GetObjectParams params = new GetObjectParams().withType("TomtomRunResult").withId(resultId).withWorkspace("AKtest").withAuth(WSUtil.authToken().toString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(params);
		TomtomRunResult result = UObject.transform(output.getData(), TomtomRunResult.class);

		
		assertNotNull(result.getHits().get(0).getTargetPspmId());
		assertEquals(result.getHits().get(0).getTargetPspmId(),result.getHits().get(0).getQueryPspmId());
		assertEquals(Integer.valueOf("0"),result.getHits().get(0).getOptimalOffset());
		assertEquals(Double.valueOf("3.89353E-37"),result.getHits().get(0).getPvalue());
		assertEquals(Double.valueOf("7.78706e-37"),result.getHits().get(0).getEvalue());
		assertEquals(Double.valueOf("7.78706e-37"),result.getHits().get(0).getQvalue());
		assertEquals(Integer.valueOf("24"),result.getHits().get(0).getOverlap());
		assertEquals("TCACGCTCGTCATGACGAGCGTGA",result.getHits().get(0).getQueryConsensus());
		assertEquals("TCACGCTCGTCATGACGAGCGTGA",result.getHits().get(0).getTargetConsensus());
		assertEquals("+",result.getHits().get(0).getStrand());
	}
	
	
	@Test
	public final void testFindSitesByMotifCollectionWithMast() throws Exception {
		MemeClient client = new MemeClient(serverUrl, "aktest", "1475rokegi");
		client.setAuthAllowedForHttp(true);

		//set params
		MemeRunParameters params = new MemeRunParameters();
		params.setMod("oops");
		params.setNmotifs(2);
		params.setMinw(14);
		params.setMaxw(24);
		params.setNsites(0);
		params.setMinsites(0);
		params.setMaxsites(0);
		params.setPal(1);
		params.setRevcomp(0);

		memeRunResult = client.findMotifsWithMeme(testSequenceSet, params);
		MemePSPMCollection memePspmCollection = client.getPspmCollectionFromMemeResult(memeRunResult);
		MastRunResult result = client.findSitesWithMastByCollection(memePspmCollection, testSequenceSet, "", 0.0005);
		assertNotNull(result);
		assertFalse(result.getHits().size() == 0);
		assertEquals("209110", result.getHits().get(0).getSequenceId());
		assertEquals("+", result.getHits().get(0).getStrand());
		assertEquals("2", result.getHits().get(0).getPspmId());
		assertEquals(Integer.valueOf("65"), result.getHits().get(0).getHitStart());
		assertEquals(Integer.valueOf("78"), result.getHits().get(0).getHitEnd());
		assertEquals(Double.valueOf("1416.26"), result.getHits().get(0).getScore());
		assertEquals(Double.valueOf("0.0000101"), result.getHits().get(0).getHitPvalue());
	}

	@Test
	public final void testFindSitesByMotifCollectionWsWithMast() throws Exception {
		String seqId = "KBase.SequenceSet.12345";
		String pspmCollectionId = "KBase.MemePSPMCollection.1380921747486";
		MemeClient client = new MemeClient(serverUrl, "aktest", "1475rokegi");
		client.setAuthAllowedForHttp(true);
		
		String resultId = client.findSitesWithMastByCollectionFromWs("AKtest", pspmCollectionId, seqId, "", 0.0005);

		GetObjectParams objectParams = new GetObjectParams().withType("MastRunResult").withId(resultId).withWorkspace(WSUtil.workspaceName).withAuth(WSUtil.authToken().toString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(objectParams);
		MastRunResult result = UObject.transform(output.getData(), MastRunResult.class);
		
		
		assertNotNull(result);
		assertFalse(result.getHits().size() == 0);
		assertEquals("209110", result.getHits().get(0).getSequenceId());
		assertEquals("+", result.getHits().get(0).getStrand());
		assertEquals("2", result.getHits().get(0).getPspmId());
		assertEquals(Integer.valueOf("65"), result.getHits().get(0).getHitStart());
		assertEquals(Integer.valueOf("78"), result.getHits().get(0).getHitEnd());
		assertEquals(Double.valueOf("1416.26"), result.getHits().get(0).getScore());
		assertEquals(Double.valueOf("0.0000101"), result.getHits().get(0).getHitPvalue());
	}
	
	@Test
	public final void testGetPspmCollectionFromMemeResultFromWs() throws Exception {
		String id = "KBase.MemeRunResult.1380917552760";
		MemeClient client = new MemeClient(serverUrl, "aktest", "1475rokegi");
		client.setAuthAllowedForHttp(true);
		
		String resultId = client.getPspmCollectionFromMemeResultFromWs("AKtest", id);
		//set params
		GetObjectParams objectParams = new GetObjectParams().withType("MemePSPMCollection").withId(resultId).withWorkspace(WSUtil.workspaceName).withAuth(WSUtil.authToken().toString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(objectParams);
		MemePSPMCollection result = UObject.transform(output.getData(), MemePSPMCollection.class);

		assertNotNull(result);
		assertFalse(result.getPspms().size() == 0);
		assertEquals("ACGT", result.getAlphabet());
	}

	@Test
	public final void testGetPspmCollectionFromMemeResult() throws Exception {
		String id = "KBase.MemeRunResult.1380917552760";
		MemeClient client = new MemeClient(serverUrl, "aktest", "1475rokegi");
		client.setAuthAllowedForHttp(true);

		GetObjectParams memeParams = new GetObjectParams().withType("MemeRunResult").withId(id).withWorkspace(WSUtil.workspaceName).withAuth(WSUtil.authToken().toString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(memeParams);
		MemeRunResult input = UObject.transform(output.getData(), MemeRunResult.class);

		
		MemePSPMCollection result = client.getPspmCollectionFromMemeResult(input);
		assertNotNull(result);
		assertNotNull(result.getId());
		assertFalse(result.getPspms().size() == 0);
		assertEquals("ACGT", result.getAlphabet());
		//Write result to WS
				WSUtil.saveObject(result.getId(), result, false);
		
		assertNotNull(result);
		assertFalse(result.getPspms().size() == 0);
		assertEquals("ACGT", result.getAlphabet());
	}

}
