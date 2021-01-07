#~!/bin/python
import os, sys

fp = open("util4j-dependencies/pom.xml", "r")
fp2 = open("util4j-dependencies/pom_bak.xml", "w")

artifactId = sys.argv[1]

for line in fp:
    if "PLACE_HOLDER" in line:
        fp2.write("\t\t\t<dependency>\r")
        fp2.write("\t\t\t\t<groupId>tech.qijin.util4j</groupId>\r")
        fp2.write("\t\t\t\t<artifactId>" + artifactId + "</artifactId>\r")
        fp2.write("\t\t\t\t<version>${project.version}</version>\r")
        fp2.write("\t\t\t</dependency>\r")
    fp2.write(line)

fp.close()
fp2.close()
