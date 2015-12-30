package pojo;

/**
 * AbstractPatient entity provides the base persistence definition of the
 * Patient entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractPatient implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String sex;
	private Integer age;
	private Integer cardId;
	private Integer admissionNum;
	private Integer bedNum;

	// Constructors

	/** default constructor */
	public AbstractPatient() {
	}

	/** minimal constructor */
	public AbstractPatient(Integer id, String name, Integer cardId) {
		this.id = id;
		this.name = name;
		this.cardId = cardId;
	}

	/** full constructor */
	public AbstractPatient(Integer id, String name, String sex, Integer age,
			Integer cardId, Integer admissionNum, Integer bedNum) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.cardId = cardId;
		this.admissionNum = admissionNum;
		this.bedNum = bedNum;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getCardId() {
		return this.cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	public Integer getAdmissionNum() {
		return this.admissionNum;
	}

	public void setAdmissionNum(Integer admissionNum) {
		this.admissionNum = admissionNum;
	}

	public Integer getBedNum() {
		return this.bedNum;
	}

	public void setBedNum(Integer bedNum) {
		this.bedNum = bedNum;
	}

}