package haw.Ausleihe.ActionPanels;

import haw.Ausleihe.ReservierungBearbeiten;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import Database.Entities.Reservierung;

public class ActionPanelReservierungBearbeiten extends Panel{
	private static final long serialVersionUID = 1L;

	public ActionPanelReservierungBearbeiten(String id, IModel<Reservierung> reservierungModel, final boolean isBearbeiten) {
		super(id,reservierungModel);
		
		Link link = new Link<Object>(id){
			 private static final long serialVersionUID = 1L;
			 
			 @Override
			 public void onClick(){
				 Reservierung reservierung = (Reservierung) ActionPanelReservierungBearbeiten.this.getDefaultModelObject();
				 PageParameters pageParameters = new PageParameters();
				 pageParameters.add("reservierungID", reservierung.getId());
				 pageParameters.add("bearbeiten", isBearbeiten);
         	
			    this.setResponsePage(ReservierungBearbeiten.class, pageParameters);
			 }
		 };
		 String labelLinkText = "Details";
		 if(isBearbeiten){
			 labelLinkText = "Bearbeiten";
		 }
		 link.add(new Label("linktext", Model.of(labelLinkText)));
		 add(link);
	}

}
