package logic;

public enum WorkerType {
	SERVICE,
	ENTRANCE,
	PARK_MANAGER,
	DEPARTMENT_MANAGER;
	
	  @Override
	  public String toString() {
	    switch(this) {
	      case SERVICE: return "Service";
	      case ENTRANCE: return "Entrance";
	      case PARK_MANAGER: return "Park Manager";
	      case DEPARTMENT_MANAGER: return "Department Manager";
	      default: throw new IllegalArgumentException();
	    }
	  }
}
