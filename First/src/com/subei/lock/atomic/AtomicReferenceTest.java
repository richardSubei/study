package com.subei.lock.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {

	public static void main(String[] args) {
		AtomicReference<Person> atomicReference = new AtomicReference<Person>();
		atomicReference.set(new Person("kobe", 30));
		atomicReference.getAndSet(new Person("james", 24));
		Person person = atomicReference.get();
		System.out.println(person.toString());
	}
}
