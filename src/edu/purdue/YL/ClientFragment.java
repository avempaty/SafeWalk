package edu.purdue.YL;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * This fragment is the "page" where the user inputs information about the
 * request, he/she wishes to send.
 *
 * @author YL
 */
public class ClientFragment extends Fragment implements OnClickListener {

	/**
	 * Activity which have to receive callbacks.
	 */
	private SubmitCallbackListener activity;
	Spinner fromSpinner, toSpinner;
	RadioButton radiobutton1, radiobutton2, radiobutton3;
	TextView preferences, name;
	EditText person;
	
	/**
	 * Creates a ProfileFragment
	 * 
	 * @param activity
	 *            activity to notify once the user click on the submit Button.
	 * 
	 *            ** DO NOT CREATE A CONSTRUCTOR FOR MatchFragment **
	 * 
	 * @return the fragment initialized.
	 */
	// ** DO NOT CREATE A CONSTRUCTOR FOR ProfileFragment **
	public static ClientFragment newInstance(SubmitCallbackListener activity) {
		ClientFragment f = new ClientFragment();

		f.activity = activity;
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

		View view = inflater.inflate(R.layout.client_fragment_layout,
				container, false);

		/**
		 * Register this fragment to be the OnClickListener for the submit
		 * Button.
		 */
		view.findViewById(R.id.bu_submit).setOnClickListener(this);
		
		// TODO: import your Views from the layout here. See example in
		// ServerFragment.
		radiobutton1 = (RadioButton) view.findViewById(R.id.radioButton1);
		radiobutton2 = (RadioButton) view.findViewById(R.id.radioButton2);
		radiobutton3 = (RadioButton) view.findViewById(R.id.radioButton3);
		fromSpinner = (Spinner) view.findViewById(R.id.fromSpinner);
		toSpinner = (Spinner) view.findViewById(R.id.toSpinner);
		preferences = (TextView) view.findViewById(R.id.textView2);
		name = (TextView) view.findViewById(R.id.serverObtained);
		person = (EditText) view.findViewById(R.id.editText1);
		
		
		return view;
	}

	/**
	 * Callback function for the OnClickListener interface.
	 */
	@Override
	public void onClick(View v) {
		this.activity.onSubmit();
	}
		
	public void radio1Clicked(View view)
	{
	    // Note that I have unchecked  radiobuttons except the one
	    // which is clicked/checked by user
	    radiobutton2.setChecked(false);
	    radiobutton3.setChecked(false);
	}

	public void radio2Clicked(View view)
	{
	    // Note that I have unchecked  radiobuttons except the one
	    // which is clicked/checked by user
	    radiobutton1.setChecked(false);
	    radiobutton3.setChecked(false);
	}

	public void radio3Clicked(View view)
	{
	    // Note that I have unchecked  radiobuttons except the one
	    // which is clicked/checked by user
	    radiobutton2.setChecked(false);
	    radiobutton1.setChecked(false);
	}
	public void person(View view) 
	{
		String personName = person.getText().toString();
	}
}
