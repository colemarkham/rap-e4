package org.eclipse.rap.e4.app.internal;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private HttpServiceTracker httpServiceTracker;

	@Override
	public void start(BundleContext context) throws Exception {
		httpServiceTracker = new HttpServiceTracker(context);
		httpServiceTracker.open();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		httpServiceTracker.close();
	}

}
