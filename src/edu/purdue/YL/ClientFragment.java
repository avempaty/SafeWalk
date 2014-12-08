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
import android.widget.RadioGroup;
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
<<<<<<< HEAD
	EditText name;
	RadioGroup radioButtonGroup;
	
=======
	TextView preferences, name;
	EditText person;
>>>>>>> 289bd8ceeecf375446a0d73996fc5367c5778ba0
	
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
		name = (EditText) view.findViewById(R.id.editText1);
		radioButtonGroup = (RadioGroup) view.findViewById(R.id.radioGroup1);
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
	
	public String getName() {
		String data = this.name.toString();
		if(data == null)
			return null;
		else 
		{
			if(data.indexOf(",") != -1 || data.length() == 1)
				return null;
		}
		return data;
	}

	/**
	 * Returns the port enter by the user or the default value if the user
	 * didn't open the fragment.
	 */
	public int getPriority() {
		int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
		View radioButton = radioButtonGroup.findViewById(radioButtonID);
		int idx = radioButtonGroup.indexOfChild(radioButton);
		return idx;
	}
	
	public String getFrom()
	{
		String from = fromSpinner.getSelectedItem().toString();
		return from.substring(0,from.indexOf("\\s+"));
	}
	
	public String getTo()
	{
		String to = toSpinner.getSelectedItem().toString();
		return to.substring(0,to.indexOf("\\s+"));
	}
	public void person(View view) 
	{
		String personName = person.getText().toString();
	}
}
