package controller;

import java.util.Observable;

import model.Model;
import view.View;

public class CourseSelectionController extends Controller {

	public CourseSelectionController(Model model, View view) {
		super(model, view);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Observable o, Object arg) {
		// enforce logic of model
		// e.g.:
//		if(view.wasPicked())
//			model.pickCourse(view.getHighlightedCourseName());
//		else
//			model.dropCousre(view.getHighlightedCourseName());
	}
}
