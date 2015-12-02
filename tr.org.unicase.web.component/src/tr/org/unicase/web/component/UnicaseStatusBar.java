
package tr.org.unicase.web.component;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import tr.org.unicase.web.component.internals.config.Configuration;

import com.vaadin.data.util.BeanItem;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextArea;

public class UnicaseStatusBar extends HorizontalLayout implements ColumnGenerator {

	private Queue<String> eventList = new ArrayBlockingQueue<String>(Configuration.Statusbar.LIMIT);
	private TextArea logInfo = new TextArea();

	public UnicaseStatusBar() {
		super();
		setWidth(Configuration.Statusbar.LIMIT, Unit.PERCENTAGE);
		init();
		addStyleName(Configuration.Statusbar.STYLE);
	}

	private void init() {
		logInfo.setSizeFull();
		logInfo.setWidth(Configuration.Statusbar.LIMIT, Unit.PERCENTAGE);
		logInfo.setWordwrap(true);
		logInfo.setMaxLength(Configuration.Statusbar.LIMIT);
		logInfo.setImmediate(true);
		logInfo.setEnabled(false);
		logInfo.addStyleName("small");
		if (logInfo.getValue() != null)
			logInfo.setRows(7);
		else
			logInfo.setRows(2);

		addComponent(logInfo);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object generateCell(Table source, Object itemId, Object columnId) {
		final String event = ((BeanItem<String>) source.getContainerDataSource().getItem(itemId)).getBean();
		Label label = new Label();
		label.setContentMode(ContentMode.HTML);
		label.setValue(event);
		return label;
	}

	public void addEvent(String event) {
		if (eventList.size() >= Configuration.Statusbar.THRESHOLD) {
			eventList.clear();
		} else {
			eventList.add(event);
			String oldLogs = logInfo.getValue();
			for (String events : eventList) {
				logInfo.setValue(eventList.size() + " " + events.toString() + "\n" + oldLogs);
			}
		}
	}

}

