package com.example.assessment_2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assessment_2.R;
import com.example.assessment_2.model.MotorItem;

public class BikeDetailActivity extends Activity {
    private ImageView btn_back;
    private ImageView bikePic;
    private RecyclerView bikeRecyclerView;
    private Button btn_sort_by_price;
    private int position;

    private boolean isReverse = false;
    private ImageView iv_bike;
    private TextView tv_bike_name;

    private TextView tvHorsePowerValue;
    private TextView tvTorqueValue;
    private TextView tvWeightValue;
    private TextView tvDisplacementValue;
    private TextView tvTankVolumeValue;
    private MotorItem motorItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_bike_detail);

        btn_back = findViewById(R.id.iv_go_back);

        iv_bike = findViewById(R.id.iv_bike);
        tv_bike_name = findViewById(R.id.tv_bike_name);

        motorItem = (MotorItem) getIntent().getExtras().getSerializable("MotorItem");

        tvHorsePowerValue = findViewById(R.id.tv_value_horsePower);
        tvTorqueValue = findViewById(R.id.tv_value_torque);
        tvWeightValue = findViewById(R.id.tv_value_weight);
        tvDisplacementValue = findViewById(R.id.tv_value_displacement);
        tvTankVolumeValue = findViewById(R.id.tv_value_tankVolume);

        tvHorsePowerValue.setText(""+motorItem.getHorsePower());
        tvTorqueValue.setText(""+motorItem.getTorque());
        tvWeightValue.setText(""+motorItem.getWeight());
        tvDisplacementValue.setText(""+motorItem.getDisplacement());
        tvTankVolumeValue.setText(""+motorItem.getTankVolume());

        iv_bike.setImageResource(motorItem.getPicRes());
        tv_bike_name.setText(motorItem.getName());

        initBackClickListener();
    }

    private void initBackClickListener() {
        btn_back.setOnClickListener(view -> {
            finish();
        });
    }

}
