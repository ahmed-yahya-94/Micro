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

package greeting.spring.boot.model;

public class Greeting {
	
	/**
	 * Revision of the document in the cloudant database. Cloudant will create this
	 * value for new documents.
	 */
	
	private String _id;

	private String _rev;
	
	private String number;
	
	private String name;
	
	private String age;
	
	private String martial_status;

	private String passport_request;
	
	private String penalties_balance;
	
	/**
	 * ID of the document in the cloudant database Cloudant will create this value
	 * for new documents.
	 */

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_rev() {
		return _rev;
	}

	public void set_rev(String _rev) {
		this._rev = _rev;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getMartial_status() {
		return martial_status;
	}

	public void setMartial_status(String martial_status) {
		this.martial_status = martial_status;
	}

	public String getPassport_request() {
		return passport_request;
	}

	public void setPassport_request(String passport_request) {
		this.passport_request = passport_request;
	}

	public String getPenalties_balance() {
		return penalties_balance;
	}

	public void setPenalties_balance(String penalties_balance) {
		this.penalties_balance = penalties_balance;
	}
}
