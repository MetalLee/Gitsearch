package main;

import org.eclipse.egit.github.core.Repository;

public class SearchResult {
    int total_count;
    
    boolean incomplete_results;
    
    Repository[] items;
    
    public int getTotal_count() {
		return total_count;
	}
    
    public Repository[] getItems(){
    	return items;
    }
    
    public boolean isIncomplete() {
		return incomplete_results;
	}
}
