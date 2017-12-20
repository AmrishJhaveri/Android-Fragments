package com.example.amrish.project3_a1;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Amrish on 28-Oct-17.
 */

/**
 * This is a subclass of ListFragment to maintain the list of the landmarks
 */
public class LandmarkListFragment extends ListFragment {

    private ListSelectionListener mListener = null;

    /**
     * This is interface which is implemented by the activity. So the events can be sent to the activity.
     */
    public interface ListSelectionListener {
        public void onListSelection(int index);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {

            // The activity is attached to this reference since the interface is implemented by it.
            mListener = (ListSelectionListener) context;

        } catch (ClassCastException e) {
            throw e;
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Setting the ListAdaptor with the context, layout and the values.
        setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.landmark_list_item, Constants.getLandmarkNames()));

        //Single choice mode list
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        //Once the item in the list is clicked, it is highlighted.
        getListView().setItemChecked(position, true);

        //Also inform the activity so appropriate action can be taken.
        mListener.onListSelection(position);
    }

    public void deselectChoice(int position) {

        //De select the item in the list at the position.
        getListView().setItemChecked(position, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        Toast.makeText(getActivity(), "LandmarkListFragment on-create", Toast.LENGTH_SHORT).show();

        //To retain the fragment across device orientations
        setRetainInstance(true);

        super.onCreate(savedInstanceState);
    }
}
