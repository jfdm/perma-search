######################################################


README.txt

Author:     Jan de Muijnck-Hughes 
Contact:    jfdm@st-andrews.ac.uk
Date:	    18th April 2008 
######################################################

This folder contains the User Manual documentation for 
the Java Project. The folders contents are as follows:


	bin/		Contains the compiled manual.
	css/		Contains the style sheet for the HTML version.
	docbook-xml/	The current specification for docbook.
	docbook-xsl/	The current stylesheet for docbook.
	lib/		Contains the various libraries used.
	src/		Contains the source XML files.
	build.xml	The ant build file.
	LICENSE.txt	A copy of the GNU Free document license.
	TODO.txt	A list of things to be done in the document.


For more information regarding the stylesheet please go to http://docbook.sourceforge.net/ .

################ Usage

To generate the documentation you can use the following commands:

	$ ant all	     // This builds all the documentation.
	$ ant build-html     // Builds the HTML version.
	$ ant build-pdf	     // Builds the PDF version.
	$ clean		     // Removes the current HTML and PDF versions	
	

######################################################
