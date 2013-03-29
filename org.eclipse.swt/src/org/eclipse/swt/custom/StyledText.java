/*******************************************************************************
 * Copyright (c) 2000, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.custom;


import java.util.*;

import org.eclipse.swt.*;
import org.eclipse.swt.accessibility.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.internal.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

/**
 * A StyledText is an editable user interface object that displays lines 
 * of text.  The following style attributes can be defined for the text: 
 * <ul>
 * <li>foreground color 
 * <li>background color
 * <li>font style (bold, italic, bold-italic, regular)
 * <li>underline
 * <li>strikeout
 * </ul>
 * <p>
 * In addition to text style attributes, the background color of a line may 
 * be specified.
 * </p><p>
 * There are two ways to use this widget when specifying text style information.  
 * You may use the API that is defined for StyledText or you may define your own 
 * LineStyleListener.  If you define your own listener, you will be responsible 
 * for maintaining the text style information for the widget.  IMPORTANT: You may 
 * not define your own listener and use the StyledText API.  The following
 * StyledText API is not supported if you have defined a LineStyleListener:
 * <ul>
 * <li>getStyleRangeAtOffset(int)
 * <li>getStyleRanges()
 * <li>replaceStyleRanges(int,int,StyleRange[])
 * <li>setStyleRange(StyleRange)
 * <li>setStyleRanges(StyleRange[])
 * </ul>
 * </p><p>
 * There are two ways to use this widget when specifying line background colors.
 * You may use the API that is defined for StyledText or you may define your own 
 * LineBackgroundListener.  If you define your own listener, you will be responsible 
 * for maintaining the line background color information for the widget.  
 * IMPORTANT: You may not define your own listener and use the StyledText API.  
 * The following StyledText API is not supported if you have defined a 
 * LineBackgroundListener:
 * <ul>
 * <li>getLineBackground(int)
 * <li>setLineBackground(int,int,Color)
 * </ul>
 * </p><p>
 * The content implementation for this widget may also be user-defined.  To do so,
 * you must implement the StyledTextContent interface and use the StyledText API
 * setContent(StyledTextContent) to initialize the widget. 
 * </p><p>
 * <dl>
 * <dt><b>Styles:</b><dd>FULL_SELECTION, MULTI, READ_ONLY, SINGLE, WRAP
 * <dt><b>Events:</b><dd>ExtendedModify, LineGetBackground, LineGetSegments, LineGetStyle, Modify, Selection, Verify, VerifyKey, OrientationChange
 * </dl>
 * </p><p>
 * IMPORTANT: This class is <em>not</em> intended to be subclassed.
 * </p>
 *
 * @see <a href="http://www.eclipse.org/swt/snippets/#styledtext">StyledText snippets</a>
 * @see <a href="http://www.eclipse.org/swt/examples.php">SWT Examples: CustomControlExample, TextEditor</a>
 * @see <a href="http://www.eclipse.org/swt/">Sample code and further information</a>
 * @noextend This class is not intended to be subclassed by clients.
 */
