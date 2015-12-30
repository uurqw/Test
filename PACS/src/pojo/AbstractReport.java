package pojo;

import java.sql.Timestamp;

/**
 * AbstractReport entity provides the base persistence definition of the Report
 * entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractReport implements java.io.Serializable {

	// Fields

	private Integer reportId;
	private Integer patId;
	private Integer docId;
	private Boolean fever;
	private Boolean vomit;
	private Boolean diarrhea;
	private Boolean cough;
	private String result;
	private Timestamp time;

	// Constructors

	/** default constructor */
	public AbstractReport() {
	}

	/** minimal constructor */
	public AbstractReport(Integer reportId, Integer patId, Integer docId,
			Boolean fever, Boolean vomit, Boolean diarrhea, Boolean cough,
			Timestamp time) {
		this.reportId = reportId;
		this.patId = patId;
		this.docId = docId;
		this.fever = fever;
		this.vomit = vomit;
		this.diarrhea = diarrhea;
		this.cough = cough;
		this.time = time;
	}

	/** full constructor */
	public AbstractReport(Integer reportId, Integer patId, Integer docId,
			Boolean fever, Boolean vomit, Boolean diarrhea, Boolean cough,
			String result, Timestamp time) {
		this.reportId = reportId;
		this.patId = patId;
		this.docId = docId;
		this.fever = fever;
		this.vomit = vomit;
		this.diarrhea = diarrhea;
		this.cough = cough;
		this.result = result;
		this.time = time;
	}

	// Property accessors

	public Integer getReportId() {
		return this.reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public Integer getPatId() {
		return this.patId;
	}

	public void setPatId(Integer patId) {
		this.patId = patId;
	}

	public Integer getDocId() {
		return this.docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	public Boolean getFever() {
		return this.fever;
	}

	public void setFever(Boolean fever) {
		this.fever = fever;
	}

	public Boolean getVomit() {
		return this.vomit;
	}

	public void setVomit(Boolean vomit) {
		this.vomit = vomit;
	}

	public Boolean getDiarrhea() {
		return this.diarrhea;
	}

	public void setDiarrhea(Boolean diarrhea) {
		this.diarrhea = diarrhea;
	}

	public Boolean getCough() {
		return this.cough;
	}

	public void setCough(Boolean cough) {
		this.cough = cough;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

}