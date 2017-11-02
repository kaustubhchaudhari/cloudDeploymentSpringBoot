#!/bin/bash

sudo systemctl stop tomcat8
cd /var/lib/tomcat8/webapps
sudo rm -r ROOT
