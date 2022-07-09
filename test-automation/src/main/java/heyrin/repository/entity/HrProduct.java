package heyrin.repository.entity;

import heyrin.dto.ProductStatus;
import heyrin.dto.ProductStatus2;
import heyrin.service.dto.ImagePurpose;
import lombok.Data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the hr_product database table.
 * 
 */
@Data
@Entity
@Table(name="hr_product")
@NamedQuery(name="HrProduct.findAll", query="SELECT h FROM HrProduct h")
public class HrProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String code;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_time")
	private Date createdTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="deprecated_time")
	private Date deprecatedTime;

	private String folder;

	private String name;

	@Enumerated(EnumType.STRING)
	private ProductStatus status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_time")
	private Date updatedTime;

	//bi-directional many-to-one association to HrProductCategory
	@ManyToOne
	@JoinColumn(name="hr_product_category_id")
	private HrProductCategory hrProductCategory;

	//bi-directional many-to-one association to HrProductArrangement
	@OneToMany(mappedBy="hrProduct")
	private List<HrProductArrangement> hrProductArrangements;

	//bi-directional many-to-one association to HrProductImageAssignment
	@OneToMany(mappedBy="hrProduct")
	private List<HrProductImageAssignment> hrProductImageAssignments;

	//bi-directional many-to-one association to HrProductPromotionAssignment
	@OneToMany(mappedBy="hrProduct")
	private List<HrProductPromotionAssignment> hrProductPromotionAssignments;


	//bi-directional many-to-one association to HrProductPrice
	@OneToMany(mappedBy="hrProduct")
	private List<HrProductPrice> hrProductPrices;


	public HrProduct() {
	}


}