package us.kbase.meme;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import us.kbase.auth.AuthService;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonClientException;
import us.kbase.common.service.Tuple7;
import us.kbase.common.service.UObject;
import us.kbase.common.service.UnauthorizedException;
import us.kbase.userandjobstate.Results;
import us.kbase.userandjobstate.UserAndJobStateClient;
import us.kbase.workspaceservice.GetObjectOutput;
import us.kbase.workspaceservice.GetObjectParams;

public class MemeServerCallerTest {

	private static final String USER_NAME = "aktest";
	private static final String PASSWORD = "1475rokegi";
	private static final String JOB_SERVICE = "http://140.221.84.180:7083";
	private String sequenceSetId = "testSequenceSet";
	private String workspace = "AKtest";

	@Test
	public void testFindMotifsWithMemeJobFromWs() throws Exception {
		AuthToken token = AuthService.login(USER_NAME, new String(PASSWORD)).getToken();
		
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
		
		//MemeServerCaller.setAuthAllowedForHttp(true);

		String jobId = MemeServerCaller.findMotifsWithMemeJobFromWs(workspace, sequenceSetId, params, token);

		System.out.println("Job ID = " + jobId);
		assertNotNull(jobId);
		
		URL jobServiceUrl = null;
		UserAndJobStateClient client = null;

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
		
		String status = "";
		
		while (!status.equalsIgnoreCase("finished")){
			
			try {
			    Thread.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
			try {
				Tuple7<String,String,String,Long,String,Long,Long> t = client.getJobStatus(jobId); 
				//System.out.println(t.getE1());
				//System.out.println(t.getE2());
				status = t.getE3();
				//System.out.println(t.getE3());//Status
				//System.out.println(t.getE4());
				//System.out.println(t.getE5());
				//System.out.println(t.getE6());
				//System.out.println(t.getE7());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String resultId = null;

		try {
			Results res = client.getResults(jobId);			
			resultId = res.getWorkspaceids().get(0);
			System.out.println("Result ID = " + resultId);
			assertNotNull(resultId);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] resultIdParts = resultId.split("/");
		resultId = resultIdParts[1];
//Read result from WS
		
		GetObjectParams objectParams = new GetObjectParams().withType("MemeRunResult").withId(resultId).withWorkspace(workspace).withAuth(token.toString());   
		GetObjectOutput output = MemeServerImpl.wsClient(token.toString()).getObject(objectParams);
		MemeRunResult result = UObject.transformObjectToObject(output.getData(), MemeRunResult.class);

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

}
