package com.mobile.mvpwithdatabinding.notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mobile.mvpwithdatabinding.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class NotificationsActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, DatePickerDialog.OnDateSetListener {

    private Button btnShowNotification;
    private Button btnClearNotification;
    private SwitchCompat swEnableSound;
    private SwitchCompat swEnableExpandable;
    private SwitchCompat swEnableMultiple;
    private SwitchCompat swEnableSchedule;
    private TextView tvDate;
    private TextView tvClearDate;
    private TextView tvTime;
    private TextView tvClearTime;
    private EditText edtMessage;
    private boolean isEnableSound;
    private boolean isEnabledExpand;
    private boolean isEnabledMultiple;
    private LinearLayout layoutScheduler;
    public static String APP_NAME = "NotificationApp";
    private NotificationManager notificationManager;
    private int selectedMonth, selectedYear, selectedDay = 0;
    private boolean isSelectTime = false;
    private boolean isSelectDate = false;
    private Calendar todayCalender;
    private Calendar selectedDate;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        init();
    }

    private void init() {
        //Create Channel
        createNotificationChannel();
        btnShowNotification = findViewById(R.id.btn_show_notification);
        btnClearNotification = findViewById(R.id.btn_clear_notification);
        swEnableExpandable = findViewById(R.id.sw_enable_expand);
        swEnableMultiple = findViewById(R.id.sw_enable_multiple);
        swEnableSound = findViewById(R.id.sw_enable_sound);
        swEnableSchedule = findViewById(R.id.sw_schedule);
        layoutScheduler = findViewById(R.id.layout_scheduler);
        tvDate = findViewById(R.id.tv_date);
        tvClearDate = findViewById(R.id.tv_clear_date);
        tvTime = findViewById(R.id.tv_time);
        tvClearTime = findViewById(R.id.tv_clear_time);
        edtMessage = findViewById(R.id.edt_message);
        //Listeners
        btnShowNotification.setOnClickListener(this);
        btnClearNotification.setOnClickListener(this);
        tvClearDate.setOnClickListener(this);
        tvClearTime.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        tvTime.setOnClickListener(this);
        swEnableSound.setOnCheckedChangeListener(this);
        swEnableExpandable.setOnCheckedChangeListener(this);
        swEnableMultiple.setOnCheckedChangeListener(this);
        swEnableSchedule.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show_notification:
                showNotification();
                break;
            case R.id.btn_clear_notification:
                clearNotification();
                break;
            case R.id.tv_date:
                selectDate();
                break;
            case R.id.tv_time:
                selectTime();
                break;
            case R.id.tv_clear_date:
                tvDate.setText("Pick Date");
                isSelectDate = false;
                break;
            case R.id.tv_clear_time:
                tvTime.setText("Pick Time");
                isSelectTime = false;
                break;
            default:
                break;
        }
    }

    private void selectDate() {
        todayCalender = Calendar.getInstance();
        mYear = todayCalender.get(Calendar.YEAR);
        mMonth = todayCalender.get(Calendar.MONTH);
        mDay = todayCalender.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, this, mYear, mMonth, mDay);
        dialog.show();
    }


    public boolean isValidDate(String d1, String d2)   {
        SimpleDateFormat dfDate  = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        try {
            return dfDate.parse(d1).before(dfDate.parse(d2)) || dfDate.parse(d1).equals(dfDate.parse(d2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void selectTime() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        selectedDate = Calendar.getInstance();
                        selectedDate.set(Calendar.YEAR, selectedYear);
                        selectedDate.set(Calendar.MONTH, selectedMonth);
                        selectedDate.set(Calendar.DAY_OF_MONTH, selectedDay);
                        selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedDate.set(Calendar.MINUTE, minute);

                        Date date2 = new Date(selectedDate.getTimeInMillis());
                        Date current = new Date(c.getTimeInMillis());

                        if (current.after(date2)) {
                            Toast.makeText(NotificationsActivity.this, "Wrong time selected. Please verify!", Toast.LENGTH_SHORT).show();
                            tvTime.setText("Pick Time");
                            isSelectTime = false;
                        } else {
                            tvTime.setText(String.format("%s:%s", hourOfDay, minute));
                            isSelectTime = true;
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void clearNotification() {
        notificationManager.cancelAll();
        tvTime.setText("Pick Time");
        tvDate.setText("Pick Date");
        edtMessage.setText("");
        swEnableExpandable.setChecked(false);
        swEnableMultiple.setChecked(false);
        swEnableSound.setChecked(false);
        swEnableSchedule.setChecked(false);
        isSelectDate = false;
        isSelectTime = false;
    }

    private void showNotification() {
        if (TextUtils.isEmpty(edtMessage.getText())) {
            edtMessage.setError("Please enter Message!");
            return;
        }
        if(swEnableSchedule.isChecked()) {
            if (isSelectDate && isSelectTime) {
                long diffInMs = selectedDate.getTimeInMillis() - todayCalender.getTimeInMillis();
                long diffInSec = TimeUnit.MILLISECONDS.toMillis(diffInMs);
                scheduleNotification(this, diffInSec, "SCHEDULED NOTIFICATION.", edtMessage.getText().toString());
            } else {
                Toast.makeText(this, "Select a valid date and time!", Toast.LENGTH_SHORT).show();
            }
        } else {
            scheduleNotification(this, 0, "NORMAL NOTIFICATION", edtMessage.getText().toString());
        }
    }

    private void createNotificationChannel() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_name";
            String description = "channel_description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(APP_NAME, name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        switch (compoundButton.getId()) {
            case R.id.sw_enable_sound:
                isEnableSound = checked;
                break;
            case R.id.sw_enable_expand:
                isEnabledExpand = checked;
                break;
            case R.id.sw_enable_multiple:
                isEnabledMultiple = checked;
                break;
            case R.id.sw_schedule:
                layoutScheduler.setVisibility(checked ? View.VISIBLE : View.GONE);
                btnShowNotification.setText(checked ? "Schedule" : "Show Notification");
                break;
            default:
                break;
        }
    }

    public void scheduleNotification(Context context, long delay, String title, String message) {
        //delay is after how much time(in millis) from current time you want to schedule the notification

        int randomNotificationId = isEnabledMultiple ? (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE) : 0;

        Intent notificationIntent = new Intent(context, MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, randomNotificationId);
        notificationIntent.putExtra(MyNotificationPublisher.KEY_EXPAND, isEnabledExpand);
        notificationIntent.putExtra(MyNotificationPublisher.KEY_MULTIPLE, isEnabledMultiple);
        notificationIntent.putExtra(MyNotificationPublisher.KEY_SOUND, isEnableSound);
        notificationIntent.putExtra(MyNotificationPublisher.KEY_MESSAGE, message);
        notificationIntent.putExtra(MyNotificationPublisher.KEY_TITLE, title);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, randomNotificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        selectedMonth = month;
        selectedYear = year;
        selectedDay = dayOfMonth;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        if(isValidDate(todayCalender.getTime().toString(), cal.getTime().toString())) {
            tvDate.setText(String.format("%s - %s - %s", dayOfMonth, month+1, year));
            isSelectDate = true;
            selectTime();
        } else {
            isSelectDate = false;
            Toast.makeText(this, "Select Valid Date !", Toast.LENGTH_SHORT).show();
        }
    }
}

