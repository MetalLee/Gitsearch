package main;


import java.io.IOException;
import java.net.MalformedURLException;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import bean.CodeDetail;


public class Main {
	public static void main(String[] args) throws IOException {
		
		/* For download test
		GithubCodeDownload githubCodeDownload = new GithubCodeDownload();
		githubCodeDownload.setURL("https://raw.githubusercontent.com/ParkShinyoung/HelloWorld/db859f5ac0d17af8e997ec55664dbd5e995dbc79/src/egit/Egit.java");
		githubCodeDownload.Download();
		*/ 
		GithubCodeDownload githubCodeDownload = new GithubCodeDownload();
		GithubCodeSearch githubCodeSearch = new GithubCodeSearch();
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Git Search and Download");
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 8;
		shell.setLayout(gridLayout);
		shell.setSize(1000, 600);
		shell.setModified(false);
		
		//Authentication
		GridData loginGridData = new GridData(GridData.FILL_HORIZONTAL);
		Label usernameLabel = new Label(shell, SWT.SINGLE);
		usernameLabel.setText("Username:");
		Text usernameInput = new Text(shell, SWT.SINGLE);
		usernameInput.setLayoutData(loginGridData);
		usernameInput.setText("461741038@qq.com");
		Label passwordLabel = new Label(shell, SWT.SINGLE);
		passwordLabel.setText("Password:");
		Text passwordInput = new Text(shell, SWT.PASSWORD);
		passwordInput.setLayoutData(loginGridData);
		passwordInput.setText("klee654944");
		//Search option
		Label keywordLabel = new Label(shell, SWT.SINGLE);
		keywordLabel.setText("Keyword:");
		Text keywordInput = new Text(shell, SWT.SINGLE);
		keywordInput.setLayoutData(loginGridData);
		
		//Search Button
		Button searchButton = new Button(shell, SWT.PUSH);
		searchButton.setText("Search!");
		
		//Download Button
		Button downloadButton = new Button(shell, SWT.PUSH);
		downloadButton.setText("Download Selected");
		//Directory Selection
		Button directoryButton = new Button(shell, SWT.PUSH);
		directoryButton.setText("Browse");
		
		Label directoryLabel = new Label(shell, SWT.SINGLE);
		directoryLabel.setText("Please choose a directory");
		directoryLabel.setLayoutData(loginGridData);
		
		DirectoryDialog directoryDialog = new DirectoryDialog(shell);
		
		//Result table
		GridData tableGridData = new GridData(GridData.FILL_BOTH);
		tableGridData.horizontalSpan = 8;
		tableGridData.verticalSpan = 6;
		Table table = new Table(shell, SWT.FULL_SELECTION|SWT.MULTI|SWT.VIRTUAL);
		table.setLayoutData(tableGridData);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		String[] tableHeader = {"File Name","File Owner","Repository","Download URL"};
		for(int i=0;i<tableHeader.length;i++){
			TableColumn tableColumn = new TableColumn(table, SWT.NONE);
			tableColumn.setText(tableHeader[i]);
			if(tableHeader[i].equals("Download URL"))
				tableColumn.setWidth(500);
			else tableColumn.setWidth(150);
		}
		
		//Page button
		Button prevButton = new Button(shell, SWT.PUSH);
		prevButton.setText("Previous");
		Button nextButton = new Button(shell, SWT.PUSH);
		nextButton.setText("Next");
		
		//Page Information Label
		Label pageLabel = new Label(shell, SWT.SINGLE);
		pageLabel.setText("Page:0/0");
		pageLabel.setLayoutData(loginGridData);
		//UI Update Runnable
		Runnable pageUpdate = new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				pageLabel.setText("Page:"+String.valueOf(githubCodeSearch.getInformation().getCurrentPage())+"/"+String.valueOf(githubCodeSearch.getInformation().getTotalPage()));
			}
		};
		//Button Event
		directoryButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub
				directoryDialog.open();
				System.out.println(directoryDialog.getFilterPath());
				new Thread(){
					public void run() {
						display.syncExec(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								directoryLabel.setText(directoryDialog.getFilterPath());
							}
						});
					}
				}.start();
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		searchButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				if(usernameInput.getText().isEmpty()||passwordInput.getText().isEmpty()||keywordInput.getText().isEmpty())
					return ;
				// TODO Auto-generated method stub	
				String username = usernameInput.getText();
				String password = passwordInput.getText();
				githubCodeSearch.Authentication(username, password);
				//Search Begin

				CodeDetail[] codeDetails;

				try {
					codeDetails = githubCodeSearch.Search(keywordInput.getText());
					table.setItemCount(codeDetails.length);
					for(int i=0;i<codeDetails.length;i++){
						table.getItem(i).setText(new String[]{codeDetails[i].getName(),codeDetails[i].getOwner(),codeDetails[i].getRepo(),codeDetails[i].getDownloadURL()});
					} 
					new Thread(){
						public void run() {
							display.syncExec(pageUpdate);
						}
					}.start();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
					
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        downloadButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(githubCodeDownload.getSavePath() == null) return ;
				TableItem[] toDownload= table.getSelection();
				for(int i=0;i<toDownload.length;i++){
					try {
						githubCodeDownload.setFileName(toDownload[i].getText(0));
						githubCodeDownload.setSavePath(directoryLabel.getText());
					    githubCodeDownload.setURL(toDownload[i].getText(3));
					    boolean downloadSuccess = githubCodeDownload.Download();
					    if(downloadSuccess){
					    	toDownload[i].setBackground(display.getSystemColor(SWT.COLOR_GREEN));
					    }
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		nextButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(githubCodeSearch.getInformation().getCurrentPage() >= githubCodeSearch.getInformation().getTotalPage()) return;
				table.removeAll();
				try {
					CodeDetail[] codeDetails = githubCodeSearch.NextPageSearch();
					table.setItemCount(codeDetails.length);
					for(int i=0;i<codeDetails.length;i++){
						table.getItem(i).setText(new String[]{codeDetails[i].getName(),codeDetails[i].getOwner(),codeDetails[i].getRepo(),codeDetails[i].getDownloadURL()});
					}
					new Thread(){
						public void run() {
							display.syncExec(pageUpdate);
						}
					}.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        prevButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(githubCodeSearch.getInformation().getCurrentPage() <= 1) return ;
				table.removeAll();
				try {
					CodeDetail[] codeDetails = githubCodeSearch.PrepPageSearch();
					table.setItemCount(codeDetails.length);
					for(int i=0;i<codeDetails.length;i++){
						table.getItem(i).setText(new String[]{codeDetails[i].getName(),codeDetails[i].getOwner(),codeDetails[i].getRepo(),codeDetails[i].getDownloadURL()});
					}
					new Thread(){
						public void run() {
							display.syncExec(pageUpdate);
						}
					}.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        shell.open();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
}
