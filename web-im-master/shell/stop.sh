#!/bin/sh
HOME=`pwd`
MANAGEMENT_PORT=`cat $HOME/manage_port`
PIDFILE=$HOME/pid
MANAGEMENT_PORT_FILE=$HOME/manage_port
PID=`cat $PIDFILE`
KILL_MAXSECONDS=30
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

curl -X POST 127.0.0.1:$MANAGEMENT_PORT/manage/shutdown


rm -f $PIDFILE >/dev/null 2>&1

rm -f $MANAGEMENT_PORT_FILE >/dev/null 2>&1

exit $RET

