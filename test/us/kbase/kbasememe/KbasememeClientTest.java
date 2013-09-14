package us.kbase.kbasememe;

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

public class KbasememeClientTest {
	
	private SequenceSet testSequenceSet = new SequenceSet();
	private MotifCollectionMeme motifCollection = new MotifCollectionMeme();

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
		motifCollection = null;
	}

	@Test
	public final void testKbasememeClient() throws MalformedURLException {
		KbasememeClient client = new KbasememeClient("http://localhost:1111");
		assertNotNull(client);
	}

	@Test
	public final void testSearchMotifsFromSequencesWithMeme() throws Exception {
		KbasememeClient client = new KbasememeClient("http://localhost:1111");
		MotifCollectionMeme result = client.searchMotifsFromSequencesWithMeme(testSequenceSet, "oops", 2, 14, 24, 0, 0, 0, 1, 0);

		assertEquals(Integer.valueOf("0"),result.getSeedMeme());
		assertEquals(Integer.valueOf("1"),result.getSeqfracMeme());
		assertEquals("+",result.getStrandsMeme());
		assertEquals(Integer.valueOf("1500"),result.getNMeme());
		assertEquals(Integer.valueOf("6"),result.getNCapMeme());
		assertEquals(Double.valueOf("0.00001"),result.getDistanceMeme());
		assertEquals("dirichlet",result.getPriorMeme());
		assertEquals(Double.valueOf("0.01"),result.getBMeme());
		assertEquals(Integer.valueOf("50"),result.getMaxiterMeme());
		assertEquals("yes",result.getSubstringMeme());
		assertEquals("no",result.getBranchingMeme());
		assertEquals("no",result.getWbranchMeme());
		assertEquals(Integer.valueOf("1"),result.getProbMeme());
		assertEquals("uni",result.getSpmapMeme());
		assertEquals("0.5",result.getSpfuzzMeme());
		assertEquals(Integer.valueOf("6"),result.getMinsitesMeme());
		assertEquals(Integer.valueOf("6"),result.getMaxsitesMeme());
		assertEquals(Double.valueOf("0.8"),result.getWnsitesMeme());
		assertEquals(Integer.valueOf("11"),result.getWgMeme());
		assertEquals(Integer.valueOf("1"),result.getWsMeme());
		assertEquals("yes",result.getEndgapsMeme());
		assertEquals(Integer.valueOf("14"),result.getMinwMeme());
		assertEquals(Integer.valueOf("24"),result.getMaxwMeme());
		assertEquals(Double.valueOf("0.00"),result.getMinicMeme());
		assertEquals("E-value of product of p-values",result.getObjectFunctionMeme());
		assertEquals("oops",result.getModMeme());
		assertEquals(Integer.valueOf("2"),result.getNmotifsMeme());
		assertEquals("inf",result.getEvtMeme());
		assertEquals(Integer.valueOf("1"),result.getMotifs().get(0).getMotifName());
		assertEquals(Integer.valueOf("24"),result.getMotifs().get(0).getMotifWidthMeme());
		assertEquals(Integer.valueOf("6"),result.getMotifs().get(0).getMotifSitesMeme());
		assertEquals(Double.valueOf("90"),result.getMotifs().get(0).getMotifLlrMeme());
		assertEquals(Double.valueOf("2300"),result.getMotifs().get(0).getMotifEvalueMeme());
		assertEquals("393587",result.getMotifs().get(0).getSites().get(0).getSiteSequenceName());
		assertEquals(Integer.valueOf("134"),result.getMotifs().get(0).getSites().get(0).getSiteStart());
		assertEquals(Double.valueOf("0.000000000152"),result.getMotifs().get(0).getSites().get(0).getSitePvalue());
		assertEquals("ACTGGTTTTG",result.getMotifs().get(0).getSites().get(0).getSiteLeftFlank());
		assertEquals("TCACGATTTTCAGGACATTCGTGA",result.getMotifs().get(0).getSites().get(0).getSiteSequence());
		assertEquals("CCGCGTTGGC",result.getMotifs().get(0).getSites().get(0).getSiteRightFlank());
	}

	@Test
	public final void testSearchMotifsFromWorkspaceWithMeme() throws MalformedURLException, Exception {
		String id = "testSequenceSet1";
		GetObjectParams params = new GetObjectParams().withType("SequenceSet").withId(id).withWorkspace(WSUtil.workspaceName).withAuth(WSUtil.authToken().toString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(params);
		SequenceSet input = UObject.transform(output.getData(), SequenceSet.class);
		KbasememeClient client = new KbasememeClient("http://localhost:1111");
		MotifCollectionMeme result = client.searchMotifsFromSequencesWithMeme(input, "oops", 2, 14, 24, 0, 0, 0, 1, 0);

		assertEquals(Integer.valueOf("0"),result.getSeedMeme());
		assertEquals(Integer.valueOf("1"),result.getSeqfracMeme());
		assertEquals("+",result.getStrandsMeme());
		assertEquals(Integer.valueOf("1500"),result.getNMeme());
		assertEquals(Integer.valueOf("6"),result.getNCapMeme());
		assertEquals(Double.valueOf("0.00001"),result.getDistanceMeme());
		assertEquals("dirichlet",result.getPriorMeme());
		assertEquals(Double.valueOf("0.01"),result.getBMeme());
		assertEquals(Integer.valueOf("50"),result.getMaxiterMeme());
		assertEquals("yes",result.getSubstringMeme());
		assertEquals("no",result.getBranchingMeme());
		assertEquals("no",result.getWbranchMeme());
		assertEquals(Integer.valueOf("1"),result.getProbMeme());
		assertEquals("uni",result.getSpmapMeme());
		assertEquals("0.5",result.getSpfuzzMeme());
		assertEquals(Integer.valueOf("6"),result.getMinsitesMeme());
		assertEquals(Integer.valueOf("6"),result.getMaxsitesMeme());
		assertEquals(Double.valueOf("0.8"),result.getWnsitesMeme());
		assertEquals(Integer.valueOf("11"),result.getWgMeme());
		assertEquals(Integer.valueOf("1"),result.getWsMeme());
		assertEquals("yes",result.getEndgapsMeme());
		assertEquals(Integer.valueOf("14"),result.getMinwMeme());
		assertEquals(Integer.valueOf("24"),result.getMaxwMeme());
		assertEquals(Double.valueOf("0.00"),result.getMinicMeme());
		assertEquals("E-value of product of p-values",result.getObjectFunctionMeme());
		assertEquals("oops",result.getModMeme());
		assertEquals(Integer.valueOf("2"),result.getNmotifsMeme());
		assertEquals("inf",result.getEvtMeme());
		assertEquals(Integer.valueOf("1"),result.getMotifs().get(0).getMotifName());
		assertEquals(Integer.valueOf("24"),result.getMotifs().get(0).getMotifWidthMeme());
		assertEquals(Integer.valueOf("6"),result.getMotifs().get(0).getMotifSitesMeme());
		assertEquals(Double.valueOf("90"),result.getMotifs().get(0).getMotifLlrMeme());
		assertEquals(Double.valueOf("2300"),result.getMotifs().get(0).getMotifEvalueMeme());
		assertEquals("393587",result.getMotifs().get(0).getSites().get(0).getSiteSequenceName());
		assertEquals(Integer.valueOf("134"),result.getMotifs().get(0).getSites().get(0).getSiteStart());
		assertEquals(Double.valueOf("0.000000000152"),result.getMotifs().get(0).getSites().get(0).getSitePvalue());
		assertEquals("ACTGGTTTTG",result.getMotifs().get(0).getSites().get(0).getSiteLeftFlank());
		assertEquals("TCACGATTTTCAGGACATTCGTGA",result.getMotifs().get(0).getSites().get(0).getSiteSequence());
		assertEquals("CCGCGTTGGC",result.getMotifs().get(0).getSites().get(0).getSiteRightFlank());
	}

	@Test
	public final void testCompareMotifsWithTomtom() throws Exception {
		KbasememeClient client = new KbasememeClient("http://localhost:1111");
		motifCollection = client.searchMotifsFromSequencesWithMeme(testSequenceSet, "oops", 2, 14, 24, 0, 0, 0, 1, 0);
//		String distTomtom = new String("pearson".getBytes("UTF-8"), "ISO-8859-1");
		String distTomtom = "pearson";
		List<HitTomtom> result = client.compareMotifsWithTomtom(motifCollection, motifCollection, 0.00, 0, distTomtom, 0, 0);
		assertEquals("1",result.get(0).getQueryMotifName());
		assertEquals("1",result.get(0).getTargetMotifName());
		assertEquals(Integer.valueOf("0"),result.get(0).getOptimalOffset());
		assertEquals(Double.valueOf("3.89353E-37"),result.get(0).getPValue());
		assertEquals(Double.valueOf("7.78706e-37"),result.get(0).getEValue());
		assertEquals(Double.valueOf("7.78706e-37"),result.get(0).getQValue());
		assertEquals(Integer.valueOf("24"),result.get(0).getOverlap());
		assertEquals("TCACGCTCGTCATGACGAGCGTGA",result.get(0).getQueryConsensus());
		assertEquals("TCACGCTCGTCATGACGAGCGTGA",result.get(0).getTargetConsensus());
		assertEquals("+",result.get(0).getStrand());
	}

	@Test
	public final void testFindSitesByMotifCollectionWithMast() throws Exception {
		KbasememeClient client = new KbasememeClient("http://localhost:1111");
		motifCollection = client.searchMotifsFromSequencesWithMeme(testSequenceSet, "oops", 2, 14, 24, 0, 0, 0, 1, 0);
		List<HitMast> result = client.findSitesByMotifCollectionWithMast(motifCollection, testSequenceSet, 0.0005);
		assertNotNull(result);
		assertFalse(result.size() == 0);
		assertEquals("209110", result.get(0).getSequenceName());
		assertEquals("+", result.get(0).getStrand());
		assertEquals("2", result.get(0).getMotifName());
		assertEquals(Integer.valueOf("65"), result.get(0).getHitStart());
		assertEquals(Integer.valueOf("78"), result.get(0).getHitEnd());
		assertEquals(Double.valueOf("1527.00"), result.get(0).getScore());
		assertEquals(Double.valueOf("0.0000253"), result.get(0).getHitPvalue());
	}

	@Test
	public final void testFindSitesByMotifCollectionWsWithMast() throws Exception {
		String id = "testSequenceSet1";
		GetObjectParams params = new GetObjectParams().withType("SequenceSet").withId(id).withWorkspace(WSUtil.workspaceName).withAuth(WSUtil.authToken().toString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(params);
		SequenceSet input = UObject.transform(output.getData(), SequenceSet.class);
		KbasememeClient client = new KbasememeClient("http://localhost:1111");
		motifCollection = client.searchMotifsFromSequencesWithMeme(testSequenceSet, "oops", 2, 14, 24, 0, 0, 0, 1, 0);
		List<HitMast> result = client.findSitesByMotifCollectionWithMast(motifCollection, input, 0.0005);
		assertNotNull(result);
		assertFalse(result.size() == 0);
		assertEquals("209110", result.get(0).getSequenceName());
		assertEquals("+", result.get(0).getStrand());
		assertEquals("2", result.get(0).getMotifName());
		assertEquals(Integer.valueOf("65"), result.get(0).getHitStart());
		assertEquals(Integer.valueOf("78"), result.get(0).getHitEnd());
		assertEquals(Double.valueOf("1527.00"), result.get(0).getScore());
		assertEquals(Double.valueOf("0.0000253"), result.get(0).getHitPvalue());
	}

}
