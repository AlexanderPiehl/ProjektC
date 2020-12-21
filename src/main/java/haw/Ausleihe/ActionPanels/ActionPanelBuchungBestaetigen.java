package haw.Ausleihe.ActionPanels;

import haw.Ausleihe.Buchungen;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import Database.Entities.Reservierung;
import dao.ReservierungDao;
import enums.ReservierungsEnum;

public class ActionPanelBuchungBestaetigen extends Panel{
	private static final long serialVersionUID = 1L;

	public ActionPanelBuchungBestaetigen(String id, IModel<Reservierung> reservierungModel) {
		super(id, reservierungModel);
        add(new Link<Object>(id)
        {
			private static final long serialVersionUID = 1L;

			@Override
            public void onClick()
            {
				Reservierung reservierung = (Reservierung) ActionPanelBuchungBestaetigen.this.getDefaultModelObject();
            	ReservierungDao reservierungDao = new ReservierungDao();
            	reservierung.setStatus(ReservierungsEnum.Rueckgabe);
            	reservierungDao.edit(reservierung);
            	
            	this.setResponsePage(Buchungen.class);
            }
        });

	}
}
