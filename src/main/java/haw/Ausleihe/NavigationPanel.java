package haw.Ausleihe;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

public class NavigationPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NavigationPanel (String id) { 
		super(id);
		
		Form form = new Form("ZurAusleihe");
		
		Button zurLeihe = new Button("zurLeihe") {
			public void onSubmit(){
				setResponsePage(HomePage.class);
			}
		};
		zurLeihe.setDefaultFormProcessing(false);
		form.add(zurLeihe);
		
		add(form);
	}
}
