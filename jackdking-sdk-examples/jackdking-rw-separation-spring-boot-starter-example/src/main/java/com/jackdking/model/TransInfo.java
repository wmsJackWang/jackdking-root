package com.jackdking.model;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class TransInfo {

	private Long id;

	public String userid;
	public Long orderid;
	public Timestamp ordertime;

}
