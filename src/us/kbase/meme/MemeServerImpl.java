package us.kbase.meme;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import us.kbase.auth.AuthException;
import us.kbase.auth.AuthToken;
import us.kbase.auth.TokenFormatException;
import us.kbase.common.service.JsonClientException;
import us.kbase.common.service.UObject;
import us.kbase.common.service.UnauthorizedException;
import us.kbase.sequences.Sequence;
import us.kbase.sequences.SequenceSet;
import us.kbase.idserverapi.IDServerAPIClient;
import us.kbase.userandjobstate.InitProgress;
import us.kbase.userandjobstate.Results;
import us.kbase.userandjobstate.UserAndJobStateClient;
import us.kbase.util.WsDeluxeUtil;

public class MemeServerImpl {
	private static Integer temporaryFileId = 0;
	private static final String WORK_DIRECTORY = MemeServerConfig.WORK_DIRECTORY;
	private static final String ID_SERVICE_URL = MemeServerConfig.ID_SERVICE_URL;
	private static final String JOB_SERVICE_URL = MemeServerConfig.JOB_SERVICE;
	private static final boolean DEPLOY_AWE = MemeServerConfig.DEPLOY_AWE;

	private static Pattern spacePattern = Pattern.compile("[\\n\\t ]");
	private static Date date = new Date();
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssZ");

	protected static void cleanUpOnStart() throws IOException {
		deleteFile(WORK_DIRECTORY, ".*.fasta");
		deleteFile(WORK_DIRECTORY, ".*.meme");
		deleteFile(WORK_DIRECTORY, ".*.out");
		deleteFile(WORK_DIRECTORY, ".*._tomtom.txt");
		deleteFile(WORK_DIRECTORY, ".*._mast.txt");
	}

	protected static void startJob(String jobId, String desc, Long tasks,
			String token) throws TokenFormatException, MalformedURLException,
			IOException, JsonClientException {

		String status = "MEME service job started. Preparing input...";
		InitProgress initProgress = new InitProgress();
		initProgress.setPtype("task");
		initProgress.setMax(3L);
		date.setTime(date.getTime() + 1000000L);

		UserAndJobStateClient jobClient = new UserAndJobStateClient(new URL(
				JOB_SERVICE_URL), new AuthToken(token));
		// jobClient.setAuthAllowedForHttp(true);
		jobClient.startJob(jobId, token, status, desc, initProgress,
				dateFormat.format(date));
		jobClient = null;
	}

	protected static void updateJobProgress(String jobId, String status,
			Long tasks, String token) throws TokenFormatException,
			MalformedURLException, IOException, JsonClientException {
		date.setTime(date.getTime() + 10000L);
		UserAndJobStateClient jobClient = new UserAndJobStateClient(new URL(
				JOB_SERVICE_URL), new AuthToken(token));
		// jobClient.setAuthAllowedForHttp(true);
		jobClient.updateJobProgress(jobId, token, status, tasks,
				dateFormat.format(date));
		jobClient = null;
	}

	protected static void finishJob(String jobId, String wsId, String objectId,
			String token) throws TokenFormatException, MalformedURLException, IOException, JsonClientException {
		String status = "Finished";
		String error = null;

		Results res = new Results();
		List<String> workspaceIds = new ArrayList<String>();
		workspaceIds.add(wsId + "/" + objectId);
		res.setWorkspaceids(workspaceIds);
		UserAndJobStateClient jobClient = new UserAndJobStateClient(new URL(
				JOB_SERVICE_URL), new AuthToken(token));
		// jobClient.setAuthAllowedForHttp(true);
		jobClient.completeJob(jobId, token, status, error, res);
		jobClient = null;
	}

	protected static String getTemporaryFileId() {
		temporaryFileId++;
		String retVal = temporaryFileId.toString();
		return retVal;
	}

