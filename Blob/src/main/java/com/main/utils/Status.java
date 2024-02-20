package com.main.utils; 

public class Status  {

	public String status;

	public String message;

	public Object data;

	public Status (String status, String message, Object data){
 		this.status = status;
		this.message = message;
		this.data = data;
 	}

	public Status (){
  	}

	public void setStatus(String status) {
		this.status = status ;
	}

	public void setMessage(String message) {
		this.message = message ;
	}

	public void setData(Object data) {
		this.data = data ;
	}

	public String getStatus() {
		return this.status ;
	}

	public String getMessage() {
		return this.message ;
	}

	public Object getData() {
		return this.data ;
	}

}
