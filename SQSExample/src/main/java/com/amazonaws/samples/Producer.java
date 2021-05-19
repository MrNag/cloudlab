package com.amazonaws.samples;
import java.util.*;

import com.amazonaws.services.sqs.*;
import com.amazonaws.services.sqs.model.*;

public class Producer extends awsClientInitializer{
	AmazonSQS amazonSQSClient;
	String awsUserID;
	public Producer(String awsUserID) {
		super();
		amazonSQSClient= new AmazonSQSClient(getCredentials());
		amazonSQSClient.setRegion(region);
		this.awsUserID=awsUserID;
	}
	public String createQueue(String queueName) {
		seperator("Create Queue");
		CreateQueueRequest request = 
				new CreateQueueRequest(queueName);
		CreateQueueResult result = 
				amazonSQSClient.createQueue(request);
		return result.getQueueUrl();
	} 
	public void addPermission(String queueUrl) {
		AddPermissionRequest request = new AddPermissionRequest();
		request.setQueueUrl(queueUrl);
		request.setActions(getActions());
		request.setAWSAccountIds(getAWSAccountIds());
		request.setLabel("Chapter3SQSPermission");
		AddPermissionResult response = amazonSQSClient.addPermission(request);
	}
	private List<String> getAWSAccountIds() {
		List<String> awsAccountIdsList = new ArrayList<String>();
		awsAccountIdsList.add(awsUserID);
		return awsAccountIdsList;
	}
	private List<String> getActions() {
		List<String> actionsList = new ArrayList<String>();
		actionsList.add("*");
		/*actionsList.add("SendMessage");
		actionsList.add("ReceiveMessage");
		actionsList.add("DeleteMessage");
		actionsList.add("ChangeMessageVisibility");
		actionsList.add("GetQueueAttributes");
		actionsList.add("GetQueueUrl");*/
		return actionsList;
	}
	private void sendMessage(String queueURL, String messageBody) {
		SendMessageRequest req = 
				new SendMessageRequest(queueURL, messageBody);
		amazonSQSClient.sendMessage(req);
		
	}
	private String getQueueURL(String queueName) {
		GetQueueUrlResult response=amazonSQSClient.getQueueUrl(queueName);
		return response.getQueueUrl();
	}
	public static void main(String[] args) {
		Producer p = new Producer("470923778669");
		String queueURL;
		try {
			queueURL = p.createQueue("MyQueue_SDK");
		}catch(Exception e) {
			System.out.println("Queue Already Existed");
			queueURL = p.getQueueURL("MyQueue_SDK");
		}
		p.addPermission(queueURL);
		System.out.println("Enter a Message");
		String message = new Scanner(System.in).next();
		p.sendMessage(queueURL, message);
		
	}

}
