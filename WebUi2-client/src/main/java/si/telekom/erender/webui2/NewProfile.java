package si.telekom.erender.webui2;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;

import si.telekom.erender.webui2.MainPanel;

public class NewProfile extends Composite implements ClickHandler {
	public NewProfile() {
		Button b = new Button();
		b.setText("New profile");
		b.addClickHandler(this);
		initWidget(b);
	}

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
		Button b = new Button();
		b.setWidth("100px");
		b.setHeight("30px");
		b.setText("Gumb");
		b.setStyleName("gwt-Button");
		MainPanel.getPanel().add(b, Random.nextInt(100), Random.nextInt(100));
	}

}
