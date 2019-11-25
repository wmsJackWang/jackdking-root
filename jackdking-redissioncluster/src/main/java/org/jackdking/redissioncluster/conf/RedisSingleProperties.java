package org.jackdking.redissioncluster.conf;

//@Data
//@ToString
public class RedisSingleProperties {
    private  String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "RedisSingleProperties [address=" + address + "]";
	}
    
}