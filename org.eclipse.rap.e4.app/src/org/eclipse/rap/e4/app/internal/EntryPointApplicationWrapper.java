package org.eclipse.rap.e4.app.internal;


import java.util.Map;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;


public final class EntryPointApplicationWrapper implements EntryPoint {

  private static IApplicationContext context;

  private final Class<? extends IApplication> applicationClass;

  public EntryPointApplicationWrapper( Class<? extends IApplication> applicationClass, Bundle brandingBundle, Map<String, String> props ) {
    this.applicationClass = applicationClass;
    context = new RAPApplicationContext(brandingBundle, props);
  }

  /*
   * Note [rst]: We don't call IApplication#stop(). According to the documentation, stop() is "only
   * called to force an application to exit" and "not called if an application exits normally from
   * start()".
   * See also https://bugs.eclipse.org/bugs/show_bug.cgi?id=372946
   */
  public int createUI() {
    int result = 0;
    IApplication application = createApplication();
    try {
      Object exitCode = application.start( context );
      if( exitCode instanceof Integer ) {
        result = ( ( Integer )exitCode ).intValue();
      }
    } catch( Exception exception  ) {
      String message = "Exception while executing application " + applicationClass.getName();
      throw new RuntimeException( message, exception );
    }
    return result;
  }

  private IApplication createApplication() {
    IApplication application;
    try {
      application = applicationClass.newInstance();
    } catch( Exception exception ) {
      String message = "Failed to create application " + applicationClass.getName();
      throw new IllegalArgumentException( message, exception );
    }
    return application;
  }
}
