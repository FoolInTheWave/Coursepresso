Coursepresso
============

Course schedule management software built using the following technologies:

* Java 8
* JavaFX
* Maven
* Spring
* Spring Data JPA
* Spring Security
* Hibernate
* Guava Collections
* Apache Commons
* Simple Logging Facade for Java (SLF4J)

You MUST have Java 8 update 40 or higher to run this program!

This is the client application in the Coursepresso course schedule management software system.  This client application uses services from the server application (Coursepresso Server), which are served from a Tomcat server.  This application logs to both the console (when running in an IDE) and a file.  The log file will be created in the same directory as the JAR file if not already created.  The log file name is “coursepresso.log” (without quotes).

The client connects to the Tomcat instance using HTTPS, and thus requires that you install an SSL certificate on any computer running this program.  This SSL certificate must be installed in the default keystore file for the most current JRE and/or JDK installed on your system.  

Please note that the aforementioned step is not necessary if your Tomcat instance uses an SSL certificate from a trusted CA (Certificate Authority) such as Comodo or Symantec.

The steps to install this certificate in both the Java Runtime Environment (JRE), when running the JAR file, and the Java Development Kit (JDK), when running this program within an IDE, are outlined below:

To install the certificate in the JRE keystore:
Run Command Prompt as Admin
cd "C:\Program Files (x86)\Java\jre1.8.0_45\bin"
  -This path may be different based on your JRE installation location
keytool -import -alias coursepresso-server -keystore "C:\Program Files (x86)\Java\jre1.8.0_45\lib\security\cacerts" -file "C:\temp\ec2-54-152-123-197.compute-1.amazonaws.com.crt"
  -The file path at the end of this command should reflect where you saved the SSL certificate file
Enter keystore password: changeit
Trust this certificate? [no]: yes
Certificate was added to the keystore

To delete the certificate in the JRE keystore (if you uninstall Coursepresso):
cd "C:\Program Files\Java\jdk1.8.0_40\jre\bin"
keytool -delete -alias coursepresso-server -keystore "C:\Program Files (x86)\Java\jre1.8.0_45\lib\security\cacerts"
Enter keystore password: changeit

To install the certificate in the JDK keystore:
Run Command Prompt as Admin
cd "C:\Program Files\Java\jdk1.8.0_40\jre\bin"
  -This path may be different based on your JDK installation location
keytool -import -alias coursepresso-server -keystore "C:\Program Files\Java\jdk1.8.0_40\jre\lib\security\cacerts" -file "C:\temp\ec2-54-152-123-197.compute-1.amazonaws.com.crt"
  -The file path at the end of this command should reflect where you saved the SSL certificate file
Enter keystore password: changeit
Trust this certificate? [no]: yes
Certificate was added to the keystore

To delete the certificate in the JDK keystore:
cd "C:\Program Files\Java\jdk1.8.0_40\jre\bin"
keytool -delete -alias coursepresso-server -keystore "C:\Program Files\Java\jdk1.8.0_40\jre\lib\security\cacerts"
Enter keystore password: changeit

Now you are ready to run Coursepresso!  To execute the program click on the coursepresso-1.0-SNAPSHOT.jar file!
