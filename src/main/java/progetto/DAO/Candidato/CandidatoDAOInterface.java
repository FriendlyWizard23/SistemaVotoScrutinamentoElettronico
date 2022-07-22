package progetto.DAO.Candidato;

import java.util.List;

import progetto.DAO.DAOInterface;
import progetto.models.Sessioni.*;

public interface CandidatoDAOInterface extends DAOInterface<Candidato> {
	public List<Candidato> getCandidati(Partito partito);

	public void save(Candidato candidato, ListaCandidati listacandidati);
}
