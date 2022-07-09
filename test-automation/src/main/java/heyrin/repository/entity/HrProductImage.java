package heyrin.repository.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the hr_product_image database table.
 * 
 */
@Entity
@Table(name="hr_product_image")
@NamedQuery(name="HrProductImage.findAll", query="SELECT h FROM HrProductImage h")
public class HrProductImage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_time")
	private Date createdTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="deprecated_time")
	private Date deprecatedTime;

	private String location;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_time")
	private Date updatedTime;

	//bi-directional many-to-one association to HrProductImageAssignment
	@OneToMany(mappedBy="hrProductImage")
	private List<HrProductImageAssignment> hrProductImageAssignments;

	public HrProductImage() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getDeprecatedTime() {
		return this.deprecatedTime;
	}

	public void setDeprecatedTime(Date deprecatedTime) {
		this.deprecatedTime = deprecatedTime;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public List<HrProductImageAssignment> getHrProductImageAssignments() {
		return this.hrProductImageAssignments;
	}

	public void setHrProductImageAssignments(List<HrProductImageAssignment> hrProductImageAssignments) {
		this.hrProductImageAssignments = hrProductImageAssignments;
	}

	public HrProductImageAssignment addHrProductImageAssignment(HrProductImageAssignment hrProductImageAssignment) {
		getHrProductImageAssignments().add(hrProductImageAssignment);
		hrProductImageAssignment.setHrProductImage(this);

		return hrProductImageAssignment;
	}

	public HrProductImageAssignment removeHrProductImageAssignment(HrProductImageAssignment hrProductImageAssignment) {
		getHrProductImageAssignments().remove(hrProductImageAssignment);
		hrProductImageAssignment.setHrProductImage(null);

		return hrProductImageAssignment;
	}

}