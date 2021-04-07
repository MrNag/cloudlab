package com.amazonaws.samples;

//import java.awt.List;
import java.io.*;
import java.util.*;

import com.amazonaws.services.ec2.*;
import com.amazonaws.services.ec2.model.*;

public class KeyPairOperations extends awsClientInitializer{
	protected AmazonEC2 amazonEC2Client;
	public KeyPairOperations() {
		super();
		amazonEC2Client = new AmazonEC2Client(getCredentials());
		amazonEC2Client.setRegion(region);
	}
	public void createKeyPair(String keyName) {
		seperator("createKeyPair");
		CreateKeyPairRequest request = new CreateKeyPairRequest();
		request.withKeyName(keyName);
		CreateKeyPairResult response = amazonEC2Client.createKeyPair(request);
		KeyPair keyPair = response.getKeyPair();
		String privateKey = keyPair.getKeyMaterial();
		try {
			File f=new File("E:\\collegedata\\CP 2020-21\\Credentials\\"+keyName+ "_keypair.pem");
			FileWriter fileWriter = new FileWriter(f.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fileWriter);
			bw.write(privateKey);
			bw.close();
		}catch(Exception e) {}
		printObject(response);
	}
	public void describeKeyPairs(String keyName) {
		seperator("Describe Keypair");
		DescribeKeyPairsRequest request=new DescribeKeyPairsRequest();
		List<String> keyPairList = new ArrayList<String>();
		keyPairList.add(keyName);
		request.setKeyNames(keyPairList);
		DescribeKeyPairsResult result = amazonEC2Client.describeKeyPairs(request);
		printObject(result);
	}
	public void deleteKeyPair(String keyName) {
		seperator("Delete Keypair");
		DeleteKeyPairRequest request=new DeleteKeyPairRequest();
		request.setKeyName(keyName);
		DeleteKeyPairResult result = 
				amazonEC2Client.deleteKeyPair(request);
		printObject(result);
	}
}
