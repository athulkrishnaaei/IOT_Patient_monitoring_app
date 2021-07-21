package com.examples.patientmonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.WallpaperInfo;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;
import static java.lang.Float.parseFloat;
import static java.nio.channels.Selector.open;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference tDatabase;
    private DatabaseReference hDatabase;
    private DatabaseReference spDatabase;

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    public static final String NOTIFICATION_CHANNEL_ID2 = "10002" ;
    public static final String NOTIFICATION_CHANNEL_ID3 = "10003" ;
    private final static String default_notification_channel_id = "default" ;
    private final static String default_notification_channel_id2 = "default2" ;
    private final static String default_notification_channel_id3 = "default3" ;
   // private DatabaseReference geturl;
    public Button btn;
    String na,ag,g,c;
    WebView we;
    //String url="http://192.168.18.158:8080";
   TextView ur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ur=findViewById(R.id.url);
        setContentView(R.layout.activity_main);
        /*String url=ur.getText().toString();
        we =findViewById(R.id.webb);
        we.setWebViewClient(new WebViewClient());
        we.getSettings().setJavaScriptEnabled(true);
        we.loadUrl(url);*/
        //Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+ getApplicationContext().getPackageName() + "/" + R.raw.temp);



        hDatabase = FirebaseDatabase.getInstance().getReference().child("Humidity");
        tDatabase = FirebaseDatabase.getInstance().getReference().child("Temperature");
        spDatabase = FirebaseDatabase.getInstance().getReference().child("SPO2");
       //FirebaseDatabase.getInstance().getReference().child("HR");
        btn =(Button) findViewById(R.id.button);
        TextView tempp = findViewById(R.id.temp);
        TextView humi = findViewById(R.id.hum);
        TextView sp = findViewById(R.id.sp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              openPatientreg();
            }
        });

        gettemp();//function to recieve temperature value from firebase
        gethum();//function to recieve humidity value from firebase
        //getsp();//function to recieve spo2 value from firebase
       getbp();
       geturl();//function to recieve heart rate value from firebase
        //bpDatabase.setValue("hello world");

        TextView nm=findViewById(R.id.p1);
        TextView agee=findViewById(R.id.a1);
        TextView gen=findViewById(R.id.s1);


        na=getIntent().getExtras().getString("1");
        ag=getIntent().getExtras().getString("2");
        g=getIntent().getExtras().getString("3");
        c=getIntent().getExtras().getString("4");
        nm.setText(na);
        agee.setText(ag);
        gen.setText(g);






    }
