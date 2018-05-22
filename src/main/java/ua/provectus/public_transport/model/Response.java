/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.provectus.public_transport.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response{
	@JsonProperty("success")
	private boolean success;
	@JsonProperty("list")
	private List<Route> list;

	@Override
	public String toString() {
		return "Response{" + "success=" + success + ", list=" + list + '}';
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<Route> getList() {
		return list;
	}

	public void setList(List<Route> list) {
		this.list = list;
	}

}
