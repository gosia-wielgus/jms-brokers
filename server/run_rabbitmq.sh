#!/bin/sh

BASE=`pwd`

export RABBITMQ_CONFIG_FILE=$BASE/rabbitmq
export RABBITMQ_LOG_BASE=$BASE/logs
export RABBITMQ_MNESIA_BASE=$BASE/mnesia
export RABBITMQ_NODENAME=local

rabbitmq-server


