package com.xuenen.njxe.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Check {
	private int id;
	private String cyrxm; // 查阅人姓名
	private String djsj; // 登记时间
	private String dalymd; // 档案利用目的
	private int jyfs; // 借阅方式：1电子借阅，2实物借阅，3馆内查询
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getCyrxm() {
		return cyrxm;
	}

	public void setCyrxm(String cyrxm) {
		this.cyrxm = cyrxm;
	}

	public String getDjsj() {
		return djsj;
	}

	public void setDjsj(String djsj) {
		this.djsj = djsj;
	}

	public String getDalymd() {
		return dalymd;
	}

	public void setDalymd(String dalymd) {
		this.dalymd = dalymd;
	}

	public int getJyfs() {
		return jyfs;
	}

	public void setJyfs(int jyfs) {
		this.jyfs = jyfs;
	}

}