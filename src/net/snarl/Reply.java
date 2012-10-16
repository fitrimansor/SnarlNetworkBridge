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
 * Enumeration containing all Snarl Replys
 * 
 * @author Patrick von Reth
 * 
 * 
 * 
 */
public enum Reply {
	SNP_SUCCESS(0), SNP_ERROR_FAILED(101), SNP_ERROR_UNKNOWN_COMMAND(102), SNP_ERROR_TIMED_OUT(
			103), SNP_ERROR_BAD_PACKET(107), SNP_ERROR_NOT_RUNNING(201), SNP_ERROR_NOT_REGISTERED(
			202), SNP_ERROR_ALREADY_REGISTERED(203), SNP_ERROR_CLASS_ALREADY_EXISTS(
			204), Notification_Cancelled(302), Notification_Timed_Out(303), Notification_Acknowledged(
			304), Notification_Closed(307);
	private int code;

	private Reply(int code) {
		this.code = code;
	}

	/**
	 * Returns the enum constant of this type with the specified code.
	 * 
	 * @param code
	 *            the Code of the enum constant to be returned
	 * @return the eum constant with the specific code
	 */
	public static Reply getByCode(int code) {
		for (Reply r : Reply.values())
			if (r.code == code)
				return r;
		return SNP_ERROR_FAILED;
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
