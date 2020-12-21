package haw.Ausleihe;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;

public class Impressum extends WebPage {
	private static final long serialVersionUID = 1L;

	public Impressum(final PageParameters parameters) {
		super(parameters);

		add(new HeaderPanel("headerPanel"));
		add(new FooterPanel("footerPanel"));
		add(new NavigationPanel("navigationPanel"));

    }
}
