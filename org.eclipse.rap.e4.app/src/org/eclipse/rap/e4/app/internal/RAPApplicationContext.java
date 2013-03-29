package org.eclipse.rap.e4.app.internal;


import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.framework.Bundle;

/*
 * Fake context for IApplications
 */
final class RAPApplicationContext implements IApplicationContext {

  private final Map arguments;
  private Bundle brandingBundle;
  private Map<String, String> props;
  
  public RAPApplicationContext(Bundle brandingBundle, Map<String, String> props) {
    this.brandingBundle = brandingBundle;
	this.props = props;
	// FIXME where should these args really come from
	arguments = new HashMap( 2 );
    arguments.put( IApplicationContext.APPLICATION_ARGS,
                   Platform.getApplicationArgs() );
  }
  
  public void applicationRunning() {
    // do nothing
  }

  public Map getArguments() {
    return arguments;
  }

  public String getBrandingApplication() {
    return null;
  }

  public Bundle getBrandingBundle() {
    return brandingBundle;
  }

  public String getBrandingDescription() {
    return null;
  }

  public String getBrandingId() {
    return null;
  }

  public String getBrandingName() {
    return null;
  }

  public String getBrandingProperty( final String key ) {
    return props.get(key);
  }
  
  public void setResult( final Object result, final IApplication application ) {
    // do nothing
  }
}