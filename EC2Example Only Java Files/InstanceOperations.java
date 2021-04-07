package com.amazonaws.samples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.amazonaws.services.ec2.*;
import com.amazonaws.services.ec2.model.*;
import com.amazonaws.util.StringUtils;

public class InstanceOperations extends awsClientInitializer {
	protected AmazonEC2 amazonEC2Client;
	public InstanceOperations() {
		super();
		amazonEC2Client = new AmazonEC2Client(getCredentials());
		amazonEC2Client.setRegion(region);
	}
	public void describeImageIds(String imageId) {
		DescribeImagesRequest request = new DescribeImagesRequest();
		if(!StringUtils.isNullOrEmpty(imageId)) {
			request.withImageIds(imageId);
		}
		DescribeImagesResult response = 
				amazonEC2Client.describeImages(request);
		printObject(response);
	}
	public String launchInstance(String ami, InstanceType instanceType, int minInstanceCount, int maxInstanceCount, String keyPairName, String securityGroupName) {
		seperator("launchInstance");
		
		RunInstancesRequest request = 
				new RunInstancesRequest();
		
		request.setImageId(ami);
		request.setInstanceType(instanceType);
		
		request.setKeyName(keyPairName);
		List<String> securityGroups = new ArrayList<String>();
		securityGroups.add(securityGroupName);
		request.setSecurityGroups(securityGroups);
		
		request.setMinCount(minInstanceCount);
		request.setMaxCount(maxInstanceCount);
		
		RunInstancesResult response = 
				amazonEC2Client.runInstances(request);
		
		printObject(response);
		
		return getInstanceId();
	}
	public String getInstanceId() {
		seperator("getInstanceId");
		
		String instanceId=null;
		DescribeInstancesResult response = 
				amazonEC2Client.describeInstances();
		for(Reservation reservation: response.getReservations()) {
			List<Instance> instances = reservation.getInstances();
			for (Instance instance : instances) {
				if (instance.getState().getName().equals("pending")) {
					instanceId = instance.getInstanceId();
					break;
				}
			}
		}
		printObject(response);
		return instanceId;
	}
	public void startInstance(String instanceId) {
		seperator("startInstance");
		
		StartInstancesRequest request = 
				new StartInstancesRequest();
		request.setInstanceIds(Arrays.asList(instanceId));
		
		StartInstancesResult response = 
				amazonEC2Client.startInstances(request);
		
		printObject(response);
	}
	public void stopInstance(String instanceId) {
		seperator("stopInstance");
		
		StopInstancesRequest request = 
				new StopInstancesRequest();
		request.setInstanceIds(Arrays.asList(instanceId));
		
		StopInstancesResult response = 
				amazonEC2Client.stopInstances(request);
		
		printObject(response);
	}
	public void terminateInstance(String instanceId) {
		seperator("stopInstance");
		
		TerminateInstancesRequest request = 
				new TerminateInstancesRequest();
		request.setInstanceIds(Arrays.asList(instanceId));
		
		TerminateInstancesResult response = 
				amazonEC2Client.terminateInstances(request);
		
		printObject(response);
	}
	public List<String> describeInstances(String instanceId) {
		seperator("listInstances");
		
		List<String> instanceIdList = new ArrayList<String>();

		DescribeInstancesRequest request = 
				new DescribeInstancesRequest();

		if(!StringUtils.isNullOrEmpty(instanceId)) {
			request.setInstanceIds(Arrays.asList(instanceId));
		}

		DescribeInstancesResult response = 
				amazonEC2Client.describeInstances(request);
		
		if(response!=null && response.getReservations()!=null && !response.getReservations().isEmpty()) {
			for(Reservation reservation: response.getReservations()) {
				List<Instance> instances = reservation.getInstances();
				if(instances!=null && !instances.isEmpty()) {
					for(Instance instance: instances) {
						instanceIdList.add(instance.getInstanceId());
					}
				}
			}
		}
		printObject(response);
		return instanceIdList;
	}
}

