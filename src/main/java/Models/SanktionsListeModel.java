package Models;

import org.apache.wicket.model.IModel;

import Database.Entities.SanktionsListe;

public class SanktionsListeModel implements IModel{
	private static final long serialVersionUID = 1L;

	private IModel<SanktionsListe> sanktionsListeModel;
	
	private SanktionsListeModelType type;
	 
	  public enum SanktionsListeModelType
	  {
	    SANKTIONSLISTE,USER_ID,USER_KENNUNG,USER_VORNAME,USER_NACHNAME;
	  };
	
	public SanktionsListeModel(IModel<SanktionsListe> sanktionsListe, SanktionsListeModelType type){
		this.sanktionsListeModel = sanktionsListe;
		this.type = type;
	}
	
	@Override
	public void detach() {
		sanktionsListeModel.detach();
	}

	@Override
	public Object getObject() {
		SanktionsListe sanktionsListe = (SanktionsListe) sanktionsListeModel.getObject();
		
		switch(type){
		case SANKTIONSLISTE:
			return sanktionsListe;
		case USER_ID:
			return sanktionsListe.getUserID();
		case USER_KENNUNG:
			return sanktionsListe.getUserID().getKennung();
		case USER_VORNAME:
			return sanktionsListe.getUserID().getFirstName();
		case USER_NACHNAME:
			return sanktionsListe.getUserID().getLastName();
		}
		throw new UnsupportedOperationException("invalid SanktionsListeModelType = "
                + type.name());	
	}

	@Override
	public void setObject(Object object) {
		// TODO Auto-generated method stub
		
	}

}
