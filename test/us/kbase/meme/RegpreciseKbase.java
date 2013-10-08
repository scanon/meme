package us.kbase.meme;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import us.kbase.util.WSUtil;

public class RegpreciseKbase {

	/**
	 * @param args
	 */

	private static MemePSPMCollection parseRegpreciseFile (List<String> regpreciseFile) {
		MemePSPMCollection result = new MemePSPMCollection();
		Date date = new Date();
		
		result.setAlphabet("ACGT");
		result.setDescription("RegPrecise 3 motif collection");
		result.setId("RegPreciseMotifs_20131006");
		result.setTimestamp(String.valueOf(date.getTime()));
		List<MemePSPM> pspms = new ArrayList<MemePSPM>();
		Boolean motifLine = false;
		Boolean notHeader = false;
		List<String> motifLines = new ArrayList<String>();
		
		for (String line: regpreciseFile){
			if (line.equals("")){
			}
			else if (line.contains("A 0.25000 C 0.25000 G 0.25000 T 0.25000")){
				notHeader = true;
				System.out.println("Header finished!");
			}
			else {
				if (notHeader == true){
					if (line.matches("MOTIF.+")){
						System.out.println("Motif found: "+ line);
					
						if (motifLine){
							pspms.add(makePspm (motifLines));
							motifLines.clear();
							motifLines.add(line);
						}
						else {
							motifLine = true;
							motifLines.add(line);
						};
					}
					else {
						motifLines.add(line);
					};

				};
			};
		}
		result.setPspms(pspms);
		
		return result;
	}
		
	private static MemePSPM makePspm (List<String> lines){
		MemePSPM result = new MemePSPM();
		result.setAlphabet("ACGT");
		result.setDescription("Exported from RegPrecise");
		result.setSourceType("RegPrecise motif");
		List<List<Double>> matrix = new ArrayList<List<Double>>();
		for (String line: lines) {
			if (line.equals("")){
				//do nothing;
			} 
			else if (line.matches("^MOTIF .*")){
				result.setId(line.substring(6));
				result.setSourceId(line.substring(6));
			}
			else if (line.matches("letter-probability matrix: alength=.*")){
				result.setWidth(Integer.parseInt(line.substring(line.indexOf("w= ")+3, line.indexOf("nsites=")-1)));
				result.setNsites(Integer.parseInt(line.substring(line.indexOf("nsites= ")+8, line.indexOf("E= ")-1)));
				result.setEvalue(Double.parseDouble(line.substring(line.indexOf("E= ")+3)));
			}
			else {
				String[] matrixTextRow = line.split(" ");
				List<Double> matrixRow = new ArrayList<Double>();
				for (String matrixTextValue: matrixTextRow){
					if(!matrixTextValue.equals(""))	matrixRow.add(Double.parseDouble(matrixTextValue));
				}
				matrix.add(matrixRow);
			}
		}
		result.setMatrix(matrix);
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		String regpreciseFileName = "test/regprecise.meme";
		List<String> regpreciseFile = new ArrayList<String>();
		
		//open file
		try {
			BufferedReader textreader = new BufferedReader(new FileReader(regpreciseFileName));
			String line = new String();
			while ((line = textreader.readLine())!=null){
				regpreciseFile.add(line);
			}
			textreader.close();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		} finally {
			
		}

		//parse, make pspms, compile collection
		MemePSPMCollection collection = parseRegpreciseFile (regpreciseFile);

		//write to ws
		WSUtil.saveObject(collection.getId(), collection, false);

	}

}
