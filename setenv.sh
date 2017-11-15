echo ‘#!/bin/sh’ > /opt/tomcat/bin/setenv.sh

echo ‘JAVA_OPTS=”$JAVA_OPTS -Dspring.datasource.url=”jdbc:mysql://YOUR_RDS_ENDPOINT:3306/csye6225”’ >> /opt/tomcat/bin/setenv.sh

echo ‘JAVA_OPTS=”$JAVA_OPTS -Dspring.datasource.username=’ >> /opt/tomcat/bin/setenv.sh

echo ‘JAVA_OPTS=”$JAVA_OPTS -Dspring.datasource.password=’ >> /opt/tomcat/bin/setenv.sh

chmod +x /opt/tomcat/bin/setenv.sh

chown tomcat8:tomcat8 /opt/tomcat/bin/setenv.sh
