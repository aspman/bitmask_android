/**
 * Copyright (c) 2013 LEAP Encryption Access Project and contributers
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package se.leap.bitmaskclient.eip;

import android.util.Log;
import java.util.*;

import de.blinkt.openvpn.core.*;

public class EipStatus extends Observable implements VpnStatus.StateListener {
    public static String TAG = EipStatus.class.getSimpleName();
    private static EipStatus current_status;

    private static EipStatus previous_status;
    private static VpnStatus.ConnectionStatus level = VpnStatus.ConnectionStatus.LEVEL_NOTCONNECTED;
    private static boolean wants_to_disconnect = false;
    private static boolean is_disconnecting = false;
    private static boolean is_connecting = false;

    private String state, log_message;
    private int localized_res_id;

    public static EipStatus getInstance() {
	if(current_status == null) {
	    current_status = new EipStatus();
	    VpnStatus.addStateListener(current_status);
	}
	return current_status;
    }

    private EipStatus() { }

    @Override
    public void updateState(final String state, final String logmessage, final int localizedResId, final VpnStatus.ConnectionStatus level) {
	current_status = getInstance();
	previous_status = current_status;
	current_status.setState(state);
	current_status.setLogMessage(logmessage);
	current_status.setLocalizedResId(localizedResId);
	current_status.setLevel(level);
	current_status.setChanged();
	Log.d(TAG, "update state with level " + level);
	current_status.notifyObservers();
    }

    public boolean isDisconnecting() {
	return is_disconnecting;
    }

    public boolean isConnecting() {
	return is_connecting;
    }

    public boolean wantsToDisconnect() {
	return wants_to_disconnect;
    }

    public boolean isConnected() {
	return level == VpnStatus.ConnectionStatus.LEVEL_CONNECTED;
    }

    public boolean isDisconnected() {
	return level == VpnStatus.ConnectionStatus.LEVEL_NOTCONNECTED || level == VpnStatus.ConnectionStatus.LEVEL_AUTH_FAILED;
    }

    public void setConnecting() {
	is_connecting = true;
	is_disconnecting = false;
	wants_to_disconnect = false;
    }

    public void setDisconnecting() {
	is_disconnecting = true;
	is_connecting = false;
	wants_to_disconnect = false;
	level = VpnStatus.ConnectionStatus.UNKNOWN_LEVEL; // Wait for the decision of the user
    }

    public void setWantsToDisconnect() {
	wants_to_disconnect = true;
    }

    public String getState() {
	return state;
    }

    public String getLogMessage() {
	return log_message;
    }

    public int getLocalizedResId() {
	return localized_res_id;
    }

    public VpnStatus.ConnectionStatus getLevel() {
	return level;
    }

    public EipStatus getPreviousStatus() {
	return previous_status;
    }

    private void setState(String state) {
	this.state = state;
    }

    private void setLogMessage(String log_message) {
	this.log_message = log_message;
    }

    private void setLocalizedResId(int localized_res_id) {
	this.localized_res_id = localized_res_id;
    }

    private void setLevel(VpnStatus.ConnectionStatus level) {
	this.level = level;
    }

}
