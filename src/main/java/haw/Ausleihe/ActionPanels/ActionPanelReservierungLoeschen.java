package haw.Ausleihe.ActionPanels;

import haw.Ausleihe.ReservierungHP;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import Database.Entities.Reservierung;
import dao.ReservierungDao;
import enums.ReservierungsEnum;

public class ActionPanelReservierungLoeschen extends Panel{
	private static final long serialVersionUID = 1L;

    public ActionPanelReservierungLoeschen(String id, IModel<Reservierung> reservierungModel)
    {
        super(id, reservierungModel);
        add(new Link<Object>(id)
        {
			private static final long serialVersionUID = 1L;

			@Override
            public void onClick()
            {
				Reservierung reservierung = (Reservierung) ActionPanelReservierungLoeschen.this.getDefaultModelObject();
            	ReservierungDao reservierungDao = new ReservierungDao();
            	reservierung.setStatus(ReservierungsEnum.Abgelehnt);
            	reservierungDao.edit(reservierung);
            	
            	this.setResponsePage(ReservierungHP.class);
            }
        });
    }
}
