package upandgo.client.view;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import upandgo.client.Resources;

public class SelectedCourseCell extends AbstractCell<String> {
	
	public SelectedCourseCell() {
		Resources.INSTANCE.courseListCellStyle().ensureInjected();
	}
	@Override
	public void render(@SuppressWarnings("unused") Context c, String value, SafeHtmlBuilder sb) {
	    if (value == null)
			return;
	    String parts[] = value.split(" - ");
	    sb.appendHtmlConstant("<div class=\"course\"><div class=\"course-name\">" + parts[0] + "</div> -" + parts[1]
				+ "<button type=\"button\" class=\"btn btn-danger cell-button\" >"
				+ "<i class=\"fa fa-times\" aria-hidden=\"true\"></i>&nbsp;&nbsp;הסר</button></div>");
		
	}


}