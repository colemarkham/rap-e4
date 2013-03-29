/*******************************************************************************
 * Copyright (c) 2007, 2012 Innoopract Informationssysteme GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Innoopract Informationssysteme GmbH - initial API and implementation
 *    EclipseSource - ongoing development
 ******************************************************************************/
package org.eclipse.rap.e4.app.internal.branding;

import java.io.IOException;


/**
 * This abstract class is intended to be implemented by clients that want
 * to have control over various aspects of the web application such as
 * <ul>
 * <li>the servlet name at which the application can be accessed,</li>
 * <li>the default entry point and the list of entry points that may
 * be accessed</li>
 * <li>appearance of the startup page (favorites icon, markup of the page
 * body, etc.),</li>
 * <li>the theme to be used</li>
 * </ul>
 * It serves as a callback that answers requests to the above outlined
 * aspects via its getter methods.
 *
 * <p><strong>Note:</strong> Instances of this class are expected to be
 * immutable. All getter methods should return the same values whenever
 * they are called.</p>
 *
 * @since 2.0
 */
public abstract class AbstractBranding {

  /**
   * Returns the name of the servlet on which the application should be
   * available. Defining this attribute will cause your application to
   * be available at
   * <code>http://&lt;host&gt;:&lt;port&gt;/&lt;servletName&gt;</code>.
   * <p>The default behavior is to return <code>null</code>.</p>
   *
   * @return the servlet name, must not return <code>null</code>
   */
  public String getServletName() {
    return null;
  }

  /**
   * Returns the default entry point. Returning <code>null</code> or an
   * empty string indicates that there is no default entry point.
   * A URL like <code>http://&lt;host&gt;:&lt;port&gt;/&lt;servletName&gt;
   * </code> would automatically execute the entry point returned here.
   * <p>The default behavior is to return <code>null</code>.</p>
   *
   * @return the default entry point or <code>null</code>
   */
  public String getDefaultEntryPoint() {
    return null;
  }

  /**
   * Returns an array of entry points which are allowed to be the started with
   * this branding (or servlet name). If <code>null</code> or an empty array
   * is returned, every entrypoint is allowed to be started.
   * <p>The default behavior is to return <code>null</code>.</p>
   *
   * @return an array of string, denoting the allowed entry points or
   * <code>null</code>
   */
  public String[] getEntryPoints() {
    return null;
  }

  /**
   * Returns the id of the theme to be used with this branding or
   * <code>null</code> to indicate that the default theme should be used.
   * <p>The default behavior is to return <code>null</code>.</p>
   *
   * @return the theme id or <code>null</code>
   */
  public String getThemeId() {
    return null;
  }

  /**
   * Returns the id of this branding extension.
   * <p>The default behavior is to return <code>null</code>.</p>
   *
   * @return the branding extension's id or <code>null</code>.
   * @since 1.1
   */
  public String getId() {
    return null;
  }

  /**
   * Returns the resource name for the favorites icon or <code>null</code> to
   * indicate that no favorites icon is available.
   * <p><strong>Note:</strong> if a fav icon is provided, the application code
   * must register the resource at the <code>ResourceManager</code>.
   * Preferrably, this should be done in the <code>registerResources</code>
   * callback method.</p>
   * <p>The default behavior is to return <code>null</code>.</p>
   *
   * @return the favorites icon or <code>null</code>
   * @see org.eclipse.rap.rwt.service.ResourceManager
   * @see #registerResources()
   */
  public String getFavIcon() {
    return null;
  }

  /**
   * Returns the title that will be displayed in the browser window or
   * <code>null</code> to indicate that no title should be displayed.
   * <p>The default behavior is to return <code>null</code>.</p>
   *
   * @return the title or <code>null</code>
   */
  public String getTitle() {
    return null;
  }

  /**
   * Returns an array of HTML header tags or null if no additional headers
   * are provided.
   * <p>The default behavior is to return <code>null</code>.</p>
   *
   * @return an array of <code>Header</code> instances or <code>null</code>
   * @see Header
   */
  public Header[] getHeaders() {
    return null;
  }

  /**
   * Returns HTML code to be placed inside the <code>&lt;body&gt;</code> tag or
   * <code>null</code> if no custom HTML code should be placed inside the
   * </code>&lt;body&gt;</code> tag.
   * <p>Be aware that the HTML code returned by this method is taken as-is
   * and may break the surrounding HTML page.</p>
   * <p>The default behavior is to return <code>null</code>.</p>
   *
   * @return body HTML code or <code>null</code>
   */
  public String getBody() {
    return null;
  }

  /**
   * This method is called before the branding is applied for the first time.
   * Clients may use this to register resources used by the branding such as
   * the {@link #getFavIcon() <code>favIcon</code>}.
   * <p>The default behavior is to do nothing.</p>
   *
   * @throws IOException if an I/O error occurs
   */
  public void registerResources() throws IOException {
    // do nothing
  }
}
