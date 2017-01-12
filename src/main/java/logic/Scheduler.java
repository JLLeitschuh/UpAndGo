package logic;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import model.constraint.Constraint;
import model.constraint.TimeConstraint;
import model.course.Course;
import model.course.LessonGroup;
import model.schedule.Schedule;
import model.schedule.Timetable;

/**
 * 
 * @author kobybs
 * @since 22-12-16
 */

@SuppressWarnings("boxing")
public class Scheduler {
	
	
	/**
	 * gets a list of courses and a list of constraints and return a possible 
	 * schedule of them which doesn't break the constraints.
	 * it works for TimeConstraints for now.
	 */
	public static List<Timetable> getTimetablesList(List<Course> lcourse){
		List<Timetable> result = new ArrayList<>();
		ArrayList< List<LessonGroup> > lessonsGroupArray = initMainArr(lcourse);
		
		ArrayList<Integer> indexes = initIndexes(lessonsGroupArray.size());
		ArrayList<Integer> max = initMax(lessonsGroupArray);
		
		for (int last = indexes.size() - 1, msb;;) {
			System.out.println(indexes);
			List<LessonGroup> lessons = getScheduleByIndexes(lessonsGroupArray, indexes);
			Schedule $ = new Schedule();
			
			boolean b = false;
			int lastAdded = 0;
			for(lastAdded = 0; lastAdded < lessons.size(); ++lastAdded){
				b = $.addLesson( lessons.get(lastAdded) );
				// if you can't add that lesson than all combination including him are not valid
				// therefore there is no use to check the rest of them - increase bit of found lesson
				//indexes.set(¢, indexes.get(¢)+1);
				// if found index was maxed, than find msb that wasn't maxed and max it
				//if (indexes.get(¢) > max.get(¢)) {
				if(!b)
					break;
				
			}
			if(b){
				System.out.println("^found");
				result.add($.getTimetable()); //return $;
			}
			
			if(!b){
				indexes.set(lastAdded, indexes.get(lastAdded)+1);
				if (indexes.get(lastAdded) > max.get(lastAdded)) {
					msb = lastAdded - 1;
					// find lowest index which is not yet maxed
					for (; msb >= 0; --msb)
						if (indexes.get(msb) < max.get(msb))
							break;
					// if every index is max than we made all combinations
					if (msb < 0)
						break;
					// increase msb and zero everything to its right
					indexes.set(msb, indexes.get(msb) + 1);
					for (int ¢ = msb + 1; ¢ <= last; ++¢)
						indexes.set(¢, 0);
				}
				continue;
			}
			
			// increase last index by 1;
			indexes.set(last, indexes.get(last) + 1);
			
			if (indexes.get(last) > max.get(last)) {
				msb = last - 1;
				// find lowest index which is not yet maxed
				for (; msb >= 0; --msb)
					if (indexes.get(msb) < max.get(msb))
						break;
				// if every index is max than we made all combinations
				if (msb < 0)
					break;
				// increase msb and zero everything to its right
				indexes.set(msb, indexes.get(msb) + 1);
				for (int ¢ = msb + 1; ¢ <= last; ++¢)
					indexes.set(¢, 0);
			}
		}
		return result;
	}
	
	
	
	/**
	 * gets a list of courses and a list of constraints and return a possible 
	 * schedule of them which doesn't break the constraints.
	 * it works for TimeConstraints for now.
	 */
	public static Schedule schedule(List<Course> lcourse, List<TimeConstraint> cs){
		ArrayList< List<LessonGroup> > lessonsGroupArray = initMainArr(lcourse);
		
		ArrayList<Integer> indexes = initIndexes(lessonsGroupArray.size());
		ArrayList<Integer> max = initMax(lessonsGroupArray);
		
		for (int last = indexes.size() - 1, msb;;) {
			System.out.println(indexes);
			List<LessonGroup> lessons = getScheduleByIndexes(lessonsGroupArray, indexes);
			Schedule $ = new Schedule();
			
			boolean b = false;
			int lastAdded = 0;
			for(lastAdded = 0; lastAdded < lessons.size(); ++lastAdded){
				b = $.addLesson( lessons.get(lastAdded) );
				// if you can't add that lesson than all combination including him are not valid
				// therefore there is no use to check the rest of them - increase bit of found lesson
				//indexes.set(¢, indexes.get(¢)+1);
				// if found index was maxed, than find msb that wasn't maxed and max it
				//if (indexes.get(¢) > max.get(¢)) {
				if(!b)
					break;
				
			}
			if(b)
				return $;
			
			if(!b){
				indexes.set(lastAdded, indexes.get(lastAdded)+1);
				if (indexes.get(lastAdded) > max.get(lastAdded)) {
					msb = lastAdded - 1;
					// find lowest index which is not yet maxed
					for (; msb >= 0; --msb)
						if (indexes.get(msb) < max.get(msb))
							break;
					// if every index is max than we made all combinations
					if (msb < 0)
						break;
					// increase msb and zero everything to its right
					indexes.set(msb, indexes.get(msb) + 1);
					for (int ¢ = msb + 1; ¢ <= last; ++¢)
						indexes.set(¢, 0);
				}
				continue;
			}
			
			// increase last index by 1;
			indexes.set(last, indexes.get(last) + 1);
			
			if (indexes.get(last) > max.get(last)) {
				msb = last - 1;
				// find lowest index which is not yet maxed
				for (; msb >= 0; --msb)
					if (indexes.get(msb) < max.get(msb))
						break;
				// if every index is max than we made all combinations
				if (msb < 0)
					break;
				// increase msb and zero everything to its right
				indexes.set(msb, indexes.get(msb) + 1);
				for (int ¢ = msb + 1; ¢ <= last; ++¢)
					indexes.set(¢, 0);
			}
		}
		return null;
	}
	
	
	
	
	
	
	/**
	 * @param c
	 * @param lconstraint
	 * @return false if course c cannot meet the given list of constraints
	 */
	public static boolean canMeetConstraints(Course c, List<Constraint> lconstraint){
		for(Constraint ¢ : lconstraint)
			if(!¢.canMeetConstraint(c))
				return false;
		return true;
	}
	
