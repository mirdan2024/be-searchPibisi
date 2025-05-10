package it.search.pibisi.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.function.Consumer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

import it.search.pibisi.bean.MatchBean;
import it.search.pibisi.bean.SubjectPoiBean;
import it.search.pibisi.controller.pojo.AccountsSearchPojo;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class PdfService {

	@Autowired
	private AccountsDetailService detailService;

	public static String getCurrentDateTime() {
		// Ottieni la data e l'ora correnti
		LocalDateTime now = LocalDateTime.now();
		return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	public byte[] createPdf(AccountsSearchPojo requestJson, HttpServletRequest request) {

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			// Recupero il dettaglio
			MatchBean matchBean = detailService.detail(requestJson, request);

			// Crea il PDF dal Json
			return createPdfFromJson(matchBean);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private byte[] createPdfFromJson(MatchBean matchBean) {
		try {

			// Crea l'HTML per il contenuto
			StringBuilder builder = getPageHeader();

			// Page Cover
			getPageCover(builder);

			// Page Summary
			getPageSummary(builder);

			// Page 1 Overview
			getPageOverview(matchBean, builder);

			// Page 2 Risk Table
			getPageRiskTable(matchBean, builder);

			// Page 4 Match Information
			getPageMatchInformation(matchBean, builder);

			// Page 5 Contact Information
			getPageContactInformation(matchBean, builder);// a

			// Page 3 Network Diagram
			getPageNetworkDiagram(matchBean, builder);

			// Page 6 Pep Check
			getPagePepCheck(matchBean, builder);

			// Page 7 Sanction Check
			getPageSanctionCheck(matchBean, builder);

			// Page 8 Negative News Check
			getPageNegativeNewsCheck(matchBean, builder);

			// Page 9 Sources Summary
			getPageSourcesSummary(matchBean, builder);

			// Page Last
			getPageLast(builder);

			// Page Footer
			getPageFooter(builder);

			System.out.println("html: " + builder.toString());

			// Crea un ByteArrayOutputStream per mantenere il PDF in memoria
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

			// Converte l'HTML in PDF e lo scrive direttamente nel ByteArrayOutputStream
			HtmlConverter.convertToPdf(builder.toString(), byteArrayOutputStream);

			// Aggiungiamo header e footer
			byteArrayOutputStream = addHeaderFooter(byteArrayOutputStream);

			// Salviamo il risultato in un file (opzionale)
			writeByteArrayToFile("C:\\Gestore\\pdf\\output_flying_saucer.pdf", byteArrayOutputStream.toByteArray());

			byteArrayOutputStream.close();
			System.out.println("PDF modificato con successo!");

			return byteArrayOutputStream.toByteArray();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ByteArrayOutputStream addHeaderFooter(ByteArrayOutputStream outputStream) throws IOException {

		// Legge il PDF dall'array di byte
		PdfReader reader = new PdfReader(new ByteArrayInputStream(outputStream.toByteArray()));
		PdfWriter writer = new PdfWriter(outputStream);
		PdfDocument pdfDoc = new PdfDocument(reader, writer);

		int totalPages = pdfDoc.getNumberOfPages();
		for (int i = 1; i <= totalPages; i++) {
			PdfPage page = pdfDoc.getPage(i);
			PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

			// **Header - Testo a Sinistra**
			float pageWidth = page.getPageSize().getWidth();
			float leftX = 40; // Margine sinistro
			float centerX = (pageWidth - 100) / 2; // Regola la larghezza del testo
			float headerY = page.getPageSize().getTop() - 30;
			float footerY = page.getPageSize().getBottom() + 65;
			if (i >= 2) {
				canvas.setFillColorGray(0.5f).beginText().setFontAndSize(PdfFontFactory.createFont(), 10)
						.moveText(leftX, headerY).showText("AegisX S.r.l.").endText();

				// **Header - Testo a Destra**
				float rightX = pageWidth - 205; // Margine destro
				canvas.setFillColorGray(0.5f).beginText().setFontAndSize(PdfFontFactory.createFont(), 10)
						.moveText(rightX, headerY).showText("AML and Reputational Check Report").endText();

				// **Linea sotto l'Header (Grigia)**
				canvas.setLineWidth(1f) // Imposta la larghezza della linea
						.setStrokeColorGray(0.5f) // Imposta la linea grigia
						.moveTo(leftX, headerY - 5) // Punto iniziale (X, Y)
						.lineTo(pageWidth - 40, headerY - 5) // Punto finale (X, Y)
						.stroke(); // Disegna la linea

				// **Linea sopra il Footer**
				canvas.setLineWidth(1f) // Imposta la larghezza della linea
						.setStrokeColorGray(0.5f) // Imposta la linea grigia
						.moveTo(leftX, footerY) // Punto iniziale (X, Y)
						.lineTo(pageWidth - 40, footerY) // Punto finale (X, Y)
						.stroke(); // Disegna la linea

				// **Footer**
				canvas.setFillColorGray(0.5f).beginText().setFontAndSize(PdfFontFactory.createFont(), 10)
						.moveText(leftX, footerY - 15).showText("© 2025. AegisX S.r.l.").endText();

				canvas.setFillColorGray(0.5f).beginText().setFontAndSize(PdfFontFactory.createFont(), 10)
						.moveText(leftX, footerY - 30)
						.showText(
								"This report is provided for the recipient only and cannot be reproduced or shared without AegisX’s express consent.")
						.endText();
			}

			canvas.setFillColorGray(0.5f).beginText().setFontAndSize(PdfFontFactory.createFont(), 10)
					.moveText(centerX, footerY - 50).showText(i + "/" + totalPages).endText();
		}

		pdfDoc.close();
		return outputStream; // Restituisce il nuovo PDF come byte[]
	}

	// Metodo per leggere un file in byte array
	public static byte[] readFileToByteArray(String filePath) throws IOException {
		return java.nio.file.Files.readAllBytes(new File(filePath).toPath());
	}

	// Metodo per scrivere un byte array in un file
	public static void writeByteArrayToFile(String filePath, byte[] data) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(filePath)) {
			fos.write(data);
		}
	}

	private void getPageFooter(StringBuilder builder) {
		builder.append("</body>");
		builder.append("</html>");
	}

	private StringBuilder getPageHeader() {
		StringBuilder builder = new StringBuilder();
		builder.append("<html>");
		builder.append("<head>");
		builder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
		builder.append("<title>Dossier Check Liste AML</title>");
		builder.append("<style type=\"text/css\">");
		builder.append("@page { margin-top: 70px; margin-right: 20px; margin-bottom: 100px; margin-left: 20px; }");
		builder.append(
				".s1  { color: #000000; font-family:\"Segoe UI Symbol\", sans-serif; font-style: normal; font-weight: normal; text-decoration: none; font-size: 20pt; }");
		builder.append(
				".s2  { color: #000000; font-family:\"Segoe UI Symbol\", sans-serif; font-style: normal; font-weight: normal; text-decoration: none; font-size: 14pt; }");
		builder.append(
				".sTH { color: #FFFFFF; font-family:\"Segoe UI Symbol\", sans-serif; font-style: normal; font-weight: normal; text-decoration: none; font-size: 14pt; border: 1px solid #003366; padding: 5px 5px 5px 5px; height: 60px; text-align: center; background-color: #003366;}");
		builder.append(
				".sTL { color: #000000; font-family:\"Segoe UI Symbol\", sans-serif; font-style: normal; font-weight: normal; text-decoration: none; font-size: 14pt; border: 1px solid #003366; padding: 5px 5px 5px 5px; height: 60px; text-align: left; }");
		builder.append(
				".sL { color: #000000; font-family:\"Segoe UI Symbol\", sans-serif; font-style: normal; font-weight: normal; text-decoration: none; font-size: 14pt; padding: 5px 5px 5px 5px; text-align: left; }");
		builder.append("p { page-break-before: always; }");
		builder.append("</style>");
		builder.append("</head>");
		builder.append("<body>");
		return builder;
	}

	private void getPageLast(StringBuilder builder) {
		builder.append("<p></p>");
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" align=\"center\">");
		builder.append("<tr>");
		builder.append("<td style=\"text-align: center;\">");
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"90%\" align=\"center\">");
		builder.append("<tr>");
		builder.append("<td height=\"100px\" class=\"s1\" style=\"text-align: center;\">");
		builder.append("This page is intentionally blank for note-taking purposes.");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("</table>");
		builder.append("</td></tr>");
		builder.append("</table>");
	}

	private void getPageNegativeNewsCheck(MatchBean matchBean, StringBuilder builder)
			throws JSONException, URISyntaxException {

		String htmlMedia = getTableSubjectMedia(matchBean);

		builder.append("<p></p>")
				.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" align=\"center\">")
				.append("<tr><td style=\"text-align: center;\">")
				.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"90%\" align=\"center\">")
				.append("<tr><td height=\"100px\" class=\"s1\" style=\"text-align: left;\">8) Negative news check</td></tr>");

		if (htmlMedia != null) {
			builder.append("<tr><td class=\"s2\" style=\"text-align: justify;\">").append(
					"A detailed Adverse Media Check has been conducted, covering global sources to identify any potential ")
					.append("associations with negative news reports. This assessment includes a thorough review of news articles, ")
					.append("media archives, regulatory reports, and other publicly available sources to ensure comprehensive risk evaluation.")
					.append("</td></tr>").append("<tr><td>").append(htmlMedia).append("</td></tr>");
		} else {
			builder.append("<tr><td class=\"s2\" style=\"text-align: justify;\">")
					.append("The check has been conducted across global sources, and no matches have been identified.")
					.append("</td></tr>");
		}

		builder.append("</table>").append("</td></tr>").append("</table>");
	}

	private void getPageContactInformation(MatchBean matchBean, StringBuilder builder) {
		builder.append("<p></p>");
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" align=\"center\">");
		builder.append("<tr>");
		builder.append("<td style=\"text-align: center;\">");
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"90%\" align=\"center\">");
		builder.append("<tr>");
		builder.append(
				"<td height=\"100px\" colspan=\"2\" class=\"s1\" style=\"text-align: left;\">4) Contact information</td>");
		builder.append("</tr>");
		getPageContactInformationData(matchBean, builder);
		builder.append("</table>");
		builder.append("</td></tr>");
		builder.append("</table>");
	}

	private static void getPageContactInformationData(MatchBean matchBean, StringBuilder builder) throws JSONException {
		if (matchBean.getSubjectPlatform() == null || matchBean.getSubjectPlatform().isEmpty()) {
			return;
		}

		// Helper function to append table rows
		Consumer<String[]> appendRow = row -> builder
				.append("<tr><td height=\"40px\" style=\"text-align: left; color: #000000;\">").append(row[0])
				.append(":").append("</td><td height=\"40px\" style=\"text-align: left; color: #000000;\">")
				.append(row[1]).append("</td></tr>");

		// Itera sull'array "subjectPlatform"
		for (int i = 0; i < matchBean.getSubjectPlatform().size(); i++) {
			Hashtable<String, String> hashtable = getHashtableList(matchBean.getSubjectPlatform().get(i).getContent());

			// Aggiungi una riga alla tabella
			appendRow
					.accept(new String[] { capitalizeFirstLetter(hashtable.get("platform")), hashtable.get("number") });
		}

		builder.append("</table>");
	}

	public static String capitalizeFirstLetter(String str) {
		if (str == null || str.isEmpty()) {
			return str; // Se la stringa è vuota o null, ritorna la stringa così com'è
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	private void getPageRiskTable(MatchBean matchBean, StringBuilder builder) {

		String contentNameFull = "-";
		String contentBirthDate = "-";
		if (matchBean.getSubjectNameFull() != null && !matchBean.getSubjectNameFull().isEmpty()) {
			contentNameFull = matchBean.getSubjectNameFull().get(0).getContent().toString();
		}

		if (matchBean.getSubjectBirthDate() != null && !matchBean.getSubjectBirthDate().isEmpty()) {
			contentBirthDate = matchBean.getSubjectBirthDate().get(0).getContent().toString();
		}

		builder.append("<p></p>");
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" align=\"center\">");
		builder.append("<tr>");
		builder.append("<td style=\"text-align: center;\">");
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"90%\" align=\"center\">");
		builder.append("<tr>");
		builder.append("<td height=\"100px\" class=\"s1\" style=\"text-align: left;\">2) Risk Table</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td class=\"s2\" style=\"text-align: justify;\">");
		builder.append("The risk table presents a summary of potential matches related to:");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td height=\"20px\"></td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td class=\"s2\" style=\"text-align: justify;\">");
		builder.append("Name: " + contentNameFull);
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td class=\"s2\" style=\"text-align: justify;\">");
		builder.append("Born: " + contentBirthDate);
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td height=\"20px\"></td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td>");
		builder.append("<table cellspacing=\"0\" cellpadding=\"0\" style=\"width: 100%;\">");
		builder.append("<tr>");
		builder.append("<td class=\"sTH\" style=\"width: 70%;\">Risk Category</td>");
		builder.append("<td class=\"sTH\" style=\"width: 30%;\">Match YES/NO</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append(
				"<td class=\"sTL\">Politically Exposed Persons (PEPs)/Ex Politically Exposed Persons (EX-PEPs)</td>");
		if (matchBean.isPep() || matchBean.isWasPep())
			builder.append("<td class=\"sTL\">1 Match</td>");
		else
			builder.append("<td class=\"sTL\">0 Match</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td class=\"sTL\">Sanctions/Ex Sanctions</td>");
		if (matchBean.isSanctioned() || matchBean.isWasSanctioned())
			builder.append("<td class=\"sTL\">1 Match</td>");
		else
			builder.append("<td class=\"sTL\">0 Match</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td class=\"sTL\">Terrorist</td>");
		if (matchBean.isTerrorist())
			builder.append("<td class=\"sTL\">1 Match</td>");
		else
			builder.append("<td class=\"sTL\">0 Match</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td class=\"sTL\">Media/Adverse Info</td>");
		if (matchBean.isHasMedia() || matchBean.isHasAdverseInfo())
			builder.append("<td class=\"sTL\">1 Match</td>");
		else
			builder.append("<td class=\"sTL\">0 Match</td>");
		builder.append("</tr>");
		builder.append("</table>");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td height=\"20px\"></td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td class=\"s2\" style=\"text-align: justify;\">");
		builder.append("We clarify that the matches indicated above should be considered potential, ");
		builder.append("and any decision regarding their validation is the sole responsibility of the ");
		builder.append("client. AegisX assumes no responsibility for the information presented in this ");
		builder.append("report, which is sourced from public or publicly accessible sources and should ");
		builder.append("be regarded solely as supporting material for analysis. It is essential to ");
		builder.append("critically assess this information, as in a limited number of cases, it may ");
		builder.append("contain inaccuracies not attributable to AegisX.");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td height=\"20px\"></td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td class=\"s2\" style=\"text-align: justify;\">");
		builder.append("The provided information should not be the sole factor in decision-making.");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td height=\"20px\"></td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td class=\"s2\" style=\"text-align: justify;\">");
		builder.append("Any decision regarding the initiation or continuation of a relationship with the ");
		builder.append("subject is the exclusive responsibility of the client.");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("</table>");
		builder.append("</td></tr>");
		builder.append("</table>");
	}

	private String getTableSubjectMedia(MatchBean matchBean) throws JSONException, URISyntaxException {

		if (matchBean.getSubjectMedia() == null || matchBean.getSubjectMedia().isEmpty()) {
			return null;
		}

		StringBuilder builder = new StringBuilder();
		builder.append("<br/>");
		// Crea l'inizio della tabella per ogni subject
		builder.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");

		// Itera sull'array "subjectMedia"
		for (int i = 0; i < matchBean.getSubjectMedia().size(); i++) {
			Hashtable<String, String> hashtable = getHashtableList(matchBean.getSubjectMedia().get(i).getContent());

			if (i != 0) {
				builder.append("<tr>");
				builder.append("<td height=\"30\" class=\"sL\" colspan=\"2\"></td>");
				builder.append("</tr>");
			}

			String action = StringUtils.hasLength(hashtable.get("action")) ? hashtable.get("action") : "";
			String from = StringUtils.hasLength(hashtable.get("from")) ? hashtable.get("from") : "";
			String issue = StringUtils.hasLength(hashtable.get("issue")) ? hashtable.get("issue") : "";

			builder.append("<tr>");
			builder.append("<td height=\"20\" class=\"sL\">Publishing date</td>");
			builder.append("<td height=\"20\" class=\"sL\">" + from + "</td>");
			builder.append("</tr>");

			builder.append("<tr>");
			builder.append("<td height=\"20\" class=\"sL\">Types</td>");
			builder.append("<td height=\"20\" class=\"sL\">" + issue + "</td>");
			builder.append("</tr>");

			builder.append("<tr>");
			builder.append("<td height=\"20\" class=\"sL\">Summary</td>");

			// Crea l'oggetto link
			URI url = new URI(hashtable.get("url"));
			String htmlLink = StringUtils.hasLength(hashtable.get("url"))
					? "<a href=\"" + hashtable.get("url") + "\" target=\"_blank\">" + url.getHost() + "</a>"
					: "";

			builder.append("<td height=\"20\" class=\"sL\">Subject " + action + " for a crime of " + issue + " "
					+ htmlLink + "</td>");
			builder.append("</tr>");
		}
		builder.append("</table>");
		return builder.toString();
	}

	private void getPageCover(StringBuilder builder) {

		URL imageLogo = PdfService.class.getClassLoader().getResource("logo/AEGIS-X-logo.png");
		URL imageCover = PdfService.class.getClassLoader().getResource("logo/COVER.png");

		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" align=\"center\">");
		builder.append("<tr>");
		builder.append("<td style=\"text-align: center;\">");
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"90%\" align=\"center\">");
		builder.append("<tr>");
		builder.append("<td height=\"50px\"></td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td style=\"text-align: center;\">");
		builder.append("<img width=\"400\" height=\"172\" src=\"" + imageLogo.toString() + "\">");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td height=\"150px\"></td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td style=\"text-align: center;\">");
		builder.append("<img width=\"600\" height=\"520\" src=\"" + imageCover.toString() + "\">");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td></td>");
		builder.append("</tr>");
		builder.append("<tr><td style=\"padding-left: 300px;\">Report Date: " + PdfService.getCurrentDateTime()
				+ "</td></tr>");
		builder.append("</table>");
		builder.append("</td></tr>");
		builder.append("</table>");

	}

	private void getPageSummary(StringBuilder builder) {
		builder.append("<p></p>");
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" align=\"center\">");
		builder.append("<tr>");
		builder.append("<td style=\"text-align: center;\">");
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"90%\" align=\"center\">");
		builder.append("<tr>");
		builder.append("<td height=\"100px\" class=\"s1\">Table of Contents</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td height=\"80px\" class=\"s1\" style=\"text-align: left;\">1. Overview</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td height=\"80px\" class=\"s1\" style=\"text-align: left;\">2. Risk Table</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td height=\"80px\" class=\"s1\" style=\"text-align: left;\">3. Match Information</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td height=\"80px\" class=\"s1\" style=\"text-align: left;\">4. Contact Information</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td height=\"80px\" class=\"s1\" style=\"text-align: left;\">5. Network Diagram</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td height=\"80px\" class=\"s1\" style=\"text-align: left;\">6. PEP Check</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td height=\"80px\" class=\"s1\" style=\"text-align: left;\">7. Sanctions Check</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td height=\"80px\" class=\"s1\" style=\"text-align: left;\">8. Negative News Check</td>");
		builder.append("</tr>");
		builder.append("</table>");
		builder.append("</td></tr>");
		builder.append("</table>");
	}

	private void getPageOverview(MatchBean matchBean, StringBuilder builder) {

		String contentNameFull = "-";
		if (matchBean.getSubjectNameFull() != null && !matchBean.getSubjectNameFull().isEmpty()) {
			contentNameFull = matchBean.getSubjectNameFull().get(0).getContent().toString();
		}

		builder.append("<p></p>");
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" align=\"center\">");
		builder.append("<tr>");
		builder.append("<td style=\"text-align: center;\">");
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"90%\" align=\"center\">");
		builder.append("<tr>");
		builder.append("<td height=\"100px\" class=\"s1\">Table of Contents</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td height=\"60px\" class=\"s1\" style=\"text-align: left;\">1) Overview</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td class=\"s2\" style=\"text-align: justify;\">");
		builder.append("The risk matrix below provides a summary of the findings related to the subject ");
		builder.append("of this report, " + contentNameFull + ". The charts illustrate whether ");
		builder.append("any matches were identified for the subject across the monitored lists.");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td height=\"20px\"></td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td class=\"s2\" style=\"text-align: justify;\">");
		builder.append("For further details, please refer to the next page and the relevant sections of this report.");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("<tr><td height=\"20\"></td></tr>");
		builder.append("<tr><td>");
		getPageOverviewCategory(matchBean, builder);
		builder.append("</td></tr>");
		builder.append("<tr>");
		builder.append("<td height=\"20px\"></td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td class=\"s2\" style=\"text-align: justify;\">");
		builder.append("The report is strictly confidential and intended exclusively for the originating client.");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("</table>");
		builder.append("</td></tr>");
		builder.append("</table>");

	}

	private void getPageOverviewCategory(MatchBean matchBean, StringBuilder builder) {
		URL imagePepV = PdfService.class.getClassLoader().getResource("category/PEP_V.png");
		URL imagePepX = PdfService.class.getClassLoader().getResource("category/PEP_X.png");
		URL imageExPepV = PdfService.class.getClassLoader().getResource("category/EX-PEP_V.png");
		URL imageExPepX = PdfService.class.getClassLoader().getResource("category/EX-PEP_X.png");
		URL imageSanctionedV = PdfService.class.getClassLoader().getResource("category/SANCTIONED_V.png");
		URL imageSanctionedX = PdfService.class.getClassLoader().getResource("category/SANCTIONED_X.png");
		URL imageExSanctionedV = PdfService.class.getClassLoader().getResource("category/EX-SANCTIONED_V.png");
		URL imageExSanctionedX = PdfService.class.getClassLoader().getResource("category/EX-SANCTIONED_X.png");
		URL imageTerroristV = PdfService.class.getClassLoader().getResource("category/TERRORIST_V.png");
		URL imageTerroristX = PdfService.class.getClassLoader().getResource("category/TERRORIST_X.png");
		URL imageMediaV = PdfService.class.getClassLoader().getResource("category/MEDIA_V.png");
		URL imageMediaX = PdfService.class.getClassLoader().getResource("category/MEDIA_X.png");
		URL imageAdverseInfoV = PdfService.class.getClassLoader().getResource("category/ADVERSE-INFO_V.png");
		URL imageAdverseInfoX = PdfService.class.getClassLoader().getResource("category/ADVERSE-INFO_X.png");

		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"90%\" align=\"center\">");

		builder.append("<tr>");
		builder.append("<td>");
		if (matchBean.isPep())
			builder.append("<img src=\"" + imagePepV.toString() + "\" alt=\"PEP\">");
		else
			builder.append("<img src=\"" + imagePepX.toString() + "\" alt=\"PEP\">");
		builder.append("</td>");

		builder.append("<td>");
		if (matchBean.isWasPep())
			builder.append("<img src=\"" + imageExPepV.toString() + "\" alt=\"EX-PEP\">");
		else
			builder.append("<img src=\"" + imageExPepX.toString() + "\" alt=\"EX-PEP\">");
		builder.append("</td>");

		builder.append("<td>");
		if (matchBean.isSanctioned())
			builder.append("<img src=\"" + imageSanctionedV.toString() + "\" alt=\"SANCTIONED\">");
		else
			builder.append("<img src=\"" + imageSanctionedX.toString() + "\" alt=\"SANCTIONED\">");
		builder.append("</td>");

		builder.append("<td>");
		if (matchBean.isWasSanctioned())
			builder.append("<img src=\"" + imageExSanctionedV.toString() + "\" alt=\"EX-SANCTIONED\">");
		else
			builder.append("<img src=\"" + imageExSanctionedX.toString() + "\" alt=\"EX-SANCTIONED\">");
		builder.append("</td>");
		builder.append("</tr>");

		builder.append("<tr><td height=\"20\"></td></tr>");

		builder.append("<tr>");
		builder.append("<td>");
		if (matchBean.isTerrorist())
			builder.append("<img src=\"" + imageTerroristV.toString() + "\" alt=\"TERRORIST\">");
		else
			builder.append("<img src=\"" + imageTerroristX.toString() + "\" alt=\"TERRORIST\">");
		builder.append("</td>");

		builder.append("<td>");
		if (matchBean.isHasMedia())
			builder.append("<img src=\"" + imageMediaV.toString() + "\" alt=\"MEDIA\">");
		else
			builder.append("<img src=\"" + imageMediaX.toString() + "\" alt=\"MEDIA\">");
		builder.append("</td>");

		builder.append("<td>");
		if (matchBean.isHasAdverseInfo())
			builder.append("<img src=\"" + imageAdverseInfoV.toString() + "\" alt=\"ADVERSE-INFO\">");
		else
			builder.append("<img src=\"" + imageAdverseInfoX.toString() + "\" alt=\"ADVERSE-INFO\">");
		builder.append("</td>");
		builder.append("</tr>");

		builder.append("</table>");
	}

	private void getPageMatchInformation(MatchBean matchBean, StringBuilder builder) {
		builder.append("<p></p>");
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" align=\"center\">");
		builder.append("<tr>");
		builder.append("<td style=\"text-align: center;\">");
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"90%\" align=\"center\">");
		builder.append("<tr>");
		builder.append(
				"<td height=\"100px\" colspan=\"2\" class=\"s1\" style=\"text-align: left;\">3) Match Information</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td colspan=\"2\">");
		getPageMatchInformationData(matchBean, builder);
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("</table>");
		builder.append("</td></tr>");
		builder.append("</table>");

		getPageMatchInformationPhoto(matchBean, builder);
	}

	private static String getTableSubjectNameFull(MatchBean matchBean) throws JSONException {
		StringBuilder alias = new StringBuilder();

		// Verifica se "subjectNameFull" esiste
		if (matchBean.getSubjectNameFull() == null || matchBean.getSubjectNameFull().isEmpty()) {
			return null;
		}

		// Itera sull'array "subjectNameFull"
		for (int i = 0; i < matchBean.getSubjectNameFull().size(); i++) {
			// Aggiungi una virgola prima del prossimo alias, se necessario
			if (i > 0) {
				alias.append(", ");
			}

			// Aggiungi il contenuto "content"
			alias.append(matchBean.getSubjectNameFull().get(i).getContent().toString());
		}

		return alias.toString();
	}

	// Metodi privati per utility
	private static String getNonNullValue(String value, String defaultValue) {
		return StringUtils.hasLength(value) ? value : defaultValue;
	}

	private static void getPageMatchInformationData(MatchBean matchBean, StringBuilder builder) {
		String strSubjectUuid = "-";
		String strgTypeCategory = "-";
		String strCreatedAtDate = "-";
		String strTypePerson = "-";
		String strGender = null;
		String urlPhoto = null;
		String contentNameFull = "-";
		String contentBirthDate = null;
		String strBirthPlace = null;
		String contentNationality = null;
		String strIdPlatform = null;
		String strIdPassport = null;

		// Helper function to append table rows
		Consumer<String[]> appendRow = row -> builder.append("<tr><td height=\"30px\" class=\"sL\">").append(row[0])
				.append("</td><td height=\"30px\" class=\"sL\">").append(row[1]).append("</td></tr>");

		// Extract values from jsonObject
		strSubjectUuid = getNonNullValue(matchBean.getSubjectUuid(), strSubjectUuid);
		strgTypeCategory = getNonNullValue(matchBean.getTypeCategory(), strgTypeCategory);
		strCreatedAtDate = getNonNullValue(matchBean.getCreatedAtDate(), strCreatedAtDate);

		if (matchBean.getSubjectPerson() != null && !matchBean.getSubjectPerson().isEmpty()) {
			strTypePerson = matchBean.getSubjectPerson().get(0).getType().toString();
		}

		if (matchBean.getSubjectGender() != null && !matchBean.getSubjectGender().isEmpty()) {
			strGender = matchBean.getSubjectGender().get(0).getContent().toString();
		}

		if (matchBean.getSubjectPhoto() != null && !matchBean.getSubjectPhoto().isEmpty()) {
			Hashtable<String, String> hashtable = getHashtableList(matchBean.getSubjectPhoto().get(0).getContent());
			urlPhoto = hashtable.get("url");
		}

		if (matchBean.getSubjectNameFull() != null && !matchBean.getSubjectNameFull().isEmpty()) {
			contentNameFull = matchBean.getSubjectNameFull().get(0).getContent().toString();
			if (matchBean.getSubjectDead() != null && !matchBean.getSubjectDead().isEmpty()) {
				String contentDead = matchBean.getSubjectDead().get(0).getContent().toString();
				if ("true".equals(contentDead)) {
					contentNameFull = contentNameFull.concat(" (Deceduto)");
				}
			}
		}

		if (matchBean.getSubjectBirthDate() != null && !matchBean.getSubjectBirthDate().isEmpty()) {
			contentBirthDate = matchBean.getSubjectBirthDate().get(0).getContent().toString();
		}

		if (matchBean.getSubjectBirthPlace() != null && !matchBean.getSubjectBirthPlace().isEmpty()) {
			Hashtable<String, String> hashtable = getHashtableList(
					matchBean.getSubjectBirthPlace().get(0).getContent());
			strBirthPlace = hashtable.get("city");
			String country = hashtable.get("country");
			if (StringUtils.hasLength(strBirthPlace) && StringUtils.hasLength(country)) {
				strBirthPlace = strBirthPlace.concat(" (").concat(country).concat(")");
			} else if (StringUtils.hasLength(country)) {
				strBirthPlace = country;
			}
		}

		if (matchBean.getSubjectNationality() != null && !matchBean.getSubjectNationality().isEmpty()) {
			contentNationality = matchBean.getSubjectNationality().get(0).getContent().toString();
		}

		if (matchBean.getSubjectIdPlatform() != null && !matchBean.getSubjectIdPlatform().isEmpty()) {
			Hashtable<String, String> hashtable = getHashtableList(
					matchBean.getSubjectIdPlatform().get(0).getContent());
			strIdPlatform = hashtable.get("number");
			String platform = hashtable.get("platform");
			if (StringUtils.hasLength(strIdPlatform) && StringUtils.hasLength(platform)) {
				strIdPlatform = strIdPlatform.concat(" (").concat(platform).concat(")");
			}
		}

		if (matchBean.getSubjectIdPassport() != null && !matchBean.getSubjectIdPassport().isEmpty()) {
			Hashtable<String, String> hashtable = getHashtableList(
					matchBean.getSubjectIdPassport().get(0).getContent());
			strIdPassport = hashtable.get("number");
			String passportCountry = hashtable.get("country");
			if (StringUtils.hasLength(strIdPassport) && StringUtils.hasLength(passportCountry)) {
				strIdPassport = strIdPassport.concat(" (").concat(passportCountry).concat(")");
			}
		}

		// Start constructing the table
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");

		if (StringUtils.hasLength(urlPhoto)) {
			builder.append("<tr><td colspan=\"2\" style=\"text-align: left;\">").append("<img src=\"").append(urlPhoto)
					.append("\" style=\"min-width: 100; min-height: 100; max-width: 200; max-height: 200;\"></img>")
					.append("</td></tr>");
		}

		builder.append("<td>");
		// Append each row with the helper function
		appendRow.accept(new String[] { "Uuid:", strSubjectUuid });
		appendRow.accept(new String[] { "Full Name:", "<strong>" + contentNameFull + "</strong>" });
		appendRow.accept(new String[] { "Type:", strTypePerson });
		appendRow.accept(new String[] { "Classification:", strgTypeCategory });
		appendRow.accept(new String[] { "Profile Creation Date:", strCreatedAtDate });

		if (StringUtils.hasLength(strGender)) {
			appendRow.accept(
					new String[] { "Gender:", "M".equals(strGender) ? "Male" : "F".equals(strGender) ? "Female" : "" });
		}
		if (StringUtils.hasLength(contentBirthDate))
			appendRow.accept(new String[] { "Date of birth:", contentBirthDate });
		if (StringUtils.hasLength(strBirthPlace))
			appendRow.accept(new String[] { "Place of birth:", strBirthPlace });
		if (StringUtils.hasLength(contentNationality))
			appendRow.accept(new String[] { "Nationality:", contentNationality });
		if (StringUtils.hasLength(strIdPlatform))
			appendRow.accept(new String[] { "Platform ID:", strIdPlatform });
		if (StringUtils.hasLength(strIdPassport))
			appendRow.accept(new String[] { "Passport ID:", strIdPassport });

		String alias = getTableSubjectNameFull(matchBean);
		if (StringUtils.hasLength(alias))
			appendRow.accept(new String[] { "Alias:", alias });

		if (matchBean.getSubjectIllegal() != null && !matchBean.getSubjectIllegal().isEmpty()) {
			// Itera sull'array "subjectIllegal"
			for (int i = 0; i < matchBean.getSubjectIllegal().size(); i++) {
				appendRow.accept(new String[] { matchBean.getSubjectIllegal().get(i).getType(),
						matchBean.getSubjectIllegal().get(i).getContent().toString() });
			}
		}

		builder.append("</td></tr>").append("</table>");
	}

	private static void getPageMatchInformationPhoto(MatchBean matchBean, StringBuilder builder) throws JSONException {
		// Controlla se la chiave "getSubjectPhoto" esiste
		if (matchBean.getSubjectPhoto() == null || matchBean.getSubjectPhoto().isEmpty()) {
			return; // Esce se la chiave non è presente
		}

		// Aggiunta di margine per separare dalla sezione precedente
		builder.append("<p></p>");

		// Creazione della struttura della tabella principale
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" align=\"center\">")
				.append("<tr><td style=\"text-align: center;\">")
				.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"90%\" align=\"center\">")
				.append("<tr><td height=\"30\" class=\"sL\">Multimedia</td></tr>");

		// Itera sull'array "getSubjectPhoto"
		int tclose = 0;
		for (int i = 1; i < matchBean.getSubjectPhoto().size(); i++) {
			Hashtable<String, String> hashtable = getHashtableList(matchBean.getSubjectPhoto().get(i).getContent());

			if (tclose == 0 || tclose == 1) {
				builder.append("<tr><td>")
						.append("<table border=\"0\" cellspacing=\"8\" cellpadding=\"8\" align=\"left\">")
						.append("<tr>");
				tclose = 1;
			}

			builder.append("<td style=\"text-align: center;\">")
					.append("<img src=\"" + hashtable.get("url")
							+ "\" style=\"min-width: 100; min-height: 100; max-width: 200; max-height: 200;\">")
					.append("</td>");

			if (tclose == 3) {
				builder.append("</tr>").append("</table>");
				tclose = 1;
			} else
				tclose++;
		}
		if (tclose != 0) {
			builder.append("</tr>").append("</table>");
		}
		builder.append("</td></tr>").append("</table>").append("</td></tr>").append("</table>");
	}

	private void getPagePepCheck(MatchBean matchBean, StringBuilder builder) {
		// Aggiunta di margine per separare dalla sezione precedente
		builder.append("<p></p>");

		// Creazione della struttura della tabella principale
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" align=\"center\">")
				.append("<tr><td style=\"text-align: center;\">")
				.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"90%\" align=\"center\">");

		// Aggiunta della sezione "Pep check"
		appendTableRow(builder, "6) Pep check", "s1", "text-align: left;", "height: 100px;");

		String htmlFunctionPublic = getPagePepCheckFunctionPublic(matchBean);
		String htmlFunctionPolitical = getPagePepCheckFunctionPolitical(matchBean);
		String htmlFunction = getPagePepCheckFunction(matchBean);

		if (htmlFunctionPublic != null || htmlFunctionPolitical != null || htmlFunction != null) {
			appendTableRow(builder,
					"A detailed Politically Exposed Person (PEP) check has been conducted, covering global sources to identify any potential associations with politically exposed individuals.",
					"s2", "text-align: justify;", "");

			// Chiamata per aggiungere i dettagli PEP check
			if (htmlFunctionPublic != null)
				builder.append("<tr><td>").append(htmlFunctionPublic).append("</td></tr>");

			if (htmlFunctionPolitical != null)
				builder.append("<tr><td>").append(htmlFunctionPolitical).append("</td></tr>");

			if (htmlFunction != null)
				builder.append("<tr><td>").append(htmlFunction).append("</td></tr>");
		} else {
			appendTableRow(builder,
					"The check has been conducted across global sources, and no matches have been identified.", "s2",
					"text-align: justify;", "");
		}

		// Chiusura della tabella
		builder.append("</table></td></tr></table>");
	}

	// Metodo helper per aggiungere righe alla tabella
	private void appendTableRow(StringBuilder builder, String content, String className, String textAlign,
			String height) {
		builder.append("<tr>").append("<td class=\"").append(className).append("\" style=\"").append(textAlign)
				.append(";").append(height != null ? " " + height : "").append("\">").append(content).append("</td>")
				.append("</tr>");
	}

	private static Hashtable<String, String> getHashtableList(Object input) {
		// Lista per memorizzare gli oggetti Hashtable
		Hashtable<String, String> hashtable = new Hashtable();
		if (input != null) {

			// Rimuoviamo le parentesi graffe e suddividiamo la stringa in coppie
			// chiave=valore
			String content = input.toString().substring(1, input.toString().length() - 1); // Rimuove '{' e '}'
			String[] pairs = content.split(","); // Suddivide la stringa in base alla virgola

			// Elaboriamo ogni coppia "key=value"
			for (String pair : pairs) {
				String[] keyValue = pair.split("="); // Suddividiamo ogni coppia in chiave e valore
				if (keyValue.length == 2) { // Verifica che ci siano esattamente due elementi
					hashtable.put(keyValue[0].trim(), keyValue[1].trim()); // Aggiungi la coppia alla Hashtable
					System.out.println("key: " + keyValue[0].trim() + " , value: " + keyValue[1].trim());
				}
			}
		}
		return hashtable;
	}

	private String getPagePepCheckFunctionPublic(MatchBean matchBean) throws JSONException {
		if (matchBean.getSubjectFunctionPublic() == null || matchBean.getSubjectFunctionPublic().isEmpty()) {
			return null;
		}

		StringBuilder builder = new StringBuilder();
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");

		// Itera sull'array "subjectFunctionPublic"
		for (int i = 0; i < matchBean.getSubjectFunctionPublic().size(); i++) {
			Hashtable<String, String> hashtable = getHashtableList(
					matchBean.getSubjectFunctionPublic().get(i).getContent());

			builder.append("<tr>");
			builder.append("<td height=\"30\" class=\"sL\">Public position</td>");
			builder.append("<td height=\"30\" class=\"sL\">");

			// Aggiungi una riga per ogni subject
			builder.append(hashtable.get("charge")).append(" of ").append(hashtable.get("organization"));

			if (hashtable.get("from") != null && !hashtable.get("from").isEmpty())
				builder.append(" From ").append(hashtable.get("from"));
			if (hashtable.get("to") != null && !hashtable.get("to").isEmpty())
				builder.append(" to ").append(hashtable.get("to"));

			builder.append("</td>");
			builder.append("</tr>");
		}

		builder.append("</table>");
		return builder.toString();
	}

	private String getPagePepCheckFunctionPolitical(MatchBean matchBean) throws JSONException {
		// Controlla se la chiave "subjectFunctionPolitical" esiste
		if (matchBean.getSubjectFunctionPolitical() == null || matchBean.getSubjectFunctionPolitical().isEmpty()) {
			return null; // Esce se la chiave non è presente
		}

		StringBuilder builder = new StringBuilder();
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");

		// Itera sull'array "subjectFunctionPublic"
		for (int i = 0; i < matchBean.getSubjectFunctionPolitical().size(); i++) {
			Hashtable<String, String> hashtable = getHashtableList(
					matchBean.getSubjectFunctionPolitical().get(i).getContent());

			builder.append("<tr>");
			builder.append("<td height=\"30\" class=\"sL\">Public position</td>");
			builder.append("<td height=\"30\" class=\"sL\">");

			// Aggiungi una riga per ogni subject
			builder.append(hashtable.get("charge")).append(" of ").append(hashtable.get("organization"));

			if (hashtable.get("from") != null && !hashtable.get("from").isEmpty())
				builder.append(" From ").append(hashtable.get("from"));
			if (hashtable.get("to") != null && !hashtable.get("to").isEmpty())
				builder.append(" to ").append(hashtable.get("to"));

			builder.append("</td>");
			builder.append("</tr>");
		}

		builder.append("</table>");
		return builder.toString();
	}

	private String getPagePepCheckFunction(MatchBean matchBean) throws JSONException {

		// Controlla se la chiave "getSubjectFunction" esiste
		if (matchBean.getSubjectFunction() == null || matchBean.getSubjectFunction().isEmpty()) {
			return null; // Esce se la chiave non è presente
		}

		StringBuilder builder = new StringBuilder();
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");

		// Itera sull'array "subjectFunctionPublic"
		for (int i = 0; i < matchBean.getSubjectFunction().size(); i++) {
			Hashtable<String, String> hashtable = getHashtableList(matchBean.getSubjectFunction().get(i).getContent());

			builder.append("<tr>");
			builder.append("<td height=\"30\" class=\"sL\">Function</td>");
			builder.append("<td height=\"30\" class=\"sL\">");

			// Aggiungi una riga per ogni subject
			builder.append(hashtable.get("charge")).append(" of ").append(hashtable.get("organization"));

			if (hashtable.get("from") != null && !hashtable.get("from").isEmpty())
				builder.append(" From ").append(hashtable.get("from"));
			if (hashtable.get("to") != null && !hashtable.get("to").isEmpty())
				builder.append(" to ").append(hashtable.get("to"));

			builder.append("</td>");
			builder.append("</tr>");
		}

		builder.append("</table>");
		return builder.toString();
	}

	private void getPageSanctionCheck(MatchBean matchBean, StringBuilder builder) {
		// Aggiungi i dati di Sanction Check
		String htmlSanctionCheck = getPageSanctionCheckData(matchBean);

		builder.append("<p></p>")
				.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" align=\"center\">")
				.append("<tr><td style=\"text-align: center;\">")
				.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"90%\" align=\"center\">")
				.append("<tr><td height=\"100px\" class=\"s1\" style=\"text-align: left;\">7) Sanction check</td></tr>");

		if (htmlSanctionCheck != null) {
			builder.append("<tr><td class=\"s2\" style=\"text-align: justify;\">").append(
					"A detailed Sanctions Check has been conducted, covering global sources to identify any potential ")
					.append("associations with sanctioned individuals or entities. This assessment includes a thorough review ")
					.append("of international sanction lists, regulatory databases, and enforcement records to ensure comprehensive ")
					.append("risk evaluation.").append("</td></tr>").append("<tr><td>").append(htmlSanctionCheck)
					.append("</td></tr>");
		} else {
			builder.append("<tr><td class=\"s2\" style=\"text-align: justify;\">")
					.append("The check has been conducted across global sources, and no matches have been identified.")
					.append("</td></tr>");
		}

		builder.append("</table>").append("</td></tr>").append("</table>");
	}

	private String getPageSanctionCheckData(MatchBean matchBean) throws JSONException {
		if (matchBean.getSubjectSanction() == null || matchBean.getSubjectSanction().isEmpty()) {
			return null;
		}

		StringBuilder builder = new StringBuilder();
		builder.append("<br/>");
		// Crea l'inizio della tabella per ogni subject
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"width: 100%;\">");
		// Itera sull'array "subjectSanction"
		for (int i = 0; i < matchBean.getSubjectSanction().size(); i++) {
			Hashtable<String, String> hashtable = getHashtableList(matchBean.getSubjectSanction().get(i).getContent());

			String issuer = StringUtils.hasLength(hashtable.get("issuer")) ? hashtable.get("issuer") : "";
			String reason = StringUtils.hasLength(hashtable.get("reason")) ? hashtable.get("reason") : "";
			String summary = StringUtils.hasLength(hashtable.get("summary")) ? hashtable.get("summary") : "";
			String from = StringUtils.hasLength(hashtable.get("from")) ? hashtable.get("from") : "";

			// Aggiungi il contenuto "content"
			builder.append("<tr>");
			builder.append("<td class=\"sL\">Issuer:</td>");
			builder.append("<td class=\"sL\">" + issuer + "</td>");
			builder.append("</tr>");
			builder.append("<tr>");
			builder.append("<td class=\"sL\">Reason:</td>");
			builder.append("<td class=\"sL\">" + reason + "</td>");
			builder.append("</tr>");
			builder.append("<tr>");
			builder.append("<td class=\"sL\">Summary:</td>");
			builder.append("<td class=\"sL\">" + summary + "</td>");
			builder.append("</tr>");
			builder.append("<tr>");
			builder.append("<td class=\"sL\">From:</td>");
			builder.append("<td class=\"sL\">" + from + "</td>");
			builder.append("</tr>");
			builder.append("<tr><td colspan=\"2\" height=\"30px;\"></td></tr>");
		}
		builder.append("</table>");
		return builder.toString();
	}

	private void getPageNetworkDiagram(MatchBean matchBean, StringBuilder builder) {
		builder.append("<p></p>");
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" align=\"center\">");
		builder.append("<tr>");
		builder.append("<td style=\"text-align: center;\">");
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"90%\" align=\"center\">");
//		builder.append("<tr>");
//		builder.append("<td height=\"100px\" class=\"s1\" style=\"text-align: left;\">5) Network diagram</td>");
//		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td height=\"100px\" class=\"s1\" style=\"text-align: left;\">5) Network</td>");
		builder.append("</tr>");
