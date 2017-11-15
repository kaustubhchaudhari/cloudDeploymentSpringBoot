#!/bin/bash
export VPC_ID=$(aws ec2 describe-vpcs --query "Vpcs[0].VpcId" --output text)
export GROUP_ID=$(aws ec2 create-security-group --group-name csye6225-fall2017-webapp --description "Sample sec grp" --vpc-id $VPC_ID --output text)
aws ec2 authorize-security-group-ingress --group-id $GROUP_ID --protocol tcp --port 22
aws ec2 authorize-security-group-ingress --group-id $GROUP_ID --protocol tcp --port 80
aws ec2 authorize-security-group-ingress --group-id $GROUP_ID --protocol tcp --port 443
export instance_id=$(aws ec2 run-instances --image-id ami-cd0f5cb6 --security-group-ids $GROUP_ID --count 1 --instance-type t2.micro --key-name csye6225 --disable-api-termination --block-device-mappings '[{"DeviceName":"/dev/sda1", "Ebs":{"VolumeSize":16,"VolumeType":"gp2"}}]' --query 'Instances[0].InstanceId')
instance_id=$(eval echo $instance_id)
aws ec2 wait instance-running --instance-ids $instance_id
export instance_ip=$(eval aws ec2 describe-instances --instance-ids $instance_id --query 'Reservations[0].Instances[0].PublicIpAddress')
instance_ip=$(eval echo $instance_ip)
zone_id=Z2NPS0RUXOU9FU
export INPUT_JSON_STR='{"ChangeBatch":{"Comment": "Update the A record set", "Changes":[{"Action":"UPSERT","ResourceRecordSet":{"Name":"ec2.csye6225-fall2017-saxenapr.me.","Type": "A","TTL": 60,"ResourceRecords":[{"Value": "127.0.0.1"}]}}]}}'
echo $INPUT_JSON_STR
INPUT_JSON=${INPUT_JSON_STR/127.0.0.1/$instance_ip}
echo $INPUT_JSON
aws route53 change-resource-record-sets --hosted-zone-id $zone_id --cli-input-json "$INPUT_JSON"
~                                                                                                

