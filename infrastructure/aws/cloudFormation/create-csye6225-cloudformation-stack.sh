#!/bin/bash
echo "Please Enter a Name for the stack you want to create"
read stack_name
hosted_id=$(aws route53 list-hosted-zones --query 'HostedZones[0].[Name]' --output text)
vpc_id=$(aws ec2 describe-vpcs --query 'Vpcs[0].[VpcId]' --output text)
echo ${hosted_id}

jq '.Resources.WebServerSecurityGroup.Properties.GroupName = "csye6225-fall2017-'$stack_name'-webapp"' ./mytemplate1.json > tmp.$$.json && mv tmp.$$.json ./mytemplate1.json

jq '.Resources.WebServerSecurityGroup.Properties.VpcId = "'$vpc_id'"' ./mytemplate1.json > tmp.$$.json && mv tmp.$$.json ./mytemplate1.json

jq '.Resources.DataBaseServerSecurityGroup.Properties.VpcId = "'$vpc_id'"' ./mytemplate1.json > tmp.$$.json && mv tmp.$$.json ./mytemplate1.json

jq '.Resources.subnet1.Properties.VpcId = "'$vpc_id'"' ./mytemplate1.json > tmp.$$.json && mv tmp.$$.json ./mytemplate1.json

jq '.Resources.subnet2.Properties.VpcId = "'$vpc_id'"' ./mytemplate1.json > tmp.$$.json && mv tmp.$$.json ./mytemplate1.json

jq '.Resources.myDNSRecord.Properties.HostedZoneName = "'$hosted_id'"' ./mytemplate1.json > tmp.$$.json && mv tmp.$$.json ./mytemplate1.json

jq '.Resources.myDNSRecord.Properties.Name = "ec2.'$hosted_id'"' ./mytemplate1.json > tmp.$$.json && mv tmp.$$.json ./mytemplate1.json

jq '.Resources.S3Bucket.Properties.BucketName = "'$hosted_id'bucket"' ./mytemplate1.json > tmp.$$.json && mv tmp.$$.json ./mytemplate1.json

echo "***Done***"

aws cloudformation create-stack --stack-name ${stack_name} --template-body file://./mytemplate1.json --capabilities=CAPABILITY_NAMED_IAM

placeholder="placeHolder"

jq '.Resources.WebServerSecurityGroup.Properties.GroupName = "csye6225-fall2017-'$placeholder'-webapp"' ./mytemplate1.json > tmp.$$.json && mv tmp.$$.json ./mytemplate1.json

jq '.Resources.WebServerSecurityGroup.Properties.VpcId = "'$placeholder'"' ./mytemplate1.json > tmp.$$.json && mv tmp.$$.json ./mytemplate1.json

jq '.Resources.DataBaseServerSecurityGroup.Properties.VpcId = "'$placeholder'"' ./mytemplate1.json > tmp.$$.json && mv tmp.$$.json ./mytemplate1.json

jq '.Resources.subnet1.Properties.VpcId = "'$placeholder'"' ./mytemplate1.json > tmp.$$.json && mv tmp.$$.json ./mytemplate1.json

jq '.Resources.subnet2.Properties.VpcId = "'$placeholder'"' ./mytemplate1.json > tmp.$$.json && mv tmp.$$.json ./mytemplate1.json

jq '.Resources.myDNSRecord.Properties.HostedZoneName = "'$placeholder'"' ./mytemplate1.json > tmp.$$.json && mv tmp.$$.json ./mytemplate1.json

jq '.Resources.myDNSRecord.Properties.Name = "ec2.'$placeholder'"' ./mytemplate1.json > tmp.$$.json && mv tmp.$$.json ./mytemplate1.json

jq '.Resources.S3Bucket.Properties.BucketName = "'$placeholder'"' ./mytemplate1.json > tmp.$$.json && mv tmp.$$.json ./mytemplate1.json

aws cloudformation wait stack-create-complete --stack-name ${stack_name}

