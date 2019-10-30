#!/bin/bash

SERVICE_NAME="EMAIL-CONNECTOR"
BUILD_NUMBER=${BUILD_NUMBER}
IMAGE_VERSION=${BUILD_NUMBER}
TASK_FAMILY="EMAIL-CONNECTOR-TD"

 DESIRED_COUNT=`aws ecs describe-services --services ${SERVICE_NAME} --cluster PRS-DEV-CLUSTER | egrep "desiredCount" | tr "/" " " | awk '{print $2}' | sed 's/,$//'`
 if [ $DESIRED_COUNT == 0 ]; then
    DESIRED_COUNT=1
 fi
aws ecs update-service --cluster SI-Dev-Cluster --service ${SERVICE_NAME} --task-definition ${TASK_FAMILY}:1 --desired-count ${DESIRED_COUNT}

OLDER_TASK=$(aws ecs list-tasks --cluster PRS-DEV-CLUSTER --desired-status RUNNING --family ${TASK_FAMILY} | egrep "task/" | sed -E "s/.*task\/(.*)\"/\1/")

echo "Older Task running  " + $OLDER_TASK

aws ecs stop-task --cluster PRS-DEV-CLUSTER --task ${OLDER_TASK}

aws ecs update-service --cluster "PRS-DEV-CLUSTER" --service "${SERVICE_NAME}" --task-definition "${TASK_FAMILY}":1 --desired-count 1 --force-new-deployment
