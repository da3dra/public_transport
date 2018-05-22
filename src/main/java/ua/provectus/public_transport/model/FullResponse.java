package ua.provectus.public_transport.model;

import org.codehaus.jackson.annotate.JsonProperty;


public class FullResponse {
	  @JsonProperty("success")
	    private boolean success;
	    @JsonProperty("data")
	    private Data data;

	    @Override
	    public String toString() {
	        return "Response{" + "success=" + success + ", data=" + data + '}';
	    }

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public Data getData() {
			return data;
		}

		public void setData(Data data) {
			this.data = data;
		}
	    
}
