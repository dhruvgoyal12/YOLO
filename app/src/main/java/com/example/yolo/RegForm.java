package com.example.yolo;

import android.app.ProgressDialog;
    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Patterns;
import android.view.View;

import android.widget.Toast;

import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.Response;
    import com.android.volley.Response.ErrorListener;
    import com.android.volley.VolleyError;
    import com.android.volley.toolbox.StringRequest;
    import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
    import com.google.android.material.textfield.TextInputLayout;

    import org.json.JSONException;
    import org.json.JSONObject;

    import java.util.HashMap;
    import java.util.Map;

    public class RegForm extends AppCompatActivity {
        @Nullable
        // Creating edittexts
        private TextInputLayout name, emailId, pass1;

        private MaterialButton btregister;

        // Creating progress dialog
        private ProgressDialog progressDialog ;


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            pass1 = findViewById(R.id.Passlay);
            name = findViewById(R.id.fullnamelay);
            emailId = findViewById(R.id.emaillay);
            btregister = findViewById(R.id.btnLinkToRegisterScreen);

            progressDialog = new ProgressDialog(RegForm.this);

            // Action to perform on click
            btregister.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    registerUser();
                }
            });




        }

        private void registerUser(){
            String http = "https://android1234321.000webhostapp.com/connect/file.php";

            final String name_s = name.getEditText().getText().toString().trim();
            final String email = emailId.getEditText().getText().toString().trim();
            final String pass= pass1.getEditText().getText().toString().trim();


            if (name_s.equals("") || email.equals("") || pass.equals("") ){
                Toast.makeText(getApplicationContext(), "Please fill all the fields.",Toast.LENGTH_LONG).show();
            }
            else if (Patterns.EMAIL_ADDRESS.matcher(email).matches() == false) {
                Toast.makeText(getApplicationContext(), "Email Address is of Invalid Format", Toast.LENGTH_LONG).show();

            }

            else{

                progressDialog.setMessage("Registering user...");
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, http ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try{

                                    JSONObject jsonObject = new JSONObject(response);

//                                    textv.setText(jsonObject.getString("message"));



                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"),Toast.LENGTH_LONG).show();


                                    if (jsonObject.getString("message").equals("Successful")) {
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                    }

                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }

                        },
                        new ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Connection Problem, Try Again", Toast.LENGTH_LONG).show();

                            }
                        }){
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String,String> params = new HashMap<>();

                        params.put("Name", name_s);
                        params.put("EmailId", email);
                        params.put("password", pass);

                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
                requestQueue.add(stringRequest);
            }}
    }






