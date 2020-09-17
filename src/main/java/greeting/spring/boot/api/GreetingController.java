/*
 * Copyright © 2017 IBM Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package greeting.spring.boot.api;

import java.io.IOException;
import java.util.List;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.IndexField;
import com.cloudant.client.api.model.IndexField.SortOrder;
import com.cloudant.client.api.model.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import greeting.spring.boot.model.ConsumptionView;
import greeting.spring.boot.model.Greeting;

@RestController
@RequestMapping("/users")
public class GreetingController {

	@Autowired
	private Database db;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Greeting> getAll() throws IOException {
			List<Greeting> allDocs = db.getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(Greeting.class);
			return allDocs;
	}

	@RequestMapping(method = RequestMethod.GET, value="/{id}")
	public @ResponseBody Greeting getGreeting(@PathVariable String id) throws IOException {
			Greeting greeting = db.find(Greeting.class, id);
			return greeting;
	}
	
	public List<Greeting> getDocByUserName(String name){
		// Get all documents from socialreviewdb
		 List<Greeting> allDocs = null;
		    try {
		        if (name == null) {
		            allDocs = db.getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(Greeting.class);
		        } else{
		            // create Index
		            // Create a design doc named designdoc
		            // A view named querybyitemIdView
		            // and an index named itemId
		        db.createIndex("querybyitemIdView","designdoc","json", new IndexField[]{new IndexField("name",SortOrder.asc)});
		        }
		            allDocs = db.findByIndex("{\"name\" : " + name + "}", Greeting.class);
		            
				    ObjectMapper mapper = new ObjectMapper();
					String docJson=mapper.writeValueAsString(allDocs);
		        }
		     catch (Exception e) {
		      System.out.println("Exception thrown : " + e.getMessage());
		      }
		   
		    return allDocs;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/login")
	public @ResponseBody JSONObject login(@RequestParam String number) throws IOException {
		
		List<Greeting> allDocs = db.getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(Greeting.class);
		JSONObject loginObject = new JSONObject();
		
		for (int i=0; i<allDocs.size() ;i++) {
			if(allDocs.get(i).getNumber().equals(number)) {
				loginObject.put("Login", allDocs.get(i).getName());
				return loginObject;
				}
			i++;
			}
		loginObject.put("Login", "can't find the user");
		return loginObject;
	    }

	@RequestMapping(method=RequestMethod.GET, value="/view")
	public @ResponseBody ConsumptionView getview(@RequestParam String name) throws IOException {
		Greeting doc=getDocByUserName(name).get(0);
		ConsumptionView view= new ConsumptionView();
		view.setNumber(doc.getNumber());
		view.setName(doc.getName());
		view.setAge(doc.getAge());
		view.setMartial_status(doc.getMartial_status());
		view.setPassport_request(doc.getPassport_request());
		view.setPenalties_balance(doc.getPenalties_balance());
		return view;	
	    }
	
	public JSONObject toJsonObject(Greeting doc) {
		JSONObject jobject = new JSONObject();
		jobject.put("number", doc.getNumber());
		jobject.put("name", doc.getName());
		jobject.put("age", doc.getAge());
		jobject.put("martial_status", doc.getMartial_status());
		jobject.put("passport_request", doc.getPassport_request());
		jobject.put("penalties_balance", doc.getPenalties_balance());
		return jobject;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/pay")
	public @ResponseBody JSONObject getPay(@RequestParam String name, @RequestParam String amount) throws JsonProcessingException {
		
		List<Greeting> doc=getDocByUserName(name);
		double updatedbalance = Double.valueOf(doc.get(0).getPenalties_balance());
		double balance = Double.valueOf(amount);
		updatedbalance = updatedbalance - balance;
		String updatedamount = Double.toString(updatedbalance);
		doc.get(0).setPenalties_balance(updatedamount);
		
		ObjectMapper mapper = new ObjectMapper();
		String docJson=mapper.writeValueAsString(doc);
		
		JSONObject jobject1 = new JSONObject();
		jobject1.put("penalties_balance", doc.get(0).getPenalties_balance());
		
		//JsonObject  jobject =  new JsonObject(docJson);
		JSONObject jobject = new JSONObject();
		jobject.put("_id", doc.get(0).get_id());
		jobject.put("_rev", doc.get(0).get_rev());
		jobject.put("number", doc.get(0).getNumber());
		jobject.put("name", doc.get(0).getName());
		jobject.put("age", doc.get(0).getAge());
		jobject.put("martial_status", doc.get(0).getMartial_status());
		jobject.put("passport_request", doc.get(0).getPassport_request());
		jobject.put("penalties_balance", doc.get(0).getPenalties_balance());
		
		db.update(jobject);
		return jobject1;
	   
	    }
	@RequestMapping(method=RequestMethod.GET, value="/passport")
	public @ResponseBody JSONObject getPassportStatus(@RequestParam String name) throws JsonProcessingException {
		
		List<Greeting> doc=getDocByUserName(name);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String time= ""+timestamp;
		doc.get(0).setPassport_request(time);
		
		ObjectMapper mapper = new ObjectMapper();
		String docJson=mapper.writeValueAsString(doc);
		
		JSONObject jobject1 = new JSONObject();
		jobject1.put("passport_request", doc.get(0).getPassport_request());
		
		
		//JsonObject  jobject =  new JsonObject(docJson);
		JSONObject jobject = new JSONObject();
		jobject.put("_id", doc.get(0).get_id());
		jobject.put("_rev", doc.get(0).get_rev());
		jobject.put("number", doc.get(0).getNumber());
		jobject.put("name", doc.get(0).getName());
		jobject.put("age", doc.get(0).getAge());
		jobject.put("martial_status", doc.get(0).getMartial_status());
		jobject.put("passport_request", doc.get(0).getPassport_request());
		jobject.put("penalties_balance", doc.get(0).getPenalties_balance());
		
		db.update(jobject);
		return jobject1;
	   
	    }

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody String add(@RequestBody Greeting greeting) {
			Response response = db.post(greeting);
			String id = response.getId();
			return id;
	}

}