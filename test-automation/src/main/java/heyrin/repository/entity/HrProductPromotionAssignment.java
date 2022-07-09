package heyrin.repository.entity;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the hr_product_promotion_assignment database table.
 * 
 */
@Data
@Entity
@Table(name="hr_product_promotion_assignment")
@NamedQuery(name="HrProductPromotionAssignment.findAll", query="SELECT h FROM HrProductPromotionAssignment h")
public class HrProductPromotionAssignment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_time")
	private Date createdTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="deprecated_time")
	private Date deprecatedTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_time")
	private Date updatedTime;

	private String value;

	//bi-directional many-to-one association to HrProduct
	@ManyToOne
	@JoinColumn(name="hr_product_id")
	private HrProduct hrProduct;

	//bi-directional many-to-one association to HrProductPromotion
	@ManyToOne
	@JoinColumn(name="hr_product_promotion_id")
	private HrProductPromotion hrProductPromotion;

	public HrProductPromotionAssignment() {
	}


}