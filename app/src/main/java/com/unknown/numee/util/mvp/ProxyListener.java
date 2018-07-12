package com.unknown.numee.util.mvp;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class ProxyListener implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return getDefaultValue(method.getReturnType());
	}

	private Object getDefaultValue(final Class type) {
		if (boolean.class == type) {
			return false;
		} else if (byte.class == type) {
			return (byte) 0;
		} else if (char.class == type) {
			return (char) 0;
		} else if (short.class == type) {
			return (short) 0;
		} else if (int.class == type) {
			return 0;
		} else if (long.class == type) {
			return 0L;
		} else if (float.class == type) {
			return 0.0F;
		} else if (double.class == type) {
			return 0.0D;
		} else {
			return null;
		}
	}
}

