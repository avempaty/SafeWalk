package edu.purdue.YL;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements SubmitCallbackListener,
		StartOverCallbackListener {

	/**
	 * The ClientFragment used by the activity.
	 */
	private ClientFragment clientFragment;

	/**
	 * The ServerFragment used by the activity.
	 */
	private ServerFragment serverFragment;

	/**
	 * UI component of the ActionBar used for navigation.
	 */
	private Button left;
	private Button right;
	private TextView title;

	/**
	 * Called once the activity is created.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_layout);

		this.clientFragment = ClientFragment.newInstance(this);
		this.serverFragment = ServerFragment.newInstance();

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.add(R.id.fl_main, this.clientFragment);
		ft.commit();
	}

	/**
	 * Creates the ActionBar: - Inflates the layout - Extracts the components
	 */
	@SuppressLint("InflateParams")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater()
				.inflate(R.layout.action_bar, null);

		// Set up the ActionBar
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);

		// Extract the UI component.
		this.title = (TextView) actionBarLayout.findViewById(R.id.tv_title);
		this.left = (Button) actionBarLayout.findViewById(R.id.bu_left);
		this.right = (Button) actionBarLayout.findViewById(R.id.bu_right);
		this.right.setVisibility(View.INVISIBLE);

		return true;
	}

	/**
	 * Callback function called when the user click on the right button of the
	 * ActionBar.
	 * 
	 * @param v
	 */
	public void onRightClick(View v) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();

		this.title.setText(this.getResources().getString(R.string.client));
		this.left.setVisibility(View.VISIBLE);
		this.right.setVisibility(View.INVISIBLE);
		ft.replace(R.id.fl_main, this.clientFragment);
		ft.commit();
	}

	/**
	 * Callback function called when the user click on the left button of the
	 * ActionBar.
	 * 
	 * @param v
	 */
	public void onLeftClick(View v) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();

		this.title.setText(this.getResources().getString(R.string.server));
		this.left.setVisibility(View.INVISIBLE);
		this.right.setVisibility(View.VISIBLE);
		ft.replace(R.id.fl_main, this.serverFragment);
		ft.commit();

	}

	/**
	 * Callback function called when the user click on the submit button.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onSubmit() {
		// TODO: Get client info via client fragment
<<<<<<< HEAD

		String name = this.clientFragment.getName();
		int priority = 0;
		switch (this.clientFragment.getPriority()) {
		case 0:
			priority = 0;
			break;
		case 1:
			priority = 1;
			break;
		case 2:
			priority = 2;
			break;
		}

		String from = this.clientFragment.getFrom();
		String to = this.clientFragment.getTo();

=======
		String person = this.clientFragment.person.toString();
		int type0 = this.clientFragment.radiobutton1.getId();
		int type1 = this.clientFragment.radiobutton2.getId();
		int type2 = this.clientFragment.radiobutton3.getId();
>>>>>>> 289bd8ceeecf375446a0d73996fc5367c5778ba0
		// Server info
		String host = this.serverFragment.getHost(getResources().getString(
				R.string.default_host));
		int port = this.serverFragment.getPort(Integer.parseInt(getResources()
				.getString(R.string.default_port)));
		// TODO: sanity check the results of the previous two dialogs
		boolean canRun = true;
		if (!checkHost(host))
			canRun = false;
		if (!checkPort(port))
			canRun = false;

		if (name == null) {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Alert");
			alertDialog.setMessage("Please fix name.");
			alertDialog.setButton("Okay",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			alertDialog.show();
			return;
		}

		if (from.equals("*")) {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Alert");
			alertDialog
					.setMessage("From cannot be *");
			alertDialog.setButton("Okay",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			alertDialog.show();
			return;
		}
		
		if(to.equals(from))
		{
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Alert");
			alertDialog
					.setMessage("To and from must be different");
			alertDialog.setButton("Okay",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			alertDialog.show();
			return;
		}
		
		if(to.equals("*") && priority != 2)
		{
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Alert");
			alertDialog
					.setMessage("Change settings to having no preference");
			alertDialog.setButton("Okay",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			alertDialog.show();
			return;
		}
		// TODO: Need to get command from client fragment
		if (!canRun) {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Alert");
			alertDialog
					.setMessage("The host or port are incorrect. Check server settings.");
			alertDialog.setButton("Okay",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			alertDialog.show();

			return;
		}
		
		String command = name + "," + from + "," + to + "," + priority;

		FragmentTransaction ft = getFragmentManager().beginTransaction();

		this.title.setText(getResources().getString(R.string.match));
		this.left.setVisibility(View.INVISIBLE);
		this.right.setVisibility(View.INVISIBLE);

		// TODO: You may want additional parameters here if you tailor
		// the match fragment
		MatchFragment frag = MatchFragment.newInstance(this, host, port,
				command);

		ft.replace(R.id.fl_main, frag);
		ft.commit();
	}

	/**
	 * Callback function call from MatchFragment when the user want to create a
	 * new request.
	 */
	@Override
	public void onStartOver() {
		onRightClick(null);
	}

	private boolean checkHost(String str) {
		String[] data = str.split("\\.");
		System.out.println(data.length);
		if (data.length != 4)
			return false;
		for (String a : data) {
			try {
				int temp = Integer.parseInt(a);
				System.out.println(temp);
				if (temp < 0 || temp > 255)
					return false;
			} catch (Exception ex) {
				return false;
			}
		}
		return true;
	}

	private boolean checkPort(int port) {
		if (port < 1 || port > 65535)
			return false;
		else
			return true;
	}
}
