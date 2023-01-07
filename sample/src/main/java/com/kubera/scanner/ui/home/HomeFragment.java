package com.kubera.scanner.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProvider;

import com.kubera.scanner.FragmentCommunicator;
import com.kubera.scanner.HomeScreen;
import com.kubera.scanner.KdGaugeView;
import com.kubera.scanner.R;
import com.kubera.scanner.databinding.FragmentHomeBinding;
import com.kubera.scanner.percentageview.PercentageChartView;

import org.json.JSONObject;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private KdGaugeView kdGaugeView;
    private PercentageChartView percentageChartView;
    private TextView voltage,current;
    int i=0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

       // final TextView textView = binding.textHome;
       // homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        kdGaugeView = root.findViewById(R.id.speedMeter);
        percentageChartView = root.findViewById(R.id.bat_view_id);
        voltage = root.findViewById(R.id.voltage);
        current = root.findViewById(R.id.current);


        ((HomeScreen) getActivity()).passVal(new FragmentCommunicator() {
            @Override
            public void passData(String name) {
               // Toast.makeText(requireContext(), name, Toast.LENGTH_SHORT).show();
                try {

                    JSONObject obj = new JSONObject(name);

                    String SOC = obj.getString("soc");
                    String speed = obj.getString("speed");
                    String currentStr = obj.getString("packCurrent");
                    String voltageStr = obj.getString("packVoltage");
                    kdGaugeView.setSpeed(Float.parseFloat(speed));
                    percentageChartView.setProgress(Float.parseFloat(SOC),true);
                    voltage.setText(voltageStr);
                    current.setText(currentStr);
                    Log.d("My App", obj.toString());
                } catch (Throwable t) {
                    Log.e("My App", "Could not parse malformed JSON:");
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}