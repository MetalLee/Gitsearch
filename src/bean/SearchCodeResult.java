package bean;

public class SearchCodeResult {
    int total_count;
    
    boolean incomplete_results;
    
    CodeURL[] items;
    
    public int getTotal_count() {
		return total_count;
	}
    
    public CodeURL[] getItems(){
    	return items;
    }
    
    public boolean isIncomplete() {
		return incomplete_results;
	}
}
