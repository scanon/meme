package us.kbase.kbasememe;

import static org.junit.Assert.*;
//import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import us.kbase.UObject;
import us.kbase.util.WSUtil;
import us.kbase.util.IdService;
import us.kbase.workspaceservice.GetObjectOutput;
import us.kbase.workspaceservice.GetObjectParams;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;

public class KbaseMemeServerImplTest {
	
	private SequenceSet testSequenceSet = new SequenceSet();
	private String fakeJobId = "12345.fasta";
	private String inputSequenceSet = new String();
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
    	for(Sequence sequence:testSequenceSet.getSequences()){
    		inputSequenceSet += ">"+sequence.getSequenceId()+"\n";
    		inputSequenceSet += KbasememeServerImpl.formatSequence(sequence.getSequence())+"\n";
    	}
	}

	@After
	public void tearDown() throws Exception {
		testSequenceSet = null;
		motifCollection = null;
		Runtime.getRuntime().exec("rm " + fakeJobId);
	}

	@Test
	public void testGetKbaseId() {
		String input = "MotifCollectionMeme";
		String result = IdService.getKbaseId(input);
		assertEquals("KBase.MotifCollectionMeme.",result.replaceAll("\\d", ""));
		input = "MotifMeme";
		result = IdService.getKbaseId(input);
		assertEquals("KBase.MotifMeme.",result.replaceAll("\\d", ""));
		input = "SiteMeme";
		result = IdService.getKbaseId(input);
		assertEquals("KBase.SiteMeme.",result.replaceAll("\\d", ""));
	}
	
	@Test
	public void testGenerateMemeInput() throws IOException {
		KbasememeServerImpl.generateFastaFile(fakeJobId, testSequenceSet);
		String result = new String();
		try {
			BufferedReader textreader = new BufferedReader(new FileReader(fakeJobId));
			String line = new String();
			while ((line = textreader.readLine())!=null){
				result += line+"\n";
			}
			textreader.close();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		} finally {
			
		}
		assertEquals(inputSequenceSet,result);
	}

	@Test
	public void testGenerateMemeCommandLine() throws UnsupportedEncodingException {
//		String modMeme = new String("anr".getBytes("UTF-8"), "ISO-8859-1");
		String modMeme = "anr";
		String result = KbasememeServerImpl.generateMemeCommandLine(fakeJobId, modMeme, 5, 8, 22, 2, 2, 3, 0, 1);
		assertEquals("meme "+fakeJobId+" -mod anr -nmotifs 5 -minw 8 -maxw 22 -nsites 2 -minsites 2 -maxsites 3 -revcomp -dna -text -nostatus",result);
	}

	@Test
	public void testRunMeme() {
		String testFileName = "test/testoutput.txt";
		KbasememeServerImpl.executeCommand("meme", testFileName);
		String line = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(testFileName));
			line = br.readLine();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				Runtime.getRuntime().exec("rm " + testFileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		assertEquals("                    MEME — Multiple EM for Motif Elicitation", line);
	}

	@Test
	public void testProcessMotifDataLine() {
		MotifMeme result = new MotifMeme();
		KbasememeServerImpl.processMotifDataLine(result, "MOTIF  1	width =   24   sites =   6   llr = 90   E-value = 2.3e+003");
		assertEquals(Integer.valueOf("1"),result.getMotifName());
		assertEquals(Integer.valueOf("24"),result.getMotifWidthMeme());
		assertEquals(Integer.valueOf("6"),result.getMotifSitesMeme());
		assertEquals(Double.valueOf("90"),result.getMotifLlrMeme());
		assertEquals(Double.valueOf("2300"),result.getMotifEvalueMeme());
	}

	@Test
	public void testProcessSampleLine() {
		MotifCollectionMeme result = new MotifCollectionMeme();
		KbasememeServerImpl.processSampleLine(result, "sample: seed=            0    seqfrac=         1");
		assertEquals(Integer.valueOf("0"),result.getSeedMeme());
		assertEquals(Integer.valueOf("1"),result.getSeqfracMeme());
	}

	@Test
	public void testProcessStrandsLine() {
		MotifCollectionMeme result = new MotifCollectionMeme();
		KbasememeServerImpl.processStrandsLine(result, "strands: +");
		assertEquals("+",result.getStrandsMeme());
	}

	@Test
	public void testProcessDataNLine() {
		MotifCollectionMeme result = new MotifCollectionMeme();
		KbasememeServerImpl.processDataNLine(result, "data:   n=            1500    N=               6");
		assertEquals(Integer.valueOf("1500"),result.getNMeme());
		assertEquals(Integer.valueOf("6"),result.getNCapMeme());
	}

	@Test
	public void testProcessDistanceLine() {
		MotifCollectionMeme result = new MotifCollectionMeme();
		KbasememeServerImpl.processDistanceLine(result, "        distance=    1e-05");
		assertEquals(Double.valueOf("0.00001"),result.getDistanceMeme());
	}

	@Test
	public void testProcessEmLine() {
		MotifCollectionMeme result = new MotifCollectionMeme();
		KbasememeServerImpl.processEmLine(result, "em:     prior=   dirichlet    b=            0.01    maxiter=        50");
		assertEquals("dirichlet",result.getPriorMeme());
		assertEquals(Double.valueOf("0.01"),result.getBMeme());
		assertEquals(Integer.valueOf("50"),result.getMaxiterMeme());
	}

	@Test
	public void testProcessGlobalsLine() {
		MotifCollectionMeme result = new MotifCollectionMeme();
		KbasememeServerImpl.processGlobalsLine(result, "global: substring=     yes    branching=      no    wbranch=        no");
		assertEquals("yes",result.getSubstringMeme());
		assertEquals("no",result.getBranchingMeme());
		assertEquals("no",result.getWbranchMeme());
	}

	@Test
	public void testProcessThetaLine() {
		MotifCollectionMeme result = new MotifCollectionMeme();
		KbasememeServerImpl.processThetaLine(result, "theta:  prob=            1    spmap=         uni    spfuzz=        0.5");
		assertEquals(Integer.valueOf("1"),result.getProbMeme());
		assertEquals("uni",result.getSpmapMeme());
		assertEquals("0.5",result.getSpfuzzMeme());
	}

	@Test
	public void testProcessNsitesLine() {
		MotifCollectionMeme result = new MotifCollectionMeme();
		KbasememeServerImpl.processNsitesLine(result, "nsites: minsites=        6    maxsites=        6    wnsites=       0.8");
		assertEquals(Integer.valueOf("6"),result.getMinsitesMeme());
		assertEquals(Integer.valueOf("6"),result.getMaxsitesMeme());
		assertEquals(Double.valueOf("0.8"),result.getWnsitesMeme());
	}

	@Test
	public void testProcessWgLine() {
		MotifCollectionMeme result = new MotifCollectionMeme();
		KbasememeServerImpl.processWgLine(result, "width:  wg=             11    ws=              1    endgaps=       yes");
		assertEquals(Integer.valueOf("11"),result.getWgMeme());
		assertEquals(Integer.valueOf("1"),result.getWsMeme());
		assertEquals("yes",result.getEndgapsMeme());
	}

	@Test
	public void testProcessMinwLine() {
		MotifCollectionMeme result = new MotifCollectionMeme();
		KbasememeServerImpl.processMinwLine(result, "width:  minw=           14    maxw=           24    minic=        0.00");
		assertEquals(Integer.valueOf("14"),result.getMinwMeme());
		assertEquals(Integer.valueOf("24"),result.getMaxwMeme());
		assertEquals(Double.valueOf("0.00"),result.getMinicMeme());
	}

	@Test
	public void testProcessOFLine() {
		MotifCollectionMeme result = new MotifCollectionMeme();
		KbasememeServerImpl.processOFLine(result, "object function=  E-value of product of p-values");
		assertEquals("E-value of product of p-values",result.getObjectFunctionMeme());
	}

	@Test
	public void testProcessModLine() {
		MotifCollectionMeme result = new MotifCollectionMeme();
		KbasememeServerImpl.processModLine(result, "model:  mod=          oops    nmotifs=         2    evt=           inf");
		assertEquals("oops",result.getModMeme());
		assertEquals(Integer.valueOf("2"),result.getNmotifsMeme());
		assertEquals("inf",result.getEvtMeme());
	}

	@Test
	public void testGenerateSiteMeme() {
		SiteMeme result = new SiteMeme();
		result = KbasememeServerImpl.generateSiteMeme("393587                      134  1.52e-10 ACTGGTTTTG TCACGATTTTCAGGACATTCGTGA CCGCGTTGGC");
		assertEquals("393587",result.getSiteSequenceName());
		assertEquals(Integer.valueOf("134"),result.getSiteStart());
		assertEquals(Double.valueOf("0.000000000152"),result.getSitePvalue());
		assertEquals("ACTGGTTTTG",result.getSiteLeftFlank());
		assertEquals("TCACGATTTTCAGGACATTCGTGA",result.getSiteSequence());
		assertEquals("CCGCGTTGGC",result.getSiteRightFlank());
	}

	@Test
	public void testGenerateMotifMeme() {
		List<MotifMeme> motifs = new ArrayList<MotifMeme>();
		List<String> sites = new ArrayList<String>();
		sites.add("393587                      134  1.52e-10 ACTGGTTTTG TCACGATTTTCAGGACATTCGTGA CCGCGTTGGC");
		sites.add("209110                      122  6.21e-10 GTGAATGCGG TCACGTTCGTCACCATGAGGGTGA CCGGGTTGCC");
		sites.add("209112                      152  9.16e-10 GGCAACCCGG TCACCCTCATGGTGACGAACGTGA CCGCATTCAC");
		sites.add("7532041                      75  4.75e-09 CGGCTACCGG TCACGCTTTTCCGCGCTACCGTGA CCGGCTTGAG");
		sites.add("8501762                     181  8.64e-07 CTGCCCCGTG CCACGGTCACCAAGACGAAAGTTT TCGTGCCTCT");
		sites.add("209114                      215  2.84e-05 GGGGCGTACG CCACGCATCCACATGACACCATAA CCGGGAAGAC");
		KbasememeServerImpl.generateMotifMeme("MOTIF  1	width =   24   sites =   6   llr = 90   E-value = 2.3e+003", "cumulativeOutput", sites, motifs);
		MotifMeme result = motifs.get(0);
		assertEquals(Integer.valueOf("1"),result.getMotifName());
		assertEquals(Integer.valueOf("24"),result.getMotifWidthMeme());
		assertEquals(Integer.valueOf("6"),result.getMotifSitesMeme());
		assertEquals(Double.valueOf("90"),result.getMotifLlrMeme());
		assertEquals(Double.valueOf("2300"),result.getMotifEvalueMeme());
		assertEquals("393587",result.getSites().get(0).getSiteSequenceName());
		assertEquals(Integer.valueOf("134"),result.getSites().get(0).getSiteStart());
		assertEquals(Double.valueOf("0.000000000152"),result.getSites().get(0).getSitePvalue());
		assertEquals("ACTGGTTTTG",result.getSites().get(0).getSiteLeftFlank());
		assertEquals("TCACGATTTTCAGGACATTCGTGA",result.getSites().get(0).getSiteSequence());
		assertEquals("CCGCGTTGGC",result.getSites().get(0).getSiteRightFlank());
	}

	@Test
	public void testParseMemeOutput() {
		String testOutputFile = "test/mem_12345.txt";

		MotifCollectionMeme result = KbasememeServerImpl.parseMemeOutput(testOutputFile);

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
	public void testSearchMotifsFromSequencesWithMeme() throws Exception {
//		String modMeme = new String("oops".getBytes("UTF-8"), "ISO-8859-1");
		String modMeme = "oops";
		MotifCollectionMeme result = KbasememeServerImpl.searchMotifsFromSequencesWithMeme(testSequenceSet, modMeme, 2, 14, 24, 0, 0, 0, 1, 0);
		displayCollection(result);

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
	public void testGenerateTomtomCommandLine() {
		String result = KbasememeServerImpl.generateTomtomCommandLine("1.meme", "2.meme", 0.5, 1, "allr", 1, 15);
		assertEquals("tomtom -thresh 0.5 -evalue -dist allr -internal -min-overlap 15 -text 1.meme 2.meme",result);
		
	}

	@Test
	public void testRunTomtom() {
		String testFileName = "test/tomtomoutput.txt"; 
		KbasememeServerImpl.executeCommand("tomtom -dist pearson -text test/mem_12345.txt test/mem_12345.txt", testFileName);
		String line = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(testFileName));
			line = br.readLine();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				Runtime.getRuntime().exec("rm " + testFileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		assertEquals("#Query ID	Target ID	Optimal offset	p-value	E-value	q-value	Overlap	Query consensus	Target consensus	Orientation",line);
	}
	
	@Test
	public void testGenerateHitTomtom() {
		String hit = "1	1	0	3.89353e-37	7.78706e-37	7.78706e-37	24	TCACGCTCGTCATGACGAGCGTGA	TCACGCTCGTCATGACGAGCGTGA	+";
		HitTomtom result = KbasememeServerImpl.generateHitTomtom(hit);
		assertEquals("1",result.getQueryMotifName());
		assertEquals("1",result.getTargetMotifName());
		assertEquals(Integer.valueOf("0"),result.getOptimalOffset());
		assertEquals(Double.valueOf("3.89353E-37"),result.getPValue());
		assertEquals(Double.valueOf("7.78706e-37"),result.getEValue());
		assertEquals(Double.valueOf("7.78706e-37"),result.getQValue());
		assertEquals(Integer.valueOf("24"),result.getOverlap());
		assertEquals("TCACGCTCGTCATGACGAGCGTGA",result.getQueryConsensus());
		assertEquals("TCACGCTCGTCATGACGAGCGTGA",result.getTargetConsensus());
		assertEquals("+",result.getStrand());
	}

	@Test
	public void testCompareMotifsWithTomtom() throws Exception {
//		String distTomtom = new String("pearson".getBytes("UTF-8"), "ISO-8859-1");
//		String modMeme = new String("oops".getBytes("UTF-8"), "ISO-8859-1");
		String distTomtom = "pearson";
		String modMeme = "oops";
		motifCollection = KbasememeServerImpl.searchMotifsFromSequencesWithMeme(testSequenceSet, modMeme, 2, 14, 24, 0, 0, 0, 1, 0);
		List<HitTomtom> result = KbasememeServerImpl.compareMotifsWithTomtom(motifCollection, motifCollection, 0.00, 0, distTomtom, 0, 0);
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
	public void testRunMast() throws IOException {
		String testFileName = "test/testoutput.txt";
		KbasememeServerImpl.executeCommand("mast test/mem_12345.txt test/seqs.fasta -hit_list -nostatus", testFileName);
		String line = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(testFileName));
			line = br.readLine();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				Runtime.getRuntime().exec("rm " + testFileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		assertEquals("# All non-overlapping hits in all sequences from \"test/seqs.fasta\".",line);
	}

	
	@Test
	public void testGenerateMastCommandLine() {
		String result = KbasememeServerImpl.generateMastCommandLine("1.meme", "2.fasta", 0.0005);
		assertEquals("mast 1.meme 2.fasta -mt 0.0005 -hit_list -nostatus",result);
	}
	
	@Test
	public void testGenerateHitMast() {
		String hit = "209110 +2 65 78  1527.00 2.53e-05";
		HitMast result = KbasememeServerImpl.generateHitMast(hit);
		assertEquals("209110", result.getSequenceName());
		assertEquals("+", result.getStrand());
		assertEquals("2", result.getMotifName());
		assertEquals(Integer.valueOf("65"), result.getHitStart());
		assertEquals(Integer.valueOf("78"), result.getHitEnd());
		assertEquals(Double.valueOf("1527.00"), result.getScore());
		assertEquals(Double.valueOf("0.0000253"), result.getHitPvalue());
	}

	@Test
	public void testParseMastOutput() {
		String testOutputFile = "test/outmast.txt";
		List<HitMast> result = KbasememeServerImpl.parseMastOutput(testOutputFile);
		assertEquals("209110", result.get(0).getSequenceName());
		assertEquals("+", result.get(0).getStrand());
		assertEquals("2", result.get(0).getMotifName());
		assertEquals(Integer.valueOf("65"), result.get(0).getHitStart());
		assertEquals(Integer.valueOf("78"), result.get(0).getHitEnd());
		assertEquals(Double.valueOf("1527.00"), result.get(0).getScore());
		assertEquals(Double.valueOf("0.0000253"), result.get(0).getHitPvalue());
	}


	@Test
	public void testFindSitesByMotifCollectionWithMast() throws Exception {
		String modMeme = "oops";
		motifCollection = KbasememeServerImpl.searchMotifsFromSequencesWithMeme(testSequenceSet, modMeme, 2, 14, 24, 0, 0, 0, 1, 0);
		List<HitMast> result = KbasememeServerImpl.findSitesByMotifCollectionWithMast(motifCollection, testSequenceSet, 0.0005);
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
	
/*	@Test
	public void testWsWrite() throws Exception {
		WSUtil.saveObject(testSequenceSet.getSequenceSetId(), testSequenceSet, false);
		fail("Not yet implemented"); // TODO
		
	}
*/
	@Test
	public void testWsRead() throws Exception {
		String id = "testSequenceSet1";
//		WSUtil.saveObject(id, testSequenceSet, false);
		GetObjectParams params = new GetObjectParams().withType("SequenceSet").withId(id).withWorkspace(WSUtil.workspaceName).withAuth(WSUtil.authToken().toString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(params);
		SequenceSet result = UObject.transform(output.getData(), SequenceSet.class);
		assertEquals(testSequenceSet.getSequenceSetId(),result.getSequenceSetId());
	}

	@Test
	public void testFormatSequence() {
		String sequence = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaattt";
//		WSUtil.saveObject(id, testSequenceSet, false);
		String result = KbasememeServerImpl.formatSequence(sequence);
		assertEquals("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\nttt",result);
	}
	
	public static void displayCollection(MotifCollectionMeme collection) {
		System.out.println("KBase collection ID = "+collection.getKbaseMotifCollectionMemeId());
		System.out.println("timestamp = "+collection.getTimestamp());
		System.out.println("versionMeme = "+collection.getVersionMeme());
		System.out.println("inputDatafile = "+collection.getInputDatafile());
		System.out.println("alphabetMeme = "+collection.getAlphabetMeme());
		System.out.println("trainingSetMeme = "+collection.getTrainingSetMeme().toString());
		System.out.println("commandLineMeme = "+collection.getCommandLineMeme());
		System.out.println("modMeme = "+collection.getModMeme());
		System.out.println("nmotifsMeme = "+collection.getNmotifsMeme());
		System.out.println("evtMeme = "+collection.getEvtMeme());
		System.out.println("objectFunctionMeme = "+collection.getObjectFunctionMeme());
		System.out.println("minwMeme = "+collection.getMinwMeme());
		System.out.println("maxwMeme = "+collection.getMaxwMeme());
		System.out.println("minicMeme = "+collection.getMinicMeme());
		System.out.println("wgMeme = "+collection.getWgMeme());
		System.out.println("wsMeme = "+collection.getWsMeme());
		System.out.println("endgapsMeme = "+collection.getEndgapsMeme());
		System.out.println("minsitesMeme = "+collection.getMinsitesMeme());
		System.out.println("maxsitesMeme = "+collection.getMaxsitesMeme());
		System.out.println("wnsitesMeme = "+collection.getWnsitesMeme());
		System.out.println("probMeme = "+collection.getProbMeme());
		System.out.println("spmapMeme = "+collection.getSpmapMeme());
		System.out.println("spfuzzMeme = "+collection.getSpfuzzMeme());
		System.out.println("substringMeme = "+collection.getSubstringMeme());
		System.out.println("branchingMeme = "+collection.getBranchingMeme());
		System.out.println("wbranchMeme = "+collection.getWbranchMeme());
		System.out.println("priorMeme = "+collection.getPriorMeme());
		System.out.println("bMeme = "+collection.getBMeme());
		System.out.println("maxiterMeme = "+collection.getMaxiterMeme());
		System.out.println("distanceMeme = "+collection.getDistanceMeme());
		System.out.println("nMeme = "+collection.getNMeme());
		System.out.println("nCapMeme = "+collection.getNCapMeme());
		System.out.println("strandsMeme = "+collection.getStrandsMeme());
		System.out.println("seedMeme = "+collection.getSeedMeme());
		System.out.println("seqfracMeme = "+collection.getSeqfracMeme());
		System.out.println("letterFreqMeme = "+collection.getLetterFreqMeme());
		System.out.println("bgFreqMeme = "+collection.getBgFreqMeme());
		System.out.println("\n"+collection.getCollectionMemeOutput()+"\n");
		for (MotifMeme motif : collection.getMotifs()){
			System.out.println("\n"+motif.getMotifMemeOutput()+"\n");
			System.out.println("\tkbaseMotifMemeId = "+motif.getKbaseMotifMemeId());
			System.out.println("\tmotifName = "+motif.getMotifName());
			System.out.println("\tmotifWidthMeme = "+motif.getMotifWidthMeme());
			System.out.println("\tmotifSitesMeme = "+motif.getMotifSitesMeme());
			System.out.println("\tmotifLlrMeme = "+motif.getMotifLlrMeme());
			System.out.println("\tmotifEvalueMeme = "+motif.getMotifEvalueMeme());
			for (SiteMeme site : motif.getSites()){
				System.out.println("\t\tkbaseMotifMemeId = "+site.getKbaseMotifMemeId());
				System.out.println("\t\tsiteSequenceName = "+site.getSiteSequenceName());
				System.out.println("\t\tsiteStart = "+site.getSiteStart());
				System.out.println("\t\tsitePvalue = "+site.getSitePvalue());
				System.out.println("\t\tsiteLeftFlank = "+site.getSiteLeftFlank());
				System.out.println("\t\tsiteSequence = "+site.getSiteSequence());
				System.out.println("\t\tsiteRightFlank = "+site.getSiteRightFlank());
			}
		}
		
	}


}
