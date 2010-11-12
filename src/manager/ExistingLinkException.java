package manager;

import manager.Manager.Link;

@SuppressWarnings("serial")
public class ExistingLinkException extends RuntimeException {

	public ExistingLinkException() {
		super("this link already exists");
	}
	public ExistingLinkException(Link link) {
		super("the link "+link+" already exists");
	}
	
	
}
