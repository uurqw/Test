package pojo;

/**
 * Doctor entity. @author MyEclipse Persistence Tools
 */
public class Doctor extends AbstractDoctor implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Doctor() {
	}

	/** minimal constructor */
	public Doctor(Integer id, String name) {
		super(id, name);
	}

	/** full constructor */
	public Doctor(Integer id, String name, String sex, String office,
			String title) {
		super(id, name, sex, office, title);
	}

}
