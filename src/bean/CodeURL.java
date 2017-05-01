package bean;

public class CodeURL {
	
	String name;
	String path;
	String html_url; //Used for download
	
	public String getURL() {
		return html_url;
	}
	public String getName() {
		return name;
	}
	public String getPath() {
		return path;
	}
}
