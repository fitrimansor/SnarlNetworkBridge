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
 * Enumeration of all user actions which can be applied to a Snarl Notification
 * 
 * @author Patrick von Reth
 * 
 * 
 */
public enum Action {

	RhigthClicked(302), Timed_Out(303), LeftClicked(304), Closed(307);

	private int code;

	private Action(int code) {
		this.code = code;
	}

	/**
	 * Returns the enum constant of this type with the specified code.
	 * 
	 * @param code
	 *            the Code of the enum constant to be returned
	 * @return the eum constant with the specific code
	 */
	public static Action getByCode(int code) {
		for (Action a : Action.values())
			if (a.code == code)
				return a;
		return Timed_Out;
	}

	/**
	 * Returns the code of the specific enum constant
	 * 
	 * @return the code of the enum constant
	 */
	public int getCode() {
		return code;
	}
}
