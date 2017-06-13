package com.graduate.gaomingzhu.planequery.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.graduate.gaomingzhu.planequery.Adapter.FlightAdapter;
import com.graduate.gaomingzhu.planequery.R;
import com.graduate.gaomingzhu.planequery.model.Flight;
import com.graduate.gaomingzhu.planequery.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    /*
    * 城市选择spinner
    * */
    private Spinner startStation;
    private Spinner endStation;
    private ArrayAdapter<CharSequence> startAdapter = null;
    private ArrayAdapter<CharSequence> endAdapter = null;
    private FlightAdapter adapter;
    private Boolean isSelecte1=true;//false：未选择起点
    private Boolean isSelecte2=true;//false：未选择终点
    /*航班信息查询实例*/
    private Button query;
    private Flight flight = new Flight();
    private BmobDate startDate;
    private BmobDate endDate;

    private String start;
    private String end;

    /*日期选择*/
    private ImageView calendar;
    private TextView y;
    private TextView m;
    private TextView d;

    /*航班查询结果显示ListView*/
    private ListView showFlighInfo;
    //private List<Flight> flightList = new ArrayList<Flight>();

    /*底部导航信息*/
    private RadioButton radio_login;
    private RadioButton radio_order;
    private RadioButton radio_myInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // shujuku();

        init();
        //final Handler h = new Handler();
        startStation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                start = parent.getSelectedItem().toString();
                flight.setStartCity(start);
                //Toast.makeText(MainActivity.this,flight.getStartCity().toString(), Toast.LENGTH_SHORT).show();
                /*System.out.println("ok2______");
                Log.i(city,""+city);*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                isSelecte1=false;
            }
        });
        endStation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                end = parent.getSelectedItem().toString();
                flight.setEndCity(end);
                //  Toast.makeText(MainActivity.this,flight.getEndCity().toString(), Toast.LENGTH_SHORT).show();
/*                 System.out.println("ok2______");
                 String city=parent.getSelectedItem().toString();
                 Log.i(city,""+city);*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                isSelecte2=false;
            }
        });

        //监听ImageView
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(y, m, d);
            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer ye = Integer.parseInt(y.getText().toString());
                //Toast.makeText(MainActivity.this,"textview***"+ye,Toast.LENGTH_LONG).show();
                Integer mo = Integer.parseInt(m.getText().toString());
                Integer da = Integer.parseInt(d.getText().toString());
                /*按起点和终点查询成功
                QueryResult();*/
                if(isSelecte1&&isSelecte2){
                    queryRequset(ye, mo, da);
                }else if(isSelecte1==false){
                    Toast.makeText(MainActivity.this,"请选择起飞城市",Toast.LENGTH_SHORT).show();
                }else if(isSelecte2==false){
                    Toast.makeText(MainActivity.this,"请选择达到城市",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void init() {
        //城市选择
        startStation = (Spinner) findViewById(R.id.spinner1);
        endStation = (Spinner) findViewById(R.id.spinner2);
        startAdapter = ArrayAdapter.createFromResource(this, R.array.city, android.R.layout.simple_spinner_item);
        startAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        endAdapter = ArrayAdapter.createFromResource(this, R.array.city, android.R.layout.simple_spinner_item);
        endAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        startStation.setAdapter(startAdapter);
        endStation.setAdapter(endAdapter);
        //日历
        calendar = (ImageView) findViewById(R.id.calendar);
        y = (TextView) findViewById(R.id.year);
        m = (TextView) findViewById(R.id.month);
        d = (TextView) findViewById(R.id.day);
        Calendar c = Calendar.getInstance();
        y.setText(c.get(Calendar.YEAR) + "");
        m.setText((c.get(Calendar.MONTH) + 1) + "");
        d.setText(c.get(Calendar.DAY_OF_MONTH) + "");


        //查询
        query = (Button) findViewById(R.id.query);
        //show flight info
        //showFlighInfo= (ListView) findViewById(R.id.listOfFlight);

        /*底部导航控件初始化*/
        radio_login = (RadioButton) findViewById(R.id.radio_login);
        radio_order = (RadioButton) findViewById(R.id.radio_order);
        radio_myInfo = (RadioButton) findViewById(R.id.radio_myInfo);
        radio_login.setOnClickListener(MainActivity.this);
        radio_order.setOnClickListener(MainActivity.this);
        radio_myInfo.setOnClickListener(MainActivity.this);

        //ListView
        showFlighInfo = (ListView) findViewById(R.id.listOfFlight);
        showFlighInfo.setOnItemClickListener(this);
    }

    private void showDatePicker(final TextView ye, final TextView mo, final TextView da) {
        View view = View.inflate(getApplicationContext(), R.layout.dialog_datetime_picker, null);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);

        // Init DatePicker
        int year;
        int month;
        int day;
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year, month, day, null);

        // Build DateTimeDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_picker);
        builder.setView(view);
        builder.setTitle("设置时间");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Integer year = datePicker.getYear();
                Integer month = datePicker.getMonth() + 1;
                Integer day = datePicker.getDayOfMonth();
                ye.setText(year.toString());
                mo.setText(month.toString());
                da.setText(day.toString());
                // Log.i("ysm************", year + "-" + month);
                // Toast.makeText(MainActivity.this,"****ok",Toast.LENGTH_SHORT).show();

            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void queryRequset(Integer y, Integer m, Integer d) {
        BmobQuery<Flight> query = new BmobQuery<Flight>();
        List<BmobQuery<Flight>> and = new ArrayList<BmobQuery<Flight>>();
        //大于00：00：00
        BmobQuery<Flight> q1 = new BmobQuery<Flight>();
        /*Calendar calendar=Calendar.getInstance();
        calendar.set(y,m,d,00,00);*/
        String s = y + "-" + m + "-" + d + " 00:00:00";
        //Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        q1.addWhereGreaterThanOrEqualTo("startDate", new BmobDate(date));
        and.add(q1);
        //小于23：59：59
        BmobQuery<Flight> q2 = new BmobQuery<Flight>();
        /*Calendar calendar2=Calendar.getInstance();
        calendar2.set(y,m,d,23,59);*/
        String en = y + "-" + m + "-" + d + " 23:59:59";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        try {
            date1 = sdf1.parse(en);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        q2.addWhereLessThanOrEqualTo("endDate", new BmobDate(date1));
        and.add(q2);
        //添加复合与查询
        BmobQuery<Flight> q3 = new BmobQuery<Flight>();
        q3.addWhereEqualTo("startCity", flight.getStartCity());
        q3.addWhereEqualTo("endCity", flight.getEndCity());
        and.add(q3);
        query.and(and);
        //   Toast.makeText(MainActivity.this,"*******chaxunyunju",Toast.LENGTH_SHORT).show();
        query.setLimit(50);
        query.findObjects(new FindListener<Flight>() {
            @Override
            public void done(List<Flight> list, BmobException e) {
                if (e == null) {
                    Toast.makeText(MainActivity.this, "查询成功：共" + list.size() + "条数据。", Toast.LENGTH_SHORT).show();
                    adapter = new FlightAdapter(MainActivity.this, R.layout.query_items, list);
                    //showFlighInfo = (ListView) findViewById(R.id.listOfFlight);
                    showFlighInfo.setAdapter(adapter);

/*                    //查看每一个航班信息
                    for (Flight f : list) {
                        Toast.makeText(MainActivity.this,"&&&&&&&&"+f.getStartCity()+f.getEndCity()+f.getStartDate().getDate().toString()+"**"+f.getRemaining().toString(),Toast.LENGTH_SHORT).show();
                    }*/
                } else {
                    Log.i("航班查询", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio_login:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                BmobUser.getCurrentUser(User.class).logOut();
                startActivity(intent);
                finish();
                break;
            case R.id.radio_order:
                Intent intent1 = new Intent();
                intent1.setClass(MainActivity.this, QueryOrderActivity.class);
                startActivity(intent1);
                break;
            case R.id.radio_myInfo:
                Intent intent2 = new Intent();
                intent2.setClass(MainActivity.this, MyInfoActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //点击list的item响应事件
        //把当前list中得flight传到另外一个页面
        Flight f = adapter.getItem(position);
        Log.e("position", position + "");
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, BookingConfirmActivity.class);
        intent.putExtra("flight", f);
        startActivity(intent);
    }

/*    private  void shujuku(){
        Orders orders=new Orders();
        Flight flight=new Flight();
       *//* private String flight;
        private String startCity;
        private String endCity;
        private BmobDate startDate;
        private BmobDate endDate;
        private Integer remaining=100;
        private Integer price;*//*
        flight.setFlightName("中联航 KN5512");
        flight.setStartCity("南京");
        flight.setEndCity("北京");
        flight.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });
        orders.setState("未出行");
        //orders.setFlight(new Flight().setFlightName("中联航 KN5512"));

    }*/
}

