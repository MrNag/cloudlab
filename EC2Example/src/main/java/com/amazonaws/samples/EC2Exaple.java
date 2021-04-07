package com.amazonaws.samples;

import com.amazonaws.services.ec2.model.InstanceType;

public class EC2Exaple {

	public static void main(String[] args) {
		SecurityGroupOperations sgo =
				new SecurityGroupOperations();
		String sgID = sgo.createSecurityGroup("SGViaAWSJavaSDK", "SG Via AWS Java SDK");
		sgo.assignSSHConnectionAccessToSecurityGroup("SGViaAWSJavaSDK");
		sgo.describeSecurityGroups(sgID);
		//sgo.deleteSecurityGroupBasedOnGroupId(sgID);
		KeyPairOperations kpo = new KeyPairOperations();
		kpo.createKeyPair("KPViaAWSJavaSDK");
		kpo.describeKeyPairs("KPViaAWSJavaSDK");
		//kpo.deleteKeyPair("KPViaAWSJavaSDK");
		InstanceOperations io = new InstanceOperations();
		io.describeImageIds("ami-0518bb0e75d3619ca");
		String instanceID = io.launchInstance("ami-0518bb0e75d3619ca", 
				InstanceType.T2Micro, 
				1, 
				1, 
				"KPViaAWSJavaSDK", 
				"SGViaAWSJavaSDK");
		io.describeInstances(instanceID);
		
	}

}
