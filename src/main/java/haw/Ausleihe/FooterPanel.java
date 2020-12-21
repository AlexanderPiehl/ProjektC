package haw.Ausleihe;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

public class FooterPanel  extends Panel {

	public FooterPanel(String id) { 
		super(id);
		
		Form form = new Form("formFooter");
		
		Button Impressum = new Button("Impressum") {
			private static final long serialVersionUID = 1L;

				public void onSubmit(){
					setResponsePage(Impressum.class);
				}

		};
		form.add(Impressum);
		add(form);
	}

}
