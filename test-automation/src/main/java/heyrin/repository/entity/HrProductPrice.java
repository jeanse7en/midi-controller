package heyrin.repository.entity;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the hr_product_price database table.
 * 
 */
@Data
@Entity
@Table(name="hr_product_price")
@NamedQuery(name="HrProductPrice.findAll", query="SELECT h FROM HrProductPrice h")
public class HrProductPrice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_time")
	private Date createdTime;

	private Double price;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_time")
	private Date updatedTime;

	//bi-directional many-to-one association to HrProduct
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="hr_product_id")
	private HrProduct hrProduct;

	public HrProductPrice() {
	}



}