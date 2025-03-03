package it.search.pibisi.service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.html2pdf.HtmlConverter;

import it.search.pibisi.controller.pojo.AccountsSearchPojo;

@Service
public class PdfService extends BaseService {

	private static final String TAG_URI = "uri";

	private static final String TAG_DESCRIPTION = "description";

	private static final String TAG_INTERNAL_URI = "internalUri";

	private static final String TAG_COUNTRY = "country";

	private static final String TAG_CITY = "city";

	private static final String TAG_NUMBER = "number";

	private static final String TAG_PLATFORM = "platform";

	private static final String TAG_TYPE = "type";

	private static final String TAG_SUMMARY = "summary";

	private static final String TAG_REASON = "reason";

	private static final String TAG_ISSUER = "issuer";

	private static final String TAG_PERSON = "person";

	private static final String TAG_ROLE = "role";

	private static final String TAG_SCOPE = "scope";

	private static final String TAG_URL = "url";

	private static final String TAG_ACTION = "action";

	private static final String TAG_ISSUE = "issue";

	private static final String TAG_CHARGE = "charge";

	private static final String TAG_ORGANIZATION = "organization";

	private static final String TAG_TO = "to";

	private static final String TAG_FROM = "from";

	private static final String TAG_CONTENT = "content";

	private static final String TABLE_OPEN = "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"background-color: #00587A; color: #FFFFFF;\">";

	private static final String TABLE_CLOSE = "</table>";

	private static final String TBODY_CLOSE = "</tbody>";

	private static final String TBODY_OPEN = "<tbody>";

	private static final String THEAD_CLOSE = "</thead>";

	private static final String THEAD_OPEN = "<thead>";

	private static final String BR_BR_BR = "<br/><br/><br/>";

	private static final String SOI_GROUP = "soiGroup";

	private static final String SOI_BEAN = "soiBean";

	private static final String TH_CLOSE = "</th>";

	private static final String TH_OPEN = "<th style=\"border: 1px solid #008891; padding: 10px; background-color: #008891; \">";

	private static final String TR_CLOSE = "</tr>";

	private static final String TR_OPEN = "<tr>";

	private static final String TD_CLOSE = "</td>";

	private static final String TD_OPEN = "<td style=\"border: 1px solid #008891; padding: 10px;\">";

	private static final String TD_OPEN_CENTER = "<td style=\"border: 1px solid #008891; text-align: center;\">";

	private static final String TD_OPEN_NORMAL = "<td>";

	private static final String SUBJECT_DEAD = "subjectDead";

	private static final String SUBJECT_PHOTO = "subjectPhoto";

	private static final String SUBJECT_NAME_LAST = "subjectNameLast";

	private static final String SUBJECT_NAME_FIRST = "subjectNameFirst";

	private static final String SUBJECT_PERSON = "subjectPerson";

	private static final String SUBJECT_GENDER = "subjectGender";

	private static final String SUBJECT_ID_PASSPORT = "subjectIdPassport";

	private static final String SUBJECT_ID_PLATFORM = "subjectIdPlatform";

	private static final String SUBJECT_NATIONALITY = "subjectNationality";

	private static final String SUBJECT_BIRTH_PLACE = "subjectBirthPlace";

	private static final String SUBJECT_BIRTH_DATE = "subjectBirthDate";

	private static final String SUBJECT_NAME_FULL = "subjectNameFull";

	private static final String SUBJECT_ILLEGAL = "subjectIllegal";

	private static final String SUBJECT_SANCTION = "subjectSanction";

	private static final String SUBJECT_FUNCTION_POLITICAL = "subjectFunctionPolitical";

	private static final String SUBJECT_RELATION_RELATIVE = "subjectRelationRelative";

	private static final String SUBJECT_FUNCTION_PUBLIC = "subjectFunctionPublic";

	private static final String SUBJECT_MEDIA = "subjectMedia";

	private static final String SUBJECT_FUNCTION = "subjectFunction";

	private static final String SUBJECT_PLATFORM = "subjectPlatform";

	@Autowired
	private AccountsDetailService detailService;

