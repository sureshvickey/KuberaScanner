package com.kubera.scanner.ui.home;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kubera.scanner.MapsActivity;
import com.kubera.scanner.utils.FragmentCommunicator;
import com.kubera.scanner.HomeScreen;
import com.kubera.scanner.utils.KdGaugeView;
import com.kubera.scanner.R;
import com.kubera.scanner.databinding.FragmentHomeBinding;
import com.kubera.scanner.percentageview.PercentageChartView;

import org.json.JSONObject;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding binding;
    private KdGaugeView kdGaugeView;
    private PercentageChartView percentageChartView;
    private TextView voltage,current;
    private ImageView modeImage;
    private CardView map;
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
        map = root.findViewById(R.id.map);
        modeImage = root.findViewById(R.id.modeImage);
        map.setOnClickListener(this);
        modeImage.setBackgroundResource(R.drawable.eco);

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
                    String driveMode = obj.getString("driveMode");
                    if(driveMode.equals("0")){
                        modeImage.setBackgroundResource(R.drawable.eco);
                    }else if(driveMode.equals("1")){
                        modeImage.setBackgroundResource(R.drawable.power);
                    }else if(driveMode.equals("2")){
                        modeImage.setBackgroundResource(R.drawable.quick);
                    }else if(driveMode.equals("5")){
                        modeImage.setBackgroundResource(R.drawable.rewind);
                    }else if(driveMode.equals("8")){
                        modeImage.setBackgroundResource(R.drawable.parking);
                    }else if(driveMode.equals("9")){
                        modeImage.setBackgroundResource(R.drawable.parking);
                    }else if(driveMode.equals("10")){
                        modeImage.setBackgroundResource(R.drawable.parking);
                    }else if(driveMode.equals("25")){
                        modeImage.setBackgroundResource(R.drawable.sidestand);
                    }else if(driveMode.equals("21")){
                        modeImage.setBackgroundResource(R.drawable.sidestand);
                    }else {
                        modeImage.setBackgroundResource(R.drawable.eco);
                    }
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.map){
                startActivity(new Intent(getActivity(), MapsActivity.class));
        }
    }
}