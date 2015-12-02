package tr.org.unicase.kernel.web.controller;

public interface Observable {
	void addListener(Listener listener);

	void removeListener(Listener listener);

	void updateListeners();
}
