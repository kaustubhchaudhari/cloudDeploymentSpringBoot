AMI="ami-cd0f5cb6"
INSTANCE_TYPE="t2.micro"
KEYPAIR_NAME="id_ras1"
 
aws cloudformation wait stack-create-complete --stack-name $1
VOLUME_ID=(aws ec2 create-volume --size 16 --region us-east-1 --availability-zone us-east-1c --volume-type gp2)
PUBLIC_IP=$(aws ec2 describe-instances | grep PublicIpAddress | awk '{print$2}' | tr -cd '[:alnum:]\n.')
echo $PUBLIC_IP

echo "Configuring Route 53 and pointing it to above IP address"

jq '.Changes[0].ResourceRecordSet.ResourceRecords[0].Value = "'$PUBLIC_IP'"' /home/kaustubh/Downloads/infrastructure/scripts/change-resource-record-sets.json > tmp.$$.json && mv tmp.$$.json //home/kaustubh/Downloads/infrastructure/scripts/change-resource-record-sets.json

aws route53 change-resource-record-sets --hosted-zone-id Z1DR1TJ5NV1YKJ
 --change-batch file:///home/kaustubh/Downloads/infrastructure/scripts/change-resource-record-sets.json
