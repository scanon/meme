package us.kbase.util;


public class IdService {
	public static String getKbaseId(String entityType) {
		String returnVal = null;
		if (entityType.contains("MotifCollectionMeme")) {
			returnVal = "KBase.MotifCollectionMeme." + String.valueOf(System.currentTimeMillis());
		} else if (entityType.contains("MotifMeme")) {
			returnVal = "KBase.MotifMeme." + String.valueOf(System.currentTimeMillis());
		} else if (entityType.contains("SiteMeme")) {
			returnVal = "KBase.SiteMeme." + String.valueOf(System.currentTimeMillis());
		} else if (entityType.contains("CmonkeyRun")) {
			returnVal = "KBase.CmonkeyRun." + String.valueOf(System.currentTimeMillis());
		} else if (entityType.contains("CmonkeyNetwork")) {
			returnVal = "KBase.CmonkeyNetwork." + String.valueOf(System.currentTimeMillis());
		} else if (entityType.contains("CmonkeyCluster")) {
			returnVal = "KBase.CmonkeyCluster." + String.valueOf(System.currentTimeMillis());
		} else {
		}
		return returnVal;
	}

}
