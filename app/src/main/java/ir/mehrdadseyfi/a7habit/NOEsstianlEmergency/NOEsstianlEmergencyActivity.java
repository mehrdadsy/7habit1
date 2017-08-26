package ir.mehrdadseyfi.a7habit.NOEsstianlEmergency;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ir.mehrdadseyfi.a7habit.AlarmSoundService;
import ir.mehrdadseyfi.a7habit.JalaliCalendar;
import ir.mehrdadseyfi.a7habit.NOEmergencyNoEsstial.NOEsstianlEmergencyMyReceiver;
import ir.mehrdadseyfi.a7habit.R;

public class NOEsstianlEmergencyActivity extends AppCompatActivity {
    NOEsstianlEmergencylItem item;
    ListView LV;
    List<NOEsstianlEmergencylItem> models;
    Context mContext = this;
    int postionAlert;
    int i = 0;
    Date date2;
    ImageView imgBackGround;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_nessntial);


        LV = (ListView) findViewById(R.id.mylist_ee);

        AddItemEE();


//        if (models.size()==0) {
//            final int sdk = android.os.Build.VERSION.SDK_INT;
//            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                back_ground.setBackgroundDrawable( getResources().getDrawable(R.drawable.ic_launcher) );
//            } else {
//                back_ground.setBackground( getResources().getDrawable(R.drawable.ic_launcher));
//            }
//        }


        ImageView img_toolbar = (ImageView) findViewById(R.id.del);
        img_toolbar.setImageResource(R.drawable.ic_note_add_black_48dp);
        img_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NOEsstianlEmergencyActivity.this, NOEsstianlEmergencyDialogActivity.class));
            }
        });
        ImageView img_toolbar_sound = (ImageView) findViewById(R.id.sound);
        img_toolbar_sound.setImageResource(R.drawable.ic_volume_off_black_48dp);
        img_toolbar_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(NOEsstianlEmergencyActivity.this, AlarmSoundService.class));
            }
        });
        LV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                postionAlert = position;
                AlertPopup();
                return true;
            }
        });
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NOEsstianlEmergencyActivity.this, EmergencyNOEssntialShowEditActivity.class);
                intent.putExtra("id", models.get(position).getId());
                startActivity(intent);


            }
        });
    }

    public void AddItemEE() {
        models = NOEsstianlEmergencylItem.listAll(NOEsstianlEmergencylItem.class);
        NOEsstianlEmergencyListAdapter adpter = new NOEsstianlEmergencyListAdapter(models, mContext);
        i = models.size();
        BackGroundIf();
        alarmManager();


        try {
            LV.setAdapter(adpter);


        } catch (Exception e) {

        }
    }

    @Override
    protected void onResume() {
        AddItemEE();
        super.onResume();
    }

    @Override
    protected void onStart() {
        AddItemEE();
        super.onStart();
    }

    public void AlertPopup() {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();

//tiltle
        alertDialog.setTitle("هشدار");

//maten dialog
        alertDialog.setMessage("آیا از حذف کار خود مطمئن هستید؟");

//dokme ---mitoni ino hey copy koni va  BUTTON_NEUTRAL ino avaz koni dokme jadid biari va ye cari behesh nesbat bedi
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",

                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        NOEsstianlEmergencylItem books = NOEsstianlEmergencylItem.findById(NOEsstianlEmergencylItem.class, models.get(postionAlert).getId());
                        books.delete();
                        Toast.makeText(NOEsstianlEmergencyActivity.this, "حذف کار انجام شد", Toast.LENGTH_SHORT).show();
                        AddItemEE();

                    }

                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }

                });


        alertDialog.show();


    }
//image backgroun ba if
    public void BackGroundIf() {
        imgBackGround = (ImageView) findViewById(R.id.img);

        if (i == 0) {
            imgBackGround.setImageResource(R.drawable.noting);

        } else {

            imgBackGround.setImageDrawable(null);
        }
    }
//lalrm with models
    public void alarmManager() {

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

        for (int j = 0; j < i; j++) {
            if (Integer.parseInt(models.get(j).getCalenderYear()) > 0) {
                int years = Integer.parseInt(models.get(j).getCalenderYear());
                int mounth = Integer.parseInt(models.get(j).getCalenderMount());
                int day = Integer.parseInt(models.get(j).getCalenderday());
                int hours = Integer.parseInt(models.get(j).getHours());
                int min = Integer.parseInt(models.get(j).getMinutes());
                int mounth_A = JalaliCalendar.jalaliToGregorian(new JalaliCalendar.YearMonthDate(years, mounth, day)).getMonth();
                int year_A = JalaliCalendar.jalaliToGregorian(new JalaliCalendar.YearMonthDate(years, mounth, day)).getYear();
                int day_A = JalaliCalendar.jalaliToGregorian(new JalaliCalendar.YearMonthDate(years, mounth, day)).getDate();

                Calendar cal = Calendar.getInstance();
                Calendar cal1 = Calendar.getInstance();
                cal1.set(year_A, mounth_A - 1, day_A, hours, min, 30);

                cal.setTimeInMillis(System.currentTimeMillis());
                long diff = (cal1.getTimeInMillis() - cal.getTimeInMillis());

                if (diff > 0) {
                    Intent intent = new Intent(NOEsstianlEmergencyActivity.this, NOEsstianlEmergencyMyReceiver.class);
                    PendingIntent pi = PendingIntent.getBroadcast(NOEsstianlEmergencyActivity.this, j, intent, 0);
                    am.set(AlarmManager.RTC_WAKEUP, cal1.getTimeInMillis(), pi);
                }
            }
        }
    }

}