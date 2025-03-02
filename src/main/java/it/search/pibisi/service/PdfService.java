package it.search.pibisi.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.search.pibisi.bean.SubjectPoiBean;
import it.search.pibisi.controller.pojo.AccountsSearchPojo;

@Service
public class PdfService extends BaseService {

	@Autowired
	private AccountsDetailService detailService;

	public void createPdf(AccountsSearchPojo requestJson) {

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			// Converte l'oggetto in una stringa JSON
			//requestJson.setSubjectId("d");
			String json = objectMapper.writeValueAsString(detailService.detail(requestJson));

			// Stampa il risultato
			System.out.println(json);

			// Crea il PDF dal Json
			createPdfFromJson(new JSONObject(json));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createPdfFromJson(JSONObject jsonObject) {
		try {

			// SubjectPoiBean
			JSONArray subjectNameFull = null;
			JSONArray subjectPerson = null;
			JSONArray subjectGender = null;
			JSONArray subjectBirthDate = null;
			JSONArray subjectBirthPlace = null;
			JSONArray subjectNationality = null;
			JSONArray subjectIllegal = null;
			JSONArray subjectIdPlatform = null;
			JSONArray subjectPlatform = null;
			JSONArray subjectNameFirst = null;
			JSONArray subjectNameLast = null;
			JSONArray subjectIdPassport = null;
			JSONArray subjectPhoto = null;
			JSONArray subjectFunction = null;
			JSONArray subjectFunctionPublic = null;
			JSONArray subjectFunctionPolitical = null;
			JSONArray subjectSanction = null;
			JSONArray subjectMedia = null;
			JSONArray subjectDead = null;
			JSONArray subjectRelationRelative = null;

			if (!jsonObject.isNull("subjectNameFull"))
				subjectNameFull = jsonObject.getJSONArray("subjectNameFull");
			if (!jsonObject.isNull("subjectPerson"))
				subjectPerson = jsonObject.getJSONArray("subjectPerson");
			if (!jsonObject.isNull("subjectGender"))
				subjectGender = jsonObject.getJSONArray("subjectGender");
			if (!jsonObject.isNull("subjectBirthDate"))
				subjectBirthDate = jsonObject.getJSONArray("subjectBirthDate");
			if (!jsonObject.isNull("subjectBirthPlace"))
				subjectBirthPlace = jsonObject.getJSONArray("subjectBirthPlace");
			if (!jsonObject.isNull("subjectNationality"))
				subjectNationality = jsonObject.getJSONArray("subjectNationality");
			if (!jsonObject.isNull("subjectIllegal"))
				subjectIllegal = jsonObject.getJSONArray("subjectIllegal");
			if (!jsonObject.isNull("subjectIdPlatform"))
				subjectIdPlatform = jsonObject.getJSONArray("subjectIdPlatform");
			if (!jsonObject.isNull("subjectPlatform"))
				subjectPlatform = jsonObject.getJSONArray("subjectPlatform");
			if (!jsonObject.isNull("subjectNameFirst"))
				subjectNameFirst = jsonObject.getJSONArray("subjectNameFirst");
			if (!jsonObject.isNull("subjectNameLast"))
				subjectNameLast = jsonObject.getJSONArray("subjectNameLast");
			if (!jsonObject.isNull("subjectIdPassport"))
				subjectIdPassport = jsonObject.getJSONArray("subjectIdPassport");
			if (!jsonObject.isNull("subjectPhoto"))
				subjectPhoto = jsonObject.getJSONArray("subjectPhoto");
			if (!jsonObject.isNull("subjectFunction"))
				subjectFunction = jsonObject.getJSONArray("subjectFunction");
			if (!jsonObject.isNull("subjectFunctionPublic"))
				subjectFunctionPublic = jsonObject.getJSONArray("subjectFunctionPublic");
			if (!jsonObject.isNull("subjectFunctionPolitical"))
				subjectFunctionPolitical = jsonObject.getJSONArray("subjectFunctionPolitical");
			if (!jsonObject.isNull("subjectSanction"))
				subjectSanction = jsonObject.getJSONArray("subjectSanction");
			if (!jsonObject.isNull("subjectMedia"))
				subjectMedia = jsonObject.getJSONArray("subjectMedia");
			if (!jsonObject.isNull("subjectDead"))
				subjectDead = jsonObject.getJSONArray("subjectDead");
			if (!jsonObject.isNull("subjectRelationRelative"))
				subjectRelationRelative = jsonObject.getJSONArray("subjectRelationRelative");

			// Crea l'HTML per il contenuto
			StringBuilder builder = new StringBuilder();
			builder.append("<html><head><style>body { font-family: Arial, sans-serif; }</style></head><body>");

			getInformazioniPrincipali(jsonObject, subjectNameFull, subjectBirthDate, subjectNationality, builder);
			
		    // Crea l'inizio della tabella per ogni subject
		    builder.append("<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"background-color: #00587A;color: #FFFFFF;border-color:#E7E7DE\">\n");
		    builder.append("<thead>\n");
		    builder.append("<tr>\n");
		    builder.append("<th style=\"padding: 10px; background-color: #008891\">Carica</th>\n");
		    builder.append("<th style=\"padding: 10px; background-color: #008891\">Organizzazione</th>\n");
		    builder.append("<th style=\"padding: 10px; background-color: #008891\">Dal</th>\n");
		    builder.append("<th style=\"padding: 10px; background-color: #008891\">Al</th>\n");
		    builder.append("</tr>\n");
		    builder.append("</thead>\n");
		    builder.append("<tbody>\n");
		    // Itera sull'array "subjectFunction"
			for (int i = 0; i < subjectFunction.length(); i++) {
			    JSONObject subject = subjectFunction.getJSONObject(i);


			    // Aggiungi il contenuto "content"
			    builder.append("<tr>");
			    builder.append("<td style=\"padding: 10px;\">" + (!subject.getJSONObject("content").isNull("charge")?subject.getJSONObject("content").getString("charge"):"") + "</td>");
			    builder.append("<td style=\"padding: 10px;\">" + (!subject.getJSONObject("content").isNull("organization")?subject.getJSONObject("content").getString("organization"):"") + "</td>");
			    builder.append("<td style=\"padding: 10px;\">" + (!subject.getJSONObject("content").isNull("from")?subject.getJSONObject("content").getString("from"):"") + "</td>");
			    builder.append("<td style=\"padding: 10px;\">" + (!subject.getJSONObject("content").isNull("to")?subject.getJSONObject("content").getString("to"):"") + "</td>");
			    builder.append("</tr>\n");
			}
		    builder.append("</tbody>\n");
		    builder.append("</table>\n");

		    builder.append("<hr>");

			JSONArray contentArray = jsonObject.getJSONArray("subjectNameFull");
			for (int i = 0; i < contentArray.length(); i++) {
				builder.append("<p>" + contentArray.getJSONObject(i).get("content") + "</p>");
			}

			builder.append("</hr></body></html>");

			// Usa Flying Saucer per generare il PDF
			OutputStream os = new FileOutputStream("C:\\Gestore\\pdf\\output_flying_saucer.pdf");
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(builder.toString());
			renderer.layout();
			renderer.createPDF(os);
			os.close();

			System.out.println("PDF creato con successo!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void getInformazioniPrincipali(JSONObject jsonObject, JSONArray subjectNameFull,
			JSONArray subjectBirthDate, JSONArray subjectNationality, StringBuilder builder) {
		builder.append("<h3>Informazioni principali</h3>\n");
		builder.append("<table border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n");
		builder.append(
				"<tr><td><strong>Uuid:</strong></td><td>" + jsonObject.getString("subjectUuid") + "</td></tr>\n");
		builder.append("<tr><td><strong>Classificazione:</strong></td><td>" + jsonObject.getString("typeCategory")
				+ "</td></tr>\n");
		builder.append("<tr><td><strong>Data Creazione:</strong></td><td>" + jsonObject.getString("createdAtDate")
				+ "</td></tr>\n");
		builder.append("<tr><td><strong>Nominativo:</strong></td><td>"
				+ subjectNameFull.getJSONObject(0).getString("content") + "</td></tr>\n");
		builder.append("<tr><td><strong>Data Nascita:</strong></td><td>"
				+ subjectBirthDate.getJSONObject(0).getString("content") + "</td></tr>\n");
		builder.append("<tr><td><strong>Nazionalità:</strong></td><td>"
				+ subjectNationality.getJSONObject(0).getString("content") + "</td></tr>\n");
		builder.append("</table>");
	}
}
