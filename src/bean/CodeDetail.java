package bean;

public class CodeDetail {
	String name;
	
	String path;
	
	String owner;
	
	String repo;
	
	String ref;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public void setRepo(String repo) {
		this.repo = repo;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	public String getName() {
		return name;
	}
	public String getPath() {
		return path;
	}
	public String getOwner() {
		return owner;
	}
	public String getRepo() {
		return repo;
	}
	public String getRef() {
		return ref;
	}
	public String getDownloadURL(){
		
		return ("https://raw.githubusercontent.com/"+owner+"/"+repo+"/"+ref+"/"+path);
	}
}
