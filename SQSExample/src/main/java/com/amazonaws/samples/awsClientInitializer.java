package com.amazonaws.samples;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class awsClientInitializer {
	private AWSCredentials credentials = null;
	protected Region region;
    
    public awsClientInitializer() {
    	try {
            credentials = new ProfileCredentialsProvider("default").getCredentials();
			region = Region.getRegion(Regions.US_WEST_2);
        } catch (Exception e) {
        	System.out.println(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (~/.aws/credentials), and is in valid format."+e);
        }
    }
    public AWSCredentials getCredentials() {
		return credentials;
	}
    public void setCredentials(AWSCredentials credentials) {
		this.credentials = credentials;
	}
    protected void seperator(String featureName) {
		System.out.println("\n\n*********************************************************************************\n\n");
		System.out.println("+++++++++++++++++++++");
		System.out.println(featureName);
		System.out.println("+++++++++++++++++++++");
	}
    protected void printObject(Object object) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(object));
	}
}
