package heyrin.repository.entity;

import heyrin.service.dto.ImagePurpose;
import lombok.Data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the hr_product_image_assignment database table.
 * 
 */
@Entity
@Table(name="hr_product_image_assignment")
@NamedQuery(name="HrProductImageAssignment.findAll", query="SELECT h FROM HrProductImageAssignment h")
@Data
public class HrProductImageAssignment implements Serializable {
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

	@Enumerated(EnumType.STRING)
	private ImagePurpose purpose;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_time")
	private Date updatedTime;

	//bi-directional many-to-one association to HrProduct
	@ManyToOne
	@JoinColumn(name="hr_product_id")
	private HrProduct hrProduct;

	//bi-directional many-to-one association to HrProductImage
	@ManyToOne
	@JoinColumn(name="hr_product_image_id")
	private HrProductImage hrProductImage;

	public HrProductImageAssignment() {
	}

}