	protected static void generateFastaFile(String jobId,
			SequenceSet sequenceSet) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(jobId));
			for (Sequence sequence : sequenceSet.getSequences()) {
				writer.write(">" + sequence.getSequenceId() + "\n"
						+ formatSequence(sequence.getSequence()) + "\n");
			}
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

	protected static String formatSequence(String sequence) {
		String result = "";
		if (sequence.length() > 80) {
			int i = 0;
			for (i = 0; i < sequence.length() - 80; i = i + 80) {
				result += sequence.substring(i, i + 80) + "\n";
			}
			result += sequence.substring(i);
		} else {
			result = sequence;
		}
		return result;
	}

	protected static String generateMemeCommandLine(String inputFileName,
			String mod, Long nmotifs, Long minw, Long maxw, Long nsites,
			Long minsites, Long maxsites, Long pal, Long revcomp)
			throws UnsupportedEncodingException {
		if ((!mod.equals("oops")) && (!mod.equals("zoops"))
				&& (!mod.equals("anr"))) {
			System.out.println("Unknown type of distribution: " + mod
					+ ". oops will be used instead.");
			mod = "oops";
		}
		String memeCommand = "meme " + inputFileName + " -mod " + mod;
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

	protected static void executeCommand(String commandLine,
			String outputFileName) {
		BufferedWriter writer = null;
		try {
			Process p = Runtime.getRuntime().exec(commandLine);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			writer = new BufferedWriter(new FileWriter(outputFileName));
			String line;
			while ((line = br.readLine()) != null) {
				writer.write(line + "\n");
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

	protected static MemeRunResult parseMemeOutput(String memeOutputFile)
			throws Exception {
		MemeRunResult memeRunResult = new MemeRunResult();
		List<MemeMotif> motifs = new ArrayList<MemeMotif>();
		List<String> sites = new ArrayList<String>();
		memeRunResult.setId(getKbaseId(MemeRunResult.class.getSimpleName()));
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
			BufferedReader br = new BufferedReader(new FileReader(
					memeOutputFile));
			while ((line = br.readLine()) != null) {
				cumulativeOutput += line + "\n";
				if (!trainingSetSection) {
					if (line.matches("^MEME version.*")) {
						memeRunResult.setMemeVersion(line);
					} else if (line.contains("TRAINING SET")) {
						trainingSetSection = true;
					} else {
					}
				} else {
					if (!commandLineSection) {
						if (line.contains("DATAFILE=")) {
							memeRunResult.setInputFileName(line.substring(10));
						} else if (line.contains("ALPHABET=")) {
							memeRunResult.setAlphabet(line.substring(10));
						} else if (line
								.contains("********************************************************************************")
								|| line.matches("")) {
							// skip line
						} else if (line.contains("COMMAND LINE SUMMARY")) {
							commandLineSection = true;
							memeRunResult.setTrainingSet(trainingSet);
						} else {
							trainingSet.add(line);
						}
					} else {
						if (!motifSection) {
							if (line.contains("command: meme")) {
								memeRunResult.setCommandLine(line.substring(9));
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
								if (line.matches("^-------------.*")) {
									// skip line
								} else {
									sites.add(line);
								}
							} else if (line.contains("SUMMARY OF MOTIFS")) {
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
			String cumulativeOutput, List<String> sites, List<MemeMotif> motifs)
			throws Exception {
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
		motif.setWidth(Long.parseLong(motifDataLine.substring(
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
		site.setStart(Long.parseLong(siteData[1]));
		site.setPvalue(Double.parseDouble(siteData[2]));
		site.setLeftFlank(siteData[3]);
		site.setSequence(siteData[4]);
		site.setRightFlank(siteData[5]);
		return site;
	}

	protected static void processSampleLine(MemeRunResult memeRunResult,
			String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult.setSeed(Long.parseLong(line.substring(12,
				line.indexOf("seqfrac="))));
		memeRunResult.setSeqfrac(Long.parseLong(line.substring(line
				.indexOf("seqfrac=") + 8)));
	}

	protected static void processStrandsLine(MemeRunResult memeRunResult,
			String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult.setStrands(line.substring(8));
	}

	protected static void processDataNLine(MemeRunResult memeRunResult,
			String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult
				.setN(Long.parseLong(line.substring(7, line.indexOf("N="))));
		memeRunResult
				.setNCap(Long.parseLong(line.substring(line.indexOf("N=") + 2)));
	}

	protected static void processDistanceLine(MemeRunResult memeRunResult,
			String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult.setDistance(Double.parseDouble(line.substring(line
				.indexOf("distance=") + 9)));
	}

	protected static void processEmLine(MemeRunResult memeRunResult, String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult.setPrior(line.substring(9, line.indexOf("b=")));
		memeRunResult.setB(Double.parseDouble(line.substring(
				line.indexOf("b=") + 2, line.indexOf("maxiter="))));
		memeRunResult.setMaxiter(Long.parseLong(line.substring(line
				.indexOf("maxiter=") + 8)));
	}

	protected static void processGlobalsLine(MemeRunResult memeRunResult,
			String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult.setSubstring(line.substring(17,
				line.indexOf("branching=")));
		memeRunResult.setBranching(line.substring(
				line.indexOf("branching=") + 10, line.indexOf("wbranch=")));
		memeRunResult.setWbranch(line.substring(line.indexOf("wbranch=") + 8));
	}

	protected static void processThetaLine(MemeRunResult memeRunResult,
			String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult.setProb(Long.parseLong(line.substring(11,
				line.indexOf("spmap="))));
		memeRunResult.setSpmap(line.substring(line.indexOf("spmap=") + 6,
				line.indexOf("spfuzz")));
		memeRunResult.setSpfuzz(line.substring(line.indexOf("spfuzz=") + 7));
	}

	protected static void processNsitesLine(MemeRunResult memeRunResult,
			String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult.setMinsites(Long.parseLong(line.substring(16,
				line.indexOf("maxsites="))));
		memeRunResult.setMaxsites(Long.parseLong(line.substring(
				line.indexOf("maxsites=") + 9, line.indexOf("wnsites="))));
		memeRunResult.setWnsites(Double.parseDouble(line.substring(line
				.indexOf("wnsites=") + 8)));
	}

	protected static void processWgLine(MemeRunResult memeRunResult, String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult.setWg(Long.parseLong(line.substring(9,
				line.indexOf("ws="))));
		memeRunResult.setWs(Long.parseLong(line.substring(
				line.indexOf("ws=") + 3, line.indexOf("endgaps="))));
		memeRunResult.setEndgaps(line.substring(line.indexOf("endgaps=") + 8));
	}

	protected static void processMinwLine(MemeRunResult memeRunResult,
			String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult.setMinw(Long.parseLong(line.substring(11,
				line.indexOf("maxw="))));
		memeRunResult.setMaxw(Long.parseLong(line.substring(
				line.indexOf("maxw=") + 5, line.indexOf("minic="))));
		memeRunResult.setMinic(Double.parseDouble(line.substring(line
				.indexOf("minic=") + 6)));
	}

	protected static void processOFLine(MemeRunResult memeRunResult, String line) {
		memeRunResult.setObjectFunction(line.substring(18));
	}

	protected static void processModLine(MemeRunResult memeRunResult,
			String line) {
		line = spacePattern.matcher(line).replaceAll("");
		memeRunResult.setMod(line.substring(10, line.indexOf("nmotifs=")));
		memeRunResult.setNmotifs(Long.parseLong(line.substring(
				line.indexOf("nmotifs=") + 8, line.indexOf("evt="))));
		memeRunResult.setEvt(line.substring(line.indexOf("evt=") + 4));
	}

	public static MemeRunResult findMotifsWithMeme(SequenceSet sequenceSet,
			MemeRunParameters params, String jobId, String token)
			throws Exception {

		MemeRunResult returnVal = null;
		// Generate unique jobId for the MEME run
		String inputFileName = null;
		String outputFileName = null;
		if (DEPLOY_AWE) {
			inputFileName = jobId + ".fasta";
			outputFileName = jobId + ".out";
		} else { 
			inputFileName = WORK_DIRECTORY + "/" + jobId + ".fasta";
			outputFileName = WORK_DIRECTORY + "/" + jobId + ".out";
		}
		
		// Generate MEME command line
		String memeCommand = generateMemeCommandLine(inputFileName,
				params.getMod(), params.getNmotifs(), params.getMinw(),
				params.getMaxw(), params.getNsites(), params.getMinsites(),
				params.getMaxsites(), params.getPal(), params.getRevcomp());
		try {
			// Generate MEME input file in FASTA format
			generateFastaFile(inputFileName, sequenceSet);
			// Run MEME and get a list of output strings
			String status = "Input prepared. Starting MEME program...";
			if (jobId != null)
				updateJobProgress(jobId, status, 1L, token);
			executeCommand(memeCommand, outputFileName);
			// Parse MEME output
			status = "MEME program finished. Processing output...";
			if (jobId != null)
				updateJobProgress(jobId, status, 1L, token);
			returnVal = parseMemeOutput(outputFileName);
			returnVal.setId(getKbaseId(MemeRunResult.class.getSimpleName()));
		} finally {
			// Clean up
			File fileDelete = new File(inputFileName);
			fileDelete.delete();
			fileDelete = new File(outputFileName);
			fileDelete.delete();
		}
		returnVal.setParams(params);
		return returnVal;
	}

	public static String findMotifsWithMemeFromWs(String wsName,
			MemeRunParameters params, String token) throws Exception {
		String returnVal = findMotifsWithMemeJobFromWs(wsName, params, null,
				token);
		return returnVal;
	}

	public static String findMotifsWithMemeJobFromWs(String wsName,
			MemeRunParameters params, String jobId, String token)
			throws Exception {

		// Start job
		String desc = "MEME service job " + jobId
				+ ". Method: findMotifsWithMemeJobFromWs. Input: "
				+ params.getSourceRef() + ". Workspace: " + wsName + ".";
		if (jobId != null)
			startJob(jobId, desc, 3L, token);

		SequenceSet input = WsDeluxeUtil
				.getObjectFromWsByRef(params.getSourceRef(), token).getData()
				.asClassInstance(SequenceSet.class);

		MemeRunResult memeRunResult = findMotifsWithMeme(input, params, jobId,
				token);
		String returnVal = memeRunResult.getId();

		// Save to workspace
		WsDeluxeUtil.saveObjectToWorkspace(
				UObject.transformObjectToObject(memeRunResult, UObject.class),
				"MEME.MemeRunResult", wsName, returnVal, token);

		// Finalize job
		if (jobId != null)
			finishJob(jobId, wsName, returnVal, token);
		return returnVal;
	}

	public static TomtomRunResult compareMotifsWithTomtom(MemePSPM query,
			MemePSPMCollection target, TomtomRunParameters params)
			throws Exception {

		MemePSPMCollection queryCol = makePSPMCollection(query);
		TomtomRunResult result = compareMotifsWithTomtomByCollection(queryCol,
				target, params, null, null);
		return result;
	}

	/*
	 * public static String compareMotifsWithTomtomFromWs(String wsId, String
	 * queryId, String targetId, TomtomRunParameters params, String token)
	 * throws MalformedURLException, Exception{ String returnVal =
	 * compareMotifsWithTomtomJobFromWs(wsId, queryId, targetId, params, null,
	 * token); return returnVal; }
	 */

	/*
	 * public static String compareMotifsWithTomtomJobFromWs(String wsId, String
	 * queryId, String targetId, TomtomRunParameters params, String jobId,
	 * String token) throws MalformedURLException, Exception{
	 * 
	 * //Start job String desc =
	 * "MEME service job. Method: compareMotifsWithTomtomJobFromWs. Input: " +
	 * queryId + ", " + targetId + ". Workspace: " + wsId + "."; if (jobId !=
	 * null) startJob (jobId, desc, 3L, token);
	 * 
	 * GetObjectParams queryParams = new
	 * GetObjectParams().withType("MemePSPM").withId
	 * (queryId).withWorkspace(wsId).withAuth(token); GetObjectOutput
	 * queryOutput = wsClient(token).getObject(queryParams); MemePSPM query =
	 * UObject.transformObjectToObject(queryOutput.getData(), MemePSPM.class);
	 * GetObjectParams targetParams = new
	 * GetObjectParams().withType("MemePSPMCollection"
	 * ).withId(targetId).withWorkspace(wsId).withAuth(token); GetObjectOutput
	 * targetOutput = wsClient(token).getObject(targetParams);
	 * MemePSPMCollection target =
	 * UObject.transformObjectToObject(targetOutput.getData(),
	 * MemePSPMCollection.class);
	 * 
	 * MemePSPMCollection queryCol = makePSPMCollection(query);
	 * 
	 * TomtomRunResult result = compareMotifsWithTomtomByCollection(queryCol,
	 * target, "", params, jobId, token);
	 * 
	 * //Write result to WS String returnVal = result.getId();
	 * saveObjectToWorkspace (UObject.transformObjectToObject(result,
	 * UObject.class), result.getClass().getSimpleName(), wsId, returnVal,
	 * token);
	 * 
	 * //Finish job if (jobId != null) finishJob (jobId, wsId, returnVal,
	 * token); return returnVal; }
	 */

	public static TomtomRunResult compareMotifsWithTomtomByCollection(
			MemePSPMCollection query, MemePSPMCollection target,
			TomtomRunParameters params, String jobId, String token)
			throws Exception {
		TomtomRunResult result = new TomtomRunResult();
		String tempFileId = null;
		if (jobId == null) {
			tempFileId = getTemporaryFileId();
		} else {
			tempFileId = jobId;
		}

		String firstInputFile = null;
		String secondInputFile = null;
		String outputFileName = null;
		
		if (DEPLOY_AWE) {
			firstInputFile = tempFileId + "_query.meme";
			secondInputFile = tempFileId + "_target.meme";
			outputFileName = tempFileId + "_tomtom.txt";
		} else {
			firstInputFile = WORK_DIRECTORY + "/" + tempFileId + "_query.meme";
			secondInputFile = WORK_DIRECTORY + "/" + tempFileId + "_target.meme";
			outputFileName = WORK_DIRECTORY + "/" + tempFileId + "_tomtom.txt";
		}
		// Generate command line
		String commandLineTomtom = generateTomtomCommandLine(firstInputFile,
				secondInputFile, params);
		try {
			// Generate first input file
			generateSimpleMemeFile(query, firstInputFile);
			// Generate second input file
			generateSimpleMemeFile(target, secondInputFile);
			// Run TOMTOM
			String status = "Input prepared. Starting TOMTOM program...";
			if (jobId != null)
				updateJobProgress(jobId, status, 1L, token);
			executeCommand(commandLineTomtom, outputFileName);
			// Parse output file
			status = "TOMTOM program finished. Processing output...";
			if (jobId != null)
				updateJobProgress(jobId, status, 1L, token);
			result = parseTomtomOutput(outputFileName, params);
		} finally {
			File fileDelete = new File(firstInputFile);
			fileDelete.delete();
			fileDelete = new File(secondInputFile);
			fileDelete.delete();
			fileDelete = new File(outputFileName);
			fileDelete.delete();
		}
		return result;
	}

	public static String compareMotifsWithTomtomByCollectionFromWs(
			String wsName, TomtomRunParameters params, String token)
			throws MalformedURLException, Exception {

		String returnVal = compareMotifsWithTomtomJobByCollectionFromWs(wsName,
				params, null, token);
		return returnVal;
	}

	public static String compareMotifsWithTomtomJobByCollectionFromWs(
			String wsName, TomtomRunParameters params, String jobId,
			String token) throws MalformedURLException, Exception {

		String desc = "MEME service job "
				+ jobId
				+ ". Method: compareMotifsWithTomtomJobByCollectionFromWs. Input: "
				+ params.getQueryRef() + ", " + params.getTargetRef()
				+ ". Workspace: " + wsName + ".";
		if (jobId != null)
			startJob(jobId, desc, 3L, token);

		MemePSPMCollection query = WsDeluxeUtil
				.getObjectFromWsByRef(params.getQueryRef(), token).getData()
				.asClassInstance(MemePSPMCollection.class);

		MemePSPMCollection target = WsDeluxeUtil
				.getObjectFromWsByRef(params.getTargetRef(), token).getData()
				.asClassInstance(MemePSPMCollection.class);

		TomtomRunResult result = compareMotifsWithTomtomByCollection(query,
				target, params, jobId, token);

		// Write result to WS
		String returnVal = result.getId();
		WsDeluxeUtil.saveObjectToWorkspace(
				UObject.transformObjectToObject(result, UObject.class),
				"MEME.TomtomRunResult", wsName, returnVal, token);

		// Finish job
		if (jobId != null)
			finishJob(jobId, wsName, returnVal, token);
		return returnVal;
	}

	public static MemePSPMCollection getPspmCollectionFromMemeResult(
			MemeRunResult memeRunResult) throws Exception {
		MemePSPMCollection returnVal = new MemePSPMCollection();
		returnVal.setId(getKbaseId("MemePSPMCollection"));
		returnVal.setTimestamp(String.valueOf(date.getTime()));
		returnVal.setSourceRef(memeRunResult.getId());
		returnVal.setDescription("Based on " + memeRunResult.getId()
				+ " MEME run");
		returnVal.setAlphabet(memeRunResult.getAlphabet());
		List<MemePSPM> pspms = new ArrayList<MemePSPM>();
		for (MemeMotif motif : memeRunResult.getMotifs()) {
			MemePSPM pspm = generateMemePSPM(motif, memeRunResult.getAlphabet());
			pspms.add(pspm);
		}
		returnVal.setPspms(pspms);
		return returnVal;
	}

	public static String getPspmCollectionFromMemeResultFromWs(String wsName,
			String memeRunResultRef, String token) throws Exception {

		String returnVal = getPspmCollectionFromMemeJobResultFromWs(wsName,
				memeRunResultRef, null, token);
		return returnVal;
	}

	public static String getPspmCollectionFromMemeJobResultFromWs(
			String wsName, String memeRunResultRef, String jobId, String token)
			throws Exception {

		// Start job
		String desc = "MEME service job " + jobId
				+ ". Method: getPspmCollectionFromMemeJobResultFromWs. Input: "
				+ memeRunResultRef + ". Workspace: " + wsName + ".";
		if (jobId != null)
			startJob(jobId, desc, 2L, token);

		MemeRunResult memeRunResult = WsDeluxeUtil
				.getObjectFromWsByRef(memeRunResultRef, token).getData()
				.asClassInstance(MemeRunResult.class);

		MemePSPMCollection collection = getPspmCollectionFromMemeResult(memeRunResult);
		collection.setSourceRef(memeRunResultRef);
		String returnVal = collection.getId();
		String status = "Conversion completed. Saving output...";
		if (jobId != null)
			updateJobProgress(jobId, status, 1L, token);

		WsDeluxeUtil.saveObjectToWorkspace(
				UObject.transformObjectToObject(collection, UObject.class),
				"MEME.MemePSPMCollection", wsName, returnVal, token);
		// Finish job
		if (jobId != null)
			finishJob(jobId, wsName, returnVal, token);
		return returnVal;
	}

	protected static MemePSPMCollection makePSPMCollection(MemePSPM pspm) {
		MemePSPMCollection returnVal = new MemePSPMCollection();
		returnVal.setId("undefined");
		returnVal.setSourceRef(pspm.getId());
		returnVal.setTimestamp("undefined");
		returnVal.setDescription("temporary collection for TOMTOM run");
		returnVal.setAlphabet(pspm.getAlphabet());
		List<MemePSPM> pspms = new ArrayList<MemePSPM>();
		pspms.add(pspm);
		returnVal.setPspms(pspms);

		return returnVal;
	}

	protected static MemePSPM generateMemePSPM(MemeMotif motif, String alphabet)
			throws Exception {
		MemePSPM returnVal = new MemePSPM();
		returnVal.setId(getKbaseId("MemePSPM"));
		returnVal.setSourceId(motif.getId());
		returnVal.setDescription(motif.getDescription());
		returnVal.setAlphabet(alphabet);
		returnVal.setWidth((long) motif.getSites().get(0).getSequence()
				.length());
		returnVal.setNsites((long) motif.getSites().size());
		returnVal.setEvalue(motif.getEvalue());
		returnVal.setMatrix(generatePspMatrix(motif.getRawOutput()));
		return returnVal;
	}

	protected static List<List<Double>> generatePspMatrix(String output) {
		List<List<Double>> returnVal = new ArrayList<List<Double>>();
		String[] memeOut = output.split("\n");
		Boolean lpmSection = false;

		for (String line : memeOut) {
			if (lpmSection == true) {
				if (line.contains("--------------------------------------------------------------------------------")) {
					lpmSection = false;
				} else {
					String[] matrixTextRow = line.split(" ");
					List<Double> matrixRow = new ArrayList<Double>();
					for (String matrixTextValue : matrixTextRow) {
						if (!matrixTextValue.equals(""))
							matrixRow.add(Double.parseDouble(matrixTextValue));
					}
					returnVal.add(matrixRow);
				}
			} else {
				if (line.matches("^letter-probability matrix: alength=.*")) {
					lpmSection = true;
				}
			}
		}
		return returnVal;
	}

	protected static void generateSimpleMemeFile(MemePSPM pspm, String fileName) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileName));
			writer.write("MEME version 4\n\n"); // Version
			writer.write("ALPHABET= " + pspm.getAlphabet() + "\n\n"); // Alphabet
			writer.write("strands + -\n\n"); // Strands
			writer.write("Background letter frequencies\nA 0.25 C 0.25 G 0.25 T 0.25\n\n"); // Background
																							// frequencies
																							// are
																							// uniform
			writer.write("MOTIF " + pspm.getId() + "\n"); // Motif ID
			writer.write("letter-probability matrix: alength= "
					+ pspm.getAlphabet().length() + " w= " + pspm.getWidth()
					+ " nsites= " + pspm.getNsites() + " E= "
					+ pspm.getEvalue() + "\n");
			// Print matrix
			DecimalFormat df = new DecimalFormat("0.000000");
			for (List<Double> row : pspm.getMatrix()) {
				for (Double value : row) {
					writer.write(" " + df.format(value));
				}
				writer.write("\n");
			}
			writer.write("\n");
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

	protected static void generateSimpleMemeFile(MemePSPMCollection collection,
			String fileName) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileName));
			writer.write("MEME version 4\n\n"); // Version
			writer.write("ALPHABET= " + collection.getAlphabet() + "\n\n"); // Alphabet
			writer.write("strands + -\n\n"); // Strands
			writer.write("Background letter frequencies\nA 0.25 C 0.25 G 0.25 T 0.25\n\n"); // Background
																							// frequencies
																							// are
																							// uniform
			for (MemePSPM pspm : collection.getPspms()) {
				writer.write("MOTIF " + pspm.getId() + "\n"); // Motif ID
				writer.write("letter-probability matrix: alength= "
						+ pspm.getAlphabet().length() + " w= "
						+ pspm.getWidth() + " nsites= " + pspm.getNsites()
						+ " E= " + pspm.getEvalue() + "\n");
				// Print matrix
				DecimalFormat df = new DecimalFormat("0.000000");
				for (List<Double> row : pspm.getMatrix()) {
					for (Double value : row) {
						writer.write(" " + df.format(value));
					}
					writer.write("\n");
				}
				writer.write("\n");
			}
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

	protected static String generateTomtomCommandLine(String firstInputFile,
			String secondInputFile, TomtomRunParameters params) {
		String commandLine = "tomtom";
		if (params.getPspmId() != null) {
			commandLine += " -m " + params.getPspmId();
		}
		if (params.getThresh() > 0) {
			commandLine += " -thresh " + params.getThresh().toString();
		}
		if (params.getEvalue() == 1) {
			commandLine += " -evalue";
		} else if (params.getEvalue() != 0) {
			System.out.println("Cannot parse value of e-value parameter: "
					+ params.getEvalue().toString());
		}
		if (params.getDist().equals("allr") || params.getDist().equals("ed")
				|| params.getDist().equals("kullback")
				|| params.getDist().equals("pearson")
				|| params.getDist().equals("sandelin")) {
			commandLine += " -dist " + params.getDist();
		} else {
			System.out.println("Cannot parse value of dist parameter: "
					+ params.getDist());
		}
		if (params.getInternal() == 1) {
			commandLine += " -internal";
		} else if (params.getInternal() != 0) {
			System.out
					.println("Cannot parse value of internalTomtom parameter: "
							+ params.getInternal().toString());
		}
		if (params.getMinOverlap() >= 1) {
			commandLine += " -min-overlap " + params.getMinOverlap().toString();
		} else if (params.getMinOverlap() != 0) {
			System.out
					.println("Cannot parse value of minOverlapTomtom parameter: "
							+ params.getMinOverlap().toString());
		}

		commandLine += " -text";
		commandLine += " " + firstInputFile + " " + secondInputFile;
		return commandLine;
	}

	protected static TomtomRunResult parseTomtomOutput(String tomtomOutputFile,
			TomtomRunParameters params) throws Exception {
		TomtomRunResult returnVal = new TomtomRunResult();
		returnVal.setId(getKbaseId(TomtomRunResult.class.getSimpleName()));
		returnVal.setTimestamp(String.valueOf(date.getTime()));
		returnVal.setParams(params);

		List<TomtomHit> hits = new ArrayList<TomtomHit>();
		try {
			String line = null;
			BufferedReader br = new BufferedReader(new FileReader(
					tomtomOutputFile));
			while ((line = br.readLine()) != null) {
				if (line.contains("#Query ID	Target ID	Optimal offset	p-value	E-value	q-value	Overlap	Query consensus	Target consensus	Orientation")) {
					// do nothing
				} else {
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

	protected static TomtomHit generateHitTomtom(String line) {
		TomtomHit result = new TomtomHit();
		String[] hitData = line.split("\t");
		result.setQueryPspmId(hitData[0]);
		result.setTargetPspmId(hitData[1]);
		result.setOptimalOffset(Long.parseLong(hitData[2]));
		result.setPvalue(Double.parseDouble(hitData[3]));
		result.setEvalue(Double.parseDouble(hitData[4]));
		result.setQvalue(Double.parseDouble(hitData[5]));
		result.setOverlap(Long.parseLong(hitData[6]));
		result.setQueryConsensus(hitData[7]);
		result.setTargetConsensus(hitData[8]);
		result.setStrand(hitData[9]);
		return result;
	}

	public static MastRunResult findSitesWithMastByCollection(
			MemePSPMCollection query, SequenceSet target,
			MastRunParameters params, String jobId, String token)
			throws Exception {
		MastRunResult returnVal = new MastRunResult();
		returnVal.setId(getKbaseId(MastRunResult.class.getSimpleName()));
		returnVal.setTimestamp(String.valueOf(date.getTime()));
		returnVal.setParams(params);
		String tempFileId = null;
		List<MastHit> hitList = new ArrayList<MastHit>();
		if (jobId == null) {
			tempFileId = getTemporaryFileId();
		} else {
			tempFileId = jobId;
		}

		String motifFileName = null;
		String sequenceFileName = null;
		String outputFileName = null;
		if (DEPLOY_AWE){
			motifFileName = tempFileId + "_query.meme";
			sequenceFileName = tempFileId + "_target.fasta";
			outputFileName = tempFileId + "_mast.txt";
		} else {
			motifFileName = WORK_DIRECTORY + "/" + tempFileId
					+ "_query.meme";
			sequenceFileName = WORK_DIRECTORY + "/" + tempFileId
					+ "_target.fasta";
			outputFileName = WORK_DIRECTORY + "/" + tempFileId + "_mast.txt";
		}
		Integer pspmNumber = -1;
		if (params.getPspmId() != null) {
			pspmNumber = getPSPMnumber(query, params.getPspmId());
			if (pspmNumber == -1)
				throw new Exception("PSPM " + params.getPspmId()
						+ " not found in the collection " + query.getId());
		}
		// Generate command line
		String commandLine = generateMastCommandLine(motifFileName,
				sequenceFileName, params.getMt(), pspmNumber);
		try {
			// Generate motif input file
			generateSimpleMemeFile(query, motifFileName);
			// Generate sequences input file
			generateFastaFile(sequenceFileName, target);
			// Run Mast
			String status = "Input prepared. Starting MAST program...";
			if (jobId != null)
				updateJobProgress(jobId, status, 1L, token);
			executeCommand(commandLine, outputFileName);
			// Parse output
			status = "MAST program finished. Processing output...";
			if (jobId != null)
				updateJobProgress(jobId, status, 1L, token);
			hitList = parseMastOutput(outputFileName);
		} finally {
			File fileDelete = new File(motifFileName);
			fileDelete.delete();
			fileDelete = new File(sequenceFileName);
			fileDelete.delete();
			fileDelete = new File(outputFileName);
			fileDelete.delete();
		}
		returnVal.setHits(hitList);

		return returnVal;
	}

	public static String findSitesWithMastByCollectionFromWs(String wsName,
			MastRunParameters params, String token)
			throws MalformedURLException, Exception {

		String returnVal = findSitesWithMastJobByCollectionFromWs(wsName,
				params, null, token);
		return returnVal;

	}

	public static String findSitesWithMastJobByCollectionFromWs(String wsName,
			MastRunParameters params, String jobId, String token)
			throws MalformedURLException, Exception {

		// Start job
		String desc = "MEME service job " + jobId
				+ ". Method: findSitesWithMastJobByCollectionFromWs. Input: "
				+ params.getQueryRef() + ", " + params.getTargetRef()
				+ ". Workspace: " + wsName + ".";
		if (jobId != null)
			startJob(jobId, desc, 2L, token);

		MemePSPMCollection query = WsDeluxeUtil
				.getObjectFromWsByRef(params.getQueryRef(), token).getData()
				.asClassInstance(MemePSPMCollection.class);
		SequenceSet target = WsDeluxeUtil
				.getObjectFromWsByRef(params.getTargetRef(), token).getData()
				.asClassInstance(SequenceSet.class);

		MastRunResult result = findSitesWithMastByCollection(query, target,
				params, jobId, token);
		String returnVal = result.getId();
		WsDeluxeUtil.saveObjectToWorkspace(
				UObject.transformObjectToObject(result, UObject.class),
				"MEME.MastRunResult", wsName, returnVal, token);

		// Finish job
		if (jobId != null)
			finishJob(jobId, wsName, returnVal, token);
		return returnVal;
	}

	public static MastRunResult findSitesWithMast(MemePSPM query,
			SequenceSet target, MastRunParameters params) throws Exception {
		MemePSPMCollection queryCol = makePSPMCollection(query);
		MastRunResult returnVal = findSitesWithMastByCollection(queryCol,
				target, params, null, null);
		return returnVal;
	}

	/*
	 * public static String findSitesWithMastFromWs(String wsName,
	 * MastRunParameters params, String token) throws MalformedURLException,
	 * Exception{
	 * 
	 * String returnVal = findSitesWithMastJobFromWs(wsName, params, null,
	 * token); return returnVal; }
	 */

	/*
	 * public static String findSitesWithMastJobFromWs(String wsName,
	 * MastRunParameters params, String jobId, String token) throws
	 * MalformedURLException, Exception{
	 * 
	 * //Start job String desc =
	 * "MEME service job. Method: findSitesWithMastJobFromWs. Input: " + queryId
	 * + ", " + targetId + ". Workspace: " + wsName + "."; if (jobId != null)
	 * startJob (jobId, desc, 2L, token);
	 * 
	 * GetObjectParams queryParams = new
	 * GetObjectParams().withType("MemePSPM").withId
	 * (queryId).withWorkspace(wsName).withAuth(token); GetObjectOutput
	 * queryOutput = wsClient(token).getObject(queryParams); MemePSPM query =
	 * UObject.transformObjectToObject(queryOutput.getData(), MemePSPM.class);
	 * 
	 * MemePSPMCollection queryCol = makePSPMCollection(query); GetObjectParams
	 * targetParams = new
	 * GetObjectParams().withType("SequenceSet").withId(targetId
	 * ).withWorkspace(wsName).withAuth(token); GetObjectOutput targetOutput =
	 * wsClient(token).getObject(targetParams); SequenceSet target =
	 * UObject.transformObjectToObject(targetOutput.getData(),
	 * SequenceSet.class);
	 * 
	 * MastRunResult result = findSitesWithMastByCollection(queryCol, target,
	 * "", mt, jobId, token);
	 * 
	 * String returnVal = result.getId(); WsDeluxeUtil.saveObjectToWorkspace
	 * (UObject.transformObjectToObject(result, UObject.class),
	 * result.getClass().getSimpleName(), wsName, returnVal, token);
	 * 
	 * //Finish job if (jobId != null) finishJob (jobId, wsName, returnVal,
	 * token); return returnVal; }
	 */

	protected static Integer getPSPMnumber(MemePSPMCollection query,
			String pspmId) {
		Integer returnVal = 0;
		for (MemePSPM pspm : query.getPspms()) {
			if (pspm.getId().equals(pspmId)) {
				returnVal = query.getPspms().indexOf(pspm) + 1;
				return returnVal;
			}
		}
		return -1;
	}

	protected static String generateMastCommandLine(String motifFileName,
			String sequenceFileName, Double mtMast, Integer pspmNumber) {
		String result = "mast " + motifFileName + " " + sequenceFileName + " ";
		if (pspmNumber > -1) {
			result += "-m " + pspmNumber.toString() + " ";
		}
		if (mtMast > 0) {
			result += "-mt "
					+ String.format("%f", mtMast).replaceAll("0*$", "");
		}
		result += " -hit_list -nostatus";
		return result;
	}

	protected static List<MastHit> parseMastOutput(String mastOutputFile) {
		List<MastHit> result = new ArrayList<MastHit>();
		try {
			String line = null;
			BufferedReader br = new BufferedReader(new FileReader(
					mastOutputFile));
			while ((line = br.readLine()) != null) {
				if (line.matches("^# All non-overlapping hits in all sequences from.*")) {
					// do nothing
				} else if (line
						.contains("# sequence_name motif hit_start hit_end score hit_p-value")) {
				} else if (line.matches("^# mast .*")) {
				} else {
					result.add(generateHitMast(line));
				}
			}
			br.close();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
		return result;
	}

	protected static MastHit generateHitMast(String line) {
		MastHit result = new MastHit();
		line = line.replaceAll("\\s+", " ");
		String[] hitData = line.split(" ");
		result.setSeqId(hitData[0]);
		result.setStrand(hitData[1].substring(0, 1));
		result.setPspmId(hitData[1].substring(1));
		result.setHitStart(Long.parseLong(hitData[2]));
		result.setHitEnd(Long.parseLong(hitData[3]));
		result.setScore(Double.parseDouble(hitData[4]));
		result.setHitPvalue(Double.parseDouble(hitData[5]));
		return result;
	}

	public static String getKbaseId(String entityType) {
		String returnVal = null;
		URL idServerUrl = null;
		try {
			idServerUrl = new URL(ID_SERVICE_URL);
		} catch (MalformedURLException e) {
			System.err.println("Unable to connect to ID service at "
					+ ID_SERVICE_URL);
			e.printStackTrace();
		}
		IDServerAPIClient idClient = new IDServerAPIClient(idServerUrl);

		try {
			if (entityType.equals("MemeRunResult")) {
				returnVal = "kb|memerunresult."
						+ idClient.allocateIdRange("kb|memerunresult", 1L)
								.toString();
			} else if (entityType.equals("MemeMotif")) {
				returnVal = "kb|mememotif."
						+ idClient.allocateIdRange("kb|mememotif", 1L)
								.toString();
			} else if (entityType.equals("MemeSite")) {
				returnVal = "kb|memesite."
						+ idClient.allocateIdRange("kb|memesite", 1L)
								.toString();
			} else if (entityType.equals("MemePSPMCollection")) {
				returnVal = "kb|memepspmcollection."
						+ idClient.allocateIdRange("kb|memepspmcollection", 1L)
								.toString();
			} else if (entityType.equals("MemePSPM")) {
				returnVal = "kb|memepspm."
						+ idClient.allocateIdRange("kb|memepspm", 1L)
								.toString();
			} else if (entityType.equals("TomtomRunResult")) {
				returnVal = "kb|tomtomrunresult."
						+ idClient.allocateIdRange("kb|tomtomrunresult", 1L)
								.toString();
			} else if (entityType.equals("MastRunResult")) {
				returnVal = "kb|mastrunresult."
						+ idClient.allocateIdRange("kb|mastrunresult", 1L)
								.toString();
			} else if (entityType.equals("Sequence")) {
				returnVal = "kb|sequence."
						+ idClient.allocateIdRange("kb|sequence", 1L)
								.toString();
			} else if (entityType.equals("SequenceSet")) {
				returnVal = "kb|sequenceset."
						+ idClient.allocateIdRange("kb|sequenceset", 1L)
								.toString();
			} else {
				System.err.println("Unknown data type " + entityType);
			}
		} catch (IOException e) {
			System.err.println("Unable to get ID for " + entityType + " at "
					+ ID_SERVICE_URL + " : IO Exception");
			e.printStackTrace();
		} catch (JsonClientException e) {
			System.err.println("Unable to get ID for " + entityType + " at "
					+ ID_SERVICE_URL + " : JSON Client Exception");
			e.printStackTrace();
		}
		return returnVal;
	}

	protected static void deleteFile(String folder, final String pattern) {
		File dir = new File(folder);
		File fileDelete;

		for (String file : dir.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.matches(pattern);
			}
		})) {
			String temp = new StringBuffer(folder).append(File.separator)
					.append(file).toString();
			fileDelete = new File(temp);
			boolean isdeleted = fileDelete.delete();
			System.out.println("file : " + temp + " is deleted : " + isdeleted);
		}
	}

}
