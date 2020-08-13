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
                    .getConnection("jdbc:mysql://localhost:3309/library?"
                    	+ " verifyServerCertificate=false&useSSL=true&"
                        + "user=msandbox&password=7334&"
                    	+ "serverTimezone=UTC");
                      
4.Download the files in your own project directory and then try to test your program:

