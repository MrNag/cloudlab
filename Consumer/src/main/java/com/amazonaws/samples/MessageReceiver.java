package com.amazonaws.samples;

import java.util.*;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.*;
import com.amazonaws.services.sqs.model.*;
	
public class MessageReceiver extends awsClientInitializer {
	AmazonSQS amazonSQSClient;
	String awsUserID;
	public MessageReceiver(String awsUserID) {
		super();
		amazonSQSClient= new AmazonSQSClient(getCredentials());
		amazonSQSClient.setRegion(region);
		this.awsUserID=awsUserID;
	}
	private String getQueueURL(String queueName) {
		GetQueueUrlResult response=amazonSQSClient.getQueueUrl(queueName);
		return response.getQueueUrl();
	}
	private void receiveMessages(String queueURL) {
		ReceiveMessageRequest req=new ReceiveMessageRequest(queueURL);
		req.setMaxNumberOfMessages(5);
		ReceiveMessageResult response =
				amazonSQSClient.receiveMessage(req);
		List<Message> messageList = response.getMessages();
		for(Message message:messageList) {
			System.out.println(message.getBody());
		}
	}
	public static void main(String[] args) {
		MessageReceiver mr=new MessageReceiver("470923778669");
		String queueURL = mr.getQueueURL("MyQueue_SDK");
		mr.receiveMessages(queueURL);
	}
}
