# Gitsearch
Gitsearch is a tool that can help you search code snippets and download mutiple Repositories from [Github](github.com) or other Github enterprise easily just by a few steps. This tool's functionality is based on [Egit](http://www.eclipse.org/egit/).

## Libraries required
1. Gson 2.8.0;
2. SWT 3.105;
3. Egit 2.1.5;

## UI Overview
![](http://i.imgur.com/uH1e8ik.png)

## How to Use
### Step 1: Enter your username and password for authentication:  

### Step 2: Enter the keyword the click the 'Search' button and wait seconds

### Step 3: Select the Repositories you want to download from the table

![](http://i.imgur.com/KeQ1Qz7.png)

### Step 4: Choose a local directory

![](http://i.imgur.com/wJcrHnt.png)

### Step 5: Click 'Download Selected' button
![](http://i.imgur.com/vKzwbPk.png)

### Congratulations! Now the files have been successfully downloaded!

## Bonus: How to compile & edit the tool on Apache NetBeans

### Convert
1. Clone the repo
2. Fire up NetBeans, go to File -> Import project -> Eclipse project
3. Pick "Import Project ignoring Project Dependencies"
	* For "Project to import", pick the repo directory you just cloned
	* For "Destination folder", pick another folder where you'd store the Netbeans project files
4. Click on "Finish"

### Compile 
* Go to Run -> Clean and Build project