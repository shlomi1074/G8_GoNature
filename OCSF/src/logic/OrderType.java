package logic;

public enum OrderType {
	SOLO,
	FAMILY,
	GROUP;
	
	  @Override
	  public String toString() {
	    switch(this) {
	      case SOLO: return "Solo Visit";
	      case FAMILY: return "Family Visit";
	      case GROUP: return "Group Visit";
	      default: throw new IllegalArgumentException();
	    }
	  }
}
