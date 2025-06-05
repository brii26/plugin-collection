+---------------------------------------------+
|                CLOCKPLUGIN README            |
+---------------------------------------------+

Build Instructions
------------------

1. Make sure to compile the core-api module first using:
   
       mvn clean install 
    in given [repository](https://github.com/brii26/core-api)

2. Then build the ClockPlugin and generate the .jar file:

       mvn clean install

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

Following this will ensure a clean and successful build.

+---------------------------------------------+
