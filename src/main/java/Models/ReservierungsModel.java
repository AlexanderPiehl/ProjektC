package Models;

import org.apache.wicket.model.IModel;

import Database.Entities.Reservierung;
import Models.SanktionsListeModel.SanktionsListeModelType;

@SuppressWarnings("rawtypes")
public class ReservierungsModel  implements IModel{
	private static final long serialVersionUID = 1L;
	
	private IModel<Reservierung> reservierungsModel;
	
	private ReservierungModelType type;
	 
	  public enum ReservierungModelType
	  {
	    RESERVIERUNG
	  };
	
	  public ReservierungsModel(IModel<Reservierung> reservierungsModel, ReservierungModelType type){
		  this.reservierungsModel = reservierungsModel;
		  this.type = type;
	  }

	@Override
	public void detach() {
		reservierungsModel.detach();
	}

	@Override
	public Object getObject() {
		Reservierung reservierung = reservierungsModel.getObject();
		switch(type){
		case RESERVIERUNG:
			return reservierung;
		}
		return null;
	}

	@Override
	public void setObject(Object object) {
		// TODO Auto-generated method stub
		
	}

}
