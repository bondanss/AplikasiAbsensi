package pnj.uas.bondanss.ui.absen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import pnj.uas.bondanss.R;
import pnj.uas.bondanss.obj.AbsensiObj;
import pnj.uas.bondanss.service.GPSTracker;

import static android.app.Activity.RESULT_OK;

public class AbsenFragment extends Fragment {
    Button BTNabsen, btnCamera, btnLocation;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String email;
    ImageView imageView;

    // === Komponen image ====
    private ImageButton mSelectImage;;
    private Button mSubmitBtn;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;
    private Uri mImageUri = null;
    private static final  int GALLERY_REQUEST = 1;
    private static final int CAMERA_REQUEST_CODE= 1;
    private StorageReference mStorage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_absen, container, false);

        //========= Ini image capture ========
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Absensi");

        mProgress = new ProgressDialog(getActivity());
        mProgress.setTitle("Mengabsen...");

        BTNabsen = root.findViewById(R.id.BTNabsen);
        btnCamera = root.findViewById(R.id.btnCamera);
        btnLocation = root.findViewById(R.id.btnLocation);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("absensi");
        user = mAuth.getInstance().getCurrentUser();

        if (user != null) {
            email = user.getEmail();
        }

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                }

            }
        });

        BTNabsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return root;
    }

    public void absen(String email, String tanggal, String waktu, String lokasi, String lat, String lng, String status, String gambar)
    {

        AbsensiObj absensiObj =  new AbsensiObj(email,tanggal,waktu,lokasi,lat,lng,status,gambar);
        myRef.push().setValue(absensiObj);

        mProgress.dismiss();
        Toast.makeText(getActivity(), "Test bisa", Toast.LENGTH_SHORT).show();

    }

    public String getAlamat(double lat, double lng)
    {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            String Alamat = knownName +", "+ state+", "+ postalCode + ", " + city+", "+ country;

            return Alamat;
        } catch (IOException e) {
            e.printStackTrace();
            return "Lokasi Tidak Terdeteksi";
        }

    }

    private void startPosting()
    {

        mProgress.show();
        UUID aneh = UUID.randomUUID();

        if(mImageUri != null)
        {
            final StorageReference filepath = mStorage.child("Absensi_Images").child(user.getEmail() +"/"+ aneh + mImageUri.getLastPathSegment());

            filepath.putFile(mImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> uploadTask) {

                    if (uploadTask.isSuccessful()){

                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException();
                                }

                                // Continue with the task to get the download URL
                                return filepath.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Uri downloadUri = task.getResult();
                                    String imageurl = downloadUri.toString();
                                    //sendfinal(imageurl);

                                    //get date now
                                    Date c = Calendar.getInstance().getTime();
                                    System.out.println("Current time => " + c);

                                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                                    String formattedDate = df.format(c);

                                    DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                                    String strDate = dateFormat.format(c);

                                    GPSTracker gp = new GPSTracker(getActivity());
                                    double lat = gp.getLatitude();
                                    double lng = gp.getLongitude();

                                    absen(email, formattedDate, strDate, getAlamat(lat,lng), String.valueOf(lat), String.valueOf(lng), "new", imageurl );

                                } else {
                                    // Handle failures
                                    // ...
                                }
                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Error :" + uploadTask.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }

                }
            });

        }
        else {
            Toast.makeText(getActivity(), "Kosong boy", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){

            mImageUri = data.getData();
            //mSelectImage.setImageURI(mImageUri);

            CropImage.activity(mImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(getContext(),this);


        /* Bitmap mImageUri1 = (Bitmap) data.getExtras().get("data");
         mSelectImage.setImageBitmap(mImageUri1);
          Toast.makeText(this, "Image saved to:\n" +
                  data.getExtras().get("data"), Toast.LENGTH_LONG).show();
*/


        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                //mSelectImage.setImageURI(resultUri);
                mImageUri = resultUri;

                if (mImageUri != null)
                {
                    startPosting();
                }
                else {
                    Toast.makeText(getActivity(), "Kosong pas dicrop", Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }


    }
}