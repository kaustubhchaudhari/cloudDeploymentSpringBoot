#!/bin/bash
instanceId=i-099b248f2d7dc727f
export securityGroupId=$(aws ec2 describe-instance-attribute --instance-id $instanceId --attribute groupSet --query "Groups[0].GroupId" --output text)
aws ec2 modify-instance-attribute --instance-id $instanceId --no-disable-api-termination
sleep 10s
aws ec2 terminate-instances --instance-ids $instanceId --region us-east-1
sleep 80s
aws ec2 delete-security-group --group-id $securityGroupId --region us-east-1

