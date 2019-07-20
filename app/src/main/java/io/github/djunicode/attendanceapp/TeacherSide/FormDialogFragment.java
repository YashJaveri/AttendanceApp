package io.github.djunicode.attendanceapp.TeacherSide;

import static android.content.Context.MODE_PRIVATE;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import io.github.djunicode.attendanceapp.R;
import io.github.djunicode.attendanceapp.RetrofitInterface;
import io.github.djunicode.attendanceapp.TeacherSide.Models.WebDivAndSubjectsForForm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FormDialogFragment extends DialogFragment implements
        View.OnClickListener {

    private AppCompatAutoCompleteTextView yearSelect, subjectSelect, divisionSelect;
    private TextInputEditText startTime, endTime, roomNumber;
    private ImageButton saveDetails;
    private ArrayList<String> year = new ArrayList<>();
    private ArrayList<String> subject = new ArrayList<>();
    private ArrayList<String> division = new ArrayList<>();
    private boolean[] checks = new boolean[6];
    SharedPreferences spref;
    private int i;

    public interface OnDetailsSaved{
        void onDetailsSaved(String year, String subject, String startTime, String endTime, String roomNumber, String division);
    }

    private OnDetailsSaved mOnDetailsSaved;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_form_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spref = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://wizdem.pythonanywhere.com/Attendance/").addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        final Call<ArrayList<WebDivAndSubjectsForForm>> divAndSubForm=retrofitInterface.formSpinnerData("Token " + spref.getString("token", null));
        divAndSubForm.enqueue(new Callback<ArrayList<WebDivAndSubjectsForForm>>() {
            @Override
            public void onResponse(Call<ArrayList<WebDivAndSubjectsForForm>> call, Response<ArrayList<WebDivAndSubjectsForForm>> response) {
                ArrayList<WebDivAndSubjectsForForm>webDivAndSubjectsForForms=response.body();
                if(webDivAndSubjectsForForms!=null)
                {
                    if (webDivAndSubjectsForForms.size()!=0)
                    {

                        for(i = 0; i<webDivAndSubjectsForForms.size(); i++)
                        {//TODO:IF NO ATTENDANCE
                            year.add(webDivAndSubjectsForForms.get(i).getDiv().substring(0,2));
                            division.add(webDivAndSubjectsForForms.get(i).getDiv().substring(3));
                            subject.add(webDivAndSubjectsForForms.get(i).getSubject());
                        }
                        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(view.getContext(),R.layout.spinner_item,year);
                        yearSelect.setAdapter(yearAdapter);
                        ArrayAdapter<String> divAdapter = new ArrayAdapter<>(view.getContext(),R.layout.spinner_item, division);
                        divisionSelect.setAdapter(divAdapter);
                        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(view.getContext(),R.layout.spinner_item, subject);
                        subjectSelect.setAdapter(subjectAdapter);

                    }
                    else
                    {
                        Toast.makeText(view.getContext(),"Something went wrong. Please try again.",Toast.LENGTH_LONG).show();

                    }
                }
                else
                {
                    Toast.makeText(view.getContext(),"Something went wrong. Please try again.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<WebDivAndSubjectsForForm>> call, Throwable t) {

            }
        });
        yearSelect = view.findViewById(R.id.year_select);
        subjectSelect = view.findViewById(R.id.subject_select);
        divisionSelect = view.findViewById(R.id.division_select);


        startTime = view.findViewById(R.id.start_time_input);
        startTime.setFocusable(false);
        startTime.setOnClickListener(this);
        endTime = view.findViewById(R.id.end_time_input);
        endTime.setFocusable(false);
        endTime.setOnClickListener(this);

        roomNumber = view.findViewById(R.id.room_input);

        roomNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent) {
                if(actionID == EditorInfo.IME_ACTION_DONE) {
                    startTime.setClickable(true);
                    startTime.setFocusable(true);
                    startTime.setOnClickListener(FormDialogFragment.this);
                    return true;
                }
                return false;
            }
        });

        saveDetails = view.findViewById(R.id.save_details);
        saveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checks[0] = (yearSelect.getText() != null && yearSelect.getText().toString().trim().length() > 0);
                checks[1] = (divisionSelect.getText() != null && divisionSelect.getText().toString().trim().length() > 0);
                checks[2] = (subjectSelect.getText() != null && subjectSelect.getText().toString().trim().length() > 0);
                checks[3] = (roomNumber.getText() != null && roomNumber.getText().toString().trim().length() > 0);
                checks[4] = (startTime.getText() != null && startTime.getText().toString().trim().length() > 0);
                checks[5] = (endTime.getText() != null && endTime.getText().toString().trim().length() > 0);
                boolean finalCheck = true;

                for (i = 0; i < checks.length; ++i) {
                    finalCheck = finalCheck && checks[i];
                }

                if (finalCheck) {
                    mOnDetailsSaved.onDetailsSaved(yearSelect.getText().toString(),
                            subjectSelect.getText().toString(),
                            startTime.getText().toString(),
                            endTime.getText().toString(),roomNumber.getText().toString(),divisionSelect.getText().toString());

                    FormDialogFragment.this.dismiss();
                } else {
                    Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnDetailsSaved = (OnDetailsSaved) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.start_time_input || view.getId() == R.id.end_time_input) {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            final int minute = calendar.get(Calendar.MINUTE);
            TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                    StringBuffer result = new StringBuffer();
                    if (hour < 10)
                        result.append(0);
                    result.append(hour + ":");
                    if(minutes < 10)
                        result.append("0");
                    result.append(minutes);
                    ((TextInputEditText) view).setText(result.toString());
                }
            };
            TimePickerDialog dialog = new TimePickerDialog(getContext(), timeSetListener, hour, minute, true);
            dialog.setTitle("Select Time");
            dialog.show();

            if (view.getId() == R.id.start_time_input) {
                endTime.setFocusable(true);
                endTime.setClickable(true);
                endTime.setOnClickListener(FormDialogFragment.this);
            } else
                saveDetails.setEnabled(true);
        }
    }
}