	public static String getCurrentDateTime() {
		// Ottieni la data e l'ora correnti
		LocalDateTime now = LocalDateTime.now();
		return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	public byte[] createPdf(AccountsSearchPojo requestJson) {

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			// Converte l'oggetto in una stringa JSON
			String json = objectMapper.writeValueAsString(detailService.detail(requestJson));

			// Crea il PDF dal Json
			return createPdfFromJson(new JSONObject(json));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private byte[] createPdfFromJson(JSONObject jsonObject) {
		try {

			// Crea l'HTML per il contenuto
			StringBuilder builder = new StringBuilder();
			builder.append("<html>");
			builder.append("<head>");
			builder.append("<style>");
			builder.append("html { margin: 0; padding: 0; height: 100%; width: 100%; }");
			builder.append(
					"body { font-family: Tahoma, Verdana, Segoe, sans-serif; font-size: 10px;  margin: 0; padding: 0; height: 100%; width: 100%; }");
			builder.append("</style>");
			builder.append(
					"<link href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css\" rel=\"stylesheet\">");
			builder.append("</head>");
			builder.append("<body>");

			getHeader(builder);
			getInformazioniPrincipali(jsonObject, builder);

			getTableSubjectNameFull(jsonObject, builder);
			getTableSubjectFunction(jsonObject, builder);
			getTableSubjectMedia(jsonObject, builder);
			getTableSubjectPlatform(jsonObject, builder);
			getTableSubjectFunctionPolitical(jsonObject, builder);
			getTableSubjectRelationRelative(jsonObject, builder);
			getTableSubjectFunctionPublic(jsonObject, builder);
			getTableSubjectSanction(jsonObject, builder);
			getTableSubjectIllegal(jsonObject, builder);

			getNotizie(jsonObject, builder);

			builder.append("</body></html>");

//			OutputStream outputFile = new FileOutputStream("C:\\Gestore\\pdf\\output_flying_saucer.pdf");
//
//			HtmlConverter.convertToPdf(builder.toString(), outputFile);
//			System.out.println("PDF creato con successo.");

	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        HtmlConverter.convertToPdf(builder.toString(), byteArrayOutputStream);
	        byteArrayOutputStream.close();
	        return byteArrayOutputStream.toByteArray();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void getHeader(StringBuilder builder) {
		// Ottieni l'URL dell'immagine all'interno della cartella resources
		ClassLoader classLoader = ResourceLoader.class.getClassLoader();
		URL imageUrl = classLoader.getResource("logo/AEGIS-X-logo.png");

		builder.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
		builder.append("<tr><td style=\"text-align: center;\">");
		builder.append("<img src=\"" + imageUrl.toString() + "\" width=\"200\" height=\"200\"></img>");
		builder.append(TD_CLOSE + TR_CLOSE);
		builder.append(TR_OPEN + TD_OPEN_NORMAL + BR_BR_BR + BR_BR_BR + TD_CLOSE + TR_CLOSE);
		builder.append("<tr style=\"text-align: right;\">" + TD_OPEN_NORMAL + "Data Report: "
				+ PdfService.getCurrentDateTime() + TD_CLOSE + TR_CLOSE);
		builder.append(TR_OPEN + TD_OPEN_NORMAL + BR_BR_BR + TD_CLOSE + TR_CLOSE);
		builder.append(TABLE_CLOSE);
	}

	private static void getTableSubjectFunction(JSONObject jsonObject, StringBuilder builder) throws JSONException {
		JSONArray subjectFunction = null;

		if (!jsonObject.isNull(SUBJECT_FUNCTION)) {
			subjectFunction = jsonObject.getJSONArray(SUBJECT_FUNCTION);

			builder.append(BR_BR_BR);
			builder.append("<h3>Funzione</h3>");
			// Crea l'inizio della tabella per ogni subject
			builder.append(TABLE_OPEN);
			builder.append(THEAD_OPEN);
			builder.append(TR_OPEN);
			builder.append(TH_OPEN + "Carica" + TH_CLOSE);
			builder.append(TH_OPEN + "Organizzazione" + TH_CLOSE);
			builder.append(TH_OPEN + "Dal" + TH_CLOSE);
			builder.append(TH_OPEN + "Al" + TH_CLOSE);
			builder.append(TR_CLOSE);
			builder.append(THEAD_CLOSE);
			builder.append(TBODY_OPEN);
			// Itera sull'array "subjectFunction"
			for (int i = 0; i < subjectFunction.length(); i++) {
				JSONObject subject = subjectFunction.getJSONObject(i);

				String charge = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_CHARGE))
					charge = subject.getJSONObject(TAG_CONTENT).getString(TAG_CHARGE);
				String organization = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_ORGANIZATION))
					organization = subject.getJSONObject(TAG_CONTENT).getString(TAG_ORGANIZATION);
				String from = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_FROM))
					from = subject.getJSONObject(TAG_CONTENT).getString(TAG_FROM);
				String to = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_TO))
					to = subject.getJSONObject(TAG_CONTENT).getString(TAG_TO);

				// Aggiungi il contenuto "content"
				builder.append(TR_OPEN);
				builder.append(TD_OPEN + charge + TD_CLOSE);
				builder.append(TD_OPEN + organization + TD_CLOSE);
				builder.append(TD_OPEN + from + TD_CLOSE);
				builder.append(TD_OPEN + to + TD_CLOSE);
				builder.append(TR_CLOSE);
			}
			builder.append(TBODY_CLOSE);
			builder.append(TABLE_CLOSE);
		}
	}

	private static void getTableSubjectMedia(JSONObject jsonObject, StringBuilder builder) throws JSONException {
		JSONArray subjectMedia = null;

		if (!jsonObject.isNull(SUBJECT_MEDIA)) {
			subjectMedia = jsonObject.getJSONArray(SUBJECT_MEDIA);

			builder.append(BR_BR_BR);
			builder.append("<h3>Media</h3>");
			// Crea l'inizio della tabella per ogni subject
			builder.append(TABLE_OPEN);
			builder.append(THEAD_OPEN);
			builder.append(TR_OPEN);
			builder.append(TH_OPEN + "Data Pubblicazione" + TH_CLOSE);
			builder.append(TH_OPEN + "Tipologia" + TH_CLOSE);
			builder.append(TH_OPEN + "Stato" + TH_CLOSE);
			builder.append(TH_OPEN + "Url" + TH_CLOSE);
			builder.append(TR_CLOSE);
			builder.append(THEAD_CLOSE);
			builder.append(TBODY_OPEN);
			// Itera sull'array "subjectMedia"
			for (int i = 0; i < subjectMedia.length(); i++) {
				JSONObject subject = subjectMedia.getJSONObject(i);

				String url = null;
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_URL))
					url = subject.getJSONObject(TAG_CONTENT).getString(TAG_URL).toString();
				String from = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_FROM))
					from = subject.getJSONObject(TAG_CONTENT).getString(TAG_FROM);
				String issue = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_ISSUE))
					issue = subject.getJSONObject(TAG_CONTENT).getString(TAG_ISSUE);

				String action = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_ACTION))
					action = subject.getJSONObject(TAG_CONTENT).getString(TAG_ACTION);

				// Aggiungi il contenuto "content"
				builder.append(TR_OPEN);
				builder.append(TD_OPEN + from + TD_CLOSE);
				builder.append(TD_OPEN + issue + TD_CLOSE);
				builder.append(TD_OPEN + action + TD_CLOSE);
				builder.append(TD_OPEN_CENTER);
				if (StringUtils.hasLength(url))
					builder.append("<a href=" + url
							+ "\" target=\"notizia\" style=\"text-decoration: none; color: inherit; text-align: center;\"><i class=\"fas fa-link\" style=\"font-size: 10px;\"></i></a>");
				builder.append(TD_CLOSE);
				builder.append(TR_CLOSE);
			}
			builder.append(TBODY_CLOSE);
			builder.append(TABLE_CLOSE);
		}
	}

	private static void getTableSubjectFunctionPublic(JSONObject jsonObject, StringBuilder builder)
			throws JSONException {
		JSONArray subjectFunctionPublic = null;

		if (!jsonObject.isNull(SUBJECT_FUNCTION_PUBLIC)) {
			subjectFunctionPublic = jsonObject.getJSONArray(SUBJECT_FUNCTION_PUBLIC);

			builder.append(BR_BR_BR);
			builder.append("<h3>Funzione Pubblica</h3>");
			// Crea l'inizio della tabella per ogni subject
			builder.append(TABLE_OPEN);
			builder.append(THEAD_OPEN);
			builder.append(TR_OPEN);
			builder.append(TH_OPEN + "Organizzaione" + TH_CLOSE);
			builder.append(TH_OPEN + "Ruolo" + TH_CLOSE);
			builder.append(TH_OPEN + "Ambito" + TH_CLOSE);
			builder.append(TH_OPEN + "Dal" + TH_CLOSE);
			builder.append(TH_OPEN + "Al" + TH_CLOSE);
			builder.append(TR_CLOSE);
			builder.append(THEAD_CLOSE);
			builder.append(TBODY_OPEN);
			// Itera sull'array "subjectFunctionPublic"
			for (int i = 0; i < subjectFunctionPublic.length(); i++) {
				JSONObject subject = subjectFunctionPublic.getJSONObject(i);

				String organization = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_ORGANIZATION))
					organization = subject.getJSONObject(TAG_CONTENT).getString(TAG_ORGANIZATION);
				String charge = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_CHARGE))
					charge = subject.getJSONObject(TAG_CONTENT).getString(TAG_CHARGE);
				String scope = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_SCOPE))
					scope = subject.getJSONObject(TAG_CONTENT).getString(TAG_SCOPE);
				String from = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_FROM))
					from = subject.getJSONObject(TAG_CONTENT).getString(TAG_FROM);
				String to = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_TO))
					to = subject.getJSONObject(TAG_CONTENT).getString(TAG_TO);

				// Aggiungi il contenuto "content"
				builder.append(TR_OPEN);
				builder.append(TD_OPEN + organization + TD_CLOSE);
				builder.append(TD_OPEN + charge + TD_CLOSE);
				builder.append(TD_OPEN + scope + TD_CLOSE);
				builder.append(TD_OPEN + from + TD_CLOSE);
				builder.append(TD_OPEN + to + TD_CLOSE);
				builder.append(TR_CLOSE);
			}
			builder.append(TBODY_CLOSE);
			builder.append(TABLE_CLOSE);
		}
	}

	private static void getTableSubjectRelationRelative(JSONObject jsonObject, StringBuilder builder)
			throws JSONException {
		JSONArray subjectRelationRelative = null;

		if (!jsonObject.isNull(SUBJECT_RELATION_RELATIVE)) {
			subjectRelationRelative = jsonObject.getJSONArray(SUBJECT_RELATION_RELATIVE);

			builder.append(BR_BR_BR);
			builder.append("<h3>Relazioni</h3>");
			// Crea l'inizio della tabella per ogni subject
			builder.append(TABLE_OPEN);
			builder.append(THEAD_OPEN);
			builder.append(TR_OPEN);
			builder.append(TH_OPEN + "Relazione" + TH_CLOSE);
			builder.append(TH_OPEN + "Tipo" + TH_CLOSE);
			builder.append(TH_OPEN + "Nome" + TH_CLOSE);
			builder.append(TR_CLOSE);
			builder.append(THEAD_CLOSE);
			builder.append(TBODY_OPEN);
			// Itera sull'array "subjectRelationRelative"
			for (int i = 0; i < subjectRelationRelative.length(); i++) {
				JSONObject subject = subjectRelationRelative.getJSONObject(i);

				String role = "";
				if (!subject.isNull(TAG_ROLE))
					role = subject.getString(TAG_ROLE);
				String person = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_PERSON))
					person = subject.getJSONObject(TAG_CONTENT).getString(TAG_PERSON);
				String nameFull = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull("name.full"))
					nameFull = subject.getJSONObject(TAG_CONTENT).getString("name.full");

				// Aggiungi il contenuto "content"
				builder.append(TR_OPEN);
				builder.append(TD_OPEN + role + TD_CLOSE);
				builder.append(TD_OPEN + person + TD_CLOSE);
				builder.append(TD_OPEN + nameFull + TD_CLOSE);
				builder.append(TR_CLOSE);
			}
			builder.append(TBODY_CLOSE);
			builder.append(TABLE_CLOSE);
		}
	}

	private static void getTableSubjectFunctionPolitical(JSONObject jsonObject, StringBuilder builder)
			throws JSONException {
		JSONArray subjectFunctionPolitical = null;

		if (!jsonObject.isNull(SUBJECT_FUNCTION_POLITICAL)) {
			subjectFunctionPolitical = jsonObject.getJSONArray(SUBJECT_FUNCTION_POLITICAL);

			builder.append(BR_BR_BR);
			builder.append("<h3>Funzione Politica</h3>");
			// Crea l'inizio della tabella per ogni subject
			builder.append(TABLE_OPEN);
			builder.append(THEAD_OPEN);
			builder.append(TR_OPEN);
			builder.append(TH_OPEN + "Organizzazione" + TH_CLOSE);
			builder.append(TH_OPEN + "Ruolo" + TH_CLOSE);
			builder.append(TH_OPEN + "Paese" + TH_CLOSE);
			builder.append(TR_CLOSE);
			builder.append(THEAD_CLOSE);
			builder.append(TBODY_OPEN);
			// Itera sull'array "subjectFunctionPolitical"
			for (int i = 0; i < subjectFunctionPolitical.length(); i++) {
				JSONObject subject = subjectFunctionPolitical.getJSONObject(i);

				String organization = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_ORGANIZATION))
					organization = subject.getJSONObject(TAG_CONTENT).getString(TAG_ORGANIZATION);
				String charge = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_CHARGE))
					charge = subject.getJSONObject(TAG_CONTENT).getString(TAG_CHARGE);
				String scope = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_SCOPE))
					scope = subject.getJSONObject(TAG_CONTENT).getString(TAG_SCOPE);

				// Aggiungi il contenuto "content"
				builder.append(TR_OPEN);
				builder.append(TD_OPEN + organization + TD_CLOSE);
				builder.append(TD_OPEN + charge + TD_CLOSE);
				builder.append(TD_OPEN + scope + TD_CLOSE);
				builder.append(TR_CLOSE);
			}
			builder.append(TBODY_CLOSE);
			builder.append(TABLE_CLOSE);
		}
	}

	private static void getTableSubjectSanction(JSONObject jsonObject, StringBuilder builder) throws JSONException {
		JSONArray subjectSanction = null;

		if (!jsonObject.isNull(SUBJECT_SANCTION)) {
			subjectSanction = jsonObject.getJSONArray(SUBJECT_SANCTION);

			builder.append(BR_BR_BR);
			builder.append("<h3>Sanzioni</h3>");
			// Crea l'inizio della tabella per ogni subject
			builder.append(TABLE_OPEN);
			builder.append(THEAD_OPEN);
			builder.append(TR_OPEN);
			builder.append(TH_OPEN + "Emittente" + TH_CLOSE);
			builder.append(TH_OPEN + "Motivo" + TH_CLOSE);
			builder.append(TH_OPEN + "Riepilogo" + TH_CLOSE);
			builder.append(TH_OPEN + "Data" + TH_CLOSE);
			builder.append(TR_CLOSE);
			builder.append(THEAD_CLOSE);
			builder.append(TBODY_OPEN);
			// Itera sull'array "subjectSanction"
			for (int i = 0; i < subjectSanction.length(); i++) {
				JSONObject subject = subjectSanction.getJSONObject(i);

				String issuer = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_ISSUER))
					issuer = subject.getJSONObject(TAG_CONTENT).getString(TAG_ISSUER);
				String reason = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_REASON))
					reason = subject.getJSONObject(TAG_CONTENT).getString(TAG_REASON);
				String summary = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_SUMMARY))
					summary = subject.getJSONObject(TAG_CONTENT).getString(TAG_SUMMARY);
				String from = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_FROM))
					from = subject.getJSONObject(TAG_CONTENT).getString(TAG_FROM);

				// Aggiungi il contenuto "content"
				builder.append(TR_OPEN);
				builder.append(TD_OPEN + issuer + TD_CLOSE);
				builder.append(TD_OPEN + reason + TD_CLOSE);
				builder.append(TD_OPEN + summary + TD_CLOSE);
				builder.append(TD_OPEN + from + TD_CLOSE);
				builder.append(TR_CLOSE);
			}
			builder.append(TBODY_CLOSE);
			builder.append(TABLE_CLOSE);
		}
	}

	private static void getTableSubjectIllegal(JSONObject jsonObject, StringBuilder builder) throws JSONException {
		JSONArray subjectIllegal = null;

		if (!jsonObject.isNull(SUBJECT_ILLEGAL)) {
			subjectIllegal = jsonObject.getJSONArray(SUBJECT_ILLEGAL);

			builder.append(BR_BR_BR);
			builder.append("<h3>Illegale</h3>");
			// Crea l'inizio della tabella per ogni subject
			builder.append(TABLE_OPEN);
			builder.append(THEAD_OPEN);
			builder.append(TR_OPEN);
			builder.append(TH_OPEN + "Tipo" + TH_CLOSE);
			builder.append(TH_OPEN + "Contenuto" + TH_CLOSE);
			builder.append(TR_CLOSE);
			builder.append(THEAD_CLOSE);
			builder.append(TBODY_OPEN);
			// Itera sull'array "subjectIllegal"
			for (int i = 0; i < subjectIllegal.length(); i++) {
				JSONObject subject = subjectIllegal.getJSONObject(i);

				String type = "";
				if (!subject.isNull(TAG_TYPE))
					type = subject.getString(TAG_TYPE);
				String content = "";
				if (!subject.isNull(TAG_CONTENT))
					content = subject.getString(TAG_CONTENT);

				builder.append(TR_OPEN);
				builder.append(TD_OPEN + type + TD_CLOSE);
				builder.append(TD_OPEN + content + TD_CLOSE);
				builder.append(TR_CLOSE);
			}
			builder.append(TBODY_CLOSE);
			builder.append(TABLE_CLOSE);
		}
	}

	private static void getTableSubjectPlatform(JSONObject jsonObject, StringBuilder builder) throws JSONException {
		JSONArray subjectPlatform = null;

		if (!jsonObject.isNull(SUBJECT_PLATFORM)) {
			subjectPlatform = jsonObject.getJSONArray(SUBJECT_PLATFORM);

			builder.append(BR_BR_BR);
			builder.append("<h3>Piattaforma</h3>");
			// Crea l'inizio della tabella per ogni subject
			builder.append(TABLE_OPEN);
			builder.append(THEAD_OPEN);
			builder.append(TR_OPEN);
			builder.append(TH_OPEN + "Piattaforma" + TH_CLOSE);
			builder.append(TH_OPEN + "Valore" + TH_CLOSE);
			builder.append(TR_CLOSE);
			builder.append(THEAD_CLOSE);
			builder.append(TBODY_OPEN);
			// Itera sull'array "subjectPlatform"
			for (int i = 0; i < subjectPlatform.length(); i++) {
				JSONObject subject = subjectPlatform.getJSONObject(i);

				String platform = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_PLATFORM))
					platform = subject.getJSONObject(TAG_CONTENT).getString(TAG_PLATFORM);
				String number = "";
				if (!subject.getJSONObject(TAG_CONTENT).isNull(TAG_NUMBER))
					number = subject.getJSONObject(TAG_CONTENT).getString(TAG_NUMBER);

				// Aggiungi il contenuto "content"
				builder.append(TR_OPEN);
				builder.append(TD_OPEN + platform + TD_CLOSE);
				builder.append(TD_OPEN + number + TD_CLOSE);
				builder.append(TR_CLOSE);
			}
			builder.append(TBODY_CLOSE);
			builder.append(TABLE_CLOSE);
		}
	}

	private static void getTableSubjectNameFull(JSONObject jsonObject, StringBuilder builder) throws JSONException {
		JSONArray subjectNameFull = null;

		if (!jsonObject.isNull(SUBJECT_NAME_FULL)) {
			subjectNameFull = jsonObject.getJSONArray(SUBJECT_NAME_FULL);

			builder.append(BR_BR_BR);
			builder.append("<h3>Altri Nomi</h3>");
			// Crea l'inizio della tabella per ogni subject
			builder.append(TABLE_OPEN);
			builder.append(THEAD_OPEN);
			builder.append(TR_OPEN);
			builder.append(TH_OPEN + "Nominativo" + TH_CLOSE);
			builder.append(TR_CLOSE);
			builder.append(THEAD_CLOSE);
			builder.append(TBODY_OPEN);
			// Itera sull'array "subjectNameFull"
			for (int i = 0; i < subjectNameFull.length(); i++) {
				JSONObject subject = subjectNameFull.getJSONObject(i);

				// Aggiungi il contenuto "content"
				builder.append(TR_OPEN);
				builder.append(TD_OPEN + (i + 1) + ". " + subject.getString(TAG_CONTENT) + TD_CLOSE);
				builder.append(TR_CLOSE);
			}
			builder.append(TBODY_CLOSE);
			builder.append(TABLE_CLOSE);
		}
	}

	private static void getInformazioniPrincipali(JSONObject jsonObject, StringBuilder builder) {
		String strSubjectUuid = "-";
		String strgTypeCategory = "-";
		String strCreatedAtDate = "-";
		String strTypePerson = "-";
		String strGender = "-";
		String urlPhoto = null;
		String contentNameFull = "-";
		String contentBirthDate = null;
		String strBirthPlace = null;
		String contentNationality = null;
		String strIdPlatform = null;
		String strIdPassport = null;

		if (!jsonObject.isNull("subjectUuid"))
			strSubjectUuid = jsonObject.getString("subjectUuid");

		if (!jsonObject.isNull("typeCategory"))
			strgTypeCategory = jsonObject.getString("typeCategory");

		if (!jsonObject.isNull("createdAtDate"))
			strCreatedAtDate = jsonObject.getString("createdAtDate");

		if (!jsonObject.isNull(SUBJECT_PERSON))
			strTypePerson = jsonObject.getJSONArray(SUBJECT_PERSON).getJSONObject(0).getString(TAG_TYPE);

		if (!jsonObject.isNull(SUBJECT_GENDER))
			strGender = jsonObject.getJSONArray(SUBJECT_GENDER).getJSONObject(0).getString(TAG_CONTENT);

		if (!jsonObject.isNull(SUBJECT_PHOTO)) {
			JSONObject jsonPhotoContent = jsonObject.getJSONArray(SUBJECT_PHOTO).getJSONObject(0)
					.getJSONObject(TAG_CONTENT);
			if (!jsonPhotoContent.isNull(TAG_URL))
				urlPhoto = jsonPhotoContent.getString(TAG_URL);
		}

		if (!jsonObject.isNull(SUBJECT_NAME_FULL)) {
			contentNameFull = jsonObject.getJSONArray(SUBJECT_NAME_FULL).getJSONObject(0).getString(TAG_CONTENT);

			if (!jsonObject.isNull(SUBJECT_DEAD)) {
				String contentDead = jsonObject.getJSONArray(SUBJECT_DEAD).getJSONObject(0).getString(TAG_CONTENT);
				if ("true".equals(contentDead))
					contentNameFull = contentNameFull.concat(" (Deceduto)");
			}
		}

		if (!jsonObject.isNull(SUBJECT_BIRTH_DATE))
			contentBirthDate = jsonObject.getJSONArray(SUBJECT_BIRTH_DATE).getJSONObject(0).getString(TAG_CONTENT);

		if (!jsonObject.isNull(SUBJECT_BIRTH_PLACE)) {
			JSONObject jsonBirthPlace = jsonObject.getJSONArray(SUBJECT_BIRTH_PLACE).getJSONObject(0)
					.getJSONObject(TAG_CONTENT);
			if (!jsonBirthPlace.isNull(TAG_CITY))
				strBirthPlace = jsonBirthPlace.getString(TAG_CITY);
			if (!jsonBirthPlace.isNull(TAG_COUNTRY))
				if (StringUtils.hasLength(strBirthPlace))
					strBirthPlace = strBirthPlace.concat(" (").concat(jsonBirthPlace.getString(TAG_COUNTRY))
							.concat(")");
				else
					strBirthPlace = jsonBirthPlace.getString(TAG_COUNTRY);
		}

		if (!jsonObject.isNull(SUBJECT_NATIONALITY))
			contentNationality = jsonObject.getJSONArray(SUBJECT_NATIONALITY).getJSONObject(0).getString(TAG_CONTENT);

		if (!jsonObject.isNull(SUBJECT_ID_PLATFORM)) {
			JSONObject contentIdPlatform = jsonObject.getJSONArray(SUBJECT_ID_PLATFORM).getJSONObject(0)
					.getJSONObject(TAG_CONTENT);
			if (!contentIdPlatform.isNull(TAG_NUMBER)) {
				strIdPlatform = contentIdPlatform.getString(TAG_NUMBER);
				if (!contentIdPlatform.isNull(TAG_PLATFORM)) {
					strIdPlatform = strIdPlatform.concat(" (").concat(contentIdPlatform.getString(TAG_PLATFORM))
							.concat(")");
				}
			}
		}

		if (!jsonObject.isNull(SUBJECT_ID_PASSPORT)) {
			JSONObject contentIdPassport = jsonObject.getJSONArray(SUBJECT_ID_PASSPORT).getJSONObject(0)
					.getJSONObject(TAG_CONTENT);
			if (!contentIdPassport.isNull(TAG_NUMBER)) {
				strIdPassport = contentIdPassport.getString(TAG_NUMBER);
				if (!contentIdPassport.isNull(TAG_COUNTRY)) {
					strIdPassport = strIdPassport.concat(" (").concat(contentIdPassport.getString(TAG_COUNTRY))
							.concat(")");
				}
			}
		}

		builder.append(BR_BR_BR);
		builder.append("<h3>Informazioni principali</h3>");
		builder.append("<table border=\"0\" cellpadding=\"5\" cellspacing=\"0\">");
		builder.append(TR_OPEN);
		if (StringUtils.hasLength(urlPhoto)) {
			builder.append("<td rowspan=\"9\" style=\"padding-right: 60px;\">");
			builder.append("<img src=\"" + urlPhoto + "\" style=\"max-width: 200; max-height: 200;\"></img>");
			builder.append(TD_CLOSE);
		}
		builder.append(TD_OPEN_NORMAL + "Uuid:" + TD_CLOSE + TD_OPEN_NORMAL + strSubjectUuid + TD_CLOSE + TR_CLOSE);
		builder.append(TR_OPEN + TD_OPEN_NORMAL + "Nominativo:" + TD_CLOSE + TD_OPEN_NORMAL + "<strong>"
				+ contentNameFull + "</strong>" + TD_CLOSE + TR_CLOSE);
		builder.append(
				TR_OPEN + TD_OPEN_NORMAL + "Tipo:" + TD_CLOSE + TD_OPEN_NORMAL + strTypePerson + TD_CLOSE + TR_CLOSE);
		builder.append(TR_OPEN + TD_OPEN_NORMAL + "Classificazione:" + TD_CLOSE + TD_OPEN_NORMAL + strgTypeCategory
				+ TD_CLOSE + TR_CLOSE);
		builder.append(TR_OPEN + TD_OPEN_NORMAL + "Data Creazione Profilo:" + TD_CLOSE + TD_OPEN_NORMAL
				+ strCreatedAtDate + TD_CLOSE + TR_CLOSE);
		builder.append(
				TR_OPEN + TD_OPEN_NORMAL + "Genere:" + TD_CLOSE + TD_OPEN_NORMAL + strGender + TD_CLOSE + TR_CLOSE);
		if (StringUtils.hasLength(contentBirthDate))
			builder.append(TR_OPEN + TD_OPEN_NORMAL + "Data Nascita:" + TD_CLOSE + TD_OPEN_NORMAL + contentBirthDate
					+ TD_CLOSE + TR_CLOSE);
		if (StringUtils.hasLength(strBirthPlace))
			builder.append(TR_OPEN + TD_OPEN_NORMAL + "Luogo di Nascita:" + TD_CLOSE + TD_OPEN_NORMAL + strBirthPlace
					+ TD_CLOSE + TR_CLOSE);
		if (StringUtils.hasLength(contentNationality))
			builder.append(TR_OPEN + TD_OPEN_NORMAL + "Nazionalità:" + TD_CLOSE + TD_OPEN_NORMAL + contentNationality
					+ TD_CLOSE + TR_CLOSE);
		if (StringUtils.hasLength(strIdPlatform))
			builder.append(TR_OPEN + TD_OPEN_NORMAL + "ID Piattaforma:" + TD_CLOSE + TD_OPEN_NORMAL + strIdPlatform
					+ TD_CLOSE + TR_CLOSE);
		if (StringUtils.hasLength(strIdPassport))
			builder.append(TR_OPEN + TD_OPEN_NORMAL + "ID Passaporto:" + TD_CLOSE + TD_OPEN_NORMAL + strIdPassport
					+ TD_CLOSE + TR_CLOSE);
		builder.append(TABLE_CLOSE);
	}

	private static void getNotizie(JSONObject jsonObject, StringBuilder builder) throws JSONException {
		List<String> urlArray = new ArrayList<>();

		// Crea l'inizio della tabella Notizie
		builder.append(BR_BR_BR);
		builder.append("<h3>Notizie</h3>");
		builder.append(TABLE_OPEN);
		builder.append(THEAD_OPEN);
		builder.append(TR_OPEN);
		builder.append(TH_OPEN + "Ambito" + TH_CLOSE);
		builder.append(TH_OPEN + "Info" + TH_CLOSE);
		builder.append(TH_OPEN + "Descrizione" + TH_CLOSE);
		builder.append(TH_OPEN + "Emittente" + TH_CLOSE);
		builder.append(TH_OPEN + "LINK" + TH_CLOSE);
		builder.append(TR_CLOSE);
		builder.append(THEAD_CLOSE);
		builder.append(TBODY_OPEN);

		// SubjectPoiBean
		getNotizieNameFull(jsonObject, builder, urlArray);
		getNotiziePerson(jsonObject, builder, urlArray);
		getNotizieGender(jsonObject, builder, urlArray);
		getNotizieBirthDate(jsonObject, builder, urlArray);
		getNotizieBirthPlace(jsonObject, builder, urlArray);
		getNotizieNationality(jsonObject, builder, urlArray);
		getNotizieIllegal(jsonObject, builder, urlArray);
		getNotizieIdPlatform(jsonObject, builder, urlArray);
		getNotiziePlatform(jsonObject, builder, urlArray);
		getNotizieNameFirst(jsonObject, builder, urlArray);
		getNotizieNameLast(jsonObject, builder, urlArray);
		getNotizieIdPassport(jsonObject, builder, urlArray);
		getNotiziePhoto(jsonObject, builder, urlArray);
		getNotizieFunction(jsonObject, builder, urlArray);
		getNotizieFunctionPublic(jsonObject, builder, urlArray);
		getNotizieFunctionPolitical(jsonObject, builder, urlArray);
		getNotizieSanction(jsonObject, builder, urlArray);
		getNotizieMedia(jsonObject, builder, urlArray);
		getNotizieDead(jsonObject, builder, urlArray);
		getNotizieRelationRelative(jsonObject, builder, urlArray);

		builder.append(TBODY_CLOSE);
		builder.append(TABLE_CLOSE);
	}

	private static void getNotizieNameFull(JSONObject jsonObject, StringBuilder builder, List<String> urlArray)
			throws JSONException {
		JSONArray subjectNameFull = null;

		if (!jsonObject.isNull(SUBJECT_NAME_FULL)) {
			subjectNameFull = jsonObject.getJSONArray(SUBJECT_NAME_FULL);

			// Itera sull'array "subjectNameFull"
			for (int i = 0; i < subjectNameFull.length(); i++) {
				JSONObject subject = subjectNameFull.getJSONObject(i);

				getSois(builder, urlArray, subject);
			}
		}
	}

	private static void getNotiziePerson(JSONObject jsonObject, StringBuilder builder, List<String> urlArray)
			throws JSONException {
		JSONArray subjectPerson = null;

		if (!jsonObject.isNull(SUBJECT_PERSON)) {
			subjectPerson = jsonObject.getJSONArray(SUBJECT_PERSON);

			// Itera sull'array "subjectPerson"
			for (int i = 0; i < subjectPerson.length(); i++) {
				JSONObject subject = subjectPerson.getJSONObject(i);

				getSois(builder, urlArray, subject);
			}
		}
	}

	private static void getNotizieGender(JSONObject jsonObject, StringBuilder builder, List<String> urlArray)
			throws JSONException {
		JSONArray subjectGender = null;

		if (!jsonObject.isNull(SUBJECT_GENDER)) {
			subjectGender = jsonObject.getJSONArray(SUBJECT_GENDER);

			// Itera sull'array "subjectGender"
			for (int i = 0; i < subjectGender.length(); i++) {
				JSONObject subject = subjectGender.getJSONObject(i);

				getSois(builder, urlArray, subject);
			}
		}
	}

	private static void getNotizieBirthDate(JSONObject jsonObject, StringBuilder builder, List<String> urlArray)
			throws JSONException {
		JSONArray subjectBirthDate = null;

		if (!jsonObject.isNull(SUBJECT_BIRTH_DATE)) {
			subjectBirthDate = jsonObject.getJSONArray(SUBJECT_BIRTH_DATE);

			// Itera sull'array "subjectBirthDate"
			for (int i = 0; i < subjectBirthDate.length(); i++) {
				JSONObject subject = subjectBirthDate.getJSONObject(i);

				getSois(builder, urlArray, subject);
			}
		}
	}

	private static void getNotizieBirthPlace(JSONObject jsonObject, StringBuilder builder, List<String> urlArray)
			throws JSONException {
		JSONArray subjectBirthPlace = null;

		if (!jsonObject.isNull(SUBJECT_BIRTH_PLACE)) {
			subjectBirthPlace = jsonObject.getJSONArray(SUBJECT_BIRTH_PLACE);

			// Itera sull'array "subjectBirthPlace"
			for (int i = 0; i < subjectBirthPlace.length(); i++) {
				JSONObject subject = subjectBirthPlace.getJSONObject(i);

				getSois(builder, urlArray, subject);
			}
		}
	}

	private static void getNotizieNationality(JSONObject jsonObject, StringBuilder builder, List<String> urlArray)
			throws JSONException {
		JSONArray subjectNationality = null;

		if (!jsonObject.isNull(SUBJECT_NATIONALITY)) {
			subjectNationality = jsonObject.getJSONArray(SUBJECT_NATIONALITY);

			// Itera sull'array "subjectNationality"
			for (int i = 0; i < subjectNationality.length(); i++) {
				JSONObject subject = subjectNationality.getJSONObject(i);

				getSois(builder, urlArray, subject);
			}
		}
	}

	private static void getNotizieIllegal(JSONObject jsonObject, StringBuilder builder, List<String> urlArray)
			throws JSONException {
		JSONArray subjectIllegal = null;

		if (!jsonObject.isNull(SUBJECT_ILLEGAL)) {
			subjectIllegal = jsonObject.getJSONArray(SUBJECT_ILLEGAL);

			// Itera sull'array "subjectIllegal"
			for (int i = 0; i < subjectIllegal.length(); i++) {
				JSONObject subject = subjectIllegal.getJSONObject(i);

				getSois(builder, urlArray, subject);
			}
		}
	}

	private static void getNotizieIdPlatform(JSONObject jsonObject, StringBuilder builder, List<String> urlArray)
			throws JSONException {
		JSONArray subjectIdPlatform = null;

		if (!jsonObject.isNull(SUBJECT_ID_PLATFORM)) {
			subjectIdPlatform = jsonObject.getJSONArray(SUBJECT_ID_PLATFORM);

			// Itera sull'array "subjectIdPlatform"
			for (int i = 0; i < subjectIdPlatform.length(); i++) {
				JSONObject subject = subjectIdPlatform.getJSONObject(i);

				getSois(builder, urlArray, subject);
			}
		}
	}

	private static void getNotiziePlatform(JSONObject jsonObject, StringBuilder builder, List<String> urlArray)
			throws JSONException {
		JSONArray subjectPlatform = null;

		if (!jsonObject.isNull(SUBJECT_PLATFORM)) {
			subjectPlatform = jsonObject.getJSONArray(SUBJECT_PLATFORM);

			// Itera sull'array "subjectPlatform"
			for (int i = 0; i < subjectPlatform.length(); i++) {
				JSONObject subject = subjectPlatform.getJSONObject(i);

				getSois(builder, urlArray, subject);
			}
		}
	}

	private static void getNotizieNameFirst(JSONObject jsonObject, StringBuilder builder, List<String> urlArray)
			throws JSONException {
		JSONArray subjectNameFirst = null;

		if (!jsonObject.isNull(SUBJECT_NAME_FIRST)) {
			subjectNameFirst = jsonObject.getJSONArray(SUBJECT_NAME_FIRST);

			// Itera sull'array "subjectNameFirst"
			for (int i = 0; i < subjectNameFirst.length(); i++) {
				JSONObject subject = subjectNameFirst.getJSONObject(i);

				getSois(builder, urlArray, subject);
			}
		}
	}

	private static void getNotizieNameLast(JSONObject jsonObject, StringBuilder builder, List<String> urlArray)
			throws JSONException {
		JSONArray subjectNameLast = null;

		if (!jsonObject.isNull(SUBJECT_NAME_LAST)) {
			subjectNameLast = jsonObject.getJSONArray(SUBJECT_NAME_LAST);

			// Itera sull'array "subjectNameLast"
			for (int i = 0; i < subjectNameLast.length(); i++) {
				JSONObject subject = subjectNameLast.getJSONObject(i);

				getSois(builder, urlArray, subject);
			}
		}
	}

	private static void getNotizieIdPassport(JSONObject jsonObject, StringBuilder builder, List<String> urlArray)
			throws JSONException {
		JSONArray subjectIdPassport = null;

		if (!jsonObject.isNull(SUBJECT_ID_PASSPORT)) {
			subjectIdPassport = jsonObject.getJSONArray(SUBJECT_ID_PASSPORT);

			// Itera sull'array "subjectIdPassport"
			for (int i = 0; i < subjectIdPassport.length(); i++) {
				JSONObject subject = subjectIdPassport.getJSONObject(i);

				getSois(builder, urlArray, subject);
			}
		}
	}

	private static void getNotiziePhoto(JSONObject jsonObject, StringBuilder builder, List<String> urlArray)
			throws JSONException {
		JSONArray subjectPhoto = null;

		if (!jsonObject.isNull(SUBJECT_PHOTO)) {
			subjectPhoto = jsonObject.getJSONArray(SUBJECT_PHOTO);

			// Itera sull'array "subjectPhoto"
			for (int i = 0; i < subjectPhoto.length(); i++) {
				JSONObject subject = subjectPhoto.getJSONObject(i);

				getSois(builder, urlArray, subject);
			}
		}
	}

	private static void getNotizieFunction(JSONObject jsonObject, StringBuilder builder, List<String> urlArray)
			throws JSONException {
		JSONArray subjectFunction = null;

		if (!jsonObject.isNull(SUBJECT_FUNCTION)) {
			subjectFunction = jsonObject.getJSONArray(SUBJECT_FUNCTION);

			// Itera sull'array "subjectFunction"
			for (int i = 0; i < subjectFunction.length(); i++) {
				JSONObject subject = subjectFunction.getJSONObject(i);

				getSois(builder, urlArray, subject);
			}
		}
	}

	private static void getNotizieFunctionPublic(JSONObject jsonObject, StringBuilder builder, List<String> urlArray)
			throws JSONException {
		JSONArray subjectFunctionPublic = null;

		if (!jsonObject.isNull(SUBJECT_FUNCTION_PUBLIC)) {
			subjectFunctionPublic = jsonObject.getJSONArray(SUBJECT_FUNCTION_PUBLIC);

			// Itera sull'array "subjectFunctionPublic"
			for (int i = 0; i < subjectFunctionPublic.length(); i++) {
				JSONObject subject = subjectFunctionPublic.getJSONObject(i);

				getSois(builder, urlArray, subject);
			}
		}
	}

	private static void getNotizieFunctionPolitical(JSONObject jsonObject, StringBuilder builder, List<String> urlArray)
			throws JSONException {
		JSONArray subjectFunctionPolitical = null;

		if (!jsonObject.isNull(SUBJECT_FUNCTION_POLITICAL)) {
			subjectFunctionPolitical = jsonObject.getJSONArray(SUBJECT_FUNCTION_POLITICAL);

			// Itera sull'array "subjectFunctionPolitical"
			for (int i = 0; i < subjectFunctionPolitical.length(); i++) {
				JSONObject subject = subjectFunctionPolitical.getJSONObject(i);

				getSois(builder, urlArray, subject);
			}
		}
	}

	private static void getNotizieSanction(JSONObject jsonObject, StringBuilder builder, List<String> urlArray)
			throws JSONException {
		JSONArray subjectSanction = null;

		if (!jsonObject.isNull(SUBJECT_SANCTION)) {
			subjectSanction = jsonObject.getJSONArray(SUBJECT_SANCTION);

			// Itera sull'array "subjectSanction"
			for (int i = 0; i < subjectSanction.length(); i++) {
				JSONObject subject = subjectSanction.getJSONObject(i);

				getSois(builder, urlArray, subject);
			}
		}
	}

	private static void getNotizieMedia(JSONObject jsonObject, StringBuilder builder, List<String> urlArray)
			throws JSONException {
		JSONArray subjectMedia = null;

		if (!jsonObject.isNull(SUBJECT_MEDIA)) {
			subjectMedia = jsonObject.getJSONArray(SUBJECT_MEDIA);

			// Itera sull'array "subjectMedia"
			for (int i = 0; i < subjectMedia.length(); i++) {
				JSONObject subject = subjectMedia.getJSONObject(i);

				getSois(builder, urlArray, subject);
			}
		}
	}

	private static void getNotizieDead(JSONObject jsonObject, StringBuilder builder, List<String> urlArray)
			throws JSONException {
		JSONArray subjectDead = null;

		if (!jsonObject.isNull(SUBJECT_DEAD)) {
			subjectDead = jsonObject.getJSONArray(SUBJECT_DEAD);

			// Itera sull'array "subjectDead"
			for (int i = 0; i < subjectDead.length(); i++) {
				JSONObject subject = subjectDead.getJSONObject(i);

				getSois(builder, urlArray, subject);
			}
		}
	}

	private static void getNotizieRelationRelative(JSONObject jsonObject, StringBuilder builder, List<String> urlArray)
			throws JSONException {
		JSONArray subjectRelationRelative = null;

		if (!jsonObject.isNull(SUBJECT_RELATION_RELATIVE)) {
			subjectRelationRelative = jsonObject.getJSONArray(SUBJECT_RELATION_RELATIVE);

			// Itera sull'array "subjectRelationRelative"
			for (int i = 0; i < subjectRelationRelative.length(); i++) {
				JSONObject subject = subjectRelationRelative.getJSONObject(i);

				getSois(builder, urlArray, subject);
			}
		}
	}

	private static void getSois(StringBuilder builder, List<String> urlArray, JSONObject subject) {
		JSONArray soisList = null;
		if (!subject.isNull(SOI_BEAN)) {
			soisList = subject.getJSONArray(SOI_BEAN);

			// Itera sull'array "sois"
			for (int y = 0; y < soisList.length(); y++) {
				JSONObject sois = soisList.getJSONObject(y);

				String uri = (!sois.isNull(TAG_URI) ? sois.getString(TAG_URI) : "");
				if (StringUtils.hasLength(uri) && !urlArray.contains(uri)) {
					urlArray.add(uri);

					// Aggiungi il contenuto "content"
					builder.append(TR_OPEN);
					builder.append(TD_OPEN + (!sois.isNull(TAG_SCOPE) ? sois.getString(TAG_SCOPE) : "") + TD_CLOSE);

					if (!sois.isNull(SOI_GROUP)) {
						builder.append(TD_OPEN + (!sois.getJSONObject(SOI_GROUP).isNull("info")
								? sois.getJSONObject(SOI_GROUP).getString("info")
								: "") + TD_CLOSE);
					} else {
						builder.append(TD_OPEN + TD_CLOSE);
					}

					builder.append(TD_OPEN + (!sois.isNull(TAG_DESCRIPTION) ? sois.getString(TAG_DESCRIPTION) : "")
							+ TD_CLOSE);

					if (!sois.isNull(SOI_GROUP)) {
						builder.append(TD_OPEN + (!sois.getJSONObject(SOI_GROUP).isNull(TAG_ISSUER)
								? sois.getJSONObject(SOI_GROUP).getString(TAG_ISSUER)
								: "") + TD_CLOSE);
					} else {
						builder.append(TD_OPEN + TD_CLOSE);
					}
					builder.append(TD_OPEN_CENTER + "<a href=" + (!sois.isNull(TAG_URI) ? sois.getString(TAG_URI) : "")
							+ "\" target=\"notizia\" style=\"text-decoration: none; color: inherit; text-align: center;\"><i class=\"fas fa-link\" style=\"font-size: 10px;\"></i></a>"
							+ TD_CLOSE);
					builder.append(TR_CLOSE);
				}
			}
		}
	}
}
