import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    public static void main(String[] args){
        Driver driver = new Driver("http://192.168.0.28/");
        driver.start();


        if(driver.entryGame()){
            driver.ready();
            System.out.println("auto 실행");
            driver.auto();
            driver.close();
        }else{
            System.out.println("존재하는 방이 없습니다. 다시 실행해 주세요.");
        }
    }
}
