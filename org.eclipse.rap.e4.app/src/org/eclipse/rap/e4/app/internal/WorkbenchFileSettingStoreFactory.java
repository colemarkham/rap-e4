package org.eclipse.rap.e4.app.internal;
/*******************************************************************************
 * Copyright (c) 2008, 2012 Innoopract Informationssysteme GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Innoopract Informationssysteme GmbH - initial API and implementation
 *    EclipseSource - ongoing devleopment
 ******************************************************************************/


import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.internal.util.ParamCheck;
import org.eclipse.rap.rwt.service.FileSettingStore;
import org.eclipse.rap.rwt.service.FileSettingStoreFactory;
import org.eclipse.rap.rwt.service.SettingStore;
import org.eclipse.rap.rwt.service.SettingStoreFactory;
import org.osgi.framework.Bundle;


/**
 * A setting store factory that creates {@link FileSettingStore}
 * instances.
 * <p>
 * This particular implementation uses the following strategy to determine
 * the path for persisting the data of a FileSettingStore:
 * <ol>
 * <li>Use the directory specified by the system property
 * <code>"org.eclipse.rap.rwt.service.FileSettingStore.dir"</code>.
 * </li>
 * <li>Use a subdirectory in the state location of the
 * org.eclipse.rap.ui.workbench bundle.
 * </li>
 * </ol>
 * The first path that can be obtained from the above choices (in the order
 * given above) will be used. If the path determined does not exist it will
 * be created.
 * <p>
 * <b>Note:</b> This setting store factory should be used in a regular
 * RAP deployment. For an RWT only deployment use the
 * {@link FileSettingStoreFactory}.
 *
 */
public final class WorkbenchFileSettingStoreFactory
  implements SettingStoreFactory
{

  public SettingStore createSettingStore( final String storeId ) {
    ParamCheck.notNullOrEmpty( storeId, "storeId" ); //$NON-NLS-1$
    SettingStore result = new FileSettingStore( getWorkDir() );
    try {
      result.loadById( storeId );
    } catch( IOException exception ) {
      String message = String.valueOf( exception.getMessage() );
      RWT.getRequest().getSession().getServletContext().log( message, exception );
    }
    return result;
  }

  //////////////////
  // helping methods

  private File getWorkDir() {
    File result = getWorkDirFromEnvironment();
    if( result == null ) {
      Bundle bundle = Platform.getBundle( "org.eclipse.e4.ui.workbench" );
      IPath stateLoc = Platform.getStateLocation( bundle );
      File parentDir = stateLoc.toFile();
      result = new File( parentDir, FileSettingStore.class.getName() );
    }
    if( !result.exists() ) {
      result.mkdirs();
    }
    return result;
  }

  private File getWorkDirFromEnvironment() {
    String path = System.getProperty( FileSettingStore.FILE_SETTING_STORE_DIR );
    return ( path != null ) ? new File( path ) : null;
  }
}
