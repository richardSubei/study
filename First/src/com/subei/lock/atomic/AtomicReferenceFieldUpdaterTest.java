package com.subei.lock.atomic; 

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;


public class AtomicReferenceFieldUpdaterTest {

	public static void main(String[] args) {
		AtomicReferenceFieldUpdater<Person, String> updater = AtomicReferenceFieldUpdater.newUpdater(Person.class, String.class, "name");
		Person person = new Person("kobe", 30);
//		updater.compareAndSet(person, person.getName(), "james");
		updater.getAndSet(person, "kurry");
//		updater.accumulateAndGet(person, "james", );
		System.out.println(person.toString());
	}
}
