package upandgo.client.view;

/**
 * 
 * @author danabra
 * @since 8-06-17
 * 
 * this class represents a single cell in the not selected courses' table 
 * 
 */

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import upandgo.client.Resources;

public class NotSelectedCourseCell extends AbstractCell<String> {
	public NotSelectedCourseCell() {
		Resources.INSTANCE.courseListCellStyle().ensureInjected();
	}
	@Override
	public void render(@SuppressWarnings("unused") Context c, String value, SafeHtmlBuilder sb) {
	    if (value == null)
			return;
	   
	    String parts[] = value.split(" - ");
			sb.appendHtmlConstant("<div class=\"course\"><div class=\"course-name\">" + parts[0] + "</div> -" + parts[1]
					+ "<button type=\"button\" class=\"btn btn-success cell-button\">"
					+ "<i class=\"fa fa-check\" aria-hidden=\"true\"></i>&nbsp;&nbsp;בחר</button></div>");
		
	}


}