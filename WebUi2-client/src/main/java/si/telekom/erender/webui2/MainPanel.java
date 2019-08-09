package si.telekom.erender.webui2;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainPanel extends Composite {
	public String loginName;

	private VerticalPanel menuPanel = new VerticalPanel();
	private HorizontalPanel topPanel = new HorizontalPanel();
	private AbsolutePanel mainPanel = new AbsolutePanel();
	private static MainPanel instance;
	
	
	public static AbsolutePanel getPanel()
	{
		return instance.mainPanel;
	}
	
	public MainPanel(String loginName) {
		if(instance == null)
			instance = this;
		
		SplitLayoutPanel splitLayoutPanel = new SplitLayoutPanel(5);
		splitLayoutPanel.setStyleName("cw-DockPanel");

		// Add text all around
		//mainPanel.setSize("400px", "100px");
		//topPanel.setSize("400px", "50px");
		//menuPanel.setSize("400px", "50px");
		
		String centerText = "centerLabel";
    for (int i = 0; i < 3; i++) {
      centerText += " " + centerText+"<br>";
    }
    Label centerLabel = new Label(centerText);		
		mainPanel.add(centerLabel,50,50);

		
    //DecoratorPanel absolutePanelWrapper = new DecoratorPanel();
    //absolutePanelWrapper.setWidget(absolutePanelWrapper);
    
		ScrollPanel centerScrollable = new ScrollPanel();
		//centerScrollable.addStyleName("");
		centerScrollable.setSize("800px","800px");
		centerScrollable.add(mainPanel);
		//DecoratorPanel decoratorPanel = new DecoratorPanel();		
		//decoratorPanel.add(mainPanel);
		topPanel.add(new HTML("DocumentumEditor web"));

		MenuPanel menuPanel = new MenuPanel(loginName);
		splitLayoutPanel.addNorth(topPanel, 50);
		splitLayoutPanel.addWest(menuPanel, 60);
		splitLayoutPanel.setWidgetMinSize(menuPanel, 200);
		splitLayoutPanel.setWidgetMinSize(topPanel, 100);
		splitLayoutPanel.add(centerScrollable);
		
	
		initWidget(splitLayoutPanel);
	}

}
