package Models;

import org.apache.wicket.model.IModel;

import Database.Entities.Artikel;

public class ArtikelModel implements IModel {
	private static final long serialVersionUID = 1L;
	
	private IModel<Artikel> artikelModel;
	
	private ArtikelModelType type;
	
		public enum ArtikelModelType {
			ARTIKEL,ARTIKEL_ID,ARTIKEL_ART;
		};
		
	public ArtikelModel(IModel<Artikel> warenkorbListe, ArtikelModelType type) {
		this.artikelModel = warenkorbListe;
		this.type = type;
	}

	@Override
	public void detach() {
		artikelModel.detach();
	}

	@Override
	public Object getObject() {
		Artikel warenkorbListe = (Artikel) artikelModel.getObject();
		
		switch(type){
		case ARTIKEL:
			return warenkorbListe;
		case ARTIKEL_ID:
			return warenkorbListe.getId();
		case ARTIKEL_ART:
			return warenkorbListe.getArt();
		}
		throw new UnsupportedOperationException("invalid WarenkorbListModelType = "
                + type.name());	
	}

	@Override
	public void setObject(Object object) {
		// TODO Auto-generated method stub
		
	}

}
