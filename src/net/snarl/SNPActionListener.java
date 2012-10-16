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

public interface SNPActionListener {
	/**
	 * Called if the Notification was left clicked
	 */
	public void notificationLeftClicked();

	/**
	 * Called if the Notification was right clicked
	 */
	public void notificationRightClicked();

	/**
	 * Called if the Notification was closed by the user
	 */
	public void notificationClosed();

	/**
	 * Called if the Notification timed out
	 */
	public void notificationTimedOut();

}
