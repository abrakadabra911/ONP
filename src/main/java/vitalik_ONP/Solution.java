package vitalik_ONP;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by leha on 2017-10-15.
 */
public class Solution {
    static private String inputString = "";
    static private ScriptEngineManager mgr = new ScriptEngineManager();
    static private ScriptEngine engine = mgr.getEngineByName("JavaScript");

    private static void start() {
        try {
            inputString = JOptionPane.showInputDialog(null, "Proszę wprowadzić szereg ONP... (rozdzielić spacjami)");

            //sprawdzenie czy szereg nie jest pusty
            if (inputString.isEmpty()) repeat();

            //rozbicie Stringu na szereg dzielony spacjami
            String[] arrays = inputString.split(" ");
            ArrayList<String> stos = new ArrayList<>();
            double result = 0.;

            //sprawdzenie czy w szeregu nie było podrząd więcej od 1 spacji
            if (hasEmptyMembers(arrays)) repeat();

            result = calculateONP(arrays, stos);

            // sprawdzenie finalne. Ilość znaków i liczb musi być kompatybilne
            if (stos.size() > 1) repeat();

            System.out.println(result);
            JOptionPane.showMessageDialog(null, "wynik: " + result);
        } catch (ScriptException e) {
            JOptionPane.showMessageDialog(null, "Proszę wprowadzić dane przez spację");
            repeat();
        } catch (Exception e) {
            showGoodByeMessage();
        }
    }

    //sprawdzenie czy string jest liczbą
    private static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    //sprawdzenie czy nie było zbędnych spacji
    private static boolean hasEmptyMembers(String[] arrays) {
        for (String member : arrays) {
            System.out.print(member);
            if (member.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    // sprawdzanie całego szeregu danych i kalkulacje
    private static double calculateONP(String[] arrays, ArrayList<String> stos) throws ScriptException {
        for (String member : arrays) {
            if (isNumeric(member)) {
                stos.add(member);
                stos.trimToSize();
            } else {
                String foo = stos.get(stos.size() - 2) + member + stos.get(stos.size() - 1);
                Double res = Double.parseDouble(engine.eval(foo).toString());

                // zdejmowanie ze stosu 2 elementów
                stos.remove(stos.size() - 1);
                stos.remove(stos.size() - 1);
                stos.trimToSize();

                // odłożenie na stos resultatu
                stos.add(res.toString());
            }
        }
        return Double.parseDouble(stos.get(0));
    }

    //ponowne uruchomienie
    private static void repeat() {
        JOptionPane.showMessageDialog(null, "Proszę wprowadzić poprawny szereg. Spróbuj ponownie");
        start();
    }

    private static void showGoodByeMessage() {
        final String link = "https://github.com/abrakadabra911/ONP";
        JLabel label = new JLabel("<html>" +
                "This application was created by Aliaksei Zayats " +
                "<br/>email: aliaksei.zayats@gmail.com" +
                "<br/>All the source code you can find at: <u>"
                + link + "</u></html>", JLabel.CENTER);

        label.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                try {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + link);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        JOptionPane.showMessageDialog(null, label, "", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) throws ScriptException {
        Solution.start();
    }
}
