package com.kpicat.sdk.java;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kpicat.sdk.java.model.component.*;
import com.kpicat.sdk.java.model.message.Notification;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KpiCat {
    
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    private static final String PROD_COMPONENT_URL = "https://kpicat.com/api/component";

    public static int CONNECT_TIMEOUT = 30 * 1000;
    public static int READ_TIMEOUT = 30 * 1000;

    public static void send(ComponentObject componentObject) throws IOException {
        httpPost(PROD_COMPONENT_URL, GSON.toJson(componentObject));
    }

    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public static void main(String[] args) throws Exception {

/*
        final ComponentObject componentObject = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("com_gL6Q2BSKSyWRvyqB0TWSiA")
                .setTimestamp(1483228800)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor("#FFFFFF")
                        .setLeftText(new TextBuilder().setValue("KPI").setColor("#00a8e8").setSize(30).create())
                        .setMiddleText(new TextBuilder().setValue("MOBILE").setColor("#ff3b3f").setSize(30).create())
                        .setRightText(new TextBuilder().setValue("API").setColor("#57bc90").setSize(30).create())
                        .create())
                .create();
*/
                //System.out.println(GSON.toJson(componentObject));

/*
        final ComponentObject componentObject = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("com_gL6Q2BSKSyWRvyqB0TWSiA")
                .setTimestamp(1483228800)
                .setData(new VerticalBoxBuilder()
                        .setBackgroundColor("#fcb064")
                        .setTopText(new TextBuilder().setValue("Top Value").setColor("#123456").setSize(24).create())
                        .setBottomText(new TextBuilder().setValue("Bottom Value").setColor("#fcec64").setSize(24).create())
                        .create())
                .create();

        System.out.println(GSON.toJson(componentObject));
*/

/*
        PieChart p2 = new PieChart("#FFFFFF", 600);
        List<DataPoint> points = new ArrayList<>();
        points.add(new DataPoint("Jan", 20, ""));
        points.add(new DataPoint("Feb", 25, ""));
        points.add(new DataPoint("Mar", 15, ""));
        p2.setPoints(points);
*/

/*
        LineChart p2 = new LineChart("#eff9f9", "#9bf8ff", 600, true, true);
        List<DataPoint> points = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            points.add(new DataPoint("" + i, randInt(1, 100), "#123456"));
        }
        p2.setPoints(points);
*/

/*
        BarChart p2 = new BarChart("#FFFFFF", 600, false);
        List<DataPoint> points = new ArrayList<>();
        points.add(new DataPoint("Jan", 20, "#123456"));
        points.add(new DataPoint("Feb", 25, "#123456"));
        points.add(new DataPoint("Mar", 15, "#123456"));
        p2.setPoints(points);
*/

/*
        final ComponentObject componentObject = new ComponentObjectBuilder()
                .setApiKey("api_0ZR9SpfUTlWw_8MUKPgJuQ")
                .setComponentId("com_SoU6DT2RSFWrMX2sLylKvA")
                .setTimestamp(1483228800)
                .setData(p2)
                .create();
        System.out.println(GSON.toJson(componentObject));
        httpPost(GSON.toJson(componentObject));
*/

        KpiCat k = new KpiCat();
        //k.runP1();
        //k.runP3();
        //k.runP2();
        //k.runChart();
        k.sendMessage();
        /*
        for (int j = 0; j <= 100; j++) {
            Thread t1 = new Thread(new Runnable() {
                public void run() {
                    try {
                        for (int i = 0; i <= 10000; i++) {
                            long tStart = System.currentTimeMillis();
                            KpiCat.send(rootObject);
                            long tEnd = System.currentTimeMillis();
                            long tDelta = tEnd - tStart;
                            System.out.println(Thread.currentThread().getId() + " " + i +  " - " + tDelta + " ms");
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t1.start();
        }
        */


    }

    public void sendMessage() throws Exception {

        String MSG_URL = "https://kpicat.com/api/message";

        Notification n = new Notification();
        n.setApiKey("1");
        n.setPageName("NYC-DB-1");
        n.setMessage("CPU running at 100%");

        httpPost(MSG_URL, GSON.toJson(n));
    }


    public void runChart() throws IOException {
        PieChart p1 = new PieChart("#FFFFFF", 600);
        List<DataPoint> points = new ArrayList<>();
        points.add(new DataPoint("L1", 20, ""));
        points.add(new DataPoint("L2", 25, ""));
        points.add(new DataPoint("L3", 15, ""));
        points.add(new DataPoint("L4", 35, ""));
        p1.setPoints(points);

        final ComponentObject c1 = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("com_C76IIljBQ3uR1AOLPu51uw")
                .setTimestamp(1483228800)
                .setData(p1)
                .create();
        httpPost(PROD_COMPONENT_URL, GSON.toJson(c1));


        LineChart p2 = new LineChart("#eff9f9", "#9bf8ff", 450, true, true);
        List<DataPoint> points2 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            points2.add(new DataPoint("" + i, randInt(1, 10), ""));
        }
        p2.setPoints(points2);

        final ComponentObject c2 = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("com_ml_pvRT3TN-fCXQKT1xdyg")
                .setTimestamp(1483228800)
                .setData(p2)
                .create();
        httpPost(PROD_COMPONENT_URL, GSON.toJson(c2));


        BarChart p3 = new BarChart("#FFFFFF", 450, false);
        List<DataPoint> points3 = new ArrayList<>();
        points3.add(new DataPoint("Jan", 200, "#f9bb68"));
        points3.add(new DataPoint("Feb", 250, "#f9bb68"));
        points3.add(new DataPoint("Mar", 150, "#f9bb68"));
        points3.add(new DataPoint("Apr", 350, "#f9bb68"));
        points3.add(new DataPoint("May", 100, "#f9bb68"));
        p3.setPoints(points3);

        final ComponentObject c3 = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("com_OMdWhOV8S_CKr-vz70Dtzw")
                .setTimestamp(1483228800)
                .setData(p3)
                .create();
        httpPost(PROD_COMPONENT_URL, GSON.toJson(c3));

    }



    public void runP1() {


        // font 18
        String green = "#5cb85c";
        String red = "#d9534f";
        String black = "#292b2c";
        String orange = "#f0ad4e";
        String grey = "#a9a9a9";
        String lightBlue = "#5bc0de";
        String blue = "#0275d8";
        String white = "#ffffff";

        final ComponentObject cpu = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p1-c1")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(blue)
                        .setMiddleText(new TextBuilder().setValue("CPU %").setColor(white).setSize(22).create())
                        .create())
                .create();

        final ComponentObject cpuMax = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p1-c2")
                .setTimestamp(6666)
                .setData(new VerticalBoxBuilder()
                        .setBackgroundColor(white)
                        .setTopText(new TextBuilder().setValue("Max").create())
                        .setBottomText(new TextBuilder().setValue("100").setColor(red).setSize(24).create())
                        .create())
                .create();

        final ComponentObject cpuAvg = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p1-c3")
                .setTimestamp(6666)
                .setData(new VerticalBoxBuilder()
                        .setBackgroundColor(white)
                        .setTopText(new TextBuilder().setValue("Avg").create())
                        .setBottomText(new TextBuilder().setValue("100").setColor(red).setSize(24).create())
                        .create())
                .create();

        final ComponentObject cpuLast = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p1-c4")
                .setTimestamp(6666)
                .setData(new VerticalBoxBuilder()
                        .setBackgroundColor(white)
                        .setTopText(new TextBuilder().setValue("Last").create())
                        .setBottomText(new TextBuilder().setValue("100").setColor(red).setSize(24).create())
                        .create())
                .create();

        final ComponentObject traffic = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p1-c5")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(blue)
                        .setMiddleText(new TextBuilder().setValue("TRAFFIC Mb/s").setColor(white).setSize(22).create())
                        .create())
                .create();

        final ComponentObject in = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p1-c6")
                .setTimestamp(6666)
                .setData(new VerticalBoxBuilder()
                        .setBackgroundColor(white)
                        .setTopText(new TextBuilder().setValue("In").create())
                        .setBottomText(new TextBuilder().setValue("3.25").setColor(green).setSize(24).create())
                        .create())
                .create();

        final ComponentObject out = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p1-c7")
                .setTimestamp(6666)
                .setData(new VerticalBoxBuilder()
                        .setBackgroundColor(white)
                        .setTopText(new TextBuilder().setValue("Out").create())
                        .setBottomText(new TextBuilder().setValue("6.06").setColor(green).setSize(24).create())
                        .create())
                .create();
        final ComponentObject io = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p1-c8")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(blue)
                        .setMiddleText(new TextBuilder().setValue("IO Ops/s").setColor(white).setSize(22).create())
                        .create())
                .create();

        final ComponentObject ioMax = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p1-c9")
                .setTimestamp(6666)
                .setData(new VerticalBoxBuilder()
                        .setBackgroundColor(white)
                        .setTopText(new TextBuilder().setValue("Max").create())
                        .setBottomText(new TextBuilder().setValue("33k").setColor(orange).setSize(24).create())
                        .create())
                .create();

        final ComponentObject ioAvg = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p1-c10")
                .setTimestamp(6666)
                .setData(new VerticalBoxBuilder()
                        .setBackgroundColor(white)
                        .setTopText(new TextBuilder().setValue("Avg").create())
                        .setBottomText(new TextBuilder().setValue("11k").setColor(green).setSize(24).create())
                        .create())
                .create();
        final ComponentObject ioLast = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p1-c11")
                .setTimestamp(6666)
                .setData(new VerticalBoxBuilder()
                        .setBackgroundColor(white)
                        .setTopText(new TextBuilder().setValue("Last").create())
                        .setBottomText(new TextBuilder().setValue("29k").setColor(orange).setSize(24).create())
                        .create())
                .create();

        final ComponentObject memory = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p1-c12")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(blue)
                        .setMiddleText(new TextBuilder().setValue("MEMORY GB").setColor(white).setSize(22).create())
                        .create())
                .create();

        final ComponentObject memoryVal = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p1-c13")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(white)
                        .setMiddleText(new TextBuilder().setValue("36.9/48").setColor(green).setSize(22).create())
                        .create())
                .create();

        final ComponentObject disk = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p1-c14")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(blue)
                        .setMiddleText(new TextBuilder().setValue("DISK GB").setColor(white).setSize(22).create())
                        .create())
                .create();

        final ComponentObject diskVal = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p1-c15")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(white)
                        .setMiddleText(new TextBuilder().setValue("325/512").setColor(green).setSize(22).create())
                        .create())
                .create();

        try {
            KpiCat.send(cpu);
            KpiCat.send(cpuMax);
            KpiCat.send(cpuAvg);
            KpiCat.send(cpuLast);
            KpiCat.send(traffic);
            KpiCat.send(in);
            KpiCat.send(out);
            KpiCat.send(io);
            KpiCat.send(ioMax);
            KpiCat.send(ioAvg);
            KpiCat.send(ioLast);
            KpiCat.send(memory);
            KpiCat.send(memoryVal);
            KpiCat.send(disk);
            KpiCat.send(diskVal);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void runP3() {

        String green = "#008000";
        String red = "#FF0000";
        String black = "#000000";
        String orange = "#FFA500";
        String grey = "#a9a9a9";
        String lightBlue = "#5bc0de";
        String blue = "#0000FF";
        String white = "#ffffff";

        final ComponentObject shiftStart = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p3-c1")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(white)
                        .setLeftText(new TextBuilder().setValue("Shift - start").create())
                        .setRightText(new TextBuilder().setValue("18:00 PM").create())
                        .create())
                .create();

        final ComponentObject shiftEnd = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p3-c14")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(white)
                        .setLeftText(new TextBuilder().setValue("Shift - end").create())
                        .setRightText(new TextBuilder().setValue("06:00 AM").create())
                        .create())
                .create();

        final ComponentObject prodRate = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p3-c2")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(black)
                        .setMiddleText(new TextBuilder().setValue("PRODUCTION").setColor(white).create())
                        .create())
                .create();

        final ComponentObject prodCurr = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p3-c3")
                .setTimestamp(6666)
                .setData(new VerticalBoxBuilder()
                        .setBackgroundColor(white)
                        .setTopText(new TextBuilder().setValue("Current").create())
                        .setBottomText(new TextBuilder().setValue("180").setColor(green).setSize(24).create())
                        .create())
                .create();

        final ComponentObject prodPeak = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p3-c4")
                .setTimestamp(6666)
                .setData(new VerticalBoxBuilder()
                        .setBackgroundColor(white)
                        .setTopText(new TextBuilder().setValue("Peak").create())
                        .setBottomText(new TextBuilder().setValue("210").setColor(green).setSize(24).create())
                        .create())
                .create();

        final ComponentObject line1 = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p3-c5")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(white)
                        .setLeftText(new TextBuilder().setValue("Line 1").create())
                        .setRightText(new TextBuilder().setValue("ACTIVE").setColor(green).create())
                        .create())
                .create();

        final ComponentObject line2 = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p3-c6")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(white)
                        .setLeftText(new TextBuilder().setValue("Line 2").create())
                        .setRightText(new TextBuilder().setValue("ACTIVE").setColor(green).create())
                        .create())
                .create();

        final ComponentObject line3 = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p3-c7")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(white)
                        .setLeftText(new TextBuilder().setValue("Line 3").create())
                        .setRightText(new TextBuilder().setValue("IN MAINT").setColor(orange).create())
                        .create())
                .create();

        final ComponentObject qty = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p3-c8")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(white)
                        .setLeftText(new TextBuilder().setValue("Quantity/Target").create())
                        .setRightText(new TextBuilder().setValue("510/2000").setColor(green).create())
                        .create())
                .create();

        final ComponentObject defects = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p3-c9")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(white)
                        .setLeftText(new TextBuilder().setValue("Defects").create())
                        .setRightText(new TextBuilder().setValue("7").setColor(red).create())
                        .create())
                .create();

        final ComponentObject labor = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p3-c10")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(black)
                        .setMiddleText(new TextBuilder().setValue("LABOR").setColor(white).create())
                        .create())
                .create();

        final ComponentObject operator = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p3-c11")
                .setTimestamp(6666)
                .setData(new VerticalBoxBuilder()
                        .setBackgroundColor(white)
                        .setTopText(new TextBuilder().setValue("Operator").create())
                        .setBottomText(new TextBuilder().setValue("16").create())
                        .create())
                .create();

        final ComponentObject admin = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p3-c12")
                .setTimestamp(6666)
                .setData(new VerticalBoxBuilder()
                        .setBackgroundColor(white)
                        .setTopText(new TextBuilder().setValue("Admin").create())
                        .setBottomText(new TextBuilder().setValue("1").create())
                        .create())
                .create();

        final ComponentObject supervisor = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p3-c13")
                .setTimestamp(6666)
                .setData(new VerticalBoxBuilder()
                        .setBackgroundColor(white)
                        .setTopText(new TextBuilder().setValue("Supervisor").create())
                        .setBottomText(new TextBuilder().setValue("2").create())
                        .create())
                .create();

        try {
            KpiCat.send(shiftStart);
            KpiCat.send(prodRate);
            KpiCat.send(prodCurr);
            KpiCat.send(prodPeak);
            KpiCat.send(line1);
            KpiCat.send(line2);
            KpiCat.send(line3);
            KpiCat.send(qty);
            KpiCat.send(defects);
            KpiCat.send(labor);
            KpiCat.send(operator);
            KpiCat.send(admin);
            KpiCat.send(supervisor);
            KpiCat.send(shiftEnd);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void runP2() {

        String green = "#5cb85c";
        String red = "#d9534f";
        String black = "#292b2c";
        String orange = "#f0ad4e";
        String grey = "#a9a9a9";
        String lightBlue = "#5bc0de";
        String blue = "#0275d8";
        String white = "#ffffff";

        String gold = "#c2b499";

        final ComponentObject date = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p2-c1")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(gold)
                        .setMiddleText(new TextBuilder().setValue("Monday 11-27").setColor(white).setSize(25).create())
                        .create())
                .create();

        final ComponentObject sales = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p2-c2")
                .setTimestamp(6666)
                .setData(new VerticalBoxBuilder()
                        .setBackgroundColor(green)
                        .setTopText(new TextBuilder().setValue("SALES").setSize(23).setColor(white).create())
                        .setBottomText(new TextBuilder().setValue("$33.5K").setSize(25).setColor(white).create())
                        .create())
                .create();

        final ComponentObject profit = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p2-c3")
                .setTimestamp(6666)
                .setData(new VerticalBoxBuilder()
                        .setBackgroundColor(green)
                        .setTopText(new TextBuilder().setValue("PROFIT").setSize(23).setColor(white).create())
                        .setBottomText(new TextBuilder().setValue("$91.2K").setSize(25).setColor(white).create())
                        .create())
                .create();

        final ComponentObject order = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p2-c4")
                .setTimestamp(6666)
                .setData(new VerticalBoxBuilder()
                        .setBackgroundColor(orange)
                        .setTopText(new TextBuilder().setValue("ORDER").setSize(23).setColor(white).create())
                        .setBottomText(new TextBuilder().setValue("412").setSize(25).setColor(white).create())
                        .create())
                .create();

        final ComponentObject avgTrans = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p2-c5")
                .setTimestamp(6666)
                .setData(new VerticalBoxBuilder()
                        .setBackgroundColor(white)
                        .setTopText(new TextBuilder().setValue("AVG TRANS").setSize(23).setColor(black).create())
                        .setBottomText(new TextBuilder().setValue("$80.9").setSize(25).setColor(black).create())
                        .create())
                .create();

        final ComponentObject inStore = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p2-c6")
                .setTimestamp(6666)
                .setData(new VerticalBoxBuilder()
                        .setBackgroundColor(white)
                        .setTopText(new TextBuilder().setValue("SOLD").create())
                        .setBottomText(new TextBuilder().setValue("380").create())
                        .create())
                .create();

        final ComponentObject returnInv = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p2-c7")
                .setTimestamp(6666)
                .setData(new VerticalBoxBuilder()
                        .setBackgroundColor(red)
                        .setTopText(new TextBuilder().setValue("RETURN").setColor(white).create())
                        .setBottomText(new TextBuilder().setValue("5").setColor(white).create())
                        .create())
                .create();

        final ComponentObject shipped = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p2-c8")
                .setTimestamp(6666)
                .setData(new VerticalBoxBuilder()
                        .setBackgroundColor(green)
                        .setTopText(new TextBuilder().setValue("SHIPPED").setColor(white).create())
                        .setBottomText(new TextBuilder().setValue("32").setColor(white).create())
                        .create())
                .create();

        final ComponentObject top3 = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p2-c9")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(gold)
                        .setMiddleText(new TextBuilder().setValue("TOP 3 CATEGORY").setColor(white).create())
                        .create())
                .create();

        final ComponentObject shirt = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p2-c10")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(white)
                        .setLeftText(new TextBuilder().setValue("#1").setColor(gold).create())
                        .setMiddleText(new TextBuilder().setValue("SHIRT").create())
                        .setRightText(new TextBuilder().setValue("1102").setColor(gold).create())
                        .create())
                .create();

        final ComponentObject jeans = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p2-c11")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(white)
                        .setLeftText(new TextBuilder().setValue("#2").setColor(gold).create())
                        .setMiddleText(new TextBuilder().setValue("JEANS").create())
                        .setRightText(new TextBuilder().setValue("315").setColor(gold).create())
                        .create())
                .create();

        final ComponentObject shoes = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p2-c12")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(white)
                        .setLeftText(new TextBuilder().setValue("#3").setColor(gold).create())
                        .setMiddleText(new TextBuilder().setValue("SHOES").create())
                        .setRightText(new TextBuilder().setValue("198").setColor(gold).create())
                        .create())
                .create();

        final ComponentObject topEmployee = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p2-c13")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(gold)
                        .setMiddleText(new TextBuilder().setValue("TOP 3 SALES REP").setColor(white).create())
                        .create())
                .create();

        final ComponentObject a = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p2-c14")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(white)
                        .setLeftText(new TextBuilder().setValue("#1").setColor(gold).create())
                        .setMiddleText(new TextBuilder().setValue("Heidi").create())
                        .setRightText(new TextBuilder().setValue("89").setColor(gold).create())
                        .create())
                .create();

        final ComponentObject b = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p2-c15")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(white)
                        .setLeftText(new TextBuilder().setValue("#2").setColor(gold).create())
                        .setMiddleText(new TextBuilder().setValue("Miranda").create())
                        .setRightText(new TextBuilder().setValue("78").setColor(gold).create())
                        .create())
                .create();

        final ComponentObject c = new ComponentObjectBuilder()
                .setApiKey("1")
                .setComponentId("p2-c16")
                .setTimestamp(6666)
                .setData(new HorizontalBoxBuilder()
                        .setBackgroundColor(white)
                        .setLeftText(new TextBuilder().setValue("#3").setColor(gold).create())
                        .setMiddleText(new TextBuilder().setValue("Jennifer").create())
                        .setRightText(new TextBuilder().setValue("72").setColor(gold).create())
                        .create())
                .create();

        try {
            KpiCat.send(date);
            KpiCat.send(sales);
            KpiCat.send(profit);
            KpiCat.send(order);
            KpiCat.send(avgTrans);
            KpiCat.send(inStore);
            KpiCat.send(returnInv);
            KpiCat.send(shipped);
            KpiCat.send(top3);
            KpiCat.send(shirt);
            KpiCat.send(jeans);
            KpiCat.send(shoes);
            KpiCat.send(topEmployee);
            KpiCat.send(a);
            KpiCat.send(b);
            KpiCat.send(c);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static RootResponse httpPost(String url, String postData) throws IOException {

        URL apiUrl = new URL(url);
        HttpURLConnection  conn = (HttpURLConnection) apiUrl.openConnection();

        conn.setConnectTimeout(CONNECT_TIMEOUT);
        conn.setReadTimeout(READ_TIMEOUT);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        conn.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(postData);
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        System.out.println(response.toString());

        in.close();
        
        return new RootResponse(responseCode, response.toString());
    }
}
