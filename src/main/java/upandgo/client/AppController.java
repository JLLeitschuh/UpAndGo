package upandgo.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;

import upandgo.client.event.GetCourseDetailsEvent;
import upandgo.client.event.GetCourseDetailsEventHandler;
import upandgo.client.event.SelectCourseEvent;
import upandgo.client.event.SelectCourseEventHandler;
import upandgo.client.event.UnselectCourseEvent;
import upandgo.client.event.UnselectCourseEventHandler;
import upandgo.client.event.buildScheduleEvent;
import upandgo.client.event.buildScheduleEventHandler;
import upandgo.client.event.clearScheduleEvent;
import upandgo.client.event.clearScheduleEventHandler;
import upandgo.client.event.nextScheduleEvent;
import upandgo.client.event.nextScheduleEventHandler;
import upandgo.client.event.prevScheduleEvent;
import upandgo.client.event.prevScheduleEventHandler;
import upandgo.client.event.saveScheduleEvent;
import upandgo.client.event.saveScheduleEventHandler;
import upandgo.client.presenter.CourseListPresenter;
import upandgo.client.presenter.Presenter;
import upandgo.client.presenter.SchedulerPresenter;
import upandgo.server.model.loader.CourseLoader;

/**
 * 
 * @author Nikita Dizhur and danabra
 * @since 22-04-17
 * 
 *        A class that creates views, presenters, history and bind them all
 *        together with event bus and rpc .
 * 
 */

class AppController implements Presenter {
	
	Panel panel;

	private EventBus eventBus;
	private CoursesServiceAsync rpcService;
	private LayoutPanel mainView;
	private CourseLoader loader;
	
	@Inject
	public AppController(CoursesServiceAsync rpcService, EventBus eventBus, CourseLoader loader) {
		initMainView();
		this.eventBus = eventBus;
		this.rpcService = rpcService;
		this.loader = loader;
		bind();
	}

	@Override
	public void bind() {
		eventBus.addHandler(buildScheduleEvent.TYPE, new buildScheduleEventHandler() {

			@Override
			public void onBuildSchedule(@SuppressWarnings("unused") buildScheduleEvent event) {
				Log.info("Build Schedule Event");
				
			}
		});

		eventBus.addHandler(clearScheduleEvent.TYPE, new clearScheduleEventHandler() {

			@Override
			public void onClearSchedule(@SuppressWarnings("unused") clearScheduleEvent event) {
				Log.info("Clear Schedule Event");
				
			}
		});

		eventBus.addHandler(GetCourseDetailsEvent.TYPE, new GetCourseDetailsEventHandler() {

			@Override
			public void onHighlightCourse(@SuppressWarnings("unused") GetCourseDetailsEvent event) {//need to be implemented with DI
				
			}
		});

		eventBus.addHandler(nextScheduleEvent.TYPE, new nextScheduleEventHandler() {

			@Override
			public void onNextSchedule(@SuppressWarnings("unused") nextScheduleEvent event) {
				Log.info("Next Schedule Event");
				
			}
		});
		
		eventBus.addHandler(prevScheduleEvent.TYPE, new prevScheduleEventHandler() {

			@Override
			public void onPrevSchedule(@SuppressWarnings("unused") prevScheduleEvent event) {
				Log.info("Previous Schedule Event");
				
			}
		});

		eventBus.addHandler(saveScheduleEvent.TYPE, new saveScheduleEventHandler() {

			@Override
			public void onSaveSchedule(@SuppressWarnings("unused") saveScheduleEvent event) {
				Log.info("Save Schedule Event");
				
			}
		});

		eventBus.addHandler(SelectCourseEvent.TYPE, new SelectCourseEventHandler() {

			@Override
			public void onSelectCourse(@SuppressWarnings("unused") SelectCourseEvent event) {
				Log.info("Course " + event.getId().getTitle() + " Selected");
				
			}
		});

		eventBus.addHandler(UnselectCourseEvent.TYPE, new UnselectCourseEventHandler() {

			@Override
			public void onUnselectCourse(@SuppressWarnings("unused") UnselectCourseEvent event) {
				Log.info("Course " + event.getId().getTitle() + " Unselected");
				
			}
		});
	}

	@Override
	public void unbind() {
		// TODO Auto-generated method stub

	}

	@Override
	public void go(LayoutPanel panel) {
		this.panel = panel;
		panel.add(mainView);

	}

	public void initMainView(){
		final Injector injector = Injector.INSTANCE;
		mainView = new LayoutPanel();
		NavBarView navBarView = new NavBarView();
		CourseListPresenter.Display courseSelectionView = injector.getCourseListPresenterDisplay();
		SchedulerPresenter.Display schedualerView = injector.getSchedulerPresenterDisplay();
		
		CourseListPresenter clPresenter = new CourseListPresenter(rpcService,eventBus,courseSelectionView);
		
		SchedulerPresenter sPresenter = new SchedulerPresenter(schedualerView, eventBus, rpcService, loader);
		
		mainView.add(navBarView);
		mainView.setWidgetLeftRight(navBarView, 0, Unit.EM, 0, Unit.EM);
		mainView.setWidgetTopHeight(navBarView, 0, Unit.EM, 4, Unit.EM);
		
		mainView.add(courseSelectionView.asWidget());
		mainView.setWidgetRightWidth(courseSelectionView.asWidget(), 1, Unit.EM, 20, Unit.PCT);
		mainView.setWidgetTopBottom(courseSelectionView.asWidget(), 4.5, Unit.EM, 1, Unit.EM);
		
		
		mainView.add(schedualerView.asWidget());
		mainView.setWidgetLeftWidth(schedualerView.asWidget(), 1, Unit.EM, 77, Unit.PCT);
		mainView.setWidgetTopBottom(schedualerView.asWidget(), 4.5, Unit.EM, 1, Unit.EM);
		
		Resources.INSTANCE.mainStyle().ensureInjected();
		
		clPresenter.go(mainView);
		sPresenter.go(mainView);
		
	}
}
