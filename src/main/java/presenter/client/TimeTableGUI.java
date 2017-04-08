package presenter.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.LayoutPanel;

public class TimeTableGUI extends LayoutPanel { 
	static final int EMPTY_COL = 0;
	static final int HOURS_COL = 1;
	
	FlexTable t = new FlexTable();
	
	public TimeTableGUI(){
    	InitializePanel();
    }
    private void InitializePanel(){
    	t.setText(0, 0, "");
    	t.setText(0, 1, "שעה");
    	t.setText(0, 2, "ראשון");
    	t.setText(0, 3, "שני");
    	t.setText(0, 4, "שלישי");
    	t.setText(0, 5, "רביעי");
    	t.setText(0, 6, "חמישי");
    	//t.setText(0, 6, "x");
    	
    	/*for(int i = 1; i<24; i++){
    		t.setText(i, 6, "s");
    		//t.setText(2*i, 0, Integer.toString(i+8)+":00");
    		//t.getCellFormatter().addStyleName(2*i-1, 0, "headerRowStyle");
    		//t.getCellFormatter().addStyleName(2*i, 0, "headerRowStyle");
    		
    		//t.getFlexCellFormatter().setRowSpan(i, 0, 2);
    	}*/
    	
    	//t.setText(0, 5, "שישי");
    	
    	t.getRowFormatter().addStyleName(0, "headerRowStyle");
    	t.getCellFormatter().addStyleName(0, 0, "emptyCol");
    	t.getCellFormatter().addStyleName(0, HOURS_COL, "hourCol");
    	t.getColumnFormatter().addStyleName(HOURS_COL, "letTry");
    	for(int i = 2; i<7; i++){
    		t.getCellFormatter().addStyleName(0, i, "headerCellStyle");
    	}
    	
    	
    	for(int i = 1; i<12; i++){
    		t.setText(2*i-1, EMPTY_COL, "");
    		t.setText(2*i, EMPTY_COL, "");
    		t.getCellFormatter().addStyleName(2*i-1, EMPTY_COL, "emptyCol");
    		t.getCellFormatter().addStyleName(2*i, EMPTY_COL, "emptyCol");
    		//t.getFlexCellFormatter().setRowSpan(i, 0, 2);
    	}
    	
    	// OLD CODE:
    	/*for(int i = 1; i<12; i++){
    		t.setText(2*i-1, HOURS_COL, Integer.toString(i+7)+":30");
    		t.setText(2*i, HOURS_COL, Integer.toString(i+8)+":00");
    		t.getCellFormatter().addStyleName(2*i-1, HOURS_COL, "headerRowStyle");
    		t.getCellFormatter().addStyleName(2*i, HOURS_COL, "headerRowStyle");
    		//t.getFlexCellFormatter().setRowSpan(i, 0, 2);
    	}*/
    	
    	for(int i = 1; i<12; i++){
    		t.setText(2*i-1, HOURS_COL, Integer.toString(i+7)+":30");
    		t.getFlexCellFormatter().setRowSpan(2*i-1, HOURS_COL, 2);
    		//t.setText(2*i, HOURS_COL, Integer.toString(i+8)+":00");
    		t.getCellFormatter().addStyleName(2*i-1, HOURS_COL, "hoursCellStyle");
    		//t.getCellFormatter().addStyleName(2*i, HOURS_COL, "headerRowStyle");
    		//t.getFlexCellFormatter().setRowSpan(i, 0, 2);
    	}
    	
    	
    	
    	for(int day = 2; day < 7; day++){
	    	for(int i = 1; i<12; i++){
	    		t.setText(2*i-1, day, "");
	    		t.getFlexCellFormatter().setRowSpan(2*i-1, day, 2);
	    		String cssSign = (i % 2 == 0) ? "insideEmptyCellEven":"insideEmptyCellOdd";
	    		t.getCellFormatter().addStyleName(2*i-1, day, cssSign);
	    		
	    	}
    	}
    	
    	
    	
    	
    	
    	/*t.setText(0, 0, "upper-left corner");
	    t.setText(0, 1, "center corner");
	    t.setText(0, 2, "right corner");
	    //t.setText(0, 1, "upper-center");
	    t.setText(1, 1, "center-left");
	    t.setText(2, 2-1, "bottom-right corner");*/

	    // Let's put a button in the middle...
	    //t.setWidget(1, 0, new Button("Wide Button"));

	    // ...and set it's column span so that it takes up the whole row.
	    //t.getFlexCellFormatter().setRowSpan(1, 0, 2);
	    /*t.getRowFormatter().addStyleName(0, "headerRowStyle");
	    t.getRowFormatter().addStyleName(1, "headerRowStyle");
	    t.getRowFormatter().addStyleName(2, "headerRowStyle");*/
	    /*t.getCellFormatter().addStyleName(0, 0, "watchListNumericColumn");
	    t.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
	    t.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");*/
	    t.addStyleName("timetableFlexStyle");
	    
	    this.add(t);
	    /*this.setWidgetTopBottom(cc, 0, Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(ccl, 1.5,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(sc, 30,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(scl, 31.5,  Unit.EM, 0, Unit.EM);*/
    }
}