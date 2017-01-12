package model.logic;
/**
 * @author kobybs
 * @since 2-1-17
 */


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import logic.Scheduler;
import model.course.Course;
import model.loader.CourseLoader;
import model.loader.XmlCourseLoader;
import model.schedule.Timetable;

@SuppressWarnings("static-method")
public class SchedulerIteratorTest {

CourseLoader cr;
	
	
	@After
	public void after(){
		System.out.println("***");
	}
	
	
	@Test
	public void test_a() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest.XML");
		
		List<Course> courses = new ArrayList<>(cr.loadAllCourses().values());
		System.out.println(courses);
		
		
		List<Timetable> tl = Scheduler.getTimetablesList(courses);
		for(Timetable ¢ : tl)
			System.out.println("rank: " + ¢.getRankOfDaysoff());
		for (@SuppressWarnings("deprecation")
		Iterator<Timetable> ¢ = Scheduler.sortedBy(tl, false, false); ¢.hasNext();)
			System.out.println(¢.next().getRankOfDaysoff());
		

		//System.out.println(tl);
		/*Timetable t = Scheduler.getTimetablesList(courses).get(0);
		
		assert t.getLessonGroups().get(0).getLessons().get(0).getStartTime().equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 00)));
		assert t.getLessonGroups().get(1).getLessons().get(0).getStartTime().equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(13, 00)));
		assert t.getLessonGroups().get(2).getLessons().get(0).getStartTime().equals(new WeekTime(DayOfWeek.TUESDAY, LocalTime.of(11, 00)));
		assert t.getLessonGroups().get(3).getLessons().get(0).getStartTime().equals(new WeekTime(DayOfWeek.WEDNESDAY, LocalTime.of(14, 00)));
		*/
	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_b() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest8.XML");
		
		List<Course> courses = new ArrayList<>(cr.loadAllCourses().values());
		System.out.println(courses);
		
		
		for (Iterator<Timetable> it = Scheduler.sortedBy(Scheduler.getTimetablesList(courses), true, false); it
				.hasNext();) {
			Timetable currentTable = it.next();
			System.out.println("days of rank: " + currentTable.getRankOfDaysoff());
			System.out.println("blank space rank: " + currentTable.getRankOfBlankSpace());
			System.out.println("time table: " + currentTable);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_c() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest8.XML");
		
		List<Course> courses = new ArrayList<>(cr.loadAllCourses().values());
		System.out.println(courses);
		
		List<Timetable> tablesList = Scheduler.getTimetablesList(courses);
		Iterator<Timetable> it = Scheduler.sortedBy(tablesList, true, false);
		assert it.next().getRankOfDaysoff() == 4;
		assert it.next().getRankOfDaysoff() == 3;
		
		it = Scheduler.sortedBy(tablesList, false, true);
		assert it.next().getRankOfBlankSpace() == 2.0;
		assert it.next().getRankOfBlankSpace() == 1.75;
		
		
	}
	
	
	

}
