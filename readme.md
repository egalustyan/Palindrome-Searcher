Palindrome Searcher
=========================
**"Palindrome Searcher"** is  a web-application, which can calculate closest palindromes of inputted number.
You can read about palindrome on the following pages:
<https://en.wikipedia.org/wiki/Palindromic_number>
<http://mathworld.wolfram.com/PalindromicNumber.html>

**Functionality:**
***
1. A user can input a number of any length (max long is not a limit) into form
2. Program calculate 2 palindromes:
  * closest below user number (“Min” in UI)
  * closest above user number (“Max” in UI)
3. If user entered palindrome, program show dialog/alert, that number is already palindrome, without starting of calculation
4. The card with information about the calculation is adding to history for long calculation
5. A user can invoke a few independent calculations
6. After page reloading history is restoring without interruption of the calculation
7. By clicking on a history value, show that Min and Max palindromes
8. User can on regularly update for history by checking corresponding checkbox
9. If the user leaves the page (close), all calculations will be closed, when session will be expired

Getting Started
--------------------
### Prerequisites
* Git
* JDK 8 or later
* Maven 3.0 or later

### Clone
At first, you can simply clone this repository using git:

    git clone https://github.com/egalustyan/irens.palindrome.git
    cd irens.palindrome

### Build and Run in IDE Intellij Idea

To do this you need to execute the main method in the `katherina.galustyan.testtask.irens.palindrome.Application`
class from your IDE:

* Choose the Spring Boot Application file (search for @SpringBootApplication)
* Right Click on the file and Run as Java Application

### Package into *.war file
#### From Intellij Idea

To create war-file you need to do the following actions:

1. Select "Run" -> "Edit Configurations" menu item
2. Add new configuration for maven and select it
3. Enter to "Command line" field `clean package`
4. Press "Apply" button, close configurations window and run created configuration
5. After packaging, in "target" folder you will see required file like following:
    irens.palindrome-0.0.1-SNAPSHOT.war

#### From command line

To create war-file you need to do the following actions:

1. Run command line
2. Go to folder with current program, where is the specific *.pom file
    cd path/to/folder
3. Run maven command:
    mvn clean package
5. After packaging, in "target" folder you will see required file like following:
    irens.palindrome-0.0.1-SNAPSHOT.war

### Run *.war file
#### From command line:
In console do the following:

1. Go to folder, where is the application war-file
    cd path/to/folder
2. Run java command:
    java -jar irens.palindrome-0.0.1-SNAPSHOT.war
3. Go to some browser and open page by following link:
    http://localhost:8080/

#### From installed web-server

Deploy war-file to web-container by container instruction for deploying and then
go to some browser and open page by following link:
    http://localhost:8080/

About application code source
---------------------------------

    src/ contains the actual Java application
        main/ contains all source code for application
            java/ contains all java code of application
                katherina.galustyan.testtask.irens.palindrome - main package of application
                    Application.java - main class of application
                    config/ contains all configuration classes of application
                    controller/ contains all controllers of application
                    entity/ contains some classes, which instances can be stored
                            (in current realization or in future) in DB
                    init/ contains initialization classes
                    listener/ contains application listeners
                    searcher/ contains almost all classes, which are required for search palindromes process
                             in a multi-threaded environment
                    service/ contains services to get data from DB
                    store/ contains classes, which stores one session information
            resources/ contains main resources for application
                application.properties - this file contains some main settings for application building and working
            webapp/ui/ contains files which are required for view
                    static/ contains jquery, bootstrap and application js and css files
                    view/ contains all jar and xml files, which are used for view building
                        tiles.xml - contains all tiles view definitions
                        layout/ contains layout file for view
                        attr/ contains tiles attributes jar files
        test/ contains tests for application

