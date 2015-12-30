package pojo;

import java.sql.Timestamp;

/**
 * Report entity. @author MyEclipse Persistence Tools
 */
public class Report extends AbstractReport implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Report() {
	}

	/** minimal constructor */
	public Report(Integer reportId, Integer patId, Integer docId,
			Boolean fever, Boolean vomit, Boolean diarrhea, Boolean cough,
			Timestamp time) {
		super(reportId, patId, docId, fever, vomit, diarrhea, cough, time);
	}

	/** full constructor */
	public Report(Integer reportId, Integer patId, Integer docId,
			Boolean fever, Boolean vomit, Boolean diarrhea, Boolean cough,
			String result, Timestamp time) {
		super(reportId, patId, docId, fever, vomit, diarrhea, cough, result,
				time);
	}

}
