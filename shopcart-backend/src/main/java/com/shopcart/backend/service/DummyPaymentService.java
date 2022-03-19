package com.shopcart.backend.service;

public class DummyPaymentService {

	private static DummyPaymentService instance = null;
	public static int count = 0;

	public static DummyPaymentService getInstance() {
		if (instance == null)
			instance = new DummyPaymentService();

		return instance;
	}

	public boolean pay() {
		System.out.print("Count :" + count);
		if (++count % 3 == 0) {
			return false; // Failure
		} else {
			return true; // Success
		}
	}
}