public class StyledText extends Composite {
	private Text textArea;
	/**
 * Constructs a new instance of this class given its parent
 * and a style value describing its behavior and appearance.
 * <p>
 * The style value is either one of the style constants defined in
 * class <code>SWT</code> which is applicable to instances of this
 * class, or must be built by <em>bitwise OR</em>'ing together 
 * (that is, using the <code>int</code> "|" operator) two or more
 * of those <code>SWT</code> style constants. The class description
 * lists the style constants that are applicable to the class.
 * Style bits are also inherited from superclasses.
 * </p>
 *
 * @param parent a widget which will be the parent of the new instance (cannot be null)
 * @param style the style of widget to construct
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the parent</li>
 * </ul>
 *
 * @see SWT#FULL_SELECTION
 * @see SWT#MULTI
 * @see SWT#READ_ONLY
 * @see SWT#SINGLE
 * @see SWT#WRAP
 * @see #getStyle
 */
public StyledText(Composite parent, int style) {
	super(parent, style);
	setLayout(new FillLayout());
	textArea = new Text(this, SWT.MULTI | style);
}
/**	 
 * Adds a modify listener. A Modify event is sent by the widget when the widget text 
 * has changed.
 *
 * @param modifyListener the listener
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT when listener is null</li>
 * </ul>
 */
public void addModifyListener(ModifyListener modifyListener) {
	checkWidget();
	if (modifyListener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
	textArea.addListener(SWT.Modify, new TypedListener(modifyListener));
}
/**	 
 * Adds a selection listener. A Selection event is sent by the widget when the 
 * user changes the selection.
 * <p>
 * When <code>widgetSelected</code> is called, the event x and y fields contain
 * the start and end caret indices of the selection.
 * <code>widgetDefaultSelected</code> is not called for StyledTexts.
 * </p>
 * 
 * @param listener the listener which should be notified when the user changes the receiver's selection

 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @see SelectionListener
 * @see #removeSelectionListener
 * @see SelectionEvent
 */
public void addSelectionListener(SelectionListener listener) {
	checkWidget();
	if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
	textArea.addSelectionListener(listener);
}
/**	 
 * Adds a verify key listener. A VerifyKey event is sent by the widget when a key 
 * is pressed. The widget ignores the key press if the listener sets the doit field 
 * of the event to false. 
 *
 * @param listener the listener
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT when listener is null</li>
 * </ul>
 */
public void addVerifyKeyListener(VerifyKeyListener listener) {
	checkWidget();
	if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
	addListener(ST.VerifyKey, new StyledTextListener(listener));
}
/**	 
 * Adds a verify listener. A Verify event is sent by the widget when the widget text 
 * is about to change. The listener can set the event text and the doit field to 
 * change the text that is set in the widget or to force the widget to ignore the 
 * text change.
 *
 * @param verifyListener the listener
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT when listener is null</li>
 * </ul>
 */
public void addVerifyListener(VerifyListener verifyListener) {
	checkWidget();
	if (verifyListener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
	addListener(SWT.Verify, new TypedListener(verifyListener));
}
/** 
 * Appends a string to the text at the end of the widget.
 *
 * @param string the string to be appended
 * @see #replaceTextRange(int,int,String)
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT when listener is null</li>
 * </ul>
 */
public void append(String string) {
	checkWidget();
	if (string == null) {
		SWT.error(SWT.ERROR_NULL_ARGUMENT);
	}
//	int lastChar = Math.max(getCharCount(), 0);
//	replaceTextRange(lastChar, 0, string);
}
/**
 * Returns a copy of the widget content.
 *
 * @return copy of the widget content
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public String getText() {
	checkWidget();
	return textArea.getText();
}
/**
 * Removes the listener from the collection of listeners who will
 * be notified when the user changes the receiver's selection.
 *
 * @param listener the listener which should no longer be notified
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @see SelectionListener
 * @see #addSelectionListener
 */
public void removeSelectionListener(SelectionListener listener) {
	checkWidget();
	if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
	removeListener(SWT.Selection, listener);
}
/**
 * Removes the specified verify listener.
 *
 * @param verifyListener the listener which should no longer be notified
 * 
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT when listener is null</li>
 * </ul>
 */
public void removeVerifyListener(VerifyListener verifyListener) {
	checkWidget();
	if (verifyListener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
	removeListener(SWT.Verify, verifyListener);
}
/**
 * Removes the specified key verify listener.
 *
 * @param listener the listener which should no longer be notified
 * 
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT when listener is null</li>
 * </ul>
 */
public void removeVerifyKeyListener(VerifyKeyListener listener) {
	if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
	removeListener(ST.VerifyKey, listener);
}
/** 
 * Sets the selection to the given position and scrolls it into view.  Equivalent to setSelection(start,start).
 *
 * @param start new caret position
 * @see #setSelection(int,int)
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * @exception IllegalArgumentException <ul>
 *   <li>ERROR_INVALID_ARGUMENT when either the start or the end of the selection range is inside a 
 * multi byte line delimiter (and thus neither clearly in front of or after the line delimiter)
 * </ul> 
 */
public void setSelection(int start) {
	// checkWidget test done in setSelectionRange	
	setSelection(start, start);
}
/** 
 * Sets the selection and scrolls it into view.
 * <p>
 * Indexing is zero based.  Text selections are specified in terms of
 * caret positions.  In a text widget that contains N characters, there are 
 * N+1 caret positions, ranging from 0..N
 * </p>
 *
 * @param start selection start offset. The caret will be placed at the 
 * 	selection start when start > end.
 * @param end selection end offset
 * @see #setSelectionRange(int,int)
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * @exception IllegalArgumentException <ul>
 *   <li>ERROR_INVALID_ARGUMENT when either the start or the end of the selection range is inside a 
 * multi byte line delimiter (and thus neither clearly in front of or after the line delimiter)
 * </ul>
 */
public void setSelection(int start, int end) {
	textArea.setSelection(start, end);
}
/** 
 * Sets the widget content. 
 * If the widget has the SWT.SINGLE style and "text" contains more than 
 * one line, only the first line is rendered but the text is stored 
 * unchanged. A subsequent call to getText will return the same text 
 * that was set.
 * <p>
 * <b>Note:</b> Only a single line of text should be set when the SWT.SINGLE 
 * style is used.
 * </p>
 *
 * @param text new widget content. Replaces existing content. Line styles 
 * 	that were set using StyledText API are discarded.  The
 * 	current selection is also discarded.
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT when string is null</li>
 * </ul>
 */
public void setText(String text) {
	checkWidget();
	if (text == null) {
		SWT.error(SWT.ERROR_NULL_ARGUMENT);
	}
	textArea.setText(text);
}
}
