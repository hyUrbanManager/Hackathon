@ECHO OFF

java -jar "F:\proguard\proguard6.0\lib\proguard.jar" ^
-verbose -injars build\jar\parser.jar -outjars build\jar\parser-min.jar @proguard-rules.pro
