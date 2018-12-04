package org.farm.fms.entity.ejb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.farm.fms.etntity.Purchase;
import org.farm.fms.etntity.Purchase_;
import org.farm.fms.etntity.Store;
import org.farm.fms.etntity.Store_;
import org.farm.pojo.MapperPOJO;
import org.farm.pojo.SalesFilterPOJO;

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

@Stateless
public class StoreEJB extends AbstructHome<Store, Integer> {

	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

	private Store store;

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub

	}

	public void updateStore(Store store) {
		merge(store);
	}

	@Override
	public Store findById(Integer id) {

		return findById(Store.class, id);
	}

	/**
	 * 
	 * @param today
	 * @return list of expired drugs
	 */
	public List<Store> findExpiredDrugFromStore(Date today) {

		List<Store> drugList = new ArrayList<Store>();

		String filter = new SimpleDateFormat("yyyy-MM-dd").format(today);
		Date filterDate = convertDate(filter);

		TypedQuery<Store> storeQuery = entityManager.createNamedQuery("findExpiredDrugFromStore", Store.class);
		storeQuery.setParameter("today", filterDate);
		drugList = storeQuery.getResultList();
		return drugList;
	}

	private Date convertDate(String date) {
		Date filter = null;
		try {
			filter = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			log.equals("----Is not a correct date format-----");
		}

		return filter;
	}

	public List<Store> filterDrugByExpireDate(Date expireDate) {
		List<Store> drugList = new ArrayList<Store>();

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Store> query = cb.createQuery(Store.class);
		Root<Store> root = query.from(Store.class);

		List<Predicate> whereCondition = getFilterCondtion(cb, root, expireDate);
		query.where(whereCondition.toArray(new Predicate[whereCondition.size()]));
		query.orderBy(cb.asc(root.get(Store_.expireDate)));

		TypedQuery<Store> typeQuery = entityManager.createQuery(query);
		drugList = typeQuery.getResultList();

		return drugList;
	}

	private List<Predicate> getFilterCondtion(CriteriaBuilder cb, Root<Store> root, Date expireDate) {
		List<Predicate> wherePredicates = new ArrayList<Predicate>();

		if (expireDate != null) {
			wherePredicates.add(cb.greaterThanOrEqualTo(root.get(Store_.expireDate), expireDate));
		}
		wherePredicates.add(cb.notLike(root.get(Store_.deletedStatus), "%" + "Yes" + "%"));
		return wherePredicates;
	}

	public List<MapperPOJO> filterDrugsByCriteria(SalesFilterPOJO filterPOJO) {
		List<MapperPOJO> searchResult = new ArrayList<MapperPOJO>();

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
		// CriteriaQuery<Store> query = cb.createQuery(Store.class);
		Root<Purchase> root = query.from(Purchase.class);
		query = queryBuilder(root, query, cb);

		List<Predicate> whereCondition = getWhereCondtion(cb, root, filterPOJO);
		query.where(whereCondition.toArray(new Predicate[whereCondition.size()]));
		query.orderBy(cb.desc(root.get(Purchase_.registrationDate)));

		TypedQuery<Object[]> typedQuery = entityManager.createQuery(query);
		List<Object[]> results = typedQuery.getResultList();
		searchResult = mapResult(results);
		return searchResult;
	}

	public List<Predicate> getWhereCondtion(CriteriaBuilder cb, Root<Purchase> root, SalesFilterPOJO filterPOJO) {

		List<Predicate> wherePredicates = new ArrayList<Predicate>();
		Join<Purchase, Store> storeJoin = root.join(Purchase_.store);
		if (filterPOJO.getFromDate() != null) {
			String date = new SimpleDateFormat("yyyy-MM-dd").format(filterPOJO.getFromDate());
			Date fromDate = convertDate(date);
			wherePredicates.add(cb.greaterThanOrEqualTo(root.get(Purchase_.registrationDate), fromDate));
		}

		if (filterPOJO.getToDate() != null) {
			String date = new SimpleDateFormat("yyy-MM-dd").format(filterPOJO.getToDate());
			Date toDate = convertDate(date);
			wherePredicates.add(cb.lessThanOrEqualTo(root.get(Purchase_.registrationDate), toDate));
		}

		if (filterPOJO.getDrugName() != null) {
			wherePredicates.add(cb.like(storeJoin.get(Store_.drugName), "%" + filterPOJO.getDrugName() + "%"));
		}

		if (filterPOJO.getExpireDate() != null) {
			String dates = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			Date toDate = convertDate(dates);
			wherePredicates.add(cb.lessThanOrEqualTo(storeJoin.get(Store_.expireDate), toDate));
		}
		if (filterPOJO.isOnlyExpired()) {
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			Date toDate = convertDate(date);
			wherePredicates.add(cb.lessThanOrEqualTo(storeJoin.get(Store_.expireDate), toDate));
		}
		return wherePredicates;
	}

	private CriteriaQuery<Object[]> queryBuilder(Root<Purchase> root, CriteriaQuery<Object[]> query,
			CriteriaBuilder cb) {

		Join<Purchase, Store> storeJoin = root.join(Purchase_.store);
		query.select(cb.array(storeJoin.get(Store_.storeId), storeJoin.get(Store_.drugName),
				storeJoin.get(Store_.brand), storeJoin.get(Store_.bathcNumber), storeJoin.get(Store_.weight),
				storeJoin.get(Store_.manufacturingDate), storeJoin.get(Store_.expireDate), storeJoin.get(Store_.unit),
				root.get(Purchase_.registrationDate), storeJoin.get(Store_.salesPrice), storeJoin.get(Store_.packUnit),
				root.get(Purchase_.quantityInBox), storeJoin.get(Store_.quantityperBoxperUnit),
				storeJoin.get(Store_.totalPrice), storeJoin.get(Store_.packType), storeJoin.get(Store_.unitPrice)));

		return query;
	}

	public List<MapperPOJO> mapResult(List<Object[]> searchResults) {
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
			mapperPOJO.setQuantityInBox(Integer.parseInt(next[11].toString()));
			mapperPOJO.setQuantityPerPack(Integer.parseInt(next[12].toString()));
			mapperPOJO.setTotalPrice(Double.parseDouble(next[13].toString()));
			mapperPOJO.setPackType(next[14].toString());
			mapperPOJO.setUnitPrice(Double.parseDouble(next[15].toString()));
			results.add(mapperPOJO);
		}
		return results;
	}

	// Generat PDF
	///// Generate pdf with java

	public InputStream createPDF(List<MapperPOJO> salesList, SalesFilterPOJO searchparam) {
		ByteArrayOutputStream outPut = new ByteArrayOutputStream();
		// Document document = new Document(PageSize.A4.rotate(), 10f, 10f, 10f, 0f);
		Document document = new Document(PageSize.A4_LANDSCAPE);
		document.setPageSize(PageSize.A4.rotate());
		document.newPage();
		try {
			PdfWriter.getInstance(document, outPut);
			document.open();
			// addMetaData(document);
			addTitlePage(document);

			Paragraph p = new Paragraph("Mega pharmacy", catFont);
			p.setAlignment(Element.ALIGN_CENTER);
			// addEmptyLine(p, 1);
			document.add(p);

			Paragraph salesDate;
			if (searchparam.getFromDate() != null && searchparam.getToDate() != null) {
				salesDate = new Paragraph("Purchased Drug Report Date" + " " + "From " + searchparam.getFromDate() + " "
						+ "To " + searchparam.getToDate(), catFont);
			} else if (searchparam.getFromDate() != null) {
				salesDate = new Paragraph("Purchased Report Date" + " " + "From " + searchparam.getFromDate(), catFont);
			} else if (searchparam.getToDate() != null) {
				salesDate = new Paragraph("Purchased Report until" + " " + searchparam.getToDate(), catFont);
			} else {
				salesDate = new Paragraph("Purchased Report", catFont);
			}
			salesDate.setAlignment(Element.ALIGN_CENTER);
			addEmptyLine(salesDate, 2);
			document.add(salesDate);

			PdfPTable table = new PdfPTable(9);
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

		header = new PdfPCell(new Phrase("Unti"));
		header.setHorizontalAlignment(Element.ALIGN_CENTER);
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setBorderWidth(2);
		table.addCell(header);

		header = new PdfPCell(new Phrase("Purchased Date"));
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

	private void addRows(PdfPTable table, List<MapperPOJO> salesList) {

		Double totalPrice = 0.0;

		for (MapperPOJO sales : salesList) {
			table.addCell(sales.getDrugName());
			table.addCell(sales.getQuantityInBox().toString());
			table.addCell(sales.getWeight());
			table.addCell(sales.getBrand());
			table.addCell(sales.getBathcNumber());
			// table.addCell(sales.getDose().toString());
			table.addCell(sales.getUnit());
			table.addCell(new SimpleDateFormat("yyyy-MM-dd").format(sales.getRegistrationDate()));
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
		// table.addCell("");
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
