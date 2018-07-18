package org.farm.fms.entity.ejb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.farm.fms.etntity.Dispensary;
import org.farm.fms.etntity.Dispensary_;
import org.farm.fms.etntity.Sales;
import org.farm.fms.etntity.Sales_;
import org.farm.fms.etntity.Store;
import org.farm.fms.etntity.Store_;
import org.farm.pojo.MapperPOJO;
import org.farm.pojo.SalesFilterPOJO;
import org.farm.utils.ConstantsSingleton;
import org.farm.utils.Utils;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.xml.XmlPage;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Stateless
public class SalesEJB extends AbstructHome<Sales, Integer> {

	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	// private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	// private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Sales findById(Integer id) {

		return findById(Sales.class, id);
	}

	/**
	 * 
	 * @param filter
	 * @return perform live search in the sales table
	 */
	public List<MapperPOJO> filterDrug(String filter) {
		List<MapperPOJO> results = new ArrayList<MapperPOJO>();

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
		Root<Dispensary> root = query.from(Dispensary.class);
		Join<Dispensary, Store> storeJoin = root.join(Dispensary_.store);
		/*
		 * query.select(cb.array(storeJoin.get(Store_.storeId), storeJoin.get(Store_.drugName),
		 * storeJoin.get(Store_.brand), storeJoin.get(Store_.bathcNumber), storeJoin.get(Store_.weight),
		 * storeJoin.get(Store_.manufacturingDate), storeJoin.get(Store_.expireDate), storeJoin.get(Store_.unit),
		 * storeJoin.get(Store_.registrationDate), storeJoin.get(Store_.salesPrice), storeJoin.get(Store_.packUnit),
		 * root.get(Dispensary_.idDispensary), root.get(Dispensary_.quantityInBox),
		 * root.get(Dispensary_.quantityPerUnit), root.get(Dispensary_.quantityPerTab),
		 * root.get(Dispensary_.quantityPerPack)));
		 */

		query = queryBuilder(root, query, cb);
		query.where(getFilterResult(cb, root, filter));
		query.orderBy(cb.desc(storeJoin.get(Store_.expireDate)));

		TypedQuery<Object[]> typedQuery = entityManager.createQuery(query);
		List<Object[]> searchResults = typedQuery.getResultList();
		results = getResults(searchResults);
		return results;

	}

	private List<MapperPOJO> getResults(List<Object[]> searchResults) {
		List<MapperPOJO> results = new ArrayList<MapperPOJO>();
		for (Object[] next : searchResults) {
			MapperPOJO mapperPOJO = new MapperPOJO();
			mapperPOJO.setStoreId(Integer.parseInt(next[0].toString()));
			mapperPOJO.setDrugName(next[1].toString());
			mapperPOJO.setBrand(next[2].toString());
			mapperPOJO.setBathcNumber(next[3].toString());
			mapperPOJO.setWeight(next[4].toString());
			mapperPOJO.setManufacturingDate((Date) next[5]);
			mapperPOJO.setExpireDate((Date) next[6]);
			mapperPOJO.setUnit(next[7].toString());
			mapperPOJO.setRegistrationDate((Date) next[8]);
			mapperPOJO.setSalesPrice(Double.parseDouble(next[9].toString()));
			mapperPOJO.setPackUnit(next[10].toString());
			mapperPOJO.setDispensaryId(Integer.parseInt(next[11].toString()));
			mapperPOJO.setQuantityInBox(Integer.parseInt(next[12].toString()));
			mapperPOJO.setQuantityPerUnit(Integer.parseInt(next[13].toString()));
			mapperPOJO.setQuantityPerUnitPack(Integer.parseInt(next[14].toString()));
			mapperPOJO.setQuantityPerPack(Integer.parseInt(next[15].toString()));
			results.add(mapperPOJO);
		}
		return results;
	}

	private Predicate getFilterResult(CriteriaBuilder cb, Root<Dispensary> root, String filter) {
		Predicate predicate = null;

		List<Predicate> wherePredicates = new ArrayList<Predicate>();
		Join<Dispensary, Store> storeJoin = root.join(Dispensary_.store);

		Date date = Calendar.getInstance().getTime();
		String today = new SimpleDateFormat("yyyy-MM-dd").format(date);
		Date todayWithoutTime = convertDate(today);

		if (filter != null && filter.length() >= 2) {
			wherePredicates.add(cb.or(cb.like(storeJoin.get(Store_.drugName), "%" + filter.trim() + "%"),
					cb.like(storeJoin.get(Store_.brand), "%" + filter.trim() + "%"),
					cb.like(storeJoin.get(Store_.packUnit), "%" + filter.trim() + "%"),
					cb.like(storeJoin.get(Store_.unit), "%" + filter.trim() + "%"),
					cb.greaterThanOrEqualTo(storeJoin.get(Store_.expireDate), convertDate(filter)),
					cb.equal(storeJoin.get(Store_.manufacturingDate), convertDate(filter))));

			// select only not expired drugs
			wherePredicates.add(cb.greaterThan(storeJoin.get(Store_.expireDate), todayWithoutTime));

			if (wherePredicates.size() > 0)
				predicate = cb.and(wherePredicates.toArray(new Predicate[wherePredicates.size()]));
		}

		return predicate;
	}

	public Date convertDate(String date) {
		Date filter = null;
		try {
			filter = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			log.equals("----Is not a correct date format-----");
		}

		return filter;
	}

	public List<MapperPOJO> getAllDrugs(Date date) {
		List<MapperPOJO> allDrugs = new ArrayList<MapperPOJO>();

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
		Root<Dispensary> root = query.from(Dispensary.class);
		Join<Dispensary, Store> storeJoin = root.join(Dispensary_.store);

		query = queryBuilder(root, query, cb);
		String today = new SimpleDateFormat("yyyy-MM-dd").format(date);
		Date dateWithoutTime = convertDate(today);

		// manage expire date of drugs
		query.where(cb.greaterThan(storeJoin.get(Store_.expireDate), dateWithoutTime));
		query.orderBy(cb.desc(storeJoin.get(Store_.expireDate)));
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(query);
		List<Object[]> searchResults = typedQuery.getResultList();
		allDrugs = getResults(searchResults);

		return allDrugs;
	}

	private CriteriaQuery<Object[]> queryBuilder(Root<Dispensary> root, CriteriaQuery<Object[]> query,
			CriteriaBuilder cb) {

		Join<Dispensary, Store> storeJoin = root.join(Dispensary_.store);
		query.select(cb.array(storeJoin.get(Store_.storeId), storeJoin.get(Store_.drugName),
				storeJoin.get(Store_.brand), storeJoin.get(Store_.bathcNumber), storeJoin.get(Store_.weight),
				storeJoin.get(Store_.manufacturingDate), storeJoin.get(Store_.expireDate), storeJoin.get(Store_.unit),
				storeJoin.get(Store_.registrationDate), storeJoin.get(Store_.salesPrice),
				storeJoin.get(Store_.packUnit), root.get(Dispensary_.idDispensary), root.get(Dispensary_.quantityInBox),
				root.get(Dispensary_.quantityPerUnit), root.get(Dispensary_.quantityPerTab),
				root.get(Dispensary_.totalStrip), root.get(Dispensary_.quantityPerPack)));

		return query;
	}

	// select expired drugs from store
	public List<MapperPOJO> findExpiredDrugsFromDispensary(Date date) {
		List<MapperPOJO> drugList = new ArrayList<MapperPOJO>();

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
		Root<Dispensary> root = query.from(Dispensary.class);
		Join<Dispensary, Store> storeJoin = root.join(Dispensary_.store);
		query = queryBuilder(root, query, cb);

		String today = new SimpleDateFormat("yyyy-MM-dd").format(date);
		Date filterDate = convertDate(today);
		query.where(cb.lessThan(storeJoin.get(Store_.expireDate), filterDate));

		TypedQuery<Object[]> typeQuery = entityManager.createQuery(query);
		List<Object[]> resultList = typeQuery.getResultList();

		drugList = getResults(resultList);
		return drugList;
	}

	// find sales history
	public List<Sales> findSalesOrderByDate() {
		List<Sales> salesList = new ArrayList<Sales>();
		Date today = Calendar.getInstance().getTime();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(today);
		Date now = convertDate(date);

		TypedQuery<Sales> salesQuery = entityManager.createNamedQuery("findSalesHistoryOrederByDate", Sales.class);
		salesQuery.setParameter("date", now);
		salesList = salesQuery.getResultList();

		return salesList;
	}

	public Predicate getSalesHistoryFilterResult(CriteriaBuilder cb, Root<Sales> root, String filter) {
		Predicate predicate = null;

		List<Predicate> wherePredicates = new ArrayList<Predicate>();
		if (filter != null && filter.length() >= 2) {
			wherePredicates.add(cb.or(cb.like(root.get(Sales_.drugName), "%" + filter + "%"),
					cb.like(root.get(Sales_.brand), "%" + filter + "%"),
					cb.like(root.get(Sales_.batchNumber), "%" + filter + "%"),
					cb.like(root.get(Sales_.unit), "%" + filter + "%"),
					cb.equal(root.get(Sales_.registrationdate), convertDate(filter))));

			if (wherePredicates.size() > 0)
				predicate = cb.and(wherePredicates.toArray(new Predicate[wherePredicates.size()]));
		}
		return predicate;
	}

	// filter sales history
	public List<Sales> salesDrugByFilterCriteria(String filter) {
		List<Sales> salesList = new ArrayList<Sales>();

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Sales> query = cb.createQuery(Sales.class);
		Root<Sales> root = query.from(Sales.class);

		query.where(getSalesHistoryFilterResult(cb, root, filter));
		query.orderBy(cb.desc(root.get(Sales_.registrationdate)));

		TypedQuery<Sales> typedQuery = entityManager.createQuery(query);
		salesList = typedQuery.getResultList();

		return salesList;
	}

	// Sales report
	public List<Sales> filterDrugsByCriteria(SalesFilterPOJO filterPOJO) {
		List<Sales> searchResult = new ArrayList<Sales>();

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Sales> query = cb.createQuery(Sales.class);
		Root<Sales> root = query.from(Sales.class);

		List<Predicate> whereCondition = getWhereCondtion(cb, root, filterPOJO);
		query.where(whereCondition.toArray(new Predicate[whereCondition.size()]));
		query.orderBy(cb.desc(root.get(Sales_.registrationdate)));

		TypedQuery<Sales> typedQuery = entityManager.createQuery(query);
		searchResult = typedQuery.getResultList();

		return searchResult;
	}

	public List<Predicate> getWhereCondtion(CriteriaBuilder cb, Root<Sales> root, SalesFilterPOJO filterPOJO) {

		List<Predicate> wherePredicates = new ArrayList<Predicate>();
		if (filterPOJO.getFromDate() != null) {
			String date = new SimpleDateFormat("yyyy-MM-dd").format(filterPOJO.getFromDate());
			Date fromDate = convertDate(date);
			wherePredicates.add(cb.greaterThanOrEqualTo(root.get(Sales_.registrationdate), fromDate));
		}

		if (filterPOJO.getToDate() != null) {
			String date = new SimpleDateFormat("yyy-MM-dd").format(filterPOJO.getToDate());
			Date toDate = convertDate(date);
			wherePredicates.add(cb.lessThanOrEqualTo(root.get(Sales_.registrationdate), toDate));
		}

		if (filterPOJO.getDrugName() != null) {
			wherePredicates.add(cb.like(root.get(Sales_.drugName), "%" + filterPOJO.getDrugName() + "%"));
		}

		return wherePredicates;
	}

	public InputStream getPrintHtmlFile(SalesFilterPOJO searchParm) {
		String result = null;
		WebClient webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(false);
		Page requestedPage = null;
		String fDate = null;
		String tDate = null;
		String url = "";
		if (searchParm.getFromDate() != null)
			fDate = new SimpleDateFormat("yyyy/MM/dd").format(searchParm.getFromDate());
		if (searchParm.getToDate() != null)
			tDate = new SimpleDateFormat("yyyy/MM/dd").format(searchParm.getToDate());
		if (searchParm.getDrugName() != null)
			url = url + searchParm.getDrugName();

		try {
			requestedPage = webClient.getPage(
					ConstantsSingleton.BASE_DOMIAN + ConstantsSingleton.CONTENT_APP + "/_common/print_report.xhtml"
							+ "?fDate=" + fDate + "&tDate=" + tDate + "&dname=" + searchParm.getDrugName());
		} catch (Exception e) {
			String errMsg = "Error during retrieving XML document : /_common/print_report.xhtml" + "?fDate=" + fDate
					+ "&tDate=" + tDate + "&dname=" + searchParm.getDrugName();
			log.error(errMsg);
			log.error("Original Exception: " + e.getClass() + "-" + e.getLocalizedMessage());
			e.printStackTrace();
		}

		if (requestedPage != null) {
			if (requestedPage instanceof XmlPage) {
				result = ((XmlPage) requestedPage).asXml();
			} else if (requestedPage instanceof HtmlPage) {
				result = ((HtmlPage) requestedPage).asXml();
			}
		}
		return htmlToPDF(result);
	}

	private InputStream htmlToPDF(String html) {
		ByteArrayOutputStream outPut = new ByteArrayOutputStream();
		Document document = new Document();

		try {
			PdfWriter writer = PdfWriter.getInstance(document, outPut);
			document.setPageSize(PageSize.A4);
			document.setMargins(10, 10, 15, 10); // L, R, T, B
			document.open();

			// ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			// String imagePath = externalContext.getRealPath("/resources/images/calabria/" + nomeImmagine);
			// Image img = Image.getInstance(imagePath);
			// img.scalePercent(50f);// W i numeri magici
			// document.add(img);

			StringBuilder HTMLtoPDF = new StringBuilder();
			HTMLtoPDF.append(Utils.htmltoXHTML(html));

			InputStream stream = new ByteArrayInputStream(HTMLtoPDF.toString().getBytes("UTF-8"));
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, stream);
			document.close();
		} catch (DocumentException e) {
			log.error("DocumentException Error generating pdf from HTML:" + e);
		} catch (Exception e) {

			log.error("DocumentException Error generating pdf from HTML:" + e);
		}

		byte[] data = outPut.toByteArray();
		return new ByteArrayInputStream(data);
	}

	public void generateSalesPDF(List<Sales> salesList, String absolutePath) throws FileNotFoundException, JRException {

		try {
			byte[] rawData = generateByteStream(salesList, absolutePath);
			log.info("Print sales report pdf finish successfully");
		} catch (Exception e) {
			log.error("Fail to generate PDF ");
		}
	}

	public byte[] generateByteStream(List<Sales> salesList, String rootPath) throws FileNotFoundException, JRException {

		final ByteArrayOutputStream bob = new ByteArrayOutputStream();

		try {
			String absolutePath = rootPath + "WEB-INF" + File.separator + "report";

			File file = new File(absolutePath + File.separator + "sales_report.jrxml");
			// File file = new File("C:/jboss-7.1.3/standalone/deployments/Test.war/WEB-INF/report/sales_report.jrxml");
			// File file = new File(rootPath);
			FileInputStream fileInput = new FileInputStream(file);
			/*
			 * int content; while ((content = fileInput.read()) != -1) { System.out.println(content); break; }
			 */
			// InputStream is = this.getClass().getResourceAsStream("/org/farm/fms/entity/ejb/sales_report.jrxml");

			// compile jasper jrxml file
			final JasperReport jasperReport = JasperCompileManager.compileReport(fileInput);
			final Map<String, Object> parameters = getParameterProgramma(entityManager, salesList, absolutePath);
			// Method to get the unwrapped connection
			Session session = entityManager.unwrap(Session.class);
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					JasperPrint jasperPrint;

					try {
						jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
						JasperExportManager.exportReportToPdfStream(jasperPrint, bob);
					} catch (JRException e) {

						e.printStackTrace();
					} finally {
						connection.close();
					}
				}
			});

			log.info("Generazione file pdf con JasperReport");

		} catch (Exception e) {
			log.error("File not found exception" + "Impossible to travel with the give path");
			log.error("Generation of pdf bye array on the given jasper file is failed");
			return null;
		}
		return bob.toByteArray();
	}

	public Map<String, Object> getParameterProgramma(EntityManager em, List<Sales> salesList, String absolutePath) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(JRParameter.REPORT_LOCALE, Locale.ENGLISH);
		parameters.put("REPORT_DIR", absolutePath);

		for (Sales sale : salesList) {
			parameters.put("quantity", sale.getQuantity());
			parameters.put("weight", sale.getWeight());
			parameters.put("brand", sale.getBrand());
			parameters.put("batch_number", sale.getBatchNumber());
			parameters.put("registration_date", sale.getRegistrationdate());
			parameters.put("unit_price", sale.getUnitPrice());
			parameters.put("total_price", sale.getTotalPrice());
			parameters.put("dose", sale.getDose());
			parameters.put("drug_name", sale.getDrugName());
			parameters.put("unit", sale.getUnit());
		}

		return parameters;
	}

	///// Generate pdf with java

	public InputStream createPDF(List<Sales> salesList, SalesFilterPOJO searchparam) {
		ByteArrayOutputStream outPut = new ByteArrayOutputStream();
		// Document doc = new Document(PageSize.A4.rotate(), 10f, 10f, 10f, 0f);
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, outPut);
			// PdfWriter writer = PdfWriter.getInstance(document, outPut);
			// Rotate rotate = new Rotate();
			// writer.setPageEvent(rotate);
			document.open();
			// addMetaData(document);
			addTitlePage(document);

			Paragraph p = new Paragraph("Mega pharmacy", catFont);
			p.setAlignment(Element.ALIGN_CENTER);
			// addEmptyLine(p, 1);
			document.add(p);

			Paragraph salesDate;
			if (searchparam.getFromDate() != null && searchparam.getToDate() != null) {
				salesDate = new Paragraph("Sales Report Date" + " " + "From " + searchparam.getFromDate() + " " + "To "
						+ searchparam.getToDate(), catFont);
			} else if (searchparam.getFromDate() != null) {
				salesDate = new Paragraph("Sales Report Date" + " " + "From " + searchparam.getFromDate(), catFont);
			} else if (searchparam.getToDate() != null) {
				salesDate = new Paragraph("Sales Report until" + " " + searchparam.getToDate(), catFont);
			} else {
				salesDate = new Paragraph("Sales Report", catFont);
			}
			salesDate.setAlignment(Element.ALIGN_CENTER);
			addEmptyLine(salesDate, 2);
			document.add(salesDate);

			PdfPTable table = new PdfPTable(10);
			table.setWidthPercentage(100);
			addTableHeader(table);
			addRows(table, salesList);
			// addCustomRows(table);

			document.add(table);
			// addContent(document, salesList);

			document.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] data = outPut.toByteArray();
		return new ByteArrayInputStream(data);

	}

	private static void addTitlePage(Document document) throws DocumentException {
		Paragraph preface = new Paragraph();
		// We add one empty line
		addEmptyLine(preface, 1);

		// Will create: Report generated by: _name, _date
		String dates = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		preface.add(new Paragraph("Report Date: " + dates, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				catFont));

		document.add(preface);
		// Start a new page
		// document.newPage();
	}

	private void addTableHeader(PdfPTable table) {

		PdfPCell header = new PdfPCell(new Phrase("Drug Name"));
		header.setHorizontalAlignment(Element.ALIGN_CENTER);
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setBorderWidth(2);
		table.addCell(header);

		header = new PdfPCell(new Phrase("Quantity"));
		header.setHorizontalAlignment(Element.ALIGN_CENTER);
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setBorderWidth(2);
		table.addCell(header);

		header = new PdfPCell(new Phrase("Weight"));
		header.setHorizontalAlignment(Element.ALIGN_CENTER);
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setBorderWidth(2);
		table.addCell(header);

		header = new PdfPCell(new Phrase("Brand"));
		header.setHorizontalAlignment(Element.ALIGN_CENTER);
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setBorderWidth(2);
		table.addCell(header);

		header = new PdfPCell(new Phrase("Batch Number"));
		header.setHorizontalAlignment(Element.ALIGN_CENTER);
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setBorderWidth(2);
		table.addCell(header);

		header = new PdfPCell(new Phrase("Dose"));
		header.setHorizontalAlignment(Element.ALIGN_CENTER);
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setBorderWidth(2);
		table.addCell(header);

		header = new PdfPCell(new Phrase("Unti"));
		header.setHorizontalAlignment(Element.ALIGN_CENTER);
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setBorderWidth(2);
		table.addCell(header);

		header = new PdfPCell(new Phrase("Reg Date"));
		header.setHorizontalAlignment(Element.ALIGN_CENTER);
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setBorderWidth(2);
		table.addCell(header);

		header = new PdfPCell(new Phrase("UPrice"));
		header.setHorizontalAlignment(Element.ALIGN_CENTER);
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setBorderWidth(2);
		table.addCell(header);

		header = new PdfPCell(new Phrase("Total Price"));
		header.setHorizontalAlignment(Element.ALIGN_CENTER);
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setBorderWidth(2);
		table.addCell(header);

	}

	private void addRows(PdfPTable table, List<Sales> salesList) {

		Double totalPrice = 0.0;

		for (Sales sales : salesList) {
			table.addCell(sales.getDrugName());
			table.addCell(sales.getQuantity().toString());
			table.addCell(sales.getWeight());
			table.addCell(sales.getBrand());
			table.addCell(sales.getBatchNumber());
			table.addCell(sales.getDose().toString());
			table.addCell(sales.getUnit());
			table.addCell(new SimpleDateFormat("yyyy-MM-dd").format(sales.getRegistrationdate()));
			table.addCell(sales.getUnitPrice().toString());
			table.addCell(sales.getTotalPrice().toString());
			totalPrice = totalPrice + sales.getTotalPrice();
		}

		table.addCell(new Phrase(new Chunk("Total", smallBold)));
		table.addCell("");
		table.addCell("");
		table.addCell("");
		table.addCell("");
		table.addCell("");
		table.addCell("");
		table.addCell("");
		table.addCell("");
		// table.addCell(totalPrice.toString());
		table.addCell(new Phrase(new Chunk(totalPrice.toString(), smallBold)));
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	public class Rotate extends PdfPageEventHelper {

		protected PdfNumber orientation = PdfPage.INVERTEDPORTRAIT;

		public void setOrientation(PdfNumber orientation) {
			this.orientation = orientation;
		}

		@Override
		public void onStartPage(PdfWriter writer, Document document) {
			writer.addPageDictEntry(PdfName.ROTATE, orientation);
		}
	}
}
