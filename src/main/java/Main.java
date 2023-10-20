import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class Main {

    public static void main(String[] args) throws AWTException {

        Clipboard cp = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(":");

        cp.setContents(selection, null);

        //pasar a este formato con chatgpt
        String[] original = {
                "AF577	BILBAO	PARIS	10:10:00	72S",
                "BA467	MADRID	LONDRES	20:40:00	73S",
                "IB023	MADRID	TENERIFE	21:20:00	320",
                "IB0640	MADRID	BARCELONA	6:45:00	DS9",
                "IB318	SEVILLA	MADRID	10:45:00	320",
                "IB327	MADRID	SEVILLA	18:05:00	320",
                "IB368	MALAGA	BARCELONA	22:25:00	737",
                "IB3709	DUBLIN	BARCELONA	14:35:00	737",
                "IB3742	MADRID	BARCELONA	9:15:00	320",
                "IB510	SEVILLA	MADRID	7:45:00	320",
                "IB600	MADRID	LONDRES	10:30:00	DS9",
                "IB610	MALAGA	LONDRES	15:05:00	320",
                "IB721	BARCELONA	SEVILLA	16:40:00	320",
                "IB77B	BARCELONA	ROMA	9:45:00	320",
                "LH1349	COPENHAGUE	FRANCFORT	10:20:00	DS9"
        };


        String[] tabla = new String[original.length * 3]; // 3 componentes por cadena

        int index = 0;
        for (String s : original) {
            String[] parts = s.split(" ");
            for (String part : parts) {
                tabla[index] = part;
                index++;
            }
        }

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("Error al registrar el JNativeHook: " + ex.getMessage());
            System.exit(1);
        }

        Robot robot = new Robot();

        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_TAB);

        robot.delay(50);

        for (int i = 0; i < tabla.length; i++) {
            String part = tabla[i];

            for (int l = 0; l < part.length(); l++) {
                char letra = part.charAt(l);
                int c = KeyEvent.getExtendedKeyCodeForChar(letra);

                if (c != KeyEvent.VK_UNDEFINED) {
                    if (c == KeyEvent.VK_COLON) {
                        robot.keyPress(KeyEvent.VK_CONTROL);
                        robot.keyPress(KeyEvent.VK_V);
                        robot.keyRelease(KeyEvent.VK_CONTROL);
                        robot.keyRelease(KeyEvent.VK_V);

                        //hay que tener el : en el portapapeles

                    } else {
                        if (Character.isUpperCase(c)) {
                            robot.keyPress(KeyEvent.VK_SHIFT);
                        }

                        robot.keyPress(c);
                        robot.keyRelease(c);

                        if (Character.isUpperCase(c)) {
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                        }
                    }
                }
            }

            robot.delay(150);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
        }


    }
}
