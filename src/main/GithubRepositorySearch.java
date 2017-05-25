package main;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.GitHubRequest;
import org.eclipse.egit.github.core.client.GitHubResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.egit.github.core.Repository;;

public class GithubRepositorySearch {
    
	GitHubClient client;
	
	GitHubRequest request;
	
	GitHubResponse response;	
	
	final String PER_PAGE = "100";
	
	Repository[] repositories;
	
	int maxPage;
	
	int currentPage;
	
	String currentQuery;
	
	String currentLanguage ;
	
    //Constructor
    public GithubRepositorySearch() {
    	client = new GitHubClient();
    	request = new GitHubRequest();
    	request.setUri("/search/repositories");
    	request.setResponseContentType("application/json");
    	request.setType(SearchResult.class);
	}
    //Authentication
    public GithubRepositorySearch Authentication(String user,String password) {
    	client.setCredentials(user, password);
		return this;
	}
    
    //Search,return a array of repositories
    public Repository[] Search(String query,int page,String language) throws IOException{
    	Map<String, String> params = new HashMap<>();
    	//query
    	if(language.isEmpty())
    	   params.put("q", query);
    	else 
    	   params.put("q", query+" language:"+ language);
    	//Result per page
    	params.put("per_page",PER_PAGE);
    	//Page to show
    	params.put("page", String.valueOf(page));
    	request.setParams(params);
    	response = client.get(request);
    	SearchResult result = null;
    	if(response.getBody()!=null){
    		result = (SearchResult)response.getBody();
    	}
    	currentPage = page;
    	maxPage = result.getTotal_count()/100+1;
    	currentQuery = query;
    	currentLanguage = language;
    	repositories = result.getItems();
    	return repositories;
    }
    
    //Download the i-th repository in the array with zip form
    public void DownloadZip(int index,String savePath) throws IOException {
    	if(index >= repositories.length) return ;
    	
    	HttpDownload download = new HttpDownload();
    	download.setURL("https://api.github.com/repos/"+ repositories[index].getOwner().getLogin() + "/" 
    			+ repositories[index].getName() + "/zipball/");
    	download.setFileName(repositories[index].getOwner().getLogin() + "-" + repositories[index].getName()+".zip");
		download.setSavePath(savePath);
		System.out.println("Downloading...");
		download.Download();
		System.out.println("Download success!");
	}
    
    //Turn to the next page
    public Repository[] NextPageSearch() throws IOException{
    	if(currentPage+1 > maxPage) return null; 
    	Map<String, String> params = new HashMap<>();
    	//query
    	if(currentLanguage.isEmpty())
    	   params.put("q", currentQuery);
    	else 
    	   params.put("q", currentQuery+" language:"+ currentLanguage);
    	//Result per page
    	params.put("per_page",PER_PAGE);
    	//Page to show
    	params.put("page", String.valueOf(currentPage+1));
    	request.setParams(params);
    	response = client.get(request);
    	SearchResult result = null;
    	if(response.getBody()!=null){
    		result = (SearchResult)response.getBody();
    	}
    	currentPage = currentPage + 1;
    	repositories = result.getItems();
    	return repositories;
    }
    
    //Turn to the previous page
    public Repository[] PreviousPageSearch() throws IOException{
    	if(currentPage-1 < 1) return null; 
    	Map<String, String> params = new HashMap<>();
    	//query
    	if(currentLanguage.isEmpty())
    	   params.put("q", currentQuery);
    	else 
    	   params.put("q", currentQuery+" language:"+ currentLanguage);
    	//Result per page
    	params.put("per_page",PER_PAGE);
    	//Page to show
    	params.put("page", String.valueOf(currentPage-1));
    	request.setParams(params);
    	response = client.get(request);
    	SearchResult result = null;
    	if(response.getBody()!=null){
    		result = (SearchResult)response.getBody();
    	}
    	currentPage = currentPage - 1;
    	repositories = result.getItems();
    	return repositories;
    }
    
    public int getCurrentPage() {
		return currentPage;
	}
    
    public int getMaxPage() {
		return maxPage;
	}
}
