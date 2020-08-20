package com.example.digibarterdesign;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.digibarterdesign.adapter.ProductImagesDataAdapters;
import com.example.digibarterdesign.helpers.SharedPreferenceHelper;
import com.example.digibarterdesign.helpers.VolleySingleton;
import com.example.digibarterdesign.model.Category;
import com.example.digibarterdesign.model.Product;
import com.example.digibarterdesign.model.ProductImages;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddProduct extends AppCompatActivity implements OnMapReadyCallback {
    Spinner spinnerCategory;
    ArrayList<Category> categories = new ArrayList<>();
    public static final int GALLERY_REQUEST_CODE = 101;
    ArrayList<ProductImages> productImages = new ArrayList<>();

    LocationManager locationManager;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);

        categories.add(new Category(1, "Electronics"));
        categories.add(new Category(2, "Cars and Vehicles"));
        updateSpinner();

        loadImagesView();
        findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addProduct();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            mMap.setMyLocationEnabled(true);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 20.0f));

            return;
        }
        mMap.setMyLocationEnabled(true);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 20.0f));

    }

    public void updateSpinner()
    {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getCategoryStringArray(categories));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(arrayAdapter);
    }
    ArrayList<String> getCategoryStringArray(ArrayList<Category> categories) {
        ArrayList<String> arrayList = new ArrayList<>();
        for(Category cat:categories)
        {
            arrayList.add(cat.name);
        }
        return arrayList;
    }

    void loadImagesView() {
        //https://javapapers.com/android/android-image-gallery-example-app-using-glide-library/

        findViewById(R.id.addImgBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFromGallery();
            }
        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewImgs);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 30);
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<ProductImages> imageUrlList = productImages;
        ProductImagesDataAdapters dataAdapter = new ProductImagesDataAdapters(AddProduct.this, imageUrlList);
        recyclerView.setAdapter(dataAdapter);
    }

    private void pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    uploadImageFile(getPath(selectedImage));
                    break;
            }

    }

    public boolean uploadImageFile(String imagePath) {

        byte[] audioBytes = getByteArr(imagePath);
        final String imageString = Base64.encodeToString(audioBytes, Base64.DEFAULT);
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Uploading the Image...");
        pDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, "http://alllinks.online/andproject/fileUpload.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    pDialog.hide();
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("status").equals("success")) {
                        Toast.makeText(AddProduct.this, "Uploaded Successful", Toast.LENGTH_LONG).show();

                        productImages.add(new ProductImages(-1,jsonObject.getString("path")));
                        loadImagesView();
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pDialog.hide();
                Toast.makeText(getApplicationContext(), "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
            }
        }) {
            //adding parameters to send
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("fileToUpload", imageString);
                parameters.put("type", "jpg");
                return parameters;
            }
        };

        request.setShouldCache(false);
        VolleySingleton.getInstance(this).addToRequestQueue(request);
        return true;
    }

    public void removeImageFromList(String removeKey) {
       for(int i=0;i<productImages.size();i++)
       {
           if(productImages.get(i).url.equals(removeKey))
           {
               productImages.remove(i);
           }
       }
        loadImagesView();
    }


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    byte[] getByteArr(String path) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));

            int read;
            byte[] buff = new byte[1024];
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
            out.flush();
            return out.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    private void getCategories() {
        String url = "http://alllinks.online/andproject/getNotesForUser.php";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Fetching Details...");
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.hide();
                        try {
                            if(response.getString("status").equals("success"))
                            {
                                // Toast.makeText(getApplicationContext(), response.toString(),Toast.LENGTH_LONG).show();
                                //updateSpinner();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Some Error occurred", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Sorry, Some error occured",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Sorry, Some error occured",Toast.LENGTH_LONG).show();
                // hide the progress dialog
                pDialog.hide();
            }
        });

        // Adding request to request queue
        jsonObjReq.setShouldCache(false);
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjReq);
    }

    public int getSelectedCatID(String val)
    {
        for(Category category: categories)
        {
            if(category.name.equals(val))
            {
                return category.id;
            }
        }
        return 1;
    }

    private void addProduct() throws JSONException {
        String url = "https://digi-barter.herokuapp.com/addProduct";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Uploading Ad...");
        pDialog.show();
        EditText title,desc;
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", title.getText().toString());
        jsonObject.put("description", desc.getText().toString());
        jsonObject.put("userId", SharedPreferenceHelper.getSharedPreferenceInt(getApplicationContext(),"id",0));
        jsonObject.put("userType", 0);
        jsonObject.put("categoryId",getSelectedCatID(categories.get(spinnerCategory.getSelectedItemPosition()).name));
        JSONArray jsonArray=new JSONArray();

        for (ProductImages pi:productImages)
        {
            jsonArray.put(new JSONObject().put("imageLink",pi.url));
        }
        jsonObject.put("images",jsonArray);
        Location newLocation = getLastBestLocation();
        jsonObject.put("lat",String.valueOf(newLocation.getLatitude()));
        jsonObject.put("longt",String.valueOf(newLocation.getLongitude()));
        final String requestBody = jsonObject.toString();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    pDialog.hide();
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getInt("userId") == SharedPreferenceHelper.getSharedPreferenceInt(getApplicationContext(),"id",0)) {
                        Toast.makeText(AddProduct.this, "Uploaded Successful", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(AddProduct.this, "Error Occurred", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pDialog.hide();
                Toast.makeText(getApplicationContext(), "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
            }
        }) {
            //adding parameters to send
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }


        };
        request.setShouldCache(false);
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    private boolean checkLocationPermission()
    {
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        int res = getApplicationContext().checkCallingOrSelfPermission(permission);
        return true;
    }
    private Location getLastBestLocation() {
        if (checkLocationPermission()){
            locationManager = (LocationManager)getSystemService
                    (Context.LOCATION_SERVICE);
            Location getLastLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            return getLastLocation;
        }
        Toast.makeText(getApplicationContext(), "location permission not granted",Toast.LENGTH_SHORT).show();
        return null;
    }

    }