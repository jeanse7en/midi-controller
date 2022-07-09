package heyrin.repository.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the hr_product_arrangement database table.
 * 
 */
@Entity
@Table(name="hr_product_arrangement")
@NamedQuery(name="HrProductArrangement.findAll", query="SELECT h FROM HrProductArrangement h")
public class HrProductArrangement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_time")
	private Date createdTime;

	private String order;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_time")
	private Date updatedTime;

	//bi-directional many-to-one association to HrProduct
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="hr_product_id")
	private HrProduct hrProduct;

	//bi-directional many-to-one association to HrProductCategory
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="hr_product_category_id")
	private HrProductCategory hrProductCategory;

	public HrProductArrangement() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getOrder() {
		return this.order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Date getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public HrProduct getHrProduct() {
		return this.hrProduct;
	}

	public void setHrProduct(HrProduct hrProduct) {
		this.hrProduct = hrProduct;
	}

	public HrProductCategory getHrProductCategory() {
		return this.hrProductCategory;
	}

	public void setHrProductCategory(HrProductCategory hrProductCategory) {
		this.hrProductCategory = hrProductCategory;
	}

}