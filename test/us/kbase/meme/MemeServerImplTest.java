package us.kbase.meme;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import us.kbase.auth.AuthException;
import us.kbase.auth.AuthService;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonClientCaller;
import us.kbase.common.service.JsonClientException;
import us.kbase.common.service.Tuple14;
import us.kbase.common.service.Tuple5;
import us.kbase.common.service.Tuple7;
//import us.kbase.common.service.Tuple9;
import us.kbase.common.service.UObject;
import us.kbase.common.service.UnauthorizedException;
import us.kbase.generaltypes.SequenceSet;
import us.kbase.generaltypes.Sequence;
import us.kbase.userandjobstate.InitProgress;
import us.kbase.userandjobstate.Results;
import us.kbase.userandjobstate.UserAndJobStateClient;
import us.kbase.util.WSUtil;

//import us.kbase.workspace.CompileTypespecParams;
//import us.kbase.workspace.ListModulesParams;
//import us.kbase.workspace.ObjectData;
//import us.kbase.workspace.ObjectIdentity;
//import us.kbase.workspace.ObjectSaveData;
//import us.kbase.workspace.SaveObjectsParams;

//import us.kbase.util.WSUtil;
import us.kbase.workspaceservice.GetObjectOutput;
import us.kbase.workspaceservice.GetObjectParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.Map;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class MemeServerImplTest {
	
	private SequenceSet testSequenceSet = new SequenceSet();
	private String fakeJobId = "12345.fasta";
	private String inputSequenceSet = new String();
	private MemeRunResult memeRunResult = new MemeRunResult();
	private final String USER_NAME = "aktest";
	private final String PASSWORD = "1475rokegi";
	private final String JOB_SERVICE = "http://140.221.84.180:7083";
	//private final String JOB_ACCOUNT = "memejobs";
	//private final String JOB_PASSWORD = "1475_rokegi";
	private String jobId = "528ec2eae4b0702f7611d8f7";
	
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
		String result = MemeServerImpl.generateMemeCommandLine(fakeJobId, modMeme, 5L, 8L, 22L, 2L, 2L, 3L, 0L, 1L);
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
		assertEquals(Long.valueOf("24"),result.getWidth());
		assertEquals(Double.valueOf("90"),result.getLlr());
		assertEquals(Double.valueOf("2300"),result.getEvalue());
	}

	@Test
	public void testProcessSampleLine() {
		MemeRunResult result = new MemeRunResult();
		MemeServerImpl.processSampleLine(result, "sample: seed=            0    seqfrac=         1");
		assertEquals(Long.valueOf("0"),result.getSeed());
		assertEquals(Long.valueOf("1"),result.getSeqfrac());
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
		assertEquals(Long.valueOf("1500"),result.getN());
		assertEquals(Long.valueOf("6"),result.getNCap());
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
		assertEquals(Long.valueOf("50"),result.getMaxiter());
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
		assertEquals(Long.valueOf("1"),result.getProb());
		assertEquals("uni",result.getSpmap());
		assertEquals("0.5",result.getSpfuzz());
	}

	@Test
	public void testProcessNsitesLine() {
		MemeRunResult result = new MemeRunResult();
		MemeServerImpl.processNsitesLine(result, "nsites: minsites=        6    maxsites=        6    wnsites=       0.8");
		assertEquals(Long.valueOf("6"),result.getMinsites());
		assertEquals(Long.valueOf("6"),result.getMaxsites());
		assertEquals(Double.valueOf("0.8"),result.getWnsites());
	}

	@Test
	public void testProcessWgLine() {
		MemeRunResult result = new MemeRunResult();
		MemeServerImpl.processWgLine(result, "width:  wg=             11    ws=              1    endgaps=       yes");
		assertEquals(Long.valueOf("11"),result.getWg());
		assertEquals(Long.valueOf("1"),result.getWs());
		assertEquals("yes",result.getEndgaps());
	}

	@Test
	public void testProcessMinwLine() {
		MemeRunResult result = new MemeRunResult();
		MemeServerImpl.processMinwLine(result, "width:  minw=           14    maxw=           24    minic=        0.00");
		assertEquals(Long.valueOf("14"),result.getMinw());
		assertEquals(Long.valueOf("24"),result.getMaxw());
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
		assertEquals(Long.valueOf("2"),result.getNmotifs());
		assertEquals("inf",result.getEvt());
	}

	@Test
	public void testGenerateSiteMeme() {
		MemeSite result = new MemeSite();
		result = MemeServerImpl.generateMemeSite("393587                      134  1.52e-10 ACTGGTTTTG TCACGATTTTCAGGACATTCGTGA CCGCGTTGGC");
		assertEquals("393587",result.getSourceSequenceId());
		assertEquals(Long.valueOf("134"),result.getStart());
		assertEquals(Double.valueOf("0.000000000152"),result.getPvalue());
		assertEquals("ACTGGTTTTG",result.getLeftFlank());
		assertEquals("TCACGATTTTCAGGACATTCGTGA",result.getSequence());
		assertEquals("CCGCGTTGGC",result.getRightFlank());
	}

	@Test
	public void testGenerateMotifMeme() throws Exception {
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
		assertEquals(Long.valueOf("24"),result.getWidth());
		assertEquals(6,result.getSites().size());
		assertEquals(Double.valueOf("90"),result.getLlr());
		assertEquals(Double.valueOf("2300"),result.getEvalue());
		assertEquals("393587",result.getSites().get(0).getSourceSequenceId());
		assertEquals(Long.valueOf("134"),result.getSites().get(0).getStart());
		assertEquals(Double.valueOf("0.000000000152"),result.getSites().get(0).getPvalue());
		assertEquals("ACTGGTTTTG",result.getSites().get(0).getLeftFlank());
		assertEquals("TCACGATTTTCAGGACATTCGTGA",result.getSites().get(0).getSequence());
		assertEquals("CCGCGTTGGC",result.getSites().get(0).getRightFlank());
	}

	@Test
	public void testParseMemeOutput() throws Exception {
		String testOutputFile = "test/mem_12345.txt";

		MemeRunResult result = MemeServerImpl.parseMemeOutput(testOutputFile);

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
		assertEquals("MOTIF  1	width =   24   sites =   6   llr = 90   E-value = 2.3e+003",result.getMotifs().get(0).getDescription());
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
	public void testSearchMotifsFromSequencesWithMeme() throws Exception {
//		String modMeme = new String("oops".getBytes("UTF-8"), "ISO-8859-1");
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
		
		MemeRunResult result = MemeServerImpl.findMotifsWithMeme(testSequenceSet, params, null, null);

		
		//displayCollection(result);

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
		assertEquals("MOTIF  1	width =   24   sites =   6   llr = 90   E-value = 2.3e+003",result.getMotifs().get(0).getDescription());
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
	public void testSearchMotifsFromSequencesWithMemeJob() throws Exception {
		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		String jobId = MemeServerImpl.jobClient(token.toString()).createJob();
		System.out.println(jobId);
		assertNotNull(jobId);

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
		
		String wsId = "AKtest";
		
		String resultId = MemeServerImpl.findMotifsWithMemeJobFromWs (wsId, testSequenceSet.getSequenceSetId(), params, jobId, token.toString());
		
		GetObjectParams objectParams = new GetObjectParams().withType("SequenceSet").withId(resultId).withWorkspace(wsId).withAuth(token.toString());
		GetObjectOutput output = MemeServerImpl.wsClient(token.toString()).getObject(objectParams);
		MemeRunResult result = UObject.transformObjectToObject(output.getData(), MemeRunResult.class);


		
		//displayCollection(result);

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
		assertEquals("MOTIF  1	width =   24   sites =   6   llr = 90   E-value = 2.3e+003",result.getMotifs().get(0).getDescription());
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
	public void testGenerateTomtomCommandLine() {
		String result = MemeServerImpl.generateTomtomCommandLine("1.meme", "2.meme", 0.5, 1L, "allr", 1L, 15L, "");
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
		assertEquals(Long.valueOf("0"),result.getOptimalOffset());
		assertEquals(Double.valueOf("3.89353E-37"),result.getPvalue());
		assertEquals(Double.valueOf("7.78706e-37"),result.getEvalue());
		assertEquals(Double.valueOf("7.78706e-37"),result.getQvalue());
		assertEquals(Long.valueOf("24"),result.getOverlap());
		assertEquals("TCACGCTCGTCATGACGAGCGTGA",result.getQueryConsensus());
		assertEquals("TCACGCTCGTCATGACGAGCGTGA",result.getTargetConsensus());
		assertEquals("+",result.getStrand());
	}

	@Test
	public void testCompareMotifsWithTomtom() throws Exception {
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
		
		memeRunResult = MemeServerImpl.findMotifsWithMeme(testSequenceSet, params, null, null);
		MemePSPMCollection memePspmCollection = MemeServerImpl.getPspmCollectionFromMemeResult(memeRunResult);

		TomtomRunParameters paramsTomtom = new TomtomRunParameters();
		paramsTomtom.setDist("pearson");
		paramsTomtom.setThresh(0.00);
		paramsTomtom.setEvalue(0L);
		paramsTomtom.setInternal(0L);
		paramsTomtom.setMinOverlap(0L);

		TomtomRunResult result = MemeServerImpl.compareMotifsWithTomtomByCollection(memePspmCollection, memePspmCollection, "", paramsTomtom, null, null);
		
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
	public void testCompareMotifsWithTomtomJobByCollectionFromWs() throws Exception {
		
		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();

		TomtomRunParameters paramsTomtom = new TomtomRunParameters();
		paramsTomtom.setDist("pearson");
		paramsTomtom.setThresh(0.00);
		paramsTomtom.setEvalue(0L);
		paramsTomtom.setInternal(0L);
		paramsTomtom.setMinOverlap(0L);
		
		String jobId = MemeServerImpl.jobClient(token.toString()).createJob();
		System.out.println(jobId);
		assertNotNull(jobId);
		
		String resultId = MemeServerImpl.compareMotifsWithTomtomJobByCollectionFromWs("AKtest", "kb|memepspmcollection.2", "kb|memepspmcollection.2", "", paramsTomtom, jobId, token.toString());

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
		assertEquals(Long.valueOf("65"), result.getHitStart());
		assertEquals(Long.valueOf("78"), result.getHitEnd());
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
		assertEquals(Long.valueOf("65"), result.get(0).getHitStart());
		assertEquals(Long.valueOf("78"), result.get(0).getHitEnd());
		assertEquals(Double.valueOf("1527.00"), result.get(0).getScore());
		assertEquals(Double.valueOf("0.0000253"), result.get(0).getHitPvalue());
	}


	@Test
	public void testFindSitesWithMastByCollection() throws Exception {
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
		
		memeRunResult = MemeServerImpl.findMotifsWithMeme(testSequenceSet, params, null, null);
		MemePSPMCollection memePspmCollection = MemeServerImpl.getPspmCollectionFromMemeResult(memeRunResult);

		MastRunResult result = MemeServerImpl.findSitesWithMastByCollection(memePspmCollection, testSequenceSet, "", 0.0005, null, null);

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
	public void testFindSitesWithMastJobByCollectionFromWs() throws Exception {

		
		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		String jobId = MemeServerImpl.jobClient(token.toString()).createJob();
		
		String resultId = MemeServerImpl.findSitesWithMastJobByCollectionFromWs("AKtest", "kb|memepspmcollection.2", testSequenceSet.getSequenceSetId(), "1", 0.0005, jobId, token.toString());
		
		GetObjectParams params = new GetObjectParams().withType("MastRunResult").withId(resultId).withWorkspace("AKtest").withAsJSON(1L).withAuth(token.toString());
		GetObjectOutput output = MemeServerImpl.wsClient(token.toString()).getObject(params);
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
	public void testGetPspmCollectionFromMemeJobResultFromWs() throws Exception{

		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		String jobId = MemeServerImpl.jobClient(token.toString()).createJob();
		String inputId = "kb|memerunresult.202";
		String resultId = MemeServerImpl.getPspmCollectionFromMemeJobResultFromWs("AKtest", inputId, jobId, token.toString());
				
		GetObjectParams objectParams = new GetObjectParams().withType("MemePSPMCollection").withId(resultId).withWorkspace("AKtest").withAuth(token.toString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(objectParams);
		MemePSPMCollection result = UObject.transformObjectToObject(output.getData(), MemePSPMCollection.class);

		assertNotNull(result);
		assertFalse(result.getPspms().size() == 0);
		assertEquals("ACGT", result.getAlphabet());

	}
	
	@Test
	public void testWsRead() throws Exception {

		AuthToken token = JsonClientCaller.requestTokenFromKBase(USER_NAME, PASSWORD.toCharArray());		
/*		
		List<ObjectIdentity> objectIds = new ArrayList<ObjectIdentity>();
		ObjectIdentity objectIdentity = new ObjectIdentity().withWorkspace("AKtest2").withName("KBase.SequenceSet.12345");
		objectIds.add(objectIdentity);
		List<ObjectData> output = MemeServerImpl.wsClient(token.toString()).getObjects(objectIds);
		
		SequenceSet result = UObject.transformObjectToObject(output.get(0).getData(), SequenceSet.class);
*/
		
		GetObjectParams params = new GetObjectParams().withType("SequenceSet").withId("KBase.SequenceSet.12345").withWorkspace("AKtest").withAsJSON(1L).withAuth(token.toString());
		GetObjectOutput output = MemeServerImpl.wsClient(token.toString()).getObject(params);
		SequenceSet result = UObject.transformObjectToObject(output.getData(), SequenceSet.class);
		assertEquals("GCCGGGCACGGGCCACCTCATCATCCGAGACTGCGACGTCTTTCATGGGGTCTCCGGTTGCTCAAGTATGAGGGTACGATGCCTCCACTCCTGCCCCAAGTCCAGCCGTGCGTGAATGCGGTCACGTTCGTCACCATGAGGGTGACCGGGTTGCCGGGTGCGATACGCAGGGCTAACGCTGCCATAATCGGGAGAGGAGTATCCACGCTTCCGGTCATGCATCATCCACCCGCATCCGCAAGGAGGCCCC",result.getSequences().get(0).getSequence());
		assertEquals(testSequenceSet.getSequenceSetId(),result.getSequenceSetId());
	}

	@Test
	public void testWsWrite() throws Exception {
		
		String id = "kb|sequenceset.3";
		AuthToken token = JsonClientCaller.requestTokenFromKBase(USER_NAME, PASSWORD.toCharArray());
		MemeServerImpl.saveObjectToWorkspace (UObject.transformObjectToObject(testSequenceSet, UObject.class), "SequenceSet", "AKtest", id, token.toString());		
		fail("Not yet implemented"); // TODO
		
	}

/*	@Test
	public void testRegisterModule() throws Exception {
		//Now requestModuleOwnership
		
		AuthToken token = JsonClientCaller.requestTokenFromKBase(USER_NAME, PASSWORD.toCharArray());
		MemeServerImpl.wsClient(token.toString()).requestModuleOwnership("MEME");
		//WSUtil.saveObject(testSequenceSet.getSequenceSetId(), testSequenceSet, false);
		fail("Not yet implemented"); // TODO
		
	}
	
	@Test
	public void testListModules() throws Exception {
		
		AuthToken token = JsonClientCaller.requestTokenFromKBase(USER_NAME, PASSWORD.toCharArray());
		ListModulesParams params = new ListModulesParams();
		
		List<String> result = MemeServerImpl.wsClient(token.toString()).listModules(params);
		System.out.println(result.toString());
		//WSUtil.saveObject(testSequenceSet.getSequenceSetId(), testSequenceSet, false);
		fail("Not yet implemented"); // TODO
		
	}

	@Test
	public void testWsRegisterType() throws Exception {
		//Now CompileTypespec
		
		AuthToken token = JsonClientCaller.requestTokenFromKBase(USER_NAME, PASSWORD.toCharArray());
		CompileTypespecParams params = new CompileTypespecParams();
		params.setMod("MEME");
		List<String> types = new ArrayList<String>();
		types.add("SequenceSet");
		params.setNewTypes(types);
		Map<String,String> result = MemeServerImpl.wsClient(token.toString()).compileTypespec(params);
		System.out.println(result.toString());
		//WSUtil.saveObject(testSequenceSet.getSequenceSetId(), testSequenceSet, false);
		fail("Not yet implemented"); // TODO
		
	}

*/	
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
		System.out.println("versionMeme = "+collection.getMemeVersion());
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

	@Test
	public void testJobClientCreation() throws IOException, JsonClientException {
		URL jobServiceUrl = null;
		UserAndJobStateClient client = null;
		try {
			jobServiceUrl = new URL(JOB_SERVICE);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			client = new UserAndJobStateClient(jobServiceUrl, USER_NAME, PASSWORD);
		} catch (UnauthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.setAuthAllowedForHttp(true);
		List<String> jobServicesList = client.listJobServices();
		assertNotNull(jobServicesList);
	}

	@Test
	public void testCreateJob() {
		URL jobServiceUrl = null;
		UserAndJobStateClient client = null;
		try {
			jobServiceUrl = new URL(JOB_SERVICE);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			client = new UserAndJobStateClient(jobServiceUrl, USER_NAME, PASSWORD);
		} catch (UnauthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.setAuthAllowedForHttp(true);
		try {
			jobId = client.createJob();
			System.out.println(jobId);
			assertNotNull(jobId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testStartJob() throws AuthException, IOException, JsonClientException {
		//AuthToken token = AuthService.login(JOB_ACCOUNT, new String(JOB_PASSWORD)).getToken();
		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		
			String status = "Starting...";
			String desc = "Test job";

			InitProgress initProgress = new InitProgress();
			initProgress.setPtype("task");
			initProgress.setMax(2L);

			Date date = new Date();
			date.setTime(date.getTime()+100000L);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
			MemeServerImpl.jobClient(token.toString()).startJob(jobId, token.toString(), status, desc, initProgress, dateFormat.format(date));
			System.out.println(jobId);
			assertNotNull(jobId);
	}

	@Test
	public void testUpdateJob() throws AuthException, IOException {
		URL jobServiceUrl = null;
		UserAndJobStateClient client = null;
		//AuthToken token = AuthService.login(JOB_ACCOUNT, new String(JOB_PASSWORD)).getToken();
		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		
		try {
			jobServiceUrl = new URL(JOB_SERVICE);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			client = new UserAndJobStateClient(jobServiceUrl, token);
		} catch (UnauthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.setAuthAllowedForHttp(true);
		try {
			String status = "Running...";
			Date date = new Date();
			date.setTime(date.getTime()+10000L);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
			client.updateJob(jobId, token.toString(), status, dateFormat.format(date));
			System.out.println(jobId);
			assertNotNull(jobId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateJobProgress() throws AuthException, IOException {
		URL jobServiceUrl = null;
		UserAndJobStateClient client = null;
		//AuthToken token = AuthService.login(JOB_ACCOUNT, new String(JOB_PASSWORD)).getToken();
		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		
		try {
			jobServiceUrl = new URL(JOB_SERVICE);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			client = new UserAndJobStateClient(jobServiceUrl, token);
		} catch (UnauthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.setAuthAllowedForHttp(true);
		try {
			String status = "Running...";
			Date date = new Date();
			date.setTime(date.getTime()+10000L);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
			client.updateJobProgress(jobId, token.toString(), status, 1L, dateFormat.format(date));
			System.out.println(jobId);
			assertNotNull(jobId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetJobInfo() throws AuthException, IOException {
		URL jobServiceUrl = null;
		UserAndJobStateClient client = null;
		//AuthToken token = AuthService.login(JOB_ACCOUNT, new String(JOB_PASSWORD)).getToken();
		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		
		try {
			jobServiceUrl = new URL(JOB_SERVICE);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			client = new UserAndJobStateClient(jobServiceUrl, token);
		} catch (UnauthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.setAuthAllowedForHttp(true);
		try {
			Tuple14<String,String,String,String,String,String,Long,Long,String,String,Long,Long,String,Results> t = client.getJobInfo(jobId); 
			System.out.println(t.getE1());
			System.out.println(t.getE2());
			System.out.println(t.getE3());
			System.out.println(t.getE4());
			System.out.println(t.getE5());
			System.out.println(t.getE6());
			System.out.println(t.getE7());
			System.out.println(t.getE8());
			System.out.println(t.getE9());
			System.out.println(t.getE10());
			System.out.println(t.getE11());
			System.out.println(t.getE12());
			System.out.println(t.getE13());
			System.out.println(t.getE14());
			assertNotNull(t.getE1());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetJobDescription() throws AuthException, IOException {
		URL jobServiceUrl = null;
		UserAndJobStateClient client = null;
		//AuthToken token = AuthService.login(JOB_ACCOUNT, new String(JOB_PASSWORD)).getToken();
		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		
		try {
			jobServiceUrl = new URL(JOB_SERVICE);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			client = new UserAndJobStateClient(jobServiceUrl, token);
		} catch (UnauthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.setAuthAllowedForHttp(true);
		try {
			Tuple5<String,String,Long,String,String> t = client.getJobDescription(jobId); 
			System.out.println(t.getE1());
			System.out.println(t.getE2());
			System.out.println(t.getE3());
			System.out.println(t.getE4());
			System.out.println(t.getE5());
			assertNotNull(t.getE1());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetJobStatus() throws AuthException, IOException {
		URL jobServiceUrl = null;
		UserAndJobStateClient client = null;
		//AuthToken token = AuthService.login(JOB_ACCOUNT, new String(JOB_PASSWORD)).getToken();
		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		
		try {
			jobServiceUrl = new URL(JOB_SERVICE);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			client = new UserAndJobStateClient(jobServiceUrl, token);
		} catch (UnauthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.setAuthAllowedForHttp(true);
		try {
			Tuple7<String,String,String,Long,String,Long,Long> t = client.getJobStatus(jobId); 
			System.out.println(t.getE1());
			System.out.println(t.getE2());
			System.out.println(t.getE3());//Status
			System.out.println(t.getE4());
			System.out.println(t.getE5());
			System.out.println(t.getE6());
			System.out.println(t.getE7());
			assertNotNull(t.getE1());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCompleteJob() throws AuthException, IOException {
		URL jobServiceUrl = null;
		UserAndJobStateClient client = null;
		//AuthToken token = AuthService.login(JOB_ACCOUNT, new String(JOB_PASSWORD)).getToken();
		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		
		try {
			jobServiceUrl = new URL(JOB_SERVICE);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			client = new UserAndJobStateClient(jobServiceUrl, token);
		} catch (UnauthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.setAuthAllowedForHttp(true);
		try {
			String status = "Finished";
			String error = null;
			
			String wsId = "AKtest";
			String resultId = "KBase.SequenceSet.12345";
			
			Results res = new Results();
			List<String> workspaceIds = new ArrayList<String>();
			workspaceIds.add(wsId);
			workspaceIds.add(resultId);
			res.setWorkspaceids(workspaceIds);
			client.completeJob(jobId, token.toString(), status, error, res); 
			assertNotNull(jobId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetResults() throws AuthException, IOException {
		URL jobServiceUrl = null;
		UserAndJobStateClient client = null;
		//AuthToken token = AuthService.login(JOB_ACCOUNT, new String(JOB_PASSWORD)).getToken();
		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		
		try {
			jobServiceUrl = new URL(JOB_SERVICE);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			client = new UserAndJobStateClient(jobServiceUrl, token);
		} catch (UnauthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.setAuthAllowedForHttp(true);

		try {
			Results res = client.getResults(jobId);			
			String workspaceId = res.getWorkspaceids().get(0);
			System.out.println(workspaceId);
			String resultId = res.getWorkspaceids().get(1);
			System.out.println(resultId);
			
			assertNotNull(res.getAdditionalProperties().keySet());
			assertEquals(workspaceId,"AKtest");
			assertEquals(resultId,"KBase.SequenceSet.12345");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteJob() throws AuthException, IOException, UnauthorizedException, JsonClientException {
		String jobId = "52904a4fe4b0702f7611d90f";

//		AuthToken token = AuthService.login(JOB_ACCOUNT, new String(JOB_PASSWORD)).getToken();
		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		
			MemeServerImpl.jobClient(token.toString()).forceDeleteJob(token.toString(), jobId); 
	}

	@Test
	public void testJsonCall() throws IOException, JsonClientException, AuthException {
		
		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
        Map<String, String> jsonArgs = new HashMap<String, String>();
        jsonArgs.put("target", "cloud");
        jsonArgs.put("application", "meme");
        jsonArgs.put("method", "find_motifs_with_meme_job_from_ws");
        jsonArgs.put("job_id", "12345");
        jsonArgs.put("workspace", "AKtest");
        jsonArgs.put("seq_id", "KBase.SequenceSet.12345");
        jsonArgs.put("mod", "oops");
        jsonArgs.put("nmotifs", "2");
        jsonArgs.put("minw", "14");
        jsonArgs.put("maxw", "24");
        jsonArgs.put("pal", "1");
        jsonArgs.put("revcomp", "0");
        jsonArgs.put("nsites", "0");
        jsonArgs.put("minsites", "0");
        jsonArgs.put("maxsites", "0");
        jsonArgs.put("token", token.toString());

        MemeServerCaller.setAuthAllowedForHttp(true);
		String result = MemeServerCaller.jsonCall(jsonArgs, token);
		System.out.println(result);

	}
	
	@Test
	public final void testGetKbaseId() throws Exception {
		String id = MemeServerImpl.getKbaseId("MemeMotif");
		System.out.println(id);
		assertNotNull(id);
		
	}
	/*	@Test
	public void testCreateWS() throws AuthException, IOException, JsonClientException {
		WSUtil.createWorkspace("AKtest2", "AK test workspace");
	}
*/	
}
