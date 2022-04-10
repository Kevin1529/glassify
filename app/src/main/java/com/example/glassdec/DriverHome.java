package com.example.glassdec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DriverHome extends AppCompatActivity {

    EditText etAddWeight;
    Button submit,comp_task,start_trip;
    String sDuty;
    boolean bDuty;
    Retrive_User_Request userRequest;
    String driver;
    String strAdd,us_Name;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    private static final String TAG = "MainActivity";
    int location_request_code = 10001;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    public String driverLocation;
    public static double driverLocation1;
    public static double driverLocation2;
    TextView txtLoc;

    //to store user location
    String us_Loc;

    // Widgets for user details
    TextView userName,userAdd,mobile;

    //to get data from task
    DatabaseReference ref;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth auth;
    String dname,dn;

//   LocationCallback locationCallback;

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {

            if (locationResult == null)
            {
                return;
            }

            for (Location location : locationResult.getLocations()) {
                Log.d(TAG, "onLocationResult: " + location.toString());
                Log.d(TAG, " " + location.toString());
                Log.d(TAG, " " + location.getLatitude());
                Log.d(TAG, " " + location.getLongitude());
                driverLocation1 = location.getLatitude();
                driverLocation2 = location.getLongitude();
                driverLocation = String.valueOf(driverLocation1) + "," + driverLocation2;

                String dloc = driverLocation;

                getCompleteAddressString(driverLocation1,driverLocation2);

//                Toast.makeText(getApplicationContext(), "LOCATION DRI IS: "+driverLocation, Toast.LENGTH_SHORT).show();

//                Toast.makeText(getApplicationContext(), "dn "+dn, Toast.LENGTH_SHORT).show();

                    DriverDetails driverDetails = new DriverDetails(strAdd);

                    FirebaseDatabase.getInstance().getReference("Drivers").child(dn).child("Driver Loc").setValue(driverDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
//
//                            Toast.makeText(getApplicationContext(), "Data Submitted Successfully", Toast.LENGTH_SHORT).show();
//
                            }
                        }
                    });

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);


        mAuth=FirebaseAuth.getInstance();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(2000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        submit=findViewById(R.id.submit);
        txtLoc=findViewById(R.id.loc);

        //connecting to user widgets

        userName = findViewById(R.id.username);
        userAdd=findViewById(R.id.useraddress);
        mobile=findViewById(R.id.mob);
        comp_task=findViewById(R.id.comptask);
        etAddWeight=findViewById(R.id.weight);
        start_trip = findViewById(R.id.start);

        auth = FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseUser currentUser=auth.getCurrentUser();
                if(currentUser!=null)
                {
                    start_trip.setEnabled(true);
                    ref = FirebaseDatabase.getInstance().getReference("Users");

                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String uid = user.getUid();
                             String driver_name = snapshot.child(uid).child("name").getValue(String.class);
                            checkDriverInTask(driver_name);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });

        comp_task.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String weight = etAddWeight.getText().toString();
                String un = userName.getText().toString();

                if (weight.isEmpty()) {
                    etAddWeight.setError("Pleas Enter Weight");
                    etAddWeight.requestFocus();
                    return;
                } else {

                    CompleteTask completeTask = new CompleteTask(dn, un, weight);
//                Toast.makeText(getApplicationContext(), "driver "+dn, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "usname "+un, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "weight "+weight, Toast.LENGTH_SHORT).show();
                    FirebaseDatabase.getInstance().getReference("Completed Task").child(un).setValue(completeTask).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Task Completed Successfully", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });


        start_trip.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
//                us_Loc= userRequest.getuLocation().toString();
            //    Toast.makeText(getApplicationContext(), ""+us_Loc, Toast.LENGTH_SHORT).show();

                DisplayTrack(us_Loc);
                etAddWeight.setEnabled(true);
                comp_task.setEnabled(true);
                start_trip.setEnabled(false);
            }
        });
    }

    private void DisplayTrack(String sDestination)
    {
        try
        {
            Uri uri=Uri.parse("https://www.google.co.in/maps/dir/"+"/"+sDestination);
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        catch (ActivityNotFoundException e)
        {
            Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    public void checkDriverInTask(String name){
        com.example.glassdec.Task task_to_compare = new com.example.glassdec.Task();
        DatabaseReference reference;
        FirebaseDatabase database;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Task");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    driver = Objects.requireNonNull(ds.getValue(com.example.glassdec.Task.class)).getDriverName();
                    us_Name = Objects.requireNonNull(ds.getValue(com.example.glassdec.Task.class)).getUserName();
                    String us_Add = Objects.requireNonNull(ds.getValue(com.example.glassdec.Task.class)).getAddress();
                    String us_Mob = Objects.requireNonNull(ds.getValue(com.example.glassdec.Task.class)).getPhone();

                    if(name.equals(driver)){
                        userName.setText("Username:- "+us_Name);
                        userAdd.setText("User Address:- "+us_Add);
                        mobile.setText("User Mobile No:- "+us_Mob);
                        us_Loc = Objects.requireNonNull(ds.getValue(com.example.glassdec.Task.class)).getLocation();
                      //  Toast.makeText(getApplicationContext(), "usloc "+us_Loc, Toast.LENGTH_SHORT).show();

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    protected void onStart()
    {
        super.onStart();
        FirebaseUser currentUser=auth.getCurrentUser();
        if(currentUser!=null)
        {
            ref = FirebaseDatabase.getInstance().getReference("Users");

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    String uid = user.getUid();
                    String chooseAc = snapshot.child(uid).child("chooseAcc").getValue(String.class);
                  //  Toast.makeText(getApplicationContext(), chooseAc, Toast.LENGTH_SHORT).show();
                    if (chooseAc.equals("Driver"))
                    {
                        dn = snapshot.child(uid).child("name").getValue(String.class);
//                        Toast.makeText(getApplicationContext(), "dname "+dn, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //getLastLocation();
            checkSettingsAndStartLocationUpdates();


        } else {
            askLocationPermission();

        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    private void checkSettingsAndStartLocationUpdates() {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(this);

        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(locationSettingsResponse -> {
            //settings of devices are satistied ready to get location updates
            startLocationUpdates();

        });
        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(DriverHome.this, 1001);
                    } catch (IntentSender.SendIntentException sendIntentException) {
                        sendIntentException.printStackTrace();
                    }
                }
            }
        });
    }


    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,Looper.getMainLooper()) ;

    }
    private void stopLocationUpdates()
    {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
    //sddsd

    private PendingIntent getPendingIntent()
    {
        Intent intent = new Intent(this,MyLocationService.class);
        intent.setAction(MyLocationService.ACTION_PROCESS_UPDATE);
        return PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }
    public void updateTextView(final  String value)
    {
        DriverHome.this.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                txtLoc.setText(value);
            }
        });
    }

    public void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();

        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null)
                {

                    Log.d(TAG, "onSuccess: " + location.toString());
                    Log.d(TAG, "onSuccess: " + location.getLatitude());
                    Log.d(TAG, "onSuccess: " + location.getLongitude());
                    driverLocation1=location.getLatitude();
                    driverLocation2=location.getLongitude();
                    driverLocation=String.valueOf(driverLocation1)+","+driverLocation2;


                    System.out.println(driverLocation);
//                    Toast.makeText(getApplicationContext(), "LOCAG IS: "+driverLocation, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.d(TAG, "onSuccess: LOCATION WAS NULL");
                }

            }
        });
        locationTask.addOnFailureListener(e -> Log.e(TAG, "onFailure: "+e.getLocalizedMessage()));
    }
    public void askLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)){
                Log.d(TAG, "askLocationPermission: SHOW ALERT");
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, location_request_code);
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},location_request_code);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, location_request_code);
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},location_request_code);
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==location_request_code)
        {
            if(grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                // getLastLocation();
                checkSettingsAndStartLocationUpdates();
            }
            else
            {

            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.logoutmenu:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DriverHome.this,Login.class));
                finish();
                break;
        }
        return true;

    }
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
         strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("Current loction address", strReturnedAddress.toString());
                txtLoc.setText(strAdd);
              //  Toast.makeText(getApplicationContext(), strAdd, Toast.LENGTH_LONG).show();

            } else {
                Log.w("Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Current loction address", "Cannot get Address!");
        }
        return strAdd;
    }
}