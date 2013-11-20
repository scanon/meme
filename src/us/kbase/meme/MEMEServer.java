package us.kbase.meme;

import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonServerMethod;
import us.kbase.common.service.JsonServerServlet;
import us.kbase.generaltypes.SequenceSet;

//BEGIN_HEADER
//END_HEADER

/**
 * <p>Original spec-file module name: MEME</p>
 * <pre>
 * Module MEME version 1.0
 * This module provides a set of methods for work with regulatory motifs. These methods integrate capabilities of three MEME Suite tools into KBase:
 * - MEME is a tool for finding ungapped motifs in unaligned DNA sequences
 * - TOMTOM is a tool for comparison of an input DNA motif to the elements of a database of known motifs
 * - MAST is a tool for searching biological sequence databases for occurrences of known motifs
 * Data types summary
 * MemeRunParameters data type keeps input parameters for MEME run.
 * TomtomRunParameters data type keeps input parameters for TOMTOM run.
 * MastRunParameters data type keeps input parameters for MAST run.
 * MemeSite data type represents a particular site from MEME motif description.
 * MemeMotif data type represents DNA sequence motif predicted by MEME.
 * MemeRunResult data type represents result of a single MEME run and contains parameters of the search and list of identified motifs.
 * MemePSPM data type represents a position-specific letter-probability matrix for use with TOMTOM and MAST.
 * MemePSPMCollection data type contains collection of matrices for use with TOMTOM and MAST.
 * TomtomHit represents a results of comparison of two matrices by TOMTOM.
 * TomtomRunResult represents result of a single TOMTOM run.
 * MastHit represents a particular MAST hit.
 * MastRunResult represents results of a single MAST run.
 * Methods summary
 * find_motifs_with_meme - runs MEME for a set of nucleotide sequences provided as SequenceSet object and returns MemeRunResult object
 * find_motifs_with_meme_from_ws - runs MEME for a set of nucleotide sequences provided as workspace ID of the SequenceSet and returns kbase ID of MemeRunResult object saved in workspace
 * find_motifs_with_meme_job_from_ws - runs MEME for a set of nucleotide sequences provided as workspace ID of the SequenceSet and returns job ID 
 * compare_motifs_with_tomtom - runs TOMTOM for MemePSPM as query vs. MemePSPMCollection as target and returns TomtomRunResult (list of motif pairs)
 * compare_motifs_with_tomtom_from_ws - runs TOMTOM for MemePSPM as query vs. MemePSPMCollection as target provided as workspace IDs and returns kbase ID of the TomtomRunResult saved in workspace
 * compare_motifs_with_tomtom_job_from_ws - runs TOMTOM for MemePSPM as query vs. MemePSPMCollection as target provided as workspace IDs and returns job ID
 * compare_motifs_with_tomtom_by_collection - runs TOMTOM for two collection of PSPMs and returns TomtomRunResult (list of motif pairs)
 * compare_motifs_with_tomtom_by_collection_from_ws - runs TOMTOM for two collection of PSPMs provided as workspace IDs and returns kbase ID of the TomtomRunResult saved in workspace
 * compare_motifs_with_tomtom_job_by_collection_from_ws - runs TOMTOM for two collection of PSPMs provided as workspace IDs and returns job ID
 * find_sites_with_mast - runs MAST for MemePSPM vs. SequenceSet and returns MastRunResult (list of MAST hits)
 * find_sites_with_mast_from_ws - runs MAST for MemePSPM stored in workspace vs. SequenceSet stored in workspace and returns kbase ID of MastRunResult (list of MAST hits) saved in workspace
 * find_sites_with_mast_job_from_ws - runs MAST for MemePSPM stored in workspace vs. SequenceSet stored in workspace and returns job ID
 * find_sites_with_mast_by_collection - runs MAST for MemePSPMCollection vs. SequenceSet and returns MastRunResult (list of MAST hits)
 * find_sites_with_mast_by_collection_from_ws - runs MAST for MemePSPMCollection stored in workspace vs. SequenceSet stored in workspace and returns kbase ID of MastRunResult (list of MAST hits) saved in workspace
 * find_sites_with_mast_job_by_collection_from_ws - runs MAST for MemePSPMCollection stored in workspace vs. SequenceSet stored in workspace and returns kbase ID of MastRunResult (list of MAST hits) saved in workspace
 * get_pspm_collection_from_meme_result - converts MemeRunResult into MemePSPMCollection
 * get_pspm_collection_from_meme_result_from_ws - converts MemeRunResult into MemePSPMCollection and writes the result to workspace
 * get_pspm_collection_from_meme_result_job_from_ws - returns ID of job object that contains ID of MemePSPMCollection converted from MemeRunResult
 * </pre>
 */
