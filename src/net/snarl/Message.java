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
 * 
 * A snarl Message containing Message and Snarl Reply
 * 
 * @author Patrick von Reth
 * 
 * 
 */
public class Message {

	private Reply reply = null;
	private SNPProperty msg[] = null;
	/**
	 * the SNP action(notification,register...);
	 */
	protected SNPProperty action;

	/**
	 * The Constructor of a Snarl Message
	 * 
	 * @param propertys
	 *            an array  of {@link SNPProperty}s 
	 */
	public Message(String action, SNPProperty[] propertys) {		
		this.action = new SNPProperty(action);
		this.msg = propertys;
	}

	/**
	 * The Constructor of a Snarl Message
	 * 
	 * @param property the {@link SNPProperty} of the message
	 */
	public Message(String action, SNPProperty property) {
		this.action = new SNPProperty(action);
		this.msg = new SNPProperty[] { property };
	}

	/**
	 * Returns the Reply of Snarl if set, otherwise null
	 * 
	 * @return the Reply of the Message
	 */
	public Reply getReply() {
		return reply;
	}

	/**
	 * Called by SnarlNetworkBridge to set the Snarl Reply
	 * 
	 * @param reply
	 *            the Reply which is to be set
	 */
	void setReply(Reply reply) {
		this.reply = reply;
	}

	/**
	 * Returns the SNP String
	 */
	public String toString() {
		String out = SnarlNetworkBridge.head + action;		
		for (SNPProperty p : msg) {
			out += p;
		}
		out = out.replaceFirst("&", "?");
		return out+"\r\n";
	}	
}
