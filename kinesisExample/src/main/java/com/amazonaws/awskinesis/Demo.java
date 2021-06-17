package com.amazonaws.awskinesis;

import java.nio.ByteBuffer;
import java.util.*;
import com.amazonaws.services.kinesis.model.*;

public class Demo {

	public static void main(String[] args) throws Exception{
		KinesisOperations ko = new KinesisOperations("470923778669");
		ko.createStream("MyStream", 5);
		Thread.sleep(30000);
		ko.createStream("MyStream1", 5);
		Thread.sleep(30000);
		DescribeStreamResult dsr = ko.describeStream("MyStream", 5, null);
		ko.listStreams("MyStream", null);
		ko.increaseRetentionPeriod("MyStream", 72);
		Thread.sleep(30000);
		ko.describeStream("MyStream", 1, null);
		ko.decreaseRetentionPeriod("MyStream", 24);
		Thread.sleep(30000);
		ko.describeStream("MyStream", 1, null);
		ko.putRecord("MyStream", "123", "Hello World".getBytes(), "272225893536750770770699685945414569165");
		Thread.sleep(30000);
		List<PutRecordsRequestEntry>records = new ArrayList<>();
		PutRecordsRequestEntry record= new PutRecordsRequestEntry();
		record.setExplicitHashKey("272225893536750770770699685945414569165");
		record.setPartitionKey("123");
		record.setData(ByteBuffer.wrap("Hello".getBytes()));
		records.add(record);
		record.setExplicitHashKey("272225893536750770770699685945414569165");
		record.setPartitionKey("123");
		record.setData(ByteBuffer.wrap("Hello User".getBytes()));
		records.add(record);
		ko.putRecords("MyStream", records);
		Thread.sleep(30000);
		/*ko.getRecords("MyStream");
		Thread.sleep(30000);
		ko.deleteStream("MyStream1");;*/
	}	

}
