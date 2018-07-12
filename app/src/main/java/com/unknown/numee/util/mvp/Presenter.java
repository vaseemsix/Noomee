package com.unknown.numee.util.mvp;

import java.lang.ref.WeakReference;
import java.lang.reflect.Proxy;

public abstract class Presenter<T> {
	private WeakReference<T> viewWeakReference;
	private Class<T> type;

	public Presenter() {

	}

	public Presenter(T view) {
		setViewInternal(view);
	}

	public void setView(T view) {
		setViewInternal(view);
	}

	protected final T getView() {
		if (viewWeakReference == null) {
			throw new NullPointerException("You have never called method 'setView(T view)'");
		}
		T view = viewWeakReference.get();
		if (view == null) {
			return getNewInstanceOf(type);
		}
		return view;
	}

	private void setViewInternal(T view) {
		if (view == null) {
			throw new NullPointerException("'view' cannot be null");
		}
		this.viewWeakReference = new WeakReference<>(view);
		type = getTypeOfView(view);
	}

	@SuppressWarnings("unchecked")
	private Class<T> getTypeOfView(T view) {
		return (Class<T>) view.getClass();
	}

	@SuppressWarnings("unchecked")
	private T getNewInstanceOf(Class<T> type) {
		return (T) Proxy.newProxyInstance(type.getClassLoader(), type.getInterfaces(), new ProxyListener());
	}
}
