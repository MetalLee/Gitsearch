package main;


import java.io.IOException;

import org.eclipse.egit.github.core.Repository;
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



public class Main {
	public static void main(String[] args) throws IOException {
		GithubRepositorySearch search = new GithubRepositorySearch();
		
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Git Search and Download");
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 14;
		shell.setLayout(gridLayout);
		shell.setSize(1000, 600);
		shell.setModified(false);
		
		//Authentication
		GridData inputGridData = new GridData();
		inputGridData.horizontalSpan = 2;
		Label usernameLabel = new Label(shell, SWT.SINGLE);
		usernameLabel.setText("Username:");
		Text usernameInput = new Text(shell, SWT.SINGLE);
		
		usernameInput.setLayoutData(inputGridData);
		usernameInput.setText("461741038@qq.com");
		Label passwordLabel = new Label(shell, SWT.SINGLE);
		passwordLabel.setText("Password:");
		Text passwordInput = new Text(shell, SWT.PASSWORD);
		passwordInput.setLayoutData(inputGridData);
		passwordInput.setText("klee654944");
		//Search option
		Label keywordLabel = new Label(shell, SWT.SINGLE);
		keywordLabel.setText("Keyword:");
		Text keywordInput = new Text(shell, SWT.SINGLE);
		keywordInput.setLayoutData(inputGridData);
		Label languageLabel = new Label(shell, SWT.SINGLE);
		languageLabel.setText("Language:");
		Text languageInput = new Text(shell, SWT.SINGLE);
		languageInput.setLayoutData(inputGridData);
		//Search Button
		Button searchButton = new Button(shell, SWT.PUSH);
		searchButton.setText("Search!");
		
		//Download Button
		Button downloadButton = new Button(shell, SWT.PUSH);
		downloadButton.setText("Download Selected");
		//Directory Selection
		Button directoryButton = new Button(shell, SWT.PUSH);
		directoryButton.setText("Browse");
		GridData directoryGridData = new GridData();
		directoryGridData.horizontalSpan = 2;
		Label directoryLabel = new Label(shell, SWT.SINGLE);
		directoryLabel.setText("Please choose a directory");
		directoryLabel.setLayoutData(directoryGridData);
		
		DirectoryDialog directoryDialog = new DirectoryDialog(shell);
		
		//Result table
		GridData tableGridData = new GridData(GridData.FILL_BOTH);
		tableGridData.horizontalSpan = 14;
		tableGridData.verticalSpan = 6;
		Table table = new Table(shell, SWT.FULL_SELECTION|SWT.MULTI|SWT.VIRTUAL);
		table.setLayoutData(tableGridData);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		String[] tableHeader = {"No.","Repository Name","Owner","Size","Description"};
		for(int i=0;i<tableHeader.length;i++){
			TableColumn tableColumn = new TableColumn(table, SWT.NONE);
			tableColumn.setText(tableHeader[i]);
			if(tableHeader[i].equals("Description"))
				tableColumn.setWidth(500);
			else if(tableHeader[i].equals("No.")||tableHeader[i].equals("Size"))
				tableColumn.setWidth(60);
			else tableColumn.setWidth(150);
			tableColumn.setResizable(false);
		}
		
		//Page button
		Button prevButton = new Button(shell, SWT.PUSH);
		prevButton.setText("Previous");
		Button nextButton = new Button(shell, SWT.PUSH);
		nextButton.setText("Next");
		
		//Page Information Label
		Label pageLabel = new Label(shell, SWT.SINGLE);
		pageLabel.setText("Page:1/1");
		pageLabel.setLayoutData(inputGridData);

		
		Runnable pageUpdate = new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				pageLabel.setText("Page:"+String.valueOf(search.getCurrentPage())+"/"+String.valueOf(search.getMaxPage()));
			}
		};
		//Button Event
		directoryButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				directoryDialog.open();
				new Thread(){
					public void run() {
						display.syncExec(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								if(directoryDialog.getFilterPath().equals(""))
									directoryLabel.setText("Please choose a directory");
								else directoryLabel.setText(directoryDialog.getFilterPath());
							}
						});
					}
				}.start();
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
			}
		});
		searchButton.addMouseListener(new MouseListener() {	
			@Override
			public void mouseUp(MouseEvent arg0) {			
			}		
			@Override
			public void mouseDown(MouseEvent arg0) {
				search.Authentication(usernameInput.getText(), passwordInput.getText());
				try {
					Repository[] repositories ;
					repositories = search.Search(keywordInput.getText(), 1, languageInput.getText());
					if(repositories == null) return ;
					table.setItemCount(repositories.length);
					for(int i=0;i<repositories.length;i++){
						table.getItem(i).setText(new String[]{String.valueOf(i+1), repositories[i].getName(),repositories[i].getOwner().getLogin()
								,String.valueOf(repositories[i].getSize()),repositories[i].getDescription()});
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

			}
		});
		downloadButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {		
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {	
				if(directoryLabel.getText().equals("Please choose a directory")) return ;
				TableItem[] toDownload= table.getSelection();
				for(int i=0;i<toDownload.length;i++){
					try {
						search.DownloadZip(Integer.valueOf(toDownload[i].getText(0)) - 1, directoryLabel.getText());
					} catch (NumberFormatException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
			}
		});
        prevButton.addMouseListener(new MouseListener() {			
			@Override
			public void mouseUp(MouseEvent arg0) {
	
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
			    try {
			    	Repository[] repositories;
					repositories = search.PreviousPageSearch();
					if(repositories == null) return ;
					table.setItemCount(repositories.length);
					for(int i=0;i<repositories.length;i++){
						table.getItem(i).setText(new String[]{String.valueOf(i+1), repositories[i].getName(),repositories[i].getOwner().getLogin()
								,String.valueOf(repositories[i].getSize()),repositories[i].getDescription()});
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
			
			}
		});
		nextButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {

			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
			    try {
			    	Repository[] repositories;
					repositories = search.NextPageSearch();
					if(repositories == null) return ;
					table.setItemCount(repositories.length);
					for(int i=0;i<repositories.length;i++){
						table.getItem(i).setText(new String[]{String.valueOf(i+1), repositories[i].getName(),repositories[i].getOwner().getLogin()
								,String.valueOf(repositories[i].getSize()),repositories[i].getDescription()});
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

			}
		});
		shell.open();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
}