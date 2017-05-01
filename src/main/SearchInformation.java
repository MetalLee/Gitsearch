package main;

public class SearchInformation {
	String query;
	
	int currentPage;
	
	int totalPage;
	
	int itemCount;
	
	String language;
	
	public SearchInformation(String q,int cp,int tp,int itemCount,String language) {
		// TODO Auto-generated constructor stub
		this.itemCount = itemCount;
		this.query = q;
		this.currentPage = cp;
		this.totalPage = tp;
		this.language = language;
	}
	
	public void nextPage(){
		if(currentPage<totalPage)
			currentPage++;
	}
	
	public void prepPage() {
		if(currentPage>1){
			currentPage--;
		}
	}
	
	public String getQuery() {
		return query;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	public int getTotalPage() {
		return totalPage;
	}
	public int getItemCount() {
		return itemCount;
	}
	public String getLanguage() {
		return language;
	}
	
}
