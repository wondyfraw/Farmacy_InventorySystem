package org.farm.entity.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.farm.fms.entity.ejb.ExpenseEJB;
import org.farm.fms.etntity.Expense;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "expenseBean")
@ViewScoped
public class ExpenseBean extends AbstructSessionBean {

	Log log = LogFactory.getLog(ExpenseBean.class);

	private Expense expense;
	private Expense sessionExpense;
	private boolean showPanel;
	private List<Expense> expenseList;
	private List<Expense> sessionExpenseList;
	private String amount;
	private String userName;
	private Double totalExpense;
	private List<Expense> expenseListByDate;
	private Date fromExpenseDate;
	private Date toExpenseDate;
	private UploadedFile fileUpload;
	private String attachFileName;
	private Double total;
	private boolean showMessage;

	@EJB
	private ExpenseEJB expenseEJB;

	@PostConstruct
	public void init() {
		expense = new Expense();
		expenseList = new ArrayList<Expense>();
		sessionExpense = new Expense();
		userName = loginAuthenticationBean.getEmail();
		sessionExpenseList = new ArrayList<Expense>();
		expenseListByDate = new ArrayList<Expense>();
		this.total = 200.00;
		List<Expense> totalExpenseList = new ArrayList<Expense>();
		totalExpense = (double) 0;
		totalExpenseList = expenseEJB.findAll();
		for (Expense element : totalExpenseList)
			totalExpense = totalExpense + element.getAmount();
	}

	public void registerExpense() {

		for (Expense element : sessionExpenseList) {
			expenseEJB.persistEntity(element);
		}
		sessionExpenseList.removeAll(sessionExpenseList);
		showPanel = false;
		showMessage = true;
		log.info("[Successfully Register Expense] " + "Performed by " + userName);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successfully Register Expense"));

	}

	// Add expense to the session
	public void addToSession() {
		Expense newExpense = new Expense();
		newExpense.setUserName(userName);
		if (amount != null) {
			Double amountVal = Double.parseDouble(amount);
			newExpense.setDescription(expense.getDescription());
			newExpense.setExpenseDate(expense.getExpenseDate());
			newExpense.setAmount(amountVal);
			newExpense.setInvoiceNumber(expense.getInvoiceNumber());
			newExpense.setFileName(fileUpload.getFileName());
			newExpense.setMimType(fileUpload.getContentType());
			newExpense.setDataFile(fileUpload.getContents());
			sessionExpenseList.add(newExpense);
			fileUpload = null;
			showPanel = true;
			log.info("Successfully put expense to session cart  " + " Amount =" + newExpense.getAmount()
					+ "  Description = " + newExpense.getDescription());
		}
	}

	// Delete single expense from session cart
	public void deleteExpenseSessionCart() {
		if (this.sessionExpense != null) {
			for (Expense elemet : sessionExpenseList) {
				if (sessionExpense.equals(elemet)) {
					sessionExpenseList.remove(sessionExpense);
					if (sessionExpenseList.size() < 1) {
						showPanel = false;
						break;
					}
					log.info("Delete expense successfully from from session cart!" + "Desctiption "
							+ elemet.getDescription() + " " + "Amount = " + elemet.getAmount());
				}
			}
		}
	}

	// Fillter expense by range of date
	public void filterExpenseByDate() {

		expenseListByDate = expenseEJB.findExpenseByFilter(fromExpenseDate, toExpenseDate);
		if (expenseListByDate.size() > 0) {
			showPanel = true;
		}
	}

	public void totalAmount() {
		if (expenseListByDate.size() > 0) {
			for (Expense expense : expenseListByDate) {
				this.total = this.total + expense.getAmount();
			}
		}
	}

	/** Uoload attached file */
	public void uploadAttachFile(FileUploadEvent event) {

		fileUpload = event.getFile();
		attachFileName = fileUpload.getFileName();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Upload file correctly "));
	}

	public Expense getExpense() {
		return expense;
	}

	public void setExpense(Expense expense) {
		this.expense = expense;
	}

	public boolean isShowPanel() {
		return showPanel;
	}

	public void setShowPanel(boolean showPanel) {
		this.showPanel = showPanel;
	}

	public List<Expense> getExpenseList() {
		return expenseList;
	}

	public void setExpenseList(List<Expense> expenseList) {
		this.expenseList = expenseList;
	}

	public Expense getSessionExpense() {
		return sessionExpense;
	}

	public void setSessionExpense(Expense sessionExpense) {
		this.sessionExpense = sessionExpense;
	}

	public List<Expense> getSessionExpenseList() {
		return sessionExpenseList;
	}

	public void setSessionExpenseList(List<Expense> sessionExpenseList) {
		this.sessionExpenseList = sessionExpenseList;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Double getTotalExpense() {
		return totalExpense;
	}

	public void setTotalExpense(Double totalExpense) {
		this.totalExpense = totalExpense;
	}

	public List<Expense> getExpenseListByDate() {
		return expenseListByDate;
	}

	public void setExpenseListByDate(List<Expense> expenseListByDate) {
		this.expenseListByDate = expenseListByDate;
	}

	public Date getFromExpenseDate() {
		return fromExpenseDate;
	}

	public void setFromExpenseDate(Date fromExpenseDate) {
		this.fromExpenseDate = fromExpenseDate;
	}

	public Date getToExpenseDate() {
		return toExpenseDate;
	}

	public void setToExpenseDate(Date toExpenseDate) {
		this.toExpenseDate = toExpenseDate;
	}

	public UploadedFile getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(UploadedFile fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getAttachFileName() {
		return attachFileName;
	}

	public void setAttachFileName(String attachFileName) {
		this.attachFileName = attachFileName;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public boolean isShowMessage() {
		return showMessage;
	}

	public void setShowMessage(boolean showMessage) {
		this.showMessage = showMessage;
	}

}
