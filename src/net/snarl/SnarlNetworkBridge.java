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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

/**
 * SnarlNetworkBridge for SNP v1.1
 * 
 * 
 * @author Patrick von Reth
 * @version 1.1
 */
public class SnarlNetworkBridge {
	// enable/disable protocol output
	private static boolean debug = false;

	// protocol header
	/**
	 * The SNP version
	 */
	private static final float SNPVersion = 2.5f; //1.1f
	/**
	 * The SNP header containing version
	 */
	//public static final String head = "type=SNP#?version=" + SNPVersion;
	public static final String head = "snp://";

	// the Application Name registred with Snarl
	static SNPProperty appSig = new SNPProperty("app-sig");
	static SNPProperty appName = new SNPProperty("title");
	static SNPProperty appUID = new SNPProperty("uid");

	private static int timeout = 10;

	private static boolean snarlIsRunning = true;
	private static boolean snarlIsRegisterd = false;

	// Message buffer, messages waiting for a id/response
	private static Deque<Message> waitingMessages = new ArrayDeque<Message>();
	// Notifications waiting for user action
	private static HashMap<Integer, Notification> notifications = new HashMap<Integer, Notification>();

	private static Socket sock = null;
	private static PrintWriter out = null;
	private static BufferedReader in = null;
	private int id = 0;

