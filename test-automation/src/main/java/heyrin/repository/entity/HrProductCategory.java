package heyrin.repository.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the hr_product_category database table.
 * 
 */
@Entity
@Table(name="hr_product_category")
@NamedQuery(name="HrProductCategory.findAll", query="SELECT h FROM HrProductCategory h")
public class HrProductCategory implements Serializable {
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

	//bi-directional many-to-one association to HrProduct
	@OneToMany(mappedBy="hrProductCategory")
	private List<HrProduct> hrProducts;

	//bi-directional many-to-one association to HrProductArrangement
	@OneToMany(mappedBy="hrProductCategory")
	private List<HrProductArrangement> hrProductArrangements;

	public HrProductCategory() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public List<HrProduct> getHrProducts() {
		return this.hrProducts;
	}

	public void setHrProducts(List<HrProduct> hrProducts) {
		this.hrProducts = hrProducts;
	}

	public HrProduct addHrProduct(HrProduct hrProduct) {
		getHrProducts().add(hrProduct);
		hrProduct.setHrProductCategory(this);

		return hrProduct;
	}

	public HrProduct removeHrProduct(HrProduct hrProduct) {
		getHrProducts().remove(hrProduct);
		hrProduct.setHrProductCategory(null);

		return hrProduct;
	}

	public List<HrProductArrangement> getHrProductArrangements() {
		return this.hrProductArrangements;
	}

	public void setHrProductArrangements(List<HrProductArrangement> hrProductArrangements) {
		this.hrProductArrangements = hrProductArrangements;
	}

	public HrProductArrangement addHrProductArrangement(HrProductArrangement hrProductArrangement) {
		getHrProductArrangements().add(hrProductArrangement);
		hrProductArrangement.setHrProductCategory(this);

		return hrProductArrangement;
	}

	public HrProductArrangement removeHrProductArrangement(HrProductArrangement hrProductArrangement) {
		getHrProductArrangements().remove(hrProductArrangement);
		hrProductArrangement.setHrProductCategory(null);

		return hrProductArrangement;
	}

}