//		builder.append("<tr>");
//		builder.append("<td>");
//		builder.append(
//				"<img width=\"550\" height=\"250\" src=\"data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAeAB4AAD/4QL4RXhpZgAATU0AKgAAAAgABAE7AAIAAAAPAAABSodpAAQAAAABAAABWpydAAEAAAAeAAAC0uocAAcAAAEMAAAAPgAAAAAc6gAAAAEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAATWlya28gQ29uZGVsbG8AAAAFkAMAAgAAABQAAAKokAQAAgAAABQAAAK8kpEAAgAAAAMwMgAAkpIAAgAAAAMwMgAA6hwABwAAAQwAAAGcAAAAABzqAAAAAQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAyMDI1OjAzOjA5IDE2OjAzOjMwADIwMjU6MDM6MDkgMTY6MDM6MzAAAABNAGkAcgBrAG8AIABDAG8AbgBkAGUAbABsAG8AAAD/4QQhaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLwA8P3hwYWNrZXQgYmVnaW49J++7vycgaWQ9J1c1TTBNcENlaGlIenJlU3pOVGN6a2M5ZCc/Pg0KPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyI+PHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj48cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0idXVpZDpmYWY1YmRkNS1iYTNkLTExZGEtYWQzMS1kMzNkNzUxODJmMWIiIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIvPjxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSJ1dWlkOmZhZjViZGQ1LWJhM2QtMTFkYS1hZDMxLWQzM2Q3NTE4MmYxYiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIj48eG1wOkNyZWF0ZURhdGU+MjAyNS0wMy0wOVQxNjowMzozMC4wMTc8L3htcDpDcmVhdGVEYXRlPjwvcmRmOkRlc2NyaXB0aW9uPjxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSJ1dWlkOmZhZjViZGQ1LWJhM2QtMTFkYS1hZDMxLWQzM2Q3NTE4MmYxYiIgeG1sbnM6ZGM9Imh0dHA6Ly9wdXJsLm9yZy9kYy9lbGVtZW50cy8xLjEvIj48ZGM6Y3JlYXRvcj48cmRmOlNlcSB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPjxyZGY6bGk+TWlya28gQ29uZGVsbG88L3JkZjpsaT48L3JkZjpTZXE+DQoJCQk8L2RjOmNyZWF0b3I+PC9yZGY6RGVzY3JpcHRpb24+PC9yZGY6UkRGPjwveDp4bXBtZXRhPg0KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAg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\"/>");
//		builder.append("</td>");
//		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td class=\"s2\" style=\"text-align: justify;\">");
		builder.append("List of an individual's personal and ");
		builder.append("business connections, direct and indirect associations with ");
		builder.append("individuals and companies. This tool helps identify potential reputational, legal, or "); 
		builder.append("regulatory risks, supporting due diligence analysis, AML/KYC compliance, and risk assessments.");
		builder.append("The information should be ");
		builder.append("verified and contextualized before making operational or strategic decisions.");
		builder.append("</td>");
		builder.append("</tr>");
