# CS410
A report of “lessons learned” and representative errors you recovered from.

Any directions to help the instructor install or operate the application.
# How To Run
1.check if you are connected your java database using port forwarding.We used mobaXterm:


<a href="https://ibb.co/4NmpjF6"><img src="https://i.ibb.co/2KndjFD/dbconnect.png" alt="dbconnect" border="0"></a>

2.Run the play button to see if it is running properly:

<a href="https://ibb.co/VvZwcmh"><img src="https://i.ibb.co/MR4Bx6j/tunneling-test.png" alt="tunneling-test" border="0"></a><br /><a target='_blank' href='https://imgbb.com/'>online pic share</a><br />

3.Now go to your java mysqlaccess file and try to match your port and password to what you have setup on your onyx.

        	// TO use MySQL remotely (e.g., your laptop to onyx)
        	// Replace <var2> and <var4> with your own data (from presentation slide)
			connect = DriverManager
                    .getConnection("jdbc:mysql://localhost:3309/Library?"
                    	+ " verifyServerCertificate=false&useSSL=true&"
                        + "user=msandbox&password=7334&"
                    	+ "serverTimezone=UTC"); 
                      
4. Connect to your database using your username and password you setup for onyx.
5.open a sql query tab and paste DDL.txt and DML.txt in your query and run it.
6.Import modules.sql as well in your database and run it.
7.Now go back to your java IDE and run the program.

8.For each case in the menu use the appropriate command line argument(Can be entered manually in eclipse or in console):

	Usage : <bookId,authorName,title,publisherName,bookCopyId,numCopies,branchId>
        ex: 200001 "Mary Ellen Season" "Honeysuckle Season" "Pacific Press" 30000002 14 106        	 

        Usage: <bookLoanId,dateOut ,branchId ,bookId ,cardNo>  ex : 1 2020-09-25 101 100001 1001"
            	
            	
        Usage: <branchId ,bookId ,cardNo> ex : 101 100001 1001
           
        Usage:<libraryBranch> ex : "Main Library" 
                        	
        Usage:<borrower> ex: "Suzanne Viescas" 
	            	
        Usage: <bookTitle, libraryBranch> ex: "Intro to Computers"  "Library! at Bown Crossing" 
	
ot setup Command line argument for each option(linux):

to compile : javac *.java 

to run:

* option 1: java Main.java 200001 "Mary Ellen Season" "Honeysuckle Season" "Pacific Press" 30000002 14 106
* option 2: java Main.java 1 2020-09-25 101 100001 1001
* option 3: java Main.java 101 100001 1001
* option 4: java Main.java "Main Library"
* option 5: java Main.java Suzanne Viescas
* option 6: java Main.java "Intro to Computers"  "Library! at Bown Crossing"

## ***** Important ******
To reset the database to the default state after each run use these queries:


    DELETE FROM `Library`.`Publishers` WHERE (`Name_` = 'Anna');
    DELETE FROM `Library`.`Borrowers` WHERE (`CardNo` = '31');
    DELETE FROM `Library`.`Library_Branches` WHERE (`BranchId` = '6');
    DELETE FROM `Library`.`Book_Copies` WHERE (`BookCopyId` = '30000002');
    DELETE FROM `Library`.`Books` WHERE (`BookId` = '200001');	

 ## Results
 
 Run these queries for each of the options:
 
 * option 1:   select * from books;        
<a href="https://imgbb.com/"><img src="https://i.ibb.co/j623D2S/option1-addbook.png" alt="option1-addbook" border="0"></a>   
	     select * from book_copies;    
<a href="https://imgbb.com/"><img src="https://i.ibb.co/52shPY3/option1-addbookcopies.png" alt="option1-addbookcopies" border="0"></a>  
	     
 * option 2: select * from book_loans;    
 <a href="https://imgbb.com/"><img src="https://i.ibb.co/MSG1Kxj/option2.png" alt="option2" border="0"></a>
	     
 * option 3: select * from book_loans; ( to check the entry is gone)\
<a href="https://imgbb.com/"><img src="https://i.ibb.co/6P8hxSN/option3.png" alt="option3" border="0"></a>
 
 * option 4:   
 <a href="https://imgbb.com/"><img src="https://i.ibb.co/0jKkp6Y/java-option4.png" alt="java-option4" border="0"></a>  
 * option 5:   
<a href="https://imgbb.com/"><img src="https://i.ibb.co/zZk3n5Y/java-option5.png" alt="java-option5" border="0"></a>  
 * option 6:  
<a href="https://imgbb.com/"><img src="https://i.ibb.co/6YPzsys/java-option6.png" alt="java-option6" border="0"></a>  
 












