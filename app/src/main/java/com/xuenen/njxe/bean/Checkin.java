package com.xuenen.njxe.bean;

import java.util.Date;

/**
 * 登记表
 * @author zxy
 */
public class Checkin {

	private Integer id; // 主键ID
	private Integer userid; // 用户ID
	private String cyrxm; // 查阅人姓名
	private String lxdh; // 联系电话

	private Integer jyfs; // 借阅方式：1电子借阅，2实物借阅，3馆内查询
	private String bmdw; // 部门单位
	private String zjlx; // 证件类型
	private String zjhm; // 证件号码
	private String cxnr; // 查询内容
	private String dalymd; // 档案利用目的
	private Date djsj; // 登记时间
	private String jydh; // 借阅档号
	private String bz; // 备注
	private Integer jyts; // 借阅天数
	private Date qzrq; // 取走日期
	private String qtime; // 取走(上午/下午)几点钟
	private Date ghrq; // 归还日期
	private String qzrxm; // 取走人姓名
	private String qzsfz; // 取走人身份证
	private Integer flag; // 申请状态：0未处理，1待取走，2已借出，3已归还，4已查阅，5已拒绝
	private Integer dqf; // 到期标记：0未到期，1即将到期，2已过期
	
	
	private Integer caseType;//1文件查阅2录取名册3成绩查阅4基建档案5科研档案6声像档案7毕业名册8其它档案9财会档案
	private String province;//省份
	private String specialty;//专业
	private String startYear;//入学年份
	private String college;//学院
	private String endYear;//毕业年份
	private String projectInfo;//项目信息
	private String soundTime;//时间
	private String soundPlace;//地点
	private String soundPeople;//人物
	private String soundThing;//事件
	private String theme;//主题
	private String accountNum;//财会号码
	private String accountContent;//财会内容
	public Integer getCaseType() {
		return caseType;
	}

	public void setCaseType(Integer caseType) {
		this.caseType = caseType;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public String getProjectInfo() {
		return projectInfo;
	}

	public void setProjectInfo(String projectInfo) {
		this.projectInfo = projectInfo;
	}

	public String getSoundTime() {
		return soundTime;
	}

	public void setSoundTime(String soundTime) {
		this.soundTime = soundTime;
	}

	public String getSoundPlace() {
		return soundPlace;
	}

	public void setSoundPlace(String soundPlace) {
		this.soundPlace = soundPlace;
	}

	public String getSoundPeople() {
		return soundPeople;
	}

	public void setSoundPeople(String soundPeople) {
		this.soundPeople = soundPeople;
	}

	public String getSoundThing() {
		return soundThing;
	}

	public void setSoundThing(String soundThing) {
		this.soundThing = soundThing;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getAccountContent() {
		return accountContent;
	}

	public void setAccountContent(String accountContent) {
		this.accountContent = accountContent;
	}

	/**
	 * getter/setter
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getCyrxm() {
		return cyrxm;
	}

	public void setCyrxm(String cyrxm) {
		this.cyrxm = cyrxm;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public Integer getJyfs() {
		return jyfs;
	}

	public void setJyfs(Integer jyfs) {
		this.jyfs = jyfs;
	}

	public String getBmdw() {
		return bmdw;
	}

	public void setBmdw(String bmdw) {
		this.bmdw = bmdw;
	}

	public String getZjlx() {
		return zjlx;
	}

	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}

	public String getZjhm() {
		return zjhm;
	}

	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}

	public String getCxnr() {
		return cxnr;
	}

	public void setCxnr(String cxnr) {
		this.cxnr = cxnr;
	}

	public String getDalymd() {
		return dalymd;
	}

	public void setDalymd(String dalymd) {
		this.dalymd = dalymd;
	}

	public Date getDjsj() {
		return djsj;
	}

	public void setDjsj(Date djsj) {
		this.djsj = djsj;
	}

	public String getJydh() {
		return jydh;
	}

	public void setJydh(String jydh) {
		this.jydh = jydh;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Integer getJyts() {
		return jyts;
	}

	public void setJyts(Integer jyts) {
		this.jyts = jyts;
	}

	public Date getQzrq() {
		return qzrq;
	}

	public void setQzrq(Date qzrq) {
		this.qzrq = qzrq;
	}

	public String getQtime() {
		return qtime;
	}

	public void setQtime(String qtime) {
		this.qtime = qtime;
	}

	public Date getGhrq() {
		return ghrq;
	}

	public void setGhrq(Date ghrq) {
		this.ghrq = ghrq;
	}

	public String getQzrxm() {
		return qzrxm;
	}

	public void setQzrxm(String qzrxm) {
		this.qzrxm = qzrxm;
	}

	public String getQzsfz() {
		return qzsfz;
	}

	public void setQzsfz(String qzsfz) {
		this.qzsfz = qzsfz;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getDqf() {
		return dqf;
	}

	public void setDqf(Integer dqf) {
		this.dqf = dqf;
	}

	@Override
	public String toString() {
		return "Checkin{" +
				"userid=" + userid +
				", id=" + id +
				", cyrxm='" + cyrxm + '\'' +
				", lxdh='" + lxdh + '\'' +
				", jyfs=" + jyfs +
				", bmdw='" + bmdw + '\'' +
				", zjlx='" + zjlx + '\'' +
				", zjhm='" + zjhm + '\'' +
				", cxnr='" + cxnr + '\'' +
				", dalymd='" + dalymd + '\'' +
				", djsj=" + djsj +
				", jydh='" + jydh + '\'' +
				", bz='" + bz + '\'' +
				", jyts=" + jyts +
				", qzrq=" + qzrq +
				", qtime='" + qtime + '\'' +
				", ghrq=" + ghrq +
				", qzrxm='" + qzrxm + '\'' +
				", qzsfz='" + qzsfz + '\'' +
				", flag=" + flag +
				", dqf=" + dqf +
				", caseType=" + caseType +
				", province='" + province + '\'' +
				", specialty='" + specialty + '\'' +
				", startYear='" + startYear + '\'' +
				", college='" + college + '\'' +
				", endYear='" + endYear + '\'' +
				", projectInfo='" + projectInfo + '\'' +
				", soundTime='" + soundTime + '\'' +
				", soundPlace='" + soundPlace + '\'' +
				", soundPeople='" + soundPeople + '\'' +
				", soundThing='" + soundThing + '\'' +
				", theme='" + theme + '\'' +
				", accountNum='" + accountNum + '\'' +
				", accountContent='" + accountContent + '\'' +
				'}';
	}
}