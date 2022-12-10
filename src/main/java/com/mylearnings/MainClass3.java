package com.mylearnings;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Student {
	
	private String name;
	private Float marks;
	private Boolean distinction;
	
	public Student(String name, Float marks) {
		super();
		this.name = name;
		this.marks = marks;
	}
	
	
	public Student(String name, Float marks, Boolean distinction) {
		super();
		this.name = name;
		this.marks = marks;
		this.distinction = distinction;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getMarks() {
		return marks;
	}
	public void setMarks(Float marks) {
		this.marks = marks;
	}
	public Boolean getDistinction() {
		return distinction;
	}
	public void setDistinction(Boolean distinction) {
		this.distinction = distinction;
	}

}

public class MainClass3{
	
	public static void main(String[] args) {
		
		List<Student> students=Arrays.asList(new Student("john",85.0f),new Student("Ajay",70.0f),new Student("Mani",85.0f),new Student("Vikram",60.0f));
		
		List<Student> student2s=students.stream().filter(s -> s.getMarks()>=80.0f).map(s -> new Student(s.getName(), s.getMarks(), true)).collect(Collectors.toList());
		
		System.out.println(student2s);
		
	}
	
}