package edu.purdue.YL;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Fragment;
import android.util.Log;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * This fragment is the "page" where the application display the log from the
 * server and wait for a match.
 *
 * @author YL
 */
public class MatchFragment extends Fragment implements OnClickListener {

	private static final String DEBUG_TAG = "DEBUG";

	/**
	 * Activity which have to receive callbacks.
	 */
	private StartOverCallbackListener activity;

	/**
	 * AsyncTask sending the request to the server.
	 */
	private Client client;

	/**
	 * Coordinate of the server.
	 */
	private String host;
	private int port;

	/**
	 * Command the user should send.
	 */
	private String command;

	// TODO: your own class fields here
	TextView serverStatus, partner, clientInfo, serverResponse;

	// Class methods
	/**
	 * Creates a MatchFragment
	 * 
	 * @param activity
	 *            activity to notify once the user click on the start over
	 *            Button.
	 * @param host
	 *            address or IP address of the server.
	 * @param port
	 *            port number.
	 * 
	 * @param command
	 *            command you have to send to the server.
	 * 
	 * @return the fragment initialized.
	 */
	// TODO: you can add more parameters, follow the way we did it.
	// ** DO NOT CREATE A CONSTRUCTOR FOR MatchFragment **
	public static MatchFragment newInstance(StartOverCallbackListener activity,
			String host, int port, String command) {
		MatchFragment f = new MatchFragment();

		f.activity = activity;
		f.host = host;
		f.port = port;
		f.command = command;

		return f;
	}

	/**
	 * Called when the fragment will be displayed.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}

		View view = inflater.inflate(R.layout.match_fragment_layout, container,
				false);

		/**
		 * Register this fragment to be the OnClickListener for the startover
		 * button.
		 */
		view.findViewById(R.id.bu_start_over).setOnClickListener(this);

		// TODO: import your Views from the layout here. See example in
		// ServerFragment.

		serverStatus = (TextView) view.findViewById(R.id.serverObtained);
		clientInfo = (TextView) view.findViewById(R.id.clientInfo);
		serverResponse = (TextView) view.findViewById(R.id.serverResponse);
		partner = (TextView) view.findViewById(R.id.partner);

		/**
		 * Launch the AsyncTask
		 */
		this.client = new Client();
		this.client.execute("");

		return view;
	}

	/**
	 * Callback function for the OnClickListener interface.
	 */
	@Override
	public void onClick(View v) {
		/**
		 * Close the AsyncTask if still running.
		 */
		this.client.close();

		/**
		 * Notify the Activity.
		 */
		this.activity.onStartOver();
	}

	class Client extends AsyncTask<String, String, String> implements Closeable {
		Socket s;
		String result = null;

		/**
		 * NOTE: you can access MatchFragment field from this class:
		 * 
		 * Example: The statement in doInBackground will print the message in
		 * the Eclipse LogCat view.
		 */

		/**
		 * The system calls this to perform work in a worker thread and delivers
		 * it the parameters given to AsyncTask.execute()
		 */
		protected String doInBackground(String... params) {

			/**
			 * TODO: Your Client code here.
			 */
			Log.d(DEBUG_TAG, String
					.format("The Server at the address %s uses the port %d",
							host, port));

			Log.d(DEBUG_TAG, String.format(
					"The Client will send the command: %s", command));
			try {
				Log.i("Back", "About to connect");
				s = new Socket(host, port);
				Log.i("Back", "found server");
				PrintWriter out = new PrintWriter(s.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						s.getInputStream()));
				Log.i("Back", "Created p&b");
				out.println(command);
				Log.i("Back", "Sent command");
				publishProgress();
				for (;;) {
					result = in.readLine();
					if (result.startsWith("RESPONSE: "))
						out.println(":ACK");
					break;
				}
			} catch (IOException e) {
				Log.i("Excetion", "" + 1);
				result = "the server is not available";
				publishProgress();
			}
			publishProgress();
			return result;
		}

		public void close() {
			// TODO: Clean up the client
			try {
				if(s == null)
					return;
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/**
		 * The system calls this to perform work in the UI thread and delivers
		 * the result from doInBackground()
		 */

		// TODO: use the following method to update the UI.
		// ** DO NOT TRY TO CALL UI METHODS FROM doInBackground!!!!!!!!!! **

		/**
		 * Method executed just before the task.
		 */
		@Override
		protected void onPreExecute() {

		}

		/**
		 * Method executed once the task is completed.
		 */
		@Override
		protected void onPostExecute(String result) {
		}

		/**
		 * Method executed when progressUpdate is called in the doInBackground
		 * function.
		 */
		@Override
		protected void onProgressUpdate(String... response) {
			Log.i("progress", "1");
			if (s != null && s.isConnected()) {
				Log.i("progress", "connected");
				serverStatus.setText("Server is connected");
				clientInfo.setText("Sending: " + command);
				Log.i("progress", "set text 1");
				if (result != null) {
					Log.i("progress", result);
					serverResponse.setText("A match was found.");
					partner.setText(result);
				} else {
					Log.i("progress", "result null");
					serverResponse.setText("Waiting on a match");
				}
			} else {
				Log.i("progress", "Server not available");
				serverStatus.setText("Server is not available.");
			}
		}
	}

}
