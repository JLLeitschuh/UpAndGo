package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.common.collect.HashMultimap;

import model.course.Course;
import model.course.CourseId;
import model.course.LessonGroup;
import model.loader.CourseLoader;
import property.CourseProperty;

/**
 * Interface for storing data inside the program. The data typically should come
 * from loader.
 */
public class CourseModel implements Model {

	protected TreeMap<String, Course> coursesById;
	protected TreeMap<String, Course> coursesByName;
	protected List<Course> pickedCourseList;
	protected HashMultimap<String, PropertyChangeListener> listenersMap;
	protected CourseLoader loader;
	protected List<Faculty> facultyList;

	public CourseModel(CourseLoader loader) {
		this.pickedCourseList = new ArrayList<>();
		this.listenersMap = HashMultimap.create();
		this.loader = loader;
		this.coursesById = loader.loadAllCoursesById();
		this.coursesByName = loader.loadAllCoursesByName();
		this.facultyList = loader.loadFaculties();
		for(String id: this.loader.loadChosenCourseNames())
			this.pickedCourseList.add(this.coursesById.get(id));
	}

	public void pickCourse(String name) {
		if (name == null)
			throw new NullPointerException();
		Course pickedCourse = this.getCourseById(name);
		if (this.pickedCourseList.contains(pickedCourse))
			return;

		// save picking in DB
		TreeSet<CourseId> pickedList = new TreeSet<>();
		List<String> pickedIds = new ArrayList<>();
		this.pickedCourseList.add(pickedCourse);
		this.pickedCourseList.forEach((course) -> {
			pickedList.add(new CourseId(course.getId(), course.getName()));
			pickedIds.add(course.getId());
		});

		// notify listeners
		this.listenersMap.get(CourseProperty.CHOSEN_LIST).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, CourseProperty.CHOSEN_LIST, null, new ArrayList<>(pickedList)))));
	}

	public void addCourse(String name) {
		if (name == null)
			throw new NullPointerException();
		List<String> prevCourseList = this.getCoursesNames();
		this.coursesById.put(name, loader.loadCourse(name));

		List<String> curCourseList = new ArrayList<>(prevCourseList);
		curCourseList.remove(name);

		// notify listeners
		this.listenersMap.get(CourseProperty.CHOSEN_LIST).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, CourseProperty.COURSE_LIST, prevCourseList, curCourseList))));
	}

	public List<String> getCoursesNames() {
		return new ArrayList<>(this.coursesById.keySet());
	}

	public void dropCourse(String name) {
		if (name == null)
			throw new NullPointerException();
		Course droppedCourse = this.getCourseById(name);
		if (!this.pickedCourseList.contains(droppedCourse))
			return;

		// save picking in DB
		TreeSet<CourseId> pickedList = new TreeSet<>();
		this.pickedCourseList.remove(droppedCourse);
		this.pickedCourseList.forEach((course) -> {
			pickedList.add(new CourseId(course.getId(), course.getName()));
		});

		// notify listeners
		this.listenersMap.get(CourseProperty.CHOSEN_LIST).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, CourseProperty.CHOSEN_LIST, null, new ArrayList<>(pickedList)))));
	}

	/*
	 * expose course to listeners for "course_list" property
	 */
	public void exposeCourse(String name) {
		if (name == null)
			throw new NullPointerException();
		this.listenersMap.get(CourseProperty.DETAILS).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, CourseProperty.DETAILS, null, this.getCourseById(name)))));
	}

	public Course getCourseByName(String name) {
		if (name == null)
			throw new NullPointerException();
		for(Entry<String, Course> ¢ : coursesById.entrySet())
			if (name.equals(¢.getValue().getName()))
				return ¢.getValue();
		return null;
	}
	
	public Course getCourseById(String name) {
		if (name == null)
			throw new NullPointerException();
		for(Entry<String, Course> ¢ : coursesById.entrySet())
			if (name.equals(¢.getValue().getId()))
				return ¢.getValue();
		return null;
	}

	public List<String> getChosenCourseNames() {
		List<String> $ = new ArrayList<>();
		this.pickedCourseList.forEach((course) -> {
			$.add(course.getId());
		});
		return $;
	}
	
	public List<LessonGroup> getChosenLessonGroups() {
		return new ArrayList<>();
	}

	public void loadChosenCourses() {
		// save picking in DB
		HashSet<CourseId> pickedList = new HashSet<>();
		this.pickedCourseList.forEach((course) -> {
			pickedList.add(new CourseId(course.getId(), course.getName()));
		});

		// notify listeners
		this.listenersMap.get(CourseProperty.CHOSEN_LIST).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, CourseProperty.CHOSEN_LIST, null, new ArrayList<>(pickedList)))));
	}
	
	public void saveChosenCourses(List<String> names) {
		this.loader.saveChosenCourseNames(names);
	}
	
	public void saveChosenLessonGroups(List<LessonGroup> ¢) {
		this.loader.saveChosenLessonGroups(¢);
	}

	/*
	 * load needed courses (by name / subname) from DB if empty, load all of
	 * them
	 */
	public void loadQuery(String query) {
		TreeSet<CourseId> matchingIds = new TreeSet<>();
		if(query.isEmpty())
			this.coursesById.forEach((key, course) -> {
				matchingIds.add(new CourseId(course.getId(), course.getName()));
			});	
		else {
			this.coursesById.forEach((key, course) -> {
				if (key.contains(query))
					matchingIds.add(new CourseId(course.getId(), course.getName()));
			});
			this.coursesByName.forEach((key, course) -> {
				if (key.toLowerCase().contains(query.toLowerCase()))
					matchingIds.add(new CourseId(course.getId(), course.getName()));
			});
		}

		this.listenersMap.get(CourseProperty.COURSE_LIST).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, CourseProperty.COURSE_LIST, null, new ArrayList<>(matchingIds)))));
	}
	
	/*
	 * load needed courses (by name / subname) from DB if empty, load all of
	 * them
	 */
	public void loadQueryByFaculty(String query, String faculty) {
		TreeSet<CourseId> matchingIds = new TreeSet<>();
		this.coursesById.forEach(query.isEmpty() ? (key, course) -> {
			if(course.getFaculty().equals(faculty))
				matchingIds.add(new CourseId(course.getId(), course.getName()));
		} : (key, course) -> {
			if (key.toLowerCase().contains(query.toLowerCase()) && course.getFaculty().equals(faculty))
				matchingIds.add(new CourseId(course.getId(), course.getName()));
		});
		this.listenersMap.get(CourseProperty.COURSE_LIST).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, CourseProperty.COURSE_LIST, null, new ArrayList<>(matchingIds)))));
	}
	
	/*
	 * load faculty names
	 */
	public void loadFacultyNames() {
		TreeSet<String> faculties = new TreeSet<>();
		this.facultyList.forEach((x) -> faculties.add(x.getName()));
		this.listenersMap.get(CourseProperty.FACULTY_LIST).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, CourseProperty.FACULTY_LIST, null, new ArrayList<>(faculties)))));
	}
	
	public void loadChosenCoursesDetails() {
		this.listenersMap.get(CourseProperty.CHOSEN_LIST_DETAILS).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, CourseProperty.CHOSEN_LIST_DETAILS, null, this.pickedCourseList))));
	}
	
	public void loadGilaionFrom(@SuppressWarnings("unused") String path) {
		// TODO: implement it
	}
	
	public void loadCatalogFrom(@SuppressWarnings("unused") String path) {
		// TODO: implement it
	}
	
	@Override
	public void addPropertyChangeListener(String property, PropertyChangeListener l) {
		if (property == null || l == null)
			throw new NullPointerException();
		this.listenersMap.put(property, l);
	}

	@Override
	public void removePropertyChangeListener(String property, PropertyChangeListener l) {
		if (property != null && l != null && this.listenersMap.containsKey(property))
			this.listenersMap.remove(property, l);

	}
}
