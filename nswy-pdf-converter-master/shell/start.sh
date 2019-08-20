#!/bin/sh
SERVER_PORT=8081
MANAGEMENT_PORT=9081
HOME=`pwd`
APP_EXEC_JAR="nswy-pdf-converter-1.0-SNAPSHOT.jar"
PIDFILE="$HOME/pid"
IP=`ifconfig -a|grep inet|grep -v 127.0.0.1|grep -v inet6|awk '{print $2}'`
checkRunning(){
    if [ -f "$PIDFILE" ]; then
       if  [ -z "`cat $PIDFILE`" ];then
        echo "ERROR: Pidfile '$PIDFILE' exists but contains no pid"
        return 2
       fi
       PID="`cat $PIDFILE`"
       RET="`ps -p $PID|grep java`"
       if [ -n "$RET" ];then
         return 0;
       else
         return 1;
       fi
    else
         return 1;
    fi
}

if ( checkRunning );then
    PID=`cat $PIDFILE`
    echo "INFO: Process with pid '$PID' is already running"
    exit 0
fi
# -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8888 \
# -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintHeapAtGC -Xloggc:auditgc \
java -server \
     -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintHeapAtGC -Xloggc:auditgc \
     -DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector \
     -jar $APP_EXEC_JAR \
     --server.port=$SERVER_PORT \
     --management.port=$MANAGEMENT_PORT \
     --eureka.instance.ip-address=$IP \
     --fdfs.conf=$HOME/config/fdfs_client.conf \
     --open-office.filePath=$HOME \
     --app.home=$HOME \
     --spring.profiles.active=dev \
     --spring.config.location=$HOME/config/application.yml \
     --logging.config=$HOME/config/log4j2.xml > /dev/null 2>&1 &
echo $! > "$HOME/pid";
echo $MANAGEMENT_PORT > "$HOME/manage_port"
PID="`cat $PIDFILE`"

echo "INFO: Starting - Process $PID, server.port: $SERVER_PORT, management.port: $MANAGEMENT_PORT"