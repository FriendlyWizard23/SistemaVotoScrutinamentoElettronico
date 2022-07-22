package progetto.models.Votazioni;

import java.util.ArrayList;
import java.util.List;

import progetto.models.Sessioni.SessioneVoto;
import progetto.utils.CANDPOSPair;
import progetto.utils.PARTPOSPair;

public class VotoOrdinaleCandidato extends VotoGeneric {
	List<CANDPOSPair> candidatiposizioni;

	public VotoOrdinaleCandidato(SessioneVoto sv, List<CANDPOSPair> candidatiposizioni) {
		super(sv);
		this.candidatiposizioni = new ArrayList<CANDPOSPair>(candidatiposizioni);
		;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTipo() {
		return "ordinalecandidato";
	}

	public List<CANDPOSPair> getCandidatiPosizioni() {
		return new ArrayList<CANDPOSPair>(candidatiposizioni);
	}
}
