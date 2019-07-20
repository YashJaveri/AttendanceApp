package io.github.djunicode.attendanceapp.StudentSide;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import io.github.djunicode.attendanceapp.R;
import io.github.djunicode.attendanceapp.StudentSide.Adapters.DaywiseDetailsAdapter;
import io.github.djunicode.attendanceapp.StudentSide.Models.DaywiseDetailsModel;


public class DaywiseDetails extends AppCompatActivity {

    DaywiseDetailsAdapter daywiseDetailsAdapter;
    ArrayList<DaywiseDetailsModel> daywiseDetailsModels;
    ListView daywiseDetailsView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        daywiseDetailsModels = new ArrayList<>();
        setContentView(R.layout.datewise_details);
        daywiseDetailsView = findViewById(R.id.list_daywise_details);

        //Test:
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        DaywiseDetailsModel daywiseDetailsModel = new DaywiseDetailsModel(simpleDateFormat.format(new Date()), "14:00-15:00", true);
        daywiseDetailsModels.add(daywiseDetailsModel);
        daywiseDetailsModel = new DaywiseDetailsModel(simpleDateFormat.format(new Date()), "16:30-17:30", false);
        daywiseDetailsModels.add(daywiseDetailsModel);

        daywiseDetailsAdapter = new DaywiseDetailsAdapter(DaywiseDetails.this, daywiseDetailsModels);
        daywiseDetailsView.setAdapter(daywiseDetailsAdapter);
    }
}