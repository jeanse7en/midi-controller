package heyrin.repository.entity;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the hr_product_promotion database table.
 * 
 */
@Data
@Entity
@Table(name="hr_product_promotion")
@NamedQuery(name="HrProductPromotion.findAll", query="SELECT h FROM HrProductPromotion h")
public class HrProductPromotion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String code;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_time")
	private Date createdTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="deprecated_time")
	private Date deprecatedTime;

	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_time")
	private Date updatedTime;

	//bi-directional many-to-one association to HrProductPromotionAssignment
	@OneToMany(mappedBy="hrProductPromotion")
	private List<HrProductPromotionAssignment> hrProductPromotionAssignments;

	public HrProductPromotion() {
	}

	public List<HrProductPromotionAssignment> getHrProductPromotionAssignments() {
		return this.hrProductPromotionAssignments;
	}

	public void setHrProductPromotionAssignments(List<HrProductPromotionAssignment> hrProductPromotionAssignments) {
		this.hrProductPromotionAssignments = hrProductPromotionAssignments;
	}

	public HrProductPromotionAssignment addHrProductPromotionAssignment(HrProductPromotionAssignment hrProductPromotionAssignment) {
		getHrProductPromotionAssignments().add(hrProductPromotionAssignment);
		hrProductPromotionAssignment.setHrProductPromotion(this);

		return hrProductPromotionAssignment;
	}

	public HrProductPromotionAssignment removeHrProductPromotionAssignment(HrProductPromotionAssignment hrProductPromotionAssignment) {
		getHrProductPromotionAssignments().remove(hrProductPromotionAssignment);
		hrProductPromotionAssignment.setHrProductPromotion(null);

		return hrProductPromotionAssignment;
	}

}