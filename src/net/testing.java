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

package net;

import java.util.UUID;
import net.snarl.Notification;
import net.snarl.SNPActionListener;
import net.snarl.SnarlNetworkBridge;

public class testing {

	public static void main(String[] args) {
		SnarlNetworkBridge.setDebug(true);
		String appUID = UUID.randomUUID().toString();    
		SnarlNetworkBridge.snRegisterConfig("test-app", 
                                            "application/x-test-app", appUID,
                                            "127.0.0.1", "!message-new_message");
		SnarlNetworkBridge.snRegisterAlert("test");
		//register alert/class is now optional
		Notification notifyMsg = new Notification("",
                		"1004 wrote", "this is another test msg", "!message-new_message", 3);
        
		notifyMsg.setActionListener(new SNPActionListener() {
					
					public void notificationTimedOut() {
						// TODO Auto-generated method stub
						
					}
					
					public void notificationRightClicked() {
						System.out.println("Right clicked");
					}
					
					public void notificationLeftClicked() {
						System.out.println("Left clicked");
						
					}
					
					public void notificationClosed() {
						// TODO Auto-generated method stub
						
					}
				});
		SnarlNetworkBridge.snShowMessage(notifyMsg);
		SnarlNetworkBridge.snRevokeConfig();

	}

}
