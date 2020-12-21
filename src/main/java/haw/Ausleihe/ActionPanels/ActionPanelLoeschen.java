package haw.Ausleihe.ActionPanels;

import haw.Ausleihe.GraueListe;
import haw.Ausleihe.SchwarzeListe;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import dao.SanktionsListeDao;
import enums.SanktionenEnum;
import Database.Entities.SanktionsListe;
import Database.Entities.User;
import Models.SanktionsListeModel;

public class ActionPanelLoeschen extends Panel
{
	private static final long serialVersionUID = 1L;

	/**
     * @param id
     *            component id
     * @param sanktionsListeModel
     *            model for contact
     */
    public ActionPanelLoeschen(String id, IModel<SanktionsListeModel> sanktionsListeModel)
    {
        super(id, sanktionsListeModel);
        add(new Link<Object>(id)
        {
			private static final long serialVersionUID = 1L;

			@Override
            public void onClick()
            {
            	SanktionsListe sanktionsListe = (SanktionsListe) ActionPanelLoeschen.this.getDefaultModelObject();
            	SanktionsListeDao sanktionsListeDao = new SanktionsListeDao();
            	sanktionsListeDao.delete(sanktionsListe);
            	
            	if(sanktionsListe.getStatus().equals(SanktionenEnum.Graue)){
            		this.setResponsePage(GraueListe.class);
            	}else
            		this.setResponsePage(SchwarzeListe.class);
            }
        });
    }
}

