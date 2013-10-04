package us.kbase.meme;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import us.kbase.UObject;
import us.kbase.generaltypes.SequenceSet;
import us.kbase.generaltypes.Sequence;
import us.kbase.util.WSUtil;
import us.kbase.workspaceservice.GetObjectOutput;
import us.kbase.workspaceservice.GetObjectParams;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;

public class MemeServerImplTest {
	
	private SequenceSet testSequenceSet = new SequenceSet();
	private String fakeJobId = "12345.fasta";
	private String inputSequenceSet = new String();
	private MemeRunResult memeRunResult = new MemeRunResult();

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
    		inputSequenceSet += MemeServerImpl.formatSequence(sequence.getSequence())+"\n";
    	}
	}

	@After
	public void tearDown() throws Exception {
		testSequenceSet = null;
		memeRunResult = null;
		Runtime.getRuntime().exec("rm " + fakeJobId);
	}

	@Test
	public void testGetKbaseId() {
		String input = "MemeRunResult";
		String result = MemeServerImpl.getKbaseId(input);
		assertEquals("KBase.MemeRunResult.",result.replaceAll("\\d", ""));
		input = "MemeMotif";
		result = MemeServerImpl.getKbaseId(input);
		assertEquals("KBase.MemeMotif.",result.replaceAll("\\d", ""));
		input = "MemeSite";
		result = MemeServerImpl.getKbaseId(input);
		assertEquals("KBase.MemeSite.",result.replaceAll("\\d", ""));
	}
	
	@Test
	public void testGenerateMemeInput() throws IOException {
		MemeServerImpl.generateFastaFile(fakeJobId, testSequenceSet);
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
		String result = MemeServerImpl.generateMemeCommandLine(fakeJobId, modMeme, 5, 8, 22, 2, 2, 3, 0, 1);
		assertEquals("meme "+fakeJobId+" -mod anr -nmotifs 5 -minw 8 -maxw 22 -nsites 2 -minsites 2 -maxsites 3 -revcomp -dna -text -nostatus",result);
	}

	@Test
	public void testRunMeme() {
		String testFileName = "test/testoutput.txt";
		MemeServerImpl.executeCommand("meme", testFileName);
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
		MemeMotif result = new MemeMotif();
		MemeServerImpl.processMotifDataLine(result, "MOTIF  1	width =   24   sites =   6   llr = 90   E-value = 2.3e+003");
		assertEquals("MOTIF  1	width =   24   sites =   6   llr = 90   E-value = 2.3e+003",result.getDescription());
		assertEquals(Integer.valueOf("24"),result.getWidth());
		assertEquals(Double.valueOf("90"),result.getLlr());
		assertEquals(Double.valueOf("2300"),result.getEvalue());
	}

	@Test
	public void testProcessSampleLine() {
		MemeRunResult result = new MemeRunResult();
		MemeServerImpl.processSampleLine(result, "sample: seed=            0    seqfrac=         1");
		assertEquals(Integer.valueOf("0"),result.getSeed());
		assertEquals(Integer.valueOf("1"),result.getSeqfrac());
	}

	@Test
	public void testProcessStrandsLine() {
		MemeRunResult result = new MemeRunResult();
		MemeServerImpl.processStrandsLine(result, "strands: +");
		assertEquals("+",result.getStrands());
	}

	@Test
	public void testProcessDataNLine() {
		MemeRunResult result = new MemeRunResult();
		MemeServerImpl.processDataNLine(result, "data:   n=            1500    N=               6");
		assertEquals(Integer.valueOf("1500"),result.getN());
		assertEquals(Integer.valueOf("6"),result.getNCap());
	}

	@Test
	public void testProcessDistanceLine() {
		MemeRunResult result = new MemeRunResult();
		MemeServerImpl.processDistanceLine(result, "        distance=    1e-05");
		assertEquals(Double.valueOf("0.00001"),result.getDistance());
	}

	@Test
	public void testProcessEmLine() {
		MemeRunResult result = new MemeRunResult();
		MemeServerImpl.processEmLine(result, "em:     prior=   dirichlet    b=            0.01    maxiter=        50");
		assertEquals("dirichlet",result.getPrior());
		assertEquals(Double.valueOf("0.01"),result.getB());
		assertEquals(Integer.valueOf("50"),result.getMaxiter());
	}

	@Test
	public void testProcessGlobalsLine() {
		MemeRunResult result = new MemeRunResult();
		MemeServerImpl.processGlobalsLine(result, "global: substring=     yes    branching=      no    wbranch=        no");
		assertEquals("yes",result.getSubstring());
		assertEquals("no",result.getBranching());
		assertEquals("no",result.getWbranch());
	}

	@Test
	public void testProcessThetaLine() {
		MemeRunResult result = new MemeRunResult();
		MemeServerImpl.processThetaLine(result, "theta:  prob=            1    spmap=         uni    spfuzz=        0.5");
		assertEquals(Integer.valueOf("1"),result.getProb());
		assertEquals("uni",result.getSpmap());
		assertEquals("0.5",result.getSpfuzz());
	}

	@Test
	public void testProcessNsitesLine() {
		MemeRunResult result = new MemeRunResult();
		MemeServerImpl.processNsitesLine(result, "nsites: minsites=        6    maxsites=        6    wnsites=       0.8");
		assertEquals(Integer.valueOf("6"),result.getMinsites());
		assertEquals(Integer.valueOf("6"),result.getMaxsites());
		assertEquals(Double.valueOf("0.8"),result.getWnsites());
	}

	@Test
	public void testProcessWgLine() {
		MemeRunResult result = new MemeRunResult();
		MemeServerImpl.processWgLine(result, "width:  wg=             11    ws=              1    endgaps=       yes");
		assertEquals(Integer.valueOf("11"),result.getWg());
		assertEquals(Integer.valueOf("1"),result.getWs());
		assertEquals("yes",result.getEndgaps());
	}

	@Test
	public void testProcessMinwLine() {
		MemeRunResult result = new MemeRunResult();
		MemeServerImpl.processMinwLine(result, "width:  minw=           14    maxw=           24    minic=        0.00");
		assertEquals(Integer.valueOf("14"),result.getMinw());
		assertEquals(Integer.valueOf("24"),result.getMaxw());
		assertEquals(Double.valueOf("0.00"),result.getMinic());
	}

	@Test
	public void testProcessOFLine() {
		MemeRunResult result = new MemeRunResult();
		MemeServerImpl.processOFLine(result, "object function=  E-value of product of p-values");
		assertEquals("E-value of product of p-values",result.getObjectFunction());
	}

	@Test
	public void testProcessModLine() {
		MemeRunResult result = new MemeRunResult();
		MemeServerImpl.processModLine(result, "model:  mod=          oops    nmotifs=         2    evt=           inf");
		assertEquals("oops",result.getMod());
		assertEquals(Integer.valueOf("2"),result.getNmotifs());
		assertEquals("inf",result.getEvt());
	}

	@Test
	public void testGenerateSiteMeme() {
		MemeSite result = new MemeSite();
		result = MemeServerImpl.generateMemeSite("393587                      134  1.52e-10 ACTGGTTTTG TCACGATTTTCAGGACATTCGTGA CCGCGTTGGC");
		assertEquals("393587",result.getSourceSequenceId());
		assertEquals(Integer.valueOf("134"),result.getStart());
		assertEquals(Double.valueOf("0.000000000152"),result.getPvalue());
		assertEquals("ACTGGTTTTG",result.getLeftFlank());
		assertEquals("TCACGATTTTCAGGACATTCGTGA",result.getSequence());
		assertEquals("CCGCGTTGGC",result.getRightFlank());
	}

	@Test
	public void testGenerateMotifMeme() {
		List<MemeMotif> motifs = new ArrayList<MemeMotif>();
		List<String> sites = new ArrayList<String>();
		sites.add("393587                      134  1.52e-10 ACTGGTTTTG TCACGATTTTCAGGACATTCGTGA CCGCGTTGGC");
		sites.add("209110                      122  6.21e-10 GTGAATGCGG TCACGTTCGTCACCATGAGGGTGA CCGGGTTGCC");
		sites.add("209112                      152  9.16e-10 GGCAACCCGG TCACCCTCATGGTGACGAACGTGA CCGCATTCAC");
		sites.add("7532041                      75  4.75e-09 CGGCTACCGG TCACGCTTTTCCGCGCTACCGTGA CCGGCTTGAG");
		sites.add("8501762                     181  8.64e-07 CTGCCCCGTG CCACGGTCACCAAGACGAAAGTTT TCGTGCCTCT");
		sites.add("209114                      215  2.84e-05 GGGGCGTACG CCACGCATCCACATGACACCATAA CCGGGAAGAC");
		MemeServerImpl.generateMemeMotif("MOTIF  1	width =   24   sites =   6   llr = 90   E-value = 2.3e+003", "cumulativeOutput", sites, motifs);
		MemeMotif result = motifs.get(0);
		assertEquals("MOTIF  1	width =   24   sites =   6   llr = 90   E-value = 2.3e+003",result.getDescription());
		assertEquals(Integer.valueOf("24"),result.getWidth());
		assertEquals(6,result.getSites().size());
		assertEquals(Double.valueOf("90"),result.getLlr());
		assertEquals(Double.valueOf("2300"),result.getEvalue());
		assertEquals("393587",result.getSites().get(0).getSourceSequenceId());
		assertEquals(Integer.valueOf("134"),result.getSites().get(0).getStart());
		assertEquals(Double.valueOf("0.000000000152"),result.getSites().get(0).getPvalue());
		assertEquals("ACTGGTTTTG",result.getSites().get(0).getLeftFlank());
		assertEquals("TCACGATTTTCAGGACATTCGTGA",result.getSites().get(0).getSequence());
		assertEquals("CCGCGTTGGC",result.getSites().get(0).getRightFlank());
	}

	@Test
	public void testParseMemeOutput() {
		String testOutputFile = "test/mem_12345.txt";

		MemeRunResult result = MemeServerImpl.parseMemeOutput(testOutputFile);

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
		assertEquals("MOTIF  1	width =   24   sites =   6   llr = 90   E-value = 2.3e+003",result.getMotifs().get(0).getDescription());
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
	public void testSearchMotifsFromSequencesWithMeme() throws Exception {
//		String modMeme = new String("oops".getBytes("UTF-8"), "ISO-8859-1");
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
		
		MemeRunResult result = MemeServerImpl.findMotifsWithMeme(testSequenceSet, params);

		
		displayCollection(result);

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
		assertEquals("MOTIF  1	width =   24   sites =   6   llr = 90   E-value = 2.3e+003",result.getMotifs().get(0).getDescription());
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
	public void testGenerateTomtomCommandLine() {
		String result = MemeServerImpl.generateTomtomCommandLine("1.meme", "2.meme", 0.5, 1, "allr", 1, 15, "");
		assertEquals("tomtom -thresh 0.5 -evalue -dist allr -internal -min-overlap 15 -text 1.meme 2.meme",result);
		
	}

	@Test
	public void testRunTomtom() {
		String testFileName = "test/tomtomoutput.txt"; 
		MemeServerImpl.executeCommand("tomtom -dist pearson -text test/mem_12345.txt test/mem_12345.txt", testFileName);
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
		TomtomHit result = MemeServerImpl.generateHitTomtom(hit);
		assertEquals("1",result.getQueryPspmId());
		assertEquals("1",result.getTargetPspmId());
		assertEquals(Integer.valueOf("0"),result.getOptimalOffset());
		assertEquals(Double.valueOf("3.89353E-37"),result.getPvalue());
		assertEquals(Double.valueOf("7.78706e-37"),result.getEvalue());
		assertEquals(Double.valueOf("7.78706e-37"),result.getQvalue());
		assertEquals(Integer.valueOf("24"),result.getOverlap());
		assertEquals("TCACGCTCGTCATGACGAGCGTGA",result.getQueryConsensus());
		assertEquals("TCACGCTCGTCATGACGAGCGTGA",result.getTargetConsensus());
		assertEquals("+",result.getStrand());
	}

	@Test
	public void testCompareMotifsWithTomtom() throws Exception {
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
		
		memeRunResult = MemeServerImpl.findMotifsWithMeme(testSequenceSet, params);
		MemePSPMCollection memePspmCollection = MemeServerImpl.getPspmCollectionFromMemeResult(memeRunResult);

		TomtomRunParameters paramsTomtom = new TomtomRunParameters();
		paramsTomtom.setDist("pearson");
		paramsTomtom.setThresh(0.00);
		paramsTomtom.setEvalue(0);
		paramsTomtom.setInternal(0);
		paramsTomtom.setMinOverlap(0);

		TomtomRunResult result = MemeServerImpl.compareMotifsWithTomtomByCollection(memePspmCollection, memePspmCollection, "", paramsTomtom);
		
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
	public void testRunMast() throws IOException {
		String testFileName = "test/testoutput.txt";
		MemeServerImpl.executeCommand("mast test/mem_12345.txt test/seqs.fasta -hit_list -nostatus", testFileName);
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
		String result = MemeServerImpl.generateMastCommandLine("1.meme", "2.fasta", 0.0005, -1);
		assertEquals("mast 1.meme 2.fasta -mt 0.0005 -hit_list -nostatus",result);
	}
	
	@Test
	public void testGenerateHitMast() {
		String hit = "209110 +2 65 78  1527.00 2.53e-05";
		MastHit result = MemeServerImpl.generateHitMast(hit);
		assertEquals("209110", result.getSequenceId());
		assertEquals("+", result.getStrand());
		assertEquals("2", result.getPspmId());
		assertEquals(Integer.valueOf("65"), result.getHitStart());
		assertEquals(Integer.valueOf("78"), result.getHitEnd());
		assertEquals(Double.valueOf("1527.00"), result.getScore());
		assertEquals(Double.valueOf("0.0000253"), result.getHitPvalue());
	}

	@Test
	public void testParseMastOutput() {
		String testOutputFile = "test/outmast.txt";
		List<MastHit> result = MemeServerImpl.parseMastOutput(testOutputFile);
		assertEquals("209110", result.get(0).getSequenceId());
		assertEquals("+", result.get(0).getStrand());
		assertEquals("2", result.get(0).getPspmId());
		assertEquals(Integer.valueOf("65"), result.get(0).getHitStart());
		assertEquals(Integer.valueOf("78"), result.get(0).getHitEnd());
		assertEquals(Double.valueOf("1527.00"), result.get(0).getScore());
		assertEquals(Double.valueOf("0.0000253"), result.get(0).getHitPvalue());
	}


	@Test
	public void testfindSitesWithMastByCollection() throws Exception {
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
		
		memeRunResult = MemeServerImpl.findMotifsWithMeme(testSequenceSet, params);
		MemePSPMCollection memePspmCollection = MemeServerImpl.getPspmCollectionFromMemeResult(memeRunResult);

		MastRunResult result = MemeServerImpl.findSitesWithMastByCollection(memePspmCollection, testSequenceSet, "", 0.0005);

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
	
/*	@Test
	public void testWsWrite() throws Exception {
		WSUtil.saveObject(testSequenceSet.getSequenceSetId(), testSequenceSet, false);
		fail("Not yet implemented"); // TODO
		
	}
*/
	@Test
	public void testWsRead() throws Exception {
		GetObjectParams params = new GetObjectParams().withType("SequenceSet").withId("KBase.SequenceSet.12345").withWorkspace("AKtest").withAuth(WSUtil.authToken().toString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(params);
		SequenceSet result = UObject.transform(output.getData(), SequenceSet.class);
		assertEquals(testSequenceSet.getSequenceSetId(),result.getSequenceSetId());
	}

	@Test
	public void testFormatSequence() {
		String sequence = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaattt";
//		WSUtil.saveObject(id, testSequenceSet, false);
		String result = MemeServerImpl.formatSequence(sequence);
		assertEquals("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\nttt",result);
	}
	
	public static void displayCollection(MemeRunResult collection) {
		System.out.println("KBase collection ID = "+collection.getId());
		System.out.println("timestamp = "+collection.getTimestamp());
		System.out.println("versionMeme = "+collection.getVersion());
		System.out.println("inputDatafile = "+collection.getInputFileName());
		System.out.println("alphabetMeme = "+collection.getAlphabet());
		System.out.println("trainingSetMeme = "+collection.getTrainingSet().toString());
		System.out.println("commandLineMeme = "+collection.getCommandLine());
		System.out.println("modMeme = "+collection.getMod());
		System.out.println("nmotifsMeme = "+collection.getNmotifs());
		System.out.println("evtMeme = "+collection.getEvt());
		System.out.println("objectFunctionMeme = "+collection.getObjectFunction());
		System.out.println("minwMeme = "+collection.getMinw());
		System.out.println("maxwMeme = "+collection.getMaxw());
		System.out.println("minicMeme = "+collection.getMinic());
		System.out.println("wgMeme = "+collection.getWg());
		System.out.println("wsMeme = "+collection.getWs());
		System.out.println("endgapsMeme = "+collection.getEndgaps());
		System.out.println("minsitesMeme = "+collection.getMinsites());
		System.out.println("maxsitesMeme = "+collection.getMaxsites());
		System.out.println("wnsitesMeme = "+collection.getWnsites());
		System.out.println("probMeme = "+collection.getProb());
		System.out.println("spmapMeme = "+collection.getSpmap());
		System.out.println("spfuzzMeme = "+collection.getSpfuzz());
		System.out.println("substringMeme = "+collection.getSubstring());
		System.out.println("branchingMeme = "+collection.getBranching());
		System.out.println("wbranchMeme = "+collection.getWbranch());
		System.out.println("priorMeme = "+collection.getPrior());
		System.out.println("bMeme = "+collection.getB());
		System.out.println("maxiterMeme = "+collection.getMaxiter());
		System.out.println("distanceMeme = "+collection.getDistance());
		System.out.println("nMeme = "+collection.getN());
		System.out.println("nCapMeme = "+collection.getNCap());
		System.out.println("strandsMeme = "+collection.getStrands());
		System.out.println("seedMeme = "+collection.getSeed());
		System.out.println("seqfracMeme = "+collection.getSeqfrac());
		System.out.println("letterFreqMeme = "+collection.getLetterFreq());
		System.out.println("bgFreqMeme = "+collection.getBgFreq());
		System.out.println("\n"+collection.getRawOutput()+"\n");
		for (MemeMotif motif : collection.getMotifs()){
			System.out.println("\n"+motif.getRawOutput()+"\n");
			System.out.println("\tkbaseMotifMemeId = "+motif.getId());
			System.out.println("\tmotifName = "+motif.getDescription());
			System.out.println("\tmotifWidthMeme = "+motif.getWidth());
			System.out.println("\tmotifSitesMeme = "+motif.getSites());
			System.out.println("\tmotifLlrMeme = "+motif.getLlr());
			System.out.println("\tmotifEvalueMeme = "+motif.getEvalue());
			for (MemeSite site : motif.getSites()){
				System.out.println("\t\tsiteSequenceName = "+site.getSourceSequenceId());
				System.out.println("\t\tsiteStart = "+site.getStart());
				System.out.println("\t\tsitePvalue = "+site.getPvalue());
				System.out.println("\t\tsiteLeftFlank = "+site.getLeftFlank());
				System.out.println("\t\tsiteSequence = "+site.getSequence());
				System.out.println("\t\tsiteRightFlank = "+site.getRightFlank());
			}
		}
		
	}


}
