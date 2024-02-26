package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class Main {
    //to calculate number of bits
    static int NumOfBits(int x) {
        if (x == 0)
            return 1;
        int ans = 0;
        while (x > 0) {
            x /= 2;
            ans++;
        }
        return ans;
    }

    static void Compression() {
        HashMap<String, Integer> dic = new HashMap<String, Integer>();
        // build the dictionary
        for (int i = 0; i < 128; i++) {
            dic.put((char) i + "", i);
        }

        Scanner input =null;
        try {
            input = new Scanner(new FileInputStream("input.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String str = input.next();
        Vector<Integer> Compression = new Vector<>();

        String curr = "";
        int cnt = 128;
        int mx = 0;

        for (int i = 0; i < str.length(); i++) {
            curr += str.charAt(i);
            if (!dic.containsKey(curr)) {
                dic.put(curr, cnt++);
                curr = curr.substring(0, curr.length() - 1);
                Compression.add(dic.get(curr));
                mx = Math.max(mx, dic.get(curr));
                curr = "";
                i--;
            }
        }
        if (curr.length() > 0) {
            Compression.add(dic.get(curr));
            mx = Math.max(mx, dic.get(curr));

        }
        try {
            PrintWriter printWriter = new PrintWriter("output.txt");


        for (int i = 0; i < Compression.size(); i++) {
            printWriter.println("<" + Compression.get(i) + "> ");
        }
//        System.out.println();
        int after = NumOfBits(mx) * Compression.size();
        int before = str.length() * 8;
            printWriter.println("THe Size Before Compression = " + before + " Bits");
            printWriter.println("THe Size After Compression = " + after + " Bits");
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void Decompression() {
        Scanner input = null;
        try {
            input = new Scanner(new FileInputStream("input.txt") );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Integer> tags = new ArrayList<Integer>();
        //System.out.println("Enter Tags Size:");
        int tagsSize = input.nextInt();
        //System.out.println("Enter " + tagsSize + " Tags:");
        for (int i = 0; i < tagsSize; i++) {
            int x = input.nextInt();
            tags.add(x);
        }

        String decompressionTxt = "";
        HashMap<Integer, String> dic = new HashMap<Integer, String>();
        for (int i = 0; i < 128; i++) {
            dic.put(i, (char) i + "");
        }
        int curr = 128;
        String prev = "";
        for (int i = 0; i < tags.size(); i++) {
            if (i > 0) {
                dic.put(curr, prev);
                dic.put(curr, prev + dic.get(tags.get(i)).charAt(0));
                curr++;
            }
            decompressionTxt += dic.get(tags.get(i));
            prev = dic.get(tags.get(i));

        }
        try {
            PrintWriter printWriter =new PrintWriter("output.txt");
            printWriter.println("The Decompression Text: " + decompressionTxt);
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {
        JFrame frame_ = new JFrame("LZW App");
        JLabel label_1;
        label_1 = new JLabel("Choose what do you want");
        label_1.setBounds(200, 20, 1000, 90);
        

        JButton button1 = new JButton("Compression");
        button1.setBounds(300, 125, 150, 30);
        JButton button2 = new JButton("Decompression");
        button2.setBounds(100, 125, 150, 30);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Compression();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);

            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Decompression();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);


            }
        });
        frame_.add(label_1);
        frame_.add(button1);
        frame_.add(button2);
        frame_.setSize(600, 250);
        frame_.setLayout(null);
        frame_.setVisible(true);
        frame_.setResizable(false);
        

    }
}

/**
 
  14

  65
  66
  65
  128
  128
  129
  131
  134
  130
  129
  66
  138
  139
  138

  ABAABABBAABAABAAAABABBBBBBBB
 
 **/