package haw.Ausleihe.ActionPanels;

import haw.Ausleihe.SanktionslisteNeu;
import haw.Ausleihe.GraueListe;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import Database.Entities.SanktionsListe;
import Database.Entities.User;
import Models.SanktionsListeModel;
import dao.SanktionsListeDao;
import enums.SanktionenEnum;

public class ActionPanelToGrey extends Panel{
	private static final long serialVersionUID = 1L;

	public ActionPanelToGrey(String id, IModel<SanktionsListeModel> sanktionsListeModel) {
		super(id, sanktionsListeModel);
		add(new Link<Object>(id){
			private static final long serialVersionUID = 1L;

			@Override
            public void onClick(){
            	User user = (User) ActionPanelToGrey.this.getDefaultModelObject();
            	System.out.println(user.getKennung());
            	
            	PageParameters pageParameters = new PageParameters();
            	pageParameters.add("typ", "grey");
            	pageParameters.add("userID", user.getId());
            	
//            	SanktionsListe liste = new SanktionsListe();
//        		liste.setStatus(SanktionenEnum.Graue);
//        		liste.setUserID(user);
//        		
//        		SanktionsListeDao sanktionsListeDao = new SanktionsListeDao();
//        		sanktionsListeDao.create(liste);
      	
            	this.setResponsePage(SanktionslisteNeu.class, pageParameters);
            }
        });
	}
}