public class MEMEServer extends JsonServerServlet {
    private static final long serialVersionUID = 1L;

    //BEGIN_CLASS_HEADER
    //END_CLASS_HEADER

    public MEMEServer() throws Exception {
        super("MEME");
        //BEGIN_CONSTRUCTOR
        MemeServerImpl.cleanUpOnStart();
        //END_CONSTRUCTOR
    }

    /**
     * <p>Original spec-file function name: find_motifs_with_meme</p>
     * <pre>
     * Returns results of a single MEME run
     * MEME will be run with -dna -text parameters
     * SequenceSet sequenceSet - input set of sequences
     * MemeRunParameters params - parameters of MEME run
     * </pre>
     * @param   sequenceSet   instance of type {@link us.kbase.generaltypes.SequenceSet SequenceSet}
     * @param   params   instance of type {@link us.kbase.meme.MemeRunParameters MemeRunParameters}
     * @return   parameter "meme_run_result" of type {@link us.kbase.meme.MemeRunResult MemeRunResult}
     */
    @JsonServerMethod(rpc = "MEME.find_motifs_with_meme")
    public MemeRunResult findMotifsWithMeme(SequenceSet sequenceSet, MemeRunParameters params) throws Exception {
        MemeRunResult returnVal = null;
        //BEGIN find_motifs_with_meme
        returnVal = MemeServerCaller.findMotifsWithMeme(sequenceSet, params);
        //END find_motifs_with_meme
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: find_motifs_with_meme_from_ws</p>
     * <pre>
     * Returns kbase id of MemeRunResult object that contains results of a single MEME run
     * MEME will be run with -dna -text parameters
     * string ws_id - workspace id
     * string sequence_set_id - kbase id of the input set of sequences
     * MemeRunParameters params - parameters of MEME run
     * </pre>
     * @param   wsId   instance of String
     * @param   sequenceSetId   instance of String
     * @param   params   instance of type {@link us.kbase.meme.MemeRunParameters MemeRunParameters}
     * @return   parameter "meme_run_result_id" of String
     */
    @JsonServerMethod(rpc = "MEME.find_motifs_with_meme_from_ws")
    public String findMotifsWithMemeFromWs(String wsId, String sequenceSetId, MemeRunParameters params, AuthToken authPart) throws Exception {
        String returnVal = null;
        //BEGIN find_motifs_with_meme_from_ws
        returnVal = MemeServerCaller.findMotifsWithMemeFromWs(wsId, sequenceSetId, params, authPart);
        //END find_motifs_with_meme_from_ws
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: find_motifs_with_meme_job_from_ws</p>
     * <pre>
     * Returns id of job object that contains id of a single MEME run result
     * MEME will be run with -dna -text parameters
     * string ws_id - workspace id
     * string sequence_set_id - kbase id of the input set of sequences
     * MemeRunParameters params - parameters of MEME run
     * </pre>
     * @param   wsId   instance of String
     * @param   sequenceSetId   instance of String
     * @param   params   instance of type {@link us.kbase.meme.MemeRunParameters MemeRunParameters}
     * @return   parameter "job_id" of String
     */
    @JsonServerMethod(rpc = "MEME.find_motifs_with_meme_job_from_ws")
    public String findMotifsWithMemeJobFromWs(String wsId, String sequenceSetId, MemeRunParameters params, AuthToken authPart) throws Exception {
        String returnVal = null;
        //BEGIN find_motifs_with_meme_job_from_ws
        returnVal = MemeServerCaller.findMotifsWithMemeJobFromWs(wsId, sequenceSetId, params, authPart);
        //END find_motifs_with_meme_job_from_ws
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: compare_motifs_with_tomtom</p>
     * <pre>
     * Returns TomtomRunResult with results of a single TOMTOM run
     * TOMTOM will be run with -text parameter
     * MemePSPM query - query motifs for TOMTOM run
     * MemePSPMCollection target - target motifs for TOMTOM run
     * TomtomRunParameters params - parameters of TOMTOM run
     * </pre>
     * @param   query   instance of type {@link us.kbase.meme.MemePSPM MemePSPM}
     * @param   target   instance of type {@link us.kbase.meme.MemePSPMCollection MemePSPMCollection}
     * @param   params   instance of type {@link us.kbase.meme.TomtomRunParameters TomtomRunParameters}
     * @return   parameter "tomtom_run_result" of type {@link us.kbase.meme.TomtomRunResult TomtomRunResult}
     */
    @JsonServerMethod(rpc = "MEME.compare_motifs_with_tomtom")
    public TomtomRunResult compareMotifsWithTomtom(MemePSPM query, MemePSPMCollection target, TomtomRunParameters params) throws Exception {
        TomtomRunResult returnVal = null;
        //BEGIN compare_motifs_with_tomtom
        returnVal = MemeServerCaller.compareMotifsWithTomtom(query,  target, params);
        //END compare_motifs_with_tomtom
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: compare_motifs_with_tomtom_from_ws</p>
     * <pre>
     * Returns kbase ID of TomtomRunResult with results of a single TOMTOM run
     * string ws_id - workspace id 
     * TOMTOM will be run with -text parameter
     * string query_id - kbase id of query MemePSPM for TOMTOM run
     * string target_id - kbase id of MemePSPMCollection with target motifs for TOMTOM run
     * TomtomRunParameters params - parameters of TOMTOM run
     * </pre>
     * @param   wsId   instance of String
     * @param   queryId   instance of String
     * @param   targetId   instance of String
     * @param   params   instance of type {@link us.kbase.meme.TomtomRunParameters TomtomRunParameters}
     * @return   parameter "tomtom_run_result_id" of String
     */
    @JsonServerMethod(rpc = "MEME.compare_motifs_with_tomtom_from_ws")
    public String compareMotifsWithTomtomFromWs(String wsId, String queryId, String targetId, TomtomRunParameters params, AuthToken authPart) throws Exception {
        String returnVal = null;
        //BEGIN compare_motifs_with_tomtom_from_ws
        returnVal = MemeServerCaller.compareMotifsWithTomtomFromWs(wsId, queryId, targetId, params, authPart);
        //END compare_motifs_with_tomtom_from_ws
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: compare_motifs_with_tomtom_job_from_ws</p>
     * <pre>
     * Returns ID of job object that contains id of a single TOMTOM run result
     * string ws_id - workspace id 
     * TOMTOM will be run with -text parameter
     * string query_id - kbase id of query MemePSPM for TOMTOM run
     * string target_id - kbase id of MemePSPMCollection with target motifs for TOMTOM run
     * TomtomRunParameters params - parameters of TOMTOM run
     * </pre>
     * @param   wsId   instance of String
     * @param   queryId   instance of String
     * @param   targetId   instance of String
     * @param   params   instance of type {@link us.kbase.meme.TomtomRunParameters TomtomRunParameters}
     * @return   parameter "job_id" of String
     */
    @JsonServerMethod(rpc = "MEME.compare_motifs_with_tomtom_job_from_ws")
    public String compareMotifsWithTomtomJobFromWs(String wsId, String queryId, String targetId, TomtomRunParameters params, AuthToken authPart) throws Exception {
        String returnVal = null;
        //BEGIN compare_motifs_with_tomtom_job_from_ws
        returnVal = MemeServerCaller.compareMotifsWithTomtomJobFromWs(wsId, queryId, targetId, params, authPart);
        //END compare_motifs_with_tomtom_job_from_ws
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: compare_motifs_with_tomtom_by_collection</p>
     * <pre>
     * Returns TomtomRunResult with results of a single TOMTOM run
     * TOMTOM will be run with -text parameter
     * MemePSPMCollection query - query motifs for TOMTOM run
     * MemePSPMCollection target - target motifs for TOMTOM run
     * string pspm_id - KBase ID of a MemePSPM from the query collection that will be used. When empty string provided, all motifs in the query collection will be used
     * TomtomRunParameters params - parameters of TOMTOM run
     * </pre>
     * @param   query   instance of type {@link us.kbase.meme.MemePSPMCollection MemePSPMCollection}
     * @param   target   instance of type {@link us.kbase.meme.MemePSPMCollection MemePSPMCollection}
     * @param   pspmId   instance of String
     * @param   params   instance of type {@link us.kbase.meme.TomtomRunParameters TomtomRunParameters}
     * @return   parameter "tomtom_run_result" of type {@link us.kbase.meme.TomtomRunResult TomtomRunResult}
     */
    @JsonServerMethod(rpc = "MEME.compare_motifs_with_tomtom_by_collection")
    public TomtomRunResult compareMotifsWithTomtomByCollection(MemePSPMCollection query, MemePSPMCollection target, String pspmId, TomtomRunParameters params) throws Exception {
        TomtomRunResult returnVal = null;
        //BEGIN compare_motifs_with_tomtom_by_collection
        returnVal = MemeServerCaller.compareMotifsWithTomtomByCollection(query, target, pspmId, params);
        //END compare_motifs_with_tomtom_by_collection
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: compare_motifs_with_tomtom_by_collection_from_ws</p>
     * <pre>
     * Returns kbase ID of TomtomRunResult with results of a single TOMTOM run
     * string ws_id - workspace id 
     * TOMTOM will be run with -text parameter
     * string query_id - kbase id of MemePSPMCollection with query motifs for TOMTOM run
     * string target_id - kbase id of MemePSPMCollection with target motifs for TOMTOM run
     * string pspm_id - KBase ID of a MemePSPM from the query collection that will be used. When empty string provided, all motifs in the query collection will be used
     * TomtomRunParameters params - parameters of TOMTOM run
     * </pre>
     * @param   wsId   instance of String
     * @param   queryId   instance of String
     * @param   targetId   instance of String
     * @param   pspmId   instance of String
     * @param   params   instance of type {@link us.kbase.meme.TomtomRunParameters TomtomRunParameters}
     * @return   parameter "tomtom_run_result_id" of String
     */
    @JsonServerMethod(rpc = "MEME.compare_motifs_with_tomtom_by_collection_from_ws")
    public String compareMotifsWithTomtomByCollectionFromWs(String wsId, String queryId, String targetId, String pspmId, TomtomRunParameters params, AuthToken authPart) throws Exception {
        String returnVal = null;
        //BEGIN compare_motifs_with_tomtom_by_collection_from_ws
        returnVal = MemeServerCaller.compareMotifsWithTomtomByCollectionFromWs(wsId, queryId, targetId, pspmId, params, authPart);
        //END compare_motifs_with_tomtom_by_collection_from_ws
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: compare_motifs_with_tomtom_job_by_collection_from_ws</p>
     * <pre>
     * Returns ID of job object that contains ID of results of a single TOMTOM run
     * string ws_id - workspace id 
     * TOMTOM will be run with -text parameter
     * string query_id - kbase id of MemePSPMCollection with query motifs for TOMTOM run
     * string target_id - kbase id of MemePSPMCollection with target motifs for TOMTOM run
     * string pspm_id - KBase ID of a MemePSPM from the query collection that will be used. When empty string provided, all motifs in the query collection will be used
     * TomtomRunParameters params - parameters of TOMTOM run
     * </pre>
     * @param   wsId   instance of String
     * @param   queryId   instance of String
     * @param   targetId   instance of String
     * @param   pspmId   instance of String
     * @param   params   instance of type {@link us.kbase.meme.TomtomRunParameters TomtomRunParameters}
     * @return   parameter "job_id" of String
     */
    @JsonServerMethod(rpc = "MEME.compare_motifs_with_tomtom_job_by_collection_from_ws")
    public String compareMotifsWithTomtomJobByCollectionFromWs(String wsId, String queryId, String targetId, String pspmId, TomtomRunParameters params, AuthToken authPart) throws Exception {
        String returnVal = null;
        //BEGIN compare_motifs_with_tomtom_job_by_collection_from_ws
        returnVal = MemeServerCaller.compareMotifsWithTomtomJobByCollectionFromWs(wsId, queryId, targetId, pspmId, params, authPart);
        //END compare_motifs_with_tomtom_job_by_collection_from_ws
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: find_sites_with_mast</p>
     * <pre>
     * Returns MastRunResult containing list of MAST hits
     * MAST will be run with -hit_list parameter
     * MemePSPM query - query PSPM for MAST run
     * SequenceSet target - target sequences for MAST run
     * float mt - value of mt parameter for MAST run
     * </pre>
     * @param   query   instance of type {@link us.kbase.meme.MemePSPM MemePSPM}
     * @param   target   instance of type {@link us.kbase.generaltypes.SequenceSet SequenceSet}
     * @param   mt   instance of Double
     * @return   parameter "mast_run_result" of type {@link us.kbase.meme.MastRunResult MastRunResult}
     */
    @JsonServerMethod(rpc = "MEME.find_sites_with_mast")
    public MastRunResult findSitesWithMast(MemePSPM query, SequenceSet target, Double mt) throws Exception {
        MastRunResult returnVal = null;
        //BEGIN find_sites_with_mast
        returnVal = MemeServerCaller.findSitesWithMast(query, target, mt);
        //END find_sites_with_mast
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: find_sites_with_mast_from_ws</p>
     * <pre>
     * Returns kbase ID of MastRunResult containing list of MAST hits
     * MAST will be run with -hit_list parameter
     * string ws_id - workspace id 
     * string query_id - kbase ID of MemePSPM containing query motifs for MAST run
     * string target_id - kbase ID of SequenceSet containing target sequences for MAST run
     * float mt - value of mt parameter for MAST run
     * </pre>
     * @param   wsId   instance of String
     * @param   queryId   instance of String
     * @param   targetId   instance of String
     * @param   mt   instance of Double
     * @return   parameter "mast_run_result_id" of String
     */
    @JsonServerMethod(rpc = "MEME.find_sites_with_mast_from_ws")
    public String findSitesWithMastFromWs(String wsId, String queryId, String targetId, Double mt, AuthToken authPart) throws Exception {
        String returnVal = null;
        //BEGIN find_sites_with_mast_from_ws
        returnVal = MemeServerCaller.findSitesWithMastFromWs(wsId, queryId, targetId, mt, authPart);
        //END find_sites_with_mast_from_ws
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: find_sites_with_mast_job_from_ws</p>
     * <pre>
     * Returns ID of job object that contains ID of MastRunResult
     * MAST will be run with -hit_list parameter
     * string ws_id - workspace id 
     * string query_id - kbase ID of MemePSPM containing query motifs for MAST run
     * string target_id - kbase ID of SequenceSet containing target sequences for MAST run
     * float mt - value of mt parameter for MAST run
     * </pre>
     * @param   wsId   instance of String
     * @param   queryId   instance of String
     * @param   targetId   instance of String
     * @param   mt   instance of Double
     * @return   parameter "job_id" of String
     */
    @JsonServerMethod(rpc = "MEME.find_sites_with_mast_job_from_ws")
    public String findSitesWithMastJobFromWs(String wsId, String queryId, String targetId, Double mt, AuthToken authPart) throws Exception {
        String returnVal = null;
        //BEGIN find_sites_with_mast_job_from_ws
        returnVal = MemeServerCaller.findSitesWithMastJobFromWs(wsId, queryId, targetId, mt, authPart);
        //END find_sites_with_mast_job_from_ws
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: find_sites_with_mast_by_collection</p>
     * <pre>
     * Returns MastRunResult containing list of MAST hits
     * MAST will be run with -hit_list parameter
     * MemePSPMCollection query - query motifs for MAST run
     * SequenceSet target - target sequences for MAST run
     * string pspm_id - KBase ID of a MemePSPM from the query collection that will be used. When empty string provided, all motifs in the query collection will be used
     * float mt - value of mt parameter for MAST run
     * </pre>
     * @param   query   instance of type {@link us.kbase.meme.MemePSPMCollection MemePSPMCollection}
     * @param   target   instance of type {@link us.kbase.generaltypes.SequenceSet SequenceSet}
     * @param   pspmId   instance of String
     * @param   mt   instance of Double
     * @return   parameter "mast_run_result" of type {@link us.kbase.meme.MastRunResult MastRunResult}
     */
    @JsonServerMethod(rpc = "MEME.find_sites_with_mast_by_collection")
    public MastRunResult findSitesWithMastByCollection(MemePSPMCollection query, SequenceSet target, String pspmId, Double mt) throws Exception {
        MastRunResult returnVal = null;
        //BEGIN find_sites_with_mast_by_collection
        returnVal = MemeServerCaller.findSitesWithMastByCollection(query, target, pspmId, mt);
        //END find_sites_with_mast_by_collection
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: find_sites_with_mast_by_collection_from_ws</p>
     * <pre>
     * Returns kbase ID of MastRunResult containing list of MAST hits
     * MAST will be run with -hit_list parameter
     * string ws_id - workspace id 
     * string query_id - kbase ID of MemePSPMCollection containing query motifs for MAST run
     * string target_id - kbase ID of SequenceSet containing target sequences for MAST run
     * string pspm_id - KBase ID of a MemePSPM from the query collection that will be used. When empty string provided, all motifs in the query collection will be used
     * float mt - value of mt parameter for MAST run
     * </pre>
     * @param   wsId   instance of String
     * @param   queryId   instance of String
     * @param   targetId   instance of String
     * @param   pspmId   instance of String
     * @param   mt   instance of Double
     * @return   parameter "mast_run_result_id" of String
     */
    @JsonServerMethod(rpc = "MEME.find_sites_with_mast_by_collection_from_ws")
    public String findSitesWithMastByCollectionFromWs(String wsId, String queryId, String targetId, String pspmId, Double mt, AuthToken authPart) throws Exception {
        String returnVal = null;
        //BEGIN find_sites_with_mast_by_collection_from_ws
        returnVal = MemeServerCaller.findSitesWithMastByCollectionFromWs(wsId, queryId, targetId, pspmId, mt, authPart);
        //END find_sites_with_mast_by_collection_from_ws
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: find_sites_with_mast_job_by_collection_from_ws</p>
     * <pre>
     * Returns ID of job object that contains ID of MastRunResult
     * MAST will be run with -hit_list parameter
     * string ws_id - workspace id 
     * string query_id - kbase ID of MemePSPMCollection containing query motifs for MAST run
     * string target_id - kbase ID of SequenceSet containing target sequences for MAST run
     * string pspm_id - KBase ID of a MemePSPM from the query collection that will be used. When empty string provided, all motifs in the query collection will be used
     * float mt - value of mt parameter for MAST run
     * </pre>
     * @param   wsId   instance of String
     * @param   queryId   instance of String
     * @param   targetId   instance of String
     * @param   pspmId   instance of String
     * @param   mt   instance of Double
     * @return   parameter "job_id" of String
     */
    @JsonServerMethod(rpc = "MEME.find_sites_with_mast_job_by_collection_from_ws")
    public String findSitesWithMastJobByCollectionFromWs(String wsId, String queryId, String targetId, String pspmId, Double mt, AuthToken authPart) throws Exception {
        String returnVal = null;
        //BEGIN find_sites_with_mast_job_by_collection_from_ws
        returnVal = MemeServerCaller.findSitesWithMastJobByCollectionFromWs(wsId, queryId, targetId, pspmId, mt, authPart);
        //END find_sites_with_mast_job_by_collection_from_ws
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: get_pspm_collection_from_meme_result</p>
     * <pre>
     * Converts MemeRunResult into MemePSPMCollection 
     * MemeRunResult meme_run_result - source MemeRunResult
     * </pre>
     * @param   memeRunResult   instance of type {@link us.kbase.meme.MemeRunResult MemeRunResult}
     * @return   parameter "meme_pspm_collection" of type {@link us.kbase.meme.MemePSPMCollection MemePSPMCollection}
     */
    @JsonServerMethod(rpc = "MEME.get_pspm_collection_from_meme_result")
    public MemePSPMCollection getPspmCollectionFromMemeResult(MemeRunResult memeRunResult) throws Exception {
        MemePSPMCollection returnVal = null;
        //BEGIN get_pspm_collection_from_meme_result
        returnVal = MemeServerCaller.getPspmCollectionFromMemeResult(memeRunResult);
        //END get_pspm_collection_from_meme_result
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: get_pspm_collection_from_meme_result_from_ws</p>
     * <pre>
     * Converts MemeRunResult into MemePSPMCollection and writes the result to workspace
     * string ws_id - workspace id
     * string meme_run_result_id - KBase ID of source MemeRunResult
     * </pre>
     * @param   wsId   instance of String
     * @param   memeRunResultId   instance of String
     * @return   parameter "meme_pspm_collection_id" of String
     */
    @JsonServerMethod(rpc = "MEME.get_pspm_collection_from_meme_result_from_ws")
    public String getPspmCollectionFromMemeResultFromWs(String wsId, String memeRunResultId, AuthToken authPart) throws Exception {
        String returnVal = null;
        //BEGIN get_pspm_collection_from_meme_result_from_ws
        returnVal = MemeServerCaller.getPspmCollectionFromMemeResultFromWs(wsId, memeRunResultId, authPart);
        //END get_pspm_collection_from_meme_result_from_ws
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: get_pspm_collection_from_meme_result_job_from_ws</p>
     * <pre>
     * Returns ID of job object that contains ID of MemePSPMCollection converted from MemeRunResult
     * string ws_id - workspace id
     * string meme_run_result_id - KBase ID of source MemeRunResult
     * </pre>
     * @param   wsId   instance of String
     * @param   memeRunResultId   instance of String
     * @return   parameter "job_id" of String
     */
    @JsonServerMethod(rpc = "MEME.get_pspm_collection_from_meme_result_job_from_ws")
    public String getPspmCollectionFromMemeResultJobFromWs(String wsId, String memeRunResultId, AuthToken authPart) throws Exception {
        String returnVal = null;
        //BEGIN get_pspm_collection_from_meme_result_job_from_ws
        returnVal = MemeServerCaller.getPspmCollectionFromMemeResultJobFromWs(wsId, memeRunResultId, authPart);
        //END get_pspm_collection_from_meme_result_job_from_ws
        return returnVal;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: <program> <server_port>");
            return;
        }
        new MEMEServer().startupServer(Integer.parseInt(args[0]));
    }
}
