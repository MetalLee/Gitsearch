package main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.GitHubRequest;
import org.eclipse.egit.github.core.client.GitHubResponse;
import bean.CodeDetail;
import bean.CodeURL;
import bean.SearchCodeResult;

public class GithubCodeSearch {
	final String PER_PAGE = "100";
	
	GitHubClient client;
	
	GitHubRequest request;
	
	GitHubResponse response;
	
	SearchInformation information;
	
	int page;
	
	String language = "Java";
		
	public GithubCodeSearch(){
		client = new GitHubClient();
		request = new GitHubRequest();
		request.setUri("/search/code");
		request.setResponseContentType("application/json");
		request.setType(SearchCodeResult.class);
	}
	
	public CodeDetail[] Search(String query) throws IOException{
		Map<String,String> params = new HashMap<>();
		params.put("q", query+" language:"+language);
		params.put("per_page", PER_PAGE);
		request.setParams(params);
		response = client.get(request);
		SearchCodeResult result = null;
		if(response.getBody()!=null){
			result = (SearchCodeResult) response.getBody();
		}
		information = new SearchInformation(query, 1, result.getTotal_count()/100, result.getTotal_count(),"Java");
		CodeURL[] codeURLs= result.getItems();
		CodeDetail[] codeDetail = new CodeDetail[codeURLs.length];
		for(int i=0;i<codeURLs.length;i++){
			codeDetail[i] = new CodeDetail();
			String[] splitted = codeURLs[i].getURL().split("/");
			codeDetail[i].setName(codeURLs[i].getName());
			codeDetail[i].setPath(codeURLs[i].getPath());
			codeDetail[i].setOwner(splitted[3]);
			codeDetail[i].setRef(splitted[6]);
			codeDetail[i].setRepo(splitted[4]);
		}
		/*
		InputStream inputStream = client.getStream(request);
		System.out.println(CharStreams.toString(new InputStreamReader(inputStream)));
		*/
		return codeDetail;
	}
	
	public void Authentication(String user,String password) {
		client.setCredentials(user, password);
	}
	public void setLanguage(String language){
		this.language = language;
	}
	
    public SearchInformation getInformation() {
		return information;
	}
	public CodeDetail[] NextPageSearch() throws IOException{
		information.nextPage();
		Map<String,String> params = new HashMap<>();
		params.put("q", information.getQuery()+" language:"+information.getLanguage());
		params.put("page", String.valueOf(information.getCurrentPage()));
		params.put("per_page", PER_PAGE);
		request.setParams(params);
		response = client.get(request);
		SearchCodeResult result = null;
		if(response.getBody()!=null){
			result = (SearchCodeResult) response.getBody();
		}
		CodeURL[] codeURLs= result.getItems();
		CodeDetail[] codeDetail = new CodeDetail[codeURLs.length];
		for(int i=0;i<codeURLs.length;i++){
			codeDetail[i] = new CodeDetail();
			String[] splitted = codeURLs[i].getURL().split("/");
			codeDetail[i].setName(codeURLs[i].getName());
			codeDetail[i].setPath(codeURLs[i].getPath());
			codeDetail[i].setOwner(splitted[3]);
			codeDetail[i].setRef(splitted[6]);
			codeDetail[i].setRepo(splitted[4]);
		}
		return codeDetail;
	}
	
	public CodeDetail[] PrepPageSearch() throws IOException{
		information.prepPage();
		Map<String,String> params = new HashMap<>();
		params.put("q", information.getQuery()+" language:"+information.getLanguage());
		params.put("page", String.valueOf(information.getCurrentPage()));
		params.put("per_page", PER_PAGE);
		request.setParams(params);
		response = client.get(request);
		SearchCodeResult result = null;
		if(response.getBody()!=null){
			result = (SearchCodeResult) response.getBody();
		}
		CodeURL[] codeURLs= result.getItems();
		CodeDetail[] codeDetail = new CodeDetail[codeURLs.length];
		for(int i=0;i<codeURLs.length;i++){
			codeDetail[i] = new CodeDetail();
			String[] splitted = codeURLs[i].getURL().split("/");
			codeDetail[i].setName(codeURLs[i].getName());
			codeDetail[i].setPath(codeURLs[i].getPath());
			codeDetail[i].setOwner(splitted[3]);
			codeDetail[i].setRef(splitted[6]);
			codeDetail[i].setRepo(splitted[4]);
		}
		return codeDetail;
	}
}
