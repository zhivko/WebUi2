package si.telekom.erender.webui2;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedStackPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MenuPanel extends Composite {

	/**
	 * Specifies the images that will be bundled for this example.
	 *
	 * We will override the leaf image used in the tree. Instead of using a blank
	 * 16x16 image, we will use a blank 1x1 image so it does not take up any
	 * space. Each TreeItem will use its own custom image.
	 */
	Images images;

	public MenuPanel(String loginName) {
		Images images = (Images) GWT.create(Images.class);

		// Create a new stack panel
		DecoratedStackPanel stackPanel = new DecoratedStackPanel();
		stackPanel.setWidth("200px");

		// Add the Mail folders
		String explorerHeader = getHeaderString("Explorer", images.explorer());
		
		stackPanel.add(createExplorerItems(images), explorerHeader, true);

		// Add a list of filters
		String adminHeader = getHeaderString("Admin", images.filtersgroup());
		stackPanel.add(createAdminItems(), adminHeader, true);

		// Add a list of contacts
		String contactsHeader = getHeaderString("Contacts", images.contactsgroup());
		stackPanel.add(createContactsItem(images), contactsHeader, true);

		// Return the stack panel
		stackPanel.ensureDebugId("cwStackPanel");

		initWidget(stackPanel);
	}

	/**
	 * Get a string representation of the header that includes an image and some
	 * text.
	 *
	 * @param text
	 *          the header text
	 * @param image
	 *          the {@link ImageResource} to add next to the header
	 * @return the header as a string
	 */
	private String getHeaderString(String text, ImageResource image) {
		// Add the image and text to a horizontal panel
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setSpacing(0);
		hPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hPanel.add(new Image(image));
		HTML headerText = new HTML(text);
		headerText.setStyleName("cw-StackPanelHeader");
		hPanel.add(headerText);

		// Return the HTML string for the panel
		return hPanel.getElement().getString();
	}

	/**
	 * Create the list of Contacts.
	 *
	 * @param images
	 *          the {@link Images} used in the Contacts
	 * @return the list of contacts
	 */
	private VerticalPanel createContactsItem(Images images) {
		// Create a popup to show the contact info when a contact is clicked
		HorizontalPanel contactPopupContainer = new HorizontalPanel();
		contactPopupContainer.setSpacing(5);
		contactPopupContainer.add(new Image(images.defaultContact()));
		final HTML contactInfo = new HTML();
		contactPopupContainer.add(contactInfo);
		final PopupPanel contactPopup = new PopupPanel(true, false);
		contactPopup.setWidget(contactPopupContainer);

		// Create the list of contacts
		VerticalPanel contactsPanel = new VerticalPanel();
		contactsPanel.setSpacing(4);
		String[] contactNames = { "Klemen", "Davor", "Raif" };
		String[] contactEmails = { "Klemen@telekom.si", "Davor@telekom.si", "Raif@telekom.si" };
		for (int i = 0; i < contactNames.length; i++) {
			final String contactName = contactNames[i];
			final String contactEmail = contactEmails[i];
			final Anchor contactLink = new Anchor(contactName);
			contactsPanel.add(contactLink);

			// Open the contact info popup when the user clicks a contact
			contactLink.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					// Set the info about the contact
					contactInfo.setHTML(contactName + "<br><i>" + contactEmail + "</i>");

					// Show the popup of contact info
					int left = contactLink.getAbsoluteLeft() + 14;
					int top = contactLink.getAbsoluteTop() + 14;
					contactPopup.setPopupPosition(left, top);
					contactPopup.show();
				}
			});
		}
		return contactsPanel;
	}

	/**
	 * Create the list of filters for the Filters item.
	 *
	 * @return the list of filters
	 */
	private VerticalPanel createAdminItems() {
		VerticalPanel filtersPanel = new VerticalPanel();
		String[] filters = {"Novi profil", "Kopiraj profil", "Brisi profil"};
		filtersPanel.setSpacing(4);

		filtersPanel.add(new NewProfile());
		
		
		return filtersPanel;
	}

	/**
	 * Create the {@link Tree} of Mail options.
	 *
	 * @param images
	 *          the {@link Images} used in the Mail options
	 * @return the {@link Tree} of mail options
	 */
	private Tree createExplorerItems(Images images) {
		Tree explorerPanel = new Tree(images);
		TreeItem newDocumentRoot = explorerPanel.addTextItem("New document");
		String[] profiles = { "Profil 1", "Profil 2", "Centreks pogodba" };
		addItem(newDocumentRoot, images.inbox(), profiles[0]);
		addItem(newDocumentRoot, images.drafts(), profiles[1]);
		addItem(newDocumentRoot, images.templates(), profiles[2]);
		addItem(newDocumentRoot, images.sent(), profiles[3]);
		addItem(newDocumentRoot, images.trash(), profiles[4]);
		newDocumentRoot.setState(true);
		return explorerPanel;
	}

	private void addItem(TreeItem root, ImageResource image, String label) {
		SafeHtmlBuilder itemHtml = new SafeHtmlBuilder();
		itemHtml.append(AbstractImagePrototype.create(image).getSafeHtml());
		itemHtml.appendHtmlConstant(" ").appendEscaped(label);
		root.addItem(itemHtml.toSafeHtml());
	}

}
