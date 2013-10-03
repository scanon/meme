package us.kbase.kbasememe;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import us.kbase.UObject;
import us.kbase.util.WSUtil;
import us.kbase.util.IdService;
import us.kbase.workspaceservice.GetObjectOutput;
import us.kbase.workspaceservice.GetObjectParams;

public class KbasememeServerImpl {
	private static Integer jobId = 0;
	private static final String WORK_DIRECTORY = "."; 
	private static Pattern spacePattern = Pattern.compile("[\\n\\t ]");
	
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

	public static MotifCollectionMeme searchMotifsFromSequencesWithMeme(
			SequenceSet sequenceSet, String modMeme, Integer nmotifsMeme,
			Integer minwMeme, Integer maxwMeme, Integer nsitesMeme,
			Integer minsitesMeme, Integer maxsitesMeme, Integer palMeme,
			Integer revcompMeme) throws Exception {
		MotifCollectionMeme returnVal = null;
		// Generate unique jobId for the MEME run
		String currentJobId = getJobId();
		String inputFileName = WORK_DIRECTORY+"/"+currentJobId	+ ".fasta";
		String outputFileName = WORK_DIRECTORY+"/"+currentJobId	+ ".out";
		// Generate MEME command line
		String memeCommand = generateMemeCommandLine(inputFileName, modMeme,
				nmotifsMeme, minwMeme, maxwMeme, nsitesMeme, minsitesMeme,
				maxsitesMeme, palMeme, revcompMeme);
		try {
			// Generate MEME input file in FASTA format
			generateFastaFile(inputFileName, sequenceSet);
			// Run MEME and get a list of output strings
			executeCommand(memeCommand, outputFileName);
			// Parse MEME output
			returnVal = parseMemeOutput(outputFileName);
			returnVal.setKbaseMotifCollectionMemeId(IdService.getKbaseId(MotifCollectionMeme.class.getSimpleName()));
		}
		finally {
			// Clean up
			Runtime.getRuntime().exec("rm " + inputFileName);
			Runtime.getRuntime().exec("rm " + outputFileName);
		}

		return returnVal;
	}
	
