package csc1304.gr13.retailmanagercsc.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.adapters.DueLvAdapter;
import csc1304.gr13.retailmanagercsc.database.CustomerDue;
import csc1304.gr13.retailmanagercsc.databinding.FragmentDueListBinding;
import csc1304.gr13.retailmanagercsc.interfaces.DueLvInterface;
import csc1304.gr13.retailmanagercsc.modelClass.DueModel;

import java.util.ArrayList;
/**
 * Created by CS1304 on 8/02/2021.
 */
/**
 * A simple {@link Fragment} subclass.
 */
public class DueListFragment extends Fragment {

    public static final String TAG = "csc1304.gr13.retailmanagercsc";
    private DueLvInterface dueLvInterface;
    private FragmentDueListBinding binder;
    private DueLvAdapter adapter;
    private ArrayList<DueModel> dues;
    private Context context;
    private CustomerDue customerDue;
    public DueListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater,R.layout.fragment_due_list, container, false);
        customerDue = new CustomerDue(getActivity());
        dues = customerDue.getDueList();
        adapter = new DueLvAdapter(getActivity(),dues);
        dueLvInterface = (DueLvInterface) context;

        return binder.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (customerDue.haveDue()){
            binder.dueLv.setVisibility(View.VISIBLE);
            binder.warning.setVisibility(View.GONE);

            binder.dueLv.setAdapter(adapter);
            binder.dueLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dueLvInterface.dueDetails(dues.get(position).getDueId());
                }
            });
        }else {
            binder.dueLv.setVisibility(View.GONE);
            binder.warning.setVisibility(View.VISIBLE);
        }


    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
