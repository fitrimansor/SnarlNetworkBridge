/*
    Copyright 2012 Patrick von Reth <vonreth@kde.org>
    
    This file is part of SnarlNetworkBridge.

    SnarlNetworkBridge is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    SnarlNetworkBridge is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with SnarlNetworkBridge.  If not, see <http://www.gnu.org/licenses/>.
*/

package net.snarl;

/**
 * A Snarl Notification, Containing Reply, Action and Notification ID
 * 
 * @author Patrick von Reth
 * 
 * 
 */

public class Notification extends Message {	
	private int id = -1;
	private SNPActionListener actionListener = null;
	private Action userAction = null;

	/**
	 * Creates a new Default Notification with SnarlNetworkBridge default
	 * timeout
	 * 
	 * @param alert the <tt>String</tt> to be used by the new instance for
	 *            the name of the alert class
	 * @param title the <tt>String</tt> to be used by the new instance for
	 *            the title of the Notification
	 * @param content the <tt>String</tt> to be used by the new instance for
	 *            the content of the Notification
	 * @param iconUrl the <tt>String</tt> to be used by the new instance for
	 *            the http url to the icon
	 */
	public Notification(String alert, String title, String content,
			String iconUrl) {
		super("notify", new SNPProperty[] {
				SnarlNetworkBridge.appSig,
				new SNPProperty("id", alert),
				new SNPProperty("title", title),
				new SNPProperty("text", content),
				new SNPProperty("icon", iconUrl),
				new SNPProperty("timeout", String.valueOf(SnarlNetworkBridge
						.snGetTimeout())) });
	}

	/**
	 * Creates a new Default Notification
	 * 
	 * @param alert the <tt>String</tt> to be used by the new instance for
	 *            the name of the alert class
	 * @param title the <tt>String</tt> to be used by the new instance for
	 *            the title of the Notification
	 * @param content the <tt>String</tt> to be used by the new instance for
	 *            the content of the Notification
	 * @param iconUrl the <tt>String</tt> to be used by the new instance for
	 *            the http url to the icon
	 * @param timeout the <tt>int</tt> to be used by the new instance for
	 *            the timeout of the Notification
	 * 
	 */
	public Notification(String alert, String title, String content,
			String iconUrl, int timeout) {
		super("notify", new SNPProperty[] {
				SnarlNetworkBridge.appSig,
				new SNPProperty("id", alert),
				new SNPProperty("title", title),
				new SNPProperty("text", content),
				new SNPProperty("icon", iconUrl),
				new SNPProperty("timeout", String.valueOf(timeout)) });

	}

	/**
	 * Sets the action which the user applied
	 * 
	 * @param action
	 *            the Action to be set
	 * 
	 */
	void setUserAction(Action action) {
		this.userAction = action;
		if (action == Action.Closed || action == Action.LeftClicked
				|| action == Action.Timed_Out)
			SnarlNetworkBridge.removeNotification(this);
		if (actionListener == null)
			return;
		switch (action) {
		case LeftClicked:
			actionListener.notificationLeftClicked();
			break;
		case RhigthClicked:
			actionListener.notificationRightClicked();
			break;
		case Closed:
			actionListener.notificationClosed();
			break;
		case Timed_Out:
			actionListener.notificationTimedOut();
		default:
			break;
		}
	}

	/**
	 * Get the Action which the user applied, or null if nothing set
	 * 
	 * @return the Action of the Notification
	 */
	public Action getUserAction() {
		return userAction;
	}

	/**
	 * Sets the id of the Notification called by SnarlNetworkBridge
	 * 
	 * @param id
	 *            the id to be set
	 */
	void setId(String id) {
		this.id = Integer.valueOf(id);
	}

	/**
	 * Returns the Snarl Notification Id
	 * 
	 * @return id of the Notification
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the SNPActionListener
	 * 
	 * @param actionListener
	 *            the ActionListener to be set
	 */
	public void setActionListener(SNPActionListener actionListener) {
		this.actionListener = actionListener;
	}

	/**
	 * Returns the SNPActionlistener
	 * 
	 * @return the current ActionListner or null
	 */
	public SNPActionListener getActionListener() {
		return actionListener;
	}

}