	public static MotifCollectionMeme searchMotifsFromWorkspaceWithMeme(
			String kbaseSequenceSetId, String modMeme, Integer nmotifsMeme,
			Integer minwMeme, Integer maxwMeme, Integer nsitesMeme,
			Integer minsitesMeme, Integer maxsitesMeme, Integer palMeme,
			Integer revcompMeme) throws Exception {
		MotifCollectionMeme returnVal = null;

		GetObjectParams params = new GetObjectParams().withType("SequenceSet").withId(kbaseSequenceSetId).withWorkspace(WSUtil.workspaceName).withAuth(WSUtil.authToken().toString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(params);
		SequenceSet input = UObject.transform(output.getData(), SequenceSet.class);
		returnVal = searchMotifsFromSequencesWithMeme(
				input, modMeme, nmotifsMeme, minwMeme, maxwMeme,
				nsitesMeme, minsitesMeme, maxsitesMeme, palMeme, revcompMeme);
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
			String modMeme, Integer nmotifsMeme, Integer minwMeme,
			Integer maxwMeme, Integer nsitesMeme, Integer minsitesMeme,
			Integer maxsitesMeme, Integer palMeme, Integer revcompMeme) throws UnsupportedEncodingException {
		if ((!modMeme.equals("oops")) && (!modMeme.equals("zoops")) && (!modMeme.equals("anr"))) {
			System.out.println("Unknown type of distribution: " + modMeme
					+ ". oops will be used instead.");
			modMeme = "oops";
		}
		String memeCommand = "meme " + jobId + " -mod " + modMeme;
		if (nmotifsMeme > 0)
			memeCommand += " -nmotifs " + nmotifsMeme;
		if (minwMeme > 0)
			memeCommand += " -minw " + minwMeme;
		if (maxwMeme > 0)
			memeCommand += " -maxw " + maxwMeme;
		if (nsitesMeme > 0 && modMeme != "oops")
			memeCommand += " -nsites " + nsitesMeme;
		if (minsitesMeme > 0 && modMeme != "oops")
			memeCommand += " -minsites " + minsitesMeme;
		if (maxsitesMeme > 0 && modMeme != "oops")
			memeCommand += " -maxsites " + maxsitesMeme;
		if (palMeme == 1)
			memeCommand += " -pal";
		if (revcompMeme == 1)
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

	protected static MotifCollectionMeme parseMemeOutput(String memeOutputFile) {
		MotifCollectionMeme motifCollection = new MotifCollectionMeme();
		List<MotifMeme> motifs = new ArrayList<MotifMeme>();
		List<String> sites = new ArrayList<String>();
		motifCollection
				.setKbaseMotifCollectionMemeId(IdService.getKbaseId(MotifCollectionMeme.class.getSimpleName()));
		Date date = new Date();
		motifCollection.setTimestamp(String.valueOf(date.getTime()));
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
						motifCollection.setVersionMeme(line);
					} else if (line.contains("TRAINING SET")){
						trainingSetSection = true;
					} else {}
				} else {
					if (!commandLineSection) {
						if (line.contains("DATAFILE=")) {
							motifCollection.setInputDatafile(line.substring(10));
						} else if (line.contains("ALPHABET=")) {
							motifCollection.setAlphabetMeme(line.substring(10));
						} else if (line.contains("********************************************************************************")|| line.matches("")) {
							//skip line
						} else if (line.contains("COMMAND LINE SUMMARY")) {
							commandLineSection = true;
							motifCollection.setTrainingSetMeme(trainingSet);
						} else {
							trainingSet.add(line);
						}
					} else {
						if (!motifSection) {
							if (line.contains("command: meme")) {
								motifCollection.setCommandLineMeme(line
										.substring(9));
							} else if (line.contains("model:  mod=")) {
								processModLine(motifCollection, line);
							} else if (line.contains("object function=")) {
								processOFLine(motifCollection, line);
							} else if (line.contains("width:  minw=")) {
								processMinwLine(motifCollection, line);
							} else if (line.contains("width:  wg=")) {
								processWgLine(motifCollection, line);
							} else if (line.contains("nsites: minsites=")) {
								processNsitesLine(motifCollection, line);
							} else if (line.contains("theta:  prob=")) {
								processThetaLine(motifCollection, line);
							} else if (line.contains("global: substring=")) {
								processGlobalsLine(motifCollection, line);
							} else if (line.contains("em:     prior=")) {
								processEmLine(motifCollection, line);
							} else if (line.contains("        distance=")) {
								processDistanceLine(motifCollection, line);
							} else if (line.contains("data:   n=")) {
								processDataNLine(motifCollection, line);
							} else if (line.contains("strands: ")) {
								processStrandsLine(motifCollection, line);
							} else if (line.contains("sample: seed=")) {
								processSampleLine(motifCollection, line);
							} else if (line
									.contains("Letter frequencies in dataset:")) {
								letterFreqLine = true;
							} else if (letterFreqLine) {
								letterFreqLine = false;
								motifCollection.setLetterFreqMeme(line);
							} else if (line
									.contains("Background letter frequencies (from dataset with add-one prior applied):")) {
								backFreqLine = true;
							} else if (backFreqLine) {
								backFreqLine = false;
								motifCollection.setBgFreqMeme(line);
							} else if (line
									.contains("********************************************************************************")) {
								if (asterisksLineCounter == 0) {
									asterisksLineCounter++;
								} else {
									motifCollection
											.setCollectionMemeOutput(cumulativeOutput);
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
								//	System.out.println(motifDataLine);
								//	System.out.println(cumulativeOutput);
								//	System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
									generateMotifMeme(motifDataLine,
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
							//	System.out.println("Motif found: "+motifDataLine);
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
		motifCollection.setMotifs(motifs);
		return motifCollection;
	}

	protected static void generateMotifMeme(String motifDataLine,
			String cumulativeOutput, List<String> sites, List<MotifMeme> motifs) {
		MotifMeme motif = new MotifMeme();
		motif.setKbaseMotifMemeId(IdService.getKbaseId(MotifMeme.class.getSimpleName()));
		motif.setMotifMemeOutput(cumulativeOutput);
		processMotifDataLine(motif, motifDataLine);
		List<SiteMeme> motifSites = new ArrayList<SiteMeme>();
		for (String siteLine : sites) {
			SiteMeme site = generateSiteMeme(siteLine);
			motifSites.add(site);
		}
		motif.setSites(motifSites);
		motifs.add(motif);
	}

	protected static void processMotifDataLine(MotifMeme motif,
			String motifDataLine) {
		motifDataLine = spacePattern.matcher(motifDataLine).replaceAll("");
		motif.setMotifName(Integer.parseInt(motifDataLine.substring(5,
				motifDataLine.indexOf("width="))));
		motif.setMotifWidthMeme(Integer.parseInt(motifDataLine.substring(
				motifDataLine.indexOf("width=") + 6,
				motifDataLine.indexOf("sites="))));
		motif.setMotifSitesMeme(Integer.parseInt(motifDataLine.substring(
				motifDataLine.indexOf("sites=") + 6,
				motifDataLine.indexOf("llr="))));
		motif.setMotifLlrMeme(Double.parseDouble(motifDataLine.substring(
				motifDataLine.indexOf("llr=") + 4,
				motifDataLine.indexOf("E-value="))));
		motif.setMotifEvalueMeme(Double.parseDouble(motifDataLine
				.substring(motifDataLine.indexOf("E-value=") + 8)));
	}

	protected static SiteMeme generateSiteMeme(String siteLine) {
		SiteMeme site = new SiteMeme();
		site.setKbaseMotifMemeId(IdService.getKbaseId(SiteMeme.class.getSimpleName()));
		siteLine = siteLine.replaceAll("\\s+", " ");
		String[] siteData = siteLine.split(" ");
		site.setSiteSequenceName(siteData[0]);
		site.setSiteStart(Integer.parseInt(siteData[1]));
		site.setSitePvalue(Double.parseDouble(siteData[2]));
		site.setSiteLeftFlank(siteData[3]);
		site.setSiteSequence(siteData[4]);
		site.setSiteRightFlank(siteData[5]);
		return site;
	}

	protected static void processSampleLine(
			MotifCollectionMeme motifCollection, String line) {
		line = spacePattern.matcher(line).replaceAll("");
		motifCollection.setSeedMeme(Integer.parseInt(line.substring(12,
				line.indexOf("seqfrac="))));
		motifCollection.setSeqfracMeme(Integer.parseInt(line.substring(line
				.indexOf("seqfrac=") + 8)));
	}

	protected static void processStrandsLine(
			MotifCollectionMeme motifCollection, String line) {
		line = spacePattern.matcher(line).replaceAll("");
		motifCollection.setStrandsMeme(line.substring(8));
	}

	protected static void processDataNLine(MotifCollectionMeme motifCollection,
			String line) {
		line = spacePattern.matcher(line).replaceAll("");
		motifCollection.setNMeme(Integer.parseInt(line.substring(7,
				line.indexOf("N="))));
		motifCollection.setNCapMeme(Integer.parseInt(line.substring(line
				.indexOf("N=") + 2)));
	}

	protected static void processDistanceLine(
			MotifCollectionMeme motifCollection, String line) {
		line = spacePattern.matcher(line).replaceAll("");
		motifCollection.setDistanceMeme(Double.parseDouble(line.substring(line
				.indexOf("distance=") + 9)));
	}

	protected static void processEmLine(MotifCollectionMeme motifCollection,
			String line) {
		line = spacePattern.matcher(line).replaceAll("");
		motifCollection.setPriorMeme(line.substring(9, line.indexOf("b=")));
		motifCollection.setBMeme(Double.parseDouble(line.substring(
				line.indexOf("b=") + 2, line.indexOf("maxiter="))));
		motifCollection.setMaxiterMeme(Integer.parseInt(line.substring(line
				.indexOf("maxiter=") + 8)));
	}

	protected static void processGlobalsLine(
			MotifCollectionMeme motifCollection, String line) {
		line = spacePattern.matcher(line).replaceAll("");
		motifCollection.setSubstringMeme(line.substring(17,
				line.indexOf("branching=")));
		motifCollection.setBranchingMeme(line.substring(
				line.indexOf("branching=") + 10, line.indexOf("wbranch=")));
		motifCollection
				.setWbranchMeme(line.substring(line.indexOf("wbranch=") + 8));
	}

	protected static void processThetaLine(MotifCollectionMeme motifCollection,
			String line) {
		line = spacePattern.matcher(line).replaceAll("");
		motifCollection.setProbMeme(Integer.parseInt(line.substring(11,
				line.indexOf("spmap="))));
		motifCollection.setSpmapMeme(line.substring(line.indexOf("spmap=") + 6,
				line.indexOf("spfuzz")));
		motifCollection
				.setSpfuzzMeme(line.substring(line.indexOf("spfuzz=") + 7));
	}

	protected static void processNsitesLine(
			MotifCollectionMeme motifCollection, String line) {
		line = spacePattern.matcher(line).replaceAll("");
		motifCollection.setMinsitesMeme(Integer.parseInt(line.substring(16,
				line.indexOf("maxsites="))));
		motifCollection.setMaxsitesMeme(Integer.parseInt(line.substring(
				line.indexOf("maxsites=") + 9, line.indexOf("wnsites="))));
		motifCollection.setWnsitesMeme(Double.parseDouble(line.substring(line
				.indexOf("wnsites=") + 8)));
	}

	protected static void processWgLine(MotifCollectionMeme motifCollection,
			String line) {
		line = spacePattern.matcher(line).replaceAll("");
		motifCollection.setWgMeme(Integer.parseInt(line.substring(9,
				line.indexOf("ws="))));
		motifCollection.setWsMeme(Integer.parseInt(line.substring(
				line.indexOf("ws=") + 3, line.indexOf("endgaps="))));
		motifCollection
				.setEndgapsMeme(line.substring(line.indexOf("endgaps=") + 8));
	}

	protected static void processMinwLine(MotifCollectionMeme motifCollection,
			String line) {
		line = spacePattern.matcher(line).replaceAll("");
		motifCollection.setMinwMeme(Integer.parseInt(line.substring(11,
				line.indexOf("maxw="))));
		motifCollection.setMaxwMeme(Integer.parseInt(line.substring(
				line.indexOf("maxw=") + 5, line.indexOf("minic="))));
		motifCollection.setMinicMeme(Double.parseDouble(line.substring(line
				.indexOf("minic=") + 6)));
	}

	protected static void processOFLine(MotifCollectionMeme motifCollection,
			String line) {
		motifCollection.setObjectFunctionMeme(line.substring(18));
	}

	protected static void processModLine(MotifCollectionMeme motifCollection,
			String line) {
		line = spacePattern.matcher(line).replaceAll("");
		motifCollection.setModMeme(line.substring(10,
				line.indexOf("nmotifs=")));
		motifCollection.setNmotifsMeme(Integer.parseInt(line.substring(
				line.indexOf("nmotifs=") + 8, line.indexOf("evt="))));
		motifCollection.setEvtMeme(line.substring(line.indexOf("evt=") + 4));
	}

	public static List<HitTomtom> compareMotifsWithTomtom(MotifCollectionMeme queryMotifCollection,
			MotifCollectionMeme targetMotifCollection, Double threshTomtom,
			Integer evalueTomtom, String distTomtom, Integer internalTomtom,
			Integer minOverlapTomtom) throws UnsupportedEncodingException{
		List<HitTomtom> result = new ArrayList<HitTomtom>();
		distTomtom = new String(distTomtom.getBytes("ISO-8859-1"), "UTF-8");
		String jobId = getJobId();
		String firstInputFile = WORK_DIRECTORY+"/"+jobId+"_query.meme";
		String secondInputFile = WORK_DIRECTORY+"/"+jobId+"_target.meme";
		String outputFileName = WORK_DIRECTORY+"/"+jobId+"_tomtom.txt";
		//Generate command line
		String commandLineTomtom = generateTomtomCommandLine (firstInputFile, secondInputFile, threshTomtom,
				evalueTomtom, distTomtom, internalTomtom, minOverlapTomtom);
		try {
			//Generate first input file
			generateMemeFile(queryMotifCollection, firstInputFile);
			//Generate second input file
			generateMemeFile(targetMotifCollection, secondInputFile);
			//Run TOMTOM
			executeCommand(commandLineTomtom, outputFileName);
			//Parse output file
			result = parseTomtomOutput(outputFileName);
		}
		finally {
			//Clean up
			try {
				Runtime.getRuntime().exec("rm " + firstInputFile);
				Runtime.getRuntime().exec("rm " + secondInputFile);
				Runtime.getRuntime().exec("rm " + outputFileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	protected static void generateMemeFile (MotifCollectionMeme motifCollection, String fileName){
		String tomtomInput = motifCollection.getCollectionMemeOutput();
		for(MotifMeme motif: motifCollection.getMotifs()){
			tomtomInput += motif.getMotifMemeOutput();
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
			Integer evalueTomtom, String distTomtom, Integer internalTomtom, Integer minOverlapTomtom){
		String commandLine = "tomtom";
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
	
	protected static List<HitTomtom> parseTomtomOutput(String tomtomOutputFile) {
		List<HitTomtom> result = new ArrayList<HitTomtom>();
		try {
			String line = null;
			BufferedReader br = new BufferedReader(new FileReader(tomtomOutputFile));
			while ((line = br.readLine()) != null) {
				if (line.contains("#Query ID	Target ID	Optimal offset	p-value	E-value	q-value	Overlap	Query consensus	Target consensus	Orientation")){
					//do nothing
				} else{
					result.add(generateHitTomtom(line));
				}
			}
			br.close();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}  
		return result;
	}
	
	protected static HitTomtom generateHitTomtom (String line){
		HitTomtom result = new HitTomtom();
		String[] hitData = line.split("\t");
		result.setQueryMotifName(hitData[0]);
		result.setTargetMotifName(hitData[1]);
		result.setOptimalOffset(Integer.parseInt(hitData[2]));
		result.setPValue(Double.parseDouble(hitData[3]));
		result.setEValue(Double.parseDouble(hitData[4]));
		result.setQValue(Double.parseDouble(hitData[5]));
		result.setOverlap(Integer.parseInt(hitData[6]));
		result.setQueryConsensus(hitData[7]);
		result.setTargetConsensus(hitData[8]);
		result.setStrand(hitData[9]);
		return result;
	}
	
	public static List<HitMast> findSitesByMotifCollectionWithMast(MotifCollectionMeme queryMotifCollection,
			SequenceSet targetSequenceDatabase, Double mtMast){
		List<HitMast> result = new ArrayList<HitMast>();
		String jobId = getJobId();
		String motifFileName = WORK_DIRECTORY+"/"+jobId+"_query.meme";
		String sequenceFileName = WORK_DIRECTORY+"/"+jobId+"_target.fasta";
		String outputFileName = WORK_DIRECTORY+"/"+jobId+"_mast.txt";
		//Generate command line
		String commandLine = generateMastCommandLine (motifFileName, sequenceFileName, mtMast);
		try {
			//Generate motif input file
			generateMemeFile(queryMotifCollection, motifFileName);
			//Generate sequences input file
			generateFastaFile(sequenceFileName, targetSequenceDatabase);
			//Run Mast
			executeCommand(commandLine, outputFileName);
			//Parse output
			result = parseMastOutput(outputFileName);
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
		return result;
	}
	
	public static List<HitMast> findSitesByMotifCollectionWsWithMast(MotifCollectionMeme queryMotifCollection,
			String kbaseSequenceSetId, Double mtMast) throws MalformedURLException, Exception{
		List<HitMast> result = new ArrayList<HitMast>();
		GetObjectParams params = new GetObjectParams().withType("SequenceSet").withId(kbaseSequenceSetId).withWorkspace(WSUtil.workspaceName).withAuth(WSUtil.authToken().toString());   
		GetObjectOutput output = WSUtil.wsClient().getObject(params);
		SequenceSet input = UObject.transform(output.getData(), SequenceSet.class);
		result = findSitesByMotifCollectionWithMast(queryMotifCollection, input, mtMast);
		return result;
	}
	
	protected static String generateMastCommandLine (String motifFileName, String sequenceFileName, Double mtMast) {
		String result = "mast " + motifFileName + " " + sequenceFileName + " ";
		if (mtMast > 0) {
			result+= "-mt "+String.format("%f",mtMast).replaceAll("0*$", "");
		}
		result+=" -hit_list -nostatus";
		return result;
	}
	
	protected static List<HitMast> parseMastOutput (String mastOutputFile){
		List<HitMast> result = new ArrayList<HitMast>();
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
	
	protected static HitMast generateHitMast (String line){
		HitMast result = new HitMast();
		line = line.replaceAll("\\s+", " ");
		String[] hitData = line.split(" ");
		result.setSequenceName(hitData[0]);
		result.setStrand(hitData[1].substring(0, 1));
		result.setMotifName(hitData[1].substring(1));
		result.setHitStart(Integer.parseInt(hitData[2]));
		result.setHitEnd(Integer.parseInt(hitData[3]));
		result.setScore(Double.parseDouble(hitData[4]));
		result.setHitPvalue(Double.parseDouble(hitData[5]));
		return result;
	}
}
