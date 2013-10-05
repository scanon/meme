package us.kbase.idserverapi;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.type.TypeReference;
import us.kbase.JsonClientCaller;
import us.kbase.Tuple2;

/**
 * <p>Original spec-file module name: IDServerAPI</p>
 * <pre>
 * The KBase ID server provides access to the mappings between KBase identifiers and
 * external identifiers (the original identifiers for data that was migrated from
 * other databases into KBase).
 * </pre>
 */
public class IdserverapiClient {
    private JsonClientCaller caller;

    public IdserverapiClient(String url) throws MalformedURLException {
        caller = new JsonClientCaller(url);
    }

    /**
     * <p>Original spec-file function name: kbase_ids_to_external_ids</p>
     * <pre>
     * Given a set of KBase identifiers, look up the associated external identifiers.
     * If no external ID is associated with the KBase id, no entry will be present in the return.
     * </pre>
     */
    public Map<String,Tuple2<String, String>> kbaseIdsToExternalIds(List<String> ids) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(ids);
        TypeReference<List<Map<String,Tuple2<String, String>>>> retType = new TypeReference<List<Map<String,Tuple2<String, String>>>>() {};
        List<Map<String,Tuple2<String, String>>> res = caller.jsonrpcCall("IDServerAPI.kbase_ids_to_external_ids", args, retType, true, false);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: external_ids_to_kbase_ids</p>
     * <pre>
     * Given a set of external identifiers, look up the associated KBase identifiers.
     * If no KBase ID is associated with the external id, no entry will be present in the return.
     * </pre>
     * @param   arg1   Original type "external_db" (Each external database is represented using a short string. Microbes Online is "MOL", the SEED is "SEED", etc.)
     */
    public Map<String,String> externalIdsToKbaseIds(String arg1, List<String> extIds) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(arg1);
        args.add(extIds);
        TypeReference<List<Map<String,String>>> retType = new TypeReference<List<Map<String,String>>>() {};
        List<Map<String,String>> res = caller.jsonrpcCall("IDServerAPI.external_ids_to_kbase_ids", args, retType, true, false);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: register_ids</p>
     * <pre>
     * Register a set of identifiers. All will be assigned identifiers with the given
     * prefix.
     * If an external ID has already been registered, the existing registration will be returned instead 
     * of a new ID being allocated.
     * </pre>
     * @param   prefix   Original type "kbase_id_prefix" (A KBase identifier prefix. This is a string that starts with "kb|" and includes either a single type designator (e.g. "kb|g") or is a prefix for a hierarchical identifier (e.g. "kb|g.1234.fp").)
     * @param   dbName   Original type "external_db" (Each external database is represented using a short string. Microbes Online is "MOL", the SEED is "SEED", etc.)
     */
    public Map<String,String> registerIds(String prefix, String dbName, List<String> ids) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(prefix);
        args.add(dbName);
        args.add(ids);
        TypeReference<List<Map<String,String>>> retType = new TypeReference<List<Map<String,String>>>() {};
        List<Map<String,String>> res = caller.jsonrpcCall("IDServerAPI.register_ids", args, retType, true, false);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: allocate_id_range</p>
     * <pre>
     * Allocate a set of identifiers. This allows efficient registration of a large
     * number of identifiers (e.g. several thousand features in a genome).
     * The return is the first identifier allocated.
     * </pre>
     * @param   arg1   Original type "kbase_id_prefix" (A KBase identifier prefix. This is a string that starts with "kb|" and includes either a single type designator (e.g. "kb|g") or is a prefix for a hierarchical identifier (e.g. "kb|g.1234.fp").)
     */
    public Integer allocateIdRange(String arg1, Integer count) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(arg1);
        args.add(count);
        TypeReference<List<Integer>> retType = new TypeReference<List<Integer>>() {};
        List<Integer> res = caller.jsonrpcCall("IDServerAPI.allocate_id_range", args, retType, true, false);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: register_allocated_ids</p>
     * <pre>
     * Register the mappings for a set of external identifiers. The
     * KBase identifiers used here were previously allocated using allocate_id_range.
     * Does not return a value.
     * </pre>
     * @param   prefix   Original type "kbase_id_prefix" (A KBase identifier prefix. This is a string that starts with "kb|" and includes either a single type designator (e.g. "kb|g") or is a prefix for a hierarchical identifier (e.g. "kb|g.1234.fp").)
     * @param   dbName   Original type "external_db" (Each external database is represented using a short string. Microbes Online is "MOL", the SEED is "SEED", etc.)
     */
    public void registerAllocatedIds(String prefix, String dbName, Map<String,Integer> assignments) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(prefix);
        args.add(dbName);
        args.add(assignments);
        TypeReference<Object> retType = new TypeReference<Object>() {};
        caller.jsonrpcCall("IDServerAPI.register_allocated_ids", args, retType, false, false);
    }
}
