package mobop.booklist.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

public class LanguageDialogFragment extends DialogFragment
{
    private static final List<String> LANGUAGES_CODE = Arrays.asList("en","fr","de","es","ja");

    public interface LanguageDialogListener {
        public void onLanguageItemClick(String languageCode);
        public String getCurrentLanguage();
    }

    // Use this instance of the interface to deliver action events
    private LanguageDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the LanguageDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the LanguageDialogListener so we can send events to the host
            mListener = (LanguageDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement LanguageDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String currentLanguage = mListener.getCurrentLanguage();
        int currentLanguageindex = LANGUAGES_CODE.indexOf(currentLanguage);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_language_message).setSingleChoiceItems(R.array.string_array_language, currentLanguageindex, new DialogInterface.OnClickListener() { //TODO init item check
            public void onClick(DialogInterface dialog, int which) {
                // The 'which' argument contains the index position
                // of the selected item
                mListener.onLanguageItemClick(LANGUAGES_CODE.get(which));
                //close the dialog
                dismiss();
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
