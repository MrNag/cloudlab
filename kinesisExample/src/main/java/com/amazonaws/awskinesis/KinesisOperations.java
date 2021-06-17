package com.amazonaws.awskinesis;


import java.nio.ByteBuffer;
import java.util.*;

import com.amazonaws.services.kinesis.*;
import com.amazonaws.services.kinesis.model.*;
import com.amazonaws.util.StringUtils;

public class KinesisOperations extends AwsClientInitializer{
	protected AmazonKinesisClient akc;
	private String awsUserAccountID;
	public KinesisOperations(String awsUserID) {
		super();
		akc=new AmazonKinesisClient(getCredentials());
		akc.setRegion(region);
		awsUserAccountID=awsUserID;
	}
	public void createStream(String streamName, Integer shardCount) {
		seperator("createStream");
		CreateStreamRequest req = new CreateStreamRequest();
		req.setStreamName(streamName);
		req.setShardCount(shardCount);
		CreateStreamResult response = akc.createStream(req);
		printObject(response);
	}
	public DescribeStreamResult describeStream(String StreamName, Integer limit, String exclusiveStartShardId) {
		seperator("DescribeStreamResult");
		DescribeStreamRequest req = new DescribeStreamRequest();
		req.setStreamName(StreamName);
		if(limit!=null)
			req.setLimit(limit);
		if(!StringUtils.isNullOrEmpty(exclusiveStartShardId))
			req.setExclusiveStartShardId(exclusiveStartShardId);
		DescribeStreamResult response = akc.describeStream(req);
		printObject(response);
		return response;
	}
	public void listStreams(String exclusiveStartStreamName, Integer streamListingLimit) {
		seperator("listStreams");
		ListStreamsRequest req = new ListStreamsRequest();
		if(streamListingLimit!=null)
			req.setLimit(streamListingLimit);
		if(!StringUtils.isNullOrEmpty(exclusiveStartStreamName))
			req.setExclusiveStartStreamName(exclusiveStartStreamName);
		ListStreamsResult response = akc.listStreams(req);
		printObject(response);
	}
	public void deleteStream(String streamName) {
		seperator("deleteStream");
		DeleteStreamResult response = akc.deleteStream(streamName);
		printObject(response);
	}
	public void increaseRetentionPeriod(String streamName, Integer retentionPeriod) {
		seperator("increaseRetentionPeriod");
		IncreaseStreamRetentionPeriodRequest request = 
				new IncreaseStreamRetentionPeriodRequest();
		request.setStreamName(streamName);
		request.setRetentionPeriodHours(retentionPeriod);
		IncreaseStreamRetentionPeriodResult response = 
				akc.increaseStreamRetentionPeriod(request);
		printObject(response);
	}
	public void decreaseRetentionPeriod(String streamName, Integer retentionPeriod) {
		seperator("decreaseRetentionPeriod");
		DecreaseStreamRetentionPeriodRequest request = 
				new DecreaseStreamRetentionPeriodRequest();
		request.setStreamName(streamName);
		request.setRetentionPeriodHours(retentionPeriod);
		DecreaseStreamRetentionPeriodResult response = 
				akc.decreaseStreamRetentionPeriod(request);
		printObject(response);
	}
	public void putRecord(String streamName, String partitionKey, byte[] data, String explicitHaskKey) {
		seperator("putRecord");
		PutRecordRequest req = new PutRecordRequest();
		req.setStreamName(streamName);
		req.setPartitionKey(partitionKey);
		req.setData(ByteBuffer.wrap(data));
		if(!StringUtils.isNullOrEmpty(explicitHaskKey)) {
			req.setExplicitHashKey(explicitHaskKey);
		}
		PutRecordResult response = akc.putRecord(req);
		printObject(response);
	}
	
	public void putRecords(String streamName, List<PutRecordsRequestEntry> records) {
		seperator("putRecords");
		PutRecordsRequest req = new PutRecordsRequest();
		req.setStreamName(streamName);
		req.setRecords(records);
		PutRecordsResult response = akc.putRecords(req);
		printObject(response);
	}
	public void getRecords(String StreamName) {
		GetRecordsRequest req = new GetRecordsRequest();
		req.setLimit(100);
		List<String> shardIterators=getShardIterators(StreamName, ShardIteratorType.TRIM_HORIZON);
		List<GetRecordsResult> response = new ArrayList<GetRecordsResult>();
		shardIterators.forEach(shardIterator -> {
			req.setShardIterator(shardIterator);
			GetRecordsResult getRecordResult=akc.getRecords(req);
			response.add(getRecordResult);
		});
		printObject(response);
	}
	private List<String> getShardIterators(String streamName, ShardIteratorType shardIteratorType){
		List<String> shardIteratorList = new ArrayList<>();
		List<Shard> shardList = getShards(streamName);
		shardList.forEach(shard -> {
			GetShardIteratorResult shardIteratorResult = getShardIteratorResult(streamName, shardIteratorType, shard);
			printObject(shardIteratorResult);
			shardIteratorList.add(shardIteratorResult.getShardIterator());
		});
		return shardIteratorList;
		
	}
	private GetShardIteratorResult getShardIteratorResult(String StreamName, ShardIteratorType shardIteratorType, Shard shard) {
		return akc.getShardIterator(StreamName, shard.getShardId(), shardIteratorType.toString());
	}
	private List<Shard> getShards(String streamName){
		DescribeStreamResult req = describeStream(streamName, null, null);
		return req.getStreamDescription().getShards();
	}
}
