package selectdegree;

public class Degree {
	private String name;
	private String degree;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	@Override
	public String toString() {
		return "Degree [name=" + name + ", degree=" + degree + "]";
	}
	

}