	/**
	 * @param c
	 * @param lconstraint
	 * @return false if list of courses cannot meet the given list of constraints
	 * 			this is a shallow check one by one, and doesnt mean all courses can be placed together.
	 */
	public static boolean canMeetConstraints(List<Course> lcourse, List<Constraint> lconstraint){
		for(Course ¢ : lcourse)
			if (!canMeetConstraints(¢, lconstraint))
				return false;
		return true;
	}
	
	private static ArrayList< List<LessonGroup> > initMainArr(List<Course> lcourse){
		ArrayList< List<LessonGroup> > $ = new ArrayList<>();
		for(Course ¢ : lcourse){
			$.add(¢.getLectures());
			$.add(¢.getTutorials());
		}
		return $;
	}
	
	private static ArrayList<Integer> initIndexes(int size){
		ArrayList<Integer> $ = new ArrayList<>();
		for(int ¢ = 0; ¢<size; ++¢)
			$.add(0);
		return $;
			
	}
	
	
	private static ArrayList<Integer> initMax(ArrayList< List<LessonGroup> > lessonsGroupArray){
		ArrayList<Integer> $ = new ArrayList<>();
		for(List<LessonGroup> ¢ : lessonsGroupArray )
			$.add(¢.size()-1);
		return $;
			
	}
	
	private static List<LessonGroup> getScheduleByIndexes(ArrayList< List<LessonGroup> > lessonsGroupArray, ArrayList<Integer> indexes){
		List<LessonGroup> $ = new ArrayList<>();
		for(int ¢ = 0; ¢ < indexes.size(); ++¢)
			$.add(lessonsGroupArray.get(¢).get(indexes.get(¢)));
		
		return $;
		
	}
	
	
	/**
	 * 
	 * @param orig
	 * @param byDaysoff
	 * @param byBlankSpace
	 * @returns an iterator of Timetable sorted by summerized rank of chosen paramaters
	 */
	public static Iterator<Timetable> sortedBy(List<Timetable> orig, boolean byDaysoff, boolean byBlankSpace, LocalTime byStartTime, LocalTime byEndTime){
		List<Timetable> res = new ArrayList<>(orig);
		Collections.sort(res, new Comparator<Timetable>() {
			@Override
			public int compare(Timetable t1, Timetable t2) {
				Double rank1 = 0.0;
				Double rank2 = 0.0;
				if(byDaysoff){
					rank1 += t1.getRankOfDaysoff();
					rank2 += t2.getRankOfDaysoff();
				}
				if(byBlankSpace){
					rank1 += t1.getRankOfBlankSpace();
					rank2 += t2.getRankOfBlankSpace();
				}
				if(byStartTime != null){
					rank1 += t1.getRankOfStartTime(byStartTime);
					rank2 += t2.getRankOfStartTime(byStartTime);
				}
				if(byEndTime != null){
					rank1 += t1.getRankOfEndTime(byEndTime);
					rank2 += t2.getRankOfEndTime(byEndTime);
				}
				
				return -rank1.compareTo(rank2);
			}
		});
		return res.iterator();
	}
	
	/**
	 * 
	 * @param orig
	 * @param byDaysoff
	 * @param byBlankSpace
	 * @returns an iterator of Timetable sorted by summerized rank of chosen paramaters
	 */
	@Deprecated
	public static Iterator<Timetable> sortedBy(List<Timetable> orig, boolean byDaysoff, boolean byBlankSpace){
		List<Timetable> res = new ArrayList<>(orig);
		Collections.sort(res, new Comparator<Timetable>() {
			@Override
			public int compare(Timetable t1, Timetable t2) {
				Double rank1 = 0.0;
				Double rank2 = 0.0;
				if(byDaysoff){
					rank1 += t1.getRankOfDaysoff();
					rank2 += t2.getRankOfDaysoff();
				}
				if(byBlankSpace){
					rank1 += t1.getRankOfBlankSpace();
					rank2 += t2.getRankOfBlankSpace();
				}
				
				return -rank1.compareTo(rank2);
			}
		});
		return res.iterator();
	}

}