//
    public void gettemp() {
        tDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                TextView tempp;
                tempp = findViewById(R.id.temp);

                String value1 = snapshot.getValue(String.class);

               // String v1 = tempp.getText().toString();
                tempp.setText(value1);
                Float f= parseFloat(value1);

                if (f>37.5) {

                   //onsp();
                    ontemp();
                }
                // after getting the value we are setting
                // our value to our text view in below line.

            }


            @Override
            public void onCancelled( DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(MainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void gethum() {
        hDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                TextView humi;
                humi = findViewById(R.id.hum);
                String value2 = datasnapshot.getValue(String.class);

                // after getting the value we are setting
                // our value to our text view in below line.
                humi.setText(value2);
                //String v2=humi.getText().toString();
                float f;
                f = parseFloat(value2);
                if (f > 100) {


                    onhumi();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(MainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void getbp() {
        (FirebaseDatabase.getInstance().getReference().child("HR")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                TextView bpp = findViewById(R.id.hr);
                String value4 = snapshot.getValue(String.class);

                bpp.setText(value4);
                float f = parseFloat(value4);
                if (f > 100) {

                    onbp();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(MainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void geturl() {
        (FirebaseDatabase.getInstance().getReference().child("Url")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                TextView ur = findViewById(R.id.url);
                String value4 = snapshot.getValue(String.class);

                ur.setText(value4);
                String url=ur.getText().toString();
                we =findViewById(R.id.webb);
                we.setWebViewClient(new WebViewClient());
                we.getSettings().setJavaScriptEnabled(true);
                we.loadUrl(url);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(MainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void ontemp() {

        Context context = getApplicationContext();

        Uri sound1 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + R.raw.temp);
        NotificationManagerCompat manager = NotificationManagerCompat.from(MainActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My notification", "My notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager managerr = getSystemService(NotificationManager.class);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            channel.setSound(sound1, audioAttributes);
            managerr.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity. this, default_notification_channel_id )
                .setSmallIcon(R.drawable. ic_launcher_foreground )
                .setContentTitle( "Patient Monitoring App" )
                .setSound(sound1)
                .setContentText( na + " Temperature level is high");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE );
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes. CONTENT_TYPE_SONIFICATION )
                    .setUsage(AudioAttributes. USAGE_ALARM )
                    .build() ;
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            notificationChannel.enableLights( true ) ;
            notificationChannel.setLightColor(Color. RED ) ;
            notificationChannel.enableVibration( true ) ;
            notificationChannel.setVibrationPattern( new long []{ 100 , 200 , 300 , 400 , 500 , 400 , 300 , 200 , 400 }) ;
            notificationChannel.setSound(sound1 , audioAttributes) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () ,
                mBuilder.build()) ;
    }

    public void onhumi() {


        Context context = getApplicationContext();
        Uri sound2 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + R.raw.hum);
        NotificationManagerCompat manager = NotificationManagerCompat.from(MainActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My notification", "My notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager managerr = getSystemService(NotificationManager.class);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            channel.setSound(sound2, audioAttributes);
            managerr.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity. this, default_notification_channel_id2 )
                .setSmallIcon(R.drawable. ic_launcher_foreground )
                .setContentTitle( "Patient Monitoring App" )
                .setSound(sound2)
                .setContentText("Humidity level on " + na + " room is high");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE );
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes. CONTENT_TYPE_SONIFICATION )
                    .setUsage(AudioAttributes. USAGE_ALARM )
                    .build() ;
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID2 , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            notificationChannel.enableLights( true ) ;
            notificationChannel.setLightColor(Color. RED ) ;
            notificationChannel.enableVibration( true ) ;
            notificationChannel.setVibrationPattern( new long []{ 100 , 200 , 300 , 400 , 500 , 400 , 300 , 200 , 400 }) ;
            notificationChannel.setSound(sound2 , audioAttributes) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID2 ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () ,
                mBuilder.build()) ;
    }

    public void onsp() {
        Context context = getApplicationContext();
        Uri sound3 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + R.raw.temp);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity. this, default_notification_channel_id )
                .setSmallIcon(R.drawable. ic_launcher_foreground )
                .setContentTitle( "Patient Monitoring App" )
                .setSound(sound3)
                .setContentText( na+" Temperature level is high" ) ;
                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE );
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes. CONTENT_TYPE_SONIFICATION )
                    .setUsage(AudioAttributes. USAGE_ALARM )
                    .build() ;
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            notificationChannel.enableLights( true ) ;
            notificationChannel.setLightColor(Color. RED ) ;
            notificationChannel.enableVibration( true ) ;
            notificationChannel.setVibrationPattern( new long []{ 100 , 200 , 300 , 400 , 500 , 400 , 300 , 200 , 400 }) ;
            notificationChannel.setSound(sound3 , audioAttributes) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () ,
                mBuilder.build()) ;
    }

    public void onbp() {
        Context context = getApplicationContext();
        Uri sound4 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + R.raw.heart);
        NotificationManagerCompat manager = NotificationManagerCompat.from(MainActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My notification", "My notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager managerr = getSystemService(NotificationManager.class);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            channel.setSound(sound4, audioAttributes);
            managerr.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity. this, default_notification_channel_id3 )
                .setSmallIcon(R.drawable. ic_launcher_foreground )
                .setContentTitle( "Patient Monitoring App" )
                .setSound(sound4)
                .setContentText( na+" Heart rate is critical" ) ;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE );
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes. CONTENT_TYPE_SONIFICATION )
                    .setUsage(AudioAttributes. USAGE_ALARM )
                    .build() ;
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID3 , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            notificationChannel.enableLights( true ) ;
            notificationChannel.setLightColor(Color. RED ) ;
            notificationChannel.enableVibration( true ) ;
            notificationChannel.setVibrationPattern( new long []{ 100 , 200 , 300 , 400 , 500 , 400 , 300 , 200 , 400 }) ;
            notificationChannel.setSound(sound4 , audioAttributes) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID3 ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () ,
                mBuilder.build()) ;
    }
    public void openPatientreg() {
        //inter.show();
        Intent intent = new Intent(MainActivity.this, mainpage.class);
        startActivity(intent);
    }
}

