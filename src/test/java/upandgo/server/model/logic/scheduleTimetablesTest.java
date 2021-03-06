package upandgo.server.model.logic;
/**
 * @author kobybs
 * @since 2-1-17
 */

import upandgo.shared.entities.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import upandgo.shared.model.scedule.Scheduler;
import upandgo.server.model.loader.CourseLoader;
import upandgo.server.model.loader.XmlCourseLoader;
import upandgo.shared.entities.Day;
import upandgo.shared.entities.WeekTime;
import upandgo.shared.entities.constraint.TimeConstraint;
import upandgo.shared.entities.course.Course;
import upandgo.shared.model.scedule.Schedule;
import upandgo.shared.model.scedule.Timetable;

@SuppressWarnings("deprecation")
public class scheduleTimetablesTest {

	CourseLoader cr;

	@After
	public void after() {
		//Do nothing
	}

	@Test
	public void test_a() {
		cr = new XmlCourseLoader("schedulerTest.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		
		final Schedule s = Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		
		assert s.getLessonGroups().get(0).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("10:00")));
		assert s.getLessonGroups().get(1).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("13:00")));
		assert s.getLessonGroups().get(2).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.TUESDAY, LocalTime.parse("11:00")));
		assert s.getLessonGroups().get(3).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.WEDNESDAY, LocalTime.parse("14:00")));

	}

	@Test
	public void test_scheduleTimetable1() {
		cr = new XmlCourseLoader("schedulerTest.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		
		final List<Timetable> tl = Scheduler.getTimetablesList(courses, null);
		
		assert tl.size() == 4;
		
	}

}
