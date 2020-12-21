package haw.Ausleihe.ActionPanels;

import haw.Ausleihe.BuchungHistorie;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ActionPanelHistorie extends Panel{
	private static final long serialVersionUID = 1L;

	public ActionPanelHistorie(String id, final boolean isUser, final long entityId) {
		super(id);
		
		add(new Link<Object>(id){
			private static final long serialVersionUID = 1L;

			@Override
            public void onClick(){
            	PageParameters pageParameters = new PageParameters();
            	if(isUser){
                	pageParameters.add("userID", entityId);
                	pageParameters.add("artikelID", 0);
            	}else{
            		pageParameters.add("artikelID", entityId);
                	pageParameters.add("userID", 0);
            	}     	
            	this.setResponsePage(BuchungHistorie.class, pageParameters);
            }
        });
	}
}
