package sqlaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.sql.CallableStatement;

public class MySQLAccess {
	private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public void readDataBase(String[] args) throws Exception {
        try {
        	System.out.println("Starting..."); //
        	
        	// TO use MySQL remotely (e.g., your laptop to onyx)
        	// Replace <var2> and <var4> with your own data (from presentation slide)
			connect = DriverManager
                    .getConnection("jdbc:mysql://localhost:3309/library?"
                    	+ " verifyServerCertificate=false&useSSL=true&"
                        + "user=msandbox&password=7334&"
                    	+ "serverTimezone=UTC"); 
			

            // Statement methods allow you to issue SQL queries to the database
			// Statements contrast with preparedStatements which you'll see below
            statement = connect.createStatement();
            

            // PreparedStatements can use variables and are more efficient AND MORE SECURE!
            String tablePublisher="publishers";
            String tableLibBranches="library_branches";
            String tableBorrowers="borrowers";
            
            

      	
                //insert into  Publishers
                
                preparedStatement = connect.prepareStatement("insert into  publishers values (?, ?, ?)");
                
                preparedStatement.setString(1, "Anna");
                preparedStatement.setString(2, "265 Boise");
                preparedStatement.setString(3, "545-0067");


                if(preparedStatement.executeUpdate()==1) {
                	System.out.println("Row inserted into publisher...");
                }
       
                //insert into borrowers
                
                preparedStatement = connect.prepareStatement("insert into  borrowers values (?, ?, ?, ?)");
          
                preparedStatement.setString(1, "31");
                preparedStatement.setString(2, "Sam");
                preparedStatement.setString(3, "277 Boise");
                preparedStatement.setString(4, "277-7767");
            
                
                if(preparedStatement.executeUpdate()==1) {
                	System.out.println("Row inserted into borrowers...");
                }
                
                //insert into  Library_Branches  
                
                preparedStatement = connect.prepareStatement("insert into  library_branches values (?, ?)");
                
                preparedStatement.setInt(1, 6);
                preparedStatement.setString(2, "BSU");          
              
                if(preparedStatement.executeUpdate()==1) {
                	System.out.println("Row inserted into library_branches...");
                }
            
          
            	 //Select Library branches
                
            	  System.out.println("-------------Library branches-------------");
                  
                  String query1=SelectQueries(tableLibBranches);
                  resultSet = statement.executeQuery(query1);
                  
                  writeResultSetLibBranch(resultSet);
                  
                 //Select Publishers
                  
                  System.out.println("--------------Publishers-------------------");
                  
                  String query2=SelectQueries(tablePublisher);
                  resultSet = statement.executeQuery(query2);
                  
                  writeResultSetPublisher(resultSet);
                  
                 //Select Borrowers
                  
                  System.out.println("--------------------------Borrowers------------------------");
                  
                  String query3=SelectQueries(tableBorrowers);
                  resultSet = statement.executeQuery(query3);
                  
                  writeResultSetBorrower(resultSet);
                  
                  //Menu to choose from    
                  menu(args);
                          
            // Remove the inserted row
            
//            preparedStatement = connect
//            .prepareStatement("delete from DEMO_DEPT where DEPTNO=70;");
//            int resultSet2 = preparedStatement.executeUpdate();
            
        } catch (Exception e) {
            throw e;
        } finally {
            close();
            System.out.println("Reached close");
        }
    }
       
