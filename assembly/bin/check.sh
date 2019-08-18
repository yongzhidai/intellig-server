#!/bin/sh

INIT_WAIT=10 # 测试开始前的预等待时间（秒）
CHECK_INTERVAL=1 # 测试间隔（秒）
CHECK_LIMIT_COUNT=80 # 最大测试次数

# 测试地址
TARGET_HOST="127.0.0.1"
TARGET_PORT="8080"
#TARGET_PATH="/api/monitor/alive"
TARGET_PATH="/api/monitor/alive"
TARGET_URL="http://$TARGET_HOST:$TARGET_PORT$TARGET_PATH"

# echo info
echo ""
echo "Preparing for checking service status"
echo "CHECK_LIMIT_COUNT=$CHECK_LIMIT_COUNT"
echo "CHECK_INTERVAL=$CHECK_INTERVAL second(s)"
echo "TARGET_URL=$TARGET_URL"
# 预等待
echo "Init waiting $INIT_WAIT seconds before checking"
sleep ${INIT_WAIT}

# 循环检测
echo "Start checking now"
for TRY_COUNT in $(seq 1 ${CHECK_LIMIT_COUNT})
do
    # 检测响应状态码
    RESPONSE_STATUS=`curl -o /dev/null -s -w %{http_code} ${TARGET_URL}`
    if [ "$RESPONSE_STATUS" = "200" ];then
        echo "Checking Success! TRY_COUNT=$TRY_COUNT RESPONSE_STATUS=$RESPONSE_STATUS"
        exit 0
    else
        echo "Checking Failed! TRY_COUNT=$TRY_COUNT RESPONSE_STATUS=$RESPONSE_STATUS"
        echo "Waiting $CHECK_INTERVAL second(s) before retry"
        sleep ${CHECK_INTERVAL}
    fi
    # 到达次数上限，终止检测
    if [ ${TRY_COUNT} -eq ${CHECK_LIMIT_COUNT} ];then
        echo "Checking limit $CHECK_LIMIT_COUNT exceeded, stop checking now"
        echo "Target service launching may be failed! Consider investigate manually"
        exit -1
    fi
done