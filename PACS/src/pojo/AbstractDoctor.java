package pojo;

/**
 * AbstractDoctor entity provides the base persistence definition of the Doctor
 * entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractDoctor implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String sex;
	private String office;
	private String title;

	// Constructors

	/** default constructor */
	public AbstractDoctor() {
	}

	/** minimal constructor */
	public AbstractDoctor(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	/** full constructor */
	public AbstractDoctor(Integer id, String name, String sex, String office,
			String title) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.office = office;
		this.title = title;
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

	public String getOffice() {
		return this.office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}