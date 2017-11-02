AMI="ami-cd0f5cb6"
INSTANCE_TYPE="t2.micro"
KEYPAIR_NAME="csye6225-aws"
hosted_name=$(aws route53 list-hosted-zones --query 'HostedZones[0].[Name]' --output text)
hosted_id=$(aws route53 list-hosted-zones --query 'HostedZones[0].[Id]' --output text)
subnet1=$(aws ec2 describe-subnets --query 'Subnets[0].[SubnetId]' --output text)
subnet2=$(aws ec2 describe-subnets --query 'Subnets[1].[SubnetId]' --output text)
vpcId=$(aws ec2 describe-vpcs --query 'Vpcs[0].[VpcId]' --output text)


aws cloudformation create-stack --capabilities CAPABILITY_NAMED_IAM --stack-name $1 --template-body file://mytemplate.json --parameters ParameterKey=ImageId,ParameterValue=$AMI ParameterKey=InstanceType,ParameterValue=$INSTANCE_TYPE ParameterKey=KeyName,ParameterValue=$KEYPAIR_NAME ParameterKey=DBEngine,ParameterValue=MySQL ParameterKey=EngineVersion,ParameterValue=5.6.35 ParameterKey=DBInstanceClass,ParameterValue=db.t2.medium  ParameterKey=DBInstanceIdentifier,ParameterValue=csye6225-fall2017 ParameterKey=MasterUsername,ParameterValue=csye6225master ParameterKey=MasterPassword,ParameterValue=csye6225password ParameterKey=DBName,ParameterValue=csye6225 ParameterKey=DynamoDBTableName,ParameterValue=csye6225 ParameterKey=BucketName,ParameterValue=csye6225-fall2017-chaudharik.me.csye.com ParameterKey=HostedZoneName,ParameterValue=$hosted_name ParameterKey=HostedZoneId,ParameterValue=$hosted_id ParameterKey=Subnet1,ParameterValue=$subnet1 ParameterKey=Subnet2,ParameterValue=$subnet2 ParameterKey=VpcId,ParameterValue=$vpcId

#aws cloudformation wait stack-create-complete --stack-name $1

VOLUME_ID=(aws ec2 create-volume --size 16 --region us-east-1 --availability-zone us-east-1c --volume-type gp2)
PUBLIC_IP=$(aws ec2 describe-instances | grep PublicIpAddress | awk '{print$2}' | tr -cd '[:alnum:]\n.')
echo $PUBLIC_IP

#aws iam create-role --role-name CodeDeployEC2ServiceRole --assume-role-policy-document file://CodeDeployDemo-EC2-Trust.json

#aws iam put-role-policy --role-name CodeDeployEC2ServiceRole --policy-name CodeDeployDemo-EC2-S3 --policy-document file://CodeDeploy-EC2-S3.json

#aws iam create-role --role-name CodeDeployServiceRole --assume-role-policy-document file://CodeDeployDemo-Trust.json

#aws iam attach-role-policy --role-name CodeDeployServiceRole --policy-arn arn:aws:iam::aws:policy/service-role/AWSCodeDeployRole

echo "Configuring Route 53 and pointing it to above IP address"
