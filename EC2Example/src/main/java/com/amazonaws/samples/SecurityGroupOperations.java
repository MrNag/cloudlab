package com.amazonaws.samples;

import java.util.Arrays;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressResult;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupResult;
import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.*;

public class SecurityGroupOperations extends awsClientInitializer {
	protected AmazonEC2 amazonEC2Client;
	public SecurityGroupOperations() {
		super();
		amazonEC2Client = new AmazonEC2Client(getCredentials());
		amazonEC2Client.setRegion(region);
	}
	public String createSecurityGroup(String groupName, String groupDescription) {
		seperator("createSecurityGroup");
		
		CreateSecurityGroupRequest securityGroupRequest = new CreateSecurityGroupRequest();
		securityGroupRequest.withGroupName(groupName);
		securityGroupRequest.withDescription(groupDescription);
		
		CreateSecurityGroupResult response = amazonEC2Client.createSecurityGroup(securityGroupRequest);
		printObject(response);
		return response.getGroupId();
	}
	public void assignSSHConnectionAccessToSecurityGroup(String groupName) {
		seperator("assignSSHConnectionAccessToSecurityGroup");
		
		AuthorizeSecurityGroupIngressRequest request = new AuthorizeSecurityGroupIngressRequest();
		request.withGroupName(groupName);
		
		IpPermission ipPermission = createIpPermission();
		request.withIpPermissions(ipPermission);
		
		AuthorizeSecurityGroupIngressResult response = amazonEC2Client.authorizeSecurityGroupIngress(request);
		printObject(response);
	}
	
	private IpPermission createIpPermission() {
		IpPermission ipPermission = new IpPermission();
		
		ipPermission.withIpProtocol("tcp");
		ipPermission.withFromPort(22);
		ipPermission.withToPort(22);
		ipPermission.withIpRanges("0.0.0.0/0");

		return ipPermission;
	}
	public void describeSecurityGroups(String securityGroupId) {
		seperator("describeSecurityGroups");

		DescribeSecurityGroupsRequest request = new DescribeSecurityGroupsRequest();
		request.setGroupIds(Arrays.asList(securityGroupId));
		DescribeSecurityGroupsResult response = amazonEC2Client.describeSecurityGroups();
		printObject(response);
	}
	
	public void deleteSecurityGroupBasedOnGroupName(String groupName) {
		seperator("deleteSecurityGroupBasedOnGroupName");
		
		DeleteSecurityGroupRequest request = new DeleteSecurityGroupRequest();
		request.setGroupName(groupName);
		DeleteSecurityGroupResult response = amazonEC2Client.deleteSecurityGroup(request);
		printObject(response);
	}
	
	public void deleteSecurityGroupBasedOnGroupId(String groupId) {
		seperator("deleteSecurityGroupBasedOnGroupId");
		
		DeleteSecurityGroupRequest request = new DeleteSecurityGroupRequest();
		request.setGroupId(groupId);
		DeleteSecurityGroupResult response = amazonEC2Client.deleteSecurityGroup(request);
		printObject(response);
	}

}
