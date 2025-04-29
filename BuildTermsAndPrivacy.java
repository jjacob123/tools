/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.programs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

/**
 *
 * @author jeremyjacobs
 */
public class BuildTermsAndPrivacy {

    private static String ROOT_PATH = "/templates/";
    private static String PRIVACY_TEMPLATE = ROOT_PATH + "PrivacyPolicy_Template.txt";
    private static String TERMS_TEMPLATE = ROOT_PATH + "TermsAndAgreements_Template.txt";
    private static String CLIENT_DIR = ROOT_PATH + "Food Truck/";
    private static String CLIENT_PROPERTIES = CLIENT_DIR + "client_properties.txt";
    private static String CLIENT_TERMS_FILENAME = CLIENT_DIR + "Terms.txt";
    private static String CLIENT_PRIVACY_FILENAME = CLIENT_DIR + "Privacy.txt";
    private Map<String,String> properties;

    public static void main(String[] args) {
	try {
	    var runner = new BuildTermsAndPrivacy();
	    runner.loadProperties();
	    runner.mainProcess();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }
    
    public void loadProperties() throws Exception {
	properties = new HashMap<>();
	List<String> records = FileUtils.readLines(new File(CLIENT_PROPERTIES));
	for (String line : records) {
	    String[] s = line.split("=");
	    properties.put("##" + s[0] + "##", s[1]);
	}
    }

    public void mainProcess() throws Exception {
	List<String> records = FileUtils.readLines(new File(PRIVACY_TEMPLATE));
	List<String> writeLines = new ArrayList<>();
	for (String line : records) {
	    writeLines.add(modifyLine(line));
	}
	writeBean(CLIENT_PRIVACY_FILENAME, writeLines);
	records = FileUtils.readLines(new File(TERMS_TEMPLATE));
	writeLines.clear();
	for (String line : records) {
	    writeLines.add(modifyLine(line));
	}
	writeBean(CLIENT_TERMS_FILENAME, writeLines);	

    }

    private String modifyLine(String line) {
	String newLine = line;
	for (Map.Entry<String, String> entry : properties.entrySet()) {
	    newLine = newLine.replaceAll(entry.getKey(), entry.getValue());
	}
	return newLine;
    }

    private void writeBean(String fileName, List<String> lines) throws IOException {
	FileUtils.writeLines(new File(fileName), lines);
    }

}
