3-sat-crititcal-number
======================

Random k-SAT solver for a university excercise.

Build
-----

You need [Maven 2.2.x](http://maven.apache.org/download.cgi) to build the project.
I also recommend using the [Maven2eclipse Plugin](http://eclipse.org/m2e/download/).
Use the command ``mvn package`` to create a .jar file or ``mvn assembly:single`` to create a jar including all dependencies.
This also downloads the needed dependencies at the first time.


To start the program outside an IDE use:

For i=1 and i=2
 ``java -jar distribute/3-sat-crititcal-number-0.0.1-SNAPSHOT-jar-with-dependencies-both.jar ``
 
 For i=1
 ``java -jar distribute/3-sat-crititcal-number-0.0.1-SNAPSHOT-jar-with-dependencies-first.jar ``
 
 For i=2
 ``java -jar distribute/3-sat-crititcal-number-0.0.1-SNAPSHOT-jar-with-dependencies-second.jar ``
