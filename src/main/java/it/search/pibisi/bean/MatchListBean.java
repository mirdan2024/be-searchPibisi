package it.search.pibisi.bean;

import java.util.ArrayList;
import java.util.List;

public class MatchListBean {

	public List<MatchBean> elencoMatch;

	public List<MatchBean> getElencoMatch() {
		if (elencoMatch == null)
			elencoMatch = new ArrayList<>();

		return elencoMatch;
	}

	public void setElencoMatch(List<MatchBean> elencoMatch) {
		this.elencoMatch = elencoMatch;
	}

	public void addMatchBean(MatchBean mb) {
		getElencoMatch().add(mb);
	}

}
