package org.farm.fms.etntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author itp-cons-59
 *
 */
@Entity
@Table(name = "invoice", schema = "myfms")
public class Invoice implements Serializable {

	private static final long serialVersionUID = -902114150480507254L;

	private Integer invoiceNum;
	private Sales salesId;
	private String customerName;
	private Date date;

	public Invoice() {
		// required by JPA
	}

	@Id
	@SequenceGenerator(name = "myfms.invoice_invoice_num_seq", sequenceName = "myfms.invoice_invoice_num_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myfms.invoice_invoice_num_seq")
	@Column(name = "invoice_num", unique = true, nullable = false)
	public Integer getInvoiceNum() {
		return invoiceNum;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_sales")
	public Sales getSalesId() {
		return salesId;
	}

	@Column(name = "cunstomer_name")
	public String getCustomerName() {
		return customerName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dates")
	public Date getDate() {
		return date;
	}

	public void setInvoiceNum(Integer invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public void setSalesId(Sales salesId) {
		this.salesId = salesId;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
