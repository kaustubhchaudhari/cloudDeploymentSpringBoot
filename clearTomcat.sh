#!/bin/bash

sudo systemctl stop tomcat8
sudo systemctl stop awslogs.service
cd /var/lib/tomcat8/webapps
sudo rm -r ROOT
cd ~
cd /var/awslogs/etc
sudo rm awslogs.conf
