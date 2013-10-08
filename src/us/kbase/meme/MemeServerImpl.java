package us.kbase.meme;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import us.kbase.UObject;
import us.kbase.auth.AuthUser;
import us.kbase.generaltypes.Sequence;
import us.kbase.generaltypes.SequenceSet;
import us.kbase.idserverapi.IdserverapiClient;
import us.kbase.util.WSUtil;
import us.kbase.workspaceservice.GetObjectOutput;
import us.kbase.workspaceservice.GetObjectParams;

public class MemeServerImpl {
	private static Integer jobId = 0;
	private static final String WORK_DIRECTORY = "."; 
	private static final String ID_SERVICE_URL = "http://kbase.us/services/idserver";
	private static Pattern spacePattern = Pattern.compile("[\\n\\t ]");
	private static Date date = new Date();
	
	
	public static void cleanUpOnStart () {
		try {
			Runtime.getRuntime().exec("rm "+WORK_DIRECTORY+"/*.fasta");
			Runtime.getRuntime().exec("rm "+WORK_DIRECTORY+"/*.meme");
			Runtime.getRuntime().exec("rm "+WORK_DIRECTORY+"/*.out");
			Runtime.getRuntime().exec("rm "+WORK_DIRECTORY+"/*_tomtom.txt");
			Runtime.getRuntime().exec("rm "+WORK_DIRECTORY+"/*_mast.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static MemeRunResult findMotifsWithMeme(
			SequenceSet sequenceSet, MemeRunParameters params) throws Exception {
		MemeRunResult returnVal = null;
		// Generate unique jobId for the MEME run
		String currentJobId = getJobId();
		String inputFileName = WORK_DIRECTORY+"/"+currentJobId	+ ".fasta";
		String outputFileName = WORK_DIRECTORY+"/"+currentJobId	+ ".out";
		// Generate MEME command line
		String memeCommand = generateMemeCommandLine(inputFileName, params.getMod(),
				params.getNmotifs(), params.getMinw(), params.getMaxw(), params.getNsites(), params.getMinsites(),
				params.getMaxsites(), params.getPal(), params.getRevcomp());
		try {
			// Generate MEME input file in FASTA format
			generateFastaFile(inputFileName, sequenceSet);
			// Run MEME and get a list of output strings
			executeCommand(memeCommand, outputFileName);
			// Parse MEME output
			returnVal = parseMemeOutput(outputFileName);
			returnVal.setId(getKbaseId(MemeRunResult.class.getSimpleName()));
		}
		finally {
			// Clean up
			Runtime.getRuntime().exec("rm " + inputFileName);
			Runtime.getRuntime().exec("rm " + outputFileName);
		}

		return returnVal;
	}
	
	public static String findMotifsWithMemeFromWs(
			String wsId, String sequenceSetId, MemeRunParameters params, AuthUser authPart) throws Exception {
		String returnVal = null;

		GetObjectParams objectParams = new GetObjectParams().withType("SequenceSet").withId(sequenceSetId).withWorkspace(wsId).withAuth(authPart.getTokenString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(objectParams);
		SequenceSet input = UObject.transform(output.getData(), SequenceSet.class);
		MemeRunResult memeRunResult = findMotifsWithMeme(input, params);
		//Write result to WS
		returnVal = memeRunResult.getId();
		WSUtil.saveObject(returnVal, memeRunResult, false);
		return returnVal;		
	}

	protected static String getJobId() {
		jobId++;
		String retVal = jobId.toString();
		return retVal;
	}

	protected static void generateFastaFile(String jobId,
			SequenceSet sequenceSet) {
		String memeInput = new String();
		for (Sequence sequence : sequenceSet.getSequences()) {
			memeInput += ">" + sequence.getSequenceId() + "\n"
					+ formatSequence(sequence.getSequence()) + "\n";
		}
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(jobId));
			writer.write(memeInput);
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
	}
	
	protected static String formatSequence (String sequence){
		String result = "";
		if (sequence.length() > 80){
			int i=0;
			for(i=0; i<sequence.length()-80;i=i+80){
				result += sequence.substring(i, i+80) + "\n";
			}
			result += sequence.substring(i);
		} else {
			result = sequence;
		}
		return result;
	}

	protected static String generateMemeCommandLine(String jobId,
			String mod, Integer nmotifs, Integer minw,
			Integer maxw, Integer nsites, Integer minsites,
			Integer maxsites, Integer pal, Integer revcomp) throws UnsupportedEncodingException {
		if ((!mod.equals("oops")) && (!mod.equals("zoops")) && (!mod.equals("anr"))) {
			System.out.println("Unknown type of distribution: " + mod
					+ ". oops will be used instead.");
			mod = "oops";
		}
		String memeCommand = "meme " + jobId + " -mod " + mod;
		if (nmotifs > 0)
			memeCommand += " -nmotifs " + nmotifs;
		if (minw > 0)
			memeCommand += " -minw " + minw;
		if (maxw > 0)
			memeCommand += " -maxw " + maxw;
		if (nsites > 0 && mod != "oops")
			memeCommand += " -nsites " + nsites;
		if (minsites > 0 && mod != "oops")
			memeCommand += " -minsites " + minsites;
		if (maxsites > 0 && mod != "oops")
			memeCommand += " -maxsites " + maxsites;
		if (pal == 1)
			memeCommand += " -pal";
		if (revcomp == 1)
			memeCommand += " -revcomp";
		memeCommand += " -dna -text -nostatus";
		return memeCommand;
	}

	protected static void executeCommand(String commandLine, String outputFileName) {
		BufferedWriter writer = null;
		try {
			Process p = Runtime.getRuntime().exec(commandLine);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			writer = new BufferedWriter(new FileWriter(outputFileName));
			String line;
			while ((line = br.readLine()) != null) {
				writer.write(line+"\n");
			}
			br.close();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
	}

	protected static MemeRunResult parseMemeOutput(String memeOutputFile) throws Exception {
		MemeRunResult memeRunResult = new MemeRunResult();
		List<MemeMotif> motifs = new ArrayList<MemeMotif>();
		List<String> sites = new ArrayList<String>();
		memeRunResult
				.setId(getKbaseId(MemeRunResult.class.getSimpleName()));
		memeRunResult.setTimestamp(String.valueOf(date.getTime()));
		boolean trainingSetSection = false;
		boolean commandLineSection = false;
		boolean motifSection = false;
		boolean letterFreqLine = false;
		boolean backFreqLine = false;
		boolean sitesSection = false;
		int asterisksLineCounter = 0;
		String cumulativeOutput = "";
		List<String> trainingSet = new ArrayList<String>();
		String motifDataLine = "starting value";
		String line = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(memeOutputFile));
			while ((line = br.readLine()) != null) {
				cumulativeOutput += line+"\n";
				if (!trainingSetSection) {
					if (line.matches("^MEME version.*")){
						memeRunResult.setVersion(line);
					} else if (line.contains("TRAINING SET")){
						trainingSetSection = true;
					} else {}
				} else {
					if (!commandLineSection) {
						if (line.contains("DATAFILE=")) {
							memeRunResult.setInputFileName(line.substring(10));
						} else if (line.contains("ALPHABET=")) {
							memeRunResult.setAlphabet(line.substring(10));
						} else if (line.contains("********************************************************************************")|| line.matches("")) {
							//skip line
						} else if (line.contains("COMMAND LINE SUMMARY")) {
							commandLineSection = true;
							memeRunResult.setTrainingSet(trainingSet);
						} else {
							trainingSet.add(line);
						}
					} else {
						if (!motifSection) {
							if (line.contains("command: meme")) {
								memeRunResult.setCommandLine(line
										.substring(9));
							} else if (line.contains("model:  mod=")) {
								processModLine(memeRunResult, line);
							} else if (line.contains("object function=")) {
								processOFLine(memeRunResult, line);
							} else if (line.contains("width:  minw=")) {
								processMinwLine(memeRunResult, line);
							} else if (line.contains("width:  wg=")) {
								processWgLine(memeRunResult, line);
							} else if (line.contains("nsites: minsites=")) {
								processNsitesLine(memeRunResult, line);
							} else if (line.contains("theta:  prob=")) {
								processThetaLine(memeRunResult, line);
							} else if (line.contains("global: substring=")) {
								processGlobalsLine(memeRunResult, line);
							} else if (line.contains("em:     prior=")) {
								processEmLine(memeRunResult, line);
							} else if (line.contains("        distance=")) {
								processDistanceLine(memeRunResult, line);
							} else if (line.contains("data:   n=")) {
								processDataNLine(memeRunResult, line);
							} else if (line.contains("strands: ")) {
								processStrandsLine(memeRunResult, line);
							} else if (line.contains("sample: seed=")) {
								processSampleLine(memeRunResult, line);
							} else if (line
									.contains("Letter frequencies in dataset:")) {
								letterFreqLine = true;
							} else if (letterFreqLine) {
								letterFreqLine = false;
								memeRunResult.setLetterFreq(line);
							} else if (line
									.contains("Background letter frequencies (from dataset with add-one prior applied):")) {
								backFreqLine = true;
							} else if (backFreqLine) {
								backFreqLine = false;
								memeRunResult.setBgFreq(line);
							} else if (line
									.contains("********************************************************************************")) {
								if (asterisksLineCounter == 0) {
									asterisksLineCounter++;
								} else {
									memeRunResult
											.setRawOutput(cumulativeOutput);
									cumulativeOutput = "";
									motifSection = true;
									asterisksLineCounter = 0;
								}
							}
						} else {
							if (line.contains("********************************************************************************")) {
								asterisksLineCounter++;
								switch (asterisksLineCounter) {
								case 1:
									break;
								case 2:
									break;
								case 3:
									asterisksLineCounter = 0;
									generateMemeMotif(motifDataLine,
											cumulativeOutput, sites, motifs);
									motifDataLine = "";
									cumulativeOutput = "";
									sites.clear();
									break;
								default:
									break;
								}
							} else if (line.matches("^MOTIF.*")) {
								motifDataLine = line;
							} else if (line
									.matches("^Sequence name.*Start.*P-value.*Site.*")) {
								sitesSection = true;
							} else if (line
									.contains("--------------------------------------------------------------------------------")) {
								sitesSection = false;
							} else if (sitesSection) {
								if (line.matches("^-------------.*")){
									//skip line
								} else {
								sites.add(line);
								}
							} else if (line.contains("SUMMARY OF MOTIFS")){
								break;
							}
						}
					}
				}
			}
			br.close();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}  
		memeRunResult.setMotifs(motifs);
		return memeRunResult;
	}

	protected static void generateMemeMotif(String motifDataLine,
			String cumulativeOutput, List<String> sites, List<MemeMotif> motifs) throws Exception {
		MemeMotif motif = new MemeMotif();
		motif.setId(getKbaseId(MemeMotif.class.getSimpleName()));
		motif.setRawOutput(cumulativeOutput);
		processMotifDataLine(motif, motifDataLine);
		List<MemeSite> motifSites = new ArrayList<MemeSite>();
		for (String siteLine : sites) {
			MemeSite site = generateMemeSite(siteLine);
			motifSites.add(site);
		}
		motif.setSites(motifSites);
		motifs.add(motif);
	}

	protected static void processMotifDataLine(MemeMotif motif,
			String motifDataLine) {
		motif.setDescription(motifDataLine);
		motifDataLine = spacePattern.matcher(motifDataLine).replaceAll("");
		motif.setWidth(Integer.parseInt(motifDataLine.substring(
				motifDataLine.indexOf("width=") + 6,
				motifDataLine.indexOf("sites="))));
		motif.setLlr(Double.parseDouble(motifDataLine.substring(
				motifDataLine.indexOf("llr=") + 4,
				motifDataLine.indexOf("E-value="))));
		motif.setEvalue(Double.parseDouble(motifDataLine
				.substring(motifDataLine.indexOf("E-value=") + 8)));
	}

	protected static MemeSite generateMemeSite(String siteLine) {
		MemeSite site = new MemeSite();
		siteLine = siteLine.replaceAll("\\s+", " ");
		String[] siteData = siteLine.split(" ");
		site.setSourceSequenceId(siteData[0]);
		site.setStart(Integer.parseInt(siteData[1]));
		site.setPvalue(Double.parseDouble(siteData[2]));
		site.setLeftFlank(siteData[3]);
		site.setSequence(siteData[4]);
		site.setRightFlank(siteData[5]);
		return site;
	}

	protected static void processSampleLine(
			MemeRunResult memeRunResult, String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult.setSeed(Integer.parseInt(line.substring(12,
				line.indexOf("seqfrac="))));
		memeRunResult.setSeqfrac(Integer.parseInt(line.substring(line
				.indexOf("seqfrac=") + 8)));
	}

	protected static void processStrandsLine(
			MemeRunResult memeRunResult, String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult.setStrands(line.substring(8));
	}

	protected static void processDataNLine(MemeRunResult memeRunResult,
			String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult.setN(Integer.parseInt(line.substring(7,
				line.indexOf("N="))));
		memeRunResult.setNCap(Integer.parseInt(line.substring(line
				.indexOf("N=") + 2)));
	}

	protected static void processDistanceLine(
			MemeRunResult memeRunResult, String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult.setDistance(Double.parseDouble(line.substring(line
				.indexOf("distance=") + 9)));
	}

	protected static void processEmLine(MemeRunResult memeRunResult,
			String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult.setPrior(line.substring(9, line.indexOf("b=")));
		memeRunResult.setB(Double.parseDouble(line.substring(
				line.indexOf("b=") + 2, line.indexOf("maxiter="))));
		memeRunResult.setMaxiter(Integer.parseInt(line.substring(line
				.indexOf("maxiter=") + 8)));
	}

	protected static void processGlobalsLine(
			MemeRunResult memeRunResult, String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult.setSubstring(line.substring(17,
				line.indexOf("branching=")));
		memeRunResult.setBranching(line.substring(
				line.indexOf("branching=") + 10, line.indexOf("wbranch=")));
		memeRunResult
				.setWbranch(line.substring(line.indexOf("wbranch=") + 8));
	}

	protected static void processThetaLine(MemeRunResult memeRunResult,
			String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult.setProb(Integer.parseInt(line.substring(11,
				line.indexOf("spmap="))));
		memeRunResult.setSpmap(line.substring(line.indexOf("spmap=") + 6,
				line.indexOf("spfuzz")));
		memeRunResult
				.setSpfuzz(line.substring(line.indexOf("spfuzz=") + 7));
	}

	protected static void processNsitesLine(
			MemeRunResult memeRunResult, String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult.setMinsites(Integer.parseInt(line.substring(16,
				line.indexOf("maxsites="))));
		memeRunResult.setMaxsites(Integer.parseInt(line.substring(
				line.indexOf("maxsites=") + 9, line.indexOf("wnsites="))));
		memeRunResult.setWnsites(Double.parseDouble(line.substring(line
				.indexOf("wnsites=") + 8)));
	}

	protected static void processWgLine(MemeRunResult memeRunResult,
			String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult.setWg(Integer.parseInt(line.substring(9,
				line.indexOf("ws="))));
		memeRunResult.setWs(Integer.parseInt(line.substring(
				line.indexOf("ws=") + 3, line.indexOf("endgaps="))));
		memeRunResult
				.setEndgaps(line.substring(line.indexOf("endgaps=") + 8));
	}

	protected static void processMinwLine(MemeRunResult memeRunResult,
			String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult.setMinw(Integer.parseInt(line.substring(11,
				line.indexOf("maxw="))));
		memeRunResult.setMaxw(Integer.parseInt(line.substring(
				line.indexOf("maxw=") + 5, line.indexOf("minic="))));
		memeRunResult.setMinic(Double.parseDouble(line.substring(line
				.indexOf("minic=") + 6)));
	}

	protected static void processOFLine(MemeRunResult memeRunResult,
			String line) {
		memeRunResult.setObjectFunction(line.substring(18));
	}

	protected static void processModLine(MemeRunResult memeRunResult,
			String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult.setMod(line.substring(10,
				line.indexOf("nmotifs=")));
		memeRunResult.setNmotifs(Integer.parseInt(line.substring(
				line.indexOf("nmotifs=") + 8, line.indexOf("evt="))));
		memeRunResult.setEvt(line.substring(line.indexOf("evt=") + 4));
	}

	public static TomtomRunResult compareMotifsWithTomtom(MemePSPM query, MemePSPMCollection target, TomtomRunParameters params) throws Exception {
				
		MemePSPMCollection queryCol = makePSPMCollection(query);
		TomtomRunResult result = compareMotifsWithTomtomByCollection(queryCol, target, "", params);
		return result;
	}

	public static String compareMotifsWithTomtomFromWs(String wsId, String queryId, String targetId, TomtomRunParameters params, AuthUser authPart) throws MalformedURLException, Exception{

		GetObjectParams queryParams = new GetObjectParams().withType("MemePSPM").withId(queryId).withWorkspace(wsId).withAuth(authPart.getTokenString());   
		GetObjectOutput queryOutput = WSUtil.wsClient().getObject(queryParams);
		MemePSPM query = UObject.transform(queryOutput.getData(), MemePSPM.class);
		GetObjectParams targetParams = new GetObjectParams().withType("MemePSPMCollection").withId(targetId).withWorkspace(wsId).withAuth(authPart.getTokenString());
		GetObjectOutput targetOutput = WSUtil.wsClient().getObject(targetParams);
		MemePSPMCollection target = UObject.transform(targetOutput.getData(), MemePSPMCollection.class);
		
		TomtomRunResult result = compareMotifsWithTomtom(query, target, params);

		//Write result to WS
		String returnVal = result.getId();
		WSUtil.saveObject(returnVal, result, false);
		return returnVal;
	}
	
	public static TomtomRunResult compareMotifsWithTomtomByCollection(MemePSPMCollection query, MemePSPMCollection target, String pspmId, TomtomRunParameters params) throws Exception {
		TomtomRunResult result = new TomtomRunResult();
		String distTomtom = new String(params.getDist().getBytes("ISO-8859-1"), "UTF-8");// MAGIC!
		String jobId = getJobId();
		String firstInputFile = WORK_DIRECTORY+"/"+jobId+"_query.meme";
		String secondInputFile = WORK_DIRECTORY+"/"+jobId+"_target.meme";
		String outputFileName = WORK_DIRECTORY+"/"+jobId+"_tomtom.txt";
		//Generate command line
		String commandLineTomtom = generateTomtomCommandLine (firstInputFile, secondInputFile, params.getThresh(),
				params.getEvalue(), distTomtom, params.getInternal(), params.getMinOverlap(), pspmId);
		try {
			//Generate first input file
			generateSimpleMemeFile(query, firstInputFile);
			//Generate second input file
			generateSimpleMemeFile(target, secondInputFile);
			//Run TOMTOM
			executeCommand(commandLineTomtom, outputFileName);
			//Parse output file
			result = parseTomtomOutput(outputFileName, params);
		}
		finally {
			//Clean up
/*			try {
				Runtime.getRuntime().exec("rm " + firstInputFile);
				Runtime.getRuntime().exec("rm " + secondInputFile);
				Runtime.getRuntime().exec("rm " + outputFileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		return result;
		
	}
	
	public static String compareMotifsWithTomtomByCollectionFromWs(String wsId, String queryId, String targetId, String pspmId, TomtomRunParameters params, AuthUser authPart) throws MalformedURLException, Exception{

		GetObjectParams queryParams = new GetObjectParams().withType("MemePSPMCollection").withId(queryId).withWorkspace(wsId).withAuth(authPart.getTokenString());   
		GetObjectOutput queryOutput = WSUtil.wsClient().getObject(queryParams);
		MemePSPMCollection query = UObject.transform(queryOutput.getData(), MemePSPMCollection.class);
		GetObjectParams targetParams = new GetObjectParams().withType("MemePSPMCollection").withId(targetId).withWorkspace(wsId).withAuth(authPart.getTokenString());
		GetObjectOutput targetOutput = WSUtil.wsClient().getObject(targetParams);
		MemePSPMCollection target = UObject.transform(targetOutput.getData(), MemePSPMCollection.class);
		TomtomRunResult result = compareMotifsWithTomtomByCollection(query, target, pspmId, params);

		//Write result to WS
		String returnVal = result.getId();
		WSUtil.saveObject(returnVal, result, false);
		return returnVal;
	}
	
	public static MemePSPMCollection getPspmCollectionFromMemeResult (MemeRunResult memeRunResult) throws Exception{
		MemePSPMCollection returnVal = new MemePSPMCollection();
		returnVal.setId(getKbaseId("MemePSPMCollection"));
		returnVal.setTimestamp(String.valueOf(date.getTime()));
		returnVal.setDescription("Based on "+ memeRunResult.getId() +" MEME run");
		returnVal.setAlphabet(memeRunResult.getAlphabet());
		List<MemePSPM> pspms = new ArrayList<MemePSPM>();
		for (MemeMotif motif : memeRunResult.getMotifs()){
			MemePSPM pspm = generateMemePSPM(motif, memeRunResult.getAlphabet());
			pspms.add(pspm);
		}
		returnVal.setPspms(pspms);
		return returnVal;
	}
	
	public static String getPspmCollectionFromMemeResultFromWs(String wsId, String memeRunResultId, AuthUser authPart) throws Exception {

		GetObjectParams objectParams = new GetObjectParams().withType("MemeRunResult").withId(memeRunResultId).withWorkspace(wsId).withAuth(authPart.getTokenString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(objectParams);
		MemeRunResult result = UObject.transform(output.getData(), MemeRunResult.class);
		MemePSPMCollection collection = getPspmCollectionFromMemeResult(result);
		//Write result to WS
		String returnVal = collection.getId();
		WSUtil.saveObject(returnVal, collection, false);
		return returnVal;		
		
	}

	protected static MemePSPMCollection makePSPMCollection (MemePSPM pspm){
		MemePSPMCollection returnVal = new MemePSPMCollection();
		returnVal.setId("undefined");
		returnVal.setTimestamp("undefined");
		returnVal.setDescription("temporary collection for TOMTOM run");
		returnVal.setAlphabet(pspm.getAlphabet());
		List<MemePSPM> pspms = new ArrayList<MemePSPM>();
		pspms.add(pspm);
		returnVal.setPspms(pspms);
		
		return returnVal;
	}
	
	protected static MemePSPM generateMemePSPM (MemeMotif motif, String alphabet) throws Exception{
		MemePSPM returnVal = new MemePSPM();
		returnVal.setId(getKbaseId("MemePSPM"));
		returnVal.setSourceId(motif.getId());
		returnVal.setSourceType(MemeMotif.class.getSimpleName());
		returnVal.setDescription(motif.getDescription());
		returnVal.setAlphabet(alphabet);
		returnVal.setWidth(motif.getSites().get(0).getSequence().length());
		returnVal.setNsites(motif.getSites().size());
		returnVal.setEvalue(motif.getEvalue());
		returnVal.setMatrix(generatePspMatrix(motif.getRawOutput()));
		return returnVal;
	}
	
	protected static List<List<Double>> generatePspMatrix (String output){
		List<List<Double>> returnVal = new ArrayList<List<Double>>();
		String[] memeOut = output.split("\n");
		Boolean lpmSection = false;
		
		for (String line: memeOut){
			if (lpmSection == true){
				if (line.contains("--------------------------------------------------------------------------------")){
					lpmSection = false;
				} else {
					String[] matrixTextRow = line.split(" ");
					List<Double> matrixRow = new ArrayList<Double>();
					for (String matrixTextValue: matrixTextRow){
						if(!matrixTextValue.equals(""))	matrixRow.add(Double.parseDouble(matrixTextValue));
					}
					returnVal.add(matrixRow);
				}
			} else {
				if (line.matches("^letter-probability matrix: alength=.*")){
				lpmSection = true;
				}
			}
		}
		return returnVal;
	}
	
	protected static void generateSimpleMemeFile (MemePSPM pspm, String fileName){
		String tomtomInput = "MEME version 4\n\n"; 				//Version
		tomtomInput += "ALPHABET= "+pspm.getAlphabet()+"\n\n"; 	//Alphabet
		tomtomInput += "strands + -\n\n";						//Strands
		tomtomInput += "Background letter frequencies\nA 0.25 C 0.25 G 0.25 T 0.25\n\n"; //Background frequencies are uniform
		tomtomInput += "MOTIF " +pspm.getId()+ "\n";			//Motif ID
		tomtomInput += "letter-probability matrix: alength= " +pspm.getAlphabet().length()+ " w= " +pspm.getWidth()+ " nsites= " +pspm.getNsites()+ " E= " +pspm.getEvalue()+ "\n";
		//Print matrix
		DecimalFormat df = new DecimalFormat("0.000000");
		for (List<Double> row : pspm.getMatrix()){
			for (Double value : row){
				tomtomInput+= " "+ df.format(value);
			}
			tomtomInput += "\n";
		}
		tomtomInput += "\n";
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(tomtomInput);
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
	}

	protected static void generateSimpleMemeFile (MemePSPMCollection collection, String fileName){
		String tomtomInput = "MEME version 4\n\n"; 				//Version
		tomtomInput += "ALPHABET= "+collection.getAlphabet()+"\n\n"; 	//Alphabet
		tomtomInput += "strands + -\n\n";						//Strands
		tomtomInput += "Background letter frequencies\nA 0.25 C 0.25 G 0.25 T 0.25\n\n"; //Background frequencies are uniform
		for (MemePSPM pspm : collection.getPspms()){
			tomtomInput += "MOTIF " +pspm.getId()+ "\n";			//Motif ID
			tomtomInput += "letter-probability matrix: alength= " +pspm.getAlphabet().length()+ " w= " +pspm.getWidth()+ " nsites= " +pspm.getNsites()+ " E= " +pspm.getEvalue()+ "\n";
			//Print matrix
			DecimalFormat df = new DecimalFormat("0.000000");
			for (List<Double> row : pspm.getMatrix()){
				for (Double value : row){
					tomtomInput+= " "+ df.format(value);
				}
				tomtomInput += "\n";
			}
			tomtomInput += "\n";
		}
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(tomtomInput);
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
	}
	
	
	protected static String generateTomtomCommandLine (String firstInputFile, String secondInputFile, Double threshTomtom,
			Integer evalueTomtom, String distTomtom, Integer internalTomtom, Integer minOverlapTomtom, String pspmId){
		String commandLine = "tomtom";
		if (!pspmId.equals("")){
			commandLine += " -m " + pspmId;
		}
		if (threshTomtom > 0){
			commandLine += " -thresh " + threshTomtom.toString();
		}
		if (evalueTomtom == 1){
			commandLine += " -evalue";
		} else if (evalueTomtom != 0) {
			System.out.println("Cannot parse value of e-value parameter: " + evalueTomtom.toString());
		}
		if (distTomtom.equals("allr")||distTomtom.equals("ed")||distTomtom.equals("kullback")||distTomtom.equals("pearson")||distTomtom.equals("sandelin")){
			commandLine += " -dist " + distTomtom;
		} else {
			System.out.println("Cannot parse value of dist parameter: " + distTomtom);
		}
		if (internalTomtom == 1){
			commandLine += " -internal";
		} else if (evalueTomtom != 0) {
			System.out.println("Cannot parse value of internalTomtom parameter: " + internalTomtom.toString());
		}
		if (minOverlapTomtom >= 1){
			commandLine += " -min-overlap " + minOverlapTomtom.toString();
		} else if (evalueTomtom != 0) {
			System.out.println("Cannot parse value of internalTomtom parameter: " + internalTomtom.toString());
		}
		
		commandLine += " -text";
		commandLine += " " + firstInputFile + " " + secondInputFile; 
		return commandLine;
	}
	
	protected static TomtomRunResult parseTomtomOutput(String tomtomOutputFile, TomtomRunParameters params) throws Exception {
		TomtomRunResult returnVal = new TomtomRunResult();
		returnVal.setId(getKbaseId(TomtomRunResult.class.getSimpleName()));
		returnVal.setTimestamp(String.valueOf(date.getTime()));
		returnVal.setThresh(params.getThresh());
		returnVal.setEvalue(params.getEvalue());
		returnVal.setDist(params.getDist());
		returnVal.setInternal(params.getInternal());
		returnVal.setMinOverlap(params.getMinOverlap());
		
		
		List<TomtomHit> hits = new ArrayList<TomtomHit>();
		try {
			String line = null;
			BufferedReader br = new BufferedReader(new FileReader(tomtomOutputFile));
			while ((line = br.readLine()) != null) {
				if (line.contains("#Query ID	Target ID	Optimal offset	p-value	E-value	q-value	Overlap	Query consensus	Target consensus	Orientation")){
					//do nothing
				} else{
					hits.add(generateHitTomtom(line));
				}
			}
			br.close();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
		returnVal.setHits(hits);
		return returnVal;
	}
	
	protected static TomtomHit generateHitTomtom (String line){
		TomtomHit result = new TomtomHit();
		String[] hitData = line.split("\t");
		result.setQueryPspmId(hitData[0]);
		result.setTargetPspmId(hitData[1]);
		result.setOptimalOffset(Integer.parseInt(hitData[2]));
		result.setPvalue(Double.parseDouble(hitData[3]));
		result.setEvalue(Double.parseDouble(hitData[4]));
		result.setQvalue(Double.parseDouble(hitData[5]));
		result.setOverlap(Integer.parseInt(hitData[6]));
		result.setQueryConsensus(hitData[7]);
		result.setTargetConsensus(hitData[8]);
		result.setStrand(hitData[9]);
		return result;
	}
	
	public static MastRunResult findSitesWithMastByCollection(MemePSPMCollection query, SequenceSet target, String pspmId, Double mt) throws Exception{
		MastRunResult returnVal = new MastRunResult();
		returnVal.setId(getKbaseId(MastRunResult.class.getSimpleName()));
		returnVal.setTimestamp(String.valueOf(date.getTime()));
		returnVal.setMt(mt);

		List<MastHit> hitList = new ArrayList<MastHit>();
		String jobId = getJobId();
		String motifFileName = WORK_DIRECTORY+"/"+jobId+"_query.meme";
		String sequenceFileName = WORK_DIRECTORY+"/"+jobId+"_target.fasta";
		String outputFileName = WORK_DIRECTORY+"/"+jobId+"_mast.txt";
		Integer pspmNumber = -1;
		if (!pspmId.equals("")) {
			pspmNumber = getPSPMnumber(query, pspmId);
		}
		//Generate command line
		String commandLine = generateMastCommandLine (motifFileName, sequenceFileName, mt, pspmNumber);
		try {
			//Generate motif input file
			generateSimpleMemeFile(query, motifFileName);
			//Generate sequences input file
			generateFastaFile(sequenceFileName, target);
			//Run Mast
			executeCommand(commandLine, outputFileName);
			//Parse output
			hitList = parseMastOutput(outputFileName);
		}
		finally {
			//Clean up
			try {
				Runtime.getRuntime().exec("rm " + motifFileName);
				Runtime.getRuntime().exec("rm " + sequenceFileName);
				Runtime.getRuntime().exec("rm " + outputFileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		returnVal.setHits(hitList);
				
		return returnVal;
	}
	
	public static String findSitesWithMastByCollectionFromWs(String wsId, String queryId, String targetId, String pspmId, Double mt, AuthUser authPart) throws MalformedURLException, Exception{

		GetObjectParams queryParams = new GetObjectParams().withType("MemePSPMCollection").withId(queryId).withWorkspace(wsId).withAuth(authPart.getTokenString());   
		GetObjectOutput queryOutput = WSUtil.wsClient().getObject(queryParams);
		MemePSPMCollection query = UObject.transform(queryOutput.getData(), MemePSPMCollection.class);
		GetObjectParams targetParams = new GetObjectParams().withType("SequenceSet").withId(targetId).withWorkspace(wsId).withAuth(authPart.getTokenString());
		GetObjectOutput targetOutput = WSUtil.wsClient().getObject(targetParams);
		SequenceSet target = UObject.transform(targetOutput.getData(), SequenceSet.class);
		MastRunResult result = findSitesWithMastByCollection(query, target, pspmId, mt);

		//Write result to WS
		String returnVal = result.getId();
		WSUtil.saveObject(returnVal, result, false);
		return returnVal;
		
	}
	
	public static MastRunResult findSitesWithMast (MemePSPM query, SequenceSet target, Double mt) throws Exception{

		MemePSPMCollection queryCol = makePSPMCollection(query);
		MastRunResult returnVal = findSitesWithMastByCollection(queryCol, target, "", mt);
		return returnVal;

	}
	
	public static String findSitesWithMastFromWs(String wsId, String queryId, String targetId, Double mt, AuthUser authPart) throws MalformedURLException, Exception{
		
		String returnVal = findSitesWithMastByCollectionFromWs(wsId, queryId, targetId, "", mt, authPart);
		return returnVal;
	}
	
	protected static Integer getPSPMnumber(MemePSPMCollection query, String pspmId){
		for (MemePSPM pspm : query.getPspms()){
			if (pspm.getId().equals(pspmId)) return query.getPspms().indexOf(pspm);
		}
		return -1;
	}

	protected static String generateMastCommandLine (String motifFileName, String sequenceFileName, Double mtMast, Integer pspmNumber) {
		String result = "mast " + motifFileName + " " + sequenceFileName + " ";
		if (pspmNumber >-1){
			result += "-m " + pspmNumber.toString() + " ";
		}
		if (mtMast > 0) {
			result+= "-mt "+String.format("%f",mtMast).replaceAll("0*$", "");
		}
		result+=" -hit_list -nostatus";
		return result;
	}
	
	protected static List<MastHit> parseMastOutput (String mastOutputFile){
		List<MastHit> result = new ArrayList<MastHit>();
		try {
			String line = null;
			BufferedReader br = new BufferedReader(new FileReader(mastOutputFile));
			while ((line = br.readLine()) != null) {
				if (line.matches("^# All non-overlapping hits in all sequences from.*")){
					//do nothing
				} else if (line.contains("# sequence_name motif hit_start hit_end score hit_p-value")){
				} else if (line.matches("^# mast .*")){
				} else{
					result.add(generateHitMast(line));
				}
			}
			br.close();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}  
		return result;
	}	
	
	protected static MastHit generateHitMast (String line){
		MastHit result = new MastHit();
		line = line.replaceAll("\\s+", " ");
		String[] hitData = line.split(" ");
		result.setSequenceId(hitData[0]);
		result.setStrand(hitData[1].substring(0, 1));
		result.setPspmId(hitData[1].substring(1));
		result.setHitStart(Integer.parseInt(hitData[2]));
		result.setHitEnd(Integer.parseInt(hitData[3]));
		result.setScore(Double.parseDouble(hitData[4]));
		result.setHitPvalue(Double.parseDouble(hitData[5]));
		return result;
	}
	
	public static String getKbaseId(String entityType) throws Exception {
		String returnVal = null;
		IdserverapiClient idClient = new IdserverapiClient(ID_SERVICE_URL);
		
		if (entityType.equals("MemeRunResult")) {
			returnVal = "kb|memerunresult." + idClient.allocateIdRange("memerunresult", 1).toString();
		} else if (entityType.equals("MemeMotif")) {
			returnVal = "kb|mememotif." + idClient.allocateIdRange("mememotif", 1).toString();
		} else if (entityType.equals("MemeSite")) {
			returnVal = "kb|memesite." + idClient.allocateIdRange("memesite", 1).toString();
		} else if (entityType.equals("MemePSPMCollection")) {
			returnVal = "kb|memepspmcollection." + idClient.allocateIdRange("memepspmcollection", 1).toString();
		} else if (entityType.equals("MemePSPM")) {
			returnVal = "kb|memepspm." + idClient.allocateIdRange("memepspm", 1).toString();
		} else if (entityType.equals("TomtomRunResult")) {
			returnVal = "kb|tomtomrunresult." + idClient.allocateIdRange("tomtomrunresult", 1).toString();
		} else if (entityType.equals("MastRunResult")) {
			returnVal = "kb|mastrunresult." + idClient.allocateIdRange("mastrunresult", 1).toString();
		} else {
		}
		return returnVal;
	}

}
