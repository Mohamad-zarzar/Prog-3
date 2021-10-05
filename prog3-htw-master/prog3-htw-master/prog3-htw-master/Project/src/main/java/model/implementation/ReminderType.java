package model.implementation;


import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import java.lang.reflect.*;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * This class is used to define the types of Reminders to be added to the
 * list. In case of extension, you just have to add the new Reminder type.
 *
 * @author Feras
 *
 */
@SuppressWarnings("unused")
public class ReminderType {

	
	//------------------Types-----------------------------
//	private static final String MEMENTOES = "All Reminders";
//	private static final String FINISHED = "Finished";
//	private static final String TODAY = "Today";
//	private static final String MISSED = "Missed";
	
	
	
	
	
	private static final List<String> list = new LinkedList<>();
	
	// Add Types to the List
	static {
		
		list.addAll(Arrays.asList("All Reminders","Today","Tomorrow","Missed","Finished"));
	}
	
	
	/**
	 * 
	 * @return 
	 * @return a list with all MementoTypes
	 */
	public static ObservableList<String> getReminderTypes() { 

		return FXCollections.observableList(list); 
	}
	
	
//	/**
//	 * Gets all defined attributes in the class to be added to the list view.
//	 */
//	private static void addTypesToView() {
//		Class<ReminderType> c = ReminderType.class;
//		Field[] fields = c.getDeclaredFields();
//		Map<String, Object> temp = new HashMap<String, Object>();
//		
//		for( Field field : fields ){
//			try {
//				temp.put(field.getName().toString(), field.get(c));
//			} catch (IllegalArgumentException e1){
//			} catch (IllegalAccessException e1) {}
//		 }
//		
//		for(String s : temp.keySet()) {
//			
//			if(!s.equals("list"))
//				list.add(s);
//			
//		}
//		
//	}
	
	public static void addNewType(String type) {
		list.add(type);
	}
	
}
