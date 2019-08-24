#!/usr/bin/env bash

# 变量设置
function init_variables(){
    echo "Configuring paths & variables:"
    JAVA_CMD=${JAVA_HOME}"/bin/java"
    EXEC_CMD="exec"
    WORK_DIR=`cd $(dirname $0);cd ../;pwd`
    LOG_PATH=${WORK_DIR}"/logs"
    APP_NAME="intellig-server"

    # debug info
    echo "JAVA_CMD=$JAVA_CMD"
    echo "EXEC_CMD=$EXEC_CMD"
    echo "WORK_DIR=$WORK_DIR"
    echo "LOG_PATH=$LOG_PATH"
}

# 环境准备
function prepare_env(){
    echo "Preparing environment:"
    # confirm log path
    echo "Confirming log path: $LOG_PATH"
    mkdir -p ${LOG_PATH}


    # load jvm args
    echo "Loading boot.ini from ${WORK_DIR}/config"
    source ${WORK_DIR}/config/boot.ini
    source ${WORK_DIR}/config/boot-env.ini
}

# 启动进程
function launch_process(){
    echo "Launching java process:"
    JVM_PARAMS="$JVM_BASE $JVM_GC_TYPE $JVM_GC_LOG_CONTENT $JVM_GC_LOG_FILE $JVM_HEAP $JVM_SIZE $JVM_DEBUG"
    LOG_PARAMS="-Xloggc:$LOG_PATH/$APP_NAME.gc.log -XX:ErrorFile=$LOG_PATH/$APP_NAME.hs_err_%p.log -XX:HeapDumpPath=$LOG_PATH/$APP_NAME.heap_err_%p.hprof"
    APP_PARAMS="-Dapp.log.dir=$LOG_PATH -Dapp.name=$APP_NAME"
    FINAL_CMD="$EXEC_CMD $JAVA_CMD $JVM_PARAMS $LOG_PARAMS $APP_PARAMS"
    echo "Executing launch command: ${FINAL_CMD}"
    ${FINAL_CMD} -jar ${WORK_DIR}/lib/*.jar 2>&1
}

# do real actions
init_variables
prepare_env
launch_process