    private void addBook(Connection connect, int BookId,String AuthorName,String Title,String Name_) throws Exception {

		PreparedStatement stmt = null;

		try {
			stmt = connect.prepareStatement("call add_Book(?,?,?,?)");

			//stmt.setString(1, Publisher);
			stmt.setInt(1,BookId);
			stmt.setString(2,AuthorName);
			stmt.setString(3,Title);
			stmt.setString(4,Name_);
			
			stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void addBookCopies(Connection connect, int BookCopyID_,int NoOfCopies_,int BookID_,int BranchId) throws Exception {

		PreparedStatement stmt = null;

		try {
			stmt = connect.prepareStatement("call add_BookCopies(?,?,?,?)");

			//stmt.setString(1, Publisher);
			stmt.setInt(1,BookCopyID_);
			stmt.setInt(2,NoOfCopies_);
			stmt.setInt(3,BookID_);
			stmt.setInt(4,BranchId);
			
			stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    private void checkOut(Connection connect, long bookloan_ID,String date_Out,String due_Date ,long branch_ID ,long book_ID ,long Card_No ) throws Exception {

		PreparedStatement stmt = null;

		
		DateFormat df = new SimpleDateFormat("YYYY/MM/DD");
		Date dateobj = new Date();
		System.out.println(df.format(dateobj));

		try {
			stmt = connect.prepareStatement("call check_out(?,?,?,?,?,?)");

			//stmt.setString(1, Publisher);
			stmt.setLong(1,bookloan_ID);
			stmt.setString(2,date_Out);
			stmt.setString(3,due_Date);
			stmt.setLong(4,branch_ID);
			stmt.setLong(5,book_ID);
			stmt.setLong(6,Card_No);
			
			stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void addBookLoans(Connection connect, long bookloan_ID,String due_Date ,long branch_ID ,long book_ID ,long Card_No ) throws Exception {

		PreparedStatement stmt = null;

		try {
			stmt = connect.prepareStatement("call add_BookLoans(?,?,?,?,?,?)");

			//stmt.setString(1, Publisher);
			stmt.setLong(1,bookloan_ID);
			stmt.setString(2,  null);
			stmt.setString(3,due_Date);
			stmt.setLong(4,branch_ID);
			stmt.setLong(5,book_ID);
			stmt.setLong(6,Card_No);
			
			stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void returnBook(Connection connect, long BranchID_ , long BookID_, long CardNo_ ) throws Exception {

		PreparedStatement stmt = null;

		try {
			stmt = connect.prepareStatement("call return_Book(?,?,?)");

			
			stmt.setLong(1,BranchID_);
			stmt.setLong(2,BookID_);
			stmt.setLong(3,CardNo_);
			
			stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void displayLibBranchBooks(Connection connect,String branch) throws Exception {

    	Statement stmt = connect.createStatement();
		ResultSet rs = null;
		try {
			System.out.println("-------------All books from "+branch+" branch---------------");
			rs = stmt.executeQuery("select * from displaybooks_atlibbranches where BranchName='"+branch+"';");
			
			while (rs.next()) {
		        String br = rs.getString("BranchName");
		        String N = rs.getString("Title");
		        Long BID = rs.getLong("BookID");
		        System.out.println("branchName: "+br+"\t");
		        System.out.println("Title: "+N+"\t");
		        System.out.println("BookID: "+BID+"\n");
		    }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    private void borrowersBook(Connection connect,String borrower) throws Exception {

    	Statement stmt = connect.createStatement();
		ResultSet rs = null;
		try {
			System.out.println("-------------List of the books checked out by "+borrower+"-----------------");
			rs = stmt.executeQuery("select * from Borrower_BookCheckOut where Borrower='"+borrower+"';");
			
			while (rs.next()) {
		        String BID = rs.getString("BookID");
		        String BT = rs.getString("BookTitle");
		        String Br = rs.getString("Borrower");
		        System.out.println("BookID: "+BID+"\t");
		        System.out.println("BookTitle: "+BT+"\t");
		        System.out.println("BookTitle: "+Br+"\n");
		    }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void returnCopiesNumber(Connection connect,String bookTitle,String libraryBranch) throws Exception {

		ResultSet rs = null;
		try {
			   CallableStatement stmt = connect.prepareCall("{? = call return_copiesNumber(?,?)}");
			    
			   //setting up the return value
			   stmt.registerOutParameter(1, Types.SMALLINT);
			   //Setting the input parameters of the function
			   stmt.setString(2, bookTitle);
			   stmt.setString(3, libraryBranch);
		
			   stmt.execute();
			   
		       String result=stmt.getString(1);
		       System.out.println("Number of copies for book '"+bookTitle+"' is "+result);
			   

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
            private void writeResultSetPublisher(ResultSet resultSet) throws SQLException {
                // ResultSet is initialized before the first data set
                while (resultSet.next()) {
                	// It is possible to get the columns via name
                    // also possible to get the columns via the column number
                    // which starts at 1
                	String PublisherName = resultSet.getString("Name_");
                	String PublisherAdress = resultSet.getString("Address");
                	String PublisherPhone = resultSet.getString("Phone");               	              	
                	System.out.print("Name: "+ PublisherName + "\t");
                	System.out.print("Address: "+ PublisherPhone+ "\t");
                	System.out.print("Phone: "+PublisherPhone+"\n");
                }
            }
            

  
            private void writeResultSetBorrower(ResultSet resultSet) throws SQLException {
            	
           	 while (resultSet.next()) {
                   
                	int BorrowerCardNo = resultSet.getInt("CardNo");
                	String  BorrowerName= resultSet.getString("Name_");
                	String BorrowerAdress = resultSet.getString("Address");
                	String BorrowerPhone = resultSet.getString("Phone");
                	
               	    System.out.print("CardNo: "+ BorrowerCardNo + "\t");
                	System.out.print("Name: "+ BorrowerName + "\t");
                	System.out.print("Address: "+  BorrowerAdress+ "\t");
                	System.out.print("Phone: "+BorrowerPhone+"\n");
                	}       	
           	
           }
           private void writeResultSetLibBranch(ResultSet resultSet) throws SQLException {
           	
          	 while (resultSet.next()) {
                  
               	int BranchId = resultSet.getInt("BranchId");
               	String  BranchName= resultSet.getString("BranchName");
               	
               	System.out.print("BranchId: "+ BranchId + "\t");
               	System.out.print("BranchName: "+  BranchName+ "\n");
               	
               	}
          	
          	
          	
          }
           
           
         
           private String SelectQueries(String tableName) {
           	String query = "";
           		query="select * from "+tableName+";" ;
           	return query;
           }
           
           
 
            public void menu(String[] args) throws Exception {
            	
                   
            	if(args.length > 0) {
            		
            	
            	showMenu();           	
            	System.out.print("\n Enter the option from menu:");
            	Scanner scan=new Scanner(System.in);
            	int menuEntry=scan.nextInt();
            	
            	switch(menuEntry) {
            	
            	
               case 1: 
                	if( args.length>= 3) {
                		
                	System.out.println("argument 1"+args[0]);
                	System.out.println("argument 1"+args[1]);
                	System.out.println("argument 1"+args[2]);

            	  // addBook(connect,200001,"Saman Rastgar","Big Boy","Pacific Press");
            	  //  addBookCopies(connect,30000002,14,100001,106);
            	    
                	}
                	else {
                        NotValidNumArguments(args);
                    }
            	    break;
            	    
                case 2:
                	if( args.length== 5) {
            		System.out.println("Book checked out from library");
            		
            		int bookLoanId=Integer.parseInt(args[0]);
            		String dateOut=args[1];
            		int branchId=Integer.parseInt(args[2]);
            		int bookId=Integer.parseInt(args[3]);
            		int cardNo=Integer.parseInt(args[4]);
            		
            		addBookLoans(connect, bookLoanId,dateOut ,branchId ,bookId ,cardNo);
            		//cmd : 1 2020-09-25 101 100001 1001
            	    //addBookLoans(connect, 1,"2020-09-25" ,101 ,100001 ,1001);
                	}
                	else {
                        NotValidNumArguments(args);
                     }
            	    break;
                case 3: 
                	
                	if( args.length== 3) {
            		System.out.println("Book returned to the library");
            		int branchId=Integer.parseInt(args[0]);
            		int bookId=Integer.parseInt(args[1]);
            		int cardNo=Integer.parseInt(args[2]);
            		
            		returnBook(connect,branchId ,bookId ,cardNo);
            	    //returnBook(connect,101,100001,1001);
                	}
                	else {
                      NotValidNumArguments(args);
                    }            	    
                	break;
                	
                case 4:
                	if( args.length== 1) {
            		System.out.println("Displaying the book at this library branch");
            		String libraryBranch=args[0];
            		displayLibBranchBooks(connect,libraryBranch);
            	    //displayLibBranchBooks(connect,"Main Library");
                	}
                	else {
                      NotValidNumArguments(args);
                    }
            	    break;
                case 5:
                	if(args.length==1) {
            		System.out.println("Display the books checked out to this borrower.");
            		String borrower=args[0];
            		borrowersBook(connect,borrower);
                 	//borrowersBook(connect,"Suzanne Viescas");
                	}
                	else {
                        NotValidNumArguments(args);
                    }
                 	break;
                case 6:
                	if(args.length==2) {
            		System.out.println("number of copies from this branch");
            		String bookTitle=args[0];
            		String libraryBranch=args[1];
            		returnCopiesNumber(connect,bookTitle, libraryBranch);
            	    //returnCopiesNumber(connect,"Intro to Computers", "Library! at Bown Crossing");
                	}
            	    else {
                        NotValidNumArguments(args);
                    }
            	    break;
            	default:
            		System.out.println("The number is not valid");
            	}
            	
            	
            	
            	
            	            	
            	
            	scan.close();
            	
            	
            	}
            	
            	else {
            		System.out.println("Enter the appropriate arguments in the program");
            	}
                
               
                
              
                
            }
            private void showMenu() {
            	
            	System.out.println();
                System.out.println("(1)Add a new book to the system.");
            	System.out.println("(2)Check out a book from a library branch.");
            	System.out.println("(3)Return a book to library branch.");
            	System.out.println("(4)Display the books at a library branch.");
            	System.out.println("(5)Display the books checked out to a borrower.");
            	System.out.println("(6)Select a book, and list the number of copies checked out from each branch.");
            }
            
            private void NotValidNumArguments(String[] args) {
            	   
            	if(args.length==0){
               		System.out.println("Arguments can't be empty");
               	}
               	else {
               		System.out.println("Enter valid amount of arguments");
               	}
            }
         
        	
            
            // You need to close the resultSet
            private void close() {
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }

                    if (statement != null) {
                        statement.close();
                    }

                    if (connect != null) {
                        connect.close();
                    }
                } catch (Exception e) {

                }
            }       
}
