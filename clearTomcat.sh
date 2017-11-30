#!/bin/bash

sudo systemctl stop tomcat8
sudo systemctl stop awslogs.service

cd /var/awslogs/etc
