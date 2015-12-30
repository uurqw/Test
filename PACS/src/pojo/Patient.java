package pojo;

/**
 * Patient entity. @author MyEclipse Persistence Tools
 */
public class Patient extends AbstractPatient implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Patient() {
	}

	/** minimal constructor */
	public Patient(Integer id, String name, Integer cardId) {
		super(id, name, cardId);
	}

	/** full constructor */
	public Patient(Integer id, String name, String sex, Integer age,
			Integer cardId, Integer admissionNum, Integer bedNum) {
		super(id, name, sex, age, cardId, admissionNum, bedNum);
	}

}
