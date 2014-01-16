package us.kbase.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import us.kbase.auth.TokenFormatException;
import us.kbase.common.service.JsonClientException;
import us.kbase.common.service.UObject;
import us.kbase.common.service.UnauthorizedException;
import us.kbase.kbasesequences.Sequence;
import us.kbase.kbasesequences.SequenceSet;
import us.kbase.meme.MemeServerImpl;

public class SequenceSetImporter {
	
	public static SequenceSet importSequenceSetFromFile (String fileName, String wsName, String setName, String description, String token) throws TokenFormatException, UnauthorizedException, IOException, JsonClientException {
		SequenceSet set = new SequenceSet();
		List<String> fileContent = readFile(fileName);
		set = generateSequenceSet(fileContent, setName, description);
		WsDeluxeUtil.saveObjectToWorkspace(UObject.transformObjectToObject(set, UObject.class), "KBaseSequences.SequenceSet", wsName, set.getSequenceSetId(), token);
		return set;
	}
	
	public static SequenceSet generateSequenceSet (List<String> fileContent, String name, String description) {
		String header = null;
		String sequence = "";
		SequenceSet returnVal = new SequenceSet();
		List<Sequence> seqList = new ArrayList<Sequence>();
		for (String line : fileContent){
			if (line == null) {
				//do nothing
			} else if (line.equals("")){
				//do nothing
			} else if (line.matches(">.*") && (header == null)){
				header = line;
			} else if ((header == null) && (sequence == null)) {
				System.out.println("No header found. Skipping line: " + line);
			} else if (line.matches(">.*")){
				Sequence seq = new Sequence();
				seq.setDescription(header);
				if (sequence != null) seq.setSequence(sequence);
				seq.setSequenceId(MemeServerImpl.getKbaseId("Sequence"));
				seqList.add(seq);
				header = line;
				sequence = "";
			} else {
				sequence += line;
			}
			
		}
		Sequence seq = new Sequence();
		seq.setDescription(header);
		seq.setSequenceId(MemeServerImpl.getKbaseId("Sequence"));
		if (sequence != null) seq.setSequence(sequence);
		seqList.add(seq);
		returnVal.setSequences(seqList);
		returnVal.setDescription(description);
		if (name == null) { 
			returnVal.setSequenceSetId(MemeServerImpl.getKbaseId("SequenceSet"));
		} else {
			returnVal.setSequenceSetId(name);
		}
		return returnVal;
		
	}
	protected static List<String> readFile (String fileName) {
		List<String> returnVal = new ArrayList<String>();
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(fileName));
			String line = null;
			while ((line = br.readLine()) != null) {
				returnVal.add(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null )
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return returnVal;
	}

}
