package se.leap.bitmaskclient.eip;

import android.app.Activity;
import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;

public class VoidVpnLauncher extends Activity {

    private static final int VPN_USER_PERMISSION = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setUp();
    }
    
    public void setUp() {
	Intent blocking_intent = VpnService.prepare(getApplicationContext()); // stops the VPN connection created by another application.
	if(blocking_intent != null)
	    startActivityForResult(blocking_intent, VPN_USER_PERMISSION);
	else {
	    onActivityResult(VPN_USER_PERMISSION, RESULT_OK, null);
	}
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
	if(requestCode == VPN_USER_PERMISSION) {
	    if(resultCode == RESULT_OK) {
		Intent void_vpn_service = new Intent(getApplicationContext(), VoidVpnService.class);
		void_vpn_service.setAction(Constants.START_BLOCKING_VPN_PROFILE);
		startService(void_vpn_service);
	    }
	}
	finish();
    }
}
