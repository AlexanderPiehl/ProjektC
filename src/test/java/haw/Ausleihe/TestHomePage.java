package haw.Ausleihe;

import Database.HibernateHelper;
import haw.Ausleihe.HomePage;
import haw.Ausleihe.WicketApplication;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage
{
	private WicketTester tester;

	@Before
	public void setUp()
	{
		HibernateHelper hibernateHelper = new HibernateHelper();
		hibernateHelper.initHibernate();
		tester = new WicketTester(new WicketApplication());
	}

	@Test
	public void homepageRendersSuccessfully()
	{
		//start and render the test page
		tester.startPage(HomePage.class);

		//assert rendered page class
		tester.assertRenderedPage(HomePage.class);
	}
}
