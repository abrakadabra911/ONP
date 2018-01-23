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
    String input = "";
    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("JavaScript");
    JFrame frame;

    public Solution() {
        frame = new JFrame();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void calculate() {
        try {
        input = JOptionPane.showInputDialog(frame, "Proszę wprowadzić szereg ONP... (rozdzielić spacjami)");

            //sprawdzenie czy szereg nie jest pusty
            if (input == null) {
                final String link = "https://github.com/abrakadabra911/ONP";
                JLabel label = new JLabel("<html>" +
                        "This application was created by Aliaksei Zayats " +
                        "<br/>email: aliaksei.zayats@gmail.com" +
                        "<br/>All the source code you can find at: <u>"
                        +link+"</u></html>",JLabel.CENTER);

                label.addMouseListener(new MouseAdapter(){
                    public void mousePressed(MouseEvent me){
                        try{
                            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+link);
                        }catch(Exception e){e.printStackTrace();}
                    }
                });
                JOptionPane.showMessageDialog(null, label, "", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
            String[] arrays = input.split(" ");
            ArrayList<String> stos = new ArrayList<>();
            double result;

            //sprawdzenie czy nie było zbędnych spacji
            for (String x : arrays) {
                System.out.print(x);
                if (x.equals("")) {
                    repeat();
                }
            }

            // sprawdzanie całego szeregu danych
            for (String x : arrays) {
                if (isNumeric(x)) {
                    stos.add(x);
                    stos.trimToSize();
                } else {
                    String foo = stos.get(stos.size() - 2) + x + stos.get(stos.size() - 1);
                    Double res = Double.parseDouble(engine.eval(foo).toString());

                    // zdejmowanie ze stosu 2 elementów
                    stos.remove(stos.size() - 1);
                    stos.remove(stos.size() - 1);
                    stos.trimToSize();

                    // odłożenie na stos resultatu
                    stos.add(res.toString());
                }
            }
            result = Double.parseDouble(stos.get(0));

            // sprawdzenie finalne. Ilość znaków i liczb musi być kompatybilne
            if (stos.size() > 1) {
                repeat();
            }
            System.out.println(result);
            JOptionPane.showMessageDialog(frame, "wynik: " + result);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Proszę wprowadzić dane przez spację");
            repeat();
        }

        System.exit(0);
    }


    //ponowne uruchomienie
    public void repeat()  {
        JOptionPane.showMessageDialog(frame, "Proszę wprowadzić poprawny szereg. Spróbuj ponownie");
        calculate();
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws ScriptException {
        new Solution().calculate();
    }
}
