/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.provectus.public_transport.model;

import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

public class StoppingResponse {
	@JsonProperty("success")
	private boolean success;
	@JsonProperty("data")
	private List<Stopping> stoppings;

	@Override
	public String toString() {
		return "Response{" + "success=" + success + ", data=" + stoppings + '}';
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<Stopping> getStoppings() {
		return stoppings;
	}

	public void setStoppings(List<Stopping> stoppings) {
		this.stoppings = stoppings;
	}

}
