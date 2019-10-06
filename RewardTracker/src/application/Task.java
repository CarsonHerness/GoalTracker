package application;

public enum Task {
	TIMED, ONE_TIME, REPEATABLE;
	
	@Override
	  public String toString() {
	    switch(this) {
	      case TIMED: return "Timed Task";
	      case ONE_TIME: return "One Time Task";
	      case REPEATABLE: return "Repeatable Task";
	      default: throw new IllegalArgumentException();
	    }
	  }
}
