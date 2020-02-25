package com.kpicat.kpicat.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kpicat.kpicat.R;
import com.kpicat.kpicat.model.BarChartComponent;
import com.kpicat.kpicat.model.Component;
import com.kpicat.kpicat.model.DataPoint;
import com.kpicat.kpicat.model.HorizontalBox;
import com.kpicat.kpicat.model.LineChartComponent;
import com.kpicat.kpicat.model.Page;
import com.kpicat.kpicat.model.PieChartComponent;
import com.kpicat.kpicat.model.Row;
import com.kpicat.kpicat.model.VerticalBox;
import com.kpicat.kpicat.screen.PageAdapter;
import com.kpicat.kpicat.service.WebServiceClient;
import com.kpicat.kpicat.util.Common;
import com.kpicat.kpicat.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PageActivity extends AppCompatActivity {

    private ListView drawerList;
    private DrawerLayout drawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    //private CustomProgressDialog progress;

    private String currentPageId;
    private String currentPageName;

    private TableLayout tableLayout;

    private SwipeRefreshLayout swipeRefreshLayout;

    private String mobileKey;

    private PageAdapter pageAdapter;

    private ScrollView pageScrollView;

    private WebServiceClient webServiceClient = new WebServiceClient();

    private ObjectMapper objectMapper = new ObjectMapper();

    private static String WHITE = "#FFFFFF";
    private static String BLACK = "#000000";
    private static int MARGIN_LEFT = 20;
    private static int MARGIN_RIGHT = 20;
    private static int MARGIN_TOP = 8;
    private static int TITLE_FONT = 18;
    private static int BOX_MARGIN = 8;

    com.kpicat.kpicat.model.Configuration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        FirebaseMessaging.getInstance().subscribeToTopic("foo-bar");


        config = (com.kpicat.kpicat.model.Configuration) getIntent().getSerializableExtra("configuration");
        if (config == null) {
            config = Constants.DEFAULT_CONFIG;
        } else {
            SharedPreferences sharedPref = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            String splashFontColor = Common.isEmpty(config.getSplashFontColor()) ? Constants.DEFAULT_CONFIG.getSplashFontColor() : config.getSplashFontColor();
            String splashBgColor = Common.isEmpty(config.getSplashBgColor()) ? Constants.DEFAULT_CONFIG.getSplashBgColor() : config.getSplashBgColor();
            String splashText = Common.isEmpty(config.getSplashText()) ? Constants.DEFAULT_CONFIG.getSplashText() : config.getSplashText();

            editor.putString(Constants.SPLASH_FONT_COLOR, splashFontColor);
            editor.putString(Constants.SPLASH_BG_COLOR, splashBgColor);
            editor.putString(Constants.SPLASH_TEXT, splashText);
            editor.commit();


        }

        // Get the page view.
        pageScrollView = (ScrollView) findViewById(R.id.pageScrollView);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(config.getToolbarBgColor())));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //progress = new CustomProgressDialog(this, "Loading");

        SharedPreferences sharedPref = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        mobileKey = sharedPref.getString(Constants.MOBILE_KEY, "");

        drawerList = (ListView)findViewById(R.id.navList);
        drawerList.setBackgroundColor(Color.parseColor(config.getSidebarBgColor()));
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        new loadPagesTask().execute();
        setupDrawer();

        tableLayout = (TableLayout)findViewById(R.id.tableLayout);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {


                        if (currentPageId != null) {
                            //progress.getDialog().show();
                            new LoadPageTask().execute(currentPageId);
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );


        //FirebaseMessaging.getInstance().subscribeToTopic("foo-bar");

    }


    public void drawRows(List<Row> rows) {

        tableLayout.removeAllViews();

        int screenWidth = this.getResources().getDisplayMetrics().widthPixels;

        for (final Row row : rows) {

            // Handle any possible exceptions.
            try {

                TableRow tableRow = new TableRow(PageActivity.this);
                TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT
                );
                tableRow.setLayoutParams(tableRowParams);

                LinearLayout rowLinearLayout = new LinearLayout(PageActivity.this);
                rowLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

                TableRow.LayoutParams subLayoutParams = new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
                subLayoutParams.setMargins(MARGIN_LEFT, MARGIN_TOP, MARGIN_RIGHT, 0);
                rowLinearLayout.setLayoutParams(subLayoutParams);

                for (final Component component : row.getComponents()) {

                    String type = component.getType();
                    final String lastUpdate = Common.parseUnixTime(component.getLastUpdate());

                    if (type == null) {
                        continue;
                    }

                    if (type.equals("verticalBox")) {

                        VerticalBox box = new VerticalBox();
                        try {
                            box = objectMapper.readValue(component.getData(), VerticalBox.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        LinearLayout verticalBox = new LinearLayout(PageActivity.this);
                        verticalBox.setOrientation(LinearLayout.VERTICAL);
                        verticalBox.setBackgroundColor(Color.parseColor(Common.parseBgColor(box.getBackgroundColor())));

                        LinearLayout.LayoutParams boxLayoutParams = new LinearLayout.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
                        boxLayoutParams.setMargins(BOX_MARGIN, BOX_MARGIN, BOX_MARGIN, BOX_MARGIN);
                        verticalBox.setLayoutParams(boxLayoutParams);

                        if (box.getTopText() != null) {
                            TextView topText = new TextView(PageActivity.this);

                            LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
                            textLayoutParams.setMargins(0, 10, 0, 0);

                            topText.setLayoutParams(textLayoutParams);
                            topText.setText(box.getTopText().getValue());
                            topText.setTextColor(Color.parseColor(Common.parseFontColor(box.getTopText().getColor())));
                            topText.setGravity(Gravity.CENTER);
                            topText.setTypeface(null, Typeface.BOLD);
                            topText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, Common.parseFontSize(box.getTopText().getSize()));

                            verticalBox.addView(topText);
                        }

                        if (box.getBottomText() != null) {
                            TextView bottomText = new TextView(PageActivity.this);

                            LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
                            textLayoutParams.setMargins(0, 0, 0, 10);

                            bottomText.setLayoutParams(textLayoutParams);
                            bottomText.setText(box.getBottomText().getValue());
                            bottomText.setTextColor(Color.parseColor(Common.parseFontColor(box.getBottomText().getColor())));
                            bottomText.setGravity(Gravity.CENTER);
                            bottomText.setTypeface(null, Typeface.BOLD);
                            bottomText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, Common.parseFontSize(box.getBottomText().getSize()));

                            verticalBox.addView(bottomText);
                        }

                        verticalBox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), lastUpdate, Toast.LENGTH_LONG).show();
                            }
                        });


                        rowLinearLayout.addView(verticalBox);

                    } else if (type.equals("horizontalBox")) {


                        HorizontalBox box = new HorizontalBox();
                        try {
                            box = objectMapper.readValue(component.getData(), HorizontalBox.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        LinearLayout horizontalBox = new LinearLayout(PageActivity.this);
                        horizontalBox.setOrientation(LinearLayout.HORIZONTAL);
                        horizontalBox.setBackgroundColor(Color.parseColor(Common.parseBgColor(box.getBackgroundColor())));

                        LinearLayout.LayoutParams boxLayoutParams = new LinearLayout.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
                        boxLayoutParams.setMargins(BOX_MARGIN, BOX_MARGIN, BOX_MARGIN, BOX_MARGIN);
                        horizontalBox.setLayoutParams(boxLayoutParams);

                        if (box.getLeftText() != null) {
                            TextView leftText = new TextView(PageActivity.this);

                            LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
                            textLayoutParams.setMargins(25, 10, 0, 10);

                            leftText.setLayoutParams(textLayoutParams);
                            leftText.setText(box.getLeftText().getValue());
                            leftText.setTextColor(Color.parseColor(Common.parseFontColor(box.getLeftText().getColor())));
                            leftText.setGravity(Gravity.LEFT);
                            leftText.setTypeface(null, Typeface.BOLD);
                            leftText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, Common.parseFontSize(box.getLeftText().getSize()));

                            horizontalBox.addView(leftText);
                        }

                        if (box.getMiddleText() != null) {
                            TextView middleText = new TextView(PageActivity.this);

                            LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
                            textLayoutParams.setMargins(0, 10, 0, 10);

                            middleText.setLayoutParams(textLayoutParams);
                            middleText.setText(box.getMiddleText().getValue());
                            middleText.setTextColor(Color.parseColor(Common.parseFontColor(box.getMiddleText().getColor())));
                            middleText.setGravity(Gravity.CENTER);
                            middleText.setTypeface(null, Typeface.BOLD);
                            middleText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, Common.parseFontSize(box.getMiddleText().getSize()));

                            horizontalBox.addView(middleText);
                        }

                        if (box.getRightText() != null) {
                            TextView rightText = new TextView(PageActivity.this);

                            LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
                            textLayoutParams.setMargins(0, 10, 25, 10);

                            rightText.setLayoutParams(textLayoutParams);
                            rightText.setText(box.getRightText().getValue());
                            rightText.setTextColor(Color.parseColor(Common.parseFontColor(box.getRightText().getColor())));
                            rightText.setGravity(Gravity.RIGHT);
                            rightText.setTypeface(null, Typeface.BOLD);
                            rightText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, Common.parseFontSize(box.getRightText().getSize()));

                            horizontalBox.addView(rightText);
                        }

                        horizontalBox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), lastUpdate, Toast.LENGTH_LONG).show();
                            }
                        });

                        rowLinearLayout.addView(horizontalBox);

                    } else if (type.equals("lineChart")) {

                        LineChartComponent lineComp = new LineChartComponent();
                        try {
                            lineComp = objectMapper.readValue(component.getData(), LineChartComponent.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        TableRow.LayoutParams chartLayoutParams = new TableRow.LayoutParams(0, Common.parseChartHeight(lineComp.getHeight()), 1f);
                        chartLayoutParams.setMargins(BOX_MARGIN, BOX_MARGIN, BOX_MARGIN, BOX_MARGIN);

                        LineChart lineChart = new LineChart(this);
                        lineChart.setLayoutParams(chartLayoutParams);
                        lineChart.getLegend().setEnabled(false);
                        lineChart.setBackgroundColor(Color.parseColor(Common.parseBgColor(lineComp.getBackgroundColor())));
                        lineChart.getDescription().setEnabled(false);

                        // Disable drag and scale
                        lineChart.setDragEnabled(false);
                        lineChart.setScaleEnabled(false);

                        // the labels that should be drawn on the XAxis
                        int size = lineComp.getPoints().size();

                        List<Entry> entries = new ArrayList<>();
                        List<Integer> colors = new ArrayList<>();
                        final String[] labels = new String[size];
                        for (int i = 0; i < size; i++) {
                            DataPoint p = lineComp.getPoints().get(i);

                            if (lineComp.isxNumber()) {
                                entries.add(new Entry(Float.parseFloat(p.getX()), p.getY()));
                            } else {
                                // Add the data points.
                                entries.add(new Entry(i, p.getY()));

                                // Set up the label on xAxis.
                                labels[i] = p.getX();
                            }

                            if (!Common.isEmpty(p.getColor())) {
                                colors.add(Color.parseColor(p.getColor()));
                            }
                        }

                        IAxisValueFormatter formatter = new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return labels[(int) value];
                            }
                        };

                        lineChart.getXAxis().setGranularity(1f); // minimum axis-step (interval) is 1
                        if (!lineComp.isxNumber()) {
                            lineChart.getXAxis().setValueFormatter(formatter);
                        }

                        // Remove the grid lines
                        lineChart.getAxisLeft().setDrawGridLines(false);
                        lineChart.getXAxis().setDrawGridLines(false);
                        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                        lineChart.getAxisRight().setEnabled(false); // no right axis

                        LineDataSet lineSet = new LineDataSet(entries, null);
                        if (!Common.isEmpty(lineComp.getFillColor())) {
                            lineSet.setDrawFilled(true);
                            lineSet.setFillColor(Color.parseColor(lineComp.getFillColor()));
                        }

                        if (colors.isEmpty()) {
                            lineSet.setColors(ColorTemplate.MATERIAL_COLORS);
                        } else {
                            lineSet.setColors(colors);
                        }

                        if (lineComp.isCubic()) {
                            lineSet.setDrawCircles(false);
                            lineSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                        }


                        LineData data = new LineData(lineSet);
                        lineChart.setData(data);
                        lineChart.invalidate(); // refresh

                        rowLinearLayout.addView(lineChart);

                    } else if (type.equals("barChart")) {

                        BarChartComponent barComp = new BarChartComponent();
                        try {
                            barComp = objectMapper.readValue(component.getData(), BarChartComponent.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        TableRow.LayoutParams chartLayoutParams = new TableRow.LayoutParams(0, Common.parseChartHeight(barComp.getHeight()), 1f);
                        chartLayoutParams.setMargins(BOX_MARGIN, BOX_MARGIN, BOX_MARGIN, BOX_MARGIN);

                        BarChart barChart = new BarChart(this);
                        barChart.setLayoutParams(chartLayoutParams);
                        barChart.getLegend().setEnabled(false);
                        barChart.setBackgroundColor(Color.parseColor(Common.parseBgColor(barComp.getBackgroundColor())));
                        barChart.getDescription().setEnabled(false);


                        // the labels that should be drawn on the XAxis
                        int size = barComp.getPoints().size();

                        List<BarEntry> entries = new ArrayList<>();
                        List<Integer> colors = new ArrayList<>();
                        final String[] labels = new String[size];
                        for (int i = 0; i < size; i++) {
                            DataPoint p = barComp.getPoints().get(i);


                            // Add the data points.
                            if (barComp.isxNumber()) {
                                entries.add(new BarEntry(Float.parseFloat(p.getX()), p.getY()));
                            } else {
                                entries.add(new BarEntry(i, p.getY()));
                                // Set up the label on xAxis.
                                labels[i] = p.getX();
                            }


                            if (!Common.isEmpty(p.getColor())) {
                                colors.add(Color.parseColor(p.getColor()));
                            }
                        }

                        IAxisValueFormatter formatter = new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return labels[(int) value];
                            }
                        };

                        barChart.getXAxis().setGranularity(1f); // minimum axis-step (interval) is 1

                        if (!barComp.isxNumber()) {
                            barChart.getXAxis().setValueFormatter(formatter);
                        }


                        // Remove the grid lines
                        barChart.getAxisLeft().setDrawGridLines(false);
                        barChart.getXAxis().setDrawGridLines(false);
                        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                        barChart.getAxisRight().setEnabled(false); // no right axis

                        // Disable drag and scale
                        barChart.setDragEnabled(false);
                        barChart.setScaleEnabled(false);

                        BarDataSet barSet = new BarDataSet(entries, null);
                        if (colors.isEmpty()) {
                            barSet.setColors(ColorTemplate.MATERIAL_COLORS);
                        } else {
                            barSet.setColors(colors);
                        }

                        BarData data = new BarData(barSet);
                        barChart.setData(data);
                        barChart.invalidate(); // refresh

                        rowLinearLayout.addView(barChart);

                    } else if (type.equals("pieChart")) {

                        PieChartComponent pieComp = new PieChartComponent();
                        try {
                            pieComp = objectMapper.readValue(component.getData(), PieChartComponent.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        TableRow.LayoutParams chartLayoutParams = new TableRow.LayoutParams(0, Common.parseChartHeight(pieComp.getHeight()), 1f);
                        chartLayoutParams.setMargins(BOX_MARGIN, BOX_MARGIN, BOX_MARGIN, BOX_MARGIN);

                        PieChart pieChart = new PieChart(this);
                        pieChart.setLayoutParams(chartLayoutParams);
                        pieChart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
                        pieChart.setBackgroundColor(Color.parseColor(Common.parseBgColor(pieComp.getBackgroundColor())));
                        pieChart.getDescription().setEnabled(false);


                        List<PieEntry> entries = new ArrayList<>();
                        List<Integer> colors = new ArrayList<>();
                        for (DataPoint p : pieComp.getPoints()) {
                            entries.add(new PieEntry(p.getY(), p.getX()));

                            if (!Common.isEmpty(p.getColor())) {
                                colors.add(Color.parseColor(p.getColor()));
                            }
                        }

                        PieDataSet pieSet = new PieDataSet(entries, null);
                        // hide the values.
                        pieSet.setDrawValues(false);
                        pieSet.setColors(ColorTemplate.JOYFUL_COLORS);
                        if (colors.isEmpty()) {
                            pieSet.setColors(ColorTemplate.MATERIAL_COLORS);
                        } else {
                            pieSet.setColors(colors);
                        }

                        PieData pieData = new PieData(pieSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate(); // refresh

                        rowLinearLayout.addView(pieChart);
                    }
                }

                tableRow.addView(rowLinearLayout);
                tableLayout.addView(tableRow);
            } catch (Exception e) {
                // Row exceptions...
            }
        }
    }


    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Select Page");
                //getSupportActionBar().setTitle(Html.fromHtml("<font color='" + config.getToolbarFontColor() + "'>Select Page</font>"));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if (currentPageName != null) {
                    getSupportActionBar().setTitle(currentPageName);
                }
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:


                new loadPagesTask().execute();
                setupDrawer();


                if (currentPageId != null) {
                    //progress.getDialog().show();
                    new LoadPageTask().execute(currentPageId);
                }
                return true;
            case R.id.action_logout:

                SharedPreferences sharedPref = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(Constants.MOBILE_KEY, "");
                editor.commit();

                final Intent loginIntent = new Intent(PageActivity.this, LoginActivity.class);
                startActivity(loginIntent);

                return true;
            default:
                int id = item.getItemId();
                // Activate the navigation drawer toggle
                if (mDrawerToggle.onOptionsItemSelected(item)) {
                    return true;
                }
                return super.onOptionsItemSelected(item);
        }
    }

    public class loadPagesTask extends AsyncTask<Void, Void, List<Page>> {
        @Override
        protected List<Page> doInBackground(Void... params) {
            return webServiceClient.fetchPages(mobileKey);
        }

        @Override
        protected void onPostExecute(final List<Page> result) {

            pageAdapter = new PageAdapter(PageActivity.this, 0, result, config.getSidebarFontColor());
            drawerList.setAdapter(pageAdapter);
            drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final Page page = (Page) drawerList.getItemAtPosition(position);
                    currentPageId = page.getPageId();
                    currentPageName = page.getName();
                    getSupportActionBar().setTitle(currentPageName);

                    if (!Common.isEmpty(page.getTitleColor())) {
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(page.getTitleColor())));
                    }

                    if (!Common.isEmpty(page.getBgColor())) {
                        pageScrollView.setBackgroundColor(Color.parseColor(page.getBgColor()));
                    }

                    //progress.getDialog().show();
                    new LoadPageTask().execute(currentPageId);
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
            });
            //progress.getDialog().dismiss();

            if (result.size() > 0) {
                currentPageName = result.get(0).getName();
                currentPageId = result.get(0).getPageId();

                // Subscribe to the firebase topic.
                for (Page p : result) {
                    //Log.d("test", p.getPageId());
                    FirebaseMessaging.getInstance().subscribeToTopic(p.getPageId());
                }

                //progress.getDialog().show();
                new LoadPageTask().execute(currentPageId);
                getSupportActionBar().setTitle(currentPageName);
            }

        }
    }

    public class LoadPageTask extends AsyncTask<String, Void, List<Row>> {

        @Override
        protected List<Row> doInBackground(String... params) {
            String pageId = params[0];
            return webServiceClient.fetchRows(pageId, mobileKey);
        }

        @Override
        protected void onPostExecute(final List<Row> result) {
            //drawAllWidgets(result);
            drawRows(result);
            //progress.getDialog().dismiss();
        }
    }

    /*
    public class logoutTask extends AsyncTask<Long, Void, List<Widget>> {
        @Override
        protected List<Widget> doInBackground(Long... params) {
            Long PageId = params[0];
            return webServiceClient.getPagebyId(PageId, apiKey);
        }

        @Override
        protected void onPostExecute(final List<Widget> result) {
            drawAllWidgets(result);
            progress.getDialog().dismiss();
        }
    }
    */


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
