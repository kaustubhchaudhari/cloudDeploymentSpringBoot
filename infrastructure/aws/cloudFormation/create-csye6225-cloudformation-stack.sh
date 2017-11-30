#!/bin/bash
echo "Please Enter a Name for the stack you want to create"
read stack_name
hosted_id=$(aws route53 list-hosted-zones --query 'HostedZones[0].[Name]' --output text)
vpc_id=$(aws ec2 describe-vpcs --query 'Vpcs[0].[VpcId]' --output text)
echo ${hosted_id}

jq '.Resources.WebServerSecurityGroup.Properties.GroupName = "csye6225-fall2017-'$stack_name'-webapp"' ./mytemplate.json > tmp.$$.json && mv tmp.$$.json ./mytemplate.json

jq '.Resources.WebServerSecurityGroup.Properties.VpcId = "'$vpc_id'"' ./mytemplate.json > tmp.$$.json && mv tmp.$$.json ./mytemplate.json

jq '.Resources.DataBaseServerSecurityGroup.Properties.VpcId = "'$vpc_id'"' ./mytemplate.json > tmp.$$.json && mv tmp.$$.json ./mytemplate.json

jq '.Resources.subnet1.Properties.VpcId = "'$vpc_id'"' ./mytemplate.json > tmp.$$.json && mv tmp.$$.json ./mytemplate.json

jq '.Resources.subnet2.Properties.VpcId = "'$vpc_id'"' ./mytemplate.json > tmp.$$.json && mv tmp.$$.json ./mytemplate.json

jq '.Resources.myDNSRecord.Properties.HostedZoneName = "'$hosted_id'"' ./mytemplate.json > tmp.$$.json && mv tmp.$$.json ./mytemplate.json

jq '.Resources.myDNSRecord.Properties.Name = "ec2.'$hosted_id'"' ./mytemplate.json > tmp.$$.json && mv tmp.$$.json ./mytemplate.json

jq '.Resources.S3Bucket.Properties.BucketName = "'$hosted_id'bucket"' ./mytemplate.json > tmp.$$.json && mv tmp.$$.json ./mytemplate.json

echo "***Done***"

aws cloudformation create-stack --stack-name ${stack_name} --template-body file://./mytemplate.json --capabilities=CAPABILITY_NAMED_IAM

placeholder="placeHolder"

jq '.Resources.WebServerSecurityGroup.Properties.GroupName = "csye6225-fall2017-'$placeholder'-webapp"' ./mytemplate.json > tmp.$$.json && mv tmp.$$.json ./mytemplate.json

jq '.Resources.WebServerSecurityGroup.Properties.VpcId = "'$placeholder'"' ./mytemplate.json > tmp.$$.json && mv tmp.$$.json ./mytemplate.json

jq '.Resources.DataBaseServerSecurityGroup.Properties.VpcId = "'$placeholder'"' ./mytemplate.json > tmp.$$.json && mv tmp.$$.json ./mytemplate.json

jq '.Resources.subnet1.Properties.VpcId = "'$placeholder'"' ./mytemplate.json > tmp.$$.json && mv tmp.$$.json ./mytemplate.json

jq '.Resources.subnet2.Properties.VpcId = "'$placeholder'"' ./mytemplate.json > tmp.$$.json && mv tmp.$$.json ./mytemplate.json

jq '.Resources.myDNSRecord.Properties.HostedZoneName = "'$placeholder'"' ./mytemplate.json > tmp.$$.json && mv tmp.$$.json ./mytemplate.json

jq '.Resources.myDNSRecord.Properties.Name = "ec2.'$placeholder'"' ./mytemplate.json > tmp.$$.json && mv tmp.$$.json ./mytemplate.json

jq '.Resources.S3Bucket.Properties.BucketName = "'$placeholder'"' ./mytemplate.json > tmp.$$.json && mv tmp.$$.json ./mytemplate.json

aws cloudformation wait stack-create-complete --stack-name ${stack_name}

