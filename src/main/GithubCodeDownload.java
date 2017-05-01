package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GithubCodeDownload {
	URL url;
	
	String savePath = null;
	
	String fileName ;
	
	GithubCodeDownload() {
	}
	public void setURL(String url) throws MalformedURLException {
		this.url = new URL(url);	
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public boolean Download() throws IOException {
		//HttpURLConnection connection = (HttpURLConnection)url.openConnection();		
		boolean success = false;
		HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
		connection.setConnectTimeout(15*1000);
		connection.setRequestProperty("USER_AGENT", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		InputStream inputStream = connection.getInputStream();
		byte[] getData = new byte[inputStream.available()];
		
		inputStream.read(getData, 0, getData.length);
		
         
        File saveDir = new File(savePath);  
        if(!saveDir.exists()){  
            saveDir.mkdir();  
        }  
        File file = new File(saveDir+File.separator+fileName);      
        FileOutputStream fos = new FileOutputStream(file);       
        fos.write(getData);   
        if(fos!=null){  
            fos.close();    
        }  
        if(inputStream!=null){  
            inputStream.close();  
        }  
        success = true;
        return success;
	}
	
	public String getSavePath() {
		return savePath;
	}
}
