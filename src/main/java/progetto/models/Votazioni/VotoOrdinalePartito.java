package progetto.models.Votazioni;

import java.util.ArrayList;
import java.util.List;

import progetto.models.Sessioni.SessioneVoto;
import progetto.utils.CANDPOSPair;
import progetto.utils.PARTPOSPair;

public class VotoOrdinalePartito extends VotoGeneric {
	List<PARTPOSPair> partitiposizioni;

	public VotoOrdinalePartito(SessioneVoto sv, List<PARTPOSPair> partitiposizioni) {
		super(sv);
		this.partitiposizioni = new ArrayList<PARTPOSPair>(partitiposizioni);
		;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTipo() {
		return "ordinalepartito";
	}

	public List<PARTPOSPair> getPartitiPosizioni() {
		return new ArrayList<PARTPOSPair>(partitiposizioni);
	}

}
