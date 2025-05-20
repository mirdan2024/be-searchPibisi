package it.search.pibisi.utils;

import org.springframework.util.StringUtils;

import it.common.pibisi.bean.MatchBean;

public class Category {

	public static void categoryPep(Boolean isPep, Boolean wasPep, String wasPepDate, MatchBean matchBean,
			StringBuilder category) {
		if (Boolean.TRUE.equals(isPep)) {
			matchBean.setPep(isPep);
			if (!StringUtils.hasLength(category))
				category.append("PEP");
			else
				category.append(", PEP");
		} else if (Boolean.TRUE.equals(wasPep)) {
			matchBean.setWasPep(wasPep);
			if (!StringUtils.hasLength(category))
				category.append("EX_PEP");
			else
				category.append(", EX_PEP");
			if (StringUtils.hasLength(wasPepDate)) {
				matchBean.setWasPepDate(wasPepDate);
				category.append(" (" + wasPepDate + ")");
			}
		}
	}

	public static void categorySanction(Boolean isSanctioned, Boolean wasSanctioned, String wasSanctionedDate,
			MatchBean matchBean, StringBuilder category) {
		if (Boolean.TRUE.equals(isSanctioned)) {
			matchBean.setSanctioned(isSanctioned);
			if (!StringUtils.hasLength(category))
				category.append("SANCTION");
			else
				category.append(", SANCTION");
		} else if (Boolean.TRUE.equals(wasSanctioned)) {
			matchBean.setWasSanctioned(wasSanctioned);
			if (!StringUtils.hasLength(category))
				category.append("EX_SANCTION");
			else
				category.append(", EX_SANCTION");
			if (StringUtils.hasLength(wasSanctionedDate)) {
				matchBean.setWasSanctionedDate(wasSanctionedDate);
				category.append(" (" + wasSanctionedDate + ")");
			}
		}
	}

	public static void categoryTerrorist(Boolean isTerrorist, MatchBean matchBean, StringBuilder category) {
		if (Boolean.TRUE.equals(isTerrorist)) {
			matchBean.setTerrorist(isTerrorist);
			if (!StringUtils.hasLength(category))
				category.append("TERRORIST");
			else
				category.append(", TERRORIST");
		}
	}

	public static void categoryHasMedia(Boolean hasMedia, MatchBean matchBean, StringBuilder category) {
		if (Boolean.TRUE.equals(hasMedia)) {
			matchBean.setHasMedia(hasMedia);
			if (!StringUtils.hasLength(category))
				category.append("MEDIA");
			else
				category.append(", MEDIA");
		}
	}

	public static void categoryHasAdverseInfo(Boolean hasAdverseInfo, MatchBean matchBean, StringBuilder category) {
		if (Boolean.TRUE.equals(hasAdverseInfo)) {
			matchBean.setHasAdverseInfo(hasAdverseInfo);
			if (!StringUtils.hasLength(category))
				category.append("ADVERSE INFO");
			else
				category.append(", ADVERSE INFO");
		}
	}

	public static String readHighRisk(Boolean isHighRisk, MatchBean matchBean) {
		String highRisk = "Low Risk";
		if (Boolean.TRUE.equals(isHighRisk)) {
			matchBean.setHighRisk(isHighRisk);
			highRisk = "High Risk";
		}
		return highRisk;
	}

}