	/**
	 * Register an application with Snarl
	 * 
	 * @param applicationName
	 *            the application name
	 * @param host
	 *            the host to connect to
	 * @return a message containing the reply of Snarl or null if an error
	 *         occurs
	 */
	static public Message snRegisterConfig(String applicationName,
						String applicationSig,
						String appUniqueId, 
						String host, String iconURL)
			throws Error {
		if (snarlIsRegisterd) {
			throw new Error("You have to unregister " + applicationName
					+ " first.");
		}
		appName.setValue(applicationName);
		appSig.setValue(applicationSig);
		appUID.setValue(appUniqueId);
		try {
			sock = new Socket(InetAddress.getByName(host), 9887);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(
					new InputStreamReader(sock.getInputStream()));

			new Thread("SnarlNetworkBridgeListener") {
				public void run() {
					SnarlNetworkBridge.listen();
				};
			}.start();
			snarlIsRegisterd = true;
			snarlIsRunning = true;
		} catch (ConnectException e) {
			snarlIsRunning = false;
			System.out.println("Snarl is not running");
		} catch (UnknownHostException e) {
			snarlIsRunning = false;
			System.out.println("Host not reachable");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Message msg = send(new Message("register", appName));
		Message msg = send(new Message("register", new SNPProperty[] { 
						appSig,
						appUID,
						appName,
						new SNPProperty("icon", iconURL)}));

		snarlIsRegisterd = snarlIsRunning = msg != null
				&& msg.getReply() != Reply.SNP_ERROR_NOT_RUNNING;
		return msg;
	}

	/**
	 * Register a new alert class to Snarl displayname and alert name are the
	 * same
	 * 
	 * @param title
	 *            the title representing the Alert
	 * @return a message containing the reply of Snarl
	 */
	public static Message snRegisterAlert(String title) {
		return snRegisterAlert(title, title);

	}

	/**
	 * Register a new alert class to Snarl
	 * 
	 * @param title
	 *            the title representing the Alert
	 * @param displayName
	 *            the user friendly name displayed in snarl
	 * @return a message containing the reply of Snarl
	 */
	public static Message snRegisterAlert(String title, String displayName) {
		return send(new Message("addclass", new SNPProperty[] { 
				appSig,
				new SNPProperty("id", title),
				new SNPProperty("title", title),
				new SNPProperty("name", displayName) }));

	}

	/**
	 * Displays a Snarl notification with the default timeout
	 * 
	 * @param alert the <tt>String</tt> to be used by the new instance for
	 *            the name of an registered Alert
	 * @param title the <tt>String</tt> to be used by the new instance for
	 *            the Title of the Notification
	 * @param content the <tt>String</tt> to be used by the new instance for
	 *            the Content of the Notification
	 * @return the Snarl notification
	 */
	public static Notification snShowMessage(String alert, String title,
			String content) {
		return snShowMessage(alert, title, content, null, timeout);
	}

	/**
	 * Displays a Snarl notification with a specific timeout
	 * 
	 * @param alert the <tt>String</tt> to be used by the new instance for
	 *            the name of an registered Alert
	 * @param title the <tt>String</tt> to be used by the new instance for
	 *            the Title of the Notification
	 * @param content the <tt>String</tt> to be used by the new instance for
	 *            the Content of the Notification
	 * @param timeout the <tt>String</tt> to be used by the new instance for
	 *            an integer value representing the timeout
	 * 
	 * @return the Snarl notification
	 */
	public static Notification snShowMessage(String alert, String title,
			String content, String iconURL, int timeout) {
		return snShowMessage(new Notification(alert, title, content, iconURL,
				timeout));
	}

	/**
	 * Displays a Snarl Notification
	 * 
	 * 
	 * @param notification
	 *            the Notification which is to be displayed
	 * @return the Snarl notification
	 */
	public static Notification snShowMessage(Notification notification) {
		if (!snarlIsRunning)
			return null;
		send(notification);
		return notification;
	}

	/**
	 * Unregister your Snarl Application with all alerts
	 * 
	 * @return a message containing the reply of Snarl
	 */
	public static Message snRevokeConfig() {
		snarlIsRegisterd = false;
		Message rep = new Message("unregister", appSig);
		send(rep);
		close();
		return rep;
	}

	/**
	 * Sets the default timeout
	 * 
	 * @param timeout
	 *            the timeout which is to be set
	 */
	public static void snSetTimeout(int timeout) {
		SnarlNetworkBridge.timeout = timeout;
	}

	/**
	 * Returns the default timeout
	 * 
	 * @return the default timeout
	 */
	public static int snGetTimeout() {
		return timeout;
	}

	/**
	 * Returns the SnarlNetworkBridge host
	 * 
	 * @return the ip of the host
	 */
	public static String snGetHost() {
		System.out.println("HostName: " + sock.getInetAddress().toString());
		return sock.getInetAddress().getHostAddress();
	}

	/**
	 * Returns a boolean value representing the Snarl running Status
	 * 
	 * @return true if Snarl is running and listening to network Connections
	 *         otherwise false
	 */
	public static boolean snIsRunning() {
		return snarlIsRunning;
	}

	public static void setDebug(boolean debug) {
		SnarlNetworkBridge.debug = debug;
	}

	/**
	 * Listening for Snarl Response
	 * 
	 * 
	 */
	private static void listen() 
	{
		String line = null;
		String data[] = null;
		Message reply = null;
		Reply replyType = null;
		try {
			while (snarlIsRegisterd&&(line=in.readLine())!=null) {
				if (debug)
					System.out.println("Reciving: " + line);
				line = line.replace("\r\n", "");
				data = line.split("/");
				replyType = Reply.getByCode(Integer.valueOf(data[2]));
				if (data.length == 5) {

					// Set action
					if (notifications.containsKey(Integer.valueOf(data[4]))) {
						reply = notifications.get(Integer.valueOf(data[4]));
						/*((Notification) reply).setUserAction(Action
								.getByCode(replyType.getCode()));
								*/
					} else {
						// set ID
						if (replyType == Reply.SNP_SUCCESS) {
							reply = waitingMessages.removeLast();
							reply.setReply(replyType);
							/*((Notification) reply).setId(data[4]);
							notifications.put(((Notification) reply).getId(),
									(Notification) reply);
									*/
						}
					}
				} else {
					// set Response
					replyType = Reply.getByCode(Integer.valueOf(data[2]));
					reply = waitingMessages.removeLast();
					reply.setReply(replyType);
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		snarlIsRunning=false;

	}

	/**
	 * Sends a string to Snarl
	 * 
	 * @param msg
	 *            the Message to send
	 * 
	 */
	private static Message send(Message msg)
	{
		if (!snarlIsRunning) 
		{
			System.err.println("Snarl is not running");
			return null;
		}
		waitingMessages.push(msg);
		if (debug)
			System.out.println("Sending: " + msg);
		
		out.println(msg);
		while (msg.getReply() == null) 
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return msg;
	}	


	/**
	 * Called by snRevokeConfig() closes the Socket and the Buffers
	 */
	private static void close() {
		try {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
			if (sock != null)
				sock.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SnarlNetworkBridge.snarlIsRegisterd = false;
	}

	/**
	 * Removes the notification from the notification HashMap
	 * 
	 * @param notification
	 *            the Notification which is to be removed
	 */
	static void removeNotification(Notification notification) {
		notifications.remove(notification);
		if (debug)
			System.out.println("Removed: " + notification.getId() + "_"
					+ notification.getUserAction());
	}
}
