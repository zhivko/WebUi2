package si.telekom.erender.webui2;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Tree;

public interface Images extends Tree.Resources {
	@Source("/content/lists/contactsgroup.gif")
	ImageResource contactsgroup();

	@Source("/content/lists/defaultContact.jpg")
	ImageResource defaultContact();

	@Source("/content/lists/drafts.gif")
	ImageResource drafts();

	@Source("/content/lists/filtersgroup.gif")
	ImageResource filtersgroup();

	@Source("/content/lists/inbox.gif")
	ImageResource inbox();

	@Source("/content/lists/mailgroup.gif")
	ImageResource mailgroup();

	@Source("/content/lists/sent.gif")
	ImageResource sent();

	@Source("/content/lists/templates.gif")
	ImageResource templates();

	@Source("/content/lists/trash.gif")
	ImageResource trash();

	/**
	 * Use noimage.png, which is a blank 1x1 image.
	 */
	@Override
	@Source("noimage.png")
	ImageResource treeLeaf();

	@Source("/content/lists/mailgroup.gif")
	ImageResource mailHeader();

	@Source("/content/webui/explorer.png")
	ImageResource explorer();

}
