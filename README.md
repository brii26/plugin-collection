+---------------------------------------------+
|           PLUGIN-Collection README          |
+---------------------------------------------+

Build Instructions
------------------

1. Make sure to compile the core-api module first using:
   
       mvn clean install 
    in given [repository](https://github.com/brii26/core-api)

2. Then build all plugins and generate the .jar file:

       mvn clean install  
3. build and test certain plugins and generate particular plugin .jar file (using the 'clock' for example):

       mvn clean install -pl clock -am
       mvn javafx:run -pl clock


This will:
  - Compile the source code.
  - Package the plugin into a .jar file.
  - Output the .jar file in the target/ directory.

Important Notes
---------------

- Before building, remove or disable any existing plugins that are incomplete or fail to compile
  to avoid build errors.

- To disable problematic plugins, either:
    • Comment out their source code files, or
    • Remove their module entries from the aggregator's pom.xml dependency list.

Following this will ensure a clean and successful build. Make sure to add <modules> in the pom.xml every plugin created.

+---------------------------------------------+
