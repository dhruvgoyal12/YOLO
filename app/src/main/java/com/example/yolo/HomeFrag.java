package com.example.yolo;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

class DatePickerFragment extends DialogFragment {
    DatePickerDialog.OnDateSetListener ondateSet;
    private int year, month, day;

    public DatePickerFragment() {}

    public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
        ondateSet = ondate;
    }

    @SuppressLint("NewApi")
    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
    }
}

public class HomeFrag extends Fragment
{
    @Nullable
    private DatePickerFragment picker;
    private Spinner mem1,tor;
    private EditText etarr,etdep;
    private Button buknow;
    private ProgressDialog pd;
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);


        mem1=view.findViewById(R.id.mem);
        tor = view.findViewById(R.id.tor);
        buknow = view.findViewById(R.id.btbook);
        etarr = view.findViewById(R.id.datearr);
        etdep = view.findViewById(R.id.datedep);
        etarr.setInputType(InputType.TYPE_NULL);
        etdep.setInputType(InputType.TYPE_NULL);
        etarr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment date = new DatePickerFragment();
                /**
                 * Set Up Current Date Into dialog
                 */
                Calendar calender = Calendar.getInstance();
                Bundle args = new Bundle();
                args.putInt("year", calender.get(Calendar.YEAR));
                args.putInt("month", calender.get(Calendar.MONTH));
                args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
                date.setArguments(args);
                /**
                 * Set Call back to capture selected date
                 */
                date.setCallBack(ondate);
                date.show(getFragmentManager(), "Date Picker");
            }
            DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {

                    etarr.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1)
                            + "-" + String.valueOf(year));

                }
            };
        });

        etdep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment date = new DatePickerFragment();
                /**
                 * Set Up Current Date Into dialog
                 */
                Calendar calender = Calendar.getInstance();
                Bundle args = new Bundle();
                args.putInt("year", calender.get(Calendar.YEAR));
                args.putInt("month", calender.get(Calendar.MONTH));
                args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
                date.setArguments(args);
                /**
                 * Set Call back to capture selected date
                 */
                date.setCallBack(ondate);
                date.show(getFragmentManager(), "Date Picker");
            }
            DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {

                    etdep.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1)
                            + "-" + String.valueOf(year));

                }
            };
        });



        buknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });




        return view;
    }

    public void openActivity(){
        Intent intent = new Intent(getActivity(), Booked.class) ;
        startActivity(intent);
    }

    private void registerUser(){
        String http = "https://android1234321.000webhostapp.com/connect/file.php";
        final String ard = etarr.getText().toString().trim();

        final String ded = etdep.getText().toString().trim();
        final String memb = mem1.getSelectedItem().toString().trim();
        final String typeofroom = tor.getSelectedItem().toString().trim();

        if (ded.equals("") || ard.equals("") || memb.equals("") || typeofroom.equals("")){
            Toast.makeText(getActivity(), "Please fill all the fields.", Toast.LENGTH_LONG).show();
        }

        else{

            pd.setMessage("Registering user...");
            pd.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, http ,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            try{

                                JSONObject jsonObject = new JSONObject(response);


                                //Toast.makeText(getActivity().getApplicationContext(), jsonObject.getString("message"),Toast.LENGTH_LONG).show();


                           if (jsonObject.getString("message").equals("Successful"))
                           {
                               Intent intent = new Intent(getActivity(),Booked.class) ;
                               startActivity(intent);
                           }

                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pd.dismiss();
                            Toast.makeText(getActivity(), "Connection Problem, Try Again", Toast.LENGTH_LONG).show();

                        }
                    }){
                @Override
                protected Map<String, String> getParams(){
                    Map<String,String> params = new HashMap<>();
                    params.put("Arrival Date",ard );
                    params.put("Departure Date", ded);
                    params.put("Members", memb);
                    params.put("Type of room", typeofroom);

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            requestQueue.add(stringRequest);
        }}
    }


