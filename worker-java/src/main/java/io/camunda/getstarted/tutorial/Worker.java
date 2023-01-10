
package io.camunda.getstarted.tutorial;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import java.util.ArrayList;

@SpringBootApplication
@EnableZeebeClient
public class Worker {
	static ArrayList<String> DB = new ArrayList<String>();	

  public static void main(String[] args) {
    SpringApplication.run(Worker.class, args);
    DB.add("Viktor Baumann");
    DB.add("Timo Lingnau");
  }
  
  @JobWorker(type = "checkIfCustomerInDB")
  public Map<String, Object> checkIfCustomerInDB(final ActivatedJob job) throws ParseException {

      // Do the business logic
      String temp = job.getVariables();
      JSONParser parser = new JSONParser();
      JSONObject json = (JSONObject) parser.parse(temp);
      String name = (String) json.get("name"); 
      boolean exists = false; 
      for (String i : DB) {
          if (i.equals(name)) {
        	  exists = true;
        	  System.out.println("Customer already exists in Database!");
          }
        }
      // Probably add some process variables
      HashMap<String, Object> variables = new HashMap<>();
      variables.put("exists", exists);
      return variables;
  }
  
  @JobWorker(type = "addCustomerInDB")
  public Map<String, Object> addCustomerInDB(final ActivatedJob job) throws ParseException {

      // Do the business logic

      String temp = job.getVariables();
      JSONParser parser = new JSONParser();
      JSONObject json = (JSONObject) parser.parse(temp);
      String name = (String) json.get("name"); 
      DB.add(name);
      // Probably add some process variablesaddCustomerInDB
      HashMap<String, Object> variables = new HashMap<>();
      variables.put("resultValue1", 42);
      System.out.println("Customer was added to Database");
      System.out.println("The Customers in the Database are:");
      System.out.println(DB);
      return variables;
  }
  @JobWorker(type = "addInquiery")
  public Map<String, Object> addInquiery(final ActivatedJob job) throws ParseException {

      // Do the business logic

	  HashMap<String, Object> variables = new HashMap<>();
      variables.put("resultValue1", 42);
      System.out.println("Inquiry added to Customer");	
      return variables;
  }

}