//		builder.append("<tr>");
//		builder.append("<td class=\"s2\" style=\"text-align: justify;\">");
//		builder.append("The network diagram provides a visual representation of an individual's ");
//		builder.append("personal and business connections, oering a clear overview of direct and ");
//		builder.append("indirect associations with individuals and companies. This tool helps identify ");
//		builder.append("potential reputational, legal, or regulatory risks, supporting due diligence ");
//		builder.append("analysis, AML/KYC compliance, and risk assessments.");
//		builder.append("</td>");
//		builder.append("</tr>");
//		builder.append("<tr>");
//		builder.append("<td height=\"20px\"></td>");
//		builder.append("</tr>");
//		builder.append("<tr>");
//		builder.append("<td class=\"s2\" style=\"text-align: justify;\">");
//		builder.append("The network diagram serves as an analytical tool and does not constitute a ");
//		builder.append("definitive judgment on the risk level of a subject or its associated entities. The ");
//		builder.append("information should be verified and contextualized before making operational ");
//		builder.append("or strategic decisions.");
//		builder.append("</td>");
//		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td>");
		getPageNetworkDiagramData(matchBean, builder);
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("</table>");
		builder.append("</td></tr>");
		builder.append("</table>");
	}

	private static void getPageNetworkDiagramData(MatchBean matchBean, StringBuilder builder) throws JSONException {
		if (matchBean.getSubjectRelationRelative() == null || matchBean.getSubjectRelationRelative().isEmpty()) {
			return;
		}

		builder.append("<br/><br/><br/>");
		// Crea l'inizio della tabella per ogni subject
		builder.append("<table cellspacing=\"0\" cellpadding=\"0\" style=\"width: 100%;\">");
		builder.append("<thead>");
		builder.append("<tr>");
		builder.append(
				"<th colspan=\"3\" style=\"text-align: left; font-size: 12px; padding: 5px; background-color: #008891;\">"
						+ "RELAZIONI</th>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<th class=\"sTH\">Role</th>");
		builder.append("<th class=\"sTH\">Person</th>");
		builder.append("<th class=\"sTH\">Name</th>");
		builder.append("</tr>");
		builder.append("</thead>");
		builder.append("<tbody>");
		// Itera sull'array "subjectRelationRelative"
		for (int i = 0; i < matchBean.getSubjectRelationRelative().size(); i++) {
			Hashtable<String, String> hashtable = getHashtableList(
					matchBean.getSubjectRelationRelative().get(i).getContent());

			// Aggiungi il contenuto "content"
			builder.append("<tr>");
			builder.append("<td class=\"sTL\">" + hashtable.get("role") + "</td>");
			builder.append("<td class=\"sTL\">" + hashtable.get("person") + "</td>");
			builder.append("<td class=\"sTL\">" + hashtable.get("name.full") + "</td>");
			builder.append("</tr>");
		}
		builder.append("</tbody>");
		builder.append("</table>");
	}

	// NOTIZIE

	private void getPageSourcesSummary(MatchBean matchBean, StringBuilder builder)
			throws JSONException, URISyntaxException {
		builder.append("<p></p>");
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" align=\"center\">");
		builder.append("<tr>");
		builder.append("<td style=\"text-align: center;\">");
		builder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"90%\" align=\"center\">");
		builder.append("<tr>");
		builder.append("<td height=\"100px\" class=\"s1\" style=\"text-align: left;\">9) Sources summary</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td class=\"s2\" style=\"text-align: justify;\">");
		builder.append("This section lists all the sources used in the preparation of this report.");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td>");
		getNotizie(matchBean, builder);
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("</table>");
		builder.append("</td></tr>");
		builder.append("</table>");
	}

	private static void getNotizie(MatchBean matchBean, StringBuilder builder)
			throws JSONException, URISyntaxException {
		List<String> urlArray = new ArrayList<>();

		// Crea l'inizio della tabella Notizie
		builder.append("<br/>");
		builder.append("<table cellspacing=\"0\" cellpadding=\"0\" style=\"width: 100%;\">");

		// SubjectPoiBean
		getNotizieNameFull(matchBean, builder, urlArray);
		getNotiziePerson(matchBean, builder, urlArray);
		getNotizieGender(matchBean, builder, urlArray);
		getNotizieBirthDate(matchBean, builder, urlArray);
		getNotizieBirthPlace(matchBean, builder, urlArray);
		getNotizieNationality(matchBean, builder, urlArray);
		getNotizieIllegal(matchBean, builder, urlArray);
		getNotizieIdPlatform(matchBean, builder, urlArray);
		getNotiziePlatform(matchBean, builder, urlArray);
		getNotizieNameFirst(matchBean, builder, urlArray);
		getNotizieNameLast(matchBean, builder, urlArray);
		getNotizieIdPassport(matchBean, builder, urlArray);
		getNotiziePhoto(matchBean, builder, urlArray);
		getNotizieFunction(matchBean, builder, urlArray);
		getNotizieFunctionPublic(matchBean, builder, urlArray);
		getNotizieFunctionPolitical(matchBean, builder, urlArray);
		getNotizieSanction(matchBean, builder, urlArray);
		getNotizieMedia(matchBean, builder, urlArray);
		getNotizieDead(matchBean, builder, urlArray);
		getNotizieRelationRelative(matchBean, builder, urlArray);

		builder.append("</table>");
	}

	private static void getNotizieNameFull(MatchBean matchBean, StringBuilder builder, List<String> urlArray)
			throws JSONException, URISyntaxException {
		if (matchBean.getSubjectNameFull() == null || matchBean.getSubjectNameFull().isEmpty()) {
			return;
		}

		// Itera sull'array "subjectNameFull"
		for (int i = 0; i < matchBean.getSubjectNameFull().size(); i++) {
			SubjectPoiBean subject = matchBean.getSubjectNameFull().get(i);

			getSois(builder, urlArray, subject);
		}
	}

	private static void getNotiziePerson(MatchBean matchBean, StringBuilder builder, List<String> urlArray)
			throws JSONException, URISyntaxException {
		if (matchBean.getSubjectPerson() == null || matchBean.getSubjectPerson().isEmpty()) {
			return;
		}

		// Itera sull'array "subjectPerson"
		for (int i = 0; i < matchBean.getSubjectPerson().size(); i++) {
			SubjectPoiBean subject = matchBean.getSubjectPerson().get(i);

			getSois(builder, urlArray, subject);
		}
	}

	private static void getNotizieGender(MatchBean matchBean, StringBuilder builder, List<String> urlArray)
			throws JSONException, URISyntaxException {
		if (matchBean.getSubjectGender() == null || matchBean.getSubjectGender().isEmpty()) {
			return;
		}

		// Itera sull'array "subjectGender"
		for (int i = 0; i < matchBean.getSubjectGender().size(); i++) {
			SubjectPoiBean subject = matchBean.getSubjectGender().get(i);

			getSois(builder, urlArray, subject);
		}
	}

	private static void getNotizieBirthDate(MatchBean matchBean, StringBuilder builder, List<String> urlArray)
			throws JSONException, URISyntaxException {
		if (matchBean.getSubjectBirthDate() == null || matchBean.getSubjectBirthDate().isEmpty()) {
			return;
		}

		// Itera sull'array "subjectBirthDate"
		for (int i = 0; i < matchBean.getSubjectBirthDate().size(); i++) {
			SubjectPoiBean subject = matchBean.getSubjectBirthDate().get(i);

			getSois(builder, urlArray, subject);
		}
	}

	private static void getNotizieBirthPlace(MatchBean matchBean, StringBuilder builder, List<String> urlArray)
			throws JSONException, URISyntaxException {
		if (matchBean.getSubjectBirthPlace() == null || matchBean.getSubjectBirthPlace().isEmpty()) {
			return;
		}

		// Itera sull'array "subjectBirthPlace"
		for (int i = 0; i < matchBean.getSubjectBirthPlace().size(); i++) {
			SubjectPoiBean subject = matchBean.getSubjectBirthPlace().get(i);

			getSois(builder, urlArray, subject);
		}
	}

	private static void getNotizieNationality(MatchBean matchBean, StringBuilder builder, List<String> urlArray)
			throws JSONException, URISyntaxException {
		if (matchBean.getSubjectNationality() == null || matchBean.getSubjectNationality().isEmpty()) {
			return;
		}

		// Itera sull'array "subjectNationality"
		for (int i = 0; i < matchBean.getSubjectNationality().size(); i++) {
			SubjectPoiBean subject = matchBean.getSubjectNationality().get(i);

			getSois(builder, urlArray, subject);
		}
	}

	private static void getNotizieIllegal(MatchBean matchBean, StringBuilder builder, List<String> urlArray)
			throws JSONException, URISyntaxException {
		if (matchBean.getSubjectIllegal() == null || matchBean.getSubjectIllegal().isEmpty()) {
			return;
		}

		// Itera sull'array "subjectIllegal"
		for (int i = 0; i < matchBean.getSubjectIllegal().size(); i++) {
			SubjectPoiBean subject = matchBean.getSubjectIllegal().get(i);

			getSois(builder, urlArray, subject);
		}
	}

	private static void getNotizieIdPlatform(MatchBean matchBean, StringBuilder builder, List<String> urlArray)
			throws JSONException, URISyntaxException {
		if (matchBean.getSubjectIdPlatform() == null || matchBean.getSubjectIdPlatform().isEmpty()) {
			return;
		}

		// Itera sull'array "subjectIdPlatform"
		for (int i = 0; i < matchBean.getSubjectIdPlatform().size(); i++) {
			SubjectPoiBean subject = matchBean.getSubjectIdPlatform().get(i);

			getSois(builder, urlArray, subject);
		}
	}

	private static void getNotiziePlatform(MatchBean matchBean, StringBuilder builder, List<String> urlArray)
			throws JSONException, URISyntaxException {
		if (matchBean.getSubjectPlatform() == null || matchBean.getSubjectPlatform().isEmpty()) {
			return;
		}

		// Itera sull'array "subjectPlatform"
		for (int i = 0; i < matchBean.getSubjectPlatform().size(); i++) {
			SubjectPoiBean subject = matchBean.getSubjectPlatform().get(i);

			getSois(builder, urlArray, subject);
		}
	}

	private static void getNotizieNameFirst(MatchBean matchBean, StringBuilder builder, List<String> urlArray)
			throws JSONException, URISyntaxException {
		if (matchBean.getSubjectNameFirst() == null || matchBean.getSubjectNameFirst().isEmpty()) {
			return;
		}

		// Itera sull'array "subjectNameFirst"
		for (int i = 0; i < matchBean.getSubjectNameFirst().size(); i++) {
			SubjectPoiBean subject = matchBean.getSubjectNameFirst().get(i);

			getSois(builder, urlArray, subject);
		}
	}

	private static void getNotizieNameLast(MatchBean matchBean, StringBuilder builder, List<String> urlArray)
			throws JSONException, URISyntaxException {
		if (matchBean.getSubjectNameLast() == null || matchBean.getSubjectNameLast().isEmpty()) {
			return;
		}

		// Itera sull'array "subjectNameLast"
		for (int i = 0; i < matchBean.getSubjectNameLast().size(); i++) {
			SubjectPoiBean subject = matchBean.getSubjectNameLast().get(i);

			getSois(builder, urlArray, subject);
		}
	}

	private static void getNotizieIdPassport(MatchBean matchBean, StringBuilder builder, List<String> urlArray)
			throws JSONException, URISyntaxException {
		if (matchBean.getSubjectIdPassport() == null || matchBean.getSubjectIdPassport().isEmpty()) {
			return;
		}

		// Itera sull'array "subjectIdPassport"
		for (int i = 0; i < matchBean.getSubjectIdPassport().size(); i++) {
			SubjectPoiBean subject = matchBean.getSubjectIdPassport().get(i);

			getSois(builder, urlArray, subject);
		}
	}

	private static void getNotiziePhoto(MatchBean matchBean, StringBuilder builder, List<String> urlArray)
			throws JSONException, URISyntaxException {
		if (matchBean.getSubjectPhoto() == null || matchBean.getSubjectPhoto().isEmpty()) {
			return;
		}

		// Itera sull'array "subjectPhoto"
		for (int i = 0; i < matchBean.getSubjectPhoto().size(); i++) {
			SubjectPoiBean subject = matchBean.getSubjectPhoto().get(i);

			getSois(builder, urlArray, subject);
		}
	}

	private static void getNotizieFunction(MatchBean matchBean, StringBuilder builder, List<String> urlArray)
			throws JSONException, URISyntaxException {
		if (matchBean.getSubjectFunction() == null || matchBean.getSubjectFunction().isEmpty()) {
			return;
		}

		// Itera sull'array "subjectFunction"
		for (int i = 0; i < matchBean.getSubjectFunction().size(); i++) {
			SubjectPoiBean subject = matchBean.getSubjectFunction().get(i);

			getSois(builder, urlArray, subject);
		}
	}

	private static void getNotizieFunctionPublic(MatchBean matchBean, StringBuilder builder, List<String> urlArray)
			throws JSONException, URISyntaxException {
		if (matchBean.getSubjectFunctionPublic() == null || matchBean.getSubjectFunctionPublic().isEmpty()) {
			return;
		}

		// Itera sull'array "subjectFunctionPublic"
		for (int i = 0; i < matchBean.getSubjectFunctionPublic().size(); i++) {
			SubjectPoiBean subject = matchBean.getSubjectFunctionPublic().get(i);

			getSois(builder, urlArray, subject);
		}
	}

	private static void getNotizieFunctionPolitical(MatchBean matchBean, StringBuilder builder, List<String> urlArray)
			throws JSONException, URISyntaxException {
		if (matchBean.getSubjectFunctionPolitical() == null || matchBean.getSubjectFunctionPolitical().isEmpty()) {
			return;
		}

		// Itera sull'array "subjectFunctionPolitical"
		for (int i = 0; i < matchBean.getSubjectFunctionPolitical().size(); i++) {
			SubjectPoiBean subject = matchBean.getSubjectFunctionPolitical().get(i);

			getSois(builder, urlArray, subject);
		}
	}

	private static void getNotizieSanction(MatchBean matchBean, StringBuilder builder, List<String> urlArray)
			throws JSONException, URISyntaxException {
		if (matchBean.getSubjectSanction() == null || matchBean.getSubjectSanction().isEmpty()) {
			return;
		}

		// Itera sull'array "subjectSanction"
		for (int i = 0; i < matchBean.getSubjectSanction().size(); i++) {
			SubjectPoiBean subject = matchBean.getSubjectSanction().get(i);

			getSois(builder, urlArray, subject);
		}
	}

	private static void getNotizieMedia(MatchBean matchBean, StringBuilder builder, List<String> urlArray)
			throws JSONException, URISyntaxException {
		if (matchBean.getSubjectMedia() == null || matchBean.getSubjectMedia().isEmpty()) {
			return;
		}

		// Itera sull'array "subjectMedia"
		for (int i = 0; i < matchBean.getSubjectMedia().size(); i++) {
			SubjectPoiBean subject = matchBean.getSubjectMedia().get(i);

			getSois(builder, urlArray, subject);
		}
	}

	private static void getNotizieDead(MatchBean matchBean, StringBuilder builder, List<String> urlArray)
			throws JSONException, URISyntaxException {
		if (matchBean.getSubjectDead() == null || matchBean.getSubjectDead().isEmpty()) {
			return;
		}

		// Itera sull'array "subjectDead"
		for (int i = 0; i < matchBean.getSubjectDead().size(); i++) {
			SubjectPoiBean subject = matchBean.getSubjectDead().get(i);

			getSois(builder, urlArray, subject);
		}
	}

	private static void getNotizieRelationRelative(MatchBean matchBean, StringBuilder builder, List<String> urlArray)
			throws JSONException, URISyntaxException {
		if (matchBean.getSubjectRelationRelative() == null || matchBean.getSubjectRelationRelative().isEmpty()) {
			return;
		}

		// Itera sull'array "subjectRelationRelative"
		for (int i = 0; i < matchBean.getSubjectRelationRelative().size(); i++) {
			SubjectPoiBean subject = matchBean.getSubjectRelationRelative().get(i);

			getSois(builder, urlArray, subject);
		}
	}

	private static void getSois(StringBuilder builder, List<String> urlArray, SubjectPoiBean subjectPoiBean)
			throws URISyntaxException {
		JSONObject subject = new JSONObject(subjectPoiBean);

		JSONArray soisList = null;
		if (!subject.isNull("soiBean")) {
			soisList = subject.getJSONArray("soiBean");

			// Itera sull'array "sois"
			for (int y = 0; y < soisList.length(); y++) {
				JSONObject sois = soisList.getJSONObject(y);

				String uri = (!sois.isNull("uri") ? sois.getString("uri") : "");
				if ("file".equals(uri)) {
					uri = (!sois.isNull("pibisiUri") ? sois.getString("pibisiUri") : "");
				}

				if (StringUtils.hasLength(uri) && !urlArray.contains(uri)) {
					urlArray.add(uri);

					String category = !sois.getJSONObject("soiGroup").isNull("info")
							? sois.getJSONObject("soiGroup").getString("info")
							: "";
					String description = !sois.isNull("description") ? sois.getString("description") : "";
					String coverage = !sois.isNull("scope") && !"".equals(sois.isNull("scope"))
							? sois.getString("scope")
							: "";
					String issuer = !sois.getJSONObject("soiGroup").isNull("issuer")
							&& !"".equals(sois.getJSONObject("soiGroup").getString("issuer"))
									? " - ".concat(sois.getJSONObject("soiGroup").getString("issuer"))
									: "";

					// Crea l'oggetto link
					URI url = new URI(uri);
					String htmlLink = "<a href=\"" + uri + "\" target=\"_blank\">" + url.getHost() + "</a>";

					builder.append("<tr>");
					builder.append("<td height=\"30px\" class=\"sL\">" + category.concat(" - ").concat(description)
							.concat(" (").concat(coverage).concat(issuer).concat(") ").concat(htmlLink) + "</td>");
					builder.append("</tr>");
					builder.append("<tr><td height=\"30px\"></td></tr>");
				}
			}
		}
	}